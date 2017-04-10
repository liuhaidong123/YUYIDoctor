package com.doctor.yuyi.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.My_forumPosts_Adapter;
import com.doctor.yuyi.bean.Bean_MyPostData;
import com.doctor.yuyi.lzh_utils.MyActivity;
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

//我的帖子
public class My_forumPosts_Activity extends MyActivity {
    private final Context con=My_forumPosts_Activity.this;
    private ListView my_forum_posts_listview;
    private My_forumPosts_Adapter adapter;
    private   List<Bean_MyPostData.RowsBean> list;
    private int start=0;
    private int limit=2;
    private boolean isEnd;//查询结束（已经不能加载更多的帖子）
    private String resStr;
    //---------
    private RelativeLayout my_forum_post_loadinglayout;
    private TextView my_forum_post_loading_textv;
    private ProgressBar my_forum_post_progress;
    //---------
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    my_forum_post_loadinglayout.setClickable(true);
                    setPro(1);
                    toast.toast_faild(con);
                    break;
                case 1:
                    my_forum_post_loadinglayout.setClickable(true);
                    setPro(1);
                try{
                    Bean_MyPostData postData=okhttp.gson.fromJson(resStr,Bean_MyPostData.class);
                    if (postData!=null&&postData.getRows()!=null&&postData.getRows().size()>0){
                        list.addAll(postData.getRows());
                        start+=postData.getRows().size();
                        if (postData.getRows().size()!=limit){//返回的不够10条时，说明数据库已经查询到了所有的数据，不能再次请求
                            isEnd=true;
                            my_forum_post_loadinglayout.setVisibility(View.GONE);
                        }
                        else {
                            isEnd=false;
                            my_forum_post_loadinglayout.setVisibility(View.VISIBLE);

                        }
                        if (list!=null&&list.size()>0){
                            adapter=new My_forumPosts_Adapter(list,con);
                            my_forum_posts_listview.setAdapter(adapter);
                        }
                        else {
                            toast.toast_gsonEmpty(con);
                            my_forum_post_loadinglayout.setVisibility(View.GONE);
                        }
                    }
                    else {
                        toast.toast_gsonEmpty(con);
                        my_forum_post_loadinglayout.setVisibility(View.GONE);
                    }
                        }
                    catch (Exception e){
                            toast.toast_gsonFaild(con);
                            e.printStackTrace();
                                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_forum_posts_);
        initView();
        getPostData(start);//获取我的帖子
    }

    private void initView() {
        list=new ArrayList<>();
        my_forum_posts_listview= (ListView) findViewById(R.id.my_forum_posts_listview);
        my_forum_post_loadinglayout= (RelativeLayout) findViewById(R.id.my_forum_post_loadinglayout);
        my_forum_post_loading_textv= (TextView) findViewById(R.id.my_forum_post_loading_textv);
        my_forum_post_progress= (ProgressBar) findViewById(R.id.my_forum_post_progress);
        my_forum_post_loadinglayout.setVisibility(View.GONE);
        setPro(1);
        my_forum_post_loadinglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPostData(start);
            }
        });
    }


    //获取我的帖子
    public void getPostData(int st) {
        Log.i("当前start===",st+"");
        my_forum_post_loadinglayout.setClickable(false);
        setPro(0);
        Map<String,String> mp=new HashMap<>();
        mp.put("token", UserInfo.testToken);
        mp.put("start",st+"");
        mp.put("limit",""+limit);
        okhttp.getCall(Ip.URL+Ip.interface_MyPostData,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                Log.i("获取我的帖子---",resStr);
                    handler.sendEmptyMessage(1);
            }
        });
    }

    private  void setPro(int state){
        switch (state){
            case 0://正在加载的时候
                my_forum_post_progress.setVisibility(View.VISIBLE);
                my_forum_post_loading_textv.setText("正在加载。。。");
                break;
            case 1://加载更多的时候
                my_forum_post_progress.setVisibility(View.GONE);
                my_forum_post_loading_textv.setText("加载更多");
                break;
        }
    }
}
