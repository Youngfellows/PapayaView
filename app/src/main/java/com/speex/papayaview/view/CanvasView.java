package com.speex.papayaview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.speex.papayaview.utils.ScreenSizeUtil;

/**
 * Created by Byron on 2018/8/12.
 */

public class CanvasView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Paint mPaint;
    private int mWidth;
    private int mHeigth;

    public CanvasView(Context context) {
        super(context);
        init(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);//消除锯齿
        ScreenSizeUtil.getWidthHeigth1(context, new ScreenSizeUtil.ScreenSizeCallback() {
            @Override
            public void onSize(int width, int heigth) {
                Log.i(TAG, "onSize: width = " + width + " ,heigth = " + heigth);
                mWidth = width;
                mHeigth = heigth;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制颜色
        canvas.drawColor(Color.YELLOW);

        //绘制弧线区域
        drawArc(canvas);

        //绘制圆
        drawCircle(canvas);

        //绘制直线
        drawLine(canvas);

        //绘制椭圆
        drawOval(canvas);

        //绘制文本
        drawText(canvas);

        //绘制矩形
        drawRect(canvas);

        //绘制圆角矩形
        drawRoundRect(canvas);

        //绘制路径
        drawPath(canvas);

        //绘制路径文字
        drawTextOnPath(canvas);
    }

    /**
     * 绘制路径文字
     *
     * @param canvas
     */
    private void drawTextOnPath(Canvas canvas) {
        mPaint.setTextSize(35);
        Path path = new Path(); //定义一条路径
        path.moveTo(10, 10); //移动到 坐标10,10
        path.lineTo(50, 60);
        path.lineTo(200, 80);
        path.lineTo(10, 10);
        canvas.drawTextOnPath("Android777", path, 10, 10, mPaint);
    }

    /**
     * 绘制路径
     *
     * @param canvas
     */
    private void drawPath(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        Path path = new Path();//定义一条路径
        path.moveTo(mWidth, 0);
        path.lineTo(mWidth - 100, mHeigth / 5);
        path.lineTo(mWidth / 2, mHeigth / 7);
        path.lineTo(mWidth, 0);
        canvas.drawPath(path, mPaint);
    }

    /**
     * 绘制圆角矩形
     *
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas) {
        RectF rectF = new RectF(mWidth / 2, mHeigth / 5, mWidth / 2 + 200, mHeigth / 3);
        canvas.drawRoundRect(rectF, 30, 30, mPaint);
    }

    /**
     * 绘制矩形
     *
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        RectF rectF = new RectF(mWidth / 3, mHeigth / 5, mWidth / 2, mHeigth / 3);
        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 绘制文本
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        mPaint.setTextSize(35);
        float x = mWidth / 2;
        //按照既定点 绘制文本内容
        canvas.drawPosText("Android666", new float[]{
                x, 10,
                x, 40,
                x, 70,
                x, 100,
                x, 130,
                x, 160,
                x, 190,
                x, 220,
                x, 250,
                x, 280
        }, mPaint);
    }

    /**
     * 绘制椭圆
     *
     * @param canvas
     */
    private void drawOval(Canvas canvas) {
        RectF rectF = new RectF(mWidth / 2 - 100, mHeigth / 2 - 200, mWidth / 2 + 100, mHeigth / 2 + 200);
        canvas.drawOval(rectF, mPaint);
    }

    /**
     * 绘制直线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        canvas.drawLine(0, 0, mWidth / 3, mHeigth / 5, mPaint);
    }

    /**
     * 绘制弧线区域
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);//绘制边线,填充
        RectF rect = new RectF(mWidth / 2 - 100, mHeigth / 2 - 100, mWidth / 2 + 100, mHeigth / 2 + 100);
        canvas.drawArc(rect, //弧线所使用的矩形区域大小
                0,//开始角度
                90, //扫过的角度
//                false,//是否使用中心
                true,//使用中心
                mPaint);
    }

    /**
     * 绘制圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);//绘制边线,不填充
        canvas.drawCircle(mWidth / 2, mHeigth / 2, 100, mPaint);
    }
}
