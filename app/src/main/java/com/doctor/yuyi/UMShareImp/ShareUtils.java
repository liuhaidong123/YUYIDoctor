package com.doctor.yuyi.UMShareImp;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by liuhaidong on 2017/4/13.
 */

public class ShareUtils {

    public static void share(Activity activity, UMShareListener umShareListner, UMWeb umWeb) {
        new ShareAction(activity)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(umWeb)
                .setCallback(umShareListner).open();
    }
}
