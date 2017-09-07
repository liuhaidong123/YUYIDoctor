package com.doctor.yuyi.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.Bean_UserInfo;
import com.doctor.yuyi.lzh_utils.BitmapTobase64;
import com.doctor.yuyi.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class UserInfo_Activity extends Activity{
    Bean_UserInfo info;//用户信息的实体类
    TextView titleinclude_textview;
    TextView UserInfo_Name,UserInfo_HospitalName,UserInfo_KS,UserInfo_ZC,UserInfo_Phone;//姓名,医院,科室,职称,电话
    com.doctor.yuyi.lzh_utils.RoundImageView UserInfo_Image;//头像
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_);
        info= (Bean_UserInfo) getIntent().getSerializableExtra("data");
        titleinclude_textview= (TextView) findViewById(R.id.titleinclude_textview);
        titleinclude_textview.setText("个人信息");
        if (info!=null){
            if (info.getPhysician()!=null){
                initView();
            }

        }
    }

    private void initView() {
        UserInfo_Name= (TextView) findViewById(R.id.UserInfo_Name);
        UserInfo_Name.setText(info.getPhysician().getTrueName()!=null&&!"".equals(info.getPhysician().getTrueName())?info.getPhysician().getTrueName():"未获取");

        UserInfo_HospitalName= (TextView) findViewById(R.id.UserInfo_HospitalName);
        UserInfo_HospitalName.setText(info.getPhysician().getHospitalName()!=null&&!"".equals(info.getPhysician().getHospitalName())?info.getPhysician().getHospitalName():"未获取");

        UserInfo_KS= (TextView) findViewById(R.id.UserInfo_KS);
        UserInfo_KS.setText(info.getPhysician().getDepartmentName()!=null&&!"".equals(info.getPhysician().getDepartmentName())?info.getPhysician().getDepartmentName():"未获取");

        UserInfo_ZC= (TextView) findViewById(R.id.UserInfo_ZC);
        UserInfo_ZC.setText(info.getPhysician().getTitle()!=null&&!"".equals(info.getPhysician().getTitle())?info.getPhysician().getTitle():"未获取");

        UserInfo_Phone= (TextView) findViewById(R.id.UserInfo_Phone);
        UserInfo_Phone.setText(info.getPhysician().getTelephone()+"");

        UserInfo_Image= (com.doctor.yuyi.lzh_utils.RoundImageView) findViewById(R.id.UserInfo_Image);
        Picasso.with(this).load(Ip.URL+info.getPhysician().getAvatar()).error(R.mipmap.userdefault).into(UserInfo_Image);
    }
    public void reBack(View vi){
        if (vi!=null){
            finish();
        }
    }
}
