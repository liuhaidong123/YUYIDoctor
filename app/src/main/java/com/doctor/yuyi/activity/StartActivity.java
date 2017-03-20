package com.doctor.yuyi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doctor.yuyi.R;

public class StartActivity extends AppCompatActivity {
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
                    startActivity(new Intent(StartActivity.this,MainActivity.class));
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
