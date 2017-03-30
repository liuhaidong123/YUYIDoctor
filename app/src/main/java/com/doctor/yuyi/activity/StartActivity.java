package com.doctor.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;

public class StartActivity extends Activity {
    private int time=2;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                if (time>=0){
                    time--;
                    mHandler.sendEmptyMessageDelayed(1,1000);
                } else {
                    if (UserInfo.isLogin(StartActivity.this)){
                        startActivity(new Intent(StartActivity.this,MainActivity.class));
                    }
                    else {
                        startActivity(new Intent(StartActivity.this,Login_Activity.class));
                    }
                    finish();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mHandler.sendEmptyMessage(1);
    }
}
