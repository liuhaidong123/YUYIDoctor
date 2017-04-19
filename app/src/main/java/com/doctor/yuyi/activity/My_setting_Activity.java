package com.doctor.yuyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.lzh_utils.MyActivity;
import com.doctor.yuyi.mApplication.MyApplication;

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
    public void initEmpty() {

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
//                user.clearLogin(this);
//                MyApp.removeActivity();
//                Intent intent=new Intent(SetActivity.this,My_userLogin_Activity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();

                MyApplication.removeActivity();
                UserInfo.clearLogin(this);
                Intent intent=new Intent();
                intent.setClass(My_setting_Activity.this,Login_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

                break;
        }
    }
}
