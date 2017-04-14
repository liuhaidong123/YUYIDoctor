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
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.SearchAdpter;
import com.doctor.yuyi.bean.BeanSearch;
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

//搜索患者
public class SearchActivity extends Activity {
    private TextView EmptyView;
    private MyListView searchac_listview;
    private EditText searchac_editext;
    private SearchAdpter adapter;
    private String content;
    private String resStr;
    private TextView listempty;
    //-----
    private RelativeLayout search_loadLayout;
    private TextView search_loadtext;
    private ProgressBar search_loadprogress;
    //-----
    private boolean isDb=true;//是否当前是查询数据库
    private List<BeanSearch.RowsBean>listDb;
    private List<BeanSearch.RowsBean>listNetWork;

    private LinearLayout searchac_layoutclear;
    private int st=0;
    private final int li=15;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    setLoadState(0);
                    search_loadLayout.setClickable(true);
                    search_loadLayout.setVisibility(View.GONE);
                    toast.toast_faild(SearchActivity.this);
                    break;
                case 1:
                    setLoadState(0);
                    search_loadLayout.setClickable(true);
                    search_loadLayout.setVisibility(View.GONE);
                    try{
                        BeanSearch search=okhttp.gson.fromJson(resStr,BeanSearch.class);
                        if (search!=null){
                            if (search.getRows()!=null&&search.getRows().size()>0){
                                    if (listNetWork!=null&&listNetWork.size()==0){//防止加载更多时往数据库一直添加数据
                                        boolean flag=DbUtils.insert(SearchActivity.this,content);
                                        if (flag){
                                            Log.i("添加搜索记录到本地数据库成功---","----成功--------");
//                                    Toast.makeText(SearchActivity.this,"搜索记录添加成功",Toast.LENGTH_SHORT).show();
                                        }else {
//                                    Toast.makeText(SearchActivity.this,"搜索记录添加失败",Toast.LENGTH_SHORT).show();
                                            Log.i("添加搜索记录到本地数据库失败---","-------是失败-----");
                                        }
                                    }
                                    listNetWork.addAll(search.getRows());
                                    st+=search.getRows().size();
                                    if (search.getRows().size()!=li){

                                    }
                                else {
                                        search_loadLayout.setVisibility(View.VISIBLE);
                                    }
                                        adapter.notifyDataSetChanged();
                            }
                            else {
                                toast.toast_gsonEmpty(SearchActivity.this);
                            }

                        }
                        else {
                            toast.toast_gsonEmpty(SearchActivity.this);
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(SearchActivity.this);
                        e.printStackTrace();
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

        searchac_layoutclear= (LinearLayout) findViewById(R.id.searchac_layoutclear);
        searchac_layoutclear.setVisibility(View.GONE);

        search_loadLayout= (RelativeLayout) findViewById(R.id.search_loadLayout);
        search_loadtext= (TextView) findViewById(R.id.search_loadtext);
        search_loadprogress= (ProgressBar) findViewById(R.id.search_loadprogress);
        search_loadLayout.setVisibility(View.GONE);
        search_loadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoadState(1);
                search_loadLayout.setClickable(false);
                search();
            }
        });


        searchac_listview= (MyListView) findViewById(R.id.searchac_listview);
        searchac_editext= (EditText) findViewById(R.id.searchac_editext);
        listDb=new ArrayList<>();//数据库的数据源
        adapter=new SearchAdpter(listDb,this);
        searchac_listview.setAdapter(adapter);
        listempty= (TextView) findViewById(R.id.listempty);
        searchac_listview.setEmptyView(listempty);


        searchac_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isDb){//当前显示的是db的数据（搜索记录）
                    isDb=false;
                    searchac_layoutclear.setVisibility(View.GONE);
                    search_loadLayout.setVisibility(View.GONE);
                    searchac_editext.setText(listDb.get(position).getTrueName());
                    listNetWork=new ArrayList<BeanSearch.RowsBean>();
                    adapter=new SearchAdpter(listNetWork,SearchActivity.this);
                    searchac_listview.setAdapter(adapter);
                    st=0;
                    getData(st,li);
                }
                else {//当前显示的是搜索到的患者
                    Intent intent=new Intent(SearchActivity.this,PatientMessageActivity.class);
                    intent.putExtra("humeuserId",listNetWork.get(position).getId());
                    startActivity(intent);
                    finish();
                }
            }
        });
        searchac_editext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            // || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (event.getAction()==KeyEvent.ACTION_UP){
                        searchac_layoutclear.setVisibility(View.GONE);
                        search_loadLayout.setVisibility(View.GONE);
                        isDb=false;
                        listNetWork=new ArrayList<BeanSearch.RowsBean>();
                        adapter=new SearchAdpter(listNetWork,SearchActivity.this);
                        searchac_listview.setAdapter(adapter);
                        st=0;//操作位重置
                        getData(st,li);
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }

                    return true;
                }
                return false;
            }
        });
            getDBdata(st,li);
    }

    private void search() {
        if (isDb){//当前是操作的数据库
            getDBdata(st,li);
        }
        else {
            getData(st,li);
        }
    }

    //取消按钮
    public void goBack(View view) {
        finish();
    }

        //清除搜索历史
    public void clear(View view) {
        Boolean flag=DbUtils.clearAll(this);
        if (flag){
            listDb.clear();
            searchac_layoutclear.setVisibility(View.GONE);
            search_loadLayout.setVisibility(View.GONE);
            Toast.makeText(SearchActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(SearchActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }





    //搜索http://192.168.1.55:8080/yuyi/homeuser/findAllUserList.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5&trueName=1
    public void getData(int start,int limit) {
     content=searchac_editext.getText().toString();
        if (!"".equals(content)&&!TextUtils.isEmpty(content)){
            Map<String,String> mp=new HashMap<>();
            mp.put("trueName",content);
            mp.put("token", UserInfo.UserToken);
            mp.put("start",start+"");
            mp.put("limit",limit+"");
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
            Toast.makeText(SearchActivity.this,"您输入的内容不正确",Toast.LENGTH_SHORT).show();
        }
    }


    public void getDBdata(int start,int limit){
        Cursor cur=DbUtils.selectAll(this,start,limit);
        if (cur!=null){
            if (cur.getCount()>0){
                st+=cur.getCount();
                if (cur.getCount()!=li){//数据库中所有数据查询完毕
                    Toast.makeText(SearchActivity.this,"加载完毕",Toast.LENGTH_SHORT).show();
                    search_loadLayout.setVisibility(View.GONE);
                }
                else {//数据库中还有数据可以执行加载更多
                    search_loadLayout.setVisibility(View.VISIBLE);
                }
                while (cur.moveToNext()){
                    BeanSearch.RowsBean bean=new BeanSearch.RowsBean();
                    bean.setTrueName(cur.getString(cur.getColumnIndex("content")));
                    listDb.add(bean);
//                    Log.i("listDb数据库长度--",listDb.size()+"");
                }
                adapter.notifyDataSetChanged();
            }
            else {
                if (listDb!=null&&listDb.size()==0){
//                    Toast.makeText(SearchActivity.this,"暂无数据",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SearchActivity.this,"没有更多记录了",Toast.LENGTH_SHORT).show();
                }
                search_loadLayout.setVisibility(View.GONE);
            }

        }
        else{
            Log.i("list数据库长度--","无法从本地数据库查询到数据");
            Toast.makeText(SearchActivity.this,"没有数据了",Toast.LENGTH_SHORT).show();
            search_loadLayout.setVisibility(View.GONE);
            }
        setLoadState(0);
        search_loadLayout.setClickable(true);
        if (listDb!=null&&listDb.size()>0){
            searchac_layoutclear.setVisibility(View.VISIBLE);
        }
    }

    public void setLoadState(int st){
        switch (st){
            case 0://加载更多
                search_loadprogress.setVisibility(View.GONE);
                search_loadtext.setText("加载更多");
                break;
            case 1://正在加载
                search_loadprogress.setVisibility(View.VISIBLE);
                search_loadtext.setText("正在加载");
                break;
        }
    }
}
