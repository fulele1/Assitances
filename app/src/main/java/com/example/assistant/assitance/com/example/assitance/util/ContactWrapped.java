package com.example.assistant.assitance.com.example.assitance.util;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.provider.ContactsContract;

import com.example.assistant.assitance.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 联系人的增删改查封装类
 */
public class ContactWrapped {
    public ContactWrapped(){

    }
    Context mContext;
    ContentResolver mContentResolver;

    public ContactWrapped(Context context){
        mContext = context;
        mContentResolver =mContext.getContentResolver();

    }

    //插入
    public void insert(String name,String phone,String address,String email,Bitmap bitmap){
        List<ContentProviderOperation> contentProviderOperations = new ArrayList<ContentProviderOperation>();
        ContentProviderOperation.Builder rawBuilder = ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI);//插入
        rawBuilder.withValue(ContactsContract.RawContacts.ACCOUNT_NAME,null);//增加一行,ID自动增长，为空表示默认的账户
        contentProviderOperations.add(rawBuilder.build());//生成操作类对象

        //插入姓名
        ContentProviderOperation.Builder nameBuilder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);//一个字段就是一个builder
        nameBuilder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);//把集合中的第一个元素作为键的值
        nameBuilder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);//往哪里插入什么类型的数据
        nameBuilder.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name);
        contentProviderOperations.add(nameBuilder.build());

        //插入电话
        ContentProviderOperation.Builder phoneBuilder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
        phoneBuilder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);//把集合中的第一个元素作为键的值
        phoneBuilder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);//往那里插入什么类型的数据
        phoneBuilder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
        contentProviderOperations.add(phoneBuilder.build());

        //插入图片
        ContentProviderOperation.Builder photoBuilder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
        photoBuilder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);
        photoBuilder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);//往那里插入什么类型的数据
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] photo = outputStream.toByteArray();
        photoBuilder.withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, photo);
        contentProviderOperations.add(photoBuilder.build());

        //插入地址
        ContentProviderOperation.Builder addressBuilder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
        addressBuilder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);//把集合中的第一个元素作为键的值
        addressBuilder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE);//往那里插入什么类型的数据
        addressBuilder.withValue(ContactsContract.CommonDataKinds.SipAddress.SIP_ADDRESS,address);
        contentProviderOperations.add(addressBuilder.build());

        //插入邮件
        ContentProviderOperation.Builder emailBuilder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
        emailBuilder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);//把集合中的第一个元素作为键的值
        emailBuilder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);//往那里插入什么类型的数据
        emailBuilder.withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, email);
        contentProviderOperations.add(emailBuilder.build());

        try {
            mContentResolver.applyBatch(ContactsContract.AUTHORITY, (ArrayList<ContentProviderOperation>) contentProviderOperations);//批量处理
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }

    }

    //删除联系人
    public void delete(int Rawcontacts){
        ArrayList<ContentProviderOperation> contentProviderOperation = new ArrayList<ContentProviderOperation>();
        ContentProviderOperation.Builder delBuilder = ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI);
        delBuilder.withSelection(ContactsContract.RawContacts._ID+"=?",new String[]{Rawcontacts+""});//通过RawContactsId删除
        contentProviderOperation.add(delBuilder.build());
        try {
            mContentResolver.applyBatch(ContactsContract.AUTHORITY,contentProviderOperation);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }


    //更新界面
    public void upData(int RawContactId,String name,String phone,String email,String address,Bitmap bitmap){
        ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<ContentProviderOperation>();

        ContentProviderOperation.Builder nameBuilder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        nameBuilder.withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? and " + ContactsContract.Data.MIMETYPE + "=?",
                new String[]{RawContactId + "", ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE});
        nameBuilder.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name);
        contentProviderOperations.add(nameBuilder.build());


        ContentProviderOperation.Builder phoneBuilder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        phoneBuilder.withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? and " + ContactsContract.Data.MIMETYPE + "=?",
                new String[]{RawContactId + "", ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE});
        phoneBuilder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
        contentProviderOperations.add(phoneBuilder.build());


        ContentProviderOperation.Builder emailBuilder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        emailBuilder.withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? and " + ContactsContract.Data.MIMETYPE + "=?",
                new String[]{RawContactId + "", ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE});
        emailBuilder.withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, email);
        contentProviderOperations.add(emailBuilder.build());


        ContentProviderOperation.Builder addressBuilder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        addressBuilder.withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? and " + ContactsContract.Data.MIMETYPE + "=?",
                new String[]{RawContactId + "", ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE});
        addressBuilder.withValue(ContactsContract.CommonDataKinds.SipAddress.SIP_ADDRESS,address);
        contentProviderOperations.add(addressBuilder.build());


        ContentProviderOperation.Builder photoBuilder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte [] photo = byteArrayOutputStream.toByteArray();
        photoBuilder.withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? and " + ContactsContract.Data.MIMETYPE + "=?",
                new String[]{RawContactId + "", ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE});
        photoBuilder.withValue(ContactsContract.CommonDataKinds.Photo.PHOTO,photo);
        contentProviderOperations.add(photoBuilder.build());

        try {
            mContentResolver.applyBatch(ContactsContract.AUTHORITY,contentProviderOperations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }


    public List<ContactInfo> getAllInfo(){
        List<ContactInfo> contactInfos = new ArrayList<ContactInfo>();
        Cursor cursorContact = mContentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);//contacts
        while(cursorContact.moveToNext()){
            ContactInfo contactInfo = new ContactInfo();

            int contactId = cursorContact.getInt(cursorContact.getColumnIndex(ContactsContract.Contacts._ID));//contactId
            contactInfo.setContactId(contactId);//设置contactId
            Cursor cursorRaw = mContentResolver.query(ContactsContract.RawContacts.CONTENT_URI, null,
                    ContactsContract.RawContacts.CONTACT_ID + "=?", new String[]{contactId + ""}, null);//RawContacts
            while(cursorRaw.moveToNext()){
                int rawId = cursorRaw.getInt(cursorRaw.getColumnIndex(ContactsContract.RawContacts._ID));//rawId
                contactInfo.setRawContactId(rawId);//设置rawId
                contactInfo.setName(getName(rawId));//设置姓名
                contactInfo.setPhone(getPhone(rawId));//设置电话
                contactInfo.setAddress(getAddress(rawId));//设置地址
                contactInfo.setEmail(getEmail(rawId));//设置邮箱
                contactInfo.setBitmap(getPhoto(rawId));//设置头像
                contactInfo.setFirstWord(getFirstWord(getName(rawId)));
                contactInfos.add(contactInfo);
            }
            cursorRaw.close();
        }
        cursorContact.close();
        return contactInfos;
    }


    //获取姓名
    public String getName(int rawId){
        Cursor cursorData = mContentResolver.query(ContactsContract.Data.CONTENT_URI, null,
                        ContactsContract.Data.RAW_CONTACT_ID + "=? and "+ ContactsContract.Data.MIMETYPE+"=?",
                new String[]{"" + rawId, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE}, null);//Data
                while(cursorData.moveToNext()){
                    String name = cursorData.getString
                            (cursorData.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));//dataId 具体信息
                    return name;
                }
        cursorData.close();
        return "无名";
    }



    //获取电话
    public String getPhone(int rawId){
        Cursor cursorData = mContentResolver.query(ContactsContract.Data.CONTENT_URI, null,
                ContactsContract.Data.RAW_CONTACT_ID + "=? and "+ ContactsContract.Data.MIMETYPE+"=?",
                new String[]{"" + rawId, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE}, null);//Data
        while(cursorData.moveToNext()){
            String phone = cursorData.getString
                    (cursorData.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));//dataId 具体信息
            return phone;
        }
        cursorData.close();
        return "";
    }


    //获取Email
    public String getEmail(int rawId){
        Cursor cursorData = mContentResolver.query(ContactsContract.Data.CONTENT_URI, null,
                ContactsContract.Data.RAW_CONTACT_ID + "=? and "+ ContactsContract.Data.MIMETYPE+"=?",
                new String[]{"" + rawId, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE}, null);//Data
        while(cursorData.moveToNext()){
            String email = cursorData.getString
                    (cursorData.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));//dataId 具体信息
            return email;
        }
        cursorData.close();
        return "";
    }



    //获取地址
    public String getAddress(int rawId){
        Cursor cursorData = mContentResolver.query(ContactsContract.Data.CONTENT_URI, null,
                ContactsContract.Data.RAW_CONTACT_ID + "=? and "+ ContactsContract.Data.MIMETYPE+"=?",
                new String[]{"" + rawId, ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE}, null);//Data
        while(cursorData.moveToNext()){
            String email = cursorData.getString
                    (cursorData.getColumnIndex(ContactsContract.CommonDataKinds.SipAddress.SIP_ADDRESS));//dataId 具体信息
            return email;
        }
        cursorData.close();
        return "";
    }


    //获取图片
    public Bitmap getPhoto (int rawId){
        Cursor cursorData = mContentResolver.query(ContactsContract.Data.CONTENT_URI, null,
                ContactsContract.Data.RAW_CONTACT_ID + "=? and "+ ContactsContract.Data.MIMETYPE+"=?",
                new String[]{"" + rawId, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE}, null);//Data
        while(cursorData.moveToNext()){
            byte [] pic = cursorData.getBlob(cursorData.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
            Bitmap photo = BitmapFactory.decodeByteArray(pic, 0, pic.length);

            return photo;
        }
        cursorData.close();
        return BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.default_contact_head_icon);
    }


    //获取首字母
    public String getFirstWord(String name){
        CharacterParser characterParser = CharacterParser.getInstance();
        String firstWord = characterParser.getSelling(name).subSequence(0,1).toString().toUpperCase();
        if(Pattern.matches("[A-Z]",firstWord)){
            return firstWord;
        }else {
            return "#";
        }
    }
}
