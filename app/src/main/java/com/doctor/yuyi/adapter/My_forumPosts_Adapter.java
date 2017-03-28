package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doctor.yuyi.R;

import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/3/27.
 */

public class My_forumPosts_Adapter extends BaseAdapter{
    private List<Map<String,String>> list;
    private Context context;
    public My_forumPosts_Adapter(List<Map<String,String>> list,Context context){
        this.list=list;
        this.context=context;
    }
    @Override
    public int getCount() {
//        return list==null?0:list.size();
        return 5;
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
            hodler=new ViewHodler();
            convertView= LayoutInflater.from(context).inflate(R.layout.forumposts_listview_item,null);
            hodler.forumpost_listitem_title= (TextView) convertView.findViewById(R.id.forumpost_listitem_title);

            hodler.forumpost_listitem_content= (TextView) convertView.findViewById(R.id.forumpost_listitem_content);

            hodler.forumpost_listitem_time= (TextView) convertView.findViewById(R.id.forumpost_listitem_time);

            hodler.forumposts_listview_item_msgNum= (TextView) convertView.findViewById(R.id.forumposts_listview_item_msgNum);

            hodler.forumposts_listview_item_postNum= (TextView) convertView.findViewById(R.id.forumposts_listview_item_postNum);

            hodler.forumposts_listview_item_postImage= (ImageView) convertView.findViewById(R.id.forumposts_listview_item_postImage);

            hodler.forumposts_listview_item_msgImage= (ImageView) convertView.findViewById(R.id.forumposts_listview_item_msgImage);

            hodler.forumpost_listitem_layout_msg= (LinearLayout) convertView.findViewById(R.id.forumpost_listitem_layout_msg);

            hodler.forumpost_listitem_layout_post= (LinearLayout) convertView.findViewById(R.id.forumpost_listitem_layout_post);
            convertView.setTag(hodler);
        }
        hodler= (ViewHodler) convertView.getTag();

        return convertView;
    }

    class ViewHodler{
        TextView forumpost_listitem_title,forumpost_listitem_content,forumpost_listitem_time;//标题，内容，时间
        TextView forumposts_listview_item_msgNum,forumposts_listview_item_postNum;//评论代数量，点赞的数量
        ImageView forumposts_listview_item_postImage,forumposts_listview_item_msgImage;//点赞，评论的image
        LinearLayout forumpost_listitem_layout_post,forumpost_listitem_layout_msg;//评论，点赞的layout;
    }
}
