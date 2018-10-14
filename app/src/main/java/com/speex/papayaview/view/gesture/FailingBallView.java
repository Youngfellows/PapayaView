package com.speex.papayaview.view.gesture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.speex.papayaview.R;

/**
 * Created by Byron on 2018/10/14.
 */

public class FailingBallView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Paint mPaint;

    private int mWidth;             // 宽度
    private int mHeight;            // 高度

    private float mStartX = 0;        // 小方块开始位置X
    private float mStartY = 0;        // 小方块开始位置Y
    private float mEdgeLength = 200;  // 边长
    private RectF mRect = new RectF(mStartX, mStartY, mStartX + mEdgeLength, mStartY + mEdgeLength);

    private float mFixedX = 0;  // 修正距离X
    private float mFixedY = 0;  // 修正距离Y

    private GestureDetector mGestureDetector;
    private boolean mCanFail = false;   // 是否可以拖动

    private float mSpeedX = 0;      // 像素/s
    private float mSpeedY = 0;

    private Boolean mXFixed = false;
    private Boolean mYFixed = false;

    private Bitmap mBitmap;


    public FailingBallView(Context context) {
        super(context);
        init(context);
    }

    public FailingBallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FailingBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);

        mGestureDetector = new GestureDetector(context, mSimpleOnGestureListener);
        mBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ball_img, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mStartX = (w - mEdgeLength) / 2;
        mStartY = (h - mEdgeLength) / 2;
        refreshRectByCurrentPoint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawRect(mRect, mPaint);
        canvas.drawOval(mRect, mPaint);
        canvas.drawBitmap(mBitmap, new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight()), mRect, mPaint);
    }

    // 每 100 ms 更新一次
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // TODO 刷新内容
            mStartX = mStartX + mSpeedX / 30;
            mStartY = mStartY + mSpeedY / 30;
            //mSpeedX = mSpeedX > 0 ? mSpeedX - 10 : mSpeedX + 10;
            //mSpeedY = mSpeedY > 0 ? mSpeedY - 10 : mSpeedY + 10;
            mSpeedX *= 0.97;
            mSpeedY *= 0.97;
            if (Math.abs(mSpeedX) < 10) {
                mSpeedX = 0;
            }
            if (Math.abs(mSpeedY) < 10) {
                mSpeedY = 0;
            }
            if (refreshRectByCurrentPoint()) {
                // 转向
                if (mXFixed) {
                    mSpeedX = -mSpeedX;
                }
                if (mYFixed) {
                    mSpeedY = -mSpeedY;
                }
            }
            invalidate();
            if (mSpeedX == 0 && mSpeedY == 0) {
                mHandler.removeCallbacks(this);
                return;
            }
            mHandler.postDelayed(this, 33);
        }
    };

    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new
            GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float
                        velocityY) {
                    Log.e(TAG, velocityX + " : " + velocityY);
                    if (!mCanFail) return false;
                    mSpeedX = velocityX;
                    mSpeedY = velocityY;
                    mHandler.removeCallbacks(mRunnable);
                    mHandler.postDelayed(mRunnable, 0);
                    return super.onFling(e1, e2, velocityX, velocityY);
                }

                @Override
                public boolean onContextClick(MotionEvent e) {
                    return super.onContextClick(e);
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.i(TAG, "onDoubleTap: ");
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent e) {
                    Log.i(TAG, "onDoubleTapEvent: ");
                    return super.onDoubleTapEvent(e);
                }

                @Override
                public boolean onDown(MotionEvent e) {
                    Log.i(TAG, "onDown: ");
                    return super.onDown(e);
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    Log.i(TAG, "onScroll: ");
                    return super.onScroll(e1, e2, distanceX, distanceY);
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    Log.i(TAG, "onSingleTapConfirmed: ");
                    return super.onSingleTapConfirmed(e);
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.i(TAG, "onSingleTapUp: ");
                    return super.onSingleTapUp(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Log.i(TAG, "onLongPress: ");
                    super.onLongPress(e);
                }

                @Override
                public void onShowPress(MotionEvent e) {
                    Log.i(TAG, "onShowPress: ");
                    super.onShowPress(e);
                }
            };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (contains(event.getX(), event.getY())) {
                    mCanFail = true;
                    mFixedX = event.getX() - mStartX;
                    mFixedY = event.getY() - mStartY;
                    mSpeedX = 0;
                    mSpeedY = 0;
                } else {
                    mCanFail = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mCanFail) {
                    break;
                }
                mStartX = event.getX() - mFixedX;
                mStartY = event.getY() - mFixedY;
                if (refreshRectByCurrentPoint()) {
                    mFixedX = event.getX() - mStartX;
                    mFixedY = event.getY() - mStartY;
                }
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 触摸点是否在圆上
     *
     * @param x 触摸的x坐标
     * @param y 触摸的y坐标
     * @return 触摸点是否在圆上
     */
    private Boolean contains(float x, float y) {
        float radius = mEdgeLength / 2;
        float centerX = mRect.left + radius;
        float centerY = mRect.top + radius;
        return Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) <= radius;
    }

    /**
     * 刷新方块位置
     *
     * @return true 表示修正过位置, false 表示没有修正过位置
     */
    private Boolean refreshRectByCurrentPoint() {
        Boolean fixed = false;
        mXFixed = false;
        mYFixed = false;
        // 修正坐标
        if (mStartX < 0) {
            mStartX = 0;
            fixed = true;
            mXFixed = true;
        }
        if (mStartY < 0) {
            mStartY = 0;
            fixed = true;
            mYFixed = true;
        }
        if (mStartX + mEdgeLength > mWidth) {
            mStartX = mWidth - mEdgeLength;
            fixed = true;
            mXFixed = true;
        }
        if (mStartY + mEdgeLength > mHeight) {
            mStartY = mHeight - mEdgeLength;
            fixed = true;
            mYFixed = true;
        }
        mRect.left = mStartX;
        mRect.top = mStartY;
        mRect.right = mStartX + mEdgeLength;
        mRect.bottom = mStartY + mEdgeLength;
        return fixed;
    }
}
