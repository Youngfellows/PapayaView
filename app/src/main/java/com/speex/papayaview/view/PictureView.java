package com.speex.papayaview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.speex.papayaview.utils.ScreenSizeUtil;

/**
 * Created by Byron on 2018/8/26.
 */

public class PictureView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Picture mPicture;
    private int mWidth, mHeight;
    private Paint mPaint;

    public PictureView(Context context) {
        super(context);
        init(context);
    }

    public PictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PictureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        ScreenSizeUtil.getWidthHeigth1(context, new ScreenSizeUtil.ScreenSizeCallback() {
            @Override
            public void onSize(int width, int heigth) {
                Log.i(TAG, "onSize: width = " + width + " ,height = " + heigth);
                mWidth = width;
                mHeight = heigth;
            }
        });

        mPicture = new Picture();
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(24);

        recording();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 录制Canvas
     */
    private void recording() {
        Canvas canvas = mPicture.beginRecording(mWidth / 2, mHeight / 2);
        canvas.drawPoint(0, 0, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawPoint(mWidth / 4, mHeight / 4, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawPoint(mWidth / 3, mHeight / 3, mPaint);
        mPicture.endRecording();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        mPicture.draw(canvas);
    }
}
