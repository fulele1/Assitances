package com.example.assistant.assitance;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.assistant.assitance.com.example.assitance.adapter.AddressLstAdapter;
import com.example.assistant.assitance.com.example.assitance.com.example.assitance.view.SideBar;
import com.example.assistant.assitance.com.example.assitance.util.ContactInfo;
import com.example.assistant.assitance.com.example.assitance.util.ContactWrapped;
import com.example.assistant.assitance.com.example.assitance.util.FirstWordCompare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AddressActivity extends BaseActivity implements SideBar.OnTouchingLetterChangedListener {

    private Button mBtn;
    private ListView mLstAdd;
    private SideBar mLstSide;
    private ContactWrapped mContactWrapped;
    private List<ContactInfo> mContactInfos;
    private TextView mTxtWord;
    private EditText mEdt;
    private AddressLstAdapter addressLstAdapter;
    private ImageButton mImgDelAll;

    @Override
    protected void onRestart() {
        super.onRestart();
        mContactInfos= mContactWrapped.getAllInfo();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_address);
        init();


        getData();
        mBtn.setOnClickListener(new BtnClick());
        Collections.sort(mContactInfos, new FirstWordCompare());
        addressLstAdapter = new AddressLstAdapter(this, mContactInfos);
        mLstAdd.setAdapter(addressLstAdapter);
        mLstAdd.setOnItemClickListener(new ListItemClick());
        mLstSide.setTextView(mTxtWord);
        mEdt.addTextChangedListener(new MyTextWatcher());
        mLstSide.setOnTouchingLetterChangedListener(this);
        mImgDelAll.setOnClickListener(new MyClick());
    }


    public void init(){
        mBtn = (Button) this.findViewById(R.id.btn_add_address);
        mLstAdd = (ListView) this.findViewById(R.id.lst_add);
        mLstSide = (SideBar) this.findViewById(R.id.txt_side);
        mTxtWord = (TextView) this.findViewById(R.id.txt_dialog);
        mEdt = (EditText) this.findViewById(R.id.edit_address);
        mImgDelAll = (ImageButton) this.findViewById(R.id.del_all_add);
        mImgDelAll.setVisibility(View.GONE);
    }


    @Override
    public void onTouchingLetterChanged(String s) {
        //适配器从查询的地方开始适配
        int position = addressLstAdapter.getFirstPosition(s);
        mLstAdd.setSelection(position);

//        matchContacts = new ArrayList<>();
//        for (ContactInfo matchContact :mContactInfos){
//            String first = matchContact.getFirstWord();
//
//            if (s.contains(first)){
//                matchContacts.add(matchContact);
//            }
//        }
//        addressLstAdapter.reflash(matchContacts);
    }


    class BtnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AddressActivity.this,AddActivity.class);
            AddressActivity.this.startActivity(intent);
        }
    }



    class ListItemClick implements OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(AddressActivity.this,ShowActivity.class);

            ContactInfo info = addressLstAdapter.getItem(position);
            intent.putExtra("contact",info);


            AddressActivity.this.startActivity(intent);
        }
    }



    public void getData(){
        mContactWrapped = new ContactWrapped(this);
        mContactInfos= mContactWrapped.getAllInfo();

    }

class MyTextWatcher implements TextWatcher{
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (0==s.length()){
            mImgDelAll.setVisibility(View.GONE);
        }else {
            mImgDelAll.setVisibility(View.VISIBLE);
        }

        List<ContactInfo> a  =getNewContactInfos(s.toString());
        addressLstAdapter.reflash(a);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

    List<ContactInfo> matchContacts;
    public List<ContactInfo> getNewContactInfos(String editText){
        matchContacts = new ArrayList<>();
        for (ContactInfo matchContact :mContactInfos){
            String name = matchContact.getName();
            String num = matchContact.getPhone();
            if (editText.contains(name)){
                matchContacts.add(matchContact);
            }if (editText.contains(num)){
                matchContacts.add(matchContact);
            }
        }

        return matchContacts;
    }


class MyClick implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        mEdt.setText("");
    }
}



}
