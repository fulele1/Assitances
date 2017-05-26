package com.example.assistant.assitance;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.assistant.assitance.com.example.assitance.adapter.BatteryAdapter;
import com.example.assistant.assitance.com.example.assitance.fragment.MaintainFragment;
import com.example.assistant.assitance.com.example.assitance.fragment.MonitorFragment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class BatteryActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private RadioGroup mRgp;
    private RadioButton mRbtn_monitor;
    private RadioButton mRbtn_maintain;
    private List<Fragment> mFragment;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_battery);
        initView();
        getData();
        mViewPager.setAdapter(new BatteryAdapter(mFragmentManager, mFragment));
        mViewPager.setOnPageChangeListener(new Pagechanged());
        mRgp.setOnCheckedChangeListener(new checkedChanged());
    }


    public void initView(){
        mViewPager = (ViewPager) this.findViewById(R.id.vpg_battery);
        mRgp = (RadioGroup) this.findViewById(R.id.rgp_battery);
        mRbtn_monitor = (RadioButton) this.findViewById(R.id.radio_momitor);
        mRbtn_maintain = (RadioButton) this.findViewById(R.id.radio_maintain);
        mFragmentManager = this.getSupportFragmentManager();
    }

    public void getData(){
        mFragment = new ArrayList<>();
        mFragment.add(new MonitorFragment());
        mFragment.add(new MaintainFragment());
    }

class Pagechanged implements ViewPager.OnPageChangeListener {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                mRbtn_monitor.setChecked(true);
                break;
            case 1:
                mRbtn_maintain.setChecked(true);
                break;
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

class checkedChanged implements RadioGroup.OnCheckedChangeListener {

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio_momitor:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.radio_maintain:
                mViewPager.setCurrentItem(1);
                break;
        }
    }
}
}
