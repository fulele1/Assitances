package com.example.assistant.assitance.com.example.assitance.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

/**
 *
 */
public class BatteryBroadCsat extends BroadcastReceiver {
    private int level;
    private int voltage;
    private int health;
    private int temperature;
    private int status;


    @Override
    public void onReceive(Context context, Intent intent) {

        level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);//当前电量
        voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);//电池电压
        health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);//健康状态
        temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);//电池温度
        status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);//电池充电状态

                mOnReceivedFinshedListener.onReceivedFinshed(level,voltage,health,temperature,status);
    }

    private OnReceivedFinshedListener mOnReceivedFinshedListener;
    
        public  void setOnReceivedFinshedListener(OnReceivedFinshedListener onReceivedFinshedListener){
             mOnReceivedFinshedListener=onReceivedFinshedListener;
        }

    public  interface  OnReceivedFinshedListener{
        void onReceivedFinshed(int level,int voltage,int health,int temperature,int status);
    }

}
