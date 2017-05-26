package com.example.assistant.assitance;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.assistant.assitance.com.example.assitance.adapter.HardwareAdapter;
import com.example.assistant.assitance.com.example.assitance.fragment.OneFragment;
import com.example.assistant.assitance.com.example.assitance.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 硬件加速
 */
public class HardwareActivity extends FragmentActivity {
    private ViewPager mVpg;
    private List<Fragment> fragments;
    private FragmentManager mFragmentManager;
    private RadioGroup mRgp;
    private RadioButton mBtn_one;
    private RadioButton mBtn_two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hardware);
        initView();
        getData();
        mVpg.setAdapter(new HardwareAdapter(mFragmentManager, fragments));
        mRgp.setOnCheckedChangeListener(new CheckedChange());//点击改变页面的监听事件
        mVpg.setOnPageChangeListener(new pageChange());//页面滑动后再设置当前为点击
    }


   //页面滑动后再设置当前为点击
    class pageChange implements ViewPager.OnPageChangeListener {

       @Override
       public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

       }
       @Override
       public void onPageSelected(int position) {
           switch (position){
               case 0:
                   mBtn_one.setChecked(true);
                   break;
               case 1:
                   mBtn_two.setChecked(true);
                   break;
           }
       }
       @Override
       public void onPageScrollStateChanged(int state) {

       }
   }


    //点击改变页面的监听事件
    class CheckedChange implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.radio_one:
                    mVpg.setCurrentItem(0);
                    break;
                case R.id.radio_two:
                    mVpg.setCurrentItem(1);
                    break;
            }
        }
    }


    //初始化
    public void initView(){
        mFragmentManager = this.getSupportFragmentManager();
        mVpg = (ViewPager) this.findViewById(R.id.vpg);
        fragments = new ArrayList<Fragment>();//实例化数据源
        mRgp = (RadioGroup) this.findViewById(R.id.rgp);
        mBtn_one = (RadioButton) this.findViewById(R.id.radio_one);
        mBtn_two = (RadioButton) this.findViewById(R.id.radio_two);

    }


    //获取数据源
    public void getData(){
        fragments.add(new OneFragment());
        fragments.add(new TwoFragment());
    }

}
