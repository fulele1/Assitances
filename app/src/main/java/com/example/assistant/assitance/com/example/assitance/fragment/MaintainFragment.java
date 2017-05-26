package com.example.assistant.assitance.com.example.assitance.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
 * 电池维护
 */
public class MaintainFragment extends Fragment implements BatteryBroadCsat.OnReceivedFinshedListener{
    private View view;
    private TextView mTxtMaintain;
    private TextView mTxtMaintainIng;
    private TextView mTxtGiveLightOne;
    private TextView mTxtGiveLightTwo;
    private TextView mTxtGiveLightThree;
    private ImageView mImgMaintain;
    private ImageView mImgGiveLightOne;
    private ImageView mImgGiveLightTwo;
    private ImageView mImgGiveLightThree;
    private BatteryBroadCsat mBatteryBroadCsat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceate) {
        view = inflater.inflate(R.layout.fragment_maintain,null);
        registBroadcase();
        initView();
        return view;
    }

    public void initView(){
        mTxtMaintain = (TextView) view.findViewById(R.id.txt_maintain);
        mTxtMaintainIng = (TextView) view.findViewById(R.id.txt_maintain_ing);
        mTxtGiveLightOne = (TextView) view.findViewById(R.id.txt_give_light_one);
        mTxtGiveLightTwo = (TextView) view.findViewById(R.id.txt_give_light_two);
        mTxtGiveLightThree = (TextView) view.findViewById(R.id.txt_give_light_three);
        mImgMaintain = (ImageView) view.findViewById(R.id.img_maintain);
        mImgGiveLightOne = (ImageView) view.findViewById(R.id.img_light_one);
        mImgGiveLightTwo = (ImageView) view.findViewById(R.id.img_light_two);
        mImgGiveLightThree = (ImageView) view.findViewById(R.id.img_light_three);

    }


    //注册广播
    public void registBroadcase(){
        mBatteryBroadCsat=new BatteryBroadCsat();
        mBatteryBroadCsat.setOnReceivedFinshedListener(this);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        this.getActivity().registerReceiver(mBatteryBroadCsat,intentFilter);
    }

    @Override
    public void onReceivedFinshed(int level, int voltage, int health, int temperature, int status) {

        if (status == BatteryManager.BATTERY_STATUS_CHARGING){
            mTxtMaintain.setText("电池正在充电中");
        }if (status ==BatteryManager.BATTERY_STATUS_DISCHARGING){
            mTxtMaintain.setText("电池放电中");
        }if (status ==BatteryManager.BATTERY_STATUS_FULL){
            mTxtMaintain.setText("电池已充满");
        }



        if (level >= 0 && level < 10) {
            mImgMaintain.setImageResource(R.mipmap.battery_00);
        }if (level >= 10 && level < 20) {
            mImgMaintain.setImageResource(R.mipmap.battery_20);
        }if (level >= 20 && level < 30) {
            mImgMaintain.setImageResource(R.mipmap.battery_30);
        }if (level >= 40 && level < 55) {
            mImgMaintain.setImageResource(R.mipmap.battery_40);
        }if (level >= 55 &&level < 70) {
            mImgMaintain.setImageResource(R.mipmap.battery_55);
        }if (level >= 70 && level < 85) {
            mImgMaintain.setImageResource(R.mipmap.battery_70);
        }if (level >= 85 && level < 100) {
            mImgMaintain.setImageResource(R.mipmap.battery_85);
        }if (level > 100) {
            mImgMaintain.setImageResource(R.mipmap.battery_100);
        }



        if (status == BatteryManager.BATTERY_STATUS_CHARGING){
            mTxtMaintainIng.setText("电池正在充电中");
            mImgMaintain.setImageResource(R.mipmap.maintain_charging);
        }if (status ==BatteryManager.BATTERY_STATUS_DISCHARGING){
            mTxtMaintainIng.setText("电池放电中");
        }if (status ==BatteryManager.BATTERY_STATUS_FULL){
            mTxtMaintainIng.setText("电池已充满");
        }



        if (status !=BatteryManager.BATTERY_STATUS_CHARGING){
            mTxtGiveLightOne.setText("1.未快速充电");
            mTxtGiveLightTwo.setText("2.未循环充电");
            mTxtGiveLightThree.setText("3.未涓流充电");

            mTxtGiveLightOne.setTextColor(Color.GRAY);
            mTxtGiveLightTwo.setTextColor(Color.GRAY);
            mTxtGiveLightThree.setTextColor(Color.GRAY);

            mImgGiveLightOne.setImageResource(R.mipmap.bulb_11);
            mImgGiveLightTwo.setImageResource(R.mipmap.bulb_11);
            mImgGiveLightThree.setImageResource(R.mipmap.bulb_11);

        }



        if (level<80&&status ==BatteryManager.BATTERY_STATUS_CHARGING){
            mTxtGiveLightOne.setText("1.正在快速充电");
            mTxtGiveLightTwo.setText("2.循环充电等待");
            mTxtGiveLightThree.setText("3.涓流充电等待");

            mTxtGiveLightOne.setTextColor(Color.GREEN);
            mTxtGiveLightTwo.setTextColor(Color.GRAY);
            mTxtGiveLightThree.setTextColor(Color.GRAY);

            mImgGiveLightOne.setImageResource(R.mipmap.bulb_07);
            mImgGiveLightTwo.setImageResource(R.mipmap.bulb_11);
            mImgGiveLightThree.setImageResource(R.mipmap.bulb_11);
        }if (level>=80&&level<100&&status ==BatteryManager.BATTERY_STATUS_CHARGING){
            mTxtGiveLightOne.setText("1.快速充电完成");
            mTxtGiveLightTwo.setText("2.正在循环充电");
            mTxtGiveLightThree.setText("3.涓流充电等待");

            mTxtGiveLightOne.setTextColor(Color.GRAY);
            mTxtGiveLightTwo.setTextColor(Color.GREEN);
            mTxtGiveLightThree.setTextColor(Color.GRAY);

            mImgGiveLightOne.setImageResource(R.mipmap.bulb_01);
            mImgGiveLightTwo.setImageResource(R.mipmap.bulb_07);
            mImgGiveLightThree.setImageResource(R.mipmap.bulb_11);
        }
        if (level>=100&&status ==BatteryManager.BATTERY_STATUS_CHARGING){
            mTxtGiveLightOne.setText("1.快速充电完成");
            mTxtGiveLightTwo.setText("2.循环充电完成");
            mTxtGiveLightThree.setText("3.正在涓流充电");

            mTxtGiveLightOne.setTextColor(Color.GRAY);
            mTxtGiveLightTwo.setTextColor(Color.GRAY);
            mTxtGiveLightThree.setTextColor(Color.GREEN);

            mImgGiveLightOne.setImageResource(R.mipmap.bulb_01);
            mImgGiveLightTwo.setImageResource(R.mipmap.bulb_01);
            mImgGiveLightThree.setImageResource(R.mipmap.bulb_07);
        }
    }
}
