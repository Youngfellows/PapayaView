package com.speex.studyview.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Byron on 2019/2/15.
 */

public class DrawPanView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private String TAG = this.getClass().getSimpleName();
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;//画布
    private Path mPath;//路径
    private Paint mPaint;//画笔
    private boolean mIsDrawing;//是否正在绘制

    public DrawPanView(Context context) {
        super(context);
        initView();
    }

    public DrawPanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DrawPanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        //初始化画笔、路径
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated");
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed");
        mIsDrawing = false;
    }

    /**
     * 在线程中执行绘制操作
     */
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        while (mIsDrawing) {
            draw();
        }
        long end = System.currentTimeMillis();
        // 50 - 100
        if (end - start < 100) {
            try {
                Thread.sleep(100 - (end - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw() {
        if (mSurfaceHolder != null) {
            try {
                synchronized (mSurfaceHolder) {
                    //获取画布
                    mCanvas = mSurfaceHolder.lockCanvas();
                    //绘制背景
                    if (mCanvas != null) {
                        mCanvas.drawColor(Color.WHITE);
                        mCanvas.drawPath(mPath, mPaint);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mCanvas != null) {
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                }
            }
        }
    }

    /**
     * 根据手势移动路径
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
