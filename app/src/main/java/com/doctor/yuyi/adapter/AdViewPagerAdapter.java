package com.doctor.yuyi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.bean.MyEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by liuhaidong on 2017/3/16.
 */

public class AdViewPagerAdapter extends PagerAdapter {
    private List<MyEntity> mList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public AdViewPagerAdapter(List<MyEntity> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return  mList.size() == 1 ? 1 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.ad_viewpager, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_ad);
        Picasso.with(mContext).load(mList.get(position % mList.size()).getId()).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
