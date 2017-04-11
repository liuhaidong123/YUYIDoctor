package com.doctor.yuyi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.My_message_listViewAdapter;
import com.doctor.yuyi.bean.Bean_MyMessage;
import com.doctor.yuyi.bean.Bean_MyMessageRead;
import com.doctor.yuyi.bean.Bean_Test_My_message;
import com.doctor.yuyi.lzh_utils.MyActivity;
import com.doctor.yuyi.lzh_utils.RoundImageView;
import com.doctor.yuyi.lzh_utils.okhttp;
import com.doctor.yuyi.lzh_utils.toast;
import com.doctor.yuyi.myview.MyListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_message_Activity extends MyActivity {
    private final Context con=My_message_Activity.this;
    private MyListView my_message_listview;
    private RoundImageView my_message_titleImage;//标题图
    private TextView my_message_notifi_time,my_message_notifi_name,my_message_notifi_msg;//标题时间,标题，标题内容
    private My_message_listViewAdapter adapter;
    private int unReadId=-1;
    private final int limit=1;
    private int start=0;
    private String resStr;
    private List<Bean_MyMessage.RowsBean>list;
    //------
    private RelativeLayout my_message_loading_layout;
    private TextView my_message_loading_text;
    private ProgressBar my_message_loading_progress;
    //------
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(con);
                    my_message_loading_layout.setClickable(true);
                    setLoading(1);
                    break;
                case 1:
                    my_message_loading_layout.setVisibility(View.GONE);
                    my_message_loading_layout.setClickable(true);
                    setLoading(1);
                    try{
                        Bean_MyMessage myMessage= okhttp.gson.fromJson(resStr,Bean_MyMessage.class);
                        if (myMessage!=null){
                            myMessage.getRows();
                            if (myMessage.getRows()!=null&&myMessage.getRows().size()>0){
                                start+=myMessage.getRows().size();
                                if (myMessage.getRows().size()!=limit){//分页数据不足，数据已经请求完毕
                                    my_message_loading_layout.setVisibility(View.GONE);
                                }
                                else {//服务器还有数据
                                    my_message_loading_layout.setVisibility(View.VISIBLE);
                                }
                                list.addAll(myMessage.getRows());
                                adapter.notifyDataSetChanged();
                            }
                            else {
                                toast.toast_gsonEmpty(con);
                            }
                        }
                        else {
                            toast.toast_gsonEmpty(con);
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(con);
                    }
                    break;
                case 2:
                    try{
                        Bean_MyMessageRead myMessageRead=okhttp.gson.fromJson(resStr,Bean_MyMessageRead.class);
                        if ("0".equals(myMessageRead.getCode())){
                            Log.i("消息设置已读成功","--messageid=="+unReadId);
                            list.get(unReadId).setIsRead(true);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    catch (Exception e){
                        toast.toast_faild(con);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message_);
        initView();
        initData();
        getMessage(start,limit);
    }

    private void initData() {
        list=new ArrayList<>();
        adapter=new My_message_listViewAdapter(this,list);
        my_message_listview.setAdapter(adapter);
        my_message_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).isIsRead()==false){
                    setUnRead(position);
                }
             int msgType=list.get(position).getMsgType();
               switch (msgType){  //消息类型--1=宇医公告，2=挂号通知，3=点赞资讯，4=点赞帖子，5=点赞帖子评论
                   case 1://公告页面
                       startActivity(new Intent(con,My_message_notification_Activity.class));
                       break;
                   case 2:
                       //不做处里
                       break;
                   case 3:
//                       咨询详情页面
                       break;
                   case 4:
                       //帖子详情页面
                       break;
                   case 5:
                       //帖子评论页面
                       break;
               }

            }
        });
    }

    private void initView() {
        my_message_listview= (MyListView) findViewById(R.id.my_message_listview);
        titleTextView= (TextView) findViewById(R.id.titleinclude_textview);
        titleTextView.setText("消息");

        my_message_loading_layout= (RelativeLayout) findViewById(R.id.my_message_loading_layout);
        my_message_loading_text= (TextView) findViewById(R.id.my_message_loading_text);
        my_message_loading_progress= (ProgressBar) findViewById(R.id.my_message_loading_progress);

        my_message_loading_layout.setVisibility(View.GONE);
        setLoading(1);
        my_message_loading_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoading(0);
                getMessage(start,limit);
            }
        });
    }


//获取消息列表http://192.168.1.55:8080/yuyi/messagePhysician/findPage.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5
    public void getMessage(int st,int lim){
        my_message_loading_layout.setClickable(false);
        Map<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.testToken);
        mp.put("start",st+"");mp.put("limit",lim+"");
        okhttp.getCall(Ip.URL+Ip.interface_MyMessageList,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取消息列表---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }

    //http://192.168.1.55:8080/yuyi/messagePhysicianLog/save.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&messageId=1
    public void setUnRead(int position){//设置已读消息
        unReadId=position;
        Map<String,String>mp=new HashMap<>();
        mp.put("token","EA62E69E02FABA4E4C9A0FDC1C7CAE10");
        mp.put("messageId",list.get(position).getId()+"");
        okhttp.getCall(Ip.URL+Ip.interface_MyMessageRead,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("设置已读消息---",resStr);
                handler.sendEmptyMessage(2);
            }
        });
    }

    public void setLoading(int state){
        switch (state){
            case 0://正在加载
                my_message_loading_text.setText("正在加载。。。");
                my_message_loading_progress.setVisibility(View.VISIBLE);
                break;
            case 1:
                my_message_loading_text.setText("加载更多");
                my_message_loading_progress.setVisibility(View.GONE);
                break;
        }
    }
}
