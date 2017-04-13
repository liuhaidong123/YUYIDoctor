package com.doctor.yuyi.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.doctor.yuyi.R;

/**
 * Created by wanyu on 2017/4/11.
 */

public class SelectImageVIew extends ImageView{
    private final int SELECTED=1;
    private final int UNSELECT=0;
    private int select=0;
    private Paint p;
    public SelectImageVIew(Context context) {

        super(context);


    }

    public SelectImageVIew(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p=new Paint();
        p.setAntiAlias(true);
        int width=canvas.getWidth();
        int height=canvas.getHeight();
        if (select==SELECTED){
            p.setColor(getResources().getColor(R.color.drawbackground));
            canvas.drawRect(0,0,width,height,p);
        }
        else if (select==UNSELECT){
            p.setColor(getResources().getColor(R.color.un));
            canvas.drawRect(0,0,width,height,p);
        }
    }
    public void setState(int state){
        this.select=state;
        invalidate();
    }
}
