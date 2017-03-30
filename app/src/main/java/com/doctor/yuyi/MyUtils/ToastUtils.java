package com.doctor.yuyi.MyUtils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liuhaidong on 2017/3/23.
 */

public class ToastUtils {
    public static void myToast(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }
}
