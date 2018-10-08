package com.speex.papayaview.view.gesture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Byron on 2018/10/9.
 */

public class CoordinateSpaceView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Paint mPaint;
    private Path mCirclePath;
    private Path mSmallCirclePath;
    private int mWidth, mHeigth;
    private int mDownX = -1, mDownY = -1;//按下位置
    private int mCircleRadius, mSmallCircleRadius = -1;//大小圆的半径
    private float[] mPts;

    public CoordinateSpaceView(Context context) {
        super(context);
        init();
    }

    public CoordinateSpaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoordinateSpaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);

        mCirclePath = new Path();//路径
        mSmallCirclePath = new Path();//小圆的路径
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeigth = h;

        mCircleRadius = Math.min(mWidth, mHeigth) / 5;//大圆半径
//        mSmallRadius = Math.min(mWidth, mHeigth) / 6;//小圆半径
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //保存统一坐标系后当前位置的数组
        mPts = new float[]{mDownX, mDownY};

        // 绘制触摸坐标系 灰色
        drawTouchCoordinateSpace(canvas);

        //平移坐标系到中央
        canvas.translate(mWidth / 2, mHeigth / 2);

        //绘制平移后的坐标系，红色
        drawTranslateCoordinateSpace(canvas);

        //绘制大圆
        drawCircle(canvas);

        //获得当前矩阵的逆矩阵
        Matrix invertMatrix = new Matrix();
        canvas.getMatrix().invert(invertMatrix);
        //使用 mapPoints 将触摸位置转换为画布坐标
        invertMatrix.mapPoints(mPts);

        //绘制小圆
        drawSmallCircle(canvas);
    }

    private void drawTranslateCoordinateSpace(Canvas canvas) {
        Path path1 = new Path();
        path1.moveTo(0, -mHeigth / 2);
        path1.lineTo(0, mHeigth / 2);

        Path path2 = new Path();
        path2.moveTo(-mWidth / 2, 0);
        path2.lineTo(mWidth / 2, 0);

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path1, mPaint);
        canvas.drawPath(path2, mPaint);
    }

    private void drawTouchCoordinateSpace(Canvas canvas) {
        canvas.save();
        canvas.translate(10, 10);
        Path path = new Path();
        path.moveTo(mWidth, 0);
        path.lineTo(0, 0);
        path.lineTo(0, mHeigth);

        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);
        canvas.restore();
    }

    private void drawCircle(Canvas canvas) {
        mCirclePath.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCirclePath.addCircle(0, 0, mCircleRadius, Path.Direction.CW);
        canvas.drawPath(mCirclePath, mPaint);
    }

    private void drawSmallCircle(Canvas canvas) {
        Log.i(TAG, "统一坐标系后绘制坐标: (" + mPts[0] + "," + mPts[1] + ")");
        mSmallCirclePath.reset();
        if (mSmallCircleRadius != -1 && mDownX != -1 && mDownY != -1) {
//            mSmallCirclePath.addCircle(mDownX, mDownY, mSmallCircleRadius, Path.Direction.CW);
            mSmallCirclePath.addCircle(mPts[0], mPts[1], mSmallCircleRadius, Path.Direction.CW);
            mPaint.setColor(Color.YELLOW);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawPath(mSmallCirclePath, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
//        int x = (int) event.getX();
//        int y = (int) event.getY();
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
//        Log.i(TAG, "坐标:(" + x + "," + y + ")");
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN 按下 坐标:(" + x + "," + y + ")");
                mDownX = x;
                mDownY = y;
                mSmallCircleRadius = Math.min(mWidth, mHeigth) / 6;//小圆半径;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE 移动 坐标:(" + x + "," + y + ")");
                mDownX = x;
                mDownY = y;
                mSmallCircleRadius = Math.min(mWidth, mHeigth) / 6;//小圆半径;
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP 松开 坐标:(" + x + "," + y + ")");
                mDownX = -1;
                mDownY = -1;
                mSmallCircleRadius = -1;
                break;
        }
        invalidate();//刷新界面
        return super.onTouchEvent(event);
    }

}
