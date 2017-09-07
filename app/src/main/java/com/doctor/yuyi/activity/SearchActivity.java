package com.doctor.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.DataBase.DbUtils;
import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.MyUtils.MyDialog;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.SearchAdpter;
import com.doctor.yuyi.bean.BeanSearch;
import com.doctor.yuyi.lzh_utils.DataUtils;
import com.doctor.yuyi.lzh_utils.okhttp;
import com.doctor.yuyi.lzh_utils.toast;
import com.doctor.yuyi.myview.MyListV;
import com.doctor.yuyi.myview.MyListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//搜索患者
public class SearchActivity extends Activity implements MyListV.IScrollBottomListener {
    private MyListV searchac_listview;
    private EditText searchac_editext;
    TextView clearTextView;//清除按钮
    private SearchAdpter adapter;
    String content;//搜索的关键词
    private List<BeanSearch.RowsBean>list;
    String resStr;
    int start=0;
    int limit=10;
    boolean dataUseDB=true;//当前的数据源来自数据库（true）
    boolean flag=false;//正在请求数据(true)
    boolean canLoading=true;//服务器还有数据(true)
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    MyDialog.stopDia();
                    flag=false;//可以继续请求网络
                    searchac_listview.setEmty("您的网络有问题！");
                    break;
                case 1:
                    canLoading=false;
                    MyDialog.stopDia();
                    flag=false;//可以继续请求网络
                    try{
                        BeanSearch bean=okhttp.gson.fromJson(resStr,BeanSearch.class);
                        if (bean!=null){
                            List<BeanSearch.RowsBean>li=bean.getRows();
                            if (li!=null&&li.size()>0){
                                start+=li.size();
                                if (li.size()==limit){//服务器没有数据了
                                    canLoading=true;
                                }
                                list.addAll(li);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        searchac_listview.setEmty("没有查询到数据！");
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    private void initView() {
        clearTextView= (TextView) findViewById(R.id.clearTextView);
        clearTextView.setVisibility(View.GONE);
        searchac_listview= (MyListV) findViewById(R.id.searchac_listview);
        searchac_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (list!=null&&list.size()>0){
                        if (dataUseDB==false){//网络数据
                            BeanSearch.RowsBean be=list.get(position);
                            boolean success= DbUtils.insert(SearchActivity.this,be.getId()+"",be.getTrueName(),be.getAvatar());
                            if (success){
                                Log.e("插入数据成功：","SearchActivity:initView()");
                            }
                            else{
                                Log.e("插入数据Error：","SearchActivity:initView()");
                            }
                        }
                        Intent intent = new Intent(SearchActivity.this, PatientMessageActivity.class);
                        intent.putExtra("humeuserId", list.get(position).getId());
                        startActivity(intent);
                    }
            }
        });
        searchac_listview.setListener(this);//滚动到底部监听
        searchac_editext= (EditText) findViewById(R.id.searchac_editext);//搜索框输入按钮
        searchac_editext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if (keyCode==KeyEvent.KEYCODE_ENTER){//回车键
                    //隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    SearchActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    dataUseDB=false;
                    canLoading=true;//从新搜索时，重置
                    start=0;
                    list.clear();
                    adapter.notifyDataSetChanged();
                    getData(start,limit);
                }
                return false;
            }
        });
        getDBdata();
    }

    //索索按钮
    public void Search(View vi){
        dataUseDB=false;
        canLoading=true;//重新搜索时，重置
        start=0;
        list.clear();
        adapter.notifyDataSetChanged();
        getData(start,limit);
    }

    //取消按钮
    public void goBack(View view) {
        finish();
    }

    //搜索http://192.168.1.55:8080/yuyi/homeuser/findAllUserList.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5&trueName=1
    public void getData(int st,int lim) {
        if (flag){
            return;
        }
        else {
            flag=true;
        }
        if (canLoading==false){
            return;
        }
        MyDialog.showDialog(SearchActivity.this);
        clearTextView.setVisibility(View.GONE);
        content=searchac_editext.getText().toString();
        if (!"".equals(content)&&!TextUtils.isEmpty(content)){
            Map<String,String> mp=new HashMap<>();
            mp.put("trueName",content);
            mp.put("token", UserInfo.UserToken);
            mp.put("start",st+"");
            mp.put("limit",lim+"");
            okhttp.getCall(Ip.URL+Ip.interface_Searchpaint,mp,okhttp.OK_GET).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("搜索患者----",resStr);
                    handler.sendEmptyMessage(1);
                }
            });

        }
        else {
            Toast.makeText(SearchActivity.this,"您的输入不正确",Toast.LENGTH_SHORT).show();
        }
    }


    public void getDBdata() {
        list = new ArrayList<>();
        adapter=new SearchAdpter(list,this);
        searchac_listview.setAdapter(adapter);
        Cursor cur = DbUtils.selectAll(this, start, limit);
        if (cur != null) {
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    BeanSearch.RowsBean bean = new BeanSearch.RowsBean();
                    bean.setTrueName(cur.getString(cur.getColumnIndex("content")));
                    bean.setAvatar(cur.getString(cur.getColumnIndex("image")));
                    bean.setId(Long.parseLong(cur.getString(cur.getColumnIndex("userId"))));
                    list.add(bean);
                }
                    if (list!=null&&list.size()>0){
                        clearTextView.setVisibility(View.VISIBLE);
                    }
                    dataUseDB=true;
                    adapter.notifyDataSetChanged();
            }
        }
        searchac_listview.setEmty("没有搜索记录！");
    }
    //清除搜索记录
    public void Clear(View vi){
       boolean success= DbUtils.clearAll(SearchActivity.this);
        if (success){
            Toast.makeText(SearchActivity.this,"清除成功",Toast.LENGTH_SHORT).show();
            clearTextView.setVisibility(View.GONE);
            list.clear();
            adapter.notifyDataSetChanged();
            searchac_listview.setEmty("没有搜索记录！");
        }
        else {
            Toast.makeText(SearchActivity.this,"清除失败",Toast.LENGTH_SHORT).show();
        }
    }
    //滚动到底部监听
    @Override
    public void onScrollBottom() {
        Log.i("滑动到底部了","=============000==============");
       if (dataUseDB){
           return;
       }
        else {//使用到不是db的数据，请求加载更多
            Log.i("滑动到底部了","========111===================");
            if(canLoading){
                getData(start,limit);
                Log.i("滑动到底部了","========222===================");
            }

       }
    }
}
