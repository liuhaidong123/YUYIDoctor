package com.doctor.yuyi.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by wanyu on 2017/3/27.
 */

public class MyScrollowView extends ScrollView{
    public MyScrollowView(Context context) {
        super(context);
    }

    public MyScrollowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int hei=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, hei);
    }
}
