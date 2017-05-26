package com.example.assistant.assitance;


import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.example.assistant.assitance.com.example.assitance.util.CameraPreview;


/**
 * 照相机
 */
public class CameraActivity extends BaseActivity implements View.OnClickListener{

    private SurfaceView mSurfaceView;
    private CameraPreview cameraPreview;
    private ImageButton mTakePic;
    private ImageButton mBack;
    private ImageButton mFlashMode;
    private ImageButton mIdBackground;
    private ImageButton mIdFront;
    private ImageButton mPhoto;
    private SeekBar mSkbZoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);
        init();
        setPre();//设置参数
        setBtnClick();
        mSkbZoom.setOnSeekBarChangeListener(new seekBarListern());
    }


    public void init(){
        mSurfaceView = (SurfaceView) this.findViewById(R.id.sfc_camera);
        mTakePic = (ImageButton) this.findViewById(R.id.btn_camera_photograph);
        mBack = (ImageButton) this.findViewById(R.id.btn_camera_back);
        mFlashMode = (ImageButton) this.findViewById(R.id.btn_camera_flash_mode);
        mIdBackground = (ImageButton) this.findViewById(R.id.btn_camera_background);
        mPhoto = (ImageButton) this.findViewById(R.id.btn_camera_pic);
        mIdFront = (ImageButton) this.findViewById(R.id.btn_camera_front);
        mIdFront.setVisibility(View.GONE);
        mSkbZoom = (SeekBar) this.findViewById(R.id.skb_camera);
    }


    //设置参数
    public void setPre(){
        cameraPreview = new CameraPreview(mSurfaceView,mSkbZoom,mIdFront,mIdBackground);
        mFlashMode.setImageResource(R.mipmap.camera_light_off);
    }


    public void setBtnClick(){
        mTakePic.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mFlashMode.setOnClickListener(this);
        mIdBackground.setOnClickListener(this);
        mIdFront.setOnClickListener(this);
        mPhoto.setOnClickListener(this);
    }


    //按钮的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_camera_photograph://拍照
                Log.e("。。。。。。。。。。。。。。", "点击拍照按钮");
                cameraPreview.takePicture();
                Log.e("。。。。。。。。。。。。。。", "执行完拍照的方法");
                break;
            case R.id.btn_camera_back://返回
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_camera_flash_mode://闪光灯
                changeMode();
                break;
            case R.id.btn_camera_background://摄像头切换为前置
                cameraPreview.startThePreview(1);
                mIdBackground.setVisibility(View.GONE);
                mIdFront.setVisibility(View.VISIBLE);//前置按钮显示
                break;
            case R.id.btn_camera_front://摄像头切换为后置
                cameraPreview.startThePreview(0);
                mIdFront.setVisibility(View.GONE);
                mIdBackground.setVisibility(View.VISIBLE);//后置按钮显示
                break;
            case R.id.btn_camera_pic://切换到图库
                Intent intentGo = new Intent();
                intentGo.setAction(Intent.ACTION_PICK);
                intentGo.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                this.startActivityForResult(intentGo,1);
                break;
        }

    }

    //切换闪光灯
    public void changeMode (){
        if (cameraPreview.getFlashMode().equals(Camera.Parameters.ANTIBANDING_OFF)){
            mFlashMode.setImageResource(R.mipmap.camera_light_open);
            cameraPreview.setFlashMode(1);//打开
        }else if (cameraPreview.getFlashMode().equals(Camera.Parameters.FLASH_MODE_ON)){
            cameraPreview.setFlashMode(0);//自动
            mFlashMode.setImageResource(R.mipmap.camera_light_auto);
        }else if (cameraPreview.getFlashMode().equals( Camera.Parameters.FLASH_MODE_AUTO)){
            cameraPreview.setFlashMode(2);//关闭
            mFlashMode.setImageResource(R.mipmap.camera_light_off);
        }
    }


    //进度条的监听事件
    class seekBarListern implements SeekBar.OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            cameraPreview.setZoom(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

}
