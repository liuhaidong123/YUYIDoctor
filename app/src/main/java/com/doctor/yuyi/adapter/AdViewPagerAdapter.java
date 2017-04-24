package com.doctor.yuyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.doctor.yuyi.HttpTools.UrlTools;
import com.doctor.yuyi.R;
import com.doctor.yuyi.activity.InformationMessageActivity;
import com.doctor.yuyi.bean.AdBean.Result;
import com.doctor.yuyi.bean.MyEntity;
import com.doctor.yuyi.fragment.InformationFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuhaidong on 2017/3/16.
 */

public class AdViewPagerAdapter extends PagerAdapter {
    private List<Result> mList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public AdViewPagerAdapter(List<Result> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setmList(List<Result> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size() == 1 ? 1 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.ad_viewpager, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_ad);

            Picasso.with(mContext).load(UrlTools.BASE + mList.get(InformationFragment.mSelectPosition).getPicture()).error(R.mipmap.error_small).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mList!=null&&mList.size()>0){
                        Intent intent = new Intent(mContext, InformationMessageActivity.class);
                        intent.putExtra("id", mList.get(InformationFragment.mSelectPosition).getId());
                        mContext.startActivity(intent);
                    }
                }
            });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
