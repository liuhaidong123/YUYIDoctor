package com.doctor.yuyi.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.UMShareImp.UMShareListner;
import com.doctor.yuyi.UMShareImp.UmAuthListener;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.bean.AdMessageDetial.Root;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class InformationMessageActivity extends AppCompatActivity implements View.OnClickListener {
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
                ToastUtils.myToast(InformationMessageActivity.this, "数据错误");
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
                ToastUtils.myToast(InformationMessageActivity.this, "数据错误");
            }
        }
    };

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
                intent.putExtra("id", getIntent().getLongExtra("id", -1));
                startActivityForResult(intent, 1);
            }


        } else if (id == mShare_img.getId()) {  //分享
            if (mRoot != null) {
////                UMImage image = new UMImage(InformationMessageActivity.this, UrlTools.BASE + mRoot.getPicture());//设置要分享的图片
//                UMImage thumb = new UMImage(InformationMessageActivity.this, UrlTools.BASE + mRoot.getPicture());//设置分享图片的缩略图
////                image.setThumb(thumb);
////                image.compressStyle = UMImage.CompressStyle.SCALE;
//                String title = mRoot.getTitle();
//                String content = mRoot.getContent();
//                UMWeb web = new UMWeb("http://www.bai.com");
//                web.setTitle(title);
//                web.setThumb(thumb);
//                web.setDescription(content);
                new ShareAction(InformationMessageActivity.this).withText("nihao")
                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(new UMShareListner()).open();
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
}
