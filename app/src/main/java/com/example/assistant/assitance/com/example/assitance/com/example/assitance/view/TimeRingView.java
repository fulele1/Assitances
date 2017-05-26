package com.example.assistant.assitance.com.example.assitance.com.example.assitance.view;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 响铃界面自定义时间
 */
public class TimeRingView extends TimeView {
    private SimpleDateFormat mSimpleDateFormat;
    private Handler mHandler;
    private Runnable runnable;

    public TimeRingView(Context context) {
        this(context, null);
    }

    public TimeRingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public TimeRingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHandler = new Handler();
        settime();
        up();

    }

    public void settime(){
        mSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        setText(mSimpleDateFormat.format(new Date()));
    }

    public void up(){
        runnable = new Runnable() {
            @Override
            public void run() {
                initTime();
                invalidate();
                long now = SystemClock.uptimeMillis();
                long next = now +(1000-now%1000);
                mHandler.postAtTime(runnable,next);
            }
        };
        runnable.run();
    }

}
