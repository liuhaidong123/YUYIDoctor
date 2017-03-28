package com.doctor.yuyi.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.R;
import com.doctor.yuyi.activity.InformationMessageActivity;
import com.doctor.yuyi.adapter.AdViewPagerAdapter;
import com.doctor.yuyi.adapter.FirstPageListviewAdapter;
import com.doctor.yuyi.bean.MyEntity;
import com.doctor.yuyi.myview.InformationListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, ViewPager.OnPageChangeListener {
    private TextView mToday_tv;
    private TextView mNew_tv;
    private TextView mHot_tv;
    private View mToday_line;
    private View mNew_line;
    private View mHot_line;

    private FirstPageListviewAdapter mFirstPageAdapter;
    private InformationListView mMyListview;
    private RelativeLayout mScrollRelative;
    private List<String> mList = new ArrayList<>();
    private SwipeRefreshLayout mRefreshLayout;

    private RelativeLayout mRefreshBox;
    private ProgressBar mBar;

    private ViewPager mViewPager;
    private AdViewPagerAdapter mImgAapter;
    private List<MyEntity> mAdList = new ArrayList<>();
    private ImageView[] mArrImageView;//存放广告小圆点的数组
    private ImageView mCircleImg;//广告小圆点
    private ViewGroup mGroup;//存放小圆点容器
    private Handler mAdHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (mAdHandler.hasMessages(1)) {
                mAdHandler.removeMessages(1);
            }
            if (msg.what == 1) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                mAdHandler.sendEmptyMessageDelayed(1, 3000);
            }
        }
    };


    public InformationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        init(view);
        return view;
    }

    //初始化数据
    public void init(View view) {
        //广告
        mAdList.add(new MyEntity("图片1", "我的图片1", R.mipmap.item01));
        mAdList.add(new MyEntity("图片2", "我的图片2", R.mipmap.item02));
        mAdList.add(new MyEntity("图片3", "我的图片3", R.mipmap.item03));
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_title);
        mImgAapter = new AdViewPagerAdapter(mAdList, getContext());
        mViewPager.setAdapter(mImgAapter);
        mViewPager.setCurrentItem(0);
        if (mAdList.size() > 1) {
            mViewPager.addOnPageChangeListener(this);
        }
        //初始化存放小圆点的容器与viewpager
        mGroup = (ViewGroup) view.findViewById(R.id.viewGroup);
        setADCircleImg();
        mAdHandler.sendEmptyMessageDelayed(1, 3000);

        //今日推荐，最新，热门
        mToday_tv = (TextView) view.findViewById(R.id.today_tv);
        mNew_tv = (TextView) view.findViewById(R.id.new_tv);
        mHot_tv = (TextView) view.findViewById(R.id.hot_tv);
        mToday_line = view.findViewById(R.id.today_line);
        mNew_line = view.findViewById(R.id.new_line);
        mHot_line = view.findViewById(R.id.hot_line);
        showTodayLine();
        mToday_tv.setOnClickListener(this);
        mNew_tv.setOnClickListener(this);
        mHot_tv.setOnClickListener(this);
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mRefreshBox = (RelativeLayout) view.findViewById(R.id.more_relative);
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
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "刷新完成", Toast.LENGTH_SHORT).show();
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getContext(),InformationMessageActivity.class);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mToday_tv.getId()) {//今日推荐
            showTodayLine();
        } else if (id == mNew_tv.getId()) {//最新
            showNewLine();
        } else if (id == mHot_tv.getId()) {//热门
            showHotLine();
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
    }

    //轮播图监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //选定某一页时
    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % mAdList.size());
    }

    //滑动状态改变
    @Override
    public void onPageScrollStateChanged(int state) {
        //手指开始滑动
        if (state == mViewPager.SCROLL_STATE_DRAGGING) {
            mAdHandler.removeMessages(1);
            mRefreshLayout.setEnabled(false);
            //手指松开后自动滑动
        } else if (state == mViewPager.SCROLL_STATE_SETTLING) {
            mAdHandler.removeMessages(1);
            mRefreshLayout.setEnabled(true);
            //停在某一页
        } else {
            mAdHandler.sendEmptyMessageDelayed(1, 3000);
            mRefreshLayout.setEnabled(true);
        }
    }

    /**
     * 初始化广告轮播图的小图标，
     */
    public void setADCircleImg() {
        //只有轮播的图片张数不为0时，才执行下面内容
        if (mAdList.size() != 0) {
            //将小圆点根据条件添加到容器中
            mArrImageView = new ImageView[mAdList.size()];
            for (int i = 0; i < mAdList.size(); i++) {
                mCircleImg = new ImageView(this.getContext());
                //小圆点的布局
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMarginStart(14);
                //给小圆点设置布局
                mCircleImg.setLayoutParams(layoutParams);
                //为存放小圆点的数组赋值
                mArrImageView[i] = mCircleImg;
                //当轮播的图片为一张时，不用设置小圆圈
                if (mAdList.size() == 1) {
                    break;
                }
                //默认第一页为白色的小圆圈(前提必须是轮播的图片大于1张)
                if (i == 0) {
                    mCircleImg.setBackgroundResource(R.mipmap.select_ad);
                } else {
                    mCircleImg.setBackgroundResource(R.mipmap.no_select_ad);
                }
                //将每一个小圆点添加到容器中
                mGroup.addView(mCircleImg);
            }
        }
    }


    /**
     * 停在某一页时，变换小圆点
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < mArrImageView.length; i++) {
            if (i == selectItems) {
                mArrImageView[i].setBackgroundResource(R.mipmap.select_ad);
            } else {
                mArrImageView[i].setBackgroundResource(R.mipmap.no_select_ad);
            }
        }
    }
}
