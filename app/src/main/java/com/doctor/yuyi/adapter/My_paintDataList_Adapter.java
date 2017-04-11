package com.doctor.yuyi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.Bean_MyPaintList;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/3/28.
 */

public class My_paintDataList_Adapter  extends BaseAdapter{
    private Context context;
    private  List<Bean_MyPaintList.RowsBean> list;
    public  My_paintDataList_Adapter(Context context, List<Bean_MyPaintList.RowsBean> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        //-------------------------许改动--------------------------
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.my_patientdatalist_item,null);
            hodler=new ViewHodler();
            hodler.my_patientList_image= (ImageView) convertView.findViewById(R.id.my_patientList_image);
            hodler.my_patientList_textVname= (TextView) convertView.findViewById(R.id.my_patientList_textVname);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
            }
        Picasso.with(context).load(Ip.URL+list.get(position).getAvatar()).error(R.mipmap.usererr).into(hodler.my_patientList_image);
        String trueName=list.get(position).getTrueName();
        if ("".equals(trueName)&&TextUtils.isEmpty(trueName)){
            trueName="匿名";
        }
        hodler.my_patientList_textVname.setText(trueName);
        return convertView;
    }
    class ViewHodler{
        ImageView my_patientList_image;
        TextView my_patientList_textVname;
    }
}
