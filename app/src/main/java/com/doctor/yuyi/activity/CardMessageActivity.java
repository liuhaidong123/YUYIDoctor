package com.doctor.yuyi.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.doctor.yuyi.UMShareImp.ShareUtils;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.CardMessageCommentAdapter;
import com.doctor.yuyi.adapter.CardMessageImgAdapter;
import com.doctor.yuyi.bean.CircleMessageBean.CommentList;
import com.doctor.yuyi.bean.CircleMessageBean.Root;
import com.doctor.yuyi.myview.InformationListView;
import com.doctor.yuyi.myview.RoundImageView;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardMessageActivity extends AppCompatActivity implements View.OnClickListener, UMShareListener {

    private InformationListView mImgListView, mCommentListView;
    private CardMessageImgAdapter mImgAdapter;
    private String[] mStrImg = new String[0];
    private CardMessageCommentAdapter mCommentAdapter;
    private List<CommentList> mList = new ArrayList<>();
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
    private ImageView mShare_img;
    private long id = -1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1012) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getCode().equals("0")) {

                        if (isFlag) {//头部一些信息只加载一次
                            //分享时需要的图片和内容
                            if ("".equals(root.getResult().getPicture())) {
                                image = new UMImage(CardMessageActivity.this, R.mipmap.hospital_img);//设置要分享的图片
                                thumb = new UMImage(CardMessageActivity.this, R.mipmap.hospital_img);//设置分享图片的缩略图
                            } else {
                                String[] strings = root.getResult().getPicture().split(";");
                                image = new UMImage(CardMessageActivity.this, UrlTools.BASE + strings[0]);//设置要分享的图片
                                thumb = new UMImage(CardMessageActivity.this, UrlTools.BASE + strings[0]);//设置分享图片的缩略图
                            }
                            image.setThumb(thumb);//图片设置缩略图
                            image.compressStyle = UMImage.CompressStyle.SCALE;
                            title = root.getResult().getTitle();
                            content = root.getResult().getContent();
                            umWeb = new UMWeb("http://www.zzzyy.cn/index.do");
                            umWeb.setTitle(title);
                            umWeb.setThumb(thumb);
                            umWeb.setDescription(content);
                            Log.e("=====", "头部一些信息只加载一次");
                            Picasso.with(CardMessageActivity.this).load(UrlTools.BASE + root.getResult().getAvatar()).error(R.mipmap.userdefault).into(mHead_img);
                            mName.setText(root.getResult().getTrueName());
                            mTime.setText(TimeUtils.getTime(root.getResult().getCreateTimeString()));
                            mContent.setText(root.getResult().getContent());
                            mTitle.setText(root.getResult().getTitle());
                            mPraise_num.setText(root.getResult().getLikeNum() + "");

                            id = root.getResult().getId();

                            //点赞设值
                            if (root.getResult().getLikeNum() == null) {
                                mPraise_num.setText("0");
                            } else {
                                mPraise_num.setText(root.getResult().getLikeNum() + "");
                            }

                            if (root.getResult().getIsLike()) {
                                mPraise_img.setImageResource(R.mipmap.like_selected);
                            } else {
                                mPraise_img.setImageResource(R.mipmap.like);
                            }


                            String strImg = root.getResult().getPicture();
                            if (!strImg.equals("") && strImg != null) {
                                mStrImg = strImg.split(";");
                            }
                            mImgAdapter.setStr(mStrImg);
                            mImgAdapter.notifyDataSetChanged();
                        }

                        isFlag = false;

                        //评论设值
                        if (root.getResult().getCommentNum() == null) {
                            mComment_allNum.setText("0");
                        } else {
                            mComment_allNum.setText(root.getResult().getCommentNum() + "");
                        }
                        mBar.setVisibility(View.INVISIBLE);
                        List<CommentList> list = new ArrayList<>();
                        list = root.getResult().getCommentList();
                        mList.addAll(list);
                        mCommentAdapter.setList(mList);
                        mCommentAdapter.notifyDataSetChanged();
                        mData_rl.setVisibility(View.VISIBLE);//显示数据
                        if (list.size() != 10) {
                            mMany_Box.setVisibility(View.GONE);
                        } else {
                            mMany_Box.setVisibility(View.VISIBLE);
                        }

                    }
                }
            } else if (msg.what == 108) {
                mNodata_Rl.setVisibility(View.VISIBLE);
                mNoMsg_tv.setText("账号异常,请重新登录");
                mShare_img.setClickable(false);
                ToastUtils.myToast(CardMessageActivity.this, "请重新登录");
            } else if (msg.what == 9) {//学术圈详情点赞接口
                Object o = msg.obj;
                if (o != null && o instanceof com.doctor.yuyi.bean.InformationPraise.Root) {
                    com.doctor.yuyi.bean.InformationPraise.Root root = (com.doctor.yuyi.bean.InformationPraise.Root) o;
                    if (root.getCode().equals("0")) {
                        if (root.getResult().equals("点赞成功")) {
                            mPraise_img.setImageResource(R.mipmap.like_selected);
                            mPraise_num.setText(Integer.valueOf(mPraise_num.getText().toString()) + 1 + "");
                        } else {
                            mPraise_img.setImageResource(R.mipmap.like);
                            mPraise_num.setText(Integer.valueOf(mPraise_num.getText().toString()) - 1 + "");
                        }
                    }
                }
            } else if (msg.what == 109) {
                ToastUtils.myToast(CardMessageActivity.this, "点赞失败");
            } else if (msg.what == 12) {//学术圈提交评论
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
    //评论框
    private EditText mEdit;
    private boolean isFlag = true;
    private UMImage image;
    private UMImage thumb;
    private String title;
    private String content;
    private UMWeb umWeb;
    private RelativeLayout mNodata_Rl,mData_rl;
    private TextView mNoMsg_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_message);
        initView();
    }

    private void initView() {
        mData_rl= (RelativeLayout) findViewById(R.id.data_rl);
        mNodata_Rl = (RelativeLayout) findViewById(R.id.nodata_rl);
        mNodata_Rl.setOnClickListener(this);
        mNoMsg_tv = (TextView) findViewById(R.id.no_msg);
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getHotSelectNewMessage(mHandler, UserInfo.UserToken, mStart, mLimit, getIntent().getLongExtra("id", -1));

        mShare_img = (ImageView) findViewById(R.id.share_card_img);
        mShare_img.setOnClickListener(this);
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
        mCommentAdapter = new CardMessageCommentAdapter(this, mList);
        mCommentListView.setAdapter(mCommentAdapter);
        //将scrollView定位到顶部
        mScrollRl = (RelativeLayout) findViewById(R.id.scroll_relative);
        mScrollRl.setFocusable(true);
        mScrollRl.setFocusableInTouchMode(true);
        mScrollRl.requestFocus();
        //加载更多
        mMany_Box = (RelativeLayout) findViewById(R.id.more_relative);
        mMany_Box.setOnClickListener(this);
        mBar = (ProgressBar) findViewById(R.id.pbLocate);

        //评论框
        mEdit = (EditText) findViewById(R.id.circle_edit);
        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (getEditContent().equals("")) {
                        ToastUtils.myToast(CardMessageActivity.this, "请输入评论内容");
                    } else {
                        if (id != -1) {
                            Map<String,String> map=new HashMap<>();
                            map.put("Content",getEditContent());
                            mHttptools.submitCircleComment(mHandler, Long.valueOf(UserInfo.UserName), id, map);
                            //隐藏软键盘
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        } else {
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
        } else if (id == mPraise_img.getId()) {//点赞
            mHttptools.circlePraise(mHandler, getIntent().getLongExtra("id", -1), UserInfo.UserToken);
        } else if (id == mMany_Box.getId()) {//加载更多
            mStart += 10;
            mHttptools.getHotSelectNewMessage(mHandler, UserInfo.UserToken, mStart, mLimit, getIntent().getLongExtra("id", -1));
            mBar.setVisibility(View.VISIBLE);
        } else if (id == mShare_img.getId()) {
            init();
        }
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
                if (content != null && title != null && umWeb != null) {
                    ShareUtils.share(this, this, umWeb);
                }

            }
            //版本小于23时，不需要判断敏感权限，执行业务逻辑
        } else {
            if (content != null && title != null && umWeb != null) {
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
                    if (content != null && title != null && umWeb != null) {
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

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.e("分享开始", "===" + share_media.toString());
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Log.e("分享成功结果", "===" + share_media);
//        if (share_media.toString().equals("SINA")){//微博
//            mHttptools.shareCard(mHandler, getIntent().getLongExtra("id", -1),UserInfo.UserToken,2);
//        }else if (share_media.toString().equals("WEIXIN_CIRCLE")){//微信朋友圈
//            mHttptools.shareCard(mHandler, getIntent().getLongExtra("id", -1),UserInfo.UserToken,1);
//        }else if (share_media.toString().equals("QZONE")){
//            mHttptools.shareCard(mHandler, getIntent().getLongExtra("id", -1),UserInfo.UserToken,3);
//        }

        ToastUtils.myToast(CardMessageActivity.this, "分享成功");
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
