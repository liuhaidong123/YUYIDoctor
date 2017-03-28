package com.doctor.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.My_forumPosts_Adapter;
import com.doctor.yuyi.lzh_utils.MyActivity;
//评论的view
public class My_forumPosts_Activity extends MyActivity {
    private ListView my_forum_posts_listview;
    private My_forumPosts_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_forum_posts_);
        initView();
    }

    private void initView() {
        my_forum_posts_listview= (ListView) findViewById(R.id.my_forum_posts_listview);
        my_forum_posts_listview.setAdapter(new My_forumPosts_Adapter(null,this));
    }
}
