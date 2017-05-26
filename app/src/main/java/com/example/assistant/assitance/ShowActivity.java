package com.example.assistant.assitance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assistant.assitance.com.example.assitance.util.ContactInfo;
import com.example.assistant.assitance.com.example.assitance.util.ContactWrapped;
import com.example.assistant.assitance.com.example.assitance.util.CutToCircleBit;

import java.io.ByteArrayOutputStream;

/**
 * 显示联系人
 */
public class ShowActivity extends BaseActivity implements View.OnClickListener{

    private Button mBtn;
    private Button mBtnEdit;
    private Button mBtnDel;
    private Button mBtnSend;
    private ImageButton mImgMsg;
    private Bitmap photo;
    private String name;
    private String email;
    private String phone;
    private String address;
    private int contactId;
    private int rawContactId;
    private ImageView imgAdd;
    private TextView txtName;
    private TextView txtPhone;
    private TextView txtEmail;
    private TextView txtAddress;
    private ContactWrapped contactWrapped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show);
        getExtraData();
        init();
        setView();
        setClick();

    }


    public void getExtraData(){
        Intent intent = this.getIntent();
        ContactInfo contactInfo = intent.getParcelableExtra("contact");
        photo = contactInfo.getBitmap();
        name = contactInfo.getName();
        phone = contactInfo.getPhone();
        email = contactInfo.getEmail();
        address = contactInfo.getAddress();
        rawContactId = contactInfo.getRawContactId();

    }


    public void init(){
        mBtn = (Button) this.findViewById(R.id.btn_show);
        mImgMsg = (ImageButton) this.findViewById(R.id.img_msg_show);
        mBtnEdit = (Button) this.findViewById(R.id.btn_edit_show);
        mBtnDel = (Button) this.findViewById(R.id.btn_del_show);
        mBtnSend = (Button) this.findViewById(R.id.btn_send_show);
        imgAdd = (ImageView) this.findViewById(R.id.img_address);
        txtName = (TextView) this.findViewById(R.id.txt_name_show);
        txtPhone = (TextView) this.findViewById(R.id.txt_phone_show);
        txtEmail = (TextView) this.findViewById(R.id.txt_email_show);
        txtAddress = (TextView) this.findViewById(R.id.txt_add_show);
        contactWrapped = new ContactWrapped(this);
    }


    public void setView(){
        imgAdd.setImageBitmap(CutToCircleBit.getCircleBit(photo));
        txtName.setText(name);
        txtPhone.setText(phone);
        txtEmail.setText(email);
        txtAddress.setText(address);
    }


    public void setClick(){
        mBtn.setOnClickListener(this);
        mImgMsg.setOnClickListener(this);
        mBtnEdit.setOnClickListener(this);
        mBtnDel.setOnClickListener(this);
        mBtnSend.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_show://返回
                ShowActivity.this.finish();
                break;
            case R.id.img_msg_show://发信息
                jumpMessage();
                break;
            case R.id.btn_edit_show://编辑联系人
                editContacts();
                break;
            case R.id.btn_del_show://删除联系人
                delContacts();
                break;
            case R.id.btn_send_show://发送桌面
                sendTable();
                break;
        }
    }



    public void jumpMessage(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:"+phone));
        startActivity(intent);
    }



    public void editContacts(){
        Intent intent1 = new Intent(this,EditContactsActivity.class);

        //把BitMap转为字节数组
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte [] photoByte = outputStream.toByteArray();

        intent1.putExtra("photo", photoByte);
        intent1.putExtra("name",name);
        intent1.putExtra("email",email);
        intent1.putExtra("phone",phone);
        intent1.putExtra("address",address);
        intent1.putExtra("RawContactId",rawContactId);
        startActivity(intent1);

    }


    public void delContacts(){
        //对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除");
        builder.setMessage("确定要删除联系人");
        builder.setPositiveButton("确定", new DialogBtnClick());
        builder.setNegativeButton("取消", new DialogBtnClick());
        builder.show();
    }


    //发送桌面
    public void sendTable(){
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,name);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON,photo);
        sendBroadcast(intent);
    }


    class DialogBtnClick implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:

                    contactWrapped.delete(rawContactId);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }

        }
    }

}
