package com.example.assistant.assitance.com.example.assitance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.assistant.assitance.R;
import com.example.assistant.assitance.com.example.assitance.enety.AppInfo;

import java.util.List;

/**
 * listViewd的适配器
 */
public class ListViewAdapter extends BaseAdapter {
    private List<AppInfo> mAppInfo;
    private Context mContext;
    private ViewHolder viewHolder;

    public ListViewAdapter(Context context, List<AppInfo> appInfos){
        mContext = context;
        mAppInfo = appInfos;
    }

    @Override
    public int getCount() {
        return mAppInfo.size();
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
        //初始化ViewHolder

        if (null ==convertView){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_app_list_software, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img_list_soft);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.txt_list);
            viewHolder.textView_package = (TextView) convertView.findViewById(R.id.txt_list_package);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        AppInfo appInfo = mAppInfo.get(position);
        viewHolder.imageView.setImageDrawable(appInfo.getmIcon());
        viewHolder.textView.setText(appInfo.getmLabel());
        viewHolder.textView_package.setText(appInfo.getmPackageName());
        viewHolder.imageView.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
        return convertView;
    }


    //更新数据
    public void updateData(List<AppInfo> appInfos){
        mAppInfo = appInfos;
        notifyDataSetChanged();
    }


    //声明控件
    class ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView textView_package;
    }
}
