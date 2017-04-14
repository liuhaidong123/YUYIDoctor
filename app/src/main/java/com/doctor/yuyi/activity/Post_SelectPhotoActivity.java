package com.doctor.yuyi.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.Manifest;
import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.PostPhotoGridviewAdapter;
import com.doctor.yuyi.lzh_utils.BitMapUtils;
import com.doctor.yuyi.lzh_utils.MyActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/4/11.
 */

public class Post_SelectPhotoActivity extends MyActivity implements PostPhotoGridviewAdapter.SelectIn{
    private final int ResultCode=200;
    private GridView post_selectphoto_gridview;
    private List<String> list;
    private PostPhotoGridviewAdapter adapter;
    private List<Map<String,String>>listCursor;
    private TextView post_select_submit;//提交按钮
//    intent.putStringArrayListExtra("data",(ArrayList<String>) list);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_selectphoto);
        list=getIntent().getStringArrayListExtra("data");
        initView();
        checkPerm();
    }

    @Override
    public void initEmpty() {

    }

    private void checkPerm() {
        if (Build.VERSION.SDK_INT>=23){
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
            } else{
              setdata();
            }
        }
        else {
            setdata();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==10){
            if (grantResults!=null&&grantResults.length>0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED||grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    setdata();
                }
                else {
                    Toast.makeText(Post_SelectPhotoActivity.this,"请您手动打开存储权限",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(Post_SelectPhotoActivity.this,"请您手动打开存储权限",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void initView() {
        post_select_submit= (TextView) findViewById(R.id.post_select_submit);
        post_selectphoto_gridview= (GridView) findViewById(R.id.post_selectphoto_gridview);
        listCursor=new ArrayList<>();
        adapter=new PostPhotoGridviewAdapter(listCursor,Post_SelectPhotoActivity.this,Post_SelectPhotoActivity.this);
        post_selectphoto_gridview.setAdapter(adapter);
    }
    public void setdata(){
        if (BitMapUtils.getCursor(Post_SelectPhotoActivity.this)!=null&&BitMapUtils.getCursor(Post_SelectPhotoActivity.this).size()>0){
            listCursor.addAll(BitMapUtils.getCursor(this));
        }
        if (list!=null&&list.size()>0){
            post_select_submit.setText("完成("+list.size()+"/6)");
            if (listCursor!=null&&listCursor.size()>0){
                for (int i=0;i<list.size();i++){
                    for (int j=0;j<listCursor.size();j++){
                        if (listCursor.get(j).get("url").equals(list.get(i))){
                            listCursor.get(j).put("select","1");
                        }
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void select(int count) {
        if (count!=0){
            post_select_submit.setText("完成("+count+"/6)");
        }
        else {
            post_select_submit.setText("完成");
        }
    }

    public void back(View view) {
        finish();
    }

    //提交按钮
    public void select(View view) {
        list=new ArrayList<>();
        if (listCursor!=null&&listCursor.size()>0){
            for (int i=0;i<listCursor.size();i++){
                if ("1".equals(listCursor.get(i).get("select"))){
                    list.add(listCursor.get(i).get("url"));
                }
            }
        }
        Intent intent=new Intent();
        intent.putStringArrayListExtra("data", (ArrayList<String>) list);
        setResult(ResultCode,intent);
        finish();
    }
}
