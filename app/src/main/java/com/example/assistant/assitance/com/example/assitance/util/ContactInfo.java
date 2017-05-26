package com.example.assistant.assitance.com.example.assitance.util;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 联系人信息
 */
public class ContactInfo implements Parcelable{
    private int contactId;
    private int rawContactId;
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Bitmap bitmap;

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    private String firstWord;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getRawContactId() {
        return rawContactId;
    }

    public void setRawContactId(int rawContactId) {
        this.rawContactId = rawContactId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //序列化
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(contactId);
        dest.writeInt(rawContactId);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeValue(bitmap);


    }

    //反序列化
    public static final Parcelable.Creator<ContactInfo> CREATOR
            = new Parcelable.Creator<ContactInfo>() {
        public ContactInfo createFromParcel(Parcel in) {

            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setContactId(in.readInt());
            contactInfo.setRawContactId(in.readInt());
            contactInfo.setId(in.readInt());
            contactInfo.setName(in.readString());
            contactInfo.setPhone(in.readString());
            contactInfo.setEmail(in.readString());
            contactInfo.setAddress(in.readString());
            contactInfo.setBitmap((Bitmap) in.readValue(Bitmap.class.getClassLoader()));
            return contactInfo;
        }

        public ContactInfo[] newArray(int size) {
            return new ContactInfo[size];
        }
    };

}
