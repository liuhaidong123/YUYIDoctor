package com.doctor.yuyi.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.myview.BloodView;
import com.doctor.yuyi.myview.TemView;

import java.util.ArrayList;

public class PatientMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mEle_btn;//电子病历按钮
    private TextView mPatient_btn;//患者数据按钮

    private RelativeLayout mEle_relative;//电子病历布局
    private RelativeLayout mPatient_relative;//患者数据布局

    private RelativeLayout mBlood_relative_btn;//血压按钮
    private RelativeLayout mTem_relative_btn;//体温按钮
    private ImageView mBlood_img;
    private ImageView mTem_img;

    private RelativeLayout mBlood_relative;//血压布局
    private RelativeLayout mTem_relative;//体温布局

    private BloodView mBloodview;
    private TemView mTemview;
    private ArrayList<Integer> YbloodNum = new ArrayList<>();//y轴血压数据
    private ArrayList<String> XdateNum = new ArrayList<>();//x轴日期数据
    private ArrayList<Integer> heightBloodData = new ArrayList<>();  //血压高压数据
    private ArrayList<Integer> lowBloodData = new ArrayList<>();//血压低压数据

    private ArrayList<Integer> YTemData = new ArrayList<>();//Y轴温度
    private ArrayList<String> XTemdateNum = new ArrayList<>(); //x轴日期数据
    private ArrayList<Float> temData = new ArrayList<>(); //体温

    private RelativeLayout mBlood_Group;
    private RelativeLayout mTem_Group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_message);
        initView();
    }

    public void initView() {
        mEle_btn = (TextView) findViewById(R.id.patient_ele_btn);//电子病历按钮
        mEle_btn.setOnClickListener(this);
        mPatient_btn = (TextView) findViewById(R.id.patient_messdata_btn);//患者数据按钮
        mPatient_btn.setOnClickListener(this);

        mEle_relative = (RelativeLayout) findViewById(R.id.patient_ele_rl);//显示电子病历布局
        mPatient_relative = (RelativeLayout) findViewById(R.id.patient_data_rl);//显示患者数据布局

        mBlood_relative_btn = (RelativeLayout) findViewById(R.id.patient_blood_rl);//血压按钮
        mBlood_relative_btn.setOnClickListener(this);
        mTem_relative_btn = (RelativeLayout) findViewById(R.id.patient_tem_rl);//体温按钮
        mTem_relative_btn.setOnClickListener(this);
        mBlood_img = (ImageView) findViewById(R.id.blood_img);
        mTem_img = (ImageView) findViewById(R.id.tem_img);

        mBlood_relative = (RelativeLayout) findViewById(R.id.blood_relative);//血压布局
        mTem_relative = (RelativeLayout) findViewById(R.id.tem_relative);//血压布局
        initBloodData();
        initTemData();
        //绘制折线图
//        mBloodview=new BloodView(this);
//        mTemview=new TemView(this);
//        mBlood_Group= (RelativeLayout) findViewById(R.id.relative_blood);
//        mTem_Group= (RelativeLayout) findViewById(R.id.relative_tem);
//        mBlood_Group.addView(mBloodview);
//        mTem_Group.addView(mTemview);

        mBloodview= (BloodView) findViewById(R.id.relative_blood);
        mTemview=(TemView) findViewById(R.id.relative_tem);
        mBloodview.setInfo(YbloodNum,XdateNum,heightBloodData,lowBloodData);
        mTemview.setTemInfo(YTemData,XTemdateNum,temData);

        mTemview.invalidate();
        mBloodview.invalidate();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mEle_btn.getId()) {//电子病历
            showEle();
        } else if (id == mPatient_btn.getId()) {//患者数据
            showPatientData();
        } else if (id == mBlood_relative_btn.getId()) {//点击血压
            showBloodRL();
        } else if (id == mTem_relative_btn.getId()) {//点击体温
            showTemRL();
        }
    }

    /**
     * 初始化血压折线图数据
     */
    public void initBloodData() {
        //y轴血压数据
        for (int i = 40; i <= 180; i += 20) {
            YbloodNum.add(i);
        }
    }

    /**
     * 初始化体温折线图数据
     */

    public void initTemData() {
        for (int i = 35; i < 43; i++) {
            YTemData.add(i);
        }

    }
    /**
     * 点击显示电子病历
     */
    public void showEle() {
        mEle_btn.setBackgroundResource(R.color.color_username);
        mEle_btn.setTextColor(ContextCompat.getColor(this, R.color.color_white));
        mPatient_btn.setBackgroundResource(R.color.color_white);
        mPatient_btn.setTextColor(ContextCompat.getColor(this, R.color.color_normal));
        mEle_relative.setVisibility(View.VISIBLE);
        mPatient_relative.setVisibility(View.GONE);
    }

    /**
     * 点击显示患者数据
     */
    public void showPatientData() {
        mEle_btn.setBackgroundResource(R.color.color_white);
        mEle_btn.setTextColor(ContextCompat.getColor(this, R.color.color_normal));
        mPatient_btn.setBackgroundResource(R.color.color_username);
        mPatient_btn.setTextColor(ContextCompat.getColor(this, R.color.color_white));
        mEle_relative.setVisibility(View.GONE);
        mPatient_relative.setVisibility(View.VISIBLE);

    }

    /**
     * 点击显示血压布局
     */
    public void showBloodRL() {
        mBlood_img.setImageResource(R.mipmap.select_data);
        mTem_img.setImageResource(R.mipmap.select_no_data);
        mBlood_relative.setVisibility(View.VISIBLE);
        mTem_relative.setVisibility(View.GONE);
    }

    /**
     * 点击显示体温布局
     */
    public void showTemRL() {
        mBlood_img.setImageResource(R.mipmap.select_no_data);
        mTem_img.setImageResource(R.mipmap.select_data);
        mBlood_relative.setVisibility(View.GONE);
        mTem_relative.setVisibility(View.VISIBLE);
    }
}
