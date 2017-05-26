package com.example.assistant.assitance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.assistant.assitance.com.example.assitance.util.ContactWrapped;
import com.example.assistant.assitance.com.example.assitance.util.CutToCircleBit;

/**
 * 编辑联系人
 */
public class EditContactsActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton mImg;
    private String name;
    private String email;
    private String phone;
    private String address;
    private int rawContactId;
    private Bitmap photo;

    private EditText mName;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mAddress;
    private Button mBitmap;
    private ContactWrapped contactWrapped;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_contacts);
        getExtraTotalData();
        init();
        setView();
        mImg.setOnClickListener(this);
        mBitmap.setOnClickListener(this);
    }


    public void getExtraTotalData(){
        Intent intent = this.getIntent();
        byte[] a =  intent.getByteArrayExtra("photo");
        photo = CutToCircleBit.getCircleBit(BitmapFactory.decodeByteArray(a, 0, a.length));
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");
        rawContactId = intent.getIntExtra("RawContactId",-1);
    }

    public void init(){
        mImg = (ImageButton) this.findViewById(R.id.img_add);
        mName = (EditText) this.findViewById(R.id.txt_name_edit);
        mPhone = (EditText) this.findViewById(R.id.txt_phone_edit);
        mEmail = (EditText) this.findViewById(R.id.txt_email_edit);
        mAddress = (EditText) this.findViewById(R.id.txt_add_edit);
        mBitmap = (Button) this.findViewById(R.id.btn_save_edit);
        contactWrapped = new ContactWrapped(this);
    }


    public void setView(){
        mImg.setImageBitmap(photo);
        mName.setText(name);
        mPhone.setText(phone);
        mEmail.setText(email);
        mAddress.setText(address);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_add://更改图片
                upDatePic();
                break;
            case R.id.btn_save_edit://保存编辑
                Log.e(".....", "..." + rawContactId + mName.getText().toString() + mPhone.getText().toString()
                        + mEmail.getText().toString() + mAddress.getText().toString() + bitmap);

                contactWrapped.upData(rawContactId, mName.getText().toString(), mPhone.getText().toString(),
                        mEmail.getText().toString(), mAddress.getText().toString(), bitmap);
                Intent intent = new Intent(EditContactsActivity.this,AddressActivity.class);
                EditContactsActivity.this.startActivity(intent);
                break;
        }
    }


    public void upDatePic(){
        //创建一个对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String item[] = {"拍照","从图库中选择"};
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){
                    case 0://调用系统相机
                        jumpSysCamera();
                        break;
                    case 1://调用图库
                        jumpSysPic();
                        break;
                }
            }
        });
        builder.show();
    }


    public void jumpSysCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        EditContactsActivity.this.startActivityForResult(intent,1);
    }


    public void jumpSysPic(){
        Intent intents = new Intent();
        intents.setAction(Intent.ACTION_PICK);
        intents.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        EditContactsActivity.this.startActivityForResult(intents, 2);
    }
    Bitmap bitmap;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1&&null!=data){
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            mImg.setImageBitmap(CutToCircleBit.getCircleBit(bitmap));
        }
        if (requestCode ==2&&null!=data){
            Uri uri = data.getData();
            mImg.setImageURI(uri);
        }
    }

}
