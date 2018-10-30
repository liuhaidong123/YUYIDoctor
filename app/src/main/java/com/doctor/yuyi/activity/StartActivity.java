package com.doctor.yuyi.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.bean.VersionRoot;

public class StartActivity extends Activity {
    private int time = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (time >= 0) {
                    time--;
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                } else {
                    if (UserInfo.isLogin(StartActivity.this)) {
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(StartActivity.this, Login_Activity.class));
                    }
                    finish();
                }
            }else if (msg.what == 909) {//检测版本号

                Object o = msg.obj;
                if (o != null && o instanceof VersionRoot) {
                    VersionRoot versionRoot = (VersionRoot) o;
                    if (versionRoot != null && "0".equals(versionRoot.getCode())) {
                        int versionC = Integer.valueOf(versionRoot.getResult().getVersion());
                        if (versionC > getVersionCode()) {//服务器版本号大于当前版本号，需要更新软件
                            Log.e("服务器版本号=", versionC + "");
                            alertDialog.show();

                        } else {//不需要更新软件
                            mHandler.sendEmptyMessage(1);
                        }
                    }
                }

            }
        }
    };
    private HttpTools httpTools;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        httpTools = HttpTools.getHttpToolsInstance();

        if (isNetworkConnected()){
            httpTools.CheckVersion(mHandler);//检测版本号
        }else {
            mHandler.sendEmptyMessage(1);
        }
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("检测到最新版本,请更新");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://sj.qq.com/myapp/search.htm?kw=%E5%AE%87%E5%8C%BB%E5%8C%BB%E7%94%9F"));
                startActivity(intent);
                alertDialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                finish();
            }
        });
        alertDialog = builder.create();

    }
    private int getVersionCode() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int versionCode = packInfo.versionCode;
        Log.e("本地软件版本号=", versionCode + "");
        return versionCode;
    }

    /**
     * 判断有没有网
     *
     * @return
     */
    public boolean isNetworkConnected() {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }

        return false;
    }
}
