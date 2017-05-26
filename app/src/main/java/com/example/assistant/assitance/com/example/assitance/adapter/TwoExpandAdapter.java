package com.example.assistant.assitance.com.example.assitance.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 可扩展的ListView的适配器
 */
public class TwoExpandAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mGroup;
    private List<List<String>> mChildSysInfo;

    public TwoExpandAdapter(Context context,List<String> group,List<List<String>> child){
        mContext = context;
        mGroup = group;
        mChildSysInfo = child;
    }


    @Override
    public int getGroupCount() {
        return mGroup.size();
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildSysInfo.get(groupPosition).size();
    }



    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView groupText;
        if(null==convertView){
            groupText = new TextView(mContext);
        }else{
            groupText= (TextView) convertView;
        }
        groupText.setText(mGroup.get(groupPosition));
        groupText.setTextSize(30);
        groupText.setTextColor(Color.GREEN);
        return groupText;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView childText = new TextView(mContext);
        childText.setText(mChildSysInfo.get(groupPosition).get(childPosition));
        return childText;
    }






    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
