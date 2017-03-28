package com.doctor.yuyi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.My_Registration_Adapter;
import com.doctor.yuyi.lzh_utils.MyActivity;

//挂号接收
public class My_registration_Activity extends MyActivity {
    private ListView my_registration_listview;
    private My_Registration_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_registration_);
        initView();
    }

    private void initView() {
        titleTextView= (TextView) findViewById(R.id.titleinclude_textview);
        titleTextView.setText("挂号接收");
        my_registration_listview= (ListView) findViewById(R.id.my_registration_listview);
        my_registration_listview.setAdapter(new My_Registration_Adapter(this));
        my_registration_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(My_registration_Activity.this,My_registration_Info_Activity.class));
            }
        });
    }
}
