package com.doctor.yuyi.activity;

import android.content.ContentValues;
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
import com.doctor.yuyi.MyUtils.TimeUtils;
import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.CommentAdapter;
import com.doctor.yuyi.bean.AdMessageDetial.Root;
import com.doctor.yuyi.bean.CommendListBean.Result;
import com.doctor.yuyi.myview.InformationListView;

import java.util.ArrayList;
import java.util.List;

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
    private Root root;
    private RelativeLayout mMany_box;
    private ProgressBar mBar;
    private HttpTools mHttptools;
    private int mStart = 0;
    private int mLimit = 2;
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

                        if (list.size() == 2) {
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
                        if (root != null) {
                            mStart = 0;
                            mList.clear();
                            mHttptools.getCommendList(mHandler, root.getId(), mStart, mLimit);//获取评论列表
                        }

                    } else {
                        ToastUtils.myToast(CommentInformationActivity.this, "评论失败");
                    }
                }
            } else if (msg.what == 104) {
                ToastUtils.myToast(CommentInformationActivity.this, "提交失败");
            }
        }
    };

    // bundle.putSerializable("root",mRoot);
    // intent.putExtra("root",bundle);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_information);
        initView();
    }

    public void initView() {
        mHttptools = HttpTools.getHttpToolsInstance();

        mComment_num = (TextView) findViewById(R.id.comment_num_tv);
        mShare_num = (TextView) findViewById(R.id.share_num_tv);
        mPraise_num = (TextView) findViewById(R.id.praise_num_tv);
        mTitle = (TextView) findViewById(R.id.title_tv);
        mPraise_img = (ImageView) findViewById(R.id.praise);

        Bundle bundle = getIntent().getBundleExtra("root");
        if (bundle != null) {
            root = (Root) bundle.getSerializable("root");
            mComment_num.setText(root.getCommentNum() + "");
            mShare_num.setText(root.getShareNum() + "");
            mPraise_num.setText(root.getLikeNum() + "");
            mTitle.setText(root.getTitle());
            if (root.isState()) {
                mPraise_img.setImageResource(R.mipmap.like_selected);
            } else {
                mPraise_img.setImageResource(R.mipmap.like);
            }
            mHttptools.getCommendList(mHandler, root.getId(), mStart, mLimit);//获取评论列表
        }


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
                            if (root != null) {
                                mHttptools.submitCommentContent(mHandler, Long.valueOf(UserInfo.UserName), root.getId(), getEditContent());
                                //隐藏软键盘
                                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            }

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
        }
        //else if (id == mSendBtn.getId()) {//发送按钮
//            if (getEditContent().equals("")) {
//                ToastUtils.myToast(this, "请输入评论内容");
//            } else {
//                if (root != null) {
//                    mHttptools.submitCommentContent(mHandler, Long.valueOf(UserInfo.UserName), root.getId(), getEditContent());
//                    //隐藏软键盘
//                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                }
//
//            }


        //   }
        else if (id == mMany_box.getId()) {//加载更多
            if (root != null) {
                mBar.setVisibility(View.VISIBLE);
                mStart += 2;
                mHttptools.getCommendList(mHandler, root.getId(), mStart, mLimit);//获取评论列表
            }

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
