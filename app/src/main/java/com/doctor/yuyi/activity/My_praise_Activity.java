package com.doctor.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.My_forumPosts_Adapter;
import com.doctor.yuyi.lzh_utils.MyActivity;

public class My_praise_Activity extends MyActivity {
    private ListView my_praise_listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_praise_);
        initView();
    }

    private void initView() {
        titleTextView= (TextView) findViewById(R.id.titleinclude_textview);
        titleTextView.setText("我的点赞");
        my_praise_listview= (ListView) findViewById(R.id.my_praise_listview);
        my_praise_listview.setAdapter(new My_forumPosts_Adapter(null,this));
    }
}
