package com.doctor.yuyi.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.MyUtils.TimeUtils;
import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.CardMessageCommentAdapter;
import com.doctor.yuyi.adapter.CardMessageImgAdapter;
import com.doctor.yuyi.bean.CircleMessageBean.Root;
import com.doctor.yuyi.myview.InformationListView;
import com.doctor.yuyi.myview.RoundImageView;
import com.squareup.picasso.Picasso;

public class CardMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private InformationListView mImgListView, mCommentListView;
    private CardMessageImgAdapter mImgAdapter;
    private CardMessageCommentAdapter mCommentAdapter;
    private RelativeLayout mScrollRl;
    private ImageView mback;

    private RoundImageView mHead_img;
    private TextView mName;
    private TextView mTime;
    private TextView mPraise_num;
    private TextView mTitle;
    private TextView mContent;
    private TextView mComment_allNum;
    private ImageView mPraise_img;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 8) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getCode().equals("0")) {
                        Picasso.with(CardMessageActivity.this).load(UrlTools.BASE+root.getResult().getAvatar()).error(R.mipmap.error_small).into(mHead_img);
                        mName.setText(root.getResult().getTrueName());
                        mTime.setText(TimeUtils.getTime(root.getResult().getCreateTimeString()));
                        mContent.setText(root.getResult().getContent());
                        mTitle.setText(root.getResult().getTitle());
                        mPraise_num.setText(root.getResult().getLikeNum()+"");
                        mComment_allNum.setText(root.getResult().getCommentNum()+"");

                    }
                }
            } else if (msg.what == 108) {
                ToastUtils.myToast(CardMessageActivity.this, "数据错误");
            }
        }
    };
    private HttpTools mHttptools;
    private int mStart = 0;
    private int mLimit = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_message);
        initView();
    }

    private void initView() {
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getHotSelectNewMessage(mHandler, mStart, mLimit, getIntent().getLongExtra("id", -1));


        mHead_img = (RoundImageView) findViewById(R.id.car_user_head_img);
        mName = (TextView) findViewById(R.id.car_user_name);
        mTime = (TextView) findViewById(R.id.car_user_time);
        mPraise_num = (TextView) findViewById(R.id.car_user_praise_tv);
        mPraise_img= (ImageView) findViewById(R.id.car_user_praise_img);
        mTitle = (TextView) findViewById(R.id.card_title);
        mContent = (TextView) findViewById(R.id.card_message);
        mComment_allNum = (TextView) findViewById(R.id.card_comment_num);


        mback = (ImageView) findViewById(R.id.comment_back);
        mback.setOnClickListener(this);
        //帖子详情页面的图片listview
        mImgListView = (InformationListView) findViewById(R.id.card_img_listview);
        mImgAdapter = new CardMessageImgAdapter(this);
        mImgListView.setAdapter(mImgAdapter);
        //帖子详情页面的评论listview
        mCommentListView = (InformationListView) findViewById(R.id.card_comment_listview);
        mCommentAdapter = new CardMessageCommentAdapter(this);
        mCommentListView.setAdapter(mCommentAdapter);
        //将scrollView定位到顶部
        mScrollRl = (RelativeLayout) findViewById(R.id.scroll_relative);
        mScrollRl.setFocusable(true);
        mScrollRl.setFocusableInTouchMode(true);
        mScrollRl.requestFocus();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
        }
    }
}
