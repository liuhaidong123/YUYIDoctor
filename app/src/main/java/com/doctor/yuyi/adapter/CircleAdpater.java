package com.doctor.yuyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.MyUtils.TimeUtils;
import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.activity.CardMessageActivity;
import com.doctor.yuyi.bean.CircleBean.Rows;
import com.doctor.yuyi.bean.InformationPraise.Root;
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
    private int mPosition;
    private List<Rows> list = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 9) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getCode().equals("0")) {
                        if (root.getResult().equals("点赞成功")) {
                            list.get(mPosition).setIsLike(true);
                            if (list.get(mPosition).getLikeNum() == null) {
                                list.get(mPosition).setLikeNum(1);
                            } else {
                                list.get(mPosition).setLikeNum(list.get(mPosition).getLikeNum() + 1);
                            }
                            notifyDataSetChanged();
                        } else {
                            list.get(mPosition).setIsLike(false);
                            list.get(mPosition).setLikeNum(list.get(mPosition).getLikeNum() - 1);
                            notifyDataSetChanged();
                        }
                    }
                }
            } else if (msg.what == 109) {
                ToastUtils.myToast(mContext, "点赞失败");
            }
        }
    };
    private HttpTools httpTools;

    public CircleAdpater(Context mContext, List<Rows> list) {
        httpTools = HttpTools.getHttpToolsInstance();
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
        if (list.get(position).getPicture().equals("")||list.get(position).getPicture()==null) {
            circleHolder.img.setVisibility(View.GONE);
        } else {
            circleHolder.img.setVisibility(View.VISIBLE);
            String [] str=list.get(position).getPicture().split(";");
            Picasso.with(mContext).load(UrlTools.BASE + str[0]).error(R.mipmap.error_small).into(circleHolder.img);
        }
        //点赞设值
        if (list.get(position).getLikeNum() == null) {
            circleHolder.praise_num.setText("0");
        } else {
            circleHolder.praise_num.setText(list.get(position).getLikeNum() + "");
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
        } else {
            circleHolder.praise_img.setImageResource(R.mipmap.like);
        }
        //点赞
        circleHolder.praise_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = position;
                finalConvertView.setFocusable(false);
                //请求点赞接口
                httpTools.circlePraise(handler, list.get(position).getId(), UserInfo.UserToken);
                Log.e("IsLike-mPosition", position + "");
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                circleHolder.praise_img.setFocusable(false);
                Intent intent = new Intent(mContext, CardMessageActivity.class);
                intent.putExtra("id", list.get(position).getId());
                Log.e("mPosition", position + "");
                Log.e("id", list.get(position).getId() + "");
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
