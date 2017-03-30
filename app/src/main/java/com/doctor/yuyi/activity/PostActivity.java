package com.doctor.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.myview.InformationListView;

public class PostActivity extends AppCompatActivity {

    private EditText mEdit_title;
    private EditText mEdit_content;
    private InformationListView mImgListView;
    private TextView mPost_btn;//发布
    private ImageView mAdd_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
    }
}
