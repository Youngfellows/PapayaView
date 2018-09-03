package com.speex.papayaview.view.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Byron on 2018/9/2.
 */

public class PathUseView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Paint mPaint;
    private int mWidth, mHeigth;

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        mPaint.setTextSize(40);
        mPaint.setTextAlign(Paint.Align.LEFT);
    }

    public PathUseView(Context context) {
        super(context);
        init();
    }

    public PathUseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathUseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeigth = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //lineTo
        lineTo(canvas);

        //moveTo
        moveTo(canvas);

        //setLastPoint
        setLastPoint(canvas);

        //绘制圆
        drawCircle(canvas);

        //绘制椭圆
        drawOval(canvas);

        //绘制矩形
        drawRect(canvas);
    }

    /**
     * 绘制矩形
     *
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 8, mHeigth / 2);
        mPaint.setStyle(Paint.Style.STROKE);
        int length = Math.min(mWidth, mHeigth) / 8;
        RectF rectF = new RectF(0, 0, 2 * length, length);
        Path path = new Path();
        //绘制矩形
        path.addRect(rectF, Path.Direction.CCW);
        canvas.drawPath(path, mPaint);

        //绘制文字
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath("绘 制 矩 形", path, length, -20, mPaint);
        canvas.restore();
    }

    /**
     * 绘制椭圆
     *
     * @param canvas
     */
    private void drawOval(Canvas canvas) {
        canvas.save();
        canvas.translate(3 * mWidth / 5, 3 * mHeigth / 8);
        int length = Math.min(mWidth, mHeigth) / 8;
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        RectF rectF = new RectF(-length, 0, 2 * length, length);
        //绘制椭圆
        path.addOval(rectF, Path.Direction.CCW);
        canvas.drawPath(path, mPaint);
        //绘制文本
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath("绘 制 椭 圆", path, 4 * length, -20, mPaint);
        canvas.restore();
    }

    /**
     * 绘制圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        canvas.save();
        canvas.translate(3 * mWidth / 5, mHeigth / 4);
        mPaint.setStyle(Paint.Style.STROKE);
        int radius = Math.min(mWidth, mHeigth) / 7;
        Path path = new Path();
        //绘制圆
        path.addCircle(0, 0, radius, Path.Direction.CCW);
        canvas.drawPath(path, mPaint);
        //绘制文本
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath("Path绘制圆", path, 0, -20, mPaint);
        canvas.restore();
    }

    private void moveTo(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 2, mHeigth / 16);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextAlign(Paint.Align.RIGHT);

        int length = Math.min(mWidth, mHeigth);
        Path path = new Path();
        path.lineTo(length / 4, length / 4);
        path.moveTo(length / 4, length / 8);
        path.lineTo(length / 4, 0);
        canvas.drawPath(path, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath("MoveTo基本使用", path, 0, -20, mPaint);
        canvas.restore();
    }

    private void lineTo(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 8, mHeigth / 16);
        int length = Math.min(mWidth, mHeigth);
        Path path = new Path();
        path.lineTo(length / 4, length / 4);
        path.lineTo(length / 4, 0);
        canvas.drawPath(path, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath("LineTo基本使用", path, 0, -20, mPaint);
        canvas.restore();
    }

    public void setLastPoint(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 8, 6 * mHeigth / 16);
        mPaint.setStyle(Paint.Style.STROKE);

        int length = Math.min(mWidth, mHeigth);
        Path path = new Path();
        path.lineTo(length / 4, length / 4);
        path.setLastPoint(length / 4, length / 8);
        path.lineTo(length / 4, 0);
        //连接两个点
        path.close();

        //绘制路径
        canvas.drawPath(path, mPaint);

        //绘制文字
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawTextOnPath("setLastPoint使用", path, -20, -20, mPaint);
        canvas.restore();
    }
}
