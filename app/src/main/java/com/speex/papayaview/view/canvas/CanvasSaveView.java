package com.speex.papayaview.view.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Byron on 2018/8/19.
 */

public class CanvasSaveView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Paint mPaint;
    private int mWidth;//屏幕宽
    private int mHeight;//屏幕高

    public CanvasSaveView(Context context) {
        super(context);
        init();
    }

    public CanvasSaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasSaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4);
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);//不填充
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged 屏幕宽: " + w + " ,屏幕高: " + h);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int length = Math.min(mWidth, mHeight) / 4;
        Log.i(TAG, "onDraw: length = " + length);

        //平移画布
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle(0, 0, length, mPaint);

        mPaint.setColor(Color.RED);
        RectF rectF = new RectF(0, 0, length, length);
        canvas.drawRect(rectF, mPaint);

        //旋转画布
        canvas.save();//先保存之前的状态
        canvas.rotate(90, length / 2, 0);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(0, 0, 5, mPaint);
    }
}
