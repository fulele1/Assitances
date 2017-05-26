package com.example.assistant.assitance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.assistant.assitance.com.example.assitance.util.ContactWrapped;
import com.example.assistant.assitance.com.example.assitance.util.CutToCircleBit;

/**
 * 增加联系人界面
 */
public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton mImg;
    private EditText mEdtName;
    private EditText mEdtPhone;
    private EditText mEdtEmail;
    private EditText mEdtAddress;
    private Button mBtnSave;
    private ContactWrapped contactWrapped;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);
        init();
        mImg.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        addTextChange();
    }

    public void init(){
        mImg = (ImageButton) this.findViewById(R.id.img_add);
        mEdtName = (EditText) this.findViewById(R.id.edt_name_add);
        mEdtPhone = (EditText) this.findViewById(R.id.edt_phone_add);
        mEdtEmail = (EditText) this.findViewById(R.id.edt_email_add);
        mEdtAddress = (EditText) this.findViewById(R.id.edt_address_add);
        mBtnSave = (Button) this.findViewById(R.id.btn_save_add);
    }


    public void addTextChange(){
        mEdtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEdtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEdtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            email = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEdtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                address = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_add://拍照
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
                break;
            case R.id.btn_save_add://添加后保存
                  contactWrapped = new ContactWrapped(this);
                contactWrapped.insert(name, phone, email, address,bitmap);
                Intent intent = new Intent(AddActivity.this,AddressActivity.class);
                AddActivity.this.startActivity(intent);
                break;
        }

    }



    public void jumpSysCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        AddActivity.this.startActivityForResult(intent,1);
    }



    public void jumpSysPic(){
        Intent intents = new Intent();
        intents.setAction(Intent.ACTION_PICK);
        intents.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        AddActivity.this.startActivityForResult(intents, 2);
    }


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
