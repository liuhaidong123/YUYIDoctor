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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.UMShareImp.ShareUtils;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.bean.AdMessageDetial.Root;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class InformationMessageActivity extends AppCompatActivity implements View.OnClickListener, UMShareListener {
    private ImageView mBack;
    private ImageView mComment_img;
    private ImageView mShare_img;
    private ImageView mPraise_img;
    private TextView mShare_tv;
    private TextView mPraise_tv;
    private TextView mCommend_tv;


    private ImageView mImg;
    private TextView mTitle;
    private TextView mContent;
    private UMWeb umWeb;

    private Root mRoot;

    private HttpTools mHttpTools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {//广告，今日推荐，最新，热门资讯详情
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    mRoot = root;
                    //分享时需要的图片和内容
                    image = new UMImage(InformationMessageActivity.this, UrlTools.BASE + mRoot.getPicture());//设置要分享的图片
                    thumb = new UMImage(InformationMessageActivity.this, UrlTools.BASE + mRoot.getPicture());//设置分享图片的缩略图
                    image.setThumb(thumb);//图片设置缩略图
                    image.compressStyle = UMImage.CompressStyle.SCALE;
                    content = mRoot.getContent();
                    title=mRoot.getTitle();
                    umWeb=new UMWeb("http://59.110.169.148:8080/static/html/sharejump.html");
                    umWeb.setTitle(title);//标题
                    umWeb.setThumb(thumb);  //缩略图
                    umWeb.setDescription(content);//描述
                    Picasso.with(InformationMessageActivity.this).load(UrlTools.BASE + root.getPicture()).error(R.mipmap.error_big).into(mImg);
                    mTitle.setText(root.getTitle());
                    mContent.setText(root.getContent());
                    if (root.getShareNum() == null) {//分享设值
                        mShare_tv.setText("0");
                    } else {
                        mShare_tv.setText(root.getShareNum() + "");
                    }

                    if (root.getLikeNum() == null) {//点赞设值
                        mPraise_tv.setText("0");
                    } else {
                        mPraise_tv.setText(root.getLikeNum() + "");
                    }

                    if (root.getCommentNum() == null) {//评论设值
                        mCommend_tv.setText("0");
                    } else {
                        mCommend_tv.setText(root.getCommentNum() + "");
                    }

                    if (root.isState()) {
                        mPraise_img.setImageResource(R.mipmap.like_selected);
                        mPraise_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mHttpTools.informationPraise(mHandler, getIntent().getLongExtra("id", -1), UserInfo.UserToken);
                            }
                        });
                    } else {
                        mPraise_img.setImageResource(R.mipmap.like);
                        mPraise_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mHttpTools.informationPraise(mHandler, getIntent().getLongExtra("id", -1), UserInfo.UserToken);
                            }
                        });
                    }
                }
            } else if (msg.what == 101) {

            } else if (msg.what == 6) {//点赞
                Object o = msg.obj;
                if (o != null && o instanceof com.doctor.yuyi.bean.InformationPraise.Root) {
                    com.doctor.yuyi.bean.InformationPraise.Root root = (com.doctor.yuyi.bean.InformationPraise.Root) o;
                    if (root.getCode().equals("0")) {
                        if (root.getResult().equals("点赞成功")) {
                            mPraise_img.setImageResource(R.mipmap.like_selected);
                            mPraise_tv.setText(Integer.valueOf(mPraise_tv.getText().toString()) + 1 + "");
                            if (mRoot != null) {
                                mRoot.setState(true);
                                mRoot.setLikeNum(mRoot.getLikeNum() + 1);
                            }

                        } else if (root.getResult().equals("撤销点赞成功")) {
                            mPraise_img.setImageResource(R.mipmap.like);
                            mPraise_tv.setText(Integer.valueOf(mPraise_tv.getText().toString()) - 1 + "");
                            if (mRoot != null) {
                                mRoot.setState(false);
                                mRoot.setLikeNum(mRoot.getLikeNum() - 1);
                            }
                        }
                    } else {
                        ToastUtils.myToast(InformationMessageActivity.this, "点赞失败");
                    }
                }
            } else if (msg.what == 105) {

            }
            else if (msg.what == 16) {//分享接口
                Object o = msg.obj;
                if (o!=null&& o instanceof com.doctor.yuyi.bean.ShareBean.Root){
                    com.doctor.yuyi.bean.ShareBean.Root root= (com.doctor.yuyi.bean.ShareBean.Root) o;
                    if (root.getCode().equals("0")){

                    }else {

                    }
                }

            }else if (msg.what == 116) {

            }
        }
    };

    private UMImage image;
    private UMImage thumb;
    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_message);
        initView();

    }

    public void initView() {
        mHttpTools = HttpTools.getHttpToolsInstance();


        mImg = (ImageView) findViewById(R.id.information_img);//图片
        mTitle = (TextView) findViewById(R.id.information_title);//标题
        mContent = (TextView) findViewById(R.id.information_mess);//内容

        mShare_tv = (TextView) findViewById(R.id.share_num_tv);//分享数
        mPraise_tv = (TextView) findViewById(R.id.praise_num_tv);//点赞数
        mCommend_tv = (TextView) findViewById(R.id.comment_num_tv);//评论数

        mBack = (ImageView) findViewById(R.id.equip_back);
        mBack.setOnClickListener(this);
        //评论
        mComment_img = (ImageView) findViewById(R.id.comment_img1);
        mComment_img.setOnClickListener(this);
        //分享
        mShare_img = (ImageView) findViewById(R.id.share_img);
        mShare_img.setOnClickListener(this);
//        //点赞
        mPraise_img = (ImageView) findViewById(R.id.praise);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {//返回
            finish();
        } else if (id == mComment_img.getId()) {//评论
            if (mRoot != null) {
                Intent intent = new Intent(InformationMessageActivity.this, CommentInformationActivity.class);
                intent.putExtra("id", getIntent().getLongExtra("id", -1L));
               startActivity(intent);
            }


        } else if (id == mShare_img.getId()) {  //分享
            if (mRoot != null) {
                init();
            }
        }
    }

    //分享回调方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
        mHttpTools.getADMessageDetial(mHandler, getIntent().getLongExtra("id", -1), UserInfo.UserToken);//获取广告,今日推荐，最新，热门资讯详情
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
                if (image != null && content != null&& umWeb!=null) {
                    ShareUtils.share(this, this, umWeb);
                }

            }
            //版本小于23时，不需要判断敏感权限，执行业务逻辑
        } else {
            if (image != null && content != null&& umWeb!=null) {
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
                    if (image != null && content != null&& umWeb!=null) {
                        ShareUtils.share(this, this,umWeb);
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
            mHttpTools.shareInformation(mHandler, getIntent().getLongExtra("id", -1), UserInfo.UserToken, 2);
        } else if (share_media.toString().equals("WEIXIN_CIRCLE")) {//微信朋友圈
            mHttpTools.shareInformation(mHandler, getIntent().getLongExtra("id", -1), UserInfo.UserToken, 1);
        } else if (share_media.toString().equals("QZONE")) {
            mHttpTools.shareInformation(mHandler, getIntent().getLongExtra("id", -1), UserInfo.UserToken, 3);
        }

        ToastUtils.myToast(InformationMessageActivity.this, "分享成功");
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
