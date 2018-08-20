package com.speex.papayaview.view.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Byron on 2018/8/20.
 */

public class AlarmClockDialView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Paint mCirclePaint;
    private int mWidth;//屏幕宽
    private int mHeight;//屏幕高
    private int CIRCLE_WIDTH = 6;//圆的线宽度
    private int LONG_LINE_WIDTH = CIRCLE_WIDTH / 2;//长线宽度
    private int SHORT_LINE_WIDTH = LONG_LINE_WIDTH / 2;//断线宽度
    private static final int LONG_LINE_HEIGHT = 35;//长刻度长度
    private static final int SHORT_LINE_HEIGHT = 25;//端刻度长度

    private int mCircleLineWidth; // 圆环线宽度
    private int mLineWidt; // 长直线刻度线宽度
    private int mHalfLineWidth; // 短直线刻度线宽度
    private int mLongLineHeight; // 长线长度
    private int mShortLineHeight; // 短线长度
    private int mRadius;//圆环半径
    private Paint mLinsPaint;
    private Paint mTextPaint;
    private int mTextSize;

    public AlarmClockDialView(Context context) {
        super(context);
        init();
    }

    public AlarmClockDialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlarmClockDialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initData();
        //圆圈的画笔
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.BLACK);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mCirclePaint.setStrokeJoin(Paint.Join.ROUND);
        mCirclePaint.setStrokeWidth(mCircleLineWidth);

        //长刻度的画笔
        mLinsPaint = new Paint(mCirclePaint);
        mLinsPaint.setStrokeWidth(mLineWidt);

        //短刻度的画笔
        mTextPaint = new Paint(mCirclePaint);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStrokeWidth(mHalfLineWidth);
    }

    /**
     * 将dp转换为px
     */
    private void initData() {
        //圆环线宽度
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mCircleLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CIRCLE_WIDTH, metrics);
        //长直线刻度宽度
        mLineWidt = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LONG_LINE_WIDTH, metrics);
        //长直线长度
        mLongLineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LONG_LINE_HEIGHT, metrics);

        //短直线刻度宽度
        mHalfLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SHORT_LINE_WIDTH, metrics);
        //短直线长度
        mShortLineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SHORT_LINE_HEIGHT, metrics);
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, metrics);

        Log.d(TAG, "圆环线宽度: " + mCircleLineWidth);
        Log.d(TAG, "长直线刻度宽度: " + mLineWidt);
        Log.d(TAG, "短直线刻度宽度: " + mHalfLineWidth);
        Log.d(TAG, "长直线长度: " + mLongLineHeight);
        Log.d(TAG, "短直线长度: " + mShortLineHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = Math.min(mWidth / 2, mHeight / 2) - Math.min(mWidth / 8, mHeight / 8);
        Log.i(TAG, "onSizeChanged 圆环半径: mRadius = " + mRadius + " ,屏幕宽: " + mWidth + " ,屏幕高: " + mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制刻度
        drawLines(canvas);
        //绘制圆环
        drawCircle(canvas);
    }

    /**
     * 绘制刻度
     *
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);
        int lineTop;//顶点位置(Y值)
        int lineBottom;//底部位置(Y值)

        //先绘制大刻度
        canvas.save();//保存画布状态
        for (int i = 0; i < 360; i++) {
            if (i % 30 == 0) {
                //长刻度
                lineBottom = -(mRadius - mLongLineHeight);//底部位置(Y值)
                lineTop = -mRadius;//顶点位置(Y值)
                mLinsPaint.setColor(Color.GREEN);
                mLinsPaint.setStrokeWidth(mLineWidt);//设置画笔宽度
            } else {
                //短刻度
                lineBottom = -(mRadius - mShortLineHeight);//底部位置(Y值)
                lineTop = -mRadius;//顶点位置(Y值)
                mLinsPaint.setColor(Color.BLACK);
                mLinsPaint.setStrokeWidth(mHalfLineWidth);//设置画笔宽度
            }
            //绘制刻度
            if (i % 6 == 0) {
                if (i % 30 == 0) {
                    canvas.drawText(String.valueOf(i / 30), -10, lineBottom + 50, mTextPaint);
                }
                canvas.drawLine(0, lineBottom, 0, lineTop, mLinsPaint);
                canvas.rotate(6);
            }
        }
        canvas.restore();//恢复画布
    }

    /**
     * 绘制圆环
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(0, 0, mRadius, mCirclePaint);
    }


}
