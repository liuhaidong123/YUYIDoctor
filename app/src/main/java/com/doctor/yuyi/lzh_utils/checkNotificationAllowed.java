package com.doctor.yuyi.lzh_utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by wanyu on 2017/4/13.
 */

public class checkNotificationAllowed {
    public static boolean isNOtificationOpen(Context context){
        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null;
        try {
            appOpsClass=Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod=appOpsClass.getMethod(CHECK_OP_NO_THROW,Integer.TYPE,Integer.TYPE,String.class);
            Field opPostNotificationValue=appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value=(Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer)checkOpNoThrowMethod.invoke(mAppOps,value,uid,pkg)==AppOpsManager.MODE_ALLOWED);
        } catch (ClassNotFoundException e) {
            e.toString();
            return false;
        } catch (NoSuchMethodException e) {
            e.toString();
            return false;
        } catch (NoSuchFieldException e) {
            e.toString();
            return false;
        } catch (IllegalAccessException e) {
            e.toString();
            return false;
        } catch (InvocationTargetException e) {
            e.toString();
            return false;
        }
    }




    public static void goToSet(Context context){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT >= 9){
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if(Build.VERSION.SDK_INT <= 8){
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }
}
