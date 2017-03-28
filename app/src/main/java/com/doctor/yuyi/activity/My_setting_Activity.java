package com.doctor.yuyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.R;
import com.doctor.yuyi.lzh_utils.MyActivity;

public class My_setting_Activity extends MyActivity implements View.OnClickListener{
    private RelativeLayout my_setting_about,my_setting_yijian,my_setting_lianxi;
    private TextView my_setting_textV_exit;//退出登录
//    private TextView titleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting_);
        titleTextView= (TextView) findViewById(R.id.titleinclude_textview);
        titleTextView.setText("设置");
        my_setting_about= (RelativeLayout) findViewById(R.id.my_setting_rela_about);
        my_setting_about.setOnClickListener(this);

        my_setting_yijian= (RelativeLayout) findViewById(R.id.my_setting_rela_yijian);
        my_setting_yijian.setOnClickListener(this);

        my_setting_lianxi= (RelativeLayout) findViewById(R.id.my_setting_rela_lianxi);
        my_setting_lianxi.setOnClickListener(this);

        my_setting_textV_exit=(TextView) findViewById(R.id.my_setting_textV_exit);
        my_setting_textV_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_setting_rela_about:
                startActivity(new Intent(My_setting_Activity.this,My_setting_about_Activity.class));
                break;
            case R.id.my_setting_rela_yijian:
                    startActivity(new Intent(My_setting_Activity.this,My_setting_feadus_Activity.class));
                break;
            case R.id.my_setting_rela_lianxi:
                startActivity(new Intent(My_setting_Activity.this,My_setting_contactus_Activity.class));
                break;

            case R.id.my_setting_textV_exit:
                Toast.makeText(My_setting_Activity.this,"退出登录",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
