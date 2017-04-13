package com.doctor.yuyi.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.DataBase.DbUtils;
import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.SearchAdpter;
import com.doctor.yuyi.bean.BeanSearch;
import com.doctor.yuyi.lzh_utils.DataUtils;
import com.doctor.yuyi.lzh_utils.MyActivity;
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
    private MyListView searchac_listview;
    private EditText searchac_editext;
    private SearchAdpter adapter;
    private String content;
    private String resStr;
    private List<BeanSearch.RowsBean>list;
    private int st=0;
            private final int li=2;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(SearchActivity.this);
                    break;
                case 1:
                    try{
                        BeanSearch search=okhttp.gson.fromJson(resStr,BeanSearch.class);
                        if (search!=null){
                            list=search.getRows();
                            if (list!=null&&list.size()>0){
                                adapter=new SearchAdpter(list,SearchActivity.this);
                                searchac_listview.setAdapter(adapter);
                                if ("".equals(content)||TextUtils.isEmpty(content)){
                                    content="空的";
                                }
                                Log.i("content---",content);
                                boolean flag=DbUtils.insert(SearchActivity.this,content);
                                if (flag){
                                    Toast.makeText(SearchActivity.this,"搜索记录添加成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(SearchActivity.this,"搜索记录添加失败",Toast.LENGTH_SHORT).show();
                                }
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
        list=new ArrayList<>();
        initView();
    }

    private void initView() {
        searchac_listview= (MyListView) findViewById(R.id.searchac_listview);
        searchac_editext= (EditText) findViewById(R.id.searchac_editext);
        searchac_editext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            // || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (event.getAction()==KeyEvent.ACTION_UP){
                        getData(st,li);
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }

                    return true;
                }
                return false;
            }
        });
        Cursor cur=DbUtils.selectAll(this);
        if (cur!=null){
            while (cur.moveToNext()){
                BeanSearch.RowsBean bean=new BeanSearch.RowsBean();
                bean.setTrueName(cur.getString(cur.getColumnIndex("content")));
                list.add(bean);
            }
            searchac_listview.setAdapter(adapter);
        }
    }

    //取消按钮
    public void goBack(View view) {
        finish();
    }

        //清除搜索历史
    public void clear(View view) {

    }





    //搜索http://192.168.1.55:8080/yuyi/homeuser/findAllUserList.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5&trueName=1
    public void getData(int start,int limit) {
     content=searchac_editext.getText().toString();
        if (!"".equals(content)&&!TextUtils.isEmpty(content)){
            Map<String,String> mp=new HashMap<>();
            mp.put("content",content);
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
}
