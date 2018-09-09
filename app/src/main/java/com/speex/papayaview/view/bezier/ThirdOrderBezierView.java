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
 * Created by Byron on 2018/9/9.
 */

public class ThirdOrderBezierView extends View {
    private String TAG = this.getClass().getSimpleName();
    private int mCenterX, mCenterY;//中心点
    private PointF mStart, mEnd, mControl1, mControl2;//两个数据点,两个控制点
    private boolean mMode = false;//设置哪一个控制点
    private Paint mPaint;

    public ThirdOrderBezierView(Context context) {
        super(context);
        init();
    }

    public ThirdOrderBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThirdOrderBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        //初始化数据点和控制点
        mStart = new PointF(0, 0);
        mEnd = new PointF(0, 0);
        mControl1 = new PointF(0, 0);
        mControl2 = new PointF(0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;

        // 初始化数据点和控制点的位置
        int length = Math.min(w, h) / 3;
        mStart.x = mCenterX - length;
        mStart.y = mCenterY;

        mEnd.x = mCenterX + length;
        mEnd.y = mCenterY;

        mControl1.x = mCenterX;
        mControl1.y = mCenterY - length / 2;

        mControl2.x = mCenterX;
        mControl2.y = mCenterY - length / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        if (mMode) {
            mControl1.x = event.getX();
            mControl1.y = event.getY();
        } else {
            mControl2.x = event.getX();
            mControl2.y = event.getY();
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(mStart.x, mStart.y, mPaint);
        canvas.drawPoint(mEnd.x, mEnd.y, mPaint);
        canvas.drawPoint(mControl1.x, mControl1.y, mPaint);
        canvas.drawPoint(mControl2.x, mControl2.y, mPaint);

        // 绘制辅助线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(mStart.x, mStart.y, mControl1.x, mControl1.y, mPaint);
        canvas.drawLine(mControl1.x, mControl1.y, mControl2.x, mControl2.y, mPaint);
        canvas.drawLine(mControl2.x, mControl2.y, mEnd.x, mEnd.y, mPaint);


        //绘制三阶贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
        Path path = new Path();
        path.moveTo(mStart.x, mStart.y);
        path.cubicTo(mControl1.x, mControl1.y, mControl2.x, mControl2.y, mEnd.x, mEnd.y);
        canvas.drawPath(path, mPaint);
    }

    /**
     * 设置模式
     *
     * @param mode
     */
    public void setMode(boolean mode) {
        mMode = mode;
    }
}
