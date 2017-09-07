package com.doctor.yuyi.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.squareup.picasso.Picasso;

/**
 * Created by wanyu on 2017/8/31.
 */

public class LookElecAdapter extends BaseAdapter{
    Activity con;
    String[]url;
    public LookElecAdapter(   Activity con, String[]url){
        this.con=con;
        this.url=url;
    }
    @Override
    public int getCount() {
        return url==null?0:url.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(con).inflate(R.layout.ele_item,null);
            hodler=new ViewHodler();
            hodler.imageView= (ImageView) convertView.findViewById(R.id.ele_image);
            convertView.setLayoutParams(new AbsListView.LayoutParams(parent.getWidth() / 3-30,parent.getWidth()/3-30));
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
            convertView.setLayoutParams(new AbsListView.LayoutParams(parent.getWidth() / 3-30,parent.getWidth()/3-30));
        }
        Log.e("url--", Ip.ImgPth+url[position]);
        Log.e("length--",url.length+"--");
        Picasso.with(con).load(Ip.ImgPth+url[position]).error(R.mipmap.error_big).into(hodler.imageView);
        return convertView;
    }
    class ViewHodler{
        ImageView imageView;
    }
}
