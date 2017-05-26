package com.example.assistant.assitance.com.example.assitance.com.example.assitance.view;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 自定义日期.
 */
public class DateView extends TextView {
    private SimpleDateFormat mSimpleDateFormat;
    private Runnable mRunnable;
    private Handler mHandler;
    private Calendar mCalendar;

    public DateView(Context context) {
        this(context, null);
    }

    public DateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mHandler = new Handler();
        upData();
    }

    public void initDate(){
        mCalendar = Calendar.getInstance();
       String  week = mCalendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG,Locale.CHINA);
        setText(mSimpleDateFormat.format(new Date()) + "  "+week);

    }

    public void upData(){
        mRunnable = new Runnable() {
            @Override
            public void run() {
                initDate();
                invalidate();
                long now = SystemClock.uptimeMillis();
                long next = now +(24*60*60*1000-now%24*60*60*1000);
                mHandler.postAtTime(mRunnable,next);
            }
        };
        mRunnable.run();
    }
}
