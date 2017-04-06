package com.doctor.yuyi.mApplication;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.doctor.yuyi.UMShareImp.UmAuthListener;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanyu on 2017/3/29.
 */

public class MyApplication extends Application {
    public static Activity activityCurrent;
    private static List<Activity> list;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化友盟SDK
        UMShareAPI.get(this);
        Config.isJumptoAppStore = true;//如果用户没有安装qq,微信客户端会自动跳转到应用商店地址去下载（微博不会，微博只会打开网页端）

        list = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 14) {//4.0以上
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    list.add(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                    activityCurrent = activity;
//                    Log.i("----Myapp----",activityCurrent.getClass().getSimpleName());
//                    Log.i("activityCurrent==null",(activityCurrent==null)+"");

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    list.remove(activity);
                }
            });
        }
    }

    public static void removeActivity() {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Activity activity = list.get(i);
                Log.i("remove-名字--", activity.getClass().getSimpleName());
                activity.finish();
            }
            list.clear();
        }

    }


    /**
     * 微信，qq,微博分享配置三方平台appkey
     */ {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setQQZone("1106000897", "G4bjclTil8So0PJg");
        // PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        PlatformConfig.setSinaWeibo("2812940198", "1cfe42039988a86b98f8ec359d4e508d", "http://sns.whalecloud.com/sina2/callback");
    }
}
