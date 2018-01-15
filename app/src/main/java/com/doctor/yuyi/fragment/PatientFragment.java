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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.MyUtils.MyDialog;
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
public class PatientFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout mNodata_rl;
    private TextView mNoMsg_Tv;

    private ListView my_patient_listview;
    private View footer;
    private ProgressBar footerBar;
    private TextView titleText;
    private ImageView activity_include_imageReturn;
    private FragmentMyPatientListAdapter adapter;
    private List<Rows> mList = new ArrayList<>();
    private SwipeRefreshLayout mRefresh;
    private int mStart = 0;
    private int mLimit = 10;
    private HttpTools mHttptools;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyDialog.stopDia();
            if (msg.what == 14) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    mRefresh.setRefreshing(false);

                    Root root = (Root) o;
                    footerBar.setVisibility(View.INVISIBLE);
                    my_patient_listview.removeFooterView(footer);
                    List<Rows> list = new ArrayList<>();
                    if (root.getRows() != null) {
                        list = root.getRows();
                        mList.addAll(list);
                        adapter.setList(mList);
                        adapter.notifyDataSetChanged();
                        if (list.size() != 10) {//隐藏加载更多
                            my_patient_listview.removeFooterView(footer);
                        } else {
                            my_patient_listview.addFooterView(footer);
                        }
                        if (mList.size() == 0) {
                            mNodata_rl.setVisibility(View.VISIBLE);
                            mNoMsg_Tv.setText(R.string.no_patient_msg);
                            Toast.makeText(getActivity(), "无患者信息", Toast.LENGTH_SHORT).show();
                        } else {
                            mNodata_rl.setVisibility(View.GONE);
                            mNoMsg_Tv.setText("");
                        }

                    } else {
                        mNodata_rl.setVisibility(View.VISIBLE);
                        mNoMsg_Tv.setText("暂无查看数据患者数据权限");
                    }
                }
            } else if (msg.what == 114) {
                mNodata_rl.setVisibility(View.VISIBLE);
                mNoMsg_Tv.setText("账号异常,请重新登录");
                footerBar.setVisibility(View.GONE);
                my_patient_listview.removeFooterView(footerBar);

                mRefresh.setRefreshing(false);
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
        mNodata_rl = (RelativeLayout) view.findViewById(R.id.nodata_rl);
        mNodata_rl.setOnClickListener(this);
        mNoMsg_Tv = (TextView) view.findViewById(R.id.nomsg_tv);

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
                adapter.notifyDataSetChanged();
                mHttptools.myPatient(mHandler, UserInfo.UserToken, mStart, mLimit);
            }
        });
        my_patient_listview = (ListView) view.findViewById(R.id.my_patient_listview);
        footer = LayoutInflater.from(getActivity()).inflate(R.layout.circle_listview_footer, null);
        footerBar = (ProgressBar) footer.findViewById(R.id.pbLocate);
        adapter = new FragmentMyPatientListAdapter(getActivity(), mList);
        my_patient_listview.setAdapter(adapter);


        titleText = (TextView) view.findViewById(R.id.titleinclude_textview);
        titleText.setText("我的患者");
        activity_include_imageReturn = (ImageView) view.findViewById(R.id.activity_include_imageReturn);
        activity_include_imageReturn.setVisibility(View.GONE);


        my_patient_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == mList.size()) {
                    footerBar.setVisibility(View.VISIBLE);
                    mStart += 10;
                    mHttptools.myPatient(mHandler, UserInfo.UserToken, mStart, mLimit);
                } else {
                    Intent intent = new Intent(getActivity(), PatientMessageActivity.class);
                    intent.putExtra("humeuserId", mList.get(position).getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mNodata_rl.getId()) {
            MyDialog.showDialog(getActivity());
            mStart = 0;
            mHttptools.myPatient(mHandler, UserInfo.UserToken, mStart, mLimit);
        }
    }
}
