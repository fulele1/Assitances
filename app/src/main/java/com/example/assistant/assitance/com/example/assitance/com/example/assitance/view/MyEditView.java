package com.example.assistant.assitance.com.example.assitance.com.example.assitance.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 自定义编辑框
 */
public class MyEditView extends EditText {

    public MyEditView(Context context) {
        this(context,null);
    }

    public MyEditView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }
}
