package com.doctor.yuyi.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.Bean_MySetting_AboutUs;
import com.doctor.yuyi.lzh_utils.MyActivity;
import com.doctor.yuyi.lzh_utils.XCRoundRectImageView;
import com.doctor.yuyi.lzh_utils.okhttp;
import com.doctor.yuyi.lzh_utils.toast;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//我的页面，关于我们
public class My_setting_about_Activity extends MyActivity {
    private XCRoundRectImageView roundRectImageView;
    private final Context con=My_setting_about_Activity.this;
    private String resStr;
    private TextView my_settings_aboutOurs_name;//宇医1。0
    private TextView my_settings_aboutOurs_pro;//简介
    private TextView my_setting_about_version;

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
                        Bean_MySetting_AboutUs info=okhttp.gson.fromJson(resStr,Bean_MySetting_AboutUs.class);
                        if ("0".equals(info.getCode())){
                          Bean_MySetting_AboutUs.ResultBean result= info.getResult();
                            my_settings_aboutOurs_name.setText(result.getTitle());
                            my_settings_aboutOurs_pro.setText(result.getContent());
                            my_setting_about_version.setText(result.getVersion());
                            Picasso.with(con).load(Ip.URL+result.getPicture()).error(R.mipmap.logo).into(roundRectImageView);
                        }
                        else {
                            Log.e("获取关于我们失败---","--------");
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
        setContentView(R.layout.activity_my_setting_about_);
        titleTextView= (TextView) findViewById(R.id.titleinclude_textview);
        setTitleText("关于宇医");
        roundRectImageView= (XCRoundRectImageView) findViewById(R.id.my_settings_aboutOurs_imageview);
        roundRectImageView.setRadios(12.5f);
        initView();
        getData();
    }

    private void initView() {
        my_settings_aboutOurs_name= (TextView) findViewById(R.id.my_settings_aboutOurs_name);
        my_settings_aboutOurs_pro= (TextView) findViewById(R.id.my_settings_aboutOurs_pro);
        my_setting_about_version= (TextView) findViewById(R.id.my_setting_about_version);
    }

    //{"result":{"createTimeString":"","updateTimeString":"","title":"宇医1.0","version":"当前版本号：1.0（wanyu2007）","content":"宇医APP，希望通过网上医疗的形式能够解决用户的一些医疗的基本需求，包括：测量监控自己及家人的健康数据；足不出户解决购药问题；提前预约专家挂号问题；在家与医生面对面交流，解决一些简单的问诊等","picture":"/static/image/avatar.jpeg","id":1},"code":"0"}
    public void getData() {
        Map<String,String> mp=new HashMap<>();
        okhttp.getCall(Ip.URL+Ip.interface_AboutUs,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("关于我们---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
