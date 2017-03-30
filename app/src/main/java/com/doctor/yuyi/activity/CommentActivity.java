package com.doctor.yuyi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.CommentAdapter;
import com.doctor.yuyi.myview.MyListView;

public class CommentActivity extends Activity implements View.OnClickListener {
    private ImageView mBack;
    private ListView mListview;
    private CommentAdapter mAdapter;
    private Button mSendBtn;
    private EditText mEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
    }

    public void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.comment_back);
        mBack.setOnClickListener(this);

        mListview = (ListView) findViewById(R.id.comment_listview);
        mAdapter = new CommentAdapter(CommentActivity.this);
        mListview.setAdapter(mAdapter);

        mEdit= (EditText) findViewById(R.id.edit);
        mSendBtn= (Button) findViewById(R.id.comment_btn);
        mSendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        }else if (id==mSendBtn.getId()){

        }
    }
}
