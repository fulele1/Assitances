package com.example.assistant.assitance.com.example.assitance.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assistant.assitance.R;
import com.example.assistant.assitance.com.example.assitance.broadcast.BatteryBroadCsat;

/**
 *
 */
public class MonitorFragment extends Fragment implements BatteryBroadCsat.OnReceivedFinshedListener{


    private View view;
    private TextView mTxt_light;
    private TextView mTxt_voltage;
    private TextView mTxt_health;
    private TextView mTxt_temperature;
    private TextView mTxt_used;
    private ImageView mImg;
    BatteryBroadCsat mBatteryBroadCsat;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_monitor,null);
        initView();
        registBroadcase();
        return view;
    }


    public void initView(){
        mTxt_light = (TextView) view.findViewById(R.id.txt_light);
        mTxt_voltage = (TextView) view.findViewById(R.id.txt_voltage);
        mTxt_health = (TextView) view.findViewById(R.id.txt_health);
        mTxt_temperature = (TextView) view.findViewById(R.id.txt_temp);
        mTxt_used = (TextView) view.findViewById(R.id.txt_used);
        mImg = (ImageView) view.findViewById(R.id.img_battery);
    }

    //注册广播
    public void registBroadcase(){
        mBatteryBroadCsat=new BatteryBroadCsat();
        mBatteryBroadCsat.setOnReceivedFinshedListener(this);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        this.getActivity().registerReceiver(mBatteryBroadCsat,intentFilter);
    }


    //反注册
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getActivity().unregisterReceiver(mBatteryBroadCsat);
    }

    @Override
    public void onReceivedFinshed(int level,int voltage,int health,int temperature,int status) {
        mTxt_light.setText("电量：" + level + "%");
        mTxt_voltage.setText("电池电压：" + voltage+"mv" );
        mTxt_temperature.setText("电池温度：" + temperature/10 +"℃");


        if (status ==BatteryManager.BATTERY_STATUS_CHARGING){
            mTxt_used.setText("电池正在充电中");
        }if (status ==BatteryManager.BATTERY_STATUS_DISCHARGING){
            mTxt_used.setText("电池放电中");
        }if (status ==BatteryManager.BATTERY_STATUS_FULL){
            mTxt_used.setText("电池已充满");
        }


        if(health== BatteryManager.BATTERY_HEALTH_COLD){
            mTxt_health.setText("电池健康状态：" + "过冷" );
        } if(health== BatteryManager.BATTERY_HEALTH_DEAD){
            mTxt_health.setText("电池健康状态：" + "损坏" );
        } if(health== BatteryManager.BATTERY_HEALTH_GOOD){
            mTxt_health.setText("电池健康状态：" + "良好" );
        } if(health== BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){
            mTxt_health.setText("电池健康状态：" + "高电压" );
        } if(health== BatteryManager.BATTERY_HEALTH_OVERHEAT){
            mTxt_health.setText("电池健康状态：" + "过热" );
        } if(health== BatteryManager.BATTERY_HEALTH_UNKNOWN){
            mTxt_health.setText("电池健康状态：" + "未知" );
        } if(health== BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE){
            mTxt_health.setText("电池健康状态：" + "未说明" );
        }


        if (level >= 0 && level < 10) {
            mImg.setImageResource(R.mipmap.battery_00);
        }if (level >= 10 && level < 20) {
            mImg.setImageResource(R.mipmap.battery_20);
        }if (level >= 20 && level < 30) {
            mImg.setImageResource(R.mipmap.battery_30);
        }if (level >= 40 && level < 55) {
            mImg.setImageResource(R.mipmap.battery_40);
        }if (level >= 55 &&level < 70) {
            mImg.setImageResource(R.mipmap.battery_55);
        }if (level >= 70 && level < 85) {
            mImg.setImageResource(R.mipmap.battery_70);
        }if (level >= 85 && level < 100) {
            mImg.setImageResource(R.mipmap.battery_85);
        }if (level >= 100) {
            mImg.setImageResource(R.mipmap.battery_100);
        }



    }

}
