package com.example.assistant.assitance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;


public class LogoActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(LogoActivity.this,LeadActivity.class);
                LogoActivity.this.startActivity(intent);
                LogoActivity.this.finish();
            }
        }, 2000);
    }
}
