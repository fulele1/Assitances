package com.example.assistant.assitance.com.example.assitance.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 照相机预览
 */
public class CameraPreview {

    public CameraPreview() {

    }

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private SeekBar mSkbZoom;
    private ImageButton mIdFront;
    private ImageButton mIdBackground;

    public CameraPreview(SurfaceView surfaceView,SeekBar skbZoom,ImageButton Front,ImageButton Background) {
        mSurfaceView = surfaceView;
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new MyCallBack());
        mSkbZoom=skbZoom;
        mIdBackground =Background;
        mIdFront =Front;
    }


    class MyCallBack implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            startThePreview(0);
            mSkbZoom.setMax(getMaxZoom());
            mSkbZoom.setProgress(getZoom());
            point();

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            stopThePreview();
        }
    }


    //开启预览
    public void startThePreview(int id) {
        stopThePreview();
        if (id == 0) {
            mCamera = Camera.open();
        }else {
            mCamera = Camera.open(getCameraID());
        }
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
        point();
    }


    //拍照
    public void takePicture() {
        mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //取出照片
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                //保存照片
                String sdCard = Environment.getExternalStorageDirectory().getAbsolutePath();
                String name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".jpg";
                FileOutputStream fileOutputStream =null;
                try {
                    fileOutputStream = new FileOutputStream(sdCard+"/DCIM/Camera/" + name);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

                    if(mIdBackground.getVisibility() == View.VISIBLE){
                        startThePreview(0);
                    }else{
                        startThePreview(1);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    //停止预览
    public void stopThePreview() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

    }


    //设置相机的方向
    public void point() {
        mCamera.setDisplayOrientation(90);
    }


    //获取前置摄像头的Id
    public int getCameraID() {
        int number = Camera.getNumberOfCameras();
        if (number > 1) {

            for (int i = 0; i < number; i++) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing==Camera.CameraInfo.CAMERA_FACING_FRONT){
                    return i;
                }
            }
        }
        return 0;
    }


    //获取焦距
    public int getZoom(){
        if(null!=mCamera){
            Camera.Parameters parameters =mCamera.getParameters();
            return parameters!=null?parameters.getZoom():0;
        }
        return 0;
    }


    //获取最大焦距
    public int getMaxZoom(){
        if(null!=mCamera){
            Camera.Parameters parameters =mCamera.getParameters();
            return parameters!=null?parameters.getMaxZoom():0;
        }
        return 0;

    }


    //设置焦距
    public void setZoom(int progress){
        Camera.Parameters parameters =mCamera.getParameters();
        parameters.setZoom(progress);
        mCamera.setParameters(parameters);
    }


    //获取闪光灯的模式
    public String getFlashMode(){
        Camera.Parameters parameters =mCamera.getParameters();
        if (null!=mCamera){
            String flashMode =parameters.getFlashMode();
            if (null!=flashMode){
                return flashMode;
            }
        }
        return "不支持闪光灯";
    }


    //设置闪光灯的模式
    public void setFlashMode(int mode){
        Camera.Parameters parameters =mCamera.getParameters();
        if (null!=parameters.getFlashMode()){
            switch (mode){
                case 0:
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                    mCamera.setParameters(parameters);
                    break;
                case 1:
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    mCamera.setParameters(parameters);
                    break;
                case 2:
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mCamera.setParameters(parameters);
                    break;
            }
        }
        mCamera.setParameters(parameters);
    }

}
