package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.BeanSearch;

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
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.searchlist_item_text.setText(list.get(position).getTrueName());
        return convertView;
    }

    class ViewHodler{
        TextView searchlist_item_text;
    }
}
