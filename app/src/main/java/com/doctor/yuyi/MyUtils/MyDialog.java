package com.doctor.yuyi.MyUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.doctor.yuyi.R;


/**
 * Created by wanyu on 2017/3/17.
 */

public class MyDialog {
    public static ProgressDialog dialog;
    public static void showDialog(Context context){
        if (context!=null){
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.window, null);
            ImageView imageView= (ImageView) v.findViewById(R.id.dialogimg);
            imageView.setBackgroundResource(R.drawable.animt);
            AnimationDrawable animationDrawable = (AnimationDrawable)
                    imageView.getBackground();
            animationDrawable.start();
            dialog = new ProgressDialog(context, R.style.dialog);
            dialog.setCancelable(true);
            dialog.show();
            dialog.setContentView(v, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        }

    }
    public static void  stopDia(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }

    }
}
