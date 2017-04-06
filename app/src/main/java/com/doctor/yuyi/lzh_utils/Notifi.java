package com.doctor.yuyi.lzh_utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.doctor.yuyi.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by wanyu on 2017/4/5.
 */

public class Notifi {
    public static void ShowNotifi(final Context context, final UserInfo info) {
        Picasso.with(context).load(info.getPortraitUri()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setContentTitle("收到一条新的消息").
                        setContentText("来自：" + info.getName()).
                        setTicker(info.getName()).setWhen(System.currentTimeMillis())
                        .setPriority(100).
                        setAutoCancel(true).
                        setDefaults(Notification.DEFAULT_ALL)
                        .setLargeIcon(bitmap);
                Notification notification = builder.build();
                notification.defaults = Notification.DEFAULT_ALL;
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                Uri uri = Uri.parse("rong://" + context.getApplicationInfo().packageName).
                        buildUpon().appendPath("conversationlist").appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false").build();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                notification.contentIntent = pendingIntent;
                manager.notify(10010, notification);
            }

            @Override
            public void onBitmapFailed(Drawable drawable) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setContentTitle("收到一条新的消息").
                        setContentText("来自：" + info.getName()).
                        setTicker(info.getName()).setWhen(System.currentTimeMillis())
                        .setPriority(100).
                        setAutoCancel(true).
                        setDefaults(Notification.DEFAULT_ALL)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo));
                Notification notification = builder.build();
                notification.defaults = Notification.DEFAULT_ALL;
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                Uri uri = Uri.parse("rong://" + context.getApplicationInfo().packageName).
                        buildUpon().appendPath("conversationlist").appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false").build();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                notification.contentIntent = pendingIntent;
                manager.notify(10010, notification);
            }

            @Override
            public void onPrepareLoad(Drawable drawable) {

            }
        });
    }
}
