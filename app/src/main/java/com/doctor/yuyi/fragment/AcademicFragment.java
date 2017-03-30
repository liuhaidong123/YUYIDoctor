package com.doctor.yuyi.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.activity.PostActivity;
import com.doctor.yuyi.adapter.CircleAdpater;


/**
 * A simple {@link Fragment} subclass.
 */
public class AcademicFragment extends Fragment implements View.OnClickListener {
    private TextView mHot_tv, mSelect_tv, mNew_tv;//热门，精选，最新
    private View mHot_line, mSelect_line, mNew_line;//线
    private String mColorSelect="#25f368";
    private String mNoSelectColor="#6a6a6a";

    private SwipeRefreshLayout mRefresh;
    private ListView mListview;
    private CircleAdpater mAdapter;

    private ImageView mPostImg;//发帖
    public AcademicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_academic, container, false);
        initView(view);
        return view;
    }


    public void initView(View view) {
        //头部热门，精选，最新
        mHot_tv = (TextView) view.findViewById(R.id.circle_hot_tv);
        mSelect_tv = (TextView) view.findViewById(R.id.circle_select_tv);
        mNew_tv = (TextView) view.findViewById(R.id.circle_new_tv);

        mHot_line = view.findViewById(R.id.circle_hot_line);
        mSelect_line = view.findViewById(R.id.circle_select_line);
        mNew_line = view.findViewById(R.id.circle_new_line);

        mSelect_tv.setOnClickListener(this);
        mNew_tv.setOnClickListener(this);
        mHot_tv.setOnClickListener(this);
        showHotLine();//刚开始显示热门
        //适配器
        mListview= (ListView) view.findViewById(R.id.circle_listview);
        mAdapter=new CircleAdpater(getContext());
        mListview.setAdapter(mAdapter);
        mRefresh= (SwipeRefreshLayout) view.findViewById(R.id.circle_refresh);
        mRefresh.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.color_blood);
        //发帖
        mPostImg= (ImageView) view.findViewById(R.id.post_img);
        mPostImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mHot_tv.getId()) {//热门
            showHotLine();
        } else if (id == mSelect_tv.getId()) {//精选
            showSelectLine();
        } else if (id == mNew_tv.getId()) {//最新
            showNewLine();
        }else if (id == mPostImg.getId()) {//发帖
           startActivity(new Intent(getContext(),PostActivity.class));
        }
    }

    //显示精选
    public void showSelectLine() {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mSelect_tv.measure(spec, spec);
        int measuredWidthTicketNum = mSelect_tv.getMeasuredWidth();

        ViewGroup.LayoutParams params = mSelect_line.getLayoutParams();
        params.width = measuredWidthTicketNum;
        mSelect_line.setLayoutParams(params);
        mSelect_line.setVisibility(View.VISIBLE);
        mHot_line.setVisibility(View.GONE);
        mNew_line.setVisibility(View.GONE);

        mSelect_tv.setTextColor(Color.parseColor(mColorSelect));
        mHot_tv.setTextColor(Color.parseColor(mNoSelectColor));
        mNew_tv.setTextColor(Color.parseColor(mNoSelectColor));
    }

    //显示最新
    public void showNewLine() {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mNew_tv.measure(spec, spec);
        int measuredWidthTicketNum = mNew_tv.getMeasuredWidth();

        ViewGroup.LayoutParams params = mNew_line.getLayoutParams();
        params.width = measuredWidthTicketNum;
        mNew_line.setLayoutParams(params);

        mNew_line.setVisibility(View.VISIBLE);
        mHot_line.setVisibility(View.GONE);
        mSelect_line.setVisibility(View.GONE);

        mSelect_tv.setTextColor(Color.parseColor(mNoSelectColor));
        mHot_tv.setTextColor(Color.parseColor(mNoSelectColor));
        mNew_tv.setTextColor(Color.parseColor(mColorSelect));
    }

    //显示热门
    public void showHotLine() {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mHot_tv.measure(spec, spec);
        int measuredWidthTicketNum = mHot_tv.getMeasuredWidth();

        ViewGroup.LayoutParams params = mHot_line.getLayoutParams();
        params.width = measuredWidthTicketNum;
        mHot_line.setLayoutParams(params);

        mHot_line.setVisibility(View.VISIBLE);
        mNew_line.setVisibility(View.GONE);
        mSelect_line.setVisibility(View.GONE);

        mSelect_tv.setTextColor(Color.parseColor(mNoSelectColor));
        mHot_tv.setTextColor(Color.parseColor(mColorSelect));
        mNew_tv.setTextColor(Color.parseColor(mNoSelectColor));
    }
}
