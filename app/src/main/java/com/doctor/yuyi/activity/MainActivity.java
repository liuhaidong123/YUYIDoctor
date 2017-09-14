package com.doctor.yuyi.activity;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.MyUtils.SharedPreferencesUtils;
import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.RongCloudUtils.RongConnection;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.bean.BeanPriRong;
import com.doctor.yuyi.broadcast.BroadCastYUYI;
import com.doctor.yuyi.fragment.AcademicFragment;
import com.doctor.yuyi.fragment.ErrorFragment;
import com.doctor.yuyi.fragment.InformationFragment;
import com.doctor.yuyi.fragment.MyFragment;
import com.doctor.yuyi.fragment.PatientFragment;
import com.doctor.yuyi.lzh_utils.okhttp;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout mtitle_rl;
    private TextView mtitle_tv;

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
    public final String errorTag = "errorFragment";

    public final String pressColor = "#1ebeec";
    public final String noPressColor = "#b2b2b2";

    private long time = 0;

    private String resStr;
    private Handler han=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    break;
                case 1:
                    try{
                        BeanPriRong rong=okhttp.gson.fromJson(resStr,BeanPriRong.class);
                       if (rong!=null){
                           if (rong.getCode()==0){
                               if (rong.isPermissionInfo()==true){
                                   String name="医生";
                                   String uri="http://img5.imgtn.bdimg.com/it/u=1482475142,4125104797&fm=23&gp=0.jpg";
                                   if (rong.getTrueName()!=null&&!"".equals(rong.getTrueName())){
                                    name=rong.getTrueName();
                                   }
                                   if (!"".equals(rong.getAvatar())&&!TextUtils.isEmpty(rong.getAvatar())){
                                       uri=rong.getAvatar();
                                   }
                                   io.rong.imlib.model.UserInfo info=new io.rong.imlib.model.UserInfo(rong.getId()+"",name,Uri.parse(uri));
                                   com.doctor.yuyi.User.UserInfo.RongToken=rong.getToken();
                                   RongConnection.connRong(MainActivity.this, com.doctor.yuyi.User.UserInfo.RongToken,info);
                               }
                               else {
                                   Log.e("当前医生无法接收到咨询xinxi ","mainActivity:医院未授予当前医生接收视频到权限");
                               }
                           }
                           else if (rong.getCode()==-1){
                               Log.e("当前用户信息无法查询到","mainActivity:当前用户没有在任何医院注册，请通知去注册");
                           }
                       }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        CheckPri();//检查是否有权限去接听视频，有权限的链接融云
        showInformationFragment();
    }
    //检查当前用户是否有权限接受视频，语音，聊天
    private void CheckPri() {
        Map<String,String>mp=new HashMap<>();
        mp.put("telephone",UserInfo.UserName);
        okhttp.getCall(Ip.URL+Ip.interface_CheckPri,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                han.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("聊天权限检查---",resStr);
                han.sendEmptyMessage(1);
            }
        });
    }


    //初始化数据
    public void initView() {
        //没有网络时显示每个fragment的标头
        mtitle_rl = (RelativeLayout) findViewById(R.id.equip_title);
        mtitle_tv = (TextView) findViewById(R.id.title_all);

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
        InformationFragment informationFragment = (InformationFragment) mFragmentManager.findFragmentByTag(informationTag);
        AcademicFragment academicFragment = (AcademicFragment) mFragmentManager.findFragmentByTag(academicTag);
        PatientFragment patientFragment = (PatientFragment) mFragmentManager.findFragmentByTag(patientTag);
        MyFragment myFragment = (MyFragment) mFragmentManager.findFragmentByTag(myTag);
        ErrorFragment errorFragment = (ErrorFragment) mFragmentManager.findFragmentByTag(errorTag);
            if (isNetworkConnected(this)) {
            mtitle_rl.setVisibility(View.GONE);
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
            if (errorFragment != null) {//隐藏错误
                fragmentTransaction.hide(errorFragment);
            }

        } else {
            if (informationFragment != null) {//隐藏资讯
                fragmentTransaction.hide(informationFragment);
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
            if (errorFragment != null) {//显示错误
                fragmentTransaction.show(errorFragment);
                mtitle_rl.setVisibility(View.VISIBLE);
                mtitle_tv.setText("资讯");
            } else {
                ErrorFragment errorFragment1 = new ErrorFragment();
                fragmentTransaction.add(mFragment_rl.getId(), errorFragment1, errorTag);
                mtitle_rl.setVisibility(View.VISIBLE);
                mtitle_tv.setText("资讯");
            }
        }

        fragmentTransaction.commit();
    }

    //显示学术圈页面
    public void showAcademicFragment() {
        clickAcademicBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        InformationFragment informationFragment = (InformationFragment) mFragmentManager.findFragmentByTag(informationTag);
        AcademicFragment academicFragment = (AcademicFragment) mFragmentManager.findFragmentByTag(academicTag);
        PatientFragment patientFragment = (PatientFragment) mFragmentManager.findFragmentByTag(patientTag);
        MyFragment myFragment = (MyFragment) mFragmentManager.findFragmentByTag(myTag);
        ErrorFragment errorFragment = (ErrorFragment) mFragmentManager.findFragmentByTag(errorTag);
        if (isNetworkConnected(this)) {
            mtitle_rl.setVisibility(View.GONE);
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
            if (errorFragment != null) {//隐藏错误
                fragmentTransaction.hide(errorFragment);
            }
        } else {
            if (academicFragment != null) {//隐藏学术圈
                fragmentTransaction.hide(academicFragment);
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
            if (errorFragment != null) {//显示错误
                fragmentTransaction.show(errorFragment);
                mtitle_rl.setVisibility(View.VISIBLE);
                mtitle_tv.setText("学术圈");
            } else {
                ErrorFragment errorFragment1 = new ErrorFragment();
                fragmentTransaction.add(mFragment_rl.getId(), errorFragment1, errorTag);
                mtitle_rl.setVisibility(View.VISIBLE);
                mtitle_tv.setText("学术圈");
            }
        }

        fragmentTransaction.commit();

    }


    //显示患者页面
    public void showPatientFragment() {
        clickPatientBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        InformationFragment informationFragment = (InformationFragment) mFragmentManager.findFragmentByTag(informationTag);
        AcademicFragment academicFragment = (AcademicFragment) mFragmentManager.findFragmentByTag(academicTag);
        PatientFragment patientFragment = (PatientFragment) mFragmentManager.findFragmentByTag(patientTag);
        MyFragment myFragment = (MyFragment) mFragmentManager.findFragmentByTag(myTag);
        ErrorFragment errorFragment = (ErrorFragment) mFragmentManager.findFragmentByTag(errorTag);
        if (isNetworkConnected(this)) {
            mtitle_rl.setVisibility(View.GONE);
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
            if (errorFragment != null) {//隐藏错误
                fragmentTransaction.hide(errorFragment);
            }

        } else {
            if (academicFragment != null) {//隐藏学术圈
                fragmentTransaction.hide(academicFragment);
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
            if (errorFragment != null) {//显示错误
                fragmentTransaction.show(errorFragment);
                mtitle_rl.setVisibility(View.VISIBLE);
                mtitle_tv.setText("患者");
            } else {
                ErrorFragment errorFragment1 = new ErrorFragment();
                fragmentTransaction.add(mFragment_rl.getId(), errorFragment1, errorTag);
                mtitle_rl.setVisibility(View.VISIBLE);
                mtitle_tv.setText("患者");
            }
        }
        fragmentTransaction.commit();

    }

    //显示我的页面
    public void showMyFragment() {
        clickMyBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        InformationFragment informationFragment = (InformationFragment) mFragmentManager.findFragmentByTag(informationTag);
        AcademicFragment academicFragment = (AcademicFragment) mFragmentManager.findFragmentByTag(academicTag);
        PatientFragment patientFragment = (PatientFragment) mFragmentManager.findFragmentByTag(patientTag);
        MyFragment myFragment = (MyFragment) mFragmentManager.findFragmentByTag(myTag);
        ErrorFragment errorFragment = (ErrorFragment) mFragmentManager.findFragmentByTag(errorTag);
        mtitle_rl.setVisibility(View.GONE);
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
        if (errorFragment != null) {//隐藏错误
            fragmentTransaction.hide(errorFragment);
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
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    /**
     * 判断有没有网
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
