package com.doctor.yuyi.UMShareImp;

import android.util.Log;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by liuhaidong on 2017/3/31.
 */

public class UMShareListner implements UMShareListener {
    @Override
    public void onStart(SHARE_MEDIA share_media) {
      Log.e("分享开始","===");
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Log.e("分享结果","==="+share_media);
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Log.e("分享错误","==="+throwable.getMessage());
        if(throwable!=null){
            Log.d("throw","throw:"+throwable.getMessage());
        }
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Log.e("分享取消","===");
    }
}
