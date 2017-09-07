package com.doctor.yuyi.lzh_utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.doctor.yuyi.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
                        // 获取图片的路径
                        String path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        mp.put("url",path);
                        mp.put("select","0");
                        listPth.add(mp);
                    }
                }
            }
            cursor.close();
            return listPth;
    }



    public static int  getWindowWidth(Context context){
        WindowManager wm = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return width;
    }


    //获取真实的url
    public static Uri getRealUri(Context context, String uri){
        String pth="";
            Uri u=Uri.parse(uri);
            if (u.getScheme()==null){
                u=Uri.fromFile(new File(u.getPath()));
            }
        return u;
    }

    //bitmap二次采样
    public static Bitmap resizeImage2(Uri uri,
                                        int width, int height)
    {
        int state=0;
        File f=new File(uri.getPath());
//        Log.i("--path--",f.getAbsolutePath());
        //第一次采样
        BitmapFactory.Options options = new BitmapFactory.Options();
        //该属性设置为true只会加载图片的边框进来，并不会加载图片具体的像素点
        options.inJustDecodeBounds = true;
        //第一次加载图片，这时只会加载图片的边框进来，并不会加载图片中的像素点
        BitmapFactory.decodeFile(f.getAbsolutePath(), options);
        //获得原图的宽和高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        //定义缩放比例
        int sampleSize = 1;
        while (outHeight / sampleSize > height || outWidth / sampleSize > width) {
            //如果宽高的任意一方的缩放比例没有达到要求，都继续增大缩放比例
            //sampleSize应该为2的n次幂，如果给sampleSize设置的数字不是2的n次幂，那么系统会就近取值
            sampleSize *= 2;
        }
        /********************************************************************************************/
        //至此，第一次采样已经结束，我们已经成功的计算出了sampleSize的大小
        /********************************************************************************************/
        //二次采样开始
        //二次采样时我需要将图片加载出来显示，不能只加载图片的框架，因此inJustDecodeBounds属性要设置为false
//        Log.i("--"+outHeight+"--"+outWidth,"--"+sampleSize);
//        Log.i("height--"+height,"width--"+width);
        options.inJustDecodeBounds = false;
        //设置缩放比例
        options.inSampleSize = sampleSize;
        //加载图片并返回
        Bitmap bt=BitmapFactory.decodeFile(f.getAbsolutePath(),options);
//        Log.i("bt.width--"+bt.getWidth(),"bt.height=--"+bt.getHeight());
        int mi=Math.abs(bt.getWidth()-bt.getHeight())/2;
          if (bt.getWidth()>bt.getHeight()){//宽》高
              bt=Bitmap.createBitmap(bt,mi,0,bt.getHeight(),bt.getHeight());
          }
        else {//宽《=高
              bt=Bitmap.createBitmap(bt,0,mi,bt.getWidth(),bt.getWidth());
          }
        Log.i("bitmp大小---",bt.getRowBytes()*bt.getHeight()/1024+"");
        return bt;
    }




//    public static Bitmap imageZoom(Bitmap bitmap,double maxSize) {
//        //图片允许最大空间   单位：KB
//        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
//        byte[] b = baos.toByteArray();
//        //将字节换成KB
//        double mid = b.length/1024;
//        Log.i("--------",mid+"------");
//        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
//        if (mid > maxSize) {
//            //获取bitmap大小 是允许最大大小的多少倍
//            double i = mid / maxSize;
//            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
//            bitmap = zoomImage(bitmap, bitmap.getWidth() / Math.sqrt(i),
//                    bitmap.getHeight() / Math.sqrt(i));
//            }
//        return bitmap;
//    }


//
//    private static Bitmap zoomImage(Bitmap bgimage, double newWidth,
//                                   double newHeight) {
//        // 获取这个图片的宽和高
//        float width = bgimage.getWidth();
//        float height = bgimage.getHeight();
//        // 创建操作图片用的matrix对象
//        Matrix matrix = new Matrix();
//        // 计算宽高缩放率
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//        // 缩放图片动作
//        matrix.postScale(scaleWidth, scaleHeight);
//        return Bitmap.createBitmap(bgimage, 0, 0, (int) width,
//                (int) height, matrix, true);
//    }
}
