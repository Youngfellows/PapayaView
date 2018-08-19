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

public class CanvasControlView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Paint mPaint;//画笔
    private int mWidth;//宽度
    private int mHeight;//高度

    public CanvasControlView(Context context) {
        super(context);
        init(context);
    }

    public CanvasControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CanvasControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();//初始化画笔
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);//不填充
        mPaint.setStrokeWidth(3);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged 屏幕宽度: w = " + w + " ,屏幕高度: h = " + h);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        translateControl(canvas);//位移操作
        scaleControl(canvas);//缩放操作
        rotateControl(canvas);//旋转操作
        skewControl(canvas);//错切操作
    }

    /**
     * 错切操作
     *
     * @param canvas
     */
    private void skewControl(Canvas canvas) {
        //从屏幕中心平移画布的原点到大圆的顶点
        float length = Math.min(mWidth, mHeight) / 6;//边长
        float radius = length + length / 2 + length / 3;
        canvas.translate(0, radius);

        RectF rectF = new RectF(0, 0, length, length);
        canvas.drawRect(rectF, mPaint);

        //水平错切45度
        mPaint.setColor(Color.BLUE);
        canvas.skew(1, 0); //水平错切45度
        canvas.skew(0, 1); //垂直错切45度
        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 旋转操作
     *
     * @param canvas
     */
    private void rotateControl(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        float length = Math.min(mWidth, mHeight) / 6;//边长
        RectF rectF = new RectF(0, 0, length, length);
        canvas.drawRect(rectF, mPaint);

        //旋转180端
        canvas.save();
//        canvas.rotate(180, length / 3, 0);
        canvas.rotate(180);
        canvas.drawRect(rectF, mPaint);
        canvas.restore();

        //测试画布状态
        canvas.save();
        mPaint.setColor(Color.BLACK);
        float radius1 = length + length / 2 + length / 3;
        float radius2 = length + length / 2 + length / 4;
        canvas.drawCircle(0, 0, radius1, mPaint);
        canvas.drawCircle(0, 0, radius2, mPaint);
        for (int i = 0; i < 36; i++) {
            canvas.drawLine(0, radius2, 0, radius1, mPaint);
            canvas.rotate(10);//每次旋转10度
        }
        canvas.restore();
    }

    /**
     * 缩放操作
     *
     * @param canvas
     */
    private void scaleControl(Canvas canvas) {
        mPaint.setColor(Color.RED);
        float length = Math.min(mWidth, mHeight) / 6;//边长
        RectF rectF = new RectF(0, -length, length, 0);
        canvas.drawRect(rectF, mPaint);

        canvas.save();//保存画布状态

        //缩放
        mPaint.setColor(Color.BLACK);
        canvas.scale(0.5f, 0.5f);
        canvas.drawRect(rectF, mPaint);

        canvas.restore();//恢复画布状态

        mPaint.setColor(Color.BLUE);
        RectF rectF1 = new RectF(-length, 0, 0, length);
        canvas.drawRect(rectF1, mPaint);

        //缩放中心向右(左)偏移length/2
        canvas.save();
        mPaint.setColor(Color.GREEN);
        canvas.scale(0.5f, 0.5f, -length / 2, 0);
        canvas.drawRect(rectF1, mPaint);
        canvas.restore();


        //测试画布
        mPaint.setColor(Color.BLUE);
        length += length / 2;
        RectF rectF2 = new RectF(-length, -length, length, length);
        canvas.drawRect(rectF2, mPaint);

//        for (int i = 0; i < 20;i++) {
//            canvas.drawRect(rectF2, mPaint);
//            canvas.scale(0.9f, 0.9f);
//        }
    }

    /**
     * 位移操作
     */
    private void translateControl(Canvas canvas) {
        float radius = Math.min(mWidth, mHeight) / 6;//边长
        canvas.translate(radius, radius);
        canvas.drawCircle(0, 0, radius, mPaint);

        //移动画布原点到屏幕中心位置
        mPaint.setColor(Color.BLACK);
        canvas.translate(mWidth / 2 - radius, mHeight / 2 - radius);
        Log.i(TAG, "radius: " + radius);
        canvas.drawCircle(0, 0, radius, mPaint);
    }
}
