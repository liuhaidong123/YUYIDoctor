package com.doctor.yuyi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.MyUtils.MyDialog;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.My_PraiseAdapter;
import com.doctor.yuyi.adapter.My_forumPosts_Adapter;
import com.doctor.yuyi.bean.Bean_MyPraise;
import com.doctor.yuyi.lzh_utils.MyActivity;
import com.doctor.yuyi.lzh_utils.MyEmptyListView;
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

public class My_praise_Activity extends MyActivity implements My_PraiseAdapter.notifi {
    private final Context con=My_praise_Activity.this;
    private MyEmptyListView my_praise_listview;
    private My_PraiseAdapter adapter;
    private String resStr;
    public  int start=0;
    private final int limit=10;
    private boolean isEnd;
    private List<Bean_MyPraise.RowsBean> list;
    //-------
    private RelativeLayout my_praise_loading_layout;
    private ProgressBar my_praise_loading_progress;
    private TextView my_praise_loding_text;
    //-------
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    my_praise_listview.setError();
                    my_praise_loading_layout.setClickable(true);
                    setLoading(1);
                    break;
                case 1:
                    my_praise_loading_layout.setClickable(true);
                    setLoading(1);
                    try{
                        Bean_MyPraise myPraise=okhttp.gson.fromJson(resStr,Bean_MyPraise.class);
                        if (  myPraise.getRows()!=null&&  myPraise.getRows().size()>0){
                            start+=myPraise.getRows().size();
                            list.addAll(myPraise.getRows());
                            adapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(my_praise_listview);
                            if (myPraise.getRows().size()<limit){
                                isEnd=true;
                                my_praise_loading_layout.setVisibility(View.GONE);
                            }
                            else {
                                isEnd=false;
                                my_praise_loading_layout.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            my_praise_loading_layout.setVisibility(View.GONE);
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        toast.toast_gsonFaild(con);
                    }
                    my_praise_listview.setEmpty();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_praise_);
        initView();
//        getPraiseData(start);
    }

    @Override
    public void initEmpty() {

    }

    private void initView() {
        my_praise_loading_layout= (RelativeLayout) findViewById(R.id.my_praise_loading_layout);
        my_praise_loading_progress= (ProgressBar) findViewById(R.id.my_praise_loading_progress);
        my_praise_loding_text= (TextView) findViewById(R.id.my_praise_loding_text);
        my_praise_loading_layout.setVisibility(View.GONE);
        my_praise_loading_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPraiseData(start);
            }
        });

        list=new ArrayList<>();
        titleTextView= (TextView) findViewById(R.id.titleinclude_textview);
        titleTextView.setText("我的点赞");
        my_praise_listview= (MyEmptyListView) findViewById(R.id.my_praise_listview);
        adapter=new My_PraiseAdapter(list,this,this);
        my_praise_listview.setAdapter(adapter);
        setListViewHeightBasedOnChildren(my_praise_listview);

        my_praise_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long liketype=list.get(position).getLikeType();
                Log.i("likeType----","--"+liketype);
                if (liketype==1L){//资讯
                    Intent intent=new Intent();
                    intent.setClass(My_praise_Activity.this,InformationMessageActivity.class);
                    intent.putExtra("id",list.get(position).getContentId());
                    Log.i("id---",list.get(position).getContentId()+"");
                    startActivity(intent);
                }
                else if (liketype==2L){//帖子
                    Intent intent=new Intent();
                    intent.setClass(My_praise_Activity.this,CardMessageActivity.class);
                    intent.putExtra("id",list.get(position).getContentId());
                    Log.i("id---",list.get(position).getId()+"");
                    startActivity(intent);
                }
            }
        });
    }

    public void getPraiseData(int star) {
//        MyDialog.showDialog(My_praise_Activity.this);
        Log.i("start----",star+"");
        my_praise_loading_layout.setClickable(false);
        setLoading(0);
        Map<String,String> m=new HashMap<>();
        m.put("token", UserInfo.UserToken);
        m.put("start",star+"");
        m.put("limit",limit+"");
        okhttp.getCall(Ip.URL+Ip.interface_MyPraise,m,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取我的点赞---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }

    public void setLoading(int state){
        switch (state){
            case 0://正在加载
                my_praise_loading_progress.setVisibility(View.VISIBLE);
                my_praise_loding_text.setText("正在加载。。。");
                break;
            case 1://加载更多
                my_praise_loading_progress.setVisibility(View.GONE);
                my_praise_loding_text.setText("加载更多");
                break;
        }
    }

    @Override
    protected void onResume() {
        start=0;
        list.clear();
        getPraiseData(start);
        super.onResume();
    }



    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    public void notifiDelete(Boolean flag) {
        start--;
        if (flag){//数据全部删除
            my_praise_listview.setEmpty();
        }
    }
}
