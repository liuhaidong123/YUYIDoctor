package com.doctor.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.lzh_utils.MyActivity;
import com.doctor.yuyi.lzh_utils.XCRoundRectImageView;

//我的页面，关于我们
public class My_setting_about_Activity extends MyActivity {
    private XCRoundRectImageView roundRectImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting_about_);
        titleTextView= (TextView) findViewById(R.id.titleinclude_textview);
        setTitleText("关于宇医");
        roundRectImageView= (XCRoundRectImageView) findViewById(R.id.my_settings_aboutOurs_imageview);
        roundRectImageView.setRadios(12.5f);
    }
}
