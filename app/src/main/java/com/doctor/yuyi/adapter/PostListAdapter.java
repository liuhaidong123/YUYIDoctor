package com.doctor.yuyi.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.lzh_utils.BitMapUtils;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;


/**
 * Created by wanyu on 2017/4/11.
 */

public class PostListAdapter extends BaseAdapter{
    private Context context;
    private List<String> list;
    public PostListAdapter(Context context,List<String> list){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.post_listview_item,null);
            hodler=new ViewHodler();
            hodler.post_listitem_img= (ImageView) convertView.findViewById(R.id.post_listitem_img);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        ViewGroup.LayoutParams params=hodler.post_listitem_img.getLayoutParams();
        params.width= BitMapUtils.getWindowWidth(context);
        params.height=BitMapUtils.getWindowWidth(context);
        hodler.post_listitem_img.setLayoutParams(params);
        Picasso.with(context).load(list.get(position)).centerCrop()
                .resize(BitMapUtils.getWindowWidth(context),BitMapUtils.getWindowWidth(context)).error(R.mipmap.logo).into(hodler.post_listitem_img);
        return convertView;
    }

    class ViewHodler{
        ImageView post_listitem_img;
    }

}
