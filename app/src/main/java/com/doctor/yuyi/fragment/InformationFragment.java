package com.doctor.yuyi.fragment;


import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.MyUtils.MyDialog;
import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.activity.InformationMessageActivity;
import com.doctor.yuyi.adapter.AdViewPagerAdapter;
import com.doctor.yuyi.adapter.FirstPageListviewAdapter;
import com.doctor.yuyi.bean.AdBean.Result;
import com.doctor.yuyi.bean.AdBean.Root;
import com.doctor.yuyi.myview.InformationListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private LinearLayout mRecommend_ll,mNew_LL,mHot_ll;

    private TextView mToday_tv;
    private TextView mNew_tv;
    private TextView mHot_tv;
    private View mToday_line;
    private View mNew_line;
    private View mHot_line;

    private FirstPageListviewAdapter mFirstPageAdapter;
    private InformationListView mMyListview;
    private RelativeLayout mScrollRelative;
    private List<com.doctor.yuyi.bean.TodayRecommendBean.Result> mList = new ArrayList<>();
    private SwipeRefreshLayout mRefreshLayout;

    private RelativeLayout mRefreshBox;
    private ProgressBar mBar;

    private Handler mHttpHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


           if (msg.what == 3) {//今日推荐,最新，热门
                Object o = msg.obj;
                if (o != null && o instanceof com.doctor.yuyi.bean.TodayRecommendBean.Root) {
                    com.doctor.yuyi.bean.TodayRecommendBean.Root root = (com.doctor.yuyi.bean.TodayRecommendBean.Root) o;
                    if (root.getCode().equals("0")) {
                        MyDialog.stopDia();
                        mRefreshLayout.setRefreshing(false);
                        mBar.setVisibility(View.INVISIBLE);
                        List<com.doctor.yuyi.bean.TodayRecommendBean.Result> list = new ArrayList<>();
                        list = root.getResult();
                        mList.addAll(list);
                        mFirstPageAdapter.setmList(mList);
                        mFirstPageAdapter.notifyDataSetChanged();
                        if (list.size() < 10) {//隐藏加载更多
                            mRefreshBox.setVisibility(View.GONE);
                        } else {
                            mRefreshBox.setVisibility(View.VISIBLE);
                        }
                    }
                }

            } else if (msg.what == 102) {
                mRefreshBox.setVisibility(View.GONE);
                mFirstPageAdapter.setmList(mList);
                mFirstPageAdapter.notifyDataSetChanged();
                MyDialog.stopDia();
                mBar.setVisibility(View.INVISIBLE);
                mRefreshLayout.setRefreshing(false);
            }
        }
    };

    private HttpTools mHttptools;
    private int mStart = 0;
    private int mLimit = 10;
    private int mFlag = 0;

    public boolean isLoop = true;
    public InformationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_information, container, false);
        init(view);
        return view;
    }

    //初始化数据
    public void init(View view) {

        mHttptools = HttpTools.getHttpToolsInstance();
       // mHttptools.getADMessage(mHttpHandler);//获取广告数据
        mHttptools.getTodayRecommend(mHttpHandler, mStart, mLimit);//今日推荐

        //今日推荐，最新，热门
        mRecommend_ll= (LinearLayout) view.findViewById(R.id.recommend_ll);
        mNew_LL= (LinearLayout) view.findViewById(R.id.new_ll);
        mHot_ll= (LinearLayout) view.findViewById(R.id.hot_ll);
        mRecommend_ll.setOnClickListener(this);
        mNew_LL.setOnClickListener(this);
        mHot_ll.setOnClickListener(this);

        mToday_tv = (TextView) view.findViewById(R.id.today_tv);
        mNew_tv = (TextView) view.findViewById(R.id.new_tv);
        mHot_tv = (TextView) view.findViewById(R.id.hot_tv);
        mToday_line = view.findViewById(R.id.today_line);
        mNew_line = view.findViewById(R.id.new_line);
        mHot_line = view.findViewById(R.id.hot_line);
        showTodayLine();

        //加载更多
        mRefreshBox = (RelativeLayout) view.findViewById(R.id.more_relative);
        mRefreshBox.setOnClickListener(this);
        mBar = (ProgressBar) view.findViewById(R.id.pbLocate);
        //listview
        mMyListview = (InformationListView) view.findViewById(R.id.information_listview);
        mFirstPageAdapter = new FirstPageListviewAdapter(getContext(), mList);
        mMyListview.setAdapter(mFirstPageAdapter);
        //
        mMyListview.setOnItemClickListener(this);
        mScrollRelative = (RelativeLayout) view.findViewById(R.id.scroll_relative);
        //将资讯页面定位到顶部
        mScrollRelative.setFocusable(true);
        mScrollRelative.setFocusableInTouchMode(true);
        mScrollRelative.requestFocus();
        //刷新
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.first_page_swiperefesh);
        mRefreshLayout.setColorSchemeResources(R.color.color_delete, R.color.color_username);
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStart = 0;
                mList.clear();
                showTodayLine();//刷新的时候回到今日推荐
                mHttptools.getTodayRecommend(mHttpHandler, mStart, mLimit);//今日推荐
                mHttptools.getADMessage(mHttpHandler);//获取广告数据
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), InformationMessageActivity.class);
        intent.putExtra("id", mList.get(position).getId());
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mRecommend_ll.getId()) {//今日推荐
            mFlag = 0;
            mStart = 0;
            showTodayLine();
            MyDialog.showDialog(this.getActivity());
            mList.clear();
            mHttptools.getTodayRecommend(mHttpHandler, mStart, mLimit);
            mFirstPageAdapter.setmList(mList);
            mFirstPageAdapter.notifyDataSetChanged();
            mRefreshBox.setVisibility(View.GONE);
        } else if (id == mNew_LL.getId()) {//最新
            mFlag = 1;
            mStart = 0;
            showNewLine();
            MyDialog.showDialog(this.getActivity());
            mList.clear();
            mHttptools.getNew(mHttpHandler, mStart, mLimit);
            mFirstPageAdapter.setmList(mList);
            mFirstPageAdapter.notifyDataSetChanged();
            mRefreshBox.setVisibility(View.GONE);
        } else if (id == mHot_ll.getId()) {//热门
            mFlag = 2;
            mStart = 0;
            MyDialog.showDialog(this.getActivity());
            mList.clear();
            mHttptools.getHot(mHttpHandler, mStart, mLimit);
            showHotLine();
            mFirstPageAdapter.setmList(mList);
            mFirstPageAdapter.notifyDataSetChanged();
            mRefreshBox.setVisibility(View.GONE);
        } else if (id == mRefreshBox.getId()) {//加载更多
            mBar.setVisibility(View.VISIBLE);
            mStart += 10;
            if (mFlag == 0) {//加载的是今日推荐
                mHttptools.getTodayRecommend(mHttpHandler, mStart, mLimit);//今日推荐
            } else if (mFlag == 1) {//加载的是最新
                mHttptools.getNew(mHttpHandler, mStart, mLimit);
            } else if (mFlag == 2) {//加载的是热门
                mHttptools.getHot(mHttpHandler, mStart, mLimit);
            }

        }
    }

    //显示今日推荐
    public void showTodayLine() {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mToday_tv.measure(spec, spec);
        int measuredWidthTicketNum = mToday_tv.getMeasuredWidth();

        ViewGroup.LayoutParams params = mToday_line.getLayoutParams();
        params.width = measuredWidthTicketNum;
        mToday_line.setLayoutParams(params);
        mToday_line.setVisibility(View.VISIBLE);
        mHot_line.setVisibility(View.GONE);
        mNew_line.setVisibility(View.GONE);

        mToday_tv.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.navigate_color));
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mToday_tv.setTypeface(font);

        mNew_tv.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.navigate_color));
        Typeface font2 = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mNew_tv.setTypeface(font2);

        mHot_tv.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.navigate_color));
        Typeface font3 = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mHot_tv.setTypeface(font3);
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
        mToday_line.setVisibility(View.GONE);

        mToday_tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.navigate_color));
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mToday_tv.setTypeface(font);

        mNew_tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.navigate_color));
        Typeface font2 = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mNew_tv.setTypeface(font2);

        mHot_tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.navigate_color));
        Typeface font3 = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mHot_tv.setTypeface(font3);
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
        mToday_line.setVisibility(View.GONE);

        mToday_tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.navigate_color));
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mToday_tv.setTypeface(font);

        mNew_tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.navigate_color));
        Typeface font2 = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mNew_tv.setTypeface(font2);
        mHot_tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.navigate_color));
        Typeface font3 = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mHot_tv.setTypeface(font3);
    }



    @Override
    public void onDestroy() {
        isLoop=false;
        super.onDestroy();
    }
}
