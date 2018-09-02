package com.speex.papayaview.view.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Byron on 2018/9/2.
 */

public class DynamicPathView extends View {
    private String TAG = this.getClass().getSimpleName();
    private int mWidth, mHeight;
    private Paint mPaint;
    private Paint mCirclePaint;
    private static final int NEED_INVALIDATE = 0X6666;
    private int mCount = 0;
    private Handler mHandler;
    //    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Log.i(TAG, "handleMessage: xxxxxxxxxx");
//            switch (msg.what) {
//                case NEED_INVALIDATE:
//                    mCount += 5;
//                    if (mCount >= 100) {
//                        mCount = 0;
//                    }
//                    invalidate();
////                    requestLayout();
////                    forceLayout();
//                    mHandler.sendEmptyMessageAtTime(NEED_INVALIDATE, 1000);
//                    break;
//            }
//        }
//    };
    private Path mPath;

    public DynamicPathView(Context context) {
        super(context);
        init();
    }

    public DynamicPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DynamicPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        setWillNotDraw(false);
        mHandler = new Handler(Looper.getMainLooper());
        mPath = new Path();

        //绘制曲线的画笔
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);

        //绘制圆圈的画笔
        mCirclePaint = new Paint();
        mCirclePaint.setStrokeWidth(10);
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStyle(Paint.Style.STROKE);
//        mHandler.removeMessages(NEED_INVALIDATE);
//        mHandler.sendEmptyMessageDelayed(NEED_INVALIDATE, 1000);
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, 1000);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mCount += 5;
            if (mCount >= 100) {
                mCount = 0;
            }
            invalidate();
//                    requestLayout();
//                    forceLayout();
            mHandler.postDelayed(mRunnable, 50);
        }
    };


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        Log.i(TAG, "onMeasure: mWidth = " + mWidth + " ,mHeight = " + mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        mWidth = w;
//        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: yyyyyyyyyyyyyyyyy");
        mPath.reset();
        mPath.moveTo(-mCount, mHeight / 2);
        for (int i = 0; i < 20; i++) {
            mPath.rQuadTo(40, 80, 80, 0);
            mPath.rQuadTo(40, -80, 80, 0);
        }
        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(mWidth / 2, mHeight / 2, Math.min(mWidth, mHeight) / 12, mCirclePaint);
    }

    public void remove() {
//        mHandler.removeMessages(NEED_INVALIDATE);
        mHandler.removeCallbacks(mRunnable);
    }
}
