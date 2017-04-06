package com.doctor.yuyi.lzh_utils;

import android.util.Log;


import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by hp on 2016/8/3.
 */
public class okhttp {
    public static final int OK_GET=0;
    public static final  int OK_POST=1;
    public static final Gson gson=new Gson();
    public static final OkHttpClient okhttpclient=new OkHttpClient();
    public static Call getCall(String url, Map<String,String>mp, int state) {
        okhttpclient.setConnectTimeout(7000, TimeUnit.MILLISECONDS);
        if (state == OK_GET) {
            StringBuilder builderString = new StringBuilder();
            if (mp!=null&&mp.size()>0){
                for (String s : mp.keySet()) {
                    builderString.append(s);
                    builderString.append("=");
                    builderString.append(mp.get(s));
                    builderString.append("&");
                }
                String bString=builderString.toString();
                bString=bString.substring(0,bString.length()-1);
                url = url +bString;
            }
            Log.i("url--okHttpGet--",url);
            return okhttpclient.newCall(new Request.Builder().url(url).build());
        }
        else if (state == OK_POST) {
            //测试用--------yixia----------
            String uri=url;
            StringBuilder builderString = new StringBuilder();
            if (mp!=null&&mp.size()>0){
                for (String s : mp.keySet()) {
                    builderString.append(s);
                    builderString.append("=");
                    builderString.append(mp.get(s));
                    builderString.append("&");
                }
                String bString=builderString.toString();
                bString=bString.substring(0,bString.length()-1);
                uri = uri +bString;
            }
            Log.i("url--okHttpPost--",uri);
            //测试用----------yishang--------
            FormEncodingBuilder builder = new FormEncodingBuilder();
            if (mp!=null&&mp.size()>0){
                for (String s : mp.keySet()) {
                    builder.add(s, mp.get(s));
                }
            }

            Request request = new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .build();
            return okhttpclient.newCall(request);
//            684228
        }
        return null;
    }

    //带cookie得post请求
    public static Call getCallCookie(String url, Map<String,String>mp,String cookie){

        //测试用--------yixia----------
        String uri=url;
        StringBuilder builderString = new StringBuilder();
        if (mp!=null&&mp.size()>0){
            for (String s : mp.keySet()) {
                builderString.append(s);
                builderString.append("=");
                builderString.append(mp.get(s));
                builderString.append("&");
            }
            String bString=builderString.toString();
            bString=bString.substring(0,bString.length()-1);
            uri = uri +bString;
        }
        Log.i("url--okHttpPost--",uri);
//        测试用----------yishang--------

        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (String s : mp.keySet()) {
            builder.add(s, mp.get(s));
        }
//        builder.add("Set-Cookie",cookie);
        Request request;
        if (cookie!=null&&!"".equals(cookie)){
            request = new Request.Builder()
                    .url(url).header("cookie",cookie)
                    .post(builder.build())
                    .build();
        }
        else {
            request = new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .build();
        }
        return new OkHttpClient().newCall(request);
    }


}
