package com.speex.papayaview.view.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
        mCenterY = h / 2;

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
        // 初始化数据点
        mData[0] = 0;
        mData[1] = mCircleRadius;

        mData[2] = mCircleRadius;
        mData[3] = 0;

        mData[4] = 0;
        mData[5] = -mCircleRadius;

        mData[6] = -mCircleRadius;
        mData[7] = 0;

        // 初始化控制点
        mCtrl[0] = mData[0] + mDifference;
        mCtrl[1] = mData[1];

        mCtrl[2] = mData[2];
        mCtrl[3] = mData[3] + mDifference;

        mCtrl[4] = mData[2];
        mCtrl[5] = mData[3] - mDifference;

        mCtrl[6] = mData[4] + mDifference;
        mCtrl[7] = mData[5];

        mCtrl[8] = mData[4] - mDifference;
        mCtrl[9] = mData[5];

        mCtrl[10] = mData[6];
        mCtrl[11] = mData[7] - mDifference;

        mCtrl[12] = mData[6];
        mCtrl[13] = mData[7] + mDifference;

        mCtrl[14] = mData[0] - mDifference;
        mCtrl[15] = mData[1];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinateSystem(canvas);       // 绘制坐标系

        canvas.translate(mCenterX, mCenterY); // 将坐标系移动到画布中央
        canvas.scale(1, -1);                 // 翻转Y轴

        drawAuxiliaryLine(canvas);

        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path path = new Path();
        path.moveTo(mData[0], mData[1]);
//        path.moveTo(mData[2], mData[3]);
//        path.moveTo(mData[4], mData[5]);
//        path.moveTo(mData[6], mData[7]);

        //第一象限的贝塞尔曲线
        path.cubicTo(mCtrl[0], mCtrl[1], mCtrl[2], mCtrl[3], mData[2], mData[3]);
        //第二象限的贝塞尔曲线
        path.cubicTo(mCtrl[4], mCtrl[5], mCtrl[6], mCtrl[7], mData[4], mData[5]);
        //第三象限的贝塞尔曲线
        path.cubicTo(mCtrl[8], mCtrl[9], mCtrl[10], mCtrl[11], mData[6], mData[7]);
        //第四象限的贝塞尔曲线
        path.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15], mData[0], mData[1]);

        canvas.drawPath(path, mPaint);

        mCurrent += mPiece;
        if (mCurrent < mDuration) {

            mData[1] -= 120 / mCount;
            mCtrl[7] += 80 / mCount;
            mCtrl[9] += 80 / mCount;

            mCtrl[4] -= 20 / mCount;
            mCtrl[10] += 20 / mCount;

            postInvalidateDelayed((long) mPiece);
        }
    }

    // 绘制辅助线
    private void drawAuxiliaryLine(Canvas canvas) {
        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);

        for (int i = 0; i < 8; i += 2) {
            canvas.drawPoint(mData[i], mData[i + 1], mPaint);
        }

        for (int i = 0; i < 16; i += 2) {
            canvas.drawPoint(mCtrl[i], mCtrl[i + 1], mPaint);
        }


        // 绘制辅助线
        mPaint.setStrokeWidth(4);

        for (int i = 2, j = 2; i < 8; i += 2, j += 4) {
            canvas.drawLine(mData[i], mData[i + 1], mCtrl[j], mCtrl[j + 1], mPaint);
            canvas.drawLine(mData[i], mData[i + 1], mCtrl[j + 2], mCtrl[j + 3], mPaint);
        }
        canvas.drawLine(mData[0], mData[1], mCtrl[0], mCtrl[1], mPaint);
        canvas.drawLine(mData[0], mData[1], mCtrl[14], mCtrl[15], mPaint);
    }

    // 绘制坐标系
    private void drawCoordinateSystem(Canvas canvas) {
        canvas.save();                      // 绘制做坐标系

        canvas.translate(mCenterX, mCenterY); // 将坐标系移动到画布中央
        canvas.scale(1, -1);                 // 翻转Y轴

        Paint fuzhuPaint = new Paint();
        fuzhuPaint.setColor(Color.RED);
        fuzhuPaint.setStrokeWidth(5);
        fuzhuPaint.setStyle(Paint.Style.STROKE);

        canvas.drawLine(0, -2000, 0, 2000, fuzhuPaint);
        canvas.drawLine(-2000, 0, 2000, 0, fuzhuPaint);

        canvas.restore();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
