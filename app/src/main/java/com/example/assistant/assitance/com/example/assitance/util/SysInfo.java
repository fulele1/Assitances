package com.example.assistant.assitance.com.example.assitance.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.hardware.Camera;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 */
public class SysInfo {
    private Activity mContext;
    private TelephonyManager telephonyManager;

    public SysInfo(){

    }
    public SysInfo(Activity context){
        mContext = context;
        telephonyManager =(TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    }


    //获取系统版本
    public String getPhoneVersion(){
        return "系统版本："+Build.VERSION.RELEASE;
    }
    //获取设备型号
    public String getPhoneVersionCode(){
        return "设备型号："+Build.MODEL;
    }

    //获取手机串号
    public String getPhoneDeviceId(){
        return "手机串号："+telephonyManager.getDeviceId();
    }
    //获取手机运营商
    public String getPhoneSimOperator(){
        String simOperator = telephonyManager.getSimOperator();
        String type = "";
        if (null!=simOperator&&"46000".equals(simOperator)||"46002".equals(simOperator)){
            type = "中国移动";
        }else if ("46003".equals(simOperator)){
            type = "中国联通";

        }else if ("46004".equals(simOperator)){
            type = "中国电信";
        }
        return "运营商："+type;
    }



    private File file;
    private Reader fileReader;

    //获取CPU型号
    public String getCPUVersion() {
        file = new File("/proc/cpuinfo");

            try {
                fileReader = new FileReader(file);
                BufferedReader BufferedReader = new BufferedReader(fileReader);
                String text = BufferedReader.readLine();
                String[] textArray = text.split(":\\s");
                return "CPU型号："+textArray[1];
            } catch (Exception e) {
                return "未知";
            } finally {
                if(null!=fileReader){
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

    }

    //获取CUP的核心数
    public String getCPUNutCount(){
        file = new File("/sys/devices/system/cpu");
        File[] files = file.listFiles(new FileFilter() {//过滤器
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                if (Pattern.matches("cpu[0-9]",name)){
                    return true;
                }
                return false;
            }
        });
        return null!=files?"核心数："+files.length+"核":-1+"";
    }




    private StatFs statFs;
    private long MemAvailable;
    //获取可用内存信息
    public String getMemAvailableBlocks(){
        File path =  Environment.getExternalStorageDirectory();
        statFs = new StatFs(path.getAbsolutePath());
        long avail = statFs.getAvailableBlocksLong();
        long mem = statFs.getBlockSizeLong();
        MemAvailable = avail*mem;
        return "SD卡可用内存："+MemAvailable/1024/1024+"M";
    }

    private long MemTotal;
    //获取总内存信息
    public String getMemTotalBlocks(){
        File path =  Environment.getExternalStorageDirectory();
        statFs = new StatFs(path.getAbsolutePath());
        long Count = statFs.getBlockCountLong();
        long mem = statFs.getBlockSizeLong();
        MemTotal = Count*mem;
        //return "SD卡总内存："+new BigDecimal(MemTotal).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP).doubleValue()+"M";
        return "SD卡总："+MemTotal/1024/1024+"M";
    }


    //获取手机分辨率
    public String getPhoneRecognize(){
        int width = mContext.getWindowManager().getDefaultDisplay().getWidth();
        int height = mContext.getWindowManager().getDefaultDisplay().getHeight();
        return "分辨率："+width+"*"+height;
    }



    //获取手机像素
    public String getPhonepx(){
        DisplayMetrics metrics=new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float density=metrics.density;
        int widthPixels=metrics.widthPixels;
        int heightPixels=metrics.heightPixels;
        return "像素："+widthPixels+"*"+heightPixels+"\n"+"像素密度："+density;
    }

    private WifiManager manager;
    private WifiInfo wifiInfo;
    //获取WIFI信息
    public String getWifiInfo(){
        manager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        wifiInfo = manager.getConnectionInfo();
        String wifiName = wifiInfo.getSSID();
        int IPAdd = wifiInfo.getIpAddress();
        String ip = Formatter.formatIpAddress(IPAdd);
        int speed = wifiInfo.getLinkSpeed();
        String mac = wifiInfo.getMacAddress();
        return "Wifi的ID："+wifiName+"\n"+"Wifi的IP地址："+ip+"\n"+"Wifi的运行速度："+speed+"\n"+"Wifi的MAC地址："+mac;
    }

    BluetoothAdapter bluetoothAdapter;
    //获取蓝牙信息
    public String getBuleToothInfo(){

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
       // bluetoothAdapter = (BluetoothAdapter)mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        int state = bluetoothAdapter.getState();
        String name = bluetoothAdapter.getName();

        return "蓝牙的名字："+name+"\n"+"蓝牙的状态："+state;
    }


   // 获取是否可以多触点
    public String getmanyTouch(){
        Class c = MotionEvent.class;
       Method methods []= c.getDeclaredMethods();
       for (Method method:methods){
           String name=method.getName();
           if(name.contains("getPointerCount")||name.contains("getPointerId")){
               return "是否支持多点触控：支持";

           }
       }
        return "是否支持多点触控：不支持";
    }




    List<Camera.Size> preview;
    String text;
    //获取照片的最大尺度和判断是否有闪光灯
    public String praInfo(){
        Camera camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        preview = parameters.getSupportedPreviewSizes();
        Camera.Size cameraSize = preview.get(0);
        int height = cameraSize.height;
        int width = cameraSize.width;


        if (null!=parameters.getSupportedFlashModes()){
            text = "支持闪光灯";
        }else {
            text = "不支持闪光灯";
        }
        return "照片的最大尺寸是"+height+"*"+width+"\n"+text;
    }


    private List<List<String>> mAllListInfo;
    private List<String> mBaseInfo;
    private List<String> mCPUInfo;
    private List<String> mMemInfo;
    private List<String> mRecgInfo;
    private List<String> mpxInfo;
    private List<String> mWifiInfo;


    //获取所有的Child集合
    public List<List<String>> getAllListInfo(){
        mAllListInfo = new ArrayList<List<String>>();

        mBaseInfo = new ArrayList<String>();
        mBaseInfo.add(getPhoneVersionCode());//设备型号
        mBaseInfo.add(getPhoneVersion());//系统版本
        mBaseInfo.add(getPhoneDeviceId());//手机串号
        mBaseInfo.add(getPhoneSimOperator());//运营商

        mCPUInfo = new ArrayList<String>();
        mCPUInfo.add(getCPUVersion());//CPU型号
        mCPUInfo.add(getCPUNutCount());//CPU核心数

        mMemInfo = new ArrayList<String>();
        mMemInfo.add(getMemAvailableBlocks());//Sd卡可用内存
        mMemInfo.add(getMemTotalBlocks());//Sd卡总内存

        mRecgInfo = new ArrayList<String>();
        mRecgInfo.add(getPhoneRecognize());//分辨率
        mRecgInfo.add(praInfo());//照片最大尺度，闪光灯

        mpxInfo = new ArrayList<String>();
        mpxInfo.add(getPhonepx());//像素
        mpxInfo.add(getmanyTouch());//是否可多触控

        mWifiInfo = new ArrayList<String>();
        mWifiInfo.add(getWifiInfo());//WIFI
        mWifiInfo.add(getBuleToothInfo());//蓝牙

        mAllListInfo.add(mBaseInfo);
        mAllListInfo.add(mCPUInfo);
        mAllListInfo.add(mMemInfo);
        mAllListInfo.add(mRecgInfo);
        mAllListInfo.add(mpxInfo);
        mAllListInfo.add(mWifiInfo);
        return mAllListInfo;
    }
}
