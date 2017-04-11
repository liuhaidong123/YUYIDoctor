package com.doctor.yuyi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.Bean_MyMessage;
import com.doctor.yuyi.lzh_utils.RoundImageView;
import com.doctor.yuyi.myview.MyImageView;
import com.squareup.picasso.Picasso;


import java.util.List;



/**
 * Created by wanyu on 2017/3/27.
 */

public class My_message_listViewAdapter extends BaseAdapter{
    private Context context;
    private List<Bean_MyMessage.RowsBean>list;
    public My_message_listViewAdapter( Context context,List<Bean_MyMessage.RowsBean>list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.my_message_listview_item,null);
            hodler=new ViewHodler();
            hodler.my_message_titleImage_item= (MyImageView) convertView.findViewById(R.id.my_message_titleImage_item);
            hodler.my_message_notifi_name_item= (TextView) convertView.findViewById(R.id.my_message_notifi_name_item);
            hodler.my_message_notifi_time_item= (TextView) convertView.findViewById(R.id.my_message_notifi_time_item);
            hodler.my_message_notifi_msg_item= (TextView) convertView.findViewById(R.id.my_message_notifi_msg_item);
            convertView.setTag(hodler);
        }
        hodler= (ViewHodler) convertView.getTag();
        if (list.get(position).isIsRead()==true){
            hodler.my_message_titleImage_item.setIsRead(true);
        }
        else {
            hodler.my_message_titleImage_item.setIsRead(false);
        }
        Picasso.with(context).load(Ip.ImgPth+list.get(position).getAvatar()).error(R.mipmap.logo).into(hodler.my_message_titleImage_item);
        hodler.my_message_notifi_msg_item.setText(list.get(position).getContent());
        hodler.my_message_notifi_name_item.setText(list.get(position).getTitle());
        hodler.my_message_notifi_time_item.setText(list.get(position).getCreateTimeString());
        return convertView;
    }
    class ViewHodler{
        MyImageView my_message_titleImage_item;
        TextView my_message_notifi_name_item,my_message_notifi_time_item,my_message_notifi_msg_item;//标题，时间，内容
    }
}
