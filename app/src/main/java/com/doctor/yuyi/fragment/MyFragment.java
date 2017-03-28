package com.doctor.yuyi.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.activity.My_forumPosts_Activity;
import com.doctor.yuyi.activity.My_message_Activity;
import com.doctor.yuyi.activity.My_patientDataList_Activity;
import com.doctor.yuyi.activity.My_praise_Activity;
import com.doctor.yuyi.activity.My_registration_Activity;
import com.doctor.yuyi.activity.My_setting_Activity;
import com.doctor.yuyi.lzh_utils.RoundImageView;


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.from(getActivity()).inflate(R.layout.fragment_my,container,false);
        initView(v);
        return v;
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
                        break;

                    case R.id.my_relative_tiezi://帖子
                        startActivity(new Intent(getActivity(), My_forumPosts_Activity.class));
                        break;

                    case R.id.my_relative_dianzan://点赞
                        startActivity(new Intent(getActivity(), My_praise_Activity.class));
                        break;

                    case R.id.my_relative_zixun://咨询

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
}
