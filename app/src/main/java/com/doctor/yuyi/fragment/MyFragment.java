package com.doctor.yuyi.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.activity.My_forumPosts_Activity;
import com.doctor.yuyi.activity.My_message_Activity;
import com.doctor.yuyi.activity.My_patientDataList_Activity;
import com.doctor.yuyi.activity.My_praise_Activity;
import com.doctor.yuyi.activity.My_registration_Activity;
import com.doctor.yuyi.activity.My_setting_Activity;
import com.doctor.yuyi.activity.RongConversationList_Activity;
import com.doctor.yuyi.bean.Bean_UserInfo;
import com.doctor.yuyi.lzh_utils.RoundImageView;
import com.doctor.yuyi.lzh_utils.checkNotificationAllowed;
import com.doctor.yuyi.lzh_utils.okhttp;
import com.doctor.yuyi.lzh_utils.toast;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener{
    private ImageView my_image_setting,my_image_message;//设置，消息图标
    private RoundImageView my_image_photo;//头像

    private TextView my_textV_docName,my_textV_zhicheng;//姓名，职称

    private TextView my_textV_hosName,my_textV_ksName;//医院，科室名称

    //帖子，点赞，咨询，查看数据，挂号
    private RelativeLayout my_relative_tiezi,my_relative_dianzan,my_relative_zixun,my_relative_shuju,my_relative_guahao;
    public MyFragment() {
        // Required empty public constructor
    }
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(getActivity());
                    break;
                case 1:
                    try{
                        Bean_UserInfo info=okhttp.gson.fromJson(resStr,Bean_UserInfo.class);
                        if (info!=null&&"0".equals(info.getCode())){
                            if (!"".equals(info.getPhysician().getTrueName())){
                                my_textV_docName.setText(info.getPhysician().getTrueName());
                            }
                           else {
                                my_textV_docName.setText("姓名");
                            }

                            if (!"".equals(info.getPhysician().getTitle())){
                                my_textV_zhicheng.setText(info.getPhysician().getTitle());
                            }
                            else {
                                my_textV_zhicheng.setText("职称");
                            }

                            if (!"".equals(info.getPhysician().getHospitalName())){
                                my_textV_hosName.setText(info.getPhysician().getHospitalName());
                            }
                            else {
                                my_textV_hosName.setText("医院名称");
                            }

                            if (!"".equals(info.getPhysician().getDepartmentName())){
                                my_textV_ksName.setText(info.getPhysician().getDepartmentName());
                            }
                            else {
                                my_textV_ksName.setText("科室名称");
                            }

                            Picasso.with(getActivity()).load(Ip.URL+info.getPhysician().getAvatar()).error(R.mipmap.doc).into(my_image_photo);
                        }
                        else if (info!=null&&"-1".equals(info.getCode())){
//                            toast.toast_gsonFaild(getActivity());
                            my_image_photo.setImageResource(R.mipmap.usererr);
                            Toast.makeText(getActivity(), "您还没有在医院系统注册，无法获取个人信息", Toast.LENGTH_SHORT).show();
//                            Log.e("获取个人信息失败","---MyFragment---");
                        }
                        else {
                            toast.toast_gsonFaild(getActivity());
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        toast.toast_gsonFaild(getActivity());
                    }
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.from(getActivity()).inflate(R.layout.fragment_my,container,false);
        initView(v);
        getUserInfo();//获取个人信息
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkNotificationAllowed.isNOtificationOpen(getActivity()) == false) {//当用户没有通知栏权限时
            SharedPreferences preferences = getActivity().getSharedPreferences("NOTIFICATION", Context.MODE_APPEND);
            if (preferences.contains("notifi") == false) {//用户第一次点击修改权限弹窗时写入，用于判断是否显示跳转到权限修改到界面（true：用户之前已经进入过修改权限到页面，但不给予通知但权限，false：用户没有进入过）
                showWindowRevampLimit();
            }
        }
    }

    private void initView(View view) {
        my_image_setting= (ImageView) view.findViewById(R.id.my_image_setting);
        my_image_setting.setOnClickListener(this);

        my_image_message= (ImageView) view.findViewById(R.id.my_image_message);
        my_image_message.setOnClickListener(this);

        my_image_photo= (RoundImageView) view.findViewById(R.id.my_image_photo);
        my_image_photo.setOnClickListener(this);

        my_textV_docName= (TextView) view.findViewById(R.id.my_textV_docName);
        my_textV_zhicheng= (TextView) view.findViewById(R.id.my_textV_zhicheng);
        my_textV_hosName= (TextView) view.findViewById(R.id.my_textV_hosName);
        my_textV_ksName= (TextView) view.findViewById(R.id.my_textV_ksName);

    //----------------------帖子，点赞，咨询，查看数据，挂号预约---------------------------
        my_relative_tiezi= (RelativeLayout) view.findViewById(R.id.my_relative_tiezi);
        my_relative_tiezi.setOnClickListener(this);

        my_relative_dianzan= (RelativeLayout) view.findViewById(R.id.my_relative_dianzan);
        my_relative_dianzan.setOnClickListener(this);

        my_relative_zixun= (RelativeLayout) view.findViewById(R.id.my_relative_zixun);
        my_relative_zixun.setOnClickListener(this);

        my_relative_shuju= (RelativeLayout) view.findViewById(R.id.my_relative_shuju);
        my_relative_shuju.setOnClickListener(this);

        my_relative_guahao= (RelativeLayout) view.findViewById(R.id.my_relative_guahao);
        my_relative_guahao.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
            if (v!=null){
                switch (v.getId()){
                    case R.id.my_image_setting://设置
                        getActivity().startActivity(new Intent(getActivity(), My_setting_Activity.class));
                        break;

                    case R.id.my_image_message://消息
                            getActivity().startActivity(new Intent(getActivity(), My_message_Activity.class));
                        break;

                    case R.id.my_image_photo://头像
//                        startActivity(new Intent(getActivity(), UserInfo_Activity.class));
                        break;

                    case R.id.my_relative_tiezi://帖子
                        startActivity(new Intent(getActivity(), My_forumPosts_Activity.class));
                        break;

                    case R.id.my_relative_dianzan://点赞
                        startActivity(new Intent(getActivity(), My_praise_Activity.class));
                        break;

                    case R.id.my_relative_zixun://咨询
                        startActivity(new Intent(getActivity(), RongConversationList_Activity.class));
                        break;

                    case R.id.my_relative_shuju://数据
                        startActivity(new Intent(getActivity(), My_patientDataList_Activity.class));
                        break;

                    case R.id.my_relative_guahao://挂号
                        startActivity(new Intent(getActivity(), My_registration_Activity.class));
                        break;

                }
            }
    }

    //获取个人信息接口http://192.168.1.55:8080/yuyi/physician/get.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10
    public void getUserInfo() {
      Map<String,String>mp=new HashMap<>();
        mp.put("token",UserInfo.UserToken);
        okhttp.getCall(Ip.URL+Ip.interface_UserInfo,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                Log.i("获取个人信息---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }

    //跳转到修改权限页面到弹窗
    private void showWindowRevampLimit() {
        new AlertDialog.Builder(getActivity()).setTitle("应用通知栏权限被禁止").
                setMessage("无法接收到应用发送的相关通知，需要您手动打开通知权限，是否现在去打开？").setIcon(R.mipmap.logo).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().getSharedPreferences("NOTIFICATION", Context.MODE_APPEND).edit().putBoolean("notifi", true).commit();
                        checkNotificationAllowed.goToSet(getActivity());
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(true).show();
    }
}
