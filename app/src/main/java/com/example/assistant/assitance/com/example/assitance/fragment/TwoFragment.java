package com.example.assistant.assitance.com.example.assitance.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.assistant.assitance.R;
import com.example.assistant.assitance.com.example.assitance.adapter.TwoExpandAdapter;
import com.example.assistant.assitance.com.example.assitance.util.SysInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TwoFragment extends Fragment {

    private ExpandableListView expandableListView;
    private List<String> group;
    private SysInfo mSysInfo;
    private List<List<String>> childSysInfo;
   private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_two,null);
        initView();
        addData();
        expandableListView.setAdapter(new TwoExpandAdapter(this.getActivity(), group, childSysInfo));
        return view;
    }

    //初始化
    public void initView(){
        expandableListView = (ExpandableListView)view.findViewById(R.id.elst);
    }


    //添加数据
    public void addData(){
        group = new ArrayList<String>();
        group.add("基本信息");
        group.add("CPU");
        group.add("内存");
        group.add("分辨率");
        group.add("像素");
        group.add("WIFI");


        mSysInfo = new SysInfo(TwoFragment.this.getActivity());
        childSysInfo = mSysInfo.getAllListInfo();

    }
}
