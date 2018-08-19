package com.speex.papayaview.view.canvas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Byron on 2018/8/20.
 */

public class ScaleRulerView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    // 刻度尺高度 70dp -->> xxx ps
    private static final int DIVIDING_RULE_HEIGHT = 70;
    // 距离左右间
    private static final int DIVIDING_RULE_MARGIN_LEFT_RIGHT = 10;
    // 第一条线距离边框距离
    private static final int FIRST_LINE_MARGIN = 5;
    // 打算绘制的厘米数
    private static final int DEFAULT_COUNT = 9;
    private Resources mResources;
    private int mDividRuleHeight;
    private int mHalfRuleHeight;
    private int mDividRuleLeftMargin;
    private int mFirstLineMargin;

    public ScaleRulerView(Context context) {
        super(context);
        init(context);
    }

    public ScaleRulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScaleRulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4);
        initData(context);
    }

    private void initData(Context context) {
        mResources = context.getResources();
        mDividRuleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DIVIDING_RULE_HEIGHT, mResources.getDisplayMetrics());
        mHalfRuleHeight = mDividRuleHeight / 2;

        mDividRuleLeftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DIVIDING_RULE_MARGIN_LEFT_RIGHT, mResources.getDisplayMetrics());
        mFirstLineMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                FIRST_LINE_MARGIN, mResources.getDisplayMetrics());

        Log.i(TAG, "刻度尺高度: mDividRuleHeight = " + mDividRuleHeight + " ,长刻度高度: mHalfRuleHeight = " + mHalfRuleHeight);
        Log.i(TAG, "第一根刻度的左边距离: mDividRuleLeftMargin = " + mFirstLineMargin + " ,左右间距: mDividRuleLeftMargin = " + mDividRuleLeftMargin);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制外框
        drawOuter(canvas);
        //绘制刻度线
        drawLines(canvas);
        //绘制数字
        drawNumber(canvas);
    }


    /**
     * 绘制外框
     *
     * @param canvas
     */
    private void drawOuter(Canvas canvas) {
        //平移画布到屏幕中心
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rectF = new RectF(-(mWidth / 2 - mDividRuleLeftMargin), -(mDividRuleHeight / 2), (mWidth / 2 - mDividRuleLeftMargin), mDividRuleHeight / 2);
        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 绘制刻度线
     *
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        //平移画布到外边框最左边
        canvas.translate(-(mWidth / 2 - mDividRuleLeftMargin), 0);
        canvas.drawCircle(0, 0, 10, mPaint);

        //绘制刻度
        int length = (mWidth / 2 - mDividRuleLeftMargin) * 2;//刻度总长度
        canvas.drawLine(0, 0, length, 0, mPaint);


        int longHeight = mHalfRuleHeight / 2;//长刻度高度
        int harfHeight = longHeight / 2;//长刻度高度
        int shortHeigh = harfHeight / 2; //短刻度高度
        canvas.drawLine(mFirstLineMargin, 0, mFirstLineMargin, -longHeight, mPaint); //绘制第一根刻度
        canvas.drawLine(length - mFirstLineMargin, 0, length - mFirstLineMargin, -longHeight, mPaint);//绘制最后一根刻度

        //实际刻度长度
        length = length - 2 * mFirstLineMargin;
        //间隔大小 --->> 平移大小
        int lineInterval = length / (DEFAULT_COUNT * 10);
        Log.i(TAG, "lineInterval: " + lineInterval);

        //平移画布到起点
        canvas.translate(mFirstLineMargin, 0);
        canvas.drawCircle(0, 0, 10, mPaint);


        //绘制刻度
        int topHeight = 0;
        for (int i = 0; i <= DEFAULT_COUNT * 10; i++) {
            if (i % 10 == 0) {
                Log.d(TAG, "drawLines: xxxx");
                topHeight = -longHeight;
            } else if (i % 5 == 0) {
                Log.d(TAG, "drawLines: yyyy");
                topHeight = -harfHeight;
            } else {
                Log.d(TAG, "drawLines: zzzz");
                topHeight = -shortHeigh;
            }
            //绘制刻度
            Log.i(TAG, "drawLines: topHeight = " + topHeight);
            canvas.drawLine(0, 0, 0, topHeight, mPaint);
            canvas.translate(lineInterval, 0);//平移
        }
    }

    /**
     * 绘制数字
     *
     * @param canvas
     */
    private void drawNumber(Canvas canvas) {

    }
}
