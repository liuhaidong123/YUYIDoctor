package com.doctor.yuyi.lzh_utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.doctor.yuyi.R;

/**
 * Created by wanyu on 2017/4/14.
 */

public class MyNorEmptyListVIew extends MyEmptyListView{
    private final String DefaultText="没有查询到数据";
    private Context con;
    private int width;
    private int ResId;
    private String emptyText;
    public MyNorEmptyListVIew(Context context) {
        super(context);
    }

    public MyNorEmptyListVIew(Context context, AttributeSet attrs) {
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

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (this.getHeaderViewsCount()!=0){
            for (int i=0;i<getHeaderViewsCount();i++){
                View view=getChildAt(i);
                removeHeaderView(view);
            }
        }
        super.setAdapter(adapter);
    }

    public void setEmpty(){
        if (this.getAdapter()!=null&&this.getAdapter().getCount()>0){
            return;
        }
        else {
            View vi= LayoutInflater.from(con).inflate(R.layout.my_message_emptyview,null);
            ViewGroup.LayoutParams params=this.getLayoutParams();
            params.width= ViewGroup.LayoutParams.MATCH_PARENT;
            params.height= ViewGroup.LayoutParams.MATCH_PARENT;
            setLayoutParams(params);
            TextView textView= (TextView) vi.findViewById(R.id.empty_text);
            ImageView imageView= (ImageView) vi.findViewById(R.id.empty_image);
            imageView.setPadding(0,width/2,0,0);
            textView.setText(emptyText);
            imageView.setImageResource(ResId);
            addHeaderView(vi);
            vi.setOnClickListener(null);
            invalidate();
        }
    }
    //断网显示到view
    public void setError(){
        if (this.getAdapter()!=null&&this.getAdapter().getCount()>0){
            return;
        }
        else {
            ViewGroup.LayoutParams params=this.getLayoutParams();
            params.width= ViewGroup.LayoutParams.MATCH_PARENT;
            params.height= ViewGroup.LayoutParams.MATCH_PARENT;
            setLayoutParams(params);

            View vi=LayoutInflater.from(con).inflate(R.layout.activity_networkerror,null);
            ImageView imageView= (ImageView) vi.findViewById(R.id.newWorkError);
            imageView.setPadding(0,width/2,0,0);
            addHeaderView(vi);
            vi.setOnClickListener(null);
            invalidate();
        }
    }
    private static void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
