package com.example.assistant.assitance.com.example.assitance.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6 0006.
 */
public class HardwareAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;
    public HardwareAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
