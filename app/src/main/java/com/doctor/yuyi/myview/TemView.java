package com.doctor.yuyi.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.doctor.yuyi.R;

import java.util.ArrayList;

/**
 * Created by liuhaidong on 2017/2/20.
 */

public class TemView extends View {
    private Context mContext;
    private DisplayMetrics mDisplayMetrics;
    private ArrayList<Integer> YData = new ArrayList<>();
    private ArrayList<String> XDate = new ArrayList<>();
    private ArrayList<Float> mTemData = new ArrayList<>();

    private final String paintColor = "#6a6a6a";
    private final String myColor = "#74958a";
    private Paint YXpaint;
    private Paint linePaint;
    private Paint mSolidCirclePaint;
    private Paint mStrokeCirclePaint;

    private float YEndPoint;//y轴终点坐标
    private float XScale;//x轴刻度
    private float YScale;//y轴刻度
    private float YEachBlood;
    private float mBigCircleRadius;
    private float mSmallCircleRadius;


    public TemView(Context context) {
        super(context);
        this.mContext = context;
        //initData();
    }

    public TemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
       // initData();
    }

    public TemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        //initData();
    }

    public void setTemInfo(ArrayList<Integer> YData, ArrayList<String> XDate, ArrayList<Float> temData) {
        this.YData = YData;
        this.XDate = XDate;
        this.mTemData = temData;
       initData();
    }

    public void initData() {
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();
//        int colorX=0x22f3f6;
//        int colorY=0x22f3f6;
//        int colorSource=0x1dbeec;
//        int colorOtherSource=0x7ed66b;
//        int colorBackground=0x30323a;
//        int firstTextColor=0xc81dbeec;
//        int otherTextColor=0xc87ed66b;

        //y,x轴数据画笔
        YXpaint = new Paint();
        YXpaint.setAntiAlias(true);
        YXpaint.setColor(Color.parseColor("#22f3f6"));
        YXpaint.setTextSize(dip2px(10));
        YXpaint.setStrokeWidth(dip2px(0.3f));
        YXpaint.setStyle(Paint.Style.FILL_AND_STROKE);
         YXpaint.setTextAlign(Paint.Align.CENTER);

        //折线画笔
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.parseColor("#7ed66b"));
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //实心圆圈画笔
        mSolidCirclePaint = new Paint();
        mSolidCirclePaint.setStyle(Paint.Style.FILL);
        mSolidCirclePaint.setStrokeWidth(dip2px(2));
        mSolidCirclePaint.setColor(Color.parseColor("#7ed66b"));
        mSolidCirclePaint.setAntiAlias(true);

        //实心圆圈外边的大圆画笔
        mStrokeCirclePaint = new Paint();
        mStrokeCirclePaint.setColor(Color.parseColor("#7ed66b"));
        mStrokeCirclePaint.setAntiAlias(true);
        mStrokeCirclePaint.setStyle(Paint.Style.STROKE);
        mStrokeCirclePaint.setStrokeWidth(dip2px(1));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mBigCircleRadius = dip2px(4);
        mSmallCircleRadius = dip2px(2.5f);

        YEndPoint = getHeight();
        YScale = YEndPoint / 10.0f;
        // YEachBlood = YScale / 20.0f;
        XScale = getWidth() / 8.0f;

        //Y轴体温刻度
        for (int i = 0; i < YData.size(); i++) {
            try {
                //Y血量小横线
                // canvas.drawLine(YScale-20, YEndPoint-i*YScale,YScale+10 ,YEndPoint-i*YScale, mPaintXY);
                //Y血量刻度值
                canvas.drawText(YData.get(i) + "", dip2px(20), YEndPoint - (2 + i) * YScale - (YXpaint.ascent() + YXpaint.descent() / 2), YXpaint);

            } catch (Exception e) {
            }
        }

        //x轴日期刻度
        for (int i = 0; i < XDate.size(); i++) {
            canvas.drawText(XDate.get(i), XScale + XScale * i, YEndPoint - YScale, YXpaint);

        }

        //折线走势
        for (int i = 0; i < mTemData.size(); i++) {
            //最后一个数据大圆套小圆
            if (i == mTemData.size() - 1) {
                canvas.drawCircle(XScale + XScale * i, Ycode(mTemData.get(i)), mBigCircleRadius, mStrokeCirclePaint);
                canvas.drawCircle(XScale + XScale * i, Ycode(mTemData.get(i)), mSmallCircleRadius, mSolidCirclePaint);

                //否则都是小圈
            } else {
                canvas.drawCircle(XScale + XScale * i, Ycode(mTemData.get(i)), mSmallCircleRadius, mSolidCirclePaint);
            }
            //画折线
            try {
                canvas.drawLine(XScale + XScale * i, Ycode(mTemData.get(i)), XScale + XScale * (i + 1), Ycode(mTemData.get(i + 1)), linePaint);
            } catch (Exception e) {
            }
        }
        String text="体温";
        Rect rect=new Rect();
        linePaint.setTextSize(getResources().getDimension(R.dimen.textSize9));
        linePaint.getTextBounds(text,0,text.length(),rect);
        canvas.drawText(text,canvas.getWidth()-rect.width()-10,rect.height()+10,linePaint);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(4);
        canvas.drawCircle(canvas.getWidth()-10-rect.width()-10-2,10+rect.height()/2.0f+1,8,linePaint);
        linePaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(canvas.getWidth()-10-rect.width()-40-20,10+rect.height()/2.0f-2+1,canvas.getWidth()-10-rect.width()-20,10+rect.height()/2.0f+2+1,linePaint);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     * @return
     */
    public float dip2px(float dpValue) {
        float scale = mDisplayMetrics.density;
        return (dpValue * scale + 0.5f);
    }

    /**
     * 每个圆圈的纵坐标
     *
     * @param a 集合中获取的tiwen的多少
     * @return
     */
    private float Ycode(float a) {
        float e = YEndPoint - (a-35+2)*YScale;
        return e;
    }
}
