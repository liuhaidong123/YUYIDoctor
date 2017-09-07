package com.doctor.yuyi.lzh_utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.doctor.yuyi.R;
import com.doctor.yuyi.adapter.ErrorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanyu on 2017/4/14.
 */

public class MyEmptyListView extends ListView{
    private final String DefaultText="没有查询到数据";
    private Context con;
    private int width;
    private int ResId;
    private String emptyText;
    public MyEmptyListView(Context context) {
        super(context);
    }

    public MyEmptyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.con=context;
        width=BitMapUtils.getWindowWidth(con);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyEmptyListView);
        ResId=typedArray.getResourceId(R.styleable.MyEmptyListView_bitmap,R.mipmap.nopost);
        emptyText=typedArray.getString(R.styleable.MyEmptyListView_emptyText);
        if (emptyText!=null&&!"".equals(emptyText)){
            return;
        }
       else {
            emptyText=DefaultText;
        }
    }


    public void setEmpty(){
        if (this.getAdapter()!=null&&this.getAdapter().getCount()>0){
            return;
        }
        else {
            View vi=LayoutInflater.from(con).inflate(R.layout.my_message_emptyview,null);
            TextView textView= (TextView) vi.findViewById(R.id.empty_text);
            ImageView imageView= (ImageView) vi.findViewById(R.id.empty_image);
            textView.setText(emptyText);
            imageView.setImageResource(ResId);
            vi.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ((ViewGroup)getParent()).addView(vi);
            setEmptyView(vi);
            }
    }
    //断网显示到view
    public void setError(){
        if (this.getAdapter()!=null&&this.getAdapter().getCount()>0){
            return;
        }
        else {
            View vi=LayoutInflater.from(con).inflate(R.layout.activity_networkerror,null);
            ImageView imageView= (ImageView) vi.findViewById(R.id.newWorkError);
            vi.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ((ViewGroup)getParent()).addView(vi);
            setEmptyView(vi);
            vi.setOnClickListener(null);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.getAdapter()!=null&&this.getAdapter().getCount()>0){
            if (this.getHeaderViewsCount()!=0){
                for (int i=0;i<this.getHeaderViewsCount();i++){
                    this.removeHeaderView(getChildAt(i));
                }
                invalidate();
            }
        }

    }

}
