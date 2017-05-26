package com.example.assistant.assitance.com.example.assitance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.assistant.assitance.R;
import com.example.assistant.assitance.com.example.assitance.util.ContactInfo;
import com.example.assistant.assitance.com.example.assitance.util.CutToCircleBit;

import java.util.List;

/**
 * 通讯录的适配器
 */
public class AddressLstAdapter extends BaseAdapter{

    List<ContactInfo> mContactInfos;
    public AddressLstAdapter(){

    }
    Context mContext;

    public AddressLstAdapter(Context context,List<ContactInfo> contactInfos){
        mContext = context;
        mContactInfos = contactInfos;
    }

    @Override
    public int getCount() {
        return mContactInfos.size();
    }
    ViewHolder viewHolder;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (null==convertView){
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_address,null);
            viewHolder.imageButton = (ImageButton) convertView.findViewById(R.id.img_item_add);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.txt_item_add);
            viewHolder.mark = (TextView) convertView.findViewById(R.id.txt_item_mark);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }



        viewHolder.imageButton.setImageBitmap(CutToCircleBit.getCircleBit(mContactInfos.get(position).getBitmap()));
        viewHolder.textView.setText(mContactInfos.get(position).getName());


        //合并首字母相同的姓名
        String fw = mContactInfos.get(position).getFirstWord();
        if (position==getFirstPosition(fw)){
            viewHolder.mark.setText(fw);
        }else{
            viewHolder.mark.setVisibility(View.GONE);
        }


        return convertView;
    }

    class ViewHolder{
        ImageButton imageButton;
        TextView textView;
        TextView mark;
    }

    @Override
    public ContactInfo getItem(int position) {
        return mContactInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    public int getFirstPosition(String fw){

        for (int i = 0;i<mContactInfos.size();i++){
            String first = mContactInfos.get(i).getFirstWord();//拿出所有的首字母
            if (first.equals(fw)){
                return i;
            }
        }
        return 0;
    }


    public void reflash(List<ContactInfo> matchContact){
        mContactInfos = matchContact;
        notifyDataSetChanged();

    }

}
