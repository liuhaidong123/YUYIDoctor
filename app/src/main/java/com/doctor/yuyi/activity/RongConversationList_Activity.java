package com.doctor.yuyi.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doctor.yuyi.HttpTools.HttpTools;
import com.doctor.yuyi.MyUtils.MyDialog;
import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.FragmentMyPatientListAdapter;
import com.doctor.yuyi.bean.PatientList.Root;
import com.doctor.yuyi.bean.PatientList.Rows;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

import static android.R.attr.background;
import static android.R.attr.fragment;

public class RongConversationList_Activity extends FragmentActivity implements View.OnClickListener {
    private RelativeLayout mNodata_rl;
    private TextView mNoMsg_Tv;

    private ListView my_patient_listview;
    private View footer;
    private ProgressBar footerBar;
    private ImageView back;
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
                            Toast.makeText(RongConversationList_Activity.this, "无患者信息", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rong_conversation_list_);
        initView();

    }

    public void initView() {
        back = (ImageView) findViewById(R.id.imageReturn);
        back.setOnClickListener(this);

        mNodata_rl = (RelativeLayout) findViewById(R.id.nodata_rl);
        mNodata_rl.setOnClickListener(this);
        mNoMsg_Tv = (TextView) findViewById(R.id.nomsg_tv);

        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.myPatient(mHandler, com.doctor.yuyi.User.UserInfo.UserToken, mStart, mLimit);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.mrefresh);
        //刷新
        mRefresh.setColorSchemeResources(R.color.color_delete, R.color.color_username);
        mRefresh.setRefreshing(true);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStart = 0;
                mList.clear();
                adapter.notifyDataSetChanged();
                mHttptools.myPatient(mHandler, com.doctor.yuyi.User.UserInfo.UserToken, mStart, mLimit);
            }
        });
        my_patient_listview = (ListView) findViewById(R.id.my_patient_listview);
        footer = LayoutInflater.from(RongConversationList_Activity.this).inflate(R.layout.circle_listview_footer, null);
        footerBar = (ProgressBar) footer.findViewById(R.id.pbLocate);
        adapter = new FragmentMyPatientListAdapter(RongConversationList_Activity.this, mList);
        my_patient_listview.setAdapter(adapter);


        my_patient_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == mList.size()) {
                    footerBar.setVisibility(View.VISIBLE);
                    mStart += 10;
                    mHttptools.myPatient(mHandler, com.doctor.yuyi.User.UserInfo.UserToken, mStart, mLimit);
                } else {
                    Intent intent = new Intent(RongConversationList_Activity.this, PatientMessageActivity.class);
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
            MyDialog.showDialog(RongConversationList_Activity.this);
            mStart = 0;
            mHttptools.myPatient(mHandler, com.doctor.yuyi.User.UserInfo.UserToken, mStart, mLimit);
        } else if (id == back.getId()) {
            finish();
        }
    }

}
