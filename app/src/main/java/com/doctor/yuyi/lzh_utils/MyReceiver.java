package com.doctor.yuyi.lzh_utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wanyu on 2017/3/8.
 * 激光推送
 */

public class MyReceiver extends BroadcastReceiver{
    final String TAG=getClass().getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        Bundle bundle=intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
            Toast.makeText(context,"注册的jpId=="+regId,Toast.LENGTH_SHORT).show();

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String msg=bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + msg);
            Toast.makeText(context,"接收到推送消息=="+msg,Toast.LENGTH_SHORT).show();
        }
        else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction()))
        {
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            String alert=bundle.getString(JPushInterface.EXTRA_ALERT);
            String title=bundle.getString(JPushInterface.EXTRA_TITLE);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知=id="+notifactionId);
            Log.i("标题--"+alert,"内容"+title);
//            Toast.makeText(context,"注册的jpId=="+notifactionId+"--标题--"+title+"--内容---"+alert,Toast.LENGTH_SHORT).show();
//
//            if ("".equals(alert)|TextUtils.isEmpty(alert)){
//                NotificationManager manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
//                builder.setContentTitle("Jpush").
//                        setContentText(title).
//                        setTicker("Jpush推送失败,显示本地推送").setWhen(System.currentTimeMillis())
//                        .setPriority(100).
//                        setAutoCancel(true).
//                        setDefaults(Notification.DEFAULT_VIBRATE)
//                        .setSmallIcon(R.mipmap.ic_launcher);
//                Notification notification = builder.build();
//                notification.flags = Notification.FLAG_AUTO_CANCEL;
//                PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, new Intent(),PendingIntent.FLAG_CANCEL_CURRENT);
//                notification.contentIntent=pendingIntent;
//                manager.notify(notifactionId,notification);
//            }
        }
        else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction()))
        {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            //打开自定义的Activity
//            Intent i = new Intent(context, TestActivity.class);
//            i.putExtras(bundle);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//            context.startActivity(i);
            Toast.makeText(context,"用户点开了通知",Toast.LENGTH_SHORT).show();

            //通知附加字段的json字符串（通过解析附加字段可以判断通知的类型（与后台约定）然后做相应处理）
//            String msg=bundle.getString(JPushInterface.EXTRA_EXTRA);     //通知的标题
//            String title=bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }

    }
}
