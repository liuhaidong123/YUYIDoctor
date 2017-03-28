package com.doctor.yuyi.lzh_utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wanyu on 2017/2/23.
 */

public class MyActivity extends Activity{
    public TextView titleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void reBack(View view){
        if (view!=null){
            finish();
        }
    }

    public void setTitleText(String str){
        titleTextView.setText(str);
    }
}
