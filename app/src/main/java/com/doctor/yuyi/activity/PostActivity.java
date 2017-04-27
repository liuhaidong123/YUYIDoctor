package com.doctor.yuyi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.MyUtils.MyDialog;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.adapter.PostListAdapter;
import com.doctor.yuyi.bean.Bean_PostIn;
import com.doctor.yuyi.lzh_utils.BitMapUtils;
import com.doctor.yuyi.lzh_utils.okhttp;
import com.doctor.yuyi.lzh_utils.toast;
import com.doctor.yuyi.myview.MyListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
//发帖页面
public class PostActivity extends AppCompatActivity {
    private final int RequestCode = 100;
    private final int ResultCode = 200;
    private EditText post_Edi_title;
    private EditText post_Edi_content;
    private TextView post_submit;//发布
    private ImageView mAdd_img;
    private MyListView img_listview;
    private List<String> list;
    private PostListAdapter adapter;
    private int selectPos;
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    post_submit.setClickable(true);
                    MyDialog.stopDia();
                    break;
                case 1:
                    post_submit.setClickable(true);
                    MyDialog.stopDia();
                    try{
                        Bean_PostIn postIn= okhttp.gson.fromJson(resStr,Bean_PostIn.class);
                        if (postIn!=null){
                            if ("0".equals(postIn.getCode())){
                                Toast.makeText(PostActivity.this,"帖子发布成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            else if ("-1".equals(postIn)){
                                Toast.makeText(PostActivity.this,"发帖失败：您没有发帖的权限",Toast.LENGTH_SHORT).show();
                            }
                            else if ("10000".equals(postIn)){
                                Toast.makeText(PostActivity.this,"发帖失败，请重新登陆",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(PostActivity.this,"发帖失败："+postIn.getResult(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(PostActivity.this,"发帖失败，请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(PostActivity.this);
                        e.printStackTrace();
                    }
                    break;
//                {"result":"帖子保存成功！","code":"0"}
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initView();
    }

    private void initView() {
        img_listview = (MyListView) findViewById(R.id.img_listview);
        list = new ArrayList<>();
        adapter = new PostListAdapter(this, list);
        img_listview.setAdapter(adapter);
        img_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDelete(position);
            }
        });
        post_Edi_content= (EditText) findViewById(R.id.post_Edi_content);
        post_Edi_title= (EditText) findViewById(R.id.post_Edi_title);

        post_submit= (TextView) findViewById(R.id.post_submit);
        post_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });
    }

    //删除已经选择的图片
    private void showDelete(int pos) {
        selectPos = pos;
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("删除图片？").setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(selectPos);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public void SelectImg(View view) {
        if (list != null) {
                Intent intent = new Intent();
                intent.setClass(PostActivity.this, Post_SelectPhotoActivity.class);
                intent.putStringArrayListExtra("data", (ArrayList<String>) list);
                startActivityForResult(intent, RequestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCode) {
            if (requestCode == RequestCode) {
//                intent.putStringArrayListExtra("data", (ArrayList<String>) list);
                if (data != null) {
                    List<String> li = data.getStringArrayListExtra("data");
                    if (li!=null){
                        Log.i("------",li.size()+"");
                        if (li.size()>0){
                            LinkedHashSet<String>hashSet=new LinkedHashSet<>();
                            hashSet.addAll(list);
                            hashSet.addAll(li);
                            list.clear();
                            for (String s:hashSet){
                                for (int i=0;i<li.size();i++){
                                    if (s.equals(li.get(i))){
                                        list.add(s);
                                    }
                                }

                            }
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            list.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
            }
        }

    }
    public void clearList(){
        if(list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++){
                list.remove(i);
            }
        }
    }

    //提交发帖信息http://192.168.1.55:8080/yuyi/academicpaper/AddAcademicpaper.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&title=test&content=test&images=
    public void Submit() {
        post_submit.setClickable(false);
            List<File>li=new ArrayList<>();
//        post_Edi_title.setText("标题");
//        post_Edi_content.setText("内容");
            String title=post_Edi_title.getText().toString();
            String content=post_Edi_content.getText().toString();

            if (!"".equals(title)&&!TextUtils.isEmpty(title)
                    &&!"".equals(content)&&!TextUtils.isEmpty(content)){
                if (content.length()>1000){
                    Toast.makeText(PostActivity.this,"发帖内容不能大于1000个字",Toast.LENGTH_SHORT).show();
                    post_submit.setClickable(true);
                    return;
                }
                if (list!=null&&list.size()>0){
                    Log.i("----list.size----",""+list.size());
                    for (int i=0;i<list.size();i++){
                            Bitmap bt= BitMapUtils.resizeImage2(Uri.fromFile(new File(list.get(i))),1000,1000);
//                            bt=BitMapUtils.imageZoom(bt,800);
                        File f=saveFile(bt,i+"");
                            if (f!=null){
                                li.add(f);
                            }
                        else {
                                Toast.makeText(PostActivity.this,"图片上传失败",Toast.LENGTH_SHORT).show();
                                return;
                            }
                    }
                }
                MyDialog.showDialog(PostActivity.this);
                MultipartBuilder builder=  new MultipartBuilder();
                builder.type(MultipartBuilder.FORM);
                builder.addFormDataPart("token", UserInfo.UserToken);
                Log.i("--",UserInfo.UserToken);
                        builder.addFormDataPart("title",title);
                                builder.addFormDataPart("content",content);
                if (li!=null&&li.size()>0){
//                    Log.i("0000000","文件个数---"+list.size());
                    for (int i=0;i<li.size();i++){
                        File f=li.get(i);
                        Log.i("文件名---"+i,f.getName());
                        builder.addFormDataPart("file"+i, f.getName(), RequestBody.create(null, f));
                    }
                }
                else {
                    Log.i("1111111","没有上传文件");
                    }
                RequestBody requestBody=builder.build();
                final Request request = new Request.Builder().url(Ip.URL+Ip.interface_PostIn).post(requestBody).build();
               OkHttpClient client=new OkHttpClient();
//                client.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
                //开始请求
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.e("TAG", "error ", e);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        resStr=response.body().string();
                        Log.i("发帖请求---",resStr);
                        handler.sendEmptyMessage(1);
                    }
                });
            }

            else {
                post_submit.setClickable(true);
                Toast.makeText(PostActivity.this,"请补全标题或内容",Toast.LENGTH_SHORT).show();
            }

    }
    public File saveFile(Bitmap bm, String fileName){
        File file=null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){//可写
            file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES),fileName+".jpg");
        }
        else {
            file=new File(getFilesDir(),fileName);
        }
//            Log.i("----",file.getAbsolutePath());

        try{
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Log.i("--file-----",file.length()/1024+"");
            return file;
        }
      catch (Exception e){
          e.printStackTrace();
          return null;
      }
    }

    public void goBack(View view) {
        finish();
    }
}
