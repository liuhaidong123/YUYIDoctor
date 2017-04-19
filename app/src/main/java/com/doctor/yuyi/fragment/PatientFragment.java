package com.doctor.yuyi.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.MyUtils.ToastUtils;
import com.doctor.yuyi.R;
import com.doctor.yuyi.User.UserInfo;
import com.doctor.yuyi.activity.PatientMessageActivity;
import com.doctor.yuyi.adapter.FragmentMyPatientListAdapter;
import com.doctor.yuyi.bean.PatientList.Root;
import com.doctor.yuyi.bean.PatientList.Rows;
import com.doctor.yuyi.myview.InformationListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends Fragment {
    private InformationListView my_patient_listview;
    private TextView titleText;
    private ImageView activity_include_imageReturn;
    private FragmentMyPatientListAdapter adapter;
    private List<Rows> mList = new ArrayList<>();
    private SwipeRefreshLayout mRefresh;
    private int mStart = 0;
    private int mLimit = 10;
    private HttpTools mHttptools;

    private RelativeLayout mRefreshBox;
    private ProgressBar mBar;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 14) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    mRefresh.setRefreshing(false);
                    Root root = (Root) o;
                    mBar.setVisibility(View.INVISIBLE);
                    List<Rows> list = new ArrayList<>();
                    if (root.getRows() != null) {
                        list = root.getRows();
                        mList.addAll(list);
                        adapter.setList(mList);
                        adapter.notifyDataSetChanged();
                        if (list.size() < 10) {//隐藏加载更多
                            mRefreshBox.setVisibility(View.GONE);
                        } else {
                            mRefreshBox.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ToastUtils.myToast(getContext(), "对不起，您没有权限");
                    }
                }
            } else if (msg.what == 114) {
                mRefreshBox.setVisibility(View.GONE);
                mBar.setVisibility(View.INVISIBLE);
                mRefresh.setRefreshing(false);
                ToastUtils.myToast(getContext(), "患者数据错误");
            }
        }
    };

    public PatientFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.myPatient(mHandler, UserInfo.UserToken, mStart, mLimit);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.mrefresh);
        //刷新
        mRefresh.setColorSchemeResources(R.color.color_delete, R.color.color_username);
        mRefresh.setRefreshing(true);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStart = 0;
                mList.clear();
                mHttptools.myPatient(mHandler, UserInfo.UserToken, mStart, mLimit);
            }
        });
        my_patient_listview = (InformationListView) view.findViewById(R.id.my_patient_listview);
        titleText = (TextView) view.findViewById(R.id.titleinclude_textview);
        titleText.setText("我的患者");
        activity_include_imageReturn = (ImageView) view.findViewById(R.id.activity_include_imageReturn);
        activity_include_imageReturn.setVisibility(View.GONE);
        adapter = new FragmentMyPatientListAdapter(getContext(), mList);
        my_patient_listview.setAdapter(adapter);

        //加载更多
        mRefreshBox = (RelativeLayout) view.findViewById(R.id.more_relative);
        mRefreshBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBar.setVisibility(View.VISIBLE);
                mStart += 10;
                mHttptools.myPatient(mHandler, UserInfo.UserToken, mStart, mLimit);
            }
        });
        mBar = (ProgressBar) view.findViewById(R.id.pbLocate);

        my_patient_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PatientMessageActivity.class);
                intent.putExtra("humeuserId", mList.get(position).getId());
                startActivity(intent);
            }
        });
    }


}
