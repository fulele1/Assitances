package com.example.assistant.assitance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends BaseActivity {

    //正常的图片的ID
    private int[] mImbIds = {R.id.imb_address_normal, R.id.imb_alarm_normal,
            R.id.imb_battery_normal, R.id.imb_camera_normal,
            R.id.imb_hardware_normal, R.id.imb_software_normal,
    };
    //按压的图片的ID
    private int[] mImbPressedIds = {R.id.imb_address_pressed, R.id.imb_alarm_pressed,
            R.id.imb_battery_pressed, R.id.imb_camera_pressed,
            R.id.imb_hardware_pressed, R.id.imb_software_pressed,
    };
    private String [] mTxts = {"通讯录","闹钟","电池","照相机", "硬件加速","软件管家"};//文字
    private ImageButton mImb[];//正常图片
    private ImageButton mImbPressed[];//按压图片
    private TextView mTxt ;//文字
    private ProgressDialog m_pDialog;//进度条对话框
    //按压图片
    private int []m_pressed = {R.mipmap.menu_address_pressed,R.mipmap.menu_alarm_pressed,
            R.mipmap.menu_battery_pressed,R.mipmap.menu_camera_pressed,
            R.mipmap.menu_hardware_pressed,R.mipmap.menu_software_pressed    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setClick();//图片的点击事件
        setLongClickEvent();//长按的点击事件
    }

    //初始化视图
    public void initView(){
        mImb= new ImageButton[6];//实例化正常图片数组
        mImbPressed= new ImageButton[6];//实例化正常图片数组
        for (int i=0;i<mImbIds.length;i++){//初始化图标
            mImb[i] = (ImageButton) this.findViewById(mImbIds[i]);//正常图片
            mImbPressed[i] = (ImageButton)this.findViewById(mImbPressedIds[i]);//按压的图片
            mImbPressed[i].setVisibility(View.GONE);

        }
        mTxt = (TextView)this.findViewById(R.id.txt1);//初始化TextView
    }

    //设置点击事件
    public void setClick(){
        for (int i=0;i<mImbIds.length;i++){
            mImb[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i=0;i<mImbIds.length;i++){
                        if (mImbIds[i]==v.getId()){
                            mImb[i].setVisibility(View.GONE);
                            mImbPressed[i].setVisibility(View.VISIBLE);
                            mTxt.setText(mTxts[i]);
                        }else{
                            mImb[i].setVisibility(View.VISIBLE);
                            mImbPressed[i].setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
    }

    //设置对话框方法
    public void setProgressD(Class cls){
        m_pDialog = new ProgressDialog(MainActivity.this);//初始化进度条对话框
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置圆形进度条
        m_pDialog.setMessage("加载中请稍等");
        m_pDialog.show();

            //跳转
            Intent intent = new Intent(MainActivity.this,cls);
            MainActivity.this.startActivity(intent);

    }
    //长按的点击事件
    public void setLongClickEvent(){
        for(int i = 0;i<mImbPressed.length;i++){
            mImbPressed[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    switch(v.getId()){
                        case R.id.imb_address_pressed://通讯录
                            setProgressD(AddressActivity.class);//设置对话框 跳转到通讯录界面
                            break;
                        case R.id.imb_alarm_pressed://闹钟
                            setProgressD(AlarmActivity.class);//设置对话框 跳转到闹钟管理界面
                            break;
                        case R.id.imb_battery_pressed://电池管理
                            setProgressD(BatteryActivity.class);//设置对话框 跳转到电池管理界面
                            break;
                        case R.id.imb_camera_pressed://照相机
                            setProgressD(CameraActivity.class);//设置对话框 跳转到照相机界面
                            break;
                        case R.id.imb_hardware_pressed://硬件加速
                            setProgressD(HardwareActivity.class);//设置对话框 跳转到硬件加速界面
                            break;
                        case R.id.imb_software_pressed://软件管理
                            setProgressD(SoftwareActivity.class);//设置对话框 跳转到软件管理界面
                            break;
                    }
                    return true;
                }
            });
        }

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        if(null!=m_pDialog){
            //m_pDialog.cancel();
            m_pDialog.dismiss();
            m_pDialog=null;
        }
    }
}
