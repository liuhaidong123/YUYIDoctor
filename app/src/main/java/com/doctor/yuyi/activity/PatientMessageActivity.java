package com.doctor.yuyi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.MyUtils.MyDialog;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.ElectronicMessListViewAdapter;
import com.doctor.yuyi.bean.PatientData.BloodpressureList;
import com.doctor.yuyi.bean.PatientData.Root;
import com.doctor.yuyi.bean.PatientData.TemperatureList;
import com.doctor.yuyi.bean.bean_MedicalRecordList;
import com.doctor.yuyi.lzh_utils.okhttp;
import com.doctor.yuyi.myview.BloodView;
import com.doctor.yuyi.myview.MyListView;
import com.doctor.yuyi.myview.RoundImageView;
import com.doctor.yuyi.myview.TemView;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientMessageActivity extends AppCompatActivity implements View.OnClickListener {
    MyListView myListView;//电子病例的listView
    ElectronicMessListViewAdapter adapter;//适配器
    bean_MedicalRecordList bean;//电子病例的数据源

    private ImageView mBack;

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
    private SimpleDateFormat simpleDateFormat;
    //血压提示
    private ImageView mPromptImg;
    private TextView mPromptTv;
    private TextView mHeightBloodTv;
    private TextView mLowBloodTv;
//    体温提示
    private ImageView mTemPromptImg;
    private TextView mTemPromptTv;
    private TextView mTem;
    public final String mGayColor = "#6a6a6a";
    private String redColor = "#ec2e2e";

// 电子病历
    private RoundImageView mHead_img;
    private TextView mName,mAge;
    ImageView imageSex;//性别
    private HttpTools mHttptools;
    String resStr;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {//患者详情中的患者数据
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    XdateNum.clear();
                    heightBloodData.clear();
                    lowBloodData.clear();
                    XTemdateNum.clear();
                    temData.clear();
                    MyDialog.stopDia();
                    Root root = (Root) o;

                    Picasso.with(PatientMessageActivity.this).load(UrlTools.BASE+root.getResult().getAvatar()).error(R.mipmap.error_small).into(mHead_img);
                    mName.setText(root.getResult().getTrueName());
                    if (root.getResult().getGender()==null){
                        Log.e("=====空的数据===","--------------");
                    }
                    if (root.getResult().getGender()!=null){
                        if (root.getResult().getGender()==0){//0：女
                            imageSex.setSelected(false);
                        }
                        else if (root.getResult().getGender()==1){
                            imageSex.setSelected(true);
                        }
                    }
                    else {
                        imageSex.setSelected(false);//默认女
                    }

                    if (root.getResult().getAge()!=null){
                        mAge.setText("年龄:"+root.getResult().getAge()+"");
                    }else {
                        mAge.setText("年龄:");
                    }

                    List<BloodpressureList> bloodlist = root.getResult().getBloodpressureList();
                    List<TemperatureList> temlist = root.getResult().getTemperatureList();
                    int month = 0;
                    int day = 0;
                    //血压
                    if (bloodlist.size() != 0) {
                        for (int i = 0; i < bloodlist.size(); i++) {
                            try {
                                Date date = simpleDateFormat.parse(bloodlist.get(i).getCreateTimeString());
                                month = date.getMonth() + 1;
                                day = date.getDate();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String date = month + "月" + day + "日";
                            XdateNum.add(date);
                            heightBloodData.add(bloodlist.get(i).getSystolic());//高
                            lowBloodData.add(bloodlist.get(i).getDiastolic());
                        }

                    }
                    //填补血压日期
                    if (XdateNum.size() != 7) {
                        Calendar calendarBlood = Calendar.getInstance();
                        int dayNum = 7 - XdateNum.size();
                        if (XdateNum.size() == 0) {
                            for (int i = 0; i < dayNum; i++) {
                                int month2 = calendarBlood.get(Calendar.MONTH) + 1;
                                int day2 = calendarBlood.get(Calendar.DAY_OF_MONTH);
                                String date2 = month2 + "月" + day2 + "日";
                                XdateNum.add(date2);
                                calendarBlood.add(Calendar.DAY_OF_MONTH, 1);
                            }
                        } else {
                            for (int i = 0; i < dayNum; i++) {
                                calendarBlood.add(Calendar.DAY_OF_MONTH, 1);
                                int month2 = calendarBlood.get(Calendar.MONTH) + 1;
                                int day2 = calendarBlood.get(Calendar.DAY_OF_MONTH);
                                String date2 = month2 + "月" + day2 + "日";
                                XdateNum.add(date2);
                            }
                        }

                    }

                    // 体温
                    if (temlist.size() != 0) {
                        for (int i = 0; i < temlist.size(); i++) {
                            try {
                                Date date = simpleDateFormat.parse(temlist.get(i).getCreateTimeString());
                                month = date.getMonth() + 1;
                                day = date.getDate();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String date = month + "月" + day + "日";
                            XTemdateNum.add(date);
                            temData.add(temlist.get(i).getTemperaturet());//体温
                        }
                    }

                    //填补体温日期
                    if (XTemdateNum.size() != 7) {
                        Calendar calendarTem = Calendar.getInstance();
                        int dayNum = 7 - XTemdateNum.size();
                        //没有数据时，从当前日期开始
                        if (XTemdateNum.size() == 0) {
                            for (int i = 0; i < dayNum; i++) {
                                int month2 = calendarTem.get(Calendar.MONTH) + 1;
                                int day2 = calendarTem.get(Calendar.DAY_OF_MONTH);
                                String date2 = month2 + "月" + day2 + "日";
                                XTemdateNum.add(date2);
                                calendarTem.add(Calendar.DAY_OF_MONTH, 1);
                            }
                            //有数据时，从当前日期的下一天开始
                        } else {
                            for (int i = 0; i < dayNum; i++) {
                                calendarTem.add(Calendar.DAY_OF_MONTH, 1);
                                int month2 = calendarTem.get(Calendar.MONTH) + 1;
                                int day2 = calendarTem.get(Calendar.DAY_OF_MONTH);
                                String date2 = month2 + "月" + day2 + "日";
                                XTemdateNum.add(date2);
                            }
                        }

                    }

                    mBloodview.setInfo(YbloodNum, XdateNum, heightBloodData, lowBloodData);
                    mBloodview.invalidate();
                    mTemview.setTemInfo(YTemData, XTemdateNum, temData);
                    mTemview.invalidate();


                    //判断数据是否正常，设置文字图片提示
                    if (bloodlist.size() != 0 && temlist.size() != 0) {
                        checkBlood(bloodlist.get(bloodlist.size() - 1).getSystolic(), bloodlist.get(bloodlist.size() - 1).getDiastolic(), temlist.get(temlist.size() - 1).getTemperaturet());
                    } else if (bloodlist.size() == 0 && temlist.size() != 0) {
                        checkBlood(0, 0, temlist.get(temlist.size() - 1).getTemperaturet());
                    } else if (bloodlist.size() != 0 && temlist.size() == 0) {
                        checkBlood(bloodlist.get(bloodlist.size() - 1).getSystolic(), bloodlist.get(bloodlist.size() - 1).getDiastolic(), 0);
                    } else {
                        checkBlood(0, 0, 0);
                    }
                }
            } else if (msg.what == 110) {
                MyDialog.stopDia();
            }
            else if (msg.what == 11) {//患者详情中的电子病历
                try{
                   bean=okhttp.gson.fromJson(resStr,bean_MedicalRecordList.class);
                    if (bean!=null){
                        if ("0".equals(bean.getCode())){
                            List<bean_MedicalRecordList.ResultBean>li=bean.getResult();
                            if (li!=null&&li.size()>0){
                                adapter=new ElectronicMessListViewAdapter(PatientMessageActivity.this,li);
                                myListView.setAdapter(adapter);
                            }

                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    myListView.setEmty("没有查询到数据！");
                }
            }else if (msg.what==111){
                MyDialog.stopDia();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_message);
        initView();
    }

    public void initView() {
        myListView= (MyListView) findViewById(R.id.myListView);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bean!=null&&bean.getResult()!=null&&bean.getResult().size()>0){
                    Intent intent=new Intent(PatientMessageActivity.this,PaintEleMessageActivity.class);
                    intent.putExtra("type","0");
                    intent.putExtra("id",bean.getResult().get(position));
                    startActivity(intent);
                }
            }
        });

        mBack= (ImageView) findViewById(R.id.equip_back);
        mBack.setOnClickListener(this);

        mHttptools = HttpTools.getHttpToolsInstance();
        MyDialog.showDialog(this);
//        mHttptools.getPatientEle(mHandler, UserInfo.UserToken, getIntent().getLongExtra("humeuserId",-1L));
        Map<String,String> m=new HashMap<>();
        m.put("id",getIntent().getLongExtra("humeuserId",-1L)+"");
        okhttp.getCall(Ip.URL+Ip.interface_PaintEleList,m,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取个人病例列表",resStr);
                mHandler.sendEmptyMessage(11);
            }
        });
        mHttptools.getPatientData(mHandler, UserInfo.UserToken, getIntent().getLongExtra("humeuserId",-1L));
        //时间
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mEle_btn = (TextView) findViewById(R.id.patient_ele_btn);//电子病历按钮
        mEle_btn.setOnClickListener(this);
        mEle_btn.setSelected(true);
        mPatient_btn = (TextView) findViewById(R.id.patient_messdata_btn);//患者数据按钮
        mPatient_btn.setOnClickListener(this);
        mPatient_btn.setSelected(false);

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

        mBloodview = (BloodView) findViewById(R.id.relative_blood);
        mTemview = (TemView) findViewById(R.id.relative_tem);
        mBloodview.setInfo(YbloodNum, XdateNum, heightBloodData, lowBloodData);
        mTemview.setTemInfo(YTemData, XTemdateNum, temData);

        mTemview.invalidate();
        mBloodview.invalidate();
        //电子病历
        mHead_img= (RoundImageView) findViewById(R.id.patient_head_img);
        mName= (TextView) findViewById(R.id.patient_name);
        imageSex= (ImageView) findViewById(R.id.imageSex);
        mAge= (TextView) findViewById(R.id.patient_age);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mEle_btn.getId()) {//电子病历
            showEle();
            mPatient_btn.setSelected(false);
            mEle_btn.setSelected(true);
        } else if (id == mPatient_btn.getId()) {//患者数据
            showPatientData();
            mPatient_btn.setSelected(true);
            mEle_btn.setSelected(false);
        } else if (id == mBlood_relative_btn.getId()) {//点击血压
            showBloodRL();
        } else if (id == mTem_relative_btn.getId()) {//点击体温
            showTemRL();
        } else if (id == mBack.getId()) {//返回
          finish();
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
        mEle_relative.setVisibility(View.VISIBLE);
        mPatient_relative.setVisibility(View.GONE);
    }

    /**
     * 点击显示患者数据
     */
    public void showPatientData() {
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


    /**
     * 判断血压,体温设置提示
     * height 高压
     * low 低压
     * tem 体温
     */
    public void checkBlood(int height, int low, float tem) {
        mPromptImg = (ImageView) findViewById(R.id.normal_btn_img);
        mPromptTv = (TextView) findViewById(R.id.normal_tv);
        mHeightBloodTv = (TextView) findViewById(R.id.height_blood_num_tv);
        mLowBloodTv = (TextView) findViewById(R.id.low_blood_num_tv);

        mTemPromptImg = (ImageView) findViewById(R.id.tem_btn_img);
        mTemPromptTv = (TextView) findViewById(R.id.tem_normal_tv);
        mTem = (TextView) findViewById(R.id.tem_num_tv);

        //血压
        if (height == 0 && low == 0) {
            mPromptImg.setImageResource(R.mipmap.normal);
            mPromptTv.setText("待测");
            mPromptTv.setTextColor(Color.parseColor(mGayColor));

        } else {
            if (height > 139) {
                mPromptImg.setImageResource(R.mipmap.height);
                mPromptTv.setText("偏高");
                mPromptTv.setTextColor(Color.parseColor(redColor));

            } else if (height < 90) {
                mPromptImg.setImageResource(R.mipmap.low);
                mPromptTv.setText("偏低");
                mPromptTv.setTextColor(Color.parseColor(redColor));
            } else {
                mPromptImg.setImageResource(R.mipmap.normal);
                mPromptTv.setText("正常");
                mPromptTv.setTextColor(Color.parseColor(mGayColor));
            }
        }

        //体温
        if (tem == 0) {
            mTemPromptImg.setImageResource(R.mipmap.normal);
            mTemPromptTv.setText("待测");
            mTemPromptTv.setTextColor(Color.parseColor(mGayColor));
        } else {
            if (tem > 37.5) {
                mTemPromptImg.setImageResource(R.mipmap.height);
                mTemPromptTv.setText("偏高");
                mTemPromptTv.setTextColor(Color.parseColor(redColor));
            } else if (tem < 36) {
                mTemPromptImg.setImageResource(R.mipmap.low);
                mTemPromptTv.setText("偏低");
                mTemPromptTv.setTextColor(Color.parseColor(redColor));
            } else {
                mTemPromptImg.setImageResource(R.mipmap.normal);
                mTemPromptTv.setText("正常");
                mTemPromptTv.setTextColor(Color.parseColor(mGayColor));
            }
        }
        mHeightBloodTv.setText(height + "");
        mLowBloodTv.setText(low + "");
        mTem.setText(tem + "°C");
    }
}
