package com.doctor.yuyi.lzh_utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.WindowManager;

import com.doctor.yuyi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/4/11.
 */

public class BitMapUtils {
    public static List<Map<String,String>> getCursor(Context context){
        List<Map<String,String>>listPth=new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media.DATA}, MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);
            if (cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                if (!"".equals(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)))
                        &&!TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)))){
                    Map<String,String>mp=new HashMap<>();
                    mp.put("url",cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                    mp.put("select","0");
                    listPth.add(mp);
                }
            }
        }
        return listPth;
    }



    public static Bitmap getImageThumbnail(Context context, String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        try{
            bitmap = BitmapFactory.decodeFile(imagePath, options);
        }
        catch (Exception e){
            bitmap=BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
        }
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }



    public static int  getWindowWidth(Context context){
        WindowManager wm = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return width;
    }
}
