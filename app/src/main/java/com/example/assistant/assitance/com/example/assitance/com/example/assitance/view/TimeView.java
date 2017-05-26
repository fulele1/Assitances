package com.example.assistant.assitance.com.example.assitance.com.example.assitance.view;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *自定义时间
 */
public class TimeView extends TextView {
    private SimpleDateFormat mSimpleDateFormat;
    private Runnable mRunnable;
    private Handler mHandler;

    public TimeView(Context context) {
        this(context, null);
    }

    public TimeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        mHandler = new Handler();
        initTime();
        upData();

    }

    public void initTime(){
        setText(mSimpleDateFormat.format(new Date()));
    }

    public void upData(){
        mRunnable = new Runnable() {
            @Override
            public void run() {
                initTime();
                invalidate();
                long now = SystemClock.uptimeMillis();
                long next = now +(1000-now%1000);
                mHandler.postAtTime(mRunnable,next);
            }
        };
        mRunnable.run();
    }
}
