package com.speex.papayaview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.speex.papayaview.utils.ScreenSizeUtil;

/**
 * Created by Byron on 2018/8/12.
 */

public class ClockView extends View {

    private Paint mPaint;
    private int mWidth, mHeigth;

    public ClockView(Context context) {
        super(context);
        init(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(3);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        ScreenSizeUtil.getWidthHeigth1(context, new ScreenSizeUtil.ScreenSizeCallback() {
            @Override
            public void onSize(int width, int heigth) {
                mWidth = width;
                mHeigth = heigth;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        //平移画布
        canvas.translate(mWidth / 2, mHeigth / 2);
        canvas.drawCircle(0, 0, 240, mPaint);//画圆圈

        //使用path绘制路径文字
//        canvas.save();//保存当前画布状态
        canvas.translate(-200, -200);
        Path path = new Path();
        RectF rectF = new RectF(0, 0, 400, 400);
        path.addArc(rectF, -180, 180);
        mPaint.setTextSize(25);
        mPaint.setStrokeWidth(1);
        canvas.drawTextOnPath("https://www.google.com.77777777777777", path, 56, 0, mPaint);
//        canvas.restore();//回滚到上一次保存的状态


        //绘制小刻度
        canvas.translate(200, 200);
        mPaint.setStrokeWidth(1);
        float y = -240;
        int count = 60; //总刻度数
        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(0f, y, 0, y - 30f, mPaint);
                canvas.drawText(String.valueOf(i / 5), -10f, y - 50f, mPaint);
            } else {
                canvas.drawLine(0f, y, 0f, y - 10f, mPaint);
            }
            canvas.rotate(360 / count, 0f, 0f); //旋转画纸
        }

        //绘制指针
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(0, 0, 20, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(0, 0, 10, mPaint);
        canvas.drawLine(0, 50, 0, -240, mPaint);
    }
}
