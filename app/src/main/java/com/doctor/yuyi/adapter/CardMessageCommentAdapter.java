package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.myview.RoundImageView;

/**
 * Created by liuhaidong on 2017/3/29.
 */

public class CardMessageCommentAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public CardMessageCommentAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater=LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return 5;
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

        CardCommentHolder holder=null;
        if (convertView==null){
            holder=new CardCommentHolder();
            convertView=mInflater.inflate(R.layout.card_comment_listview_item,null);
            holder.img= (RoundImageView) convertView.findViewById(R.id.head_img);
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.time= (TextView) convertView.findViewById(R.id.time);
            holder.content_tv= (TextView) convertView.findViewById(R.id.content);
            holder.praise_img= (ImageView) convertView.findViewById(R.id.car_user_praise_img);
            convertView.setTag(holder);

        }else {
            holder= (CardCommentHolder) convertView.getTag();
        }
        return convertView;
    }

    class CardCommentHolder{
        RoundImageView img;
        TextView name;
        TextView time;
        TextView content_tv;
        ImageView praise_img;
    }
}
