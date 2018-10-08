package com.speex.papayaview.view.gesture;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Byron on 2018/10/8.
 */

public class RegionClickView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Paint mPaint;
    private int mWidth;
    private int mHeigth;
    private Path mCirclePath;
    private Region mCircleRegion;
    private int mSmallX, mSmallY, mRadius = 0;
    private Path mSmallCirclePath;


    public RegionClickView(Context context) {
        super(context);
        init();
    }

    public RegionClickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegionClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);

        mCirclePath = new Path();//路径
        mCircleRegion = new Region();//截取区域

        mSmallCirclePath = new Path();//小圆的路径
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeigth = h;

        //在屏幕中间添加一个圆
        mCirclePath.addCircle(mWidth / 2, mHeigth / 2, Math.min(mWidth, mHeigth) / 6, Path.Direction.CW);

        //将剪裁边界设置为视图大小
        Region globalRegion = new Region(-w, -h, w, h);

        //将 Path 添加到 Region 中
        mCircleRegion.setPath(mCirclePath, globalRegion);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制大圆
        drawCenterCircle(canvas);

        //绘制小圆
        drawSmallCircle(canvas);
    }

    private void drawCenterCircle(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        canvas.drawPath(mCirclePath, mPaint);
    }

    private void drawSmallCircle(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        mSmallCirclePath.reset();
        mSmallCirclePath.addCircle(mSmallX, mSmallY, mRadius, Path.Direction.CW);
        canvas.drawPath(mSmallCirclePath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
//        Log.i(TAG, "坐标:(" + x + "," + y + ")");
        mRadius = 0;
        mSmallX = x;
        mSmallY = y;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN 按下 坐标:(" + x + "," + y + ")");
                //点击区域判断
                if (mCircleRegion.contains(x, y)) {
                    Toast.makeText(getContext(), "圆被点击了", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "圆被点击了");
                } else {
                    Log.i(TAG, "点击圆外，在圆外面绘制一个小圆");
                    mRadius = 150;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE 移动 坐标:(" + x + "," + y + ")");
                if (mCircleRegion.contains(x, y)) {
                    Toast.makeText(getContext(), "在圆上移动", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "在圆上移动");
                } else {
                    Log.i(TAG, "在圆外移动，在圆外面绘制一个小圆");
                    mRadius = 150;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP 松开 坐标:(" + x + "," + y + ")");
                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
//        Log.i(TAG, "坐标:(" + x + "," + y + ")");
        //阻止父控件对按键事件的拦截,请求自身消费按键事件
        getParent().requestDisallowInterceptTouchEvent(true);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "dispatchTouchEvent ACTION_DOWN 按下 坐标:(" + x + "," + y + ")");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "dispatchTouchEvent ACTION_MOVE 移动 坐标:(" + x + "," + y + ")");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "dispatchTouchEvent ACTION_UP 松开 坐标:(" + x + "," + y + ")");
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
