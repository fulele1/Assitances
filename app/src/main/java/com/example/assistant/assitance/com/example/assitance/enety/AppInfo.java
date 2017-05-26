package com.example.assistant.assitance.com.example.assitance.enety;

import android.graphics.drawable.Drawable;

/**
 * 应用程序的信息的实体类
 *
 */
public class AppInfo {
    private String mPackageName;
    private int mVersion;
    private String mVName;
    private Drawable mIcon;
    private String mLabel;
    private int flags;

    public String getmPackageName() {
        return mPackageName;
    }

    public void setmPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    public String getmLabel() {
        return mLabel;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public int getmVersion() {
        return mVersion;
    }

    public void setmVersion(int mVersion) {
        this.mVersion = mVersion;
    }

    public String getmVName() {
        return mVName;
    }

    public void setmVName(String mVName) {
        this.mVName = mVName;
    }

    public Drawable getmIcon() {
        return mIcon;
    }

    public void setmIcon(Drawable mIcon) {
        this.mIcon = mIcon;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }
}
