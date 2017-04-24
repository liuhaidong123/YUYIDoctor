package com.doctor.yuyi.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.doctor.yuyi.MyUtils.SharedPreferencesUtils;
import com.doctor.yuyi.MyUtils.ToastUtils;

/**
 * Created by liuhaidong on 2017/4/17.
 */

public class BroadCastYUYI extends BroadcastReceiver {
    public BroadCastYUYI() {

    }

    ;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
        } else {

        }
    }

}
