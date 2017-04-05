package com.doctor.yuyi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.UMShareImp.UMShareListner;
import com.doctor.yuyi.UMShareImp.UmAuthListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class InformationMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private ImageView mComment_img;
    private ImageView mShare_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_message);
        initView();

    }

    public void initView() {
        mBack = (ImageView) findViewById(R.id.equip_back);
        mBack.setOnClickListener(this);
        //评论
        mComment_img = (ImageView) findViewById(R.id.comment_img1);
        mComment_img.setOnClickListener(this);
        //分享
        mShare_img= (ImageView) findViewById(R.id.share_img);
        mShare_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {//返回
            finish();
        } else if (id == mComment_img.getId()) {//评论
            startActivity(new Intent(InformationMessageActivity.this, CommentInformationActivity.class));
        }else if (id == mShare_img.getId()) {  //分享
            new ShareAction(InformationMessageActivity.this).withText("斤斤计较军军")
                    .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(new UMShareListner()).open();
        }
    }

    //分享回调方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
}
