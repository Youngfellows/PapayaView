package com.speex.studyview.surfaceview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.speex.studyview.R;

/**
 * SurfaceView绘制图片
 */

public class DrawableSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private String TAG = this.getClass().getSimpleName();
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private boolean mIsDrawing;
    private Bitmap mBitmap;
    private Context mContext;

    public DrawableSurfaceView(Context context) {
        super(context);
        init(context, null);
    }

    public DrawableSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DrawableSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        //获取属性
        Drawable drawable;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableSurfaceView);
            drawable = typedArray.getDrawable(R.styleable.DrawableSurfaceView_imgSrc);
            typedArray.recycle();
        } else {
            drawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
        }
        if (drawable == null) {
            throw new RuntimeException("DrawableSurfaceView get null drawable");
        }
        //获取绘制的Bitmap
        mBitmap = ((BitmapDrawable) drawable).getBitmap();
        drawable.setLevel(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int drawableWidth = mBitmap.getWidth();
        int drawableHeight = mBitmap.getHeight();
        Log.i(TAG, "图片的 drawableWidth: " + drawableWidth + " ,drawableHeight: " + drawableHeight);

        //MeasureSpec封装了父View传递给子View的布局要求
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.d(TAG, "onMeasure specWidthSize: " + specWidthSize + " ,specHeightSize: " + specHeightSize);
        int mWidth = 0;
        int mHeight = 0;
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                //相当于match_parent或者一个具体值
                mWidth = specWidthSize;
                break;
            case MeasureSpec.AT_MOST:
                // 相当于wrap_content 默认会充满父View
                // 可以根据子View的大小来计算父View大小，这里先写死大小
                mWidth = drawableWidth;
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
                mHeight = drawableHeight;
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
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    private void draw() {
        if (mSurfaceHolder != null) {
            try {
                synchronized (mSurfaceHolder) {
                    mCanvas = mSurfaceHolder.lockCanvas();
                    //绘制图片
                    if (mCanvas != null) {
                        mCanvas.drawColor(Color.WHITE);
                        mCanvas.drawBitmap(mBitmap, 0, 0, null);
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

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            draw();
        }
    }
}
