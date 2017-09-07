package com.doctor.yuyi.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.doctor.yuyi.R;

import java.util.ArrayList;

/**
 * Created by liuhaidong on 2017/2/20.
 */

public class BloodView extends View {
    private ArrayList<Integer> YBlood = new ArrayList<>();
    private ArrayList<String> XDate = new ArrayList<>();
    private ArrayList<Integer> mHeightBloodData = new ArrayList<>();
    private ArrayList<Integer> mLowBloodData = new ArrayList<>();

    private final String paintColor = "#74958a";
    private final String lineColor = "#6a6a6a";
    private Paint mPaintXY;
    private Paint mPaintBloodLine;
    private Paint mPaintSloidCircle;
    private Paint mPaintStrokeCircle2;

    private float YEndPoint;//y轴终点坐标
    private float XScale;//x轴刻度
    private float YScale;//y轴刻度
    private float YEachBlood;
    private float mBigCircleRadius;
    private float mSmallCircleRadius;
    private Context mContext;
    private DisplayMetrics mDisplayMetrics;

    public BloodView(Context context) {
        super(context);
        this.mContext = context;
        //initPaint();
    }

    public BloodView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
       // initPaint();
    }

    public BloodView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        //initPaint();
    }

    public void setInfo(ArrayList<Integer> YBlood, ArrayList<String> XDate, ArrayList<Integer> mHeightBloodData, ArrayList<Integer> mLowBloodData) {
        this.YBlood = YBlood;
        this.XDate = XDate;
        this.mHeightBloodData = mHeightBloodData;
        this.mLowBloodData = mLowBloodData;
        initPaint();
    }

    public void initPaint() {
//        int colorX=0x22f3f6;
//        int colorY=0x22f3f6;
//        int colorSource=0x1dbeec;
//        int colorOtherSource=0x7ed66b;
//        int colorBackground=0x30323a;
//        int firstTextColor=0xc81dbeec;
//        int otherTextColor=0xc87ed66b;
        //屏幕信息类
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();

        //x,y轴画笔
        mPaintXY = new Paint();
        mPaintXY.setColor(Color.parseColor("#22f3f6"));
        mPaintXY.setAntiAlias(true);
        mPaintXY.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintXY.setTextSize(dip2px(10));
        mPaintXY.setStrokeWidth(dip2px(0.3f));
        mPaintXY.setTextAlign(Paint.Align.CENTER);
        //折线
        mPaintBloodLine = new Paint();
        mPaintBloodLine.setColor(Color.parseColor("#1dbeec"));
        mPaintBloodLine.setAntiAlias(true);
        mPaintBloodLine.setStyle(Paint.Style.STROKE);

        //实心圆圈
        mPaintSloidCircle = new Paint();
        mPaintSloidCircle.setStyle(Paint.Style.FILL);
        mPaintSloidCircle.setStrokeWidth(dip2px(2));
        mPaintSloidCircle.setColor(Color.parseColor("#1dbeec"));
        mPaintSloidCircle.setAntiAlias(true);

        //实心圆圈外边的圆圈
        mPaintStrokeCircle2 = new Paint();
        mPaintStrokeCircle2.setStyle(Paint.Style.STROKE);
        mPaintStrokeCircle2.setStrokeWidth(dip2px(1));
        mPaintStrokeCircle2.setColor(Color.parseColor("#1dbeec"));
        mPaintStrokeCircle2.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBigCircleRadius = dip2px(4);
        mSmallCircleRadius = dip2px(2.5f);

        YEndPoint = getHeight();
        YScale = YEndPoint / 10.0f;
        YEachBlood = YScale / 20.0f;
        XScale = getWidth() / 8.0f;

        Log.e("YEndPoint", YEndPoint + "");
        Log.e("XScale", XScale + "");
        Log.e("YScale", YScale + "");
        Log.e("YEachBlood", YEachBlood + "");



        //Y血量刻度
        for (int i = 0; i < YBlood.size(); i++) {
            try {
                //Y血量小横线
                // canvas.drawLine(YScale-20, YEndPoint-i*YScale,YScale+10 ,YEndPoint-i*YScale, mPaintXY);

                //Y血量刻度值
                canvas.drawText(YBlood.get(i) + "", dip2px(20), YEndPoint - (2 + i) * YScale - (mPaintXY.ascent() + mPaintXY.descent() / 2), mPaintXY);
            } catch (Exception e) {

            }

        }

        //x轴日期刻度
        for (int i = 0; i < XDate.size(); i++) {
            canvas.drawText(XDate.get(i), XScale + XScale * i, YEndPoint - YScale, mPaintXY);
        }
        //折线走势
        for (int i = 0; i < mHeightBloodData.size(); i++) {
            //最后一个数据大圆套小圆
            if (i == mHeightBloodData.size() - 1) {
                //低压
                mPaintStrokeCircle2.setColor(Color.parseColor("#7ed66b"));
                canvas.drawCircle(XScale + XScale * i, Ycode(mLowBloodData.get(i)), mBigCircleRadius, mPaintStrokeCircle2);
                mPaintSloidCircle.setColor(Color.parseColor("#7ed66b"));
                canvas.drawCircle(XScale + XScale * i, Ycode(mLowBloodData.get(i)), mSmallCircleRadius, mPaintSloidCircle);
                //高压
                mPaintStrokeCircle2.setColor(Color.parseColor("#1dbeec"));
                canvas.drawCircle(XScale + XScale * i, Ycode(mHeightBloodData.get(i)), mBigCircleRadius, mPaintStrokeCircle2);
                mPaintSloidCircle.setColor(Color.parseColor("#1dbeec"));
                canvas.drawCircle(XScale + XScale * i, Ycode(mHeightBloodData.get(i)), mSmallCircleRadius, mPaintSloidCircle);

                //否则都是小圈
            } else {
                mPaintSloidCircle.setColor(Color.parseColor("#1dbeec"));
                canvas.drawCircle(XScale + XScale * i, Ycode(mHeightBloodData.get(i)), mSmallCircleRadius, mPaintSloidCircle);
                mPaintSloidCircle.setColor(Color.parseColor("#7ed66b"));
                canvas.drawCircle(XScale + XScale * i, Ycode(mLowBloodData.get(i)), mSmallCircleRadius, mPaintSloidCircle);
                    }
            //画折线
            try {
                mPaintBloodLine.setColor(Color.parseColor("#1dbeec"));
                canvas.drawLine(XScale + XScale * i, Ycode(mHeightBloodData.get(i)), XScale + XScale * (i + 1), Ycode(mHeightBloodData.get(i + 1)), mPaintBloodLine);
                mPaintBloodLine.setColor(Color.parseColor("#7ed66b"));
                canvas.drawLine(XScale + XScale * i, Ycode(mLowBloodData.get(i)), XScale + XScale * (i + 1), Ycode(mLowBloodData.get(i + 1)), mPaintBloodLine);
            } catch (Exception e) {
            }
        }
        String textHeigh="高压";
        String textLow="低压";
        Paint p=new Paint();
        //高压
        p.setColor(Color.parseColor("#1dbeec"));
        p.setAntiAlias(true);
        p.setTextSize(getResources().getDimension(R.dimen.textSize9));
        Rect rectHeigh=new Rect();
        p.getTextBounds(textHeigh,0,textHeigh.length(),rectHeigh);


        canvas.drawText(textHeigh,canvas.getWidth()-rectHeigh.width()-10,rectHeigh.height()+10,p);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(4);
        canvas.drawCircle(canvas.getWidth()-10-rectHeigh.width()-10-2,10+rectHeigh.height()/2.0f+1,8,p);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(canvas.getWidth()-10-rectHeigh.width()-40-20,10+rectHeigh.height()/2.0f-2+1,canvas.getWidth()-10-rectHeigh.width()-20,10+rectHeigh.height()/2.0f+2+1,p);

        Rect rectLow=new Rect();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.parseColor("#7ed66b"));
        p.getTextBounds(textLow,0,textLow.length(),rectLow);

        canvas.drawText(textLow,canvas.getWidth()-rectLow.width()-10,rectHeigh.height()+10+10+rectLow.height(),p);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(4);
        canvas.drawCircle(canvas.getWidth()-10-rectHeigh.width()-10-2,10+rectHeigh.height()/2.0f+1+rectHeigh.height()/2.0f+10+rectLow.height()/2.0f,8,p);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(canvas.getWidth()-10-rectHeigh.width()-40-20,10+rectHeigh.height()/2.0f-2+1+rectHeigh.height()/2.0f+10+rectLow.height()/2.0f,canvas.getWidth()-10-rectHeigh.width()-20,10+rectHeigh.height()/2.0f+2+1+rectHeigh.height()/2.0f+10+rectLow.height()/2.0f,p);
    }

    /**
     * 每个圆圈的纵坐标
     *
     * @param a 集合中获取的血量的多少
     * @return
     */
    private float Ycode(int a) {

        float e = YEndPoint - a * YEachBlood;
        return e;
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
}
