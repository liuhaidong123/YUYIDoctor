package com.doctor.yuyi.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.R;
import com.doctor.yuyi.lzh_utils.BitmapTobase64;
import com.doctor.yuyi.myview.RoundImageView;

import java.io.File;
import java.io.IOException;

public class UserInfo_Activity extends Activity implements View.OnClickListener {

    private RelativeLayout mSex;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;
    private View mSexAlertView;
    private RadioGroup user_editor_sex_radioGroup;
    private RadioButton boyBtn,grilBtn;
    private ImageView mBack;
    private EditText mAgeEdit;
    private EditText mNikName;
    private EditText mAdEdit;
    private EditText mAddressEdit;

    private RelativeLayout select_head;
    private TextView usereditor_textv_cancle,usereditor_textv_picture,usereditor_textv_camera;
    private PopupWindow popW;
    private RoundImageView usereditor_image_userphoto;
    private EditText user_editor_userName;
    private TextView user_editor_sex;
    private File fileaa;
    private Bitmap bit;
    private String bit64;
    private String resStr;

    private File outputImage;
    private Uri imageUri;

    private int SE;//性别标示0女，1男
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://失败
//                    toast.toast_faild(UserInfo_Activity.this);
                    break;
                case 1://获取用户信息
//                    try{
//                        bean_My_UserMsg usMsg= gson.gson.fromJson(resStr,bean_My_UserMsg.class);
//                        if ("0".equals(usMsg.getCode())){
//                            bean_My_UserMsg.ResultBean bean=usMsg.getResult();
//                            mNikName.setText(bean.getUserName());
//                            user_editor_userName.setText(bean.getTrueName());
//                            Picasso.with(UserInfo_Activity.this).load(Ip.imagePth+bean.getAvatar()).error(R.mipmap.logo).into(new Target() {
//                                @Override
//                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
//                                    bit64=BitmapTobase64.bitmapToBase64(bitmap);
//                                    usereditor_image_userphoto.setImageBitmap(bitmap);
//                                }
//
//                                @Override
//                                public void onBitmapFailed(Drawable drawable) {
//                                    bit64=BitmapTobase64.bitmapToBase64(BitmapFactory.decodeResource(getResources(),R.mipmap.logo));
//                                    usereditor_image_userphoto.setImageResource(R.mipmap.logo);
//                                }
//
//                                @Override
//                                public void onPrepareLoad(Drawable drawable) {
//
//                                }
//                            });
//                            int sx=bean.getGender();
//                            SE=bean.getGender();
//                            if (sx==0){//nv
//                                user_editor_sex.setText("女");
//                            }
//                            else if (sx==1){//nan
//                                user_editor_sex.setText("男");
//                            }
//                            mAgeEdit.setText(bean.getAge()+"");
//                            mAdEdit.setText(bean.getIdCard());
//                        }
//
//                        else {
//                            Toast.makeText(UserInfo_Activity.this,"获取信息失败：",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    catch (Exception e){
//                        toast.toast_gsonFaild(UserInfo_Activity.this);
////                        Log.e("----gson-----",e.toString());
//                        e.printStackTrace();
//                    }
                    break;
                case 2://修改／添加用户信息
//                    try{
//                        bean_ChangeUserMsg changeUserMsg=gson.gson.fromJson(resStr,bean_ChangeUserMsg.class);
//                        if ("0".equals(changeUserMsg.getCode())){
//                            Toast.makeText(UserEditorActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                        else {
//                            Toast.makeText(UserInfo_Activity.this,"修改失败",Toast.LENGTH_SHORT).show();
//                        }
//                    }catch (Exception e){
//                        toast.toast_gsonFaild(UserInfo_Activity.this);
//                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_);
        initView();
        getUserData();//获取用户个人信息
    }

    private void initView() {
        //性别
        user_editor_sex= (TextView) findViewById(R.id.user_editor_sex);
        //用户名
        user_editor_userName= (EditText) findViewById(R.id.user_editor_userName);
        user_editor_userName.setSelection(user_editor_userName.getText().length());
        //选择性别
        mSex = (RelativeLayout) findViewById(R.id.user_sex_rl);
        mSex.setOnClickListener(this);
        mBuilder = new AlertDialog.Builder(this);
        mAlertDialog = mBuilder.create();
        mSexAlertView = LayoutInflater.from(this).inflate(R.layout.sex_alert_box, null);
        boyBtn= (RadioButton) mSexAlertView.findViewById(R.id.boy_btn);
        grilBtn= (RadioButton) mSexAlertView.findViewById(R.id.girl_btn);
        user_editor_sex_radioGroup= (RadioGroup) mSexAlertView.findViewById(R.id.user_editor_sex_radioGroup);
        user_editor_sex_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.boy_btn://男
                        SE=1;
                        user_editor_sex.setText("男");
                        break;
                    case R.id.girl_btn://女
                        SE=0;
                        user_editor_sex.setText("女");
                        break;
                }
            }
        });
        mAlertDialog.setView(mSexAlertView);
        //返回
        mBack = (ImageView) findViewById(R.id.editor_back);
        mBack.setOnClickListener(this);
        //年龄编辑框
        mAgeEdit = (EditText) findViewById(R.id.age_edit);
        mAgeEdit.setSelection(mAgeEdit.getText().length());

        select_head= (RelativeLayout) findViewById(R.id.select_head);
        select_head.setOnClickListener(this);

        usereditor_image_userphoto= (RoundImageView) findViewById(R.id.usereditor_image_userphoto);
        //昵称
        mNikName=(EditText) findViewById(R.id.nikname_edit);
        mNikName.setSelection(mNikName.getText().length());
        //省份证号
        mAdEdit=(EditText) findViewById(R.id.ad_edit);
        mAdEdit.setSelection(mAdEdit.getText().length());
        //籍贯
        mAddressEdit=(EditText) findViewById(R.id.jg_edit);
        mAddressEdit.setSelection(mAddressEdit.getText().length());

        select_head= (RelativeLayout) findViewById(R.id.select_head);
        select_head.setOnClickListener(this);

        usereditor_image_userphoto= (RoundImageView) findViewById(R.id.usereditor_image_userphoto);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.user_sex_rl://点击个人信息编辑
                mAlertDialog.show();
                switch (SE){
                    case 0:
                        boyBtn.setChecked(false);
                        grilBtn.setChecked(true);
                        break;
                    case 1://男
                        boyBtn.setChecked(true);
                        grilBtn.setChecked(false);
                        break;
                }
                setAlertWidth();
                break;
            case R.id.editor_back://返回
                finish();
                break;
            case R.id.select_head://上传头像
                showWindowUploading();//
                break;
            case R.id.usereditor_textv_picture://图库选取头像
                popW.dismiss();
                SearchPhoto();
                break;
            case R.id.usereditor_textv_camera://拍照头像
                popW.dismiss();
                TakePhoto();
                break;
            case R.id.usereditor_textv_cancle://取消
                popW.dismiss();
                break;
        }
    }


    //设置alert宽度
    public void setAlertWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager m = getWindowManager();
        m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = mAlertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (dm.widthPixels * (0.7));
        mAlertDialog.getWindow().setAttributes(p);//设置生效
    }
    //上传头像
    private void showWindowUploading() {
        View vi=LayoutInflater.from(UserInfo_Activity.this).inflate(R.layout.usereditor_pop_uploading,null);
        LinearLayout usereditor_pop_layout= (LinearLayout) vi.findViewById(R.id.usereditor_pop_layout_t);
        usereditor_textv_cancle= (TextView) vi.findViewById(R.id.usereditor_textv_cancle);
        usereditor_textv_picture= (TextView) vi.findViewById(R.id.usereditor_textv_picture);
        usereditor_textv_camera= (TextView) vi.findViewById(R.id.usereditor_textv_camera);
        usereditor_textv_cancle.setOnClickListener(this);
        usereditor_textv_camera.setOnClickListener(this);
        usereditor_textv_picture.setOnClickListener(this);
        popW=new PopupWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=0.6f;
        getWindow().setAttributes(params);
        popW.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popW.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popW.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popW.setContentView(vi);
        popW.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popW.setTouchable(true);
        popW.setFocusable(true);
        popW.setOutsideTouchable(true);

        RelativeLayout parent= (RelativeLayout) findViewById(R.id.par_layout);
        popW.setAnimationStyle(R.style.popup3_anim);
        popW.showAtLocation(parent, Gravity.CENTER, 0,0);

        ViewGroup.LayoutParams param=usereditor_pop_layout.getLayoutParams();
        param.width=(int)(getWindowManager().getDefaultDisplay().getWidth()*0.8);
        usereditor_pop_layout.setLayoutParams(param);
        popW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });
    }


    //拍照
    private void TakePhoto() {
        if (Build.VERSION.SDK_INT>=23){
            int Permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (Permission!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},10);
            }
            else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                outputImage=new File(getExternalFilesDir("DCIM").getAbsolutePath(),"user"+".jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, 21);
            }
        }
        else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            outputImage=new File(getExternalFilesDir("DCIM").getAbsolutePath(),"user"+".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, 21);

        }

    }


    //图库选取
    private void SearchPhoto() {
        if (Build.VERSION.SDK_INT>=23){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED&&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},11);
            }
            else {
                outputImage = new File(getExternalFilesDir("DCIM").getAbsolutePath(),"user"+".jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                //此处调用了图片选择器
                //如果直接写intent.setDataAndType("image/*");
                //调用的是系统图库
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
                startActivityForResult(intent, 20);
            }
        }
        else {
            outputImage = new File(getExternalFilesDir("DCIM").getAbsolutePath(),"user"+".jpg");
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                //此处调用了图片选择器
                //如果直接写intent.setDataAndType("image/*");
                //调用的是系统图库
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
                startActivityForResult(intent, 20);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    TakePhoto();
                }
                else {
                    Toast.makeText(UserInfo_Activity.this,"相机权限被禁用，无法拍照",Toast.LENGTH_SHORT).show();
                }
                break;
            case 11:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    SearchPhoto();
                }
                else {
                    Toast.makeText(UserInfo_Activity.this,"存储权限被禁用，无法选取图片",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 20:
                    //此处启动裁剪程序
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    //此处注释掉的部分是针对android 4.4路径修改的一个测试
                    //有兴趣的读者可以自己调试看看
                    intent.setDataAndType(data.getData(), "image/*");
                    intent.putExtra("scale", true);

                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("outputX", 200);//宽度
                    intent.putExtra("outputY", 200);//高度
//                    intent.putExtra("return-data", true);
//                    intent.putExtra("noFaceDetection", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
                    startActivityForResult(intent, 12);
                    break;
                case 21:
                    //此处启动裁剪程序
                    Intent intent2 = new Intent("com.android.camera.action.CROP");
                    //此处注释掉的部分是针对android 4.4路径修改的一个测试
                    //有兴趣的读者可以自己调试看看
                    intent2.setDataAndType(Uri.fromFile(outputImage), "image/*");
                    intent2.putExtra("scale", true);

                    intent2.putExtra("aspectX", 1);
                    intent2.putExtra("aspectY", 1);
                    intent2.putExtra("outputX", 200);//宽度
                    intent2.putExtra("outputY", 200);//高度
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
                    startActivityForResult(intent2, 12);
                    break;
                case 12:
                    try{
                        //将output_image.jpg对象解析成Bitmap对象，然后设置到ImageView中显示出来
                        Bitmap bitmap = BitmapFactory.decodeFile(outputImage.getAbsolutePath());
                        if (bitmap!=null){
                            bit=bitmap;
                            bit64=BitmapTobase64.bitmapToBase64(bit);
                            usereditor_image_userphoto.setImageBitmap(bit);
                        }
                        else {
                            Toast.makeText(UserInfo_Activity.this,"照片截取失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(UserInfo_Activity.this,"照片截取失败",Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }

    }




    //获取个人信息接口
    public void getUserData() {
//        Map<String,String> mp=new HashMap<>();
//        mp.put("token", user.token);
//        okhttp.getCall(Ip.url_F+Ip.interface_UserMsg,mp,okhttp.OK_GET).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                handler.sendEmptyMessage(0);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                resStr=response.body().string();
//                Log.i("获取用户个人信息--",resStr);
//                handler.sendEmptyMessage(1);
//            }
//        });
    }

    //提交用户信息http://localhost:8080/yuyi/personal/save.do?token=C0700876FB2F9BEC156AC039F894E92B&idCard=515251635262&age=26
    public void sendMsg(){
//        Map<String,String>mp=new HashMap<>();
//        mp.put("token",user.token);
//        mp.put("avatar",bit64);
//        mp.put("userName","");
//        mp.put("trueName",user_editor_userName.getText().toString());//真实姓名
//        mp.put("age",mAgeEdit.getText().toString());//年龄
//        mp.put("gender",""+SE);//性别
//        Log.i("---性别--","--------"+SE);
//        mp.put("idCard",mAdEdit.getText().toString());//身份证号
//        okhttp.getCall(Ip.url_F+Ip.interface_UserMsgRevise,mp,okhttp.OK_POST).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                handler.sendEmptyMessage(0);
//            }
//            @Override
//            public void onResponse(Response response) throws IOException {
//                resStr=response.body().string();
//                Log.i("修改个人信息----",resStr);
//                handler.sendEmptyMessage(2);
//            }
//        });
    }
    // 提交／上传用户信息``
    public void Submit(View view) {
        int input=checkInput();
        if(input==0){
            sendMsg();
        }
        else if (input==1){
            Toast.makeText(UserInfo_Activity.this,"头像未上传",Toast.LENGTH_SHORT).show();
        }
        else if (input==2){
            Toast.makeText(UserInfo_Activity.this,"信息填写不完整",Toast.LENGTH_SHORT).show();
        }

    }
    //检查
    private int checkInput() {
        if (!"".equals(bit64)&&!TextUtils.isEmpty(bit64)){
            if (!"".equals(user_editor_userName.getText().toString())&&!TextUtils.isEmpty(user_editor_userName.getText().toString())
                    &&!"".equals(user_editor_sex.getText().toString())&&!TextUtils.isEmpty(user_editor_sex.getText().toString())
                    &&!"".equals(mAgeEdit.getText().toString())&&!TextUtils.isEmpty(mAgeEdit.getText().toString()
            )&&!"".equals(mAdEdit.getText().toString())&&!TextUtils.isEmpty(mAdEdit.getText().toString())){
                return 0;

            }
            else {
                return 2;//其他信息不完整
            }
        }
        else {
            return 1;//头像不完整
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (outputImage != null) {
            if (outputImage.exists() && outputImage.isFile()) {
                outputImage.delete();
            }
        }
    }
}
