package com.speex.papayaview.view.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Byron on 2018/8/30.
 */

public class DrawTextView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public DrawTextView(Context context) {
        super(context);
        init(context);
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(50);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制文本1
        drawText1(canvas);
        //从字符串的多少个开始绘制
        drawText2(canvas);
        drawText3(canvas);

        //指定每个字符的位置
        drawPosText(canvas);
    }

    /***
     * 指定每个字符的位置
     * @param canvas
     */
    private void drawPosText(Canvas canvas) {
        String text = "女神蔡妍,爱你么么哒";
        float[] pos = new float[]{
                100,200,
                150,250,
                200,300,
                250,350,
                300,400,
                350,450,
                400,500,
                450,550,
                500,600,
                550,650
        };
        canvas.drawPosText(text, pos, mPaint);
    }

    /**
     * 从字符串的多少个开始绘制
     *
     * @param canvas
     */
    private void drawText2(Canvas canvas) {
        String text = "你好女神,晚上一起去小树林看星星";
        char[] chars = text.toCharArray();
        canvas.drawText(chars, 2, text.length() - 4, 100, 150, mPaint);
    }

    private void drawText3(Canvas canvas) {
        String text = "你好女神,晚上一起去小树林看星星";
        canvas.drawText(text, 3, text.length() - 3, 100, 100, mPaint);
    }

    /**
     * 从头开始绘制
     *
     * @param canvas
     */
    private void drawText1(Canvas canvas) {
        String text = "你好女神";
        canvas.drawText(text, mWidth / 2, mHeight / 2, mPaint);
    }
}
