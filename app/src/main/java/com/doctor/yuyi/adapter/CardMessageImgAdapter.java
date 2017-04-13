package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.R;
import com.squareup.picasso.Picasso;

/**
 * Created by liuhaidong on 2017/3/29.
 */

public class CardMessageImgAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private String[] str = new String[0];


    public CardMessageImgAdapter(Context mContext, String[] str) {
        this.mContext = mContext;
        this.str = str;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setStr(String[] str) {
        this.str = str;
    }

    @Override
    public int getCount() {
        return str.length;
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
        ImgHolder holder = null;
        if (convertView == null) {
            holder = new ImgHolder();
            convertView = mInflater.inflate(R.layout.card_img_litsview_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_img);
            convertView.setTag(holder);
        } else {
            holder = (ImgHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(UrlTools.BASE + str[position]).error(R.mipmap.error_big).into(holder.img);
        return convertView;
    }

    class ImgHolder {
        ImageView img;
    }
}
