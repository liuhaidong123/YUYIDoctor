package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.Bean_MyMessage;
import com.doctor.yuyi.bean.Bean_MyMessage_Notifi;
import com.doctor.yuyi.bean.Bean_Test_My_message;
import com.doctor.yuyi.lzh_utils.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by wanyu on 2017/3/27.
 */

public class My_message_notifi_listAdapter extends BaseAdapter {
    private Context context;
    private List<Bean_MyMessage_Notifi.RowsBean> list;
    public My_message_notifi_listAdapter( Context context,List<Bean_MyMessage_Notifi.RowsBean> list){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.my_message_notifi_listitem,null);
            hodler=new ViewHodler();
            hodler.my_message_notifi_list_title= (TextView) convertView.findViewById(R.id.my_message_notifi_list_title);
            hodler.my_message_notifi_list_time= (TextView) convertView.findViewById(R.id.my_message_notifi_list_time);
            hodler.my_message_notifi_list_msg= (TextView) convertView.findViewById(R.id.my_message_notifi_list_msg);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.my_message_notifi_list_msg.setText(list.get(position).getContent());
        hodler.my_message_notifi_list_title.setText(list.get(position).getTitle());
        hodler.my_message_notifi_list_time.setText(list.get(position).getCreateTimeString());
        return convertView;
    }
    class ViewHodler{;
        TextView my_message_notifi_list_time,my_message_notifi_list_title,my_message_notifi_list_msg;//标题，时间，内容
    }
}
