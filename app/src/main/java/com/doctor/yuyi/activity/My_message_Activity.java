package com.doctor.yuyi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.My_message_listViewAdapter;
import com.doctor.yuyi.bean.Bean_Test_My_message;
import com.doctor.yuyi.lzh_utils.MyActivity;
import com.doctor.yuyi.lzh_utils.RoundImageView;
import com.doctor.yuyi.myview.MyListView;

import java.util.ArrayList;
import java.util.List;

public class My_message_Activity extends MyActivity {
    private MyListView my_message_listview;
    private RoundImageView my_message_titleImage;//标题图
    private TextView my_message_notifi_time,my_message_notifi_name,my_message_notifi_msg;//标题时间,标题，标题内容
    private List<Bean_Test_My_message> list;
    private My_message_listViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message_);
        initView();
        initData();
    }

    private void initData() {
        list=new ArrayList<>();
        String []title=new String[]{"LIM","ZY","LHD","JDC"};
        String[] msg=new String[]{"点赞了你的帖子\"共享脑颅CT--广泛性低密度性\"","点赞了你的帖子\"共享脑颅CT--广泛性低密度性\"","点赞了你的帖子\"闪亮的赞了你一下\"","赞了你的评论\"重度缺血缺氧性脑病，小脑密度正常考察范围不得低于规定值\""};
        String[]tim=new String[]{"8:00","10:10","12:50","17:20"};
        String []imgPth=new String[]{"http://scimg.jb51.net/touxiang/201703/2017032611250192.jpg","http://img01.skqkw.cn:888/touxiang/2014/06/16/15/20140616155026101610.jpg",
                "http://img2.imgtn.bdimg.com/it/u=1071549289,969751588&fm=23&gp=0.jpg","http://img4.imgtn.bdimg.com/it/u=1483569741,1992390913&fm=23&gp=0.jpg"};
        for (int i=0;i<4;i++){
            Bean_Test_My_message ms=new Bean_Test_My_message(imgPth[i],title[i],msg[i],tim[i]);
            list.add(ms);
        }
        adapter=new My_message_listViewAdapter(this,list);
        my_message_listview.setAdapter(adapter);
    }

    private void initView() {
        my_message_listview= (MyListView) findViewById(R.id.my_message_listview);
        titleTextView= (TextView) findViewById(R.id.titleinclude_textview);
        titleTextView.setText("消息");
        my_message_titleImage= (RoundImageView) findViewById(R.id.my_message_titleImage);
        my_message_notifi_time= (TextView) findViewById(R.id.my_message_notifi_time);
        my_message_notifi_name= (TextView) findViewById(R.id.my_message_notifi_name);
        my_message_notifi_msg= (TextView) findViewById(R.id.my_message_notifi_msg);
    }

    //进入宇医通知
    public void getNotifi(View view) {
//        my_message_notifi
        startActivity(new Intent(this,My_message_notification_Activity.class));

    }
}
