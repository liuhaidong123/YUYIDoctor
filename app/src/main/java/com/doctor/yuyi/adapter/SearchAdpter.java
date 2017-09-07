package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doctor.yuyi.Ip.Ip;
import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.BeanSearch;
import com.doctor.yuyi.lzh_utils.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by wanyu on 2017/4/12.
 */

public class SearchAdpter extends BaseAdapter{
    private List<BeanSearch.RowsBean>list;
    private Context context;
    public SearchAdpter(List<BeanSearch.RowsBean>list,Context context){
        this.list=list;
        this.context=context;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.searchlist_item,null);
            hodler=new ViewHodler();
            hodler.searchlist_item_text= (TextView) convertView.findViewById(R.id.searchlist_item_text);
            hodler.my_patient_image= (RoundImageView) convertView.findViewById(R.id.my_patient_image);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.searchlist_item_text.setText(list.get(position).getTrueName());
        Picasso.with(context).load(Ip.URL+list.get(position).getAvatar()).error(R.mipmap.userdefault).into(hodler.my_patient_image);
        return convertView;
    }

    class ViewHodler{
        TextView searchlist_item_text;
        RoundImageView my_patient_image;
    }
}
