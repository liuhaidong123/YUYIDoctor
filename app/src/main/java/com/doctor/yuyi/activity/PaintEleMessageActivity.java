package com.doctor.yuyi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.LookElecAdapter;
import com.doctor.yuyi.bean.bean_MedicalRecordList;
import com.doctor.yuyi.myview.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanyu on 2017/8/31.
 */
//电子病例详情
public class PaintEleMessageActivity extends Activity{
    bean_MedicalRecordList.ResultBean beanUser;//用户自己的电子病例
//    bean_FamilyUserEle.ResultBean beanFamilyUser;//家人的电子病历
    private ImageView mBack;
    TextView eleMsg_text_creatTime,eleMsg_text_hospitalName,eleMsg_text_KS,eleMsg_text_DocName;//时间，医院，科室，医生
    MyGridView ele_gridView;//传入的图片
    LookElecAdapter adapter;
    TextView ele_message;//病例内容
    private int id;
    private String type;//0我的病历，1家人病历
    List<String> list;//存放图片的url（不加ip）
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_electronic_mess);
        //返回
        mBack = (ImageView) findViewById(R.id.elec_mess_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置textview文字过多时，可以垂直滚动
        ele_message = (TextView) findViewById(R.id.ele_message);
        ele_message.setMovementMethod(ScrollingMovementMethod.getInstance());
        initView();
        initIntentData();
    }

    private void initIntentData() {
        type = getIntent().getStringExtra("type");
        list=new ArrayList<>();
        switch (type){
            case "0"://自己的电子病例
                beanUser= (bean_MedicalRecordList.ResultBean) getIntent().getSerializableExtra("id");
                if (beanUser!=null){
                    eleMsg_text_creatTime.setText(beanUser.getCreateTimeString());
                    eleMsg_text_hospitalName.setText(beanUser.getHospitalName());
                    eleMsg_text_KS.setText(beanUser.getDepartmentName());
                    eleMsg_text_DocName.setText(beanUser.getPhysicianName());
                    ele_message.setText(beanUser.getMedicalrecord());
                    String url=beanUser.getPicture();
                    if (!"".equals(url)&&url!=null) {
                        String[] str = url.split(";");
                        adapter = new LookElecAdapter(this, str);
                        ele_gridView.setAdapter(adapter);
                    }
                }
                break;
            case "1"://家人的电子病例
//                beanFamilyUser= (bean_FamilyUserEle.ResultBean) getIntent().getSerializableExtra("id");
//                if (beanFamilyUser!=null){
//                    eleMsg_text_creatTime.setText(beanFamilyUser.getCreateTimeString());
//                    eleMsg_text_hospitalName.setText(beanFamilyUser.getHospitalName());
//                    eleMsg_text_KS.setText(beanFamilyUser.getDepartmentName());
//                    eleMsg_text_DocName.setText(beanFamilyUser.getPhysicianName());
//                    ele_message.setText(beanFamilyUser.getMedicalrecord());
//                    String url=beanFamilyUser.getPicture();
//                    String[]str=url.split(";");
//                    adapter=new LookElecAdapter(this,str);
//                    ele_gridView.setAdapter(adapter);
//                }
                break;
        }
    }

    private void initView() {
        eleMsg_text_creatTime= (TextView) findViewById(R.id.eleMsg_text_creatTime);
        eleMsg_text_hospitalName= (TextView) findViewById(R.id.eleMsg_text_hospitalName);
        eleMsg_text_KS= (TextView) findViewById(R.id.eleMsg_text_KS);
        eleMsg_text_DocName= (TextView) findViewById(R.id.eleMsg_text_DocName);
        ele_gridView= (MyGridView) findViewById(R.id.ele_gridView);
    }
}
