package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.Bean_Test_My_message;
import com.doctor.yuyi.lzh_utils.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by wanyu on 2017/3/27.
 */

public class My_message_listViewAdapter extends BaseAdapter{
    private Context context;
    private List<Bean_Test_My_message> list;
    public My_message_listViewAdapter( Context context,List<Bean_Test_My_message> list){
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
            hodler.my_message_titleImage_item= (RoundImageView) convertView.findViewById(R.id.my_message_titleImage_item);
            hodler.my_message_notifi_name_item= (TextView) convertView.findViewById(R.id.my_message_notifi_name_item);
            hodler.my_message_notifi_time_item= (TextView) convertView.findViewById(R.id.my_message_notifi_time_item);
            hodler.my_message_notifi_msg_item= (TextView) convertView.findViewById(R.id.my_message_notifi_msg_item);
            convertView.setTag(hodler);
        }
        hodler= (ViewHodler) convertView.getTag();
        Picasso.with(context).load(list.get(position).getImaPth()).error(R.mipmap.doc).into(hodler.my_message_titleImage_item);
        hodler.my_message_notifi_msg_item.setText(list.get(position).getMsg());
        hodler.my_message_notifi_name_item.setText(list.get(position).getTitle());
        hodler.my_message_notifi_time_item.setText(list.get(position).getTime());
        return convertView;
    }
    class ViewHodler{
        RoundImageView my_message_titleImage_item;
        TextView my_message_notifi_name_item,my_message_notifi_time_item,my_message_notifi_msg_item;//标题，时间，内容
    }
}
