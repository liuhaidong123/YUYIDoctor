package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.doctor.yuyi.R;

/**
 * Created by liuhaidong on 2017/3/28.
 */

public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public CommentAdapter(Context mContext) {
        this.mContext = mContext;
//        this.mInflater=LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return 6;
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
        View view=LayoutInflater.from(mContext).inflate(R.layout.comment_listview_item,null);
        return view;
    }
}
