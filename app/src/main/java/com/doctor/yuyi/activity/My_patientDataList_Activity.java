package com.doctor.yuyi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.MyUtils.MyDialog;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.My_paintDataList_Adapter;
import com.doctor.yuyi.bean.Bean_MyPaintList;
import com.doctor.yuyi.lzh_utils.MyActivity;
import com.doctor.yuyi.lzh_utils.MyNorEmptyListVIew;
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

//患者数据
public class My_patientDataList_Activity extends MyActivity {
    private final Context con = My_patientDataList_Activity.this;
    private MyNorEmptyListVIew my_listview;
    private My_paintDataList_Adapter adapter;
    private int start = 0;
    private final int limit = 15;
    private String resStr;
    private List<Bean_MyPaintList.RowsBean> list;
    //-------
    private RelativeLayout my_paintlist_loading_laout;
    private TextView my_paintlist_loading_text;
    private ProgressBar my_paintlist_loading_progress;
    //-------
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    MyDialog.stopDia();
                    my_listview.setError();
                    toast.toast_faild(con);
                    my_paintlist_loading_laout.setClickable(true);
                    setLoading(1);
                    break;
                case 1:
                    MyDialog.stopDia();
                    my_paintlist_loading_laout.setClickable(true);
                    setLoading(1);
                    try {
                        Bean_MyPaintList paintList = okhttp.gson.fromJson(resStr, Bean_MyPaintList.class);
                        if (paintList != null) {
                            if (paintList.getRows() != null && paintList.getRows().size() > 0) {
                                if (paintList.getRows().size() != limit) {//请求到到数据达不到分页标准,已经加载所有数据
                                    my_paintlist_loading_laout.setVisibility(View.GONE);
                                } else {
                                    my_paintlist_loading_laout.setVisibility(View.VISIBLE);
                                }
                                list.addAll(paintList.getRows());
                                start += paintList.getRows().size();
                                adapter.notifyDataSetChanged();
                            } else {
//                                toast.toast_gsonEmpty(con);
                            }
                        } else {
//                            toast.toast_gsonEmpty(con);
                        }
                    } catch (Exception e) {
//                        toast.toast_gsonFaild(con);
                    }
                    my_listview.setEmpty();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patient_data_list_);
        initView();
        getDataList(start, limit);

    }

    @Override
    public void initEmpty() {

    }

    private void initView() {
        my_listview = (MyNorEmptyListVIew) findViewById(R.id.my_patientList_listview);
        list = new ArrayList<>();
        adapter = new My_paintDataList_Adapter(this, list);
        my_listview.setAdapter(adapter);

        my_paintlist_loading_laout = (RelativeLayout) findViewById(R.id.my_paintlist_loading_laout);
        my_paintlist_loading_text = (TextView) findViewById(R.id.my_paintlist_loading_text);
        my_paintlist_loading_progress = (ProgressBar) findViewById(R.id.my_paintlist_loading_progress);

        my_paintlist_loading_laout.setVisibility(View.GONE);
        my_paintlist_loading_laout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataList(start, limit);
            }
        });
        setLoading(1);


        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(My_patientDataList_Activity.this, PatientMessageActivity.class);
                intent.putExtra("humeuserId", list.get(position).getId());
                startActivity(intent);
            }
        });
    }


    //搜索按钮的点击事件
    public void Search(View view) {
        if (view != null) {
            startActivity(new Intent(My_patientDataList_Activity.this, SearchActivity.class));
        }
    }

    //获取患者列表http://192.168.1.55:8080/yuyi/homeuser/findAllUserList.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5
    public void getDataList(int st, int lim) {
        MyDialog.showDialog(My_patientDataList_Activity.this);
        setLoading(0);
        my_paintlist_loading_laout.setClickable(false);
        Map<String, String> mp = new HashMap<>();
        mp.put("token", UserInfo.UserToken);
        mp.put("start", st + "");
        mp.put("limit", lim + "");
        okhttp.getCall(Ip.URL + Ip.interface_MyPaintList, mp, okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("获取我的患者列表返回----", resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }


    public void setLoading(int state) {
        switch (state) {
            case 0://正在加载
                my_paintlist_loading_progress.setVisibility(View.VISIBLE);
                my_paintlist_loading_text.setText("正在加载。。。");
                break;
            case 1:
                my_paintlist_loading_progress.setVisibility(View.GONE);
                my_paintlist_loading_text.setText("加载更多");
                break;
        }
    }
}
