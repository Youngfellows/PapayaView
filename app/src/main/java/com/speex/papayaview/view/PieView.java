package com.speex.papayaview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.speex.papayaview.view.pieview.PieData;

import java.util.ArrayList;

/**
 * Created by Byron on 2018/8/13.
 */

public class PieView extends View {
    private String TAG = this.getClass().getSimpleName();
    // 颜色表
    private int[] mColors = {0xFFCCFF00,
            0xFF6495ED, 0xFFE32636,
            0xFF800000, 0xFF808000,
            0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private float mStartAngle; //饼状图初始绘制角度
    private ArrayList<PieData> mDatas;//数据
    private int mWidth;//宽度
    private int mHeight;//高度
    private Paint mPaint;


    public PieView(Context context) {
        super(context);
        Log.i(TAG, "PieView: 1");
        init(context);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "PieView: 2");
        init(context);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "PieView: 3");
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(1);
//        paint.setAntiAlias(true);//消除锯齿
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);//消除锯齿
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged: 宽: " + w + " ,高: " + h);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: ");
        //平移画布
        if (mDatas == null) {
            return;
        }
        canvas.translate(mWidth / 2, mHeight / 2);
        float radius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        RectF rectF = new RectF(-radius, -radius, radius, radius);

        //绘制饼状
        float currentStartAngle = mStartAngle;
        for (int i = 0; i < mDatas.size(); i++) {
            PieData pieData = mDatas.get(i);
            //设置颜色
            int color = pieData.getColor();
            mPaint.setColor(color);
            //绘制扇形
            canvas.drawArc(rectF, currentStartAngle, pieData.getAngle(), true, mPaint);
            currentStartAngle += pieData.getAngle();
        }
    }

    public void setStartAngle(int startAngle) {
        this.mStartAngle = startAngle;
        invalidate();
    }

    public void setData(ArrayList<PieData> datas) {
        this.mDatas = datas;
        initData();
        invalidate();
    }

    private void initData() {
        if (mDatas == null || mDatas.size() == 0) {
            Log.e(TAG, "initData: 数据有问题,直接返回");
            return;
        }

        //保存总值
        float sumValue = 0;
        //设置颜色
        for (int i = 0; i < mDatas.size(); i++) {
            PieData pieData = mDatas.get(i);
            sumValue += pieData.getValue();//计算总值
            int j = i % mColors.length;//设置颜色
            Log.i(TAG, "j = " + j);
            pieData.setColor(mColors[j]);
        }

        //设置角度
        float sumAngle = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            PieData pieData = mDatas.get(i);
            //计算百分百
            float percentage = pieData.getValue() / sumValue;
            //对应的角度
            float angle = percentage * 360;

            //设置百分比，设置角度
            pieData.setPercentage(percentage);
            pieData.setAngle(angle);
            sumAngle += pieData.getAngle();
            Log.i(TAG, "名字: " + pieData.getName() + ",颜色: " + pieData.getColor() + " ,角度: " + pieData.getAngle() + " ，百分比: " + pieData.getPercentage());
        }
    }
}
