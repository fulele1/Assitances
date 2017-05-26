package com.example.assistant.assitance;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * 闹钟
 */
public class AlarmActivity extends AppCompatActivity {

    private Button mBtnOpen;
    private Button mBtnSet;
    private Calendar calendar;
    private int hour;
    private int minute;
    private AlarmManager mAlarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm);
        initView();
        getDate();
        mBtnOpen.setOnClickListener(new SetBtnClick());
    }


    public void initView(){
        mBtnOpen = (Button) this.findViewById(R.id.open_alarm);
        mBtnSet = (Button) this.findViewById(R.id.set_alarm);
        mAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
    }


    //获取时间数据
    public void getDate(){
        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    PendingIntent pendingIntent ;
    Calendar mCalendar;

    //重置时间
    class SetBtnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //创建一个对话框时间选择器
            TimePickerDialog timePickerDialog = new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    //创建一个闹钟
                    mCalendar = Calendar.getInstance();
                    mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    mCalendar.set(Calendar.MINUTE, minute);
                    mCalendar.set(Calendar.SECOND, 0);
                    long time = mCalendar.getTimeInMillis();
                    if (time-System.currentTimeMillis()>0){
                        Intent intent = new Intent(AlarmActivity.this,RingActivity.class);
                        pendingIntent = PendingIntent.getActivity(AlarmActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                        mAlarmManager.set(AlarmManager.RTC_WAKEUP, time,pendingIntent );
                        Toast.makeText(AlarmActivity.this,"闹中将会在"+(((time-System.currentTimeMillis())/1000/60)+1)+"分钟内响起",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AlarmActivity.this,"闹钟将会在明天唤醒",Toast.LENGTH_SHORT).show();
                    }
                    mBtnSet.setText((hourOfDay<10?"0"+hourOfDay:hourOfDay)+":"+(minute<10?"0"+minute:minute));
                    mBtnOpen.setText("关闭闹钟");
                }
            }, hour, minute, true);
            timePickerDialog.show();

        }
    }


}
