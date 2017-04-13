package com.doctor.yuyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.MyUtils.TimeUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.activity.CardMessageActivity;
import com.doctor.yuyi.bean.CircleBean.Rows;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/3/28.
 */

public class CircleAdpater extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private CircleHolder circleHolder;
    private List<Rows> list = new ArrayList<>();

    public CircleAdpater(Context mContext, List<Rows> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setList(List<Rows> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            circleHolder = new CircleHolder();
            convertView = mInflater.inflate(R.layout.circle_listview_item, null);
            circleHolder.title = (TextView) convertView.findViewById(R.id.forumpost_listitem_title);
            circleHolder.content_tv = (TextView) convertView.findViewById(R.id.forumpost_listitem_content);
            circleHolder.praise_num = (TextView) convertView.findViewById(R.id.forumposts_listview_item_postNum);
            circleHolder.comment_num = (TextView) convertView.findViewById(R.id.forumposts_listview_item_msgNum);
            circleHolder.time = (TextView) convertView.findViewById(R.id.forumpost_listitem_time);
            circleHolder.img = (ImageView) convertView.findViewById(R.id.img_head);
            circleHolder.praise_img = (ImageView) convertView.findViewById(R.id.forumposts_listview_item_postImage);//点赞
            convertView.setTag(circleHolder);
        } else {
            circleHolder = (CircleHolder) convertView.getTag();
        }

        circleHolder.title.setText(list.get(position).getTitle());
        circleHolder.content_tv.setText(list.get(position).getContent());
        circleHolder.time.setText(TimeUtils.getTime(list.get(position).getCreateTimeString()));
        //设置图片
        if (list.get(position).getPicture().equals("")){
            circleHolder.img.setVisibility(View.GONE);
        }else {
            circleHolder.img.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(UrlTools.BASE+list.get(position).getPicture()).error(R.mipmap.error_small).into(circleHolder.img);
        }
        //点赞设值
        if (list.get(position).getLikeNum() == null) {
            circleHolder.praise_num.setText("0");
        } else {
            circleHolder.praise_num.setText(list.get(position).getShareNum() + "");
        }
        //评论设值
        if (list.get(position).getCommentNum() == null) {
            circleHolder.comment_num.setText("0");
        } else {
            circleHolder.comment_num.setText(list.get(position).getCommentNum() + "");
        }

        final View finalConvertView = convertView;
        if (list.get(position).getIsLike()) {
            circleHolder.praise_img.setImageResource(R.mipmap.like_selected);
            //点赞
            circleHolder.praise_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalConvertView.setFocusable(false);
                    //请求点赞接口
                }
            });
        } else {
            circleHolder.praise_img.setImageResource(R.mipmap.like);
            //点赞
            circleHolder.praise_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalConvertView.setFocusable(false);
                    //请求点赞接口
                }
            });
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleHolder.praise_img.setFocusable(false);
                Intent intent = new Intent(mContext, CardMessageActivity.class);
                intent.putExtra("id",list.get(position).getId());
                Log.e("id",list.get(position).getId()+"");
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class CircleHolder {
        TextView title;
        TextView content_tv;
        ImageView img;
        ImageView praise_img;
        TextView praise_num;
        TextView comment_num;
        TextView time;
    }
}
