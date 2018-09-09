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

public class SecondOrderBezierView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Paint mPaint;//画笔
    private int mCenterX;
    private int mCenterY;
    private PointF mStart;
    private PointF mEnd;
    private PointF mControl;


    public SecondOrderBezierView(Context context) {
        super(context);
        init();
    }

    public SecondOrderBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SecondOrderBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        //初始化数据点和控制点
        mStart = new PointF(0, 0);
        mEnd = new PointF(0, 0);
        mControl = new PointF(0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;

        // 初始化数据点和控制点的位置
        int length = Math.min(w, h) / 4;
        mStart.x = mCenterX - length;
        mStart.y = mCenterY;
        mEnd.x = mCenterX + length;
        mEnd.y = mCenterY;
        mControl.x = mCenterX;
        mControl.y = mCenterY - length / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        mControl.x = event.getX();
        mControl.y = event.getY();
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
        canvas.drawPoint(mControl.x, mControl.y, mPaint);

        //绘制辅助线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(mStart.x, mStart.y, mControl.x, mControl.y, mPaint);
        canvas.drawLine(mControl.x, mControl.y, mEnd.x, mEnd.y, mPaint);

        //绘制贝塞尔曲线
        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.RED);

        Path path = new Path();
        path.moveTo(mStart.x, mStart.y);//数据点1
        path.quadTo(mControl.x, mControl.y, mEnd.x, mEnd.y);//控制点+数据点2
        canvas.drawPath(path, mPaint);
    }
}
