package com.example.assistant.assitance.com.example.assitance.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *
 */
public class BatteryAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public BatteryAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        mFragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
