package com.doctor.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.MyUtils.MyDialog;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.bean.Bean_Login;
import com.doctor.yuyi.bean.Bean_SMSCode;
import com.doctor.yuyi.lzh_utils.okhttp;
import com.doctor.yuyi.lzh_utils.toast;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.string.ok;

public class Login_Activity extends Activity {

    private int timeOut = 60;//计时器
    private TextView my_userlogin_getSMScode;//获取验证码按钮
    private EditText my_userlogin_edit_name, my_userlogin_edit_smdCode;//用户名与验证码输入框
    private String userName, userPsd;
    private String cookie;
        private String resStr;
    private Handler hand=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    MyDialog.stopDia();
                    toast.toast_faild(Login_Activity.this);
                    break;
                case 1:
                    MyDialog.stopDia();
                    try{
                        Bean_SMSCode smsCode=okhttp.gson.fromJson(resStr,Bean_SMSCode.class);
                        if ("0".equals(smsCode.getCode())){
//                            my_userlogin_edit_smdCode.setText(smsCode.getResult());
                        }
                        else {
                            Toast.makeText(Login_Activity.this,"获取验证失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    catch (Exception e){
                        toast.toast_gsonFaild(Login_Activity.this);
                    }
                    break;
                case 2:
                    MyDialog.stopDia();
                    try{
                        Bean_Login login=okhttp.gson.fromJson(resStr,Bean_Login.class);
                        if ("0".equals(login.getCode())){
                            Toast.makeText(Login_Activity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                            SharedPreferences preferences=getSharedPreferences(UserInfo.SharedPreName,MODE_APPEND);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString(UserInfo.Sname,userName);
                    editor.putString(UserInfo.SToken,login.getResult());//许改动，此处放的是验证码，真实情况需存放服务器返回的token
                    editor.commit();
                            UserInfo.UserToken=login.getResult();
                            UserInfo.UserName=userName;
                    startActivity(new Intent(Login_Activity.this,MainActivity.class));
                    finish();
                        }
                        else if ("-1".equals(login.getCode())){
                            Toast.makeText(Login_Activity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Login_Activity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(Login_Activity.this);
                    }
                    break;
            }
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what=msg.what;
            if (what>0){
                my_userlogin_getSMScode.setText("剩余 "+what+ "s");
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
                my_userlogin_getSMScode.setText("发送验证码");
            }
            else if(msg.what==-2){
                my_userlogin_getSMScode.setClickable(true);
                my_userlogin_getSMScode.setBackgroundResource(R.drawable.my_userlogin_loginbutton);
            }
        }
    };

    private EditText mMyStatus_Num;
    private ImageView mMyStatus_Img;
    private long mCurrentMillis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_login_);
        Intent intent = getIntent();
        String scheme = intent.getScheme();
        Uri uri = intent.getData();
        if (uri != null) {
            String host = uri.getHost();
            String dataString = intent.getDataString();
            String id = uri.getQueryParameter("id");
            String path = uri.getPath();
            String path1 = uri.getEncodedPath();
            String queryString = uri.getQuery();
            Log.e("scheme",scheme);
            Log.e("host",host);
            Log.e("dataString",host);
            Log.e("id",host);
            Log.e("path",host);
            Log.e("path1",host);
            Log.e("queryString",host);
        }
        initView();
    }



    private void initView() {
        my_userlogin_edit_name = (EditText) findViewById(R.id.my_userlogin_edit_name);
        my_userlogin_edit_smdCode = (EditText) findViewById(R.id.my_userlogin_edit_smdCode);
        my_userlogin_getSMScode= (TextView) findViewById(R.id.my_userlogin_getSMScode);
        my_userlogin_getSMScode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = my_userlogin_edit_name.getText().toString();
                if (!"".equals(userName) && !TextUtils.isEmpty(userName)) {
                    if (isPhoneNum(userName)) {
                        getSMScode();
//                        my_userlogin_edit_smdCode.setText("123456");//测试用

                    } else {
                        Toast.makeText(Login_Activity.this, "用户名不正确", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login_Activity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCurrentMillis = System.currentTimeMillis();
        mMyStatus_Num = (EditText) findViewById(R.id.my_status_num_edit);
        mMyStatus_Img = (ImageView) findViewById(R.id.my_status_num_img);
        //获取动态验证码
        getDynamicNumAndCookie();
        //重新获取动态验证码
        mMyStatus_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentMillis = System.currentTimeMillis();
                getDynamicNumAndCookie();
            }
        });
    }

    //获取验证码
    private void getSMScode() {
        MyDialog.showDialog(Login_Activity.this);
        my_userlogin_getSMScode.setClickable(false);//获取验证码按钮不能点击
        my_userlogin_getSMScode.setBackgroundResource(R.drawable.my_userlogin_unclick);
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
        Map<String,String> mp=new HashMap<>();
        mp.put("id",my_userlogin_edit_name.getText().toString());
        mp.put("ts",String.valueOf(mCurrentMillis));
        mp.put("imgcode", mMyStatus_Num.getText().toString());
        okhttp.getCallCookie(Ip.URL+Ip.interface_SMSCode,mp,cookie).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hand.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取验证码---",resStr);
                hand.sendEmptyMessage(1);

            }
        });
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
                if (!"".equals(userName)&&!TextUtils.isEmpty(userName)&& !"".equals(userPsd) && !TextUtils.isEmpty(userPsd)){
                    if (isPhoneNum(userName)){
                        MyDialog.showDialog(Login_Activity.this);
                        Map<String,String>mp=new HashMap<>();
                        mp.put("id",userName);
                        mp.put("vcode",userPsd);
                        okhttp.getCallCookie(Ip.URL+Ip.interface_Login,mp,cookie).enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                hand.sendEmptyMessage(0);
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                resStr=response.body().string();
                                Log.i("登陆返回---",resStr);
                                hand.sendEmptyMessage(2);
                            }
                        });
                    }
                    else {
                        Toast.makeText(Login_Activity.this,"您输入的手机号不正确",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Login_Activity.this, "您输入的手机号或验证码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 获取动态验证码以及cookie
     */
    public void getDynamicNumAndCookie() {

        Call call = okhttp.getCall(UrlTools.BASE + UrlTools.URL_GET_DYNAMIC_NUM + "ts=" + mCurrentMillis, null, 0);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("获取动态验证码错误", e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    cookie = response.headers().get("Set-Cookie");
                    //Log.e("动态验证码myCooike=", myCooike);
                    InputStream inputStream = response.body().byteStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bitmap!=null){
                                mMyStatus_Img.setImageBitmap(bitmap);
                            }else {
                                mMyStatus_Img.setBackgroundResource(R.color.color_ad);
                            }

                        }
                    });
                } else {
                    Log.e("onResponse--", "获取动态验证码错误");
                }

            }
        });
    }
}
