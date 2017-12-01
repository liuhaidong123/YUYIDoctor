package com.doctor.yuyi.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
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
import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.UMShareImp.ShareUtils;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.CommentAdapter;
import com.doctor.yuyi.bean.AdMessageDetial.Root;
import com.doctor.yuyi.bean.CommendListBean.Result;
import com.doctor.yuyi.myview.InformationListView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

public class CommentInformationActivity extends AppCompatActivity implements View.OnClickListener, UMShareListener {
    private ImageView mBack;
    private InformationListView mListview;
    private CommentAdapter mAdapter;
    private List<Result> mList = new ArrayList<>();
    private EditText mEdit;
    private RelativeLayout mComment_img_rl;
    private TextView mSend_btn;

    private TextView mComment_num;
    private TextView mTitle;
    private ImageView mShare_img;
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
                        mHttptools.getCommendList(mHandler, mid, mStart, mLimit);//获取评论列表
                        mComment_num.setText(Integer.valueOf(mComment_num.getText().toString()) + 1 + "");

                    } else {
                        ToastUtils.myToast(CommentInformationActivity.this, "评论失败");
                    }
                }
            } else if (msg.what == 104) {
                ToastUtils.myToast(CommentInformationActivity.this, "提交失败");
            } else if (msg.what == 2) {//广告，今日推荐，最新，热门资讯详情
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    //分享时需要的图片和内容
                    if ("".equals(root.getPicture())) {
                        image = new UMImage(CommentInformationActivity.this, R.mipmap.hospital_img);//设置要分享的图片
                        thumb = new UMImage(CommentInformationActivity.this, R.mipmap.hospital_img);//设置分享图片的缩略图
                    } else {
                        String[] strings = root.getPicture().split(";");
                        image = new UMImage(CommentInformationActivity.this, UrlTools.BASE + strings[0]);//设置要分享的图片
                        thumb = new UMImage(CommentInformationActivity.this, UrlTools.BASE + strings[0]);//设置分享图片的缩略图
                    }
                    image.setThumb(thumb);//图片设置缩略图
                    image.compressStyle = UMImage.CompressStyle.SCALE;
                    title = root.getTitle();
                    content = root.getContent();
                    umWeb = new UMWeb("http://www.zzzyy.cn/index.do");
                    umWeb.setThumb(thumb);
                    umWeb.setTitle(title);
                    umWeb.setDescription(content);
                    mTitle.setText(content);

                    if (root.getCommentNum() == null) {//评论设值
                        mComment_num.setText("0");
                    } else {
                        mComment_num.setText(root.getCommentNum() + "");
                    }

                }
            } else if (msg.what == 101) {
                ToastUtils.myToast(CommentInformationActivity.this, "数据错误");
            }

        }
    };


    private long mid;

    private UMImage image;
    private UMImage thumb;
    private String title;
    private String content;
    private UMWeb umWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_information);
        initView();
    }

    public void initView() {
        mid = getIntent().getLongExtra("id", -1);
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getCommendList(mHandler, mid, mStart, mLimit);//获取评论列表

        mComment_img_rl = (RelativeLayout) findViewById(R.id.comment_rl);
        mSend_btn = (TextView) findViewById(R.id.send_msg);
        mSend_btn.setOnClickListener(this);
        mComment_num = (TextView) findViewById(R.id.comment_num_tv);
        mShare_img = (ImageView) findViewById(R.id.share_img);
        mShare_img.setOnClickListener(this);
        mTitle = (TextView) findViewById(R.id.title_tv);


        //返回
        mBack = (ImageView) findViewById(R.id.comment_back);
        mBack.setOnClickListener(this);

        mListview = (InformationListView) findViewById(R.id.comment_listview);
        mAdapter = new CommentAdapter(this, mList);
        mListview.setAdapter(mAdapter);
        //输入评论框
        mEdit = (EditText) findViewById(R.id.my_comment_edit);
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mComment_img_rl.setVisibility(View.GONE);
                mShare_img.setVisibility(View.GONE);
                mSend_btn.setVisibility(View.VISIBLE);
            }
        });
        //发送按钮
        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (getEditContent().equals("")) {
                        ToastUtils.myToast(CommentInformationActivity.this, "请输入评论内容");
                    } else {

                        if (!"".equals(UserInfo.UserName)) {
                            mHttptools.submitCommentContent(mHandler, Long.valueOf(UserInfo.UserName), mid, getEditContent());
                            mEdit.setText("");
                            //隐藏软键盘
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            mComment_img_rl.setVisibility(View.VISIBLE);
                            mShare_img.setVisibility(View.VISIBLE);
                            mSend_btn.setVisibility(View.GONE);
                        } else {
                            ToastUtils.myToast(getApplicationContext(), "请登录您的账号");
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
    protected void onResume() {
        super.onResume();
        mHttptools.getADMessageDetial(mHandler, mid, UserInfo.UserToken);//获取广告,今日推荐，最新，热门资讯详情
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mMany_box.getId()) {//加载更多
            mBar.setVisibility(View.VISIBLE);
            mStart += 10;
            mHttptools.getCommendList(mHandler, mid, mStart, mLimit);//获取评论列表
        } else if (id == mShare_img.getId()) {
            init();
        } else if (id == mSend_btn.getId()) {//发送
            if (getEditContent().equals("")) {
                ToastUtils.myToast(getApplicationContext(), "请输入评论内容");
            } else {

                if (!"".equals(UserInfo.UserName)) {
                    mHttptools.submitCommentContent(mHandler, Long.valueOf(UserInfo.UserName), mid, getEditContent());
                    mEdit.setText("");
                    //隐藏软键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    mComment_img_rl.setVisibility(View.VISIBLE);
                    mShare_img.setVisibility(View.VISIBLE);
                    mSend_btn.setVisibility(View.GONE);
                } else {
                    ToastUtils.myToast(getApplicationContext(), "请登录您的账号");
                }
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

    //分享回调方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public static final int REQUEST_CODE_ASK_READ_PHONE = 123;

    public void init() {
        //sdk版本>=23时，
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            //如果读取电话权限没有授权
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //请求授权， 点击允许或者拒绝时会回调onRequestPermissionsResult（），
                //注意 ：如果是在fragment中申请权限，不要使用ActivityCompat.requestPermissions，
                //直接使用requestPermissions （new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_READ_PHONE）
                //否则不会调用onRequestPermissionsResult（）方法。
                ActivityCompat.requestPermissions(this,
                        //在这个数组中可以添加很多权限
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_READ_PHONE);
                return;
                //如果已经授权，执行业务逻辑
            } else {
                if (image != null && content != null && umWeb != null) {
                    ShareUtils.share(this, this, umWeb);
                }

            }
            //版本小于23时，不需要判断敏感权限，执行业务逻辑
        } else {
            if (image != null && content != null && umWeb != null) {
                ShareUtils.share(this, this, umWeb);
            }

        }
    }


    //请求授权， 点击允许或者拒绝时会回调onRequestPermissionsResult（），
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_READ_PHONE:
                //点击了允许，授权成功
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (image != null && content != null && umWeb != null) {
                        ShareUtils.share(this, this, umWeb);
                    }
                    //点击了拒绝，授权失败
                } else {
                    // Permission Denied
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.e("分享开始", "===" + share_media.toString());
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        if (share_media.toString().equals("SINA")) {//微博
            // mHttptools.shareInformation(mHandler, getIntent().getLongExtra("id", -1), UserInfo.UserToken, 2);
        } else if (share_media.toString().equals("WEIXIN_CIRCLE")) {//微信朋友圈
            // mHttptools.shareInformation(mHandler, getIntent().getLongExtra("id", -1), UserInfo.UserToken, 1);
        } else if (share_media.toString().equals("QZONE")) {
            // mHttptools.shareInformation(mHandler, getIntent().getLongExtra("id", -1), UserInfo.UserToken, 3);
        }

        ToastUtils.myToast(CommentInformationActivity.this, "分享成功");
        Log.e("分享成功结果", "===" + share_media);
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Log.e("分享错误", "===" + share_media.toString());
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Log.e("分享取消", "===" + share_media.toString());
    }
}
