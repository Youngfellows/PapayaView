package com.speex.studyview.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * SurfaceView绘制模板
 */

public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private String TAG = this.getClass().getSimpleName();
    private SurfaceHolder mSurfaceHolder;
    // 用于绘图的Canvas
    private Canvas mCanvas;
    // 子线程标志位
    private boolean mIsDrawing;

    public SurfaceViewTemplate(Context context) {
        super(context);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //初始化SurfaceHolder对象
        mSurfaceHolder = getHolder();

        //注册SurfaceHolder回调
        mSurfaceHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated");
        mIsDrawing = true;
        new Thread(this).start();//启动绘制线程
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed");

    }

    /**
     * 在子线程内进行绘制
     */
    @Override
    public void run() {
        //mIsDrawing标志位控制子线程
        while (mIsDrawing) {
            draw();
        }
    }

    private void draw() {

        try {
            //获得当前Canvas绘图对象
            //获取到的Canvas对象还是继续上次的Canvas对象，而不是一个新的对象。因此，之前的绘图操作都将保留，如果需要擦除，则可以在绘制前，通过drawColor()方法来进行清屏操作。
            mCanvas = mSurfaceHolder.lockCanvas();
            // TODO: 2019/2/15 绘制
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                //将该方法放到finally代码块中，来保证每次都能将画布内容进行提交
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }
}
