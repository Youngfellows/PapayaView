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

        //绘制圆角矩形
        drawRoundRect(canvas);

        //设置最后一个点
        setLastPoint2(canvas);
        setLastPoint3(canvas);

        //addPath使用
        addPath(canvas);
        addPath2(canvas);

        //绘制圆弧
        drawArc(canvas);
        drawArc2(canvas);

        //拷贝路径
        copyPath(canvas);
    }

    /**
     * 拷贝路径
     *
     * @param canvas
     */
    private void copyPath(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 8, 14 * mHeigth / 15);  // 移动坐标系到屏幕中心
        canvas.scale(1, -1);                         // <-- 注意 翻转y坐标轴

        Path path = new Path();                     // path中添加一个圆形(圆心在坐标原点)
        path.addCircle(0, 0, 50, Path.Direction.CW);

        Path dst = new Path();                      // dst中添加一个矩形
        dst.addRect(-200, -200, 200, 200, Path.Direction.CW);

        path.offset(300, 0, dst);                     // 平移

        canvas.drawPath(path, mPaint);               // 绘制path

        mPaint.setColor(Color.BLUE);                // 更改画笔颜色

        canvas.drawPath(dst, mPaint);
    }

    /**
     * 绘制圆弧
     *
     * @param canvas
     */
    private void drawArc2(Canvas canvas) {
        canvas.save();
        canvas.translate(3 * mWidth / 5, 9 * mHeigth / 10);
        canvas.scale(1, -1);
        int length = Math.min(mWidth, mHeigth) / 8;
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.lineTo(length / 2, length / 2);
        RectF oval = new RectF(0, 0, length, length);
//        path.arcTo(oval, 0, 270);//连接最后一个点与圆弧起点
        path.arcTo(oval, 0, 270, false);
        canvas.drawPath(path, mPaint);
        canvas.restore();
    }

    /**
     * 绘制圆弧
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(1 * mWidth / 8, 9 * mHeigth / 10);
        canvas.scale(1, -1);
        int length = Math.min(mWidth, mHeigth) / 8;
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.lineTo(length / 2, length / 2);
        RectF oval = new RectF(0, 0, length, length);
//        path.addArc(oval, 0, 270);//不连接最后一个点与圆弧起点
        path.arcTo(oval, 0, 270, true);
        canvas.drawPath(path, mPaint);
        canvas.restore();
    }

    private void addPath2(Canvas canvas) {
        canvas.save();
        canvas.translate(3 * mWidth / 5, 3 * mHeigth / 4);
        int length = Math.min(mWidth, mHeigth) / 8;
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.addCircle(length / 2, length / 2, length / 2, Path.Direction.CW);

        Path des = new Path();
        RectF rectF = new RectF(0, 0, 2 * length, length);
        des.addRect(rectF, Path.Direction.CW);//顺时针
        path.addPath(des, -length, 0);//矩形左边平移

        canvas.drawPath(path, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath("顺时针", des, 0, 30, mPaint);
        canvas.restore();
    }

    private void addPath(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 8, 3 * mHeigth / 4);
        int length = Math.min(mWidth, mHeigth) / 8;
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        RectF rectF = new RectF(0, 0, 2 * length, length);
        path.addRect(rectF, Path.Direction.CW);//顺时针

        Path src = new Path();
        src.addCircle(length, 0, length / 2, Path.Direction.CW);
        path.addPath(src);

        canvas.drawPath(path, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath("顺时针", path, 0, 30, mPaint);
        canvas.restore();
    }

    /**
     * 设置最后一个点
     *
     * @param canvas
     */
    private void setLastPoint3(Canvas canvas) {
        canvas.save();
        canvas.translate(3 * mWidth / 5, 3 * mHeigth / 5);
        int length = Math.min(mWidth, mHeigth) / 8;
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        RectF rectF = new RectF(0, 0, 2 * length, length);
        path.addRect(rectF, Path.Direction.CW);//顺时针
        path.setLastPoint(-length / 4, length + length / 2);//设置最后一个点
        canvas.drawPath(path, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath("顺时针", path, 0, 30, mPaint);
        canvas.restore();
    }

    /**
     * 设置最后一个点
     *
     * @param canvas
     */
    private void setLastPoint2(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 8, 3 * mHeigth / 5);
        int length = Math.min(mWidth, mHeigth) / 8;
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        RectF rectF = new RectF(0, 0, 2 * length, length);
        path.addRect(rectF, Path.Direction.CCW);//逆时针
        path.setLastPoint(-length / 4, length + length / 2);//设置最后一个点
        canvas.drawPath(path, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath("逆时针", path, length, -20, mPaint);
        canvas.restore();
    }

    /**
     * 绘制圆角矩形
     *
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas) {
        canvas.save();
        canvas.translate(3 * mWidth / 5, mHeigth / 2);
        mPaint.setStyle(Paint.Style.STROKE);
        int length = Math.min(mWidth, mHeigth) / 8;
        Path path = new Path();
        //绘制圆角矩形
        RectF rectF = new RectF(0, 0, 2 * length, length);
        path.addRoundRect(rectF, 30, 30, Path.Direction.CW);
        canvas.drawPath(path, mPaint);

        //绘制文字
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath("绘 制 圆 角 矩 形", path, 0, 40, mPaint);
        canvas.restore();
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
