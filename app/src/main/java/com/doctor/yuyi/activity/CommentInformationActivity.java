package com.doctor.yuyi.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.MyUtils.TimeUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.CommentAdapter;
import com.doctor.yuyi.bean.AdMessageDetial.Root;
import com.doctor.yuyi.bean.CommendListBean.Result;

import java.util.ArrayList;
import java.util.List;

public class CommentInformationActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private ListView mListview;
    private CommentAdapter mAdapter;
    private List<Result> mList=new ArrayList<>();
    private Button mSendBtn;
    private EditText mEdit;

    private TextView mComment_num;
    private TextView mShare_num;
    private TextView mPraise_num;
    private TextView mTitle;

    private HttpTools mHttptools;
    private int mStart=0;
    private int mLimit=10;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==4){
              Object o=  msg.obj;
                if (o!=null&&o instanceof com.doctor.yuyi.bean.CommendListBean.Root){
                    com.doctor.yuyi.bean.CommendListBean.Root root= (com.doctor.yuyi.bean.CommendListBean.Root) o;
                    if (root.getCode().equals("0")){
                        mList=root.getResult();
                        mAdapter.setmList(mList);
                        mAdapter.notifyDataSetChanged();
                    }
                }
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
        Bundle bundle = getIntent().getBundleExtra("root");
        if (bundle != null) {
            Root root = (Root) bundle.getSerializable("root");
            mComment_num.setText(root.getCommentNum() + "");
            mShare_num.setText(root.getShareNum() + "");
            mPraise_num.setText(root.getLikeNum() + "");
            mTitle.setText(root.getTitle());
            mHttptools.getCommendList(mHandler,root.getId(),mStart,mLimit);//获取评论列表
        }



        //返回
        mBack = (ImageView) findViewById(R.id.comment_back);
        mBack.setOnClickListener(this);

        mListview = (ListView) findViewById(R.id.comment_listview);
        mAdapter = new CommentAdapter(this,mList);
        mListview.setAdapter(mAdapter);

        mEdit = (EditText) findViewById(R.id.edit);
        mSendBtn = (Button) findViewById(R.id.comment_btn);
        mSendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mSendBtn.getId()) {
            //隐藏软键盘
            ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
