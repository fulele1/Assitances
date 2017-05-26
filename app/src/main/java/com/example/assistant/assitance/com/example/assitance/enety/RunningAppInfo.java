package com.example.assistant.assitance.com.example.assitance.enety;

import android.graphics.drawable.Drawable;

/**
 *
 */
public class RunningAppInfo {
    private Drawable mIcon;
    private String mLabel;
    private long mAvalMemoryInfo;
    private long mTotalMemoryInfo;
    private long mPmem;
    private boolean isCheked;

    public boolean isCheked() {
        return isCheked;
    }

    public void setIsCheked(boolean isCheked) {
        this.isCheked = isCheked;
    }

    public long getmPmem() {

        return mPmem;
    }

    public void setmPmem(long mPmem) {
        this.mPmem = mPmem;
    }

    public String getmPackageName() {
        return mPackageName;
    }

    public void setmPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    private String mPackageName;

    public long getmAvalMemoryInfo() {
        return mAvalMemoryInfo;
    }

    public void setmAvalMemoryInfo(long mAvalMemoryInfo) {
        this.mAvalMemoryInfo = mAvalMemoryInfo;
    }

    public long getmTotalMemoryInfo() {
        return mTotalMemoryInfo;
    }

    public void setmTotalMemoryInfo(long mTotalMemoryInfo) {
        this.mTotalMemoryInfo = mTotalMemoryInfo;
    }

    public long getmMemoryInfo() {
        return mAvalMemoryInfo;
    }

    public void setmMemoryInfo(int mMemoryInfo) {
        this.mAvalMemoryInfo = mMemoryInfo;
    }

    public String getmLabel() {
        return mLabel;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public Drawable getmIcon() {
        return mIcon;
    }

    public void setmIcon(Drawable mIcon) {
        this.mIcon = mIcon;
    }
}
