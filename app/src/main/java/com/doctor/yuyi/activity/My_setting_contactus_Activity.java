package com.doctor.yuyi.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.Bean_MySetting_ContactUs;
import com.doctor.yuyi.lzh_utils.MyActivity;
import com.doctor.yuyi.lzh_utils.okhttp;
import com.doctor.yuyi.lzh_utils.toast;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class My_setting_contactus_Activity extends MyActivity {
    private String teleNum;
    private final Context con=My_setting_contactus_Activity.this;
    private String resStr;
    private TextView my_settings_contactOur_phone,my_settings_contactOur_phoneNum;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(con);
                    break;
                case 1:
                    try{
                        Bean_MySetting_ContactUs info=okhttp.gson.fromJson(resStr,Bean_MySetting_ContactUs.class);
                        if ("0".equals(info.getCode())){
                            my_settings_contactOur_phone.setText(info.getResult().getTellText());
                            my_settings_contactOur_phoneNum.setText(info.getResult().getTellNum());
                        }
                        else {
                            Log.e("获取联系我们失败---","------------"+"获取林夕我们接口请求失败");
                        }
                    }
                    catch (Exception e){
                            e.printStackTrace();
                            toast.toast_gsonFaild(con);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting_contactus_);
        titleTextView= (TextView) findViewById(R.id.titleinclude_textview);
        setTitleText("联系我们");
        getData();
        teleNum=getResources().getString(R.string.my_settings_contactOur_phoneNum);
        my_settings_contactOur_phone= (TextView) findViewById(R.id.my_settings_contactOur_phone);
        my_settings_contactOur_phoneNum= (TextView) findViewById(R.id.my_settings_contactOur_phoneNum);
    }
    //拨打电话的按钮
    public void callPhone(View view) {
        if (view!=null){
            if (view.getId()==R.id.my_settings_contactOur_callPhone){
                Intent intent = new Intent();//创建一个意图对象，用来激发拨号的Activity
                intent.setAction("android.intent.action.DIAL");//android.intent.action.CALL
                intent.setData(Uri.parse("tel:"+teleNum));
                startActivity(intent);//方法内部会自动添加类别,android.intent.category.DEFAULT
            }
        }
    }

//http://192.168.1.55:8080/yuyi/contactUs/getph.do
    public void getData() {
      Map<String,String> mp=new HashMap<>();
        okhttp.getCall(Ip.URL+Ip.interface_ContactUs,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取联系我们的接口----",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
