package com.doctor.yuyi.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.MyUtils.MyDialog;
import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.activity.CardMessageActivity;
import com.doctor.yuyi.activity.My_message_Activity;
import com.doctor.yuyi.activity.PostActivity;
import com.doctor.yuyi.activity.TestWXPayActivity;
import com.doctor.yuyi.adapter.CircleAdpater;
import com.doctor.yuyi.adapter.CircleSelectAda;
import com.doctor.yuyi.bean.CircleBean.Root;
import com.doctor.yuyi.bean.CircleBean.Rows;
import com.doctor.yuyi.bean.CircleBean.SelectBean.Result;
import com.doctor.yuyi.myview.InformationListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 学术圈
 */
public class AcademicFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mNodata_Rl;
    private TextView mNoMsg_tv;
    private ImageView mNo_Post_img;
    private ImageView mMessage_img;
    private TextView mHot_tv, mSelect_tv, mNew_tv;//热门，精选，最新
    private View mHot_line, mSelect_line, mNew_line;//线
    private String mColorSelect = "#1ebeec";
    private String mNoSelectColor = "#6a6a6a";
    private SwipeRefreshLayout mRefresh;
    private ListView mListview;
    private View footer;
    private ProgressBar footerBar;
    private CircleAdpater mAdapter;
    private List<Rows> mList = new ArrayList<>();//热门，最新集合
    private CircleSelectAda mSelectAdapter;
    private List<Result> mSelectList = new ArrayList<>();//精选集合
    private int mFlag = 0;
    private ImageView mPostImg;//发帖
    private HttpTools mHttptools;
    private int mStart = 0;
    private int mLimit = 10;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 7) {//学术圈热门，最新
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    mListview.removeFooterView(footer);
                    footerBar.setVisibility(View.INVISIBLE);
                    MyDialog.stopDia();
                    mRefresh.setRefreshing(false);
                    mRefresh.setEnabled(false);

                    Root root = (Root) o;
                    List<Rows> list = new ArrayList<>();
                    list = root.getRows();
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    if (list.size() != 10) {
                        mListview.removeFooterView(footer);
                    } else {
                        mListview.addFooterView(footer);
                    }
                    if (mList.size() == 0) {
                        mNodata_Rl.setVisibility(View.VISIBLE);
                        mNoMsg_tv.setText("数据走丢了");
                        mNo_Post_img.setVisibility(View.VISIBLE);
                    } else {
                        mNodata_Rl.setVisibility(View.GONE);
                        mNoMsg_tv.setText("");
                        mNo_Post_img.setVisibility(View.GONE);
                    }
                }

            } else if (msg.what == 106) {
                MyDialog.stopDia();
                mRefresh.setRefreshing(false);
                mRefresh.setEnabled(false);
                footerBar.setVisibility(View.INVISIBLE);
                mListview.removeFooterView(footer);
                mNodata_Rl.setVisibility(View.VISIBLE);
                mNoMsg_tv.setText("账号异常,请重新登录");
            } else if (msg.what == 8) {//学术圈精选
                Object o = msg.obj;
                if (o != null && o instanceof com.doctor.yuyi.bean.CircleBean.SelectBean.Root) {
                    com.doctor.yuyi.bean.CircleBean.SelectBean.Root root = (com.doctor.yuyi.bean.CircleBean.SelectBean.Root) o;
                    if (root.getCode().equals("0")) {
                        MyDialog.stopDia();
                        mListview.removeFooterView(footer);
                        footerBar.setVisibility(View.INVISIBLE);
                        List<Result> list = new ArrayList<>();
                        list = root.getResult();
                        mSelectList.addAll(list);
                        mSelectAdapter.notifyDataSetChanged();
                        if (list.size() != 10) {
                            mListview.removeFooterView(footer);
                        } else {
                            mListview.addFooterView(footer);
                        }
                        if (mSelectList.size() == 0) {
                            mNodata_Rl.setVisibility(View.VISIBLE);
                            mNoMsg_tv.setText("数据走丢了");
                            mNo_Post_img.setVisibility(View.VISIBLE);
                        } else {
                            mNodata_Rl.setVisibility(View.GONE);
                            mNoMsg_tv.setText("");
                            mNo_Post_img.setVisibility(View.GONE);
                        }
                    }
                }
            } else if (msg.what == 107) {
                MyDialog.stopDia();
                mListview.removeFooterView(footer);
                mNodata_Rl.setVisibility(View.VISIBLE);
                mNoMsg_tv.setText("账号异常,请重新登录");
                mNo_Post_img.setVisibility(View.GONE);
            }
        }
    };

    public AcademicFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_academic, container, false);
        getHttpData();
        initView(view);
        return view;
    }

    public void getHttpData() {
        mHttptools = HttpTools.getHttpToolsInstance();
        Log.e("token", UserInfo.UserToken);
    }


    public void initView(View view) {

        mNodata_Rl = (RelativeLayout) view.findViewById(R.id.nodata_rl);
        mNodata_Rl.setOnClickListener(this);
        mNoMsg_tv = (TextView) view.findViewById(R.id.no_msg);
        mNo_Post_img = (ImageView) view.findViewById(R.id.no_post_img);
        mNo_Post_img.setOnClickListener(this);
        mMessage_img = (ImageView) view.findViewById(R.id.information_img);
        mMessage_img.setOnClickListener(this);
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
        mListview = (ListView) view.findViewById(R.id.circle_listview);
        mAdapter = new CircleAdpater(getActivity(), mList);
        mListview.setAdapter(mAdapter);
        footer = LayoutInflater.from(getActivity()).inflate(R.layout.circle_listview_footer, null);
        footerBar = (ProgressBar) footer.findViewById(R.id.pbLocate);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.circle_refresh);
        mRefresh.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.color_blood);
        mRefresh.setRefreshing(true);

        //精选适配器
        mSelectAdapter = new CircleSelectAda(this.getActivity(), mSelectList);
        //发帖
        mPostImg = (ImageView) view.findViewById(R.id.post_img);
        mPostImg.setOnClickListener(this);


        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mFlag == 0) {//学术圈热门
                    if (position == mList.size()) {//加载更多
                        mStart += 10;
                        footerBar.setVisibility(View.VISIBLE);
                        mHttptools.circleHot(mHandler, mStart, mLimit, UserInfo.UserToken);
                    } else {
                        Intent intent = new Intent(getActivity(), CardMessageActivity.class);
                        intent.putExtra("id", mList.get(position).getId());
                        startActivity(intent);
                    }
                } else if (mFlag == 1) {//学术圈精选
                    if (position == mSelectList.size()) {//加载更多
                        mStart += 10;
                        footerBar.setVisibility(View.VISIBLE);
                        mHttptools.circleSelect(mHandler, mStart, mLimit, UserInfo.UserToken);
                    } else {
                        Intent intent = new Intent(getActivity(), CardMessageActivity.class);
                        intent.putExtra("id", mSelectList.get(position).getId());
                        startActivity(intent);
                    }

                } else if (mFlag == 2) {//学术圈最新
                    if (position == mList.size()) {//加载更多
                        mStart += 10;
                        footerBar.setVisibility(View.VISIBLE);
                        mHttptools.circleNew(mHandler, mStart, mLimit, UserInfo.UserToken);
                    } else {
                        Intent intent = new Intent(getActivity(), CardMessageActivity.class);
                        intent.putExtra("id", mList.get(position).getId());
                        startActivity(intent);
                    }

                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mHot_tv.getId()) {//热门
            mListview.removeFooterView(footer);
            MyDialog.showDialog(this.getActivity());
            mFlag = 0;
            mStart = 0;
            mHttptools.circleHot(mHandler, mStart, mLimit, UserInfo.UserToken);
            Log.e("token", UserInfo.UserToken);
            showHotLine();
            mList.clear();
            mListview.removeFooterView(footer);
            mListview.setAdapter(mAdapter);

        } else if (id == mSelect_tv.getId()) {//精选
            mListview.removeFooterView(footer);
            MyDialog.showDialog(this.getActivity());
            mFlag = 1;
            mStart = 0;
            mHttptools.circleSelect(mHandler, mStart, mLimit, UserInfo.UserToken);
            showSelectLine();
            mSelectList.clear();
            mListview.removeFooterView(footer);
            mListview.setAdapter(mSelectAdapter);
        } else if (id == mNew_tv.getId()) {//最新
            mListview.removeFooterView(footer);
            MyDialog.showDialog(this.getActivity());
            mFlag = 2;
            mStart = 0;
            mHttptools.circleNew(mHandler, mStart, mLimit, UserInfo.UserToken);
            showNewLine();
            mList.clear();
            mListview.removeFooterView(footer);
            mListview.setAdapter(mAdapter);
        } else if (id == mPostImg.getId()) {//发帖
            startActivity(new Intent(getActivity(), PostActivity.class));
        } else if (id == mMessage_img.getId()) {//消息
            startActivity(new Intent(getActivity(), My_message_Activity.class));
        } else if (id == mNo_Post_img.getId()) {//没有数据时，显示的发帖按钮发帖
            startActivity(new Intent(getActivity(), PostActivity.class));
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


    @Override
    public void onResume() {
        if (mFlag == 0) {//学术圈热门
            mListview.removeFooterView(footer);
            mList.clear();
            mAdapter.notifyDataSetChanged();
            mStart = 0;
            mHttptools.circleHot(mHandler, mStart, mLimit, UserInfo.UserToken);
        } else if (mFlag == 1) {//学术圈精选
            mListview.removeFooterView(footer);
            mSelectList.clear();
            mSelectAdapter.notifyDataSetChanged();
            mStart = 0;
            mHttptools.circleSelect(mHandler, mStart, mLimit, UserInfo.UserToken);
        } else if (mFlag == 2) {//学术圈最新
            mListview.removeFooterView(footer);
            mList.clear();
            mAdapter.notifyDataSetChanged();
            mStart = 0;
            mHttptools.circleNew(mHandler, mStart, mLimit, UserInfo.UserToken);
        }
        super.onResume();
    }
}
