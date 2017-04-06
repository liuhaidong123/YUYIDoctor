package com.doctor.yuyi.mApplication;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.lzh_utils.Notifi;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.AnnotationNotFoundException;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

import static android.content.ContentValues.TAG;

/**
 * Created by wanyu on 2017/3/29.
 */

public class MyApplication extends Application{
    public static Activity activityCurrent;
    private static List<Activity> list;
    public static List<UserInfo> li=new ArrayList<>();
    private Bitmap bit;
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplicationContext());

        list=new ArrayList<>();
        if (Build.VERSION.SDK_INT>=14){//4.0以上
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

                    activityCurrent=activity;
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
        RongIM.init(this);
        RongIM.getInstance().setMessageAttachedUserInfo(true);

        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                switch (connectionStatus){

                    case CONNECTED://连接成功。
                            Log.e("connection","连接成功");
                        break;
                    case DISCONNECTED://断开连接。
                        Log.e("connection","断开连接");
                        break;
                    case CONNECTING://连接中。
                        Log.e("connection","连接中");
                        break;
                    case NETWORK_UNAVAILABLE://网络不可用。
                        Log.e("connection","网络不可用");
                        break;
                    case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                        Log.e("connection","用户账户在其他设备登录，本机会被踢掉线");
                        break;
                }
            }
        });
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                if (message!=null){
                    UserInfo info=message.getContent().getUserInfo();
                    if (info==null){
                        info=new UserInfo(message.getTargetId(),"宇医",Uri.parse("http://k1.jsqq.net/uploads/allimg/1612/140F5A32-6.jpg"));
                    }
                    MessageContent messageContent= message.getContent();
                    if (messageContent instanceof TextMessage) {//文本消息
                        TextMessage textMessage = (TextMessage) messageContent;
                        Log.d("---TextMessage--", "onSent-TextMessage:" + textMessage.getContent());
                    } else if (messageContent instanceof ImageMessage) {//图片消息
                        ImageMessage imageMessage = (ImageMessage) messageContent;
                        Log.d("---imageMessage--", "onSent-ImageMessage:" + imageMessage.getRemoteUri());
                    } else if (messageContent instanceof VoiceMessage) {//语音消息
                        VoiceMessage voiceMessage = (VoiceMessage) messageContent;
                        Log.d("-onSent-voiceMessage--", "onSent-voiceMessage:" + voiceMessage.getUri().toString());
                    } else if (messageContent instanceof RichContentMessage) {//图文消息
                        RichContentMessage richContentMessage = (RichContentMessage) messageContent;
                        Log.d("---RichContentMessage--", "onSent-RichContentMessage:" + richContentMessage.getContent());
                    } else {
                        Log.d("---app--", "onSent-其他消息，自己来判断处理");
                            }
                    NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                    builder.setContentTitle("收到一条新的消息").
                            setContentText("来自：" + info.getName()).
                            setTicker(info.getName()).setWhen(System.currentTimeMillis())
                            .setPriority(100).
                            setAutoCancel(true).
                            setDefaults(Notification.DEFAULT_ALL)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.logo)).setSmallIcon(R.mipmap.logo);
                    Notification notification = builder.build();
                    notification.defaults = Notification.DEFAULT_ALL;
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    Uri uri = Uri.parse("rong://" + getApplicationContext().getApplicationInfo().packageName).
                            buildUpon().appendPath("conversationlist").appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                            .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                            .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                            .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false").build();
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(uri);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    notification.contentIntent = pendingIntent;
                    manager.notify(10010, notification);
                }
                return false;
            }
        });

    }

    public static void removeActivity(){
        if (list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++){
                Activity activity=list.get(i);
                Log.i("remove-名字--",activity.getClass().getSimpleName());
                activity.finish();
            }
            list.clear();
        }

    }

}
