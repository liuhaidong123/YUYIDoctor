package com.doctor.yuyi.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.bean.Bean_MySetting_Feadus;
import com.doctor.yuyi.lzh_utils.MyActivity;
import com.doctor.yuyi.lzh_utils.okhttp;
import com.doctor.yuyi.lzh_utils.toast;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//意见反馈
public class My_setting_feadus_Activity extends MyActivity {
    private TextView title;
    private final Context con=My_setting_feadus_Activity.this;
    private EditText my_settings_idea_editIdea,my_settings_idea_editContact;
    private TextView my_settings_idea_textNum;
    private String resStr;
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
                        Bean_MySetting_Feadus info=okhttp.gson.fromJson(resStr,Bean_MySetting_Feadus.class);
                        if ("0".equals(info.getCode())){
                            Toast.makeText(con,"提交成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(con,"提交失败："+info.getResult(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(con);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting_feadus_);
        title= (TextView) findViewById(R.id.activity_idea_include_title);
        title.setText("意见反馈");
        initView();
    }

    @Override
    public void initEmpty() {

    }


    private void initView() {
        my_settings_idea_editIdea= (EditText) findViewById(R.id.my_settings_idea_editIdea);
        my_settings_idea_editContact= (EditText) findViewById(R.id.my_settings_idea_editContact);
        my_settings_idea_textNum= (TextView) findViewById(R.id.my_settings_idea_textNum);
        my_settings_idea_editIdea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text=s.toString();
                if (!"".equals(s)&&!TextUtils.isEmpty(text)){
                    int length=text.length();
                    my_settings_idea_textNum.setText(length+"/"+200);
                }
                else {
                    my_settings_idea_textNum.setText(0+"/"+200);
                }
            }
        });

    }
    public void goBack(View view){
        if (view!=null){
            if (view.getId()==R.id.activity_idea_include_imageReturn){
                finish();
            }
        }
    }

    //确定按钮
    public void submitIdea(View view) {
        String contact=my_settings_idea_editContact.getText().toString();
        String content=my_settings_idea_editIdea.getText().toString();
        if (!"".equals(contact)&&!TextUtils.isEmpty(contact)&&!"".equals(content)&&!TextUtils.isEmpty(content)){
            Map<String,String> mp=new HashMap<>();
            mp.put("token", UserInfo.UserToken);
            mp.put("content",content);
            mp.put("contact",contact);
            okhttp.getCall(Ip.URL+Ip.interface_MySetting_FeadUs,mp,okhttp.OK_GET).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("提交意见反馈----",resStr);
                    handler.sendEmptyMessage(1);
                }
            });
        }else {
            Toast.makeText(con,"信息不完整",Toast.LENGTH_SHORT).show();
        }
    }
}
