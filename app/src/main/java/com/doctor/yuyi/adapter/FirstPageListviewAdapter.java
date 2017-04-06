package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.TodayRecommendBean.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

;

/**
 * Created by liuhaidong on 2017/3/14.
 */

public class FirstPageListviewAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List <Result> mList=new ArrayList<>();

    public FirstPageListviewAdapter(Context mContext, List <Result> mList) {
        this.mContext = mContext;
        this.mList=mList;
        this.mInflater=LayoutInflater.from(this.mContext);
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
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.first_page_listview_item,null);
            viewHolder=new ViewHolder();
            viewHolder.img= (ImageView) convertView.findViewById(R.id.img);
            viewHolder.title= (TextView) convertView.findViewById(R.id.title);
            viewHolder.content= (TextView) convertView.findViewById(R.id.message);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(UrlTools.BASE+mList.get(position).getPicture()).error(R.mipmap.error_small).into(viewHolder.img);
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.content.setText(mList.get(position).getContent());
        return convertView;
    }

    class ViewHolder{
        ImageView img;
        TextView title;
        TextView content;
    }
}
