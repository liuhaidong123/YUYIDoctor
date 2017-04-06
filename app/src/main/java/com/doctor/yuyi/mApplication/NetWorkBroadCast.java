package com.doctor.yuyi.mApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.doctor.yuyi.activity.NetWorkError_Activity;

/**
 * Created by wanyu on 2017/4/6.
 */

public class NetWorkBroadCast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager!=null) {
            NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();
            for (int i = 0; i < networkInfos.length; i++) {
                NetworkInfo.State state=networkInfos[i].getState();
                if (NetworkInfo.State.CONNECTED==state) {
                    System.out.println("------------> Network is ok");
                    return;
                }
            }
        }
        //没有执行return,则说明当前无网络连接
        System.out.println("------------> Network is validate");
        intent.setClass(context, NetWorkError_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
