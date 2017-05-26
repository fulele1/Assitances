package com.example.assistant.assitance.com.example.assitance.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug;

import com.example.assistant.assitance.com.example.assitance.enety.AppInfo;
import com.example.assistant.assitance.com.example.assitance.enety.RunningAppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 软件信息的封装类
 */
public class AppInfoWrapped {
    private PackageManager mPackageManagerg;
    private Context mContext;
    private AppInfo appInfo;
    private List<AppInfo> appInfoList;
    private List<RunningAppInfo> runningInfoList;
    private ActivityManager activityManager;
    private List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos;
    public double availMem;
    public double totalMem;

    public AppInfoWrapped(){

    }

    //传递数据
    public AppInfoWrapped(Context context){
        mContext = context;
        mPackageManagerg = mContext.getPackageManager();
        activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);


        //获取手机的内存信息
        ActivityManager.MemoryInfo memoryInfo=new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        availMem = memoryInfo.availMem/1024/1024;//可用内存
        totalMem =memoryInfo.totalMem/1024/1024;//总内存

    }


    //获取软件所有信息
    public List<AppInfo> getAllAppInfo(){
        appInfoList = new ArrayList<AppInfo>();
        List<PackageInfo> packageInfoList = mPackageManagerg.getInstalledPackages(0);
        for (PackageInfo packageInfo:packageInfoList){
            //获取包名
            String packageName = packageInfo.packageName;
            //获取版本号
            int versioncode = packageInfo.versionCode;
            //获取版本名
            String versionName = packageInfo.versionName;
            //获取图标
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            Drawable icon = applicationInfo.loadIcon(mPackageManagerg);
            //获取标签名
            String label= applicationInfo.loadLabel(mPackageManagerg).toString();
            //获取软件的标识
            int flags = applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM;
            //设置给每个实体
            appInfo = new AppInfo();
            appInfo.setmPackageName(packageName);
            appInfo.setmVersion(versioncode);
            appInfo.setmVName(versionName);
            appInfo.setmIcon(icon);
            appInfo.setmLabel(label);
            appInfo.setFlags(flags);

            //添加到实体集合中
            appInfoList.add(appInfo);
        }

        return  appInfoList;//返回实体集合
    }


    //获取sysAppInfo
    public List<AppInfo> sysAppInfo(){
        List<AppInfo> sysAppInfoList = new ArrayList<AppInfo>();
        List<AppInfo> info= this.getAllAppInfo();
        for (AppInfo sysInfo:info){
            if (sysInfo.getFlags() !=0){
                sysAppInfoList.add(sysInfo);
            }
        }
        return sysAppInfoList;
    }


    //获取userAppInfo
    public List<AppInfo> userAppInfo(){
        List<AppInfo> userAppInfoList = new ArrayList<AppInfo>();
        List<AppInfo> info= this.getAllAppInfo();
        for (AppInfo userInfo:info){
            if (userInfo.getFlags() ==0){
                userAppInfoList.add(userInfo);
        }
        }
        return userAppInfoList;
    }




    //获取所有的运行时软件的信息
    public List<RunningAppInfo> getRunningAppinfo()  {

        runningInfoList = new ArrayList<RunningAppInfo>();

        runningAppProcessInfos = activityManager.getRunningAppProcesses();//获取所有的运行时信息

        for (ActivityManager.RunningAppProcessInfo runPros:runningAppProcessInfos){

            int[] pid = {runPros.pid};//获得运行的Id
            String pName = runPros.processName;//进程名相当于包名
            PackageInfo packageInfo = null;

            try {
                if (null==pName||"".equals(pName)){
                    continue;
                }
                packageInfo = mPackageManagerg.getPackageInfo(pName,0);//通过包名反得到包的信息PackageInfo

                ApplicationInfo applicationIn = packageInfo.applicationInfo;
                Drawable icon =applicationIn.loadIcon(mPackageManagerg);//获得图标
                String label = applicationIn.loadLabel(mPackageManagerg).toString();//获得标签 淘宝
                String p = packageInfo.packageName.toString();//获得包名
                Debug.MemoryInfo [] memoryInfos = activityManager.getProcessMemoryInfo(pid);//获得运行时内存
                int pMem  = memoryInfos[0].dalvikPrivateDirty/1024;

                if ((applicationIn.flags& ApplicationInfo.FLAG_SYSTEM)!=0){
                    continue;
                }
                RunningAppInfo runningAppInfo = new RunningAppInfo();
                runningAppInfo.setmIcon(icon);
                runningAppInfo.setmLabel(label);
                runningAppInfo.setmPackageName(p);
                runningAppInfo.setmPmem(pMem);

                runningInfoList.add(runningAppInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return runningInfoList;
    }
}