package com.doctor.yuyi.User;

import android.content.Context;
import android.content.SharedPreferences;

import com.doctor.yuyi.RongCloudUtils.RongUserInfo;

/**
 * Created by wanyu on 2017/3/29.
 */
//保存与用户相关的信息
public class UserInfo {
    public static final String SharedPreName="USER";
    public static final String Sname="userName";
    public static final String SToken="userToken";
    public static final String SRongToken="rongToken";//融云的token

    public static String UserName="";//用户名（电话号ma）
    public static String UserToken="";//服务器返回的token
    public static String RongToken= RongUserInfo.RongToken;//融云token
    //判断用户是否已经登陆
    public static boolean isLogin(Context context){
        SharedPreferences preferences=context.getSharedPreferences(SharedPreName,Context.MODE_APPEND);
        //只有用户名与密码同时存在时才确定登陆
        if (preferences.contains(Sname)&&preferences.contains(SToken)){
            String Name=preferences.getString(Sname,"0");
            String Token=preferences.getString(SToken,"0");
            if (!"0".equals(Name)&&!"0".equals(Token)){
                UserName=Name;
                UserToken=Token;
                return true;
            }
                return false;
        }
        return false;
    }

    //退出登录
    public static void clearLogin(Context context){
        SharedPreferences preferences=context.getSharedPreferences(SharedPreName,Context.MODE_APPEND);
        SharedPreferences.Editor editor=preferences.edit();
        editor.remove(Sname);
        editor.remove(SToken);
        editor.commit();
        UserName="";
        UserToken="";
        RongToken="";
    }
    //判断当前用户的融云信息是否存在，token，id,touxiang
    public static Boolean isRongInfoExit(Context context){
        SharedPreferences preferences=context.getSharedPreferences(SharedPreName,Context.MODE_APPEND);

        return false;
    }
}
