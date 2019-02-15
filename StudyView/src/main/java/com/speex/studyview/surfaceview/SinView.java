package com.speex.studyview.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.speex.studyview.utils.DpUtil;

/**
 * 绘制Sin曲线
 */

public class SinView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private String TAG = this.getClass().getSimpleName();
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private boolean mIsDrawing;
    private int mWidth;
    private int mHeight;
    private Context mContext;
    private Paint mPaint;
    private Path mPath;
    private int x, y;//绘制点的X/Y坐标
    private final int OFFSET_X = 0;//X坐标的偏移量

    public SinView(Context context) {
        super(context);
        initView(context);
    }

    public SinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        //获取Path
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //MeasureSpec封装了父View传递给子View的布局要求
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.d(TAG, "onMeasure specWidthSize: " + specWidthSize + " ,specHeightSize: " + specHeightSize);

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                //相当于match_parent或者一个具体值
                mWidth = specWidthSize;
                break;
            case MeasureSpec.AT_MOST:
                // 相当于wrap_content 默认会充满父View
                // 可以根据子View的大小来计算父View大小，这里先写死大小
                mWidth = (int) DpUtil.dp2px(mContext, 200.0f);
                break;
            case MeasureSpec.UNSPECIFIED:
                //很少会用到
                break;
            default:
                mWidth = specWidthSize;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                mHeight = specHeightSize;
                break;
            case MeasureSpec.AT_MOST:
                mHeight = (int) DpUtil.dp2px(mContext, 200.0f);
                break;
            case MeasureSpec.UNSPECIFIED:
                //很少会用到
                break;
            default:
                mHeight = specHeightSize;
                break;
        }
        Log.d(TAG, "onMeasure mWidth: " + mWidth + " ,mHeight: " + mHeight);
        //存储测量好的宽和高
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated");
        mIsDrawing = true;
        mPath.moveTo(OFFSET_X, mHeight / 2);
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


    @Override
    public void run() {
        Log.i(TAG, "run");
        while (mIsDrawing) {
            draw();
            y = (int) (100 * Math.sin(x * 2 * Math.PI / 180) + mHeight / 2);
            x += 5;//修改X的坐标
            mPath.lineTo(x, y);
        }
    }

    /**
     * 绘制
     */
    public void draw() {
        Log.i(TAG, "draw");
        if (mSurfaceHolder != null) {
            try {
                //线程同步
                synchronized (mSurfaceHolder) {
                    mCanvas = mSurfaceHolder.lockCanvas();
                    //绘制Surface背景
                    if (mCanvas != null) {
                        mCanvas.drawColor(Color.YELLOW);
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
}
