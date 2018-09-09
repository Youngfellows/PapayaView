package com.speex.papayaview.view.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Byron on 2018/9/10.
 */

public class BezierLoveCircleView extends View {
    private String TAG = this.getClass().getSimpleName();
    private static final float C = 0.551915024494f;// 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置
    private Paint mPaint;
    private int mCenterX, mCenterY;//中心点
    private PointF mCenter;//中心点
    private float mCircleRadius;//圆的半径
    private float mDifference;//圆形的控制点与数据点的差值

    private float[] mData;// 顺时针记录绘制圆形的四个数据点
    private float[] mCtrl;// 顺时针记录绘制圆形的八个控制点

    private float mDuration = 1000;//变化总时长
    private float mCurrent = 0;//当前已进行时长
    private int mCount = 100;//将时长总共划分多少份
    private float mPiece = mDuration / mCount;//每一份的时长

    public BezierLoveCircleView(Context context) {
        super(context);
        init();
    }

    public BezierLoveCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierLoveCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
        mPaint.setAntiAlias(true);

        //初始化
        mCenter = new PointF(0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterX = h / 2;

        //初始化圆的半径,圆形的控制点与数据点的差值
        float length = Math.min(w, h) / 3;
        mCircleRadius = length;//圆的半径
        mDifference = mCircleRadius * C;//圆形的控制点与数据点的差值

        mData = new float[8];//顺时针记录绘制圆形的四个数据点
        mCtrl = new float[16];//顺时针记录绘制圆形的八个控制点

        //初始化数据点和控制点
        initPoint();
    }

    /**
     * 初始化数据点和控制点
     */
    private void initPoint() {
        
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
