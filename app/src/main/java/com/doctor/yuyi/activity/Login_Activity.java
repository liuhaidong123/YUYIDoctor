package com.doctor.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_Activity extends Activity {

    private int timeOut = 60;//计时器
    private TextView my_userlogin_getSMScode;//获取验证码按钮
    private TextView my_userlogin_SMStimer;//显示计时器的view
    private EditText my_userlogin_edit_name, my_userlogin_edit_smdCode;//用户名与验证码输入框
    private String userName, userPsd;



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what=msg.what;
            if (what>0){
                my_userlogin_SMStimer.setText(what+ "S");
                my_userlogin_SMStimer.setVisibility(View.VISIBLE);
            }
            else if (what==0){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            handler.sendEmptyMessage(-2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                my_userlogin_SMStimer.setVisibility(View.GONE);
            }
            else if(msg.what==-2){
                my_userlogin_getSMScode.setClickable(true);
                my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_smscode));
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        my_userlogin_getSMScode = (TextView) findViewById(R.id.my_userlogin_getSMScode);
        my_userlogin_SMStimer = (TextView) findViewById(R.id.my_userlogin_SMStimer);
        my_userlogin_SMStimer.setVisibility(View.INVISIBLE);
        my_userlogin_edit_name = (EditText) findViewById(R.id.my_userlogin_edit_name);
        my_userlogin_edit_smdCode = (EditText) findViewById(R.id.my_userlogin_edit_smdCode);
        my_userlogin_getSMScode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = my_userlogin_edit_name.getText().toString();
                if (!"".equals(userName) && !TextUtils.isEmpty(userName)) {
                    if (isPhoneNum(userName)) {
                        getSMScode();
                        my_userlogin_edit_smdCode.setText("123456");//测试用

                    } else {
                        Toast.makeText(Login_Activity.this, "用户名不正确", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login_Activity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        my_userlogin_SMStimer.setVisibility(View.GONE);
        my_userlogin_getSMScode.setClickable(true);
        my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_smscode));
    }

    //获取验证码
    private void getSMScode() {
        my_userlogin_getSMScode.setClickable(false);//获取验证码按钮不能点击
        my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_unclick));
        timeOut=60;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (timeOut>0) {
                    try {
                        timeOut--;
                        handler.sendEmptyMessage(timeOut);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    //判断是否输入的为手机号
    public boolean isPhoneNum(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //登陆按钮
    public void Login(View view) {
        if (view != null) {
            if (view.getId() == R.id.my_userlogin_logninButton) {
                userName = my_userlogin_edit_name.getText().toString();
                userPsd = my_userlogin_edit_smdCode.getText().toString();
                if (isPhoneNum(userName) && !"".equals(userPsd) && !TextUtils.isEmpty(userPsd)) {
                    SharedPreferences preferences=getSharedPreferences(UserInfo.SharedPreName,MODE_APPEND);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString(UserInfo.Sname,userName);
                    editor.putString(UserInfo.SToken,userPsd);//许改动，此处放的是验证码，真实情况需存放服务器返回的token
                    editor.commit();
                    startActivity(new Intent(Login_Activity.this,MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(Login_Activity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
