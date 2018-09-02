package com.speex.papayaview.view.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Byron on 2018/9/1.
 */

public class PathView extends View {
    private String TAG = this.getClass().getSimpleName();
    private int mWidth, mHeigth;
    private Paint mPaint;
    private Paint mPointPaint;


    public PathView(Context context) {
        super(context);
        init();
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);//设置不填充
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        mPointPaint = new Paint();
        mPointPaint.setColor(Color.BLACK);
        mPointPaint.setStrokeWidth(10);
        mPointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPointPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeigth = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1.绘制三角形
        drawTriangle(canvas);

        //2.书写文字沿圆形轨迹
        drawCiclerText(canvas);

        //3.绘制贝赛尔曲线
        drawQuadToPath2(canvas);

        //4.绘制带动效的贝塞尔曲线
        drawDynamicPath(canvas);
    }

    /**
     * 绘制带动效的贝塞尔曲线
     *
     * @param canvas
     */
    private void drawDynamicPath(Canvas canvas) {

    }

    /**
     * 绘制贝赛尔曲线
     *
     * @param canvas
     */
    private void drawQuadToPath2(Canvas canvas) {
        //画笔非填充
        mPaint.setStyle(Paint.Style.STROKE);
        //确定贝塞尔曲线的第一个点
        Path path = new Path();
        path.moveTo(mWidth / 6, mHeigth / 3);
        //前两个参数确定控制点， 后两个参数确定结束点
        path.quadTo(mWidth / 5, 3 * mHeigth / 4, 3 * mWidth / 4, mHeigth / 2);
        //绘制出起始点，终止点，以及控制点
        canvas.drawPoint(mWidth / 6, mHeigth / 3, mPointPaint);
        canvas.drawPoint(mWidth / 5, 3 * mHeigth / 4, mPointPaint);
        canvas.drawPoint(3 * mWidth / 4, mHeigth / 2, mPointPaint);

        //绘制文字
        mPaint.setTextSize(40);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath("这是贝塞尔曲线", path, 0, -30, mPaint);

        //绘制贝塞尔曲线
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);
    }

    /**
     * 书写文字沿圆形轨迹
     *
     * @param canvas
     */
    private void drawCiclerText(Canvas canvas) {
        canvas.save();//保存画布
        canvas.translate(mWidth / 2, mHeigth / 4);//平移画布
        float radius = Math.min(mWidth, mHeigth) / 4;
        //绘制路径
        Path path = new Path();
        path.addCircle(0, 0, radius, Path.Direction.CW);
        canvas.drawPath(path, mPaint);

        //绘制文字
        mPaint.setTextSize(40);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        String text = "我要沿着这个圆写一些文字";
        canvas.drawTextOnPath(text, path, 0, 40, mPaint);
        canvas.restore();//恢复画布
    }

    /***
     * 绘制三角形
     * @param canvas
     */
    private void drawTriangle(Canvas canvas) {
        int minLength = Math.min(mWidth, mHeigth);
        Path path = new Path();
        path.moveTo(minLength / 4, 0);
        path.lineTo(0, minLength / 4);
        path.lineTo(minLength / 2, minLength / 4);
        path.close();
        canvas.drawPath(path, mPaint);
    }
}
