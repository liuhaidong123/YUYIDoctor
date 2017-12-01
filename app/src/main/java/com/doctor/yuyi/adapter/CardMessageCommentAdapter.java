package com.doctor.yuyi.adapter;

import android.content.Context;
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
import com.doctor.yuyi.activity.CommentInformationActivity;
import com.doctor.yuyi.bean.CircleMessageBean.CommentList;
import com.doctor.yuyi.bean.InformationPraise.Root;
import com.doctor.yuyi.myview.RoundImageView;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/3/29.
 */

public class CardMessageCommentAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CommentList> list = new ArrayList<>();
    private int mPosition;

    private HttpTools httpTools;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==13){
                Object o=msg.obj;
                if (o!=null&& o instanceof Root){
                    Root root= (Root) o;
                    if (root.getCode().equals("0")) {
                        if (root.getResult().equals("点赞成功")) {
                            list.get(mPosition).setLike(true);
                            if (list.get(mPosition).getLikeNum() == null) {
                                list.get(mPosition).setLikeNum(1);
                            } else {
                                list.get(mPosition).setLikeNum(list.get(mPosition).getLikeNum() + 1);
                            }
                            notifyDataSetChanged();

                        } else if (root.getResult().equals("撤销点赞成功")) {
                            list.get(mPosition).setLike(false);
                            list.get(mPosition).setLikeNum(list.get(mPosition).getLikeNum() - 1);
                            notifyDataSetChanged();
                        }
                    } else {
                        ToastUtils.myToast(mContext, "点赞失败");
                    }
                }
            }else if (msg.what==113){
                ToastUtils.myToast(mContext, "数据错误");
            }
        }
    };
    public CardMessageCommentAdapter(Context mContext, List<CommentList> list) {
        this.mContext = mContext;
        this.list = list;
        this.mInflater = LayoutInflater.from(this.mContext);
        httpTools=HttpTools.getHttpToolsInstance();
    }

    public void setList(List<CommentList> list) {
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

        CardCommentHolder holder = null;
        if (convertView == null) {
            holder = new CardCommentHolder();
            convertView = mInflater.inflate(R.layout.card_comment_listview_item, null);
            holder.img = (RoundImageView) convertView.findViewById(R.id.head_img);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.content_tv = (TextView) convertView.findViewById(R.id.content);
            holder.praise_img = (ImageView) convertView.findViewById(R.id.car_user_praise_img);
            holder.praise_num = (TextView) convertView.findViewById(R.id.card_user_praise_num);
            convertView.setTag(holder);

        } else {
            holder = (CardCommentHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(UrlTools.BASE + list.get(position).getAvatar()).error(R.mipmap.userdefault).into(holder.img);
        holder.name.setText(list.get(position).getTrueName());
        holder.time.setText(TimeUtils.getTime(list.get(position).getCreateTimeString()));
//        String str="";
//        try {
//            str= URLDecoder.decode(list.get(position).getContent(),"utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        holder.content_tv.setText(str);
        holder.content_tv.setText(list.get(position).getContent());
        if (list.get(position).getLikeNum() == null) {
            holder.praise_num.setText("0");
        } else {
            holder.praise_num.setText(list.get(position).getLikeNum() + "");
        }

        if (list.get(position).isLike()) {
            holder.praise_img.setImageResource(R.mipmap.like_selected);
        } else {
            holder.praise_img.setImageResource(R.mipmap.like);
        }

        holder.praise_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = position;
                httpTools.circleCommendPrise(handler,list.get(position).getId(), UserInfo.UserToken);
                Log.e("id",list.get(position).getId()+"");
                Log.e("token",UserInfo.UserToken);
            }
        });

        return convertView;
    }

    class CardCommentHolder {
        RoundImageView img;
        TextView name;
        TextView time;
        TextView content_tv;
        ImageView praise_img;
        TextView praise_num;
    }
}
