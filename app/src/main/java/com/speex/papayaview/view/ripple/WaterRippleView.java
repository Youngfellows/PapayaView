package com.speex.papayaview.view.ripple;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Byron on 2018/9/30.
 */

public class WaterRippleView extends View {
    private String TAG = this.getClass().getSimpleName();
    private int mWidth, mHeight;
    private int mCenterY, mWL;
    private Paint mPaint;
    private Path mPath;
    private Integer mOffset = 0;
    private int mWaveCount = 100;

    public WaterRippleView(Context context) {
        super(context);
        init();
    }

    public WaterRippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaterRippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#57C1E2"));
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mWL = w;
        mCenterY = h / 2;
        startAnim();//启动刷新
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");
        mPath.reset();
        //测试绘制波纹线
//        drawRipple();

        //绘制波浪
        drawWaterRipple();

        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 绘制波浪
     */
    private void drawWaterRipple() {
        mPath.moveTo(-mWL + mOffset, mCenterY);
        for (int i = 0; i < mWaveCount; i++) {
            //画第一段波纹的第一条曲线
            mPath.quadTo((-mWL * 3 / 4) + (i * mWL) + mOffset, mCenterY + 60, (-mWL / 2) + (i * mWL) + mOffset, mCenterY);

            //画出第一段波纹的第二条曲线
            mPath.quadTo((-mWL / 4) + (i * mWL) + mOffset, mCenterY - 60, i * mWL + mOffset, mCenterY);
        }
        //绘制深海
        mPath.lineTo(mWL, mCenterY);
        mPath.lineTo(mWL, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();
    }

    /**
     * 测试绘制波纹线
     */
    private void drawRipple() {
        //画第一段波纹的第一条曲线
        mPath.moveTo(-mWL, mCenterY); //将path操作的起点移动到(-mWL,mCenterY)
        mPath.quadTo((-mWL * 3 / 4), mCenterY + 60, (-mWL / 2), mCenterY); //画出第一段波纹的第一条曲线

        //画出第一段波纹的第二条曲线
        mPath.quadTo((-mWL / 4), mCenterY - 60, 0, mCenterY); //画出第一段波纹的第二条曲线

        //画出第二段波纹的第一条曲线
        mPath.quadTo((mWL / 4), mCenterY + 60, (mWL / 2), mCenterY); //画出第二段波纹的第一条曲线

        //画出第二段波纹的第二条曲线
        mPath.quadTo((mWL * 3 / 4), mCenterY - 60, mWL, mCenterY);  //画出第二段波纹的第二条曲线

        mPath.lineTo(mWL, mCenterY);
        mPath.lineTo(mWL, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();
    }

    private void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mWL); //mWL是一段波纹的长度
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i(TAG, "onAnimationUpdate: " + animation.getAnimatedValue());
                mOffset = (Integer) animation.getAnimatedValue(); //offset 的值的范围在[0,mWL]之间。
                postInvalidate();
//                invalidate();
            }
        });
        animator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
