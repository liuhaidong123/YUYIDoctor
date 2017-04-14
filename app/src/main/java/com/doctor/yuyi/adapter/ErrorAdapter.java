package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.doctor.yuyi.R;

import java.util.List;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by wanyu on 2017/4/14.
 */

public class ErrorAdapter extends BaseAdapter{
    private Context context;
    public ErrorAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_networkerror,null);
        return v;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
