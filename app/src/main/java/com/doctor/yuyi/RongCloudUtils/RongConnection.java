package com.doctor.yuyi.RongCloudUtils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * Created by wanyu on 2017/3/30.
 */

public class RongConnection {
    private static Context con;
    //连接服务器
public static void connRong(final Context context, String token){
    con=context;
    if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                RongUserInfo.RongId=userid;
                Log.e("融云id获取成功----",""+userid);
                UserInfo userInfo=new UserInfo("9","李师师", Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490873959821&di=4a36685a2bec6f1b7ecd006ff529e795&imgtype=0&src=http%3A%2F%2Fp.3761.com%2Fpic%2F29641417567978.jpg"));
                RongIM.getInstance().setCurrentUserInfo(userInfo);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("融云id获取失败----",""+RongUserInfo.RongToken);
            }
        });
    }
}
}
