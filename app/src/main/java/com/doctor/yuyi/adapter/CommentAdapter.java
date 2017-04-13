package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.MyUtils.TimeUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.CommendListBean.Result;
import com.doctor.yuyi.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/3/28.
 */

public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Result> mList = new ArrayList<>();

    public CommentAdapter(Context mContext, List<Result> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setmList(List<Result> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommendHolder commendHolder=null;
        if (convertView==null){
            convertView = mInflater.inflate(R.layout.comment_listview_item, null);
            commendHolder=new CommendHolder();
            commendHolder.img= (RoundImageView) convertView.findViewById(R.id.comment_userhead);
            commendHolder.name= (TextView) convertView.findViewById(R.id.comment_username);
            commendHolder.time= (TextView) convertView.findViewById(R.id.comment_time);
            commendHolder.content= (TextView) convertView.findViewById(R.id.comment_content);
            convertView.setTag(commendHolder);
        }else {
            commendHolder= (CommendHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(UrlTools.BASE+mList.get(position).getAvatar()).error(R.mipmap.error_small).into(commendHolder.img);
        commendHolder.name.setText(mList.get(position).getTrueName());
        String str="";
        try {
            str =  URLDecoder.decode(mList.get(position).getContent(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        commendHolder.content.setText(str);
        commendHolder.time.setText(TimeUtils.getTime(mList.get(position).getCreateTimeString()));
        return convertView;
    }

    class CommendHolder{
        RoundImageView img;
        TextView name;
        TextView time;
        TextView content;
    }
}
