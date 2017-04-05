package com.doctor.yuyi.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.R;
import com.doctor.yuyi.RongCloudUtils.RongConnection;
import com.doctor.yuyi.RongCloudUtils.RongUserInfo;
import com.doctor.yuyi.fragment.AcademicFragment;
import com.doctor.yuyi.fragment.InformationFragment;
import com.doctor.yuyi.fragment.MyFragment;
import com.doctor.yuyi.fragment.PatientFragment;
import com.doctor.yuyi.mApplication.MyApplication;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,RongIM.UserInfoProvider{
    private LinearLayout mInformation_ll;
    private LinearLayout mAcademicCircle_ll;
    private LinearLayout mPatient_ll;
    private LinearLayout mMy_ll;

    private TextView mInformation_tv;
    private TextView mAcademicCircle_tv;
    private TextView mPatient_tv;
    private TextView mMy_tv;

    private ImageView mInformation_img;
    private ImageView mAcademicCircle_img;
    private ImageView mPatient_img;
    private ImageView mMy_img;
    private RelativeLayout mFragment_rl;
    private FragmentManager mFragmentManager;
    public final String informationTag = "informationFragment";
    public final String academicTag = "academicFragment";
    public final String patientTag = "patientFragment";
    public final String myTag = "myFragment";

    public final String pressColor = "#25f368";
    public final String noPressColor = "#666666";

    private long time = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showInformationFragment();
        RongIM.setUserInfoProvider(this,true);
        RongConnection.connRong(MainActivity.this, RongUserInfo.RongToken);
    }


    //初始化数据
    public void initView() {
        mFragment_rl = (RelativeLayout) findViewById(R.id.fragment_relative);
        mFragmentManager = getSupportFragmentManager();
        //底部按钮
        mInformation_ll = (LinearLayout) findViewById(R.id.information_btn_ll);
        mAcademicCircle_ll = (LinearLayout) findViewById(R.id.academic_btn_ll);
        mPatient_ll = (LinearLayout) findViewById(R.id.patient_btn_ll);
        mMy_ll = (LinearLayout) findViewById(R.id.my_btn_ll);
        mInformation_ll.setOnClickListener(this);
        mAcademicCircle_ll.setOnClickListener(this);
        mPatient_ll.setOnClickListener(this);
        mMy_ll.setOnClickListener(this);
        //底部文字
        mInformation_tv = (TextView) findViewById(R.id.information_tv);
        mAcademicCircle_tv = (TextView) findViewById(R.id.macademic_tv);
        mPatient_tv = (TextView) findViewById(R.id.patient_tv);
        mMy_tv = (TextView) findViewById(R.id.my_tv);
        //底部图片
        mAcademicCircle_img = (ImageView) findViewById(R.id.academic_btn_img);
        mInformation_img = (ImageView) findViewById(R.id.information_btn_img);
        mPatient_img = (ImageView) findViewById(R.id.patient_btn_img);
        mMy_img = (ImageView) findViewById(R.id.my_btn_img);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mInformation_ll.getId()) {
            showInformationFragment();
        } else if (id == mAcademicCircle_ll.getId()) {
            showAcademicFragment();
        } else if (id == mPatient_ll.getId()) {
            showPatientFragment();
        } else if (id == mMy_ll.getId()) {
            showMyFragment();
        }
    }

    //显示资讯
    public void showInformationFragment() {
        clickInformationBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment informationFragment = mFragmentManager.findFragmentByTag(informationTag);
        Fragment academicFragment = mFragmentManager.findFragmentByTag(academicTag);
        Fragment patientFragment = mFragmentManager.findFragmentByTag(patientTag);
        Fragment myFragment = mFragmentManager.findFragmentByTag(myTag);

        if (informationFragment != null) {//显示资讯
            fragmentTransaction.show(informationFragment);
        } else {
            InformationFragment informationF = new InformationFragment();
            fragmentTransaction.add(mFragment_rl.getId(), informationF, informationTag);
        }
        if (academicFragment != null) {//隐藏学术圈
            fragmentTransaction.hide(academicFragment);
        }
        if (patientFragment != null) {//隐藏患者
            fragmentTransaction.hide(patientFragment);
        }
        if (myFragment != null) {//隐藏我的
            fragmentTransaction.hide(myFragment);
        }
        fragmentTransaction.commit();
    }

    //显示学术圈页面
    public void showAcademicFragment() {
        clickAcademicBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment informationFragment = mFragmentManager.findFragmentByTag(informationTag);
        Fragment academicFragment = mFragmentManager.findFragmentByTag(academicTag);
        Fragment patientFragment = mFragmentManager.findFragmentByTag(patientTag);
        Fragment myFragment = mFragmentManager.findFragmentByTag(myTag);

        if (academicFragment != null) {//显示学术圈
            fragmentTransaction.show(academicFragment);
        } else {
            AcademicFragment academicF = new AcademicFragment();
            fragmentTransaction.add(mFragment_rl.getId(), academicF, academicTag);
        }
        if (informationFragment != null) {//隐藏资讯
            fragmentTransaction.hide(informationFragment);
        }
        if (patientFragment != null) {//隐藏患者
            fragmentTransaction.hide(patientFragment);
        }
        if (myFragment != null) {//隐藏我的
            fragmentTransaction.hide(myFragment);
        }
        fragmentTransaction.commit();

    }


    //显示患者页面
    public void showPatientFragment() {
        clickPatientBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment informationFragment = mFragmentManager.findFragmentByTag(informationTag);
        Fragment academicFragment = mFragmentManager.findFragmentByTag(academicTag);
        Fragment patientFragment = mFragmentManager.findFragmentByTag(patientTag);
        Fragment myFragment = mFragmentManager.findFragmentByTag(myTag);

        if (patientFragment != null) {//显示患者
            fragmentTransaction.show(patientFragment);
        } else {
            PatientFragment patientF = new PatientFragment();
            fragmentTransaction.add(mFragment_rl.getId(), patientF, patientTag);
        }
        if (informationFragment != null) {//隐藏资讯
            fragmentTransaction.hide(informationFragment);
        }
        if (academicFragment != null) {//隐藏学术圈
            fragmentTransaction.hide(academicFragment);
        }
        if (myFragment != null) {//隐藏我的
            fragmentTransaction.hide(myFragment);
        }
        fragmentTransaction.commit();

    }

    //显示我的页面
    public void showMyFragment() {
        clickMyBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment informationFragment = mFragmentManager.findFragmentByTag(informationTag);
        Fragment academicFragment = mFragmentManager.findFragmentByTag(academicTag);
        Fragment patientFragment = mFragmentManager.findFragmentByTag(patientTag);
        Fragment myFragment = mFragmentManager.findFragmentByTag(myTag);

        if (myFragment != null) {//显示我的
            fragmentTransaction.show(myFragment);
        } else {

            MyFragment myF = new MyFragment();
            fragmentTransaction.add(mFragment_rl.getId(), myF, myTag);
        }
        if (informationFragment != null) {//隐藏资讯
            fragmentTransaction.hide(informationFragment);
        }
        if (academicFragment != null) {//隐藏学术
            fragmentTransaction.hide(academicFragment);
        }
        if (patientFragment != null) {//隐藏患者
            fragmentTransaction.hide(patientFragment);
        }
        fragmentTransaction.commit();

    }


    //点击资讯文字图片变化
    public void clickInformationBtnChangeTvColor() {
        mInformation_tv.setTextColor(Color.parseColor(pressColor));
        mInformation_img.setImageResource(R.mipmap.information_selected);

        mAcademicCircle_tv.setTextColor(Color.parseColor(noPressColor));
        mAcademicCircle_img.setImageResource(R.mipmap.academiccircles);

        mPatient_tv.setTextColor(Color.parseColor(noPressColor));
        mPatient_img.setImageResource(R.mipmap.patient);

        mMy_tv.setTextColor(Color.parseColor(noPressColor));
        mMy_img.setImageResource(R.mipmap.my);

    }

    //点击学术圈文字图片变化
    public void clickAcademicBtnChangeTvColor() {
        mInformation_tv.setTextColor(Color.parseColor(noPressColor));
        mInformation_img.setImageResource(R.mipmap.information);

        mAcademicCircle_tv.setTextColor(Color.parseColor(pressColor));
        mAcademicCircle_img.setImageResource(R.mipmap.academiccircles_selected);

        mPatient_tv.setTextColor(Color.parseColor(noPressColor));
        mPatient_img.setImageResource(R.mipmap.patient);

        mMy_tv.setTextColor(Color.parseColor(noPressColor));
        mMy_img.setImageResource(R.mipmap.my);
    }

    //点击患者文字图片变化
    public void clickPatientBtnChangeTvColor() {
        mInformation_tv.setTextColor(Color.parseColor(noPressColor));
        mInformation_img.setImageResource(R.mipmap.information);

        mAcademicCircle_tv.setTextColor(Color.parseColor(noPressColor));
        mAcademicCircle_img.setImageResource(R.mipmap.academiccircles);

        mPatient_tv.setTextColor(Color.parseColor(pressColor));
        mPatient_img.setImageResource(R.mipmap.patient_selected);

        mMy_tv.setTextColor(Color.parseColor(noPressColor));
        mMy_img.setImageResource(R.mipmap.my);
    }

    //点击我的文字图片变化
    public void clickMyBtnChangeTvColor() {
        mInformation_tv.setTextColor(Color.parseColor(noPressColor));
        mInformation_img.setImageResource(R.mipmap.information);

        mAcademicCircle_tv.setTextColor(Color.parseColor(noPressColor));
        mAcademicCircle_img.setImageResource(R.mipmap.academiccircles);

        mPatient_tv.setTextColor(Color.parseColor(noPressColor));
        mPatient_img.setImageResource(R.mipmap.patient);

        mMy_tv.setTextColor(Color.parseColor(pressColor));
        mMy_img.setImageResource(R.mipmap.my_selected);

    }

    @Override
    public void onBackPressed() {
        if (time > 0) {
            if (System.currentTimeMillis() - time < 2000) {
                super.onBackPressed();
            } else {
                time = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }

        } else {
            time = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public UserInfo getUserInfo(String s) {
        for (int i=0;i< MyApplication.li.size();i++){
            if (s.equals(MyApplication.li.get(i).getUserId())){
                return MyApplication.li.get(i);
            }
        }
        return null;
    }
}
