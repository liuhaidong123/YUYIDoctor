package com.doctor.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.My_paintDataList_Adapter;
import com.doctor.yuyi.lzh_utils.MyActivity;

import java.util.ArrayList;
import java.util.Map;
//患者数据
public class My_patientDataList_Activity extends MyActivity {
    private ListView my_patientList_listview;
    private My_paintDataList_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patient_data_list_);
        initView();
    }

    private void initView() {
        my_patientList_listview= (ListView) findViewById(R.id.my_patientList_listview);
        adapter=new My_paintDataList_Adapter(this,null);
        my_patientList_listview.setAdapter(adapter);
    }


    //搜索按钮的点击事件
    public void Search(View view) {
        if (view!=null){

        }
    }
}
