package com.example.assistant.assitance.com.example.assitance.com.example.assitance.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class PrecentView extends View {
    private double mGetAngle;
    private double mPaintAngle;
    int mGetPercent;
    private double everyAngle =mGetAngle/5 ;
    private double [] backAngle = {-everyAngle,-everyAngle,-everyAngle,-everyAngle,-everyAngle,};
    private double [] goAngle = {everyAngle,everyAngle,everyAngle,everyAngle,everyAngle,};
    private int state = 0;

    public void sendData(int getPercent){
        mGetPercent = getPercent;
        mGetAngle = mGetPercent*36/10;
        mPaintAngle = mGetAngle;
    }
    public PrecentView(Context context) {
        this(context, null);
    }
    public PrecentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrecentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //画出矩形
    private RectF mRectF;
    private int mW;
    private int mH;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mW = MeasureSpec.getSize(widthMeasureSpec);
        mH = MeasureSpec.getSize(heightMeasureSpec);
        mRectF = new RectF(0, 0, mW, mH);
    }
    private Canvas mCanvas;
    private Paint paint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas =canvas;
        paint = new Paint();
        paint.setColor(Color.RED);
        //MyThread myThread = new MyThread();
        //myThread.start();
        mCanvas.drawArc(mRectF, 270, (int)mPaintAngle, true,paint);
    }



    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            Timer Timer = new Timer();
            Timer.schedule(new TimerTask() {
                @Override
                public void run() {


                        for (;;){
                            switch (state){
                            case 0:
                                for (int i = 0;i<backAngle.length;i++){
                                    mPaintAngle+=backAngle[i];
                                    if (i==backAngle.length){
                                        state = 1;
                                    }
                                    postInvalidate();
                                }
                                break;
                            case 1:
                                for (int i = 0;i<goAngle.length;i++){
                                    mPaintAngle+=backAngle[i];
                                    if ((int)goAngle[i]>=mPaintAngle){
                                        mPaintAngle = mGetAngle;
                                        state = -1;
                                    }
                                    postInvalidate();
                                }
                                break;
                            case -1:
                                return;
                        }
                }

                }
            }, 30, 30);
        }
    }
}
