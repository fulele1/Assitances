package com.example.assistant.assitance.com.example.assitance.fragment;


import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assistant.assitance.R;
import com.example.assistant.assitance.com.example.assitance.adapter.HardwareListAdapter;
import com.example.assistant.assitance.com.example.assitance.com.example.assitance.view.PrecentView;
import com.example.assistant.assitance.com.example.assitance.enety.RunningAppInfo;
import com.example.assistant.assitance.com.example.assitance.util.AppInfoWrapped;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class OneFragment extends Fragment implements AdapterView.OnItemClickListener{

    private TextView mTxt_one;
    private TextView mTxt_three;
    private TextView mTxt_four;
    private Button mBtn_speed;
    private View view;
    private AppInfoWrapped appInfoWrapped;
    private List<RunningAppInfo> runningAppInfoList;
    private ListView mLst;
    private ActivityManager activityManager;
    private HardwareListAdapter hardwareListAdapter;
    private TextView mTxt_ci;
    private Button mBtn_ci;
    private PrecentView precentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one,null);
        initView();
        getResourcesData();//获取数据源
        setText();//设置文本信息
        hardwareListAdapter = new HardwareListAdapter(this.getActivity(), runningAppInfoList);
        mLst.setAdapter(hardwareListAdapter);
        mLst.setOnItemClickListener(this);
        activityManager =(ActivityManager) this.getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        mBtn_speed.setOnClickListener(new speedClick());
        mBtn_ci.setOnClickListener(new speedClick());

        return view;
    }


    //初始化视图
    public void initView(){

        mTxt_one = (TextView) view.findViewById(R.id.txt_one_onefragment);
        mTxt_three = (TextView) view.findViewById(R.id.txt_three_onefragment);
        mTxt_four = (TextView) view.findViewById(R.id.txt_four_onefragment);
        mLst = (ListView) view.findViewById(R.id.lst);
        mBtn_speed = (Button) view.findViewById(R.id.btn_speed);
        mTxt_ci = (TextView) view.findViewById(R.id.txt_ci);
        mBtn_ci = (Button) view.findViewById(R.id.btn_ci);
        precentView =(PrecentView) view.findViewById(R.id.view);

    }


    //获取数据源
    public void getResourcesData(){
        appInfoWrapped = new AppInfoWrapped(this.getActivity());
        runningAppInfoList = appInfoWrapped.getRunningAppinfo();
    }
    public double mAvai;
    public double mTotal;
    public double mUsed;
    public int percent;
    //设置TextView的文本
    public void setText(){

        mTotal = appInfoWrapped.totalMem;
        mAvai = appInfoWrapped.availMem;
        mUsed = appInfoWrapped.totalMem-appInfoWrapped.availMem;
        percent =(int)(mUsed/mTotal*100);

        mTxt_one.setText("应用程序："+runningAppInfoList.size()+"个");
        mTxt_three.setText(mTotal-mAvai+"MB/");
        mTxt_four.setText(mAvai+"MB");
        mTxt_ci.setText(percent+"%");
        precentView.sendData(percent);
    }



    //绑定子条目和多选框
    //@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckBox mCkb = (CheckBox) view.findViewById(R.id.ckb);
        if (mCkb.isChecked()){
            mCkb.setChecked(false);
            runningAppInfoList.get(position).setIsCheked(false);
        }
        else {
            mCkb.setChecked(true);
            runningAppInfoList.get(position).setIsCheked(true);
        }
    }


    //按钮加速
    class speedClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            List<RunningAppInfo> toKillApp = new ArrayList<RunningAppInfo>();
            for (RunningAppInfo checkedApp:runningAppInfoList) {
                if (checkedApp.isCheked()) {

                    activityManager.killBackgroundProcesses(checkedApp.getmPackageName());
                }
            }
            Toast.makeText(OneFragment.this.getActivity(), "你成功杀死了" + toKillApp.size() + "个进程", Toast.LENGTH_LONG).show();

            //更新数据
            appInfoWrapped = new AppInfoWrapped(OneFragment.this.getActivity());
            runningAppInfoList = appInfoWrapped.getRunningAppinfo();
            hardwareListAdapter.resume(runningAppInfoList);
            setText();//更新文字信息

            //更新界面所有的文字数据
            activityManager = (ActivityManager)OneFragment.this.getActivity().getSystemService(Context.ACTIVITY_SERVICE);
            mAvai = appInfoWrapped.availMem;
            mTotal = appInfoWrapped.totalMem;
    }}
}
