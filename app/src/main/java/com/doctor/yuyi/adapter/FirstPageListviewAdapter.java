package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.doctor.yuyi.R;

import java.util.ArrayList;
import java.util.List;

;

/**
 * Created by liuhaidong on 2017/3/14.
 */

public class FirstPageListviewAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List <String> mList=new ArrayList<>();

    public FirstPageListviewAdapter(Context mContext, List <String> mList) {
        this.mContext = mContext;
        this.mList=mList;
        this.mInflater=LayoutInflater.from(this.mContext);
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
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
        convertView=mInflater.inflate(R.layout.first_page_listview_item,null);
        return convertView;
    }
}
