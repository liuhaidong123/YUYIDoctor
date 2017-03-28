package com.doctor.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.lzh_utils.MyActivity;

//挂号信息详情
public class My_registration_Info_Activity extends MyActivity {
    private TextView my_registrationInfo_textV_bianhao,my_registrationInfo_textV_name,my_registrationInfo_textV_sex;//编号,名字，性别
    private TextView my_registrationInfo_textV_age,my_registrationInfo_textV_tel,my_registrationInfo_textV_address;//年龄，电话，地址
    private TextView my_registrationInfo_textV_keshi,my_registrationInfo_textV_docName,my_registrationInfo_docAddress;//科室名称，医生名称，科室地址
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_registration__info_);
        initView();
    }

    private void initView() {
        titleTextView= (TextView) findViewById(R.id.titleinclude_textview);
        titleTextView.setText("挂号单");

        my_registrationInfo_textV_bianhao= (TextView) findViewById(R.id.my_registrationInfo_textV_bianhao);
        my_registrationInfo_textV_name= (TextView) findViewById(R.id.my_registrationInfo_textV_name);
        my_registrationInfo_textV_sex= (TextView) findViewById(R.id.my_registrationInfo_textV_sex);

        my_registrationInfo_textV_age= (TextView) findViewById(R.id.my_registrationInfo_textV_age);
        my_registrationInfo_textV_tel= (TextView) findViewById(R.id.my_registrationInfo_textV_tel);
        my_registrationInfo_textV_address= (TextView) findViewById(R.id.my_registrationInfo_textV_address);


        my_registrationInfo_textV_keshi= (TextView) findViewById(R.id.my_registrationInfo_textV_keshi);
        my_registrationInfo_textV_docName= (TextView) findViewById(R.id.my_registrationInfo_textV_docName);
        my_registrationInfo_docAddress= (TextView) findViewById(R.id.my_registrationInfo_docAddress);
    }
}
