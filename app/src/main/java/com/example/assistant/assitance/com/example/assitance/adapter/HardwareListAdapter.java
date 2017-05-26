package com.example.assistant.assitance.com.example.assitance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assistant.assitance.R;
import com.example.assistant.assitance.com.example.assitance.enety.RunningAppInfo;

import java.util.List;

/**
 *
 */
public class HardwareListAdapter extends BaseAdapter {

    private List<RunningAppInfo> mRunningAppInfos;
    private Context mContext;
    private ViewHolder holder ;

    public HardwareListAdapter(Context context,List<RunningAppInfo> runningAppInfos){
        mRunningAppInfos = runningAppInfos;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mRunningAppInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(null ==convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_hardware,null);
            holder.ImageView = (android.widget.ImageView) convertView.findViewById(R.id.img);
            holder.txt_packageName = (TextView) convertView.findViewById(R.id.txt_item_packageName);
            holder.txt_mem = (TextView) convertView.findViewById(R.id.txt_item_mem);

            convertView.setTag(holder);
        }else {
            holder =(ViewHolder) convertView.getTag();
        }
        holder.ImageView.setImageDrawable(mRunningAppInfos.get(position).getmIcon());
        holder.txt_packageName.setText(mRunningAppInfos.get(position).getmLabel());
        holder.txt_mem.setText(""+mRunningAppInfos.get(position).getmPmem()+"MB");
        return convertView;
    }


    //更新数据
    public void resume(List<RunningAppInfo> runningAppInfos){
        mRunningAppInfos = runningAppInfos;
        notifyDataSetChanged();



    }
    //convertView
    class ViewHolder{
        ImageView ImageView;
        TextView txt_packageName;
        TextView txt_mem;
    }
}
