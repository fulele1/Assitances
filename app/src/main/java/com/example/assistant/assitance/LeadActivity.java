package com.example.assistant.assitance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.assistant.assitance.com.example.assitance.util.Screen;

import java.util.ArrayList;
import java.util.List;


public class LeadActivity extends BaseActivity {
    private ViewPager mVpg;
    private int [] mImgIds = {R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};//数据源
    private int [] mImgPointsIds = {R.id.img_point1,R.id.img_point2,R.id.img_point3};//红点的Id
    private List<Integer> mimg;
    private Button mBtn;//按钮
    private ImageView mImg_point[];//红点
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = this.getSharedPreferences("count.xml",MODE_PRIVATE);
        Boolean come = preferences.getBoolean("first",true);
        int count = preferences.getInt("start_count",0);
        if (come){
            setContentView(R.layout.activity_lead);
            SharedPreferences.Editor editor =preferences.edit();
            editor.putBoolean("first",false);
           // editor.putInt("start_count",++count);
            editor.commit();

            initView();//初始化
            mVpg.setAdapter(new MyAdapter());//設置適配器
            eventPager();//圖片监听事件
            eventButton();//按鈕的點擊事件
        }else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }





    }

    //初始化視圖
    public void initView(){
        mVpg = (ViewPager)this.findViewById(R.id.vpg);
        mImg_point = new ImageView[3];
        for (int i =0;i<mImgPointsIds.length;i++){
            mImg_point[i]=(ImageView)this.findViewById(mImgPointsIds[i]);//红点
        }
        mBtn = (Button)this.findViewById(R.id.btn);//按钮
        mBtn.setVisibility(View.INVISIBLE);
        mimg =new ArrayList<Integer>();
        for (int i = 0;i<mImgIds.length;i++){

            mimg.add(mImgIds[i]);
        }
    }

    //页面是否改变监听事件
    public void eventPager(){
        mVpg.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //显示按钮
                if (position == mimg.size() - 1) {
                    mBtn.setVisibility(View.VISIBLE);
                } else {
                    mBtn.setVisibility(View.INVISIBLE);
                }

                //设置红点
                for (int i = 0; i < mImg_point.length; i++) {
                    if (position == i) {
                        mImg_point[i].setBackgroundResource(R.mipmap.presence_online);
                    } else {
                        mImg_point[i].setBackgroundResource(R.mipmap.presence_invisible);
                    }
                }
            }

            @Override

            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //按鈕的點擊事件
    public void eventButton(){
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeadActivity.this,MainActivity.class);
                LeadActivity.this.startActivity(intent);
                LeadActivity.this.finish();
            }
        });
    }

    /**
     * 适配器
     * */
    class MyAdapter extends PagerAdapter{

        //数据源的长度
        @Override
        public int getCount() {
            return mImgIds.length;
        }

        //获得子视图
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=new ImageView(LeadActivity.this);
            //获得大图
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mimg.get(position));
            Bitmap bigBitmap= Bitmap.createScaledBitmap(bitmap, Screen.getScreenWidth(LeadActivity.this),
                    Screen.getScreenHeight(LeadActivity.this),true);
            imageView.setImageBitmap(bigBitmap);
            //添加到视图组
            container.addView(imageView);
            return imageView;
        }

        //摧毁子视图
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        //视图是否是Object里的
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
