package com.doctor.yuyi.lzh_utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wanyu on 2017/3/14.
 */

public class toast {
    public static void toast_faild(Context context){
        Toast.makeText(context,conn.conn_FAILE,Toast.LENGTH_SHORT).show();
    }
    public static void toast_gsonFaild(Context context){
        Toast.makeText(context,conn.conn_gson_FAILE,Toast.LENGTH_SHORT).show();
    }
    public static void toast_gsonEmpty(Context context){//数据为空
        Toast.makeText(context,conn.conn_gson_EMPTY,Toast.LENGTH_SHORT).show();
    }
}
