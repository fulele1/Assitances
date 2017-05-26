package com.example.assistant.assitance.com.example.assitance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assistant.assitance.R;
import com.example.assistant.assitance.com.example.assitance.enety.AppInfo;

import java.util.List;

/**
 * 适配器
 */
public class GirdViewAdapter extends BaseAdapter {
    private List<AppInfo> mAppInfo;
    private Context mContext;
    private ViewHolder viewHolder;

    public GirdViewAdapter(Context context, List<AppInfo> appInfos){
        mContext = context;
        mAppInfo = appInfos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //初始化ViewHolder
        if (null ==convertView){
            viewHolder= new ViewHolder();//为空时New一个viewHolder
            convertView =  LayoutInflater.from(mContext).inflate(R.layout.item_app_software,null);//把布局转为视图赋给converView
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img);//把convertView中控件赋给viewHolder中的控件
            viewHolder.textView = (TextView) convertView.findViewById(R.id.txt);
            convertView.setTag(viewHolder);//把ViewHolder对象赋给convertView

        }else {
            viewHolder = (ViewHolder)convertView.getTag();//获取已经加载的地址赋给已有的viewHolder
        }
        AppInfo appInfo =mAppInfo.get(position);
        viewHolder.imageView.setImageDrawable(appInfo.getmIcon());
        viewHolder.textView.setText(appInfo.getmLabel());
        convertView.setLayoutParams(new GridView.LayoutParams(150, 250));
        return convertView;
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


    //更新数据
    public void updateData(List<AppInfo> appInfos){
        mAppInfo = appInfos;
        notifyDataSetChanged();
    }


    //声明控件
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
