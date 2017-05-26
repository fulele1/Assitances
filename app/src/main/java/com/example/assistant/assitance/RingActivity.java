package com.example.assistant.assitance;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * 响铃界面
 */
public class RingActivity extends BaseActivity{

    private Button btnRing;
    private Button btnRingSleep;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ring);
        init();
        btnRing.setOnClickListener(new BtnRingClick());
        btnRingSleep.setOnClickListener(new BtnRingClick());
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            mediaPlayer.setDataSource(path+"/Music/Goldberg_Variations.mp3/");
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void init(){
        btnRing = (Button) this.findViewById(R.id.btn_ring);
        btnRingSleep = (Button) this.findViewById(R.id.btn_ring_sleep);
    }


    class BtnRingClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_ring://关闭
                    RingActivity.this.finish();
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    break;
                case R.id.btn_ring_sleep://继续

                    break;
            }

        }
    }

}
