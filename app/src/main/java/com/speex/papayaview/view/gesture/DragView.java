package com.speex.papayaview.view.gesture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.speex.papayaview.R;
import com.speex.papayaview.utils.ScreenUtils;

/**
 * Created by Byron on 2018/10/13.
 */

public class DragView extends View {
    private String TAG = this.getClass().getSimpleName();
    private Paint mPaint;
    private int mWidth, mHeigth;

    private Bitmap mBitmap;         // 图片
    private RectF mBitmapRectF;     // 图片所在区域
    private Matrix mBitmapMatrix;   // 控制图片的 matrix


    private boolean canDrag = false;//能否拖动
    private PointF lastPoint = new PointF(0, 0);//上次按下的点

    public DragView(Context context) {
        super(context);
        init(context);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mWidth = ScreenUtils.getScreenWidth(context);
        mHeigth = ScreenUtils.getScreenHeight(context);
        Log.i(TAG, "mWidth: " + mWidth + " ,mHeigth: " + mHeigth);

        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        //调整图片大小
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 1;
        options.outWidth = mWidth / 2;
        options.outHeight = mHeigth / 2;

        mBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.drag_img, options);
        mBitmapRectF = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mBitmapMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeigth = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        float x = event.getX();
        float y = event.getY();
        Log.i(TAG, "index = " + index + " ,action = " + action + " ,坐标(" + x + "," + y + ")");
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.w(TAG, "第" + (index + 1) + "个手指按下");
                //判断是否是第一个手指 && 是否包含在图片区域内
                if (event.getPointerId(index) == 0 && mBitmapRectF.contains(x, y)) {
                    Log.i(TAG, "第1个手指按下");
                    canDrag = true;
                    lastPoint.set(x, y);
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                Log.w(TAG, "第" + (index + 1) + "个手指抬起");
                //判断是否是第一个手指
                if (event.getPointerId(index) == 0) {
                    Log.i(TAG, "第1个手指抬起");
                    canDrag = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "ACTION_MOVE 移动 index = " + index);

                //如果存在第一个手指，且这个手指的落点在图片区域内
                if (canDrag) {
                    //注意 getX 和 getY
                    int pointerIndex = event.findPointerIndex(0);

                    mBitmapMatrix.postTranslate(event.getX(pointerIndex) - lastPoint.x, event.getY(pointerIndex) - lastPoint.y);
                    lastPoint.set(event.getX(index), event.getY(index));

                    mBitmapRectF = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
                    mBitmapMatrix.mapRect(mBitmapRectF);
                }
                break;
        }
        //刷新
        invalidate();
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mBitmapMatrix, mPaint);
    }
}
