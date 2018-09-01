package com.speex.papayaview.view.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Byron on 2018/9/1.
 * Android的Canvas提供了一个drawTextOnPath(String text,Path path,float hOffset,float vOffset,Paint paint)方法，
 * 该方法可以沿着Path路径绘制文本，其中text指文本内容，hOffset参数指定水平偏移、vOffset指定垂直偏移
 */

public class DrawTextOnPathView extends View {
    private String TAG = this.getClass().getSimpleName();
    private String DEFAULT_TEXT = "让 购 物 更 便 捷";
    private Paint mPaint;
    private Path[] mPaths;
    private int mWidth;
    private int mHeight;

    public DrawTextOnPathView(Context context) {
        super(context);
        init();
    }

    public DrawTextOnPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawTextOnPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setPathEffect(new CornerPathEffect(50));//CornerPathEffect则可以将路径的转角变得圆滑，CornerPathEffect的构造方法只接受一个参数radius，意思就是转角处的圆滑程度。
        /**
         * 它的效果相对与上面两种路径效果来说要略显复杂，其虽说也是包含了两个参数：
         * 第一个参数是一个浮点型的数组，那这个数组有什么意义呢？其实是这样的，我们在定义该参数的时候只要浮点型数组中元素个数大于等于2即可
         */
        mPaint.setPathEffect(new DashPathEffect(new float[]{20, 10}, 1));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged: mWidth = " + w + " ,mHeight = " + h);
        mWidth = w;
        mHeight = h;

        mPaths = new Path[3];
        mPaths[0] = new Path();
        mPaths[0].moveTo(0, 0);
        for (int i = 0; i < DEFAULT_TEXT.length(); i++) {
            //生成6个点，随机生成Y坐标，并且连在一起
            mPaths[0].lineTo(i * 30, (float) (Math.random() * 30));
        }

        mPaths[1] = new Path();
        int minLenght = Math.min(mWidth, mHeight);
        RectF oval = new RectF(0, 0, minLenght / 2, minLenght / 3);
        mPaths[1].addOval(oval, Path.Direction.CCW);//椭圆路径

        mPaths[2] = new Path();
        mPaths[2].addArc(oval, 60, 180);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1.绘制文字
        drawTextOnPath1(canvas);

        //2.绘制文字
        drawTextOnPath2(canvas);

        //3.绘制文字
        drawTextOnPath3(canvas);
    }

    private void drawTextOnPath3(Canvas canvas) {
        canvas.save();//保存画布状态
        canvas.translate(mWidth / 4, 3 * mHeight / 4);

        //绘制路径
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPaths[2], mPaint);

        //绘制文字
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath(DEFAULT_TEXT, mPaths[2], -20, -20, mPaint);
        canvas.restore();//恢复画布状态
    }

    private void drawTextOnPath2(Canvas canvas) {
        canvas.save();//保存画布状态
        canvas.translate(mWidth / 4, mHeight / 4);//平移画布

        //绘制路径
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPaths[1], mPaint);

        //绘制文字
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath(DEFAULT_TEXT, mPaths[1], -20, -20, mPaint);
        canvas.restore();//恢复画布状态
    }

    //保存画布状态
    private void drawTextOnPath1(Canvas canvas) {
        canvas.save();
        //绘制白色背景
        canvas.drawColor(Color.WHITE);
        canvas.translate(mWidth / 3, mHeight / 13);

        //右边开始绘制，对齐
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPaint.setTextSize(40);

        //绘制路径
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(mPaths[0], mPaint);

        //绘制文字
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath(DEFAULT_TEXT, mPaths[0], -8, 20, mPaint);
        canvas.restore();//恢复画布状态
    }
}
