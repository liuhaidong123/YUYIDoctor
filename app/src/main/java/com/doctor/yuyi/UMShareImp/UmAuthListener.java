package com.doctor.yuyi.UMShareImp;

import android.util.Log;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by liuhaidong on 2017/4/1.
 */

public class UmAuthListener implements UMAuthListener {
    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.e("授权开始","===");
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        Log.e("授权完成","===");
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        Log.e("授权错误","===");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {
        Log.e("授权取消","===");
    }
}
