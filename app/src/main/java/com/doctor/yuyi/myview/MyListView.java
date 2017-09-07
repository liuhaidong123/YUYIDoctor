package com.doctor.yuyi.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.doctor.yuyi.R;

import java.util.ArrayList;

/**
 * Created by wanyu on 2017/3/27.
 */

public class MyListView extends ListView implements AbsListView.OnScrollListener{
    View headView;
    Context context;
    IScrollBottomListener listener;
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }
    public void  setEmty(String text){
//        if (this.getHeaderViewsCount()==1){
//            this.removeHeaderView(headView);
//        }
        if (this.getAdapter()!=null&&this.getAdapter().getCount()!=0){
                return;
            }
        if (this.getAdapter()==null){
            this.setAdapter(new ArrayAdapter(context,android.R.layout.simple_list_item_1,new ArrayList<String>()));
        }
        if (headView==null){
            headView= LayoutInflater.from(context).inflate(R.layout.emp,null);
            headView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            ((ViewGroup)getParent()).addView(headView);
            headView.setClickable(false);
        }
        TextView empText= (TextView) headView.findViewById(R.id.empText);
        empText.setText(text);
        this.setEmptyView(headView);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            // 当不滚动时
            case OnScrollListener.SCROLL_STATE_IDLE:
                // 判断滚动到底部
                if (view!=null){
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                        listener.onScrollBottom();
                    }
                }
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
    public void setListener(IScrollBottomListener listener){
        this.listener=listener;
    }
    public interface IScrollBottomListener{
        void onScrollBottom();//滚动到底部
    }
}
