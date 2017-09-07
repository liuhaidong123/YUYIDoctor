package com.doctor.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private List<Result> mList = new ArrayList<>();

    public FirstPageListviewAdapter(Context mContext, List<Result> mList) {
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

        ViewHolder viewHolder = null;
        FirstHeaderHolder firstHeaderHolder = null;

        if (position == 0) {//资讯首页的头部
            View firstView = mInflater.inflate(R.layout.first_header_layout, null);
            firstHeaderHolder = new FirstHeaderHolder();
            firstHeaderHolder.bg_rl = (RelativeLayout) firstView.findViewById(R.id.bg_rl);
            firstHeaderHolder.imageView = (ImageView) firstView.findViewById(R.id.img_first);
            firstHeaderHolder.title = (TextView) firstView.findViewById(R.id.first_title);

            firstHeaderHolder.bg_rl.getBackground().setAlpha(55);
            Picasso.with(mContext).load(UrlTools.BASE + mList.get(position).getPicture()).error(R.mipmap.error_big).into(firstHeaderHolder.imageView);
            firstHeaderHolder.title.setText(mList.get(position).getTitle().trim());
            return firstView;
        } else {
            View viewAll = mInflater.inflate(R.layout.first_page_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) viewAll.findViewById(R.id.img);
            viewHolder.title = (TextView) viewAll.findViewById(R.id.title);
            viewHolder.comment_num = (TextView) viewAll.findViewById(R.id.comment_num);

            Picasso.with(mContext).load(UrlTools.BASE + mList.get(position).getPicture()).error(R.mipmap.error_small).into(viewHolder.img);
            viewHolder.title.setText(mList.get(position).getTitle().trim());
            viewHolder.comment_num.setText(mList.get(position).getCommentNum()+"评");
            return viewAll;
        }


    }

    class ViewHolder {
        ImageView img;
        TextView title;
        TextView comment_num;
    }

    class FirstHeaderHolder {
        RelativeLayout bg_rl;
        ImageView imageView;
        TextView title;
    }
}
