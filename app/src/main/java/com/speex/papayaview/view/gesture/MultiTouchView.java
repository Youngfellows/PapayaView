package com.speex.papayaview.view.gesture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Byron on 2018/10/13.
 */

public class MultiTouchView extends View {
    private String TAG = this.getClass().getSimpleName();

    private Paint mPaint;
    private int mWidth, mHeigth;

    //用于判断第2个手指是否存在
    boolean haveSecondPoint = false;

    //记录第2个手指第位置
    PointF mPoint;

    public MultiTouchView(Context context) {
        super(context);
        init();
    }

    public MultiTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MultiTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(45);

        mPoint = new PointF(0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeigth = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int actionMasked = event.getActionMasked();
        float x = event.getX();
        float y = event.getY();
        Log.i(TAG, "onTouchEvent index = " + index + " ,action = " + actionMasked + "(" + x + "," + y + ")");
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                Log.w(TAG, "ACTION_DOWN 第1个手指按下");
                break;
            case MotionEvent.ACTION_UP:
                Log.w(TAG, "ACTION_UP 第1个手指抬起");
                haveSecondPoint = false;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.w(TAG, "ACTION_POINTER_DOWN 第" + (index + 1) + "个手指按下");
                //判断是否是第2个手指按下
                if (event.getPointerId(index) == 1) {
                    Log.i(TAG, "第二个手指按下");
                    haveSecondPoint = true;
                    mPoint.set(x, y);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.w(TAG, "ACTION_POINTER_UP 第" + (index + 1) + "个手指抬起");
                //判断抬起的手指是否是第2个
                if (event.getPointerId(index) == 1) {
                    Log.i(TAG, "第二个手指松开啦");
                    haveSecondPoint = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "ACTION_MOVE 移动 index = " + index + " ,是否第二个手指按下移动: " + haveSecondPoint);

                //移动的是否第二个手指
                if (haveSecondPoint) {
                    //通过 pointerId 来获取 pointerIndex
                    int pointerIndex = event.findPointerIndex(1);
                    // 通过 pointerIndex 来取出对应的坐标
                    float x1 = event.getX(pointerIndex);
                    float y1 = event.getY(pointerIndex);
                    Log.i(TAG, "move: (" + x1 + "," + y1 + ")");
                    mPoint.set(x1, y1);
                }
                break;
        }

        //刷新
        invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mWidth / 2, mHeigth / 2);
        canvas.drawText("追踪第二个手指按下的位置", -mWidth / 4, 0, mPaint);
        canvas.restore();

        //如果屏幕上有第2个手指则绘制出来其位置
        if (haveSecondPoint) {
            canvas.drawCircle(mPoint.x, mPoint.y, Math.min(mWidth, mHeigth) / 8, mPaint);
        }
    }
}
