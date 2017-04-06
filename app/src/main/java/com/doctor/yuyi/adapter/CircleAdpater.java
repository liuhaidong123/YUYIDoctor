package com.doctor.yuyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.activity.CardMessageActivity;
import com.doctor.yuyi.activity.CommentInformationActivity;
import com.doctor.yuyi.activity.StartActivity;

/**
 * Created by liuhaidong on 2017/3/28.
 */

public class CircleAdpater extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private CircleHolder circleHolder;
    public CircleAdpater(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(this.mContext);
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

        if (convertView == null) {
            circleHolder = new CircleHolder();
            convertView = mInflater.inflate(R.layout.circle_listview_item, null);
            circleHolder.title = (TextView) convertView.findViewById(R.id.forumpost_listitem_title);
            circleHolder.content = (TextView) convertView.findViewById(R.id.forumpost_listitem_content);
            circleHolder.praise_num = (TextView) convertView.findViewById(R.id.forumposts_listview_item_postNum);
            circleHolder.comment_num = (TextView) convertView.findViewById(R.id.forumposts_listview_item_msgNum);
            circleHolder.time = (TextView) convertView.findViewById(R.id.forumpost_listitem_time);
            circleHolder.img = (ImageView) convertView.findViewById(R.id.img_head);
            circleHolder.praise_img = (LinearLayout) convertView.findViewById(R.id.forumpost_listitem_layout_post);//点赞
            circleHolder.comment_img = (LinearLayout) convertView.findViewById(R.id.forumpost_listitem_layout_msg);//评论
            convertView.setTag(circleHolder);
        } else {
            circleHolder = (CircleHolder) convertView.getTag();
        }

//        //评论
        final View finalConvertView = convertView;
//        circleHolder.comment_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                circleHolder.praise_img.setFocusable(false);
//                finalConvertView.setFocusable(false);
//                mContext.startActivity(new Intent(mContext, CommentInformationActivity.class));
//            }
//        });
        //点赞
        circleHolder.praise_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleHolder.comment_img.setFocusable(false);
                finalConvertView.setFocusable(false);
                ToastUtils.myToast(mContext,"点赞");
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleHolder.praise_img.setFocusable(false);
                circleHolder.comment_img.setFocusable(false);
                Intent intent=new Intent(mContext,CardMessageActivity.class);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class CircleHolder {
        TextView title;
        TextView content;
        ImageView img;
        LinearLayout praise_img;
        TextView praise_num;
        LinearLayout comment_img;
        TextView comment_num;
        TextView time;
    }
}
