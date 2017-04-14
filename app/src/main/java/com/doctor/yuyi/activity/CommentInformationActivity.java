package com.doctor.yuyi.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.MyUtils.TimeUtils;
import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.CommentAdapter;
import com.doctor.yuyi.bean.AdMessageDetial.Root;
import com.doctor.yuyi.bean.CommendListBean.Result;
import com.doctor.yuyi.myview.InformationListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
//咨询评论页面
public class CommentInformationActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private InformationListView mListview;
    private CommentAdapter mAdapter;
    private List<Result> mList = new ArrayList<>();
    private EditText mEdit;

    private TextView mComment_num;
    private TextView mShare_num;
    private TextView mPraise_num;
    private TextView mTitle;
    private ImageView mPraise_img;
    private RelativeLayout mMany_box;
    private ProgressBar mBar;
    private HttpTools mHttptools;
    private int mStart = 0;
    private int mLimit = 10;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 4) {//评论列表
                Object o = msg.obj;
                if (o != null && o instanceof com.doctor.yuyi.bean.CommendListBean.Root) {
                    com.doctor.yuyi.bean.CommendListBean.Root root = (com.doctor.yuyi.bean.CommendListBean.Root) o;
                    if (root.getCode().equals("0")) {
                        List<Result> list = new ArrayList<>();
                        list = root.getResult();
                        mList.addAll(list);
                        mAdapter.setmList(mList);
                        mAdapter.notifyDataSetChanged();

                        if (list.size() == 10) {
                            mMany_box.setVisibility(View.VISIBLE);
                            mBar.setVisibility(View.INVISIBLE);
                        } else {
                            mMany_box.setVisibility(View.GONE);
                            mBar.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            } else if (msg.what == 103) {
                ToastUtils.myToast(CommentInformationActivity.this, "获取评论列表失败");
            } else if (msg.what == 5) {//提交评论返回结果
                Object o = msg.obj;
                if (o != null && o instanceof com.doctor.yuyi.bean.SubmitComment.Root) {
                    com.doctor.yuyi.bean.SubmitComment.Root rootSubmit = (com.doctor.yuyi.bean.SubmitComment.Root) o;
                    if (rootSubmit.getCode().equals("0")) {
                        mEdit.setText("");
                        mStart = 0;
                        mList.clear();
                        mHttptools.getCommendList(mHandler, id, mStart, mLimit);//获取评论列表
                        mComment_num.setText(Integer.valueOf(mComment_num.getText().toString()) + 1 + "");

                    } else {
                        ToastUtils.myToast(CommentInformationActivity.this, "评论失败");
                    }
                }
            } else if (msg.what == 104) {
                ToastUtils.myToast(CommentInformationActivity.this, "提交失败");
            }
            if (msg.what == 2) {//广告，今日推荐，最新，热门资讯详情
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    mTitle.setText(root.getTitle());
                    if (root.getShareNum() == null) {//分享设值
                        mShare_num.setText("0");
                    } else {
                        mShare_num.setText(root.getShareNum() + "");
                    }

                    if (root.getLikeNum() == null) {//点赞设值
                        mPraise_num.setText("0");
                    } else {
                        mPraise_num.setText(root.getLikeNum() + "");
                    }

                    if (root.getCommentNum() == null) {//评论设值
                        mComment_num.setText("0");
                    } else {
                        mComment_num.setText(root.getCommentNum() + "");
                    }

                    if (root.isState()) {
                        mPraise_img.setImageResource(R.mipmap.like_selected);
                        mPraise_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mHttptools.informationPraise(mHandler, id, UserInfo.UserToken);
                            }
                        });
                    } else {
                        mPraise_img.setImageResource(R.mipmap.like);
                        mPraise_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mHttptools.informationPraise(mHandler, id, UserInfo.UserToken);
                            }
                        });
                    }
                }
            } else if (msg.what == 101) {
                ToastUtils.myToast(CommentInformationActivity.this, "数据错误");
            } else if (msg.what == 6) {//点赞
                Object o = msg.obj;
                if (o != null && o instanceof com.doctor.yuyi.bean.InformationPraise.Root) {
                    com.doctor.yuyi.bean.InformationPraise.Root root = (com.doctor.yuyi.bean.InformationPraise.Root) o;
                    if (root.getCode().equals("0")) {
                        if (root.getResult().equals("点赞成功")) {
                            mPraise_img.setImageResource(R.mipmap.like_selected);
                            mPraise_num.setText(Integer.valueOf(mPraise_num.getText().toString()) + 1 + "");

                        } else if (root.getResult().equals("撤销点赞成功")) {
                            mPraise_img.setImageResource(R.mipmap.like);
                            mPraise_num.setText(Integer.valueOf(mPraise_num.getText().toString()) - 1 + "");
                        }
                    } else {
                        ToastUtils.myToast(CommentInformationActivity.this, "点赞失败");
                    }
                }
            } else if (msg.what == 105) {
                ToastUtils.myToast(CommentInformationActivity.this, "数据错误");
            }
        }
    };


    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_information);
        initView();
    }

    public void initView() {
        id = getIntent().getLongExtra("id", -1);
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getCommendList(mHandler, id, mStart, mLimit);//获取评论列表
        mHttptools.getADMessageDetial(mHandler, id, UserInfo.UserToken);//获取广告,今日推荐，最新，热门资讯详情
        mComment_num = (TextView) findViewById(R.id.comment_num_tv);
        mShare_num = (TextView) findViewById(R.id.share_num_tv);
        mPraise_num = (TextView) findViewById(R.id.praise_num_tv);
        mTitle = (TextView) findViewById(R.id.title_tv);
        mPraise_img = (ImageView) findViewById(R.id.praise);

        //返回
        mBack = (ImageView) findViewById(R.id.comment_back);
        mBack.setOnClickListener(this);

        mListview = (InformationListView) findViewById(R.id.comment_listview);
        mAdapter = new CommentAdapter(this, mList);
        mListview.setAdapter(mAdapter);
        //输入评论框
        mEdit = (EditText) findViewById(R.id.edit);
        //发送按钮
        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (getEditContent().equals("")) {
                        ToastUtils.myToast(CommentInformationActivity.this, "请输入评论内容");
                    } else {

                        mHttptools.submitCommentContent(mHandler, Long.valueOf(UserInfo.UserName), id, getEditContent());
                        //隐藏软键盘
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                    }
                    return true;
                }
                return false;
            }

        });

        //加载更多
        mMany_box = (RelativeLayout) findViewById(R.id.many_relative);
        mMany_box.setOnClickListener(this);
        mBar = (ProgressBar) findViewById(R.id.pbLocate);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mMany_box.getId()) {//加载更多
            mBar.setVisibility(View.VISIBLE);
            mStart += 10;
            mHttptools.getCommendList(mHandler, id, mStart, mLimit);//获取评论列表

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
