package com.doctor.yuyi.MyUtils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuhaidong on 2017/4/6.
 */

public class TimeUtils {

    static Date curDate = new Date(System.currentTimeMillis());//获取当前时间

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getTime(String strTime) {
        Date d1 = null;
        try {
            d1 = df.parse(strTime);
            double diff = curDate.getTime()-d1.getTime() ;//这样得到的差值是微秒级别

            double days = diff / (1000 * 60 * 60 * 24);
            double year = days / 365;
            double month = days / 30;
            double hours = days*24;
            double minutes = hours*60;
            Log.e("diff=", diff + "");
            Log.e("year=", year + "");
            Log.e("month=", month + "");
            Log.e("day=", days + "");
            Log.e("hours=", hours + "");
            Log.e("minutes=", minutes + "");
            if (year > 1) {
                return (int)year + "年前";
            }else {
                if (month>1){
                    return (int)month+"个月前";
                }else {
                    if (days>1){
                        return (int)days+"天前";
                    }else {
                        if (hours>1){
                            return (int)hours+"小时前";
                        }else {
                            if (minutes>1){
                                return (int)minutes+"分钟前";
                            }else {
                                return "刚刚";
                            }
                        }
                    }
                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


}
