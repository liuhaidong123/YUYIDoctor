package com.doctor.yuyi.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.MyUtils.MyDialog;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.My_message_notifi_listAdapter;
import com.doctor.yuyi.bean.Bean_MyMessage_Notifi;
import com.doctor.yuyi.lzh_utils.ListVIewUtils;
import com.doctor.yuyi.lzh_utils.MyActivity;
import com.doctor.yuyi.lzh_utils.MyEmptyListView;
import com.doctor.yuyi.lzh_utils.okhttp;
import com.doctor.yuyi.lzh_utils.toast;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_message_notification_Activity extends MyActivity {
    private MyEmptyListView my_message_notifi_listview;
    private List<Bean_MyMessage_Notifi.RowsBean> list;
    private My_message_notifi_listAdapter adapter;
    private final int limit = 10;
    private int start = 0;
    private String resStr;
    private RelativeLayout my_message_notifi_loading_layo;
    private TextView my_message_notifi_loading_text;
    private ProgressBar my_message_notifi_loading_progress;
    private final Context context = My_message_notification_Activity.this;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    MyDialog.stopDia();
                    my_message_notifi_loading_layo.setClickable(true);
                    my_message_notifi_loading_layo.setVisibility(View.VISIBLE);
                    setPro(1);
                    my_message_notifi_listview.setError();
//                    toast.toast_faild(context);
                    break;
                case 1:
                    MyDialog.stopDia();
                    my_message_notifi_loading_layo.setClickable(true);
                    my_message_notifi_loading_layo.setVisibility(View.GONE);
                    setPro(1);
                    try {
                        Bean_MyMessage_Notifi noti = okhttp.gson.fromJson(resStr, Bean_MyMessage_Notifi.class);
                        if (noti != null) {
                            if (noti.getRows() != null && noti.getRows().size() > 0) {
                                list.addAll(noti.getRows());
                                adapter.notifyDataSetChanged();
                                ListVIewUtils.setListViewHeightBasedOnChildren(my_message_notifi_listview);
                                start += noti.getRows().size();
                                if (noti.getRows().size() != limit) {
                                    my_message_notifi_loading_layo.setVisibility(View.GONE);
                                } else {
                                    my_message_notifi_loading_layo.setVisibility(View.VISIBLE);
                                }
                            } else {
                                toast.toast_gsonEmpty(context);
                            }
                        } else {
                            toast.toast_gsonEmpty(context);
                        }
                    } catch (Exception e) {
                        toast.toast_gsonFaild(context);
                    }
                    my_message_notifi_listview.setEmpty();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message_notification_);
        initView();
        initData();
        getMessage(start, limit);
    }

    @Override
    public void initEmpty() {

    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new My_message_notifi_listAdapter(this, list);
        my_message_notifi_listview.setAdapter(adapter);
        ListVIewUtils.setListViewHeightBasedOnChildren(my_message_notifi_listview);
    }

    private void initView() {
        titleTextView = (TextView) findViewById(R.id.titleinclude_textview);
        titleTextView.setText("宇医公告");
        my_message_notifi_listview = (MyEmptyListView) findViewById(R.id.my_message_notifi_listview);
        my_message_notifi_loading_layo = (RelativeLayout) findViewById(R.id.my_message_notifi_loading_layout);
        my_message_notifi_loading_text = (TextView) findViewById(R.id.my_message_notifi_loading_text);
        my_message_notifi_loading_progress = (ProgressBar) findViewById(R.id.my_message_notifi_loading_progress);

        my_message_notifi_loading_layo.setVisibility(View.GONE);
        my_message_notifi_loading_layo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPro(0);
                getMessage(start, limit);
            }
        });
    }

    public void getMessage(int st, int lim) {
        MyDialog.showDialog(My_message_notification_Activity.this);
        my_message_notifi_loading_layo.setClickable(false);
        Map<String, String> mp = new HashMap<>();
        mp.put("token", UserInfo.UserToken);
        mp.put("start", st + "");
        mp.put("limit", lim + "");
        okhttp.getCall(Ip.URL + Ip.interface_MyMessageList, mp, okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("获取消息列表---", resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }

    private void setPro(int state) {
        switch (state) {
            case 0://正在加载的时候
                my_message_notifi_loading_progress.setVisibility(View.VISIBLE);
                my_message_notifi_loading_text.setText("正在加载。。。");
                break;
            case 1://加载更多的时候
                my_message_notifi_loading_progress.setVisibility(View.GONE);
                my_message_notifi_loading_text.setText("加载更多");
                break;
        }
    }

}
