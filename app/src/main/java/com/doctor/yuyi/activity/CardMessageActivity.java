package com.doctor.yuyi.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.MyUtils.TimeUtils;
import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.CardMessageCommentAdapter;
import com.doctor.yuyi.adapter.CardMessageImgAdapter;
import com.doctor.yuyi.bean.CircleMessageBean.CommentList;
import com.doctor.yuyi.bean.CircleMessageBean.Root;
import com.doctor.yuyi.myview.InformationListView;
import com.doctor.yuyi.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
//帖子详情
public class CardMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private InformationListView mImgListView, mCommentListView;
    private CardMessageImgAdapter mImgAdapter;
    private String[] mStrImg = new String[0];

    private CardMessageCommentAdapter mCommentAdapter;
    private List<CommentList> mList=new ArrayList<>();
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

    private long id=-1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 8) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getCode().equals("0")) {

                        if (isFlag){//头部一些信息只加载一次

                            Log.e("=====","头部一些信息只加载一次");
                            Picasso.with(CardMessageActivity.this).load(UrlTools.BASE + root.getResult().getAvatar()).error(R.mipmap.error_small).into(mHead_img);
                            mName.setText(root.getResult().getTrueName());
                            mTime.setText(TimeUtils.getTime(root.getResult().getCreateTimeString()));
                            mContent.setText(root.getResult().getContent());
                            mTitle.setText(root.getResult().getTitle());
                            mPraise_num.setText(root.getResult().getLikeNum() + "");

                            id=root.getResult().getId();

                            //点赞设值
                            if (root.getResult().getLikeNum() == null) {
                                mPraise_num.setText("0");
                            } else {
                                mPraise_num.setText(root.getResult().getLikeNum() + "");
                            }

                            if (root.getResult().getIsLike()){
                                mPraise_img.setImageResource(R.mipmap.like_selected);
                            }else {
                                mPraise_img.setImageResource(R.mipmap.like);
                            }



                            String strImg = root.getResult().getPicture();
                            if (!strImg.equals("") && strImg != null) {
                                mStrImg = strImg.split(";");
                            }
                            mImgAdapter.setStr(mStrImg);
                            mImgAdapter.notifyDataSetChanged();
                        }

                        isFlag=false;

                        //评论设值
                        if (root.getResult().getCommentNum() == null) {
                            mComment_allNum.setText("0");
                        } else {
                            mComment_allNum.setText(root.getResult().getCommentNum() + "");
                        }
                        mBar.setVisibility(View.INVISIBLE);
                        List<CommentList> list=new ArrayList<>();
                        list=root.getResult().getCommentList();
                        mList.addAll(list);
                        mCommentAdapter.setList(mList);
                        mCommentAdapter.notifyDataSetChanged();

                        if (list.size() != 10) {
                            mMany_Box.setVisibility(View.GONE);
                        } else {
                            mMany_Box.setVisibility(View.VISIBLE);
                        }

                    }
                }
            } else if (msg.what == 108) {
                ToastUtils.myToast(CardMessageActivity.this, "数据错误");
            } else  if (msg.what == 9) {//学术圈详情点赞接口
                Object o = msg.obj;
                if (o != null && o instanceof com.doctor.yuyi.bean.InformationPraise.Root) {
                    com.doctor.yuyi.bean.InformationPraise.Root root = (com.doctor.yuyi.bean.InformationPraise.Root) o;
                    if (root.getCode().equals("0")) {
                        if (root.getResult().equals("点赞成功")) {
                            mPraise_img.setImageResource(R.mipmap.like_selected);
                            mPraise_num.setText( Integer.valueOf(mPraise_num.getText().toString())+1+"");
                        } else {
                            mPraise_img.setImageResource(R.mipmap.like);
                            mPraise_num.setText( Integer.valueOf(mPraise_num.getText().toString())-1+"");
                        }
                    }
                }
            } else if (msg.what == 109) {
                ToastUtils.myToast(CardMessageActivity.this, "点赞失败");
            }else if (msg.what==12){//学术圈提交评论
                Object o = msg.obj;
                if (o != null && o instanceof com.doctor.yuyi.bean.SubmitComment.Root) {
                    com.doctor.yuyi.bean.SubmitComment.Root rootSubmit = (com.doctor.yuyi.bean.SubmitComment.Root) o;
                    if (rootSubmit.getCode().equals("0")) {
                        mEdit.setText("");
                        mStart = 0;
                        mList.clear();
                        mHttptools.getHotSelectNewMessage(mHandler, UserInfo.UserToken, mStart, mLimit, getIntent().getLongExtra("id", -1));

                    } else {
                        ToastUtils.myToast(CardMessageActivity.this, "评论失败");
                    }
                }
            } else if (msg.what == 112) {
                ToastUtils.myToast(CardMessageActivity.this, "提交失败");
            }

        }
    };
    private HttpTools mHttptools;
    private int mStart = 0;
    private int mLimit = 10;
    private RelativeLayout mMany_Box;//加载更多
    private ProgressBar mBar;

    private boolean isFlag=true;

    //评论框
    private EditText mEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_message);
        initView();
    }

    private void initView() {
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getHotSelectNewMessage(mHandler, UserInfo.UserToken, mStart, mLimit, getIntent().getLongExtra("id", -1));


        mHead_img = (RoundImageView) findViewById(R.id.car_user_head_img);
        mName = (TextView) findViewById(R.id.car_user_name);
        mTime = (TextView) findViewById(R.id.car_user_time);
        mPraise_num = (TextView) findViewById(R.id.car_user_praise_tv);
        mPraise_img = (ImageView) findViewById(R.id.car_user_praise_img);
        mPraise_img.setOnClickListener(this);
        mTitle = (TextView) findViewById(R.id.card_title);
        mContent = (TextView) findViewById(R.id.card_message);
        mComment_allNum = (TextView) findViewById(R.id.card_comment_num);


        mback = (ImageView) findViewById(R.id.comment_back);
        mback.setOnClickListener(this);
        //帖子详情页面的图片listview
        mImgListView = (InformationListView) findViewById(R.id.card_img_listview);
        mImgAdapter = new CardMessageImgAdapter(this, mStrImg);
        mImgListView.setAdapter(mImgAdapter);
        //帖子详情页面的评论listview
        mCommentListView = (InformationListView) findViewById(R.id.card_comment_listview);
        mCommentAdapter = new CardMessageCommentAdapter(this,mList);
        mCommentListView.setAdapter(mCommentAdapter);
        //将scrollView定位到顶部
        mScrollRl = (RelativeLayout) findViewById(R.id.scroll_relative);
        mScrollRl.setFocusable(true);
        mScrollRl.setFocusableInTouchMode(true);
        mScrollRl.requestFocus();
        //加载更多
        mMany_Box = (RelativeLayout)findViewById(R.id.more_relative);
        mMany_Box.setOnClickListener(this);
        mBar = (ProgressBar)findViewById(R.id.pbLocate);

        //评论框
        mEdit= (EditText) findViewById(R.id.circle_edit);
        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (getEditContent().equals("")) {
                        ToastUtils.myToast(CardMessageActivity.this, "请输入评论内容");
                    } else {
                        if (id!=-1){
                            mHttptools.submitCircleComment(mHandler, Long.valueOf(UserInfo.UserName), id, getEditContent());
                            //隐藏软键盘
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }else {
                            ToastUtils.myToast(CardMessageActivity.this, "评论数据错误");
                        }

                    }
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mback.getId()) {
            finish();
        }else if (id==mPraise_img.getId()){
            mHttptools.circlePraise(mHandler,getIntent().getLongExtra("id", -1),UserInfo.UserToken);
        }else if (id == mMany_Box.getId()) {//加载更多
            mStart += 10;
            mHttptools.getHotSelectNewMessage(mHandler, UserInfo.UserToken, mStart, mLimit, getIntent().getLongExtra("id", -1));
            mBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取输入框的内容
     *
     * @return
     */
    public String getEditContent() {
        String content = mEdit.getText().toString().trim();
        if (content.equals("")) {
            return "";
        }
        return content;
    }
}
