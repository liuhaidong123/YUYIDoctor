package com.doctor.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.CardMessageCommentAdapter;
import com.doctor.yuyi.adapter.CardMessageImgAdapter;
import com.doctor.yuyi.myview.InformationListView;

public class CardMessageActivity extends AppCompatActivity {

    private InformationListView mImgListView,mCommentListView;
    private CardMessageImgAdapter mImgAdapter;
    private CardMessageCommentAdapter mCommentAdapter;
    private RelativeLayout mScrollRl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_message);
        initView();
    }

    private void initView() {
        //帖子详情页面的图片listview
        mImgListView= (InformationListView) findViewById(R.id.card_img_listview);
        mImgAdapter=new CardMessageImgAdapter(this);
        mImgListView.setAdapter(mImgAdapter);
        //帖子详情页面的评论listview
        mCommentListView=(InformationListView) findViewById(R.id.card_comment_listview);
        mCommentAdapter=new CardMessageCommentAdapter(this);
        mCommentListView.setAdapter(mCommentAdapter);
        //将scrollView定位到顶部
        mScrollRl= (RelativeLayout) findViewById(R.id.scroll_relative);
        mScrollRl.setFocusable(true);
        mScrollRl.setFocusableInTouchMode(true);
        mScrollRl.requestFocus();
    }
}
