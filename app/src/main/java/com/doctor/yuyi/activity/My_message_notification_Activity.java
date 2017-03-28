package com.doctor.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.My_message_listViewAdapter;
import com.doctor.yuyi.adapter.My_message_notifi_listAdapter;
import com.doctor.yuyi.bean.Bean_Test_My_message;
import com.doctor.yuyi.lzh_utils.MyActivity;

import java.util.ArrayList;
import java.util.List;

public class My_message_notification_Activity extends MyActivity {
    private ListView my_message_notifi_listview;
    private List<Bean_Test_My_message> list;
    private My_message_notifi_listAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message_notification_);
        initView();
        initData();
    }

    private void initData() {
        list=new ArrayList<>();
        String []title=new String[]{"每日精选","热门讨论","更新通知"};
        String[] msg=new String[]{"Nature：核磁共振让自闭症早期诊断成为可能，自闭症又称孤独症，是一种脑部发育障碍引起的疾病。社会交往障碍，无法与大众融合，缺少交流沟通",
        "组织固定后，是否还能够提取固定细胞，并进行流式分选（皮肤及皮下组织）",getResources().getString(R.string.my_message_notifi_title)};
        String[]tim=new String[]{"8:20","10:50","16:50"};
        String []imgPth=new String[]{"http://scimg.jb51.net/touxiang/201703/2017032611250192.jpg","http://img01.skqkw.cn:888/touxiang/2014/06/16/15/20140616155026101610.jpg",
                "http://img4.imgtn.bdimg.com/it/u=1483569741,1992390913&fm=23&gp=0.jpg"};
        for (int i=0;i<3;i++){
            Bean_Test_My_message ms=new Bean_Test_My_message(imgPth[i],title[i],msg[i],tim[i]);
            list.add(ms);
        }
        adapter=new My_message_notifi_listAdapter(this,list);
        my_message_notifi_listview.setAdapter(adapter);
    }

    private void initView() {
        titleTextView= (TextView) findViewById(R.id.titleinclude_textview);
        titleTextView.setText("宇医公告");
        my_message_notifi_listview= (ListView) findViewById(R.id.my_message_notifi_listview);
    }
}
