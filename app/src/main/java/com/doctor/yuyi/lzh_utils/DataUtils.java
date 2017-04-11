package com.doctor.yuyi.lzh_utils;

import android.util.Log;

/**
 * Created by wanyu on 2017/4/7.
 */

public class DataUtils {
    public static String getData(long mills){
//        Log.i("发表的时间--",""+mills);
        long current=System.currentTimeMillis();
//        Log.i("当前时间---",""+current);
        long mi=current-mills;
//        Log.i("时间差----",""+mi);
        if (mi>=0){
            String str;
            if (mi>=1000&&mi<60000){//大与1s小与1分钟
                return "刚刚";
            }
            else if (mi>=60000&&mi<3600000){//大于等于1分钟，小与1小时
                return (mi/60000)+"分钟前";
            }
            else if (mi>=3600000&&mi<86400000){//大于等于1小时小与1天
                return (mi/3600000)+"小时前";
            }
            else if (mi>=86400000&&mi<604800000){//大于等于1天小与1周（7天）
                return (mi/86400000)+"天前";
            }
            else if (mi>=604800000&&mi<2628000000L){//大约等于1周小与1个月（30天4周零2天）2419200000（4周）+172800000（2天）+36000000（一年360天剩余的5天／12）=2628000000L(一年中一个月所占的毫秒数)
                return (mi/604800000)+"周前";
            }
            else if (mi>=2628000000L&&mi<31536000000L){
                return (mi/2628000000L)+"月前";
            }
            else if (mi>=31536000000L){//大于等于1年
                return (mi/31536000000L)+"年前";
            }
            return "刚刚";
        }
        else {
            return "0";
        }
    }
}
