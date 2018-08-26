package com.speex.papayaview.view.checkview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.speex.papayaview.R;

/**
 * Created by Byron on 2018/8/26.
 */

public class CheckPapayaView extends View {
    private String TAG = this.getClass().getSimpleName();
    private int mWidth, mHeight;//屏幕宽高
    private Handler mHandler;
    private Paint mPaint;
    private int mAnimCurrentPage = -1;//当前页码
    private int ANIM_MAX_PAGE = 13;//最大13页
    private int mAnimDuration = 500;//动画执行时间500毫秒
    private AnimState mAnimState = AnimState.ANIM_NULL;//默认不执行
    private static final int HANDLER_WHAD = 1;
    private boolean isCheck = false;//是否选中状态
    private Bitmap mOkBitmap;

    public CheckPapayaView(Context context) {
        super(context);
        init(context);
    }

    public CheckPapayaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CheckPapayaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(0xffFF5317);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mOkBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.checkmark, null);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case HANDLER_WHAD:
                        if (mAnimCurrentPage < ANIM_MAX_PAGE && mAnimCurrentPage >= 0) {
                            invalidate();
                            if (mAnimState == AnimState.ANIM_NULL)
                                return;
                            if (mAnimState == AnimState.ANIM_CHECK) {

                                mAnimCurrentPage++;
                            } else if (mAnimState == AnimState.ANIM_UNCHECK) {
                                mAnimCurrentPage--;
                            }
                            this.sendEmptyMessageDelayed(HANDLER_WHAD, mAnimDuration / ANIM_MAX_PAGE);
                            Log.e("AAA", "Count=" + mAnimCurrentPage);
                        } else {
                            Log.i(TAG, "handleMessage: xxxxxxxxx");
                            if (isCheck) {
                                mAnimCurrentPage = ANIM_MAX_PAGE - 1;
                            } else {
                                mAnimCurrentPage = -1;
                            }
                            invalidate();
                            mAnimState = AnimState.ANIM_NULL;
                        }
                        break;
                }
            }
        };

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //平移画布
        canvas.translate(mWidth / 2, mHeight / 2);

        //绘制圆
        float radius = Math.min(mWidth / 2, mHeight / 2) / 2;
        canvas.drawCircle(0, 0, radius, mPaint);

        //获取图片的边长
        int width = mOkBitmap.getWidth();
        int sideLength = mOkBitmap.getHeight();
        Log.i(TAG, "图片: width = " + width + " ,sideLength = " + sideLength);

        //绘制图片
        int left = mAnimCurrentPage * sideLength;
        int top = 0;
        int right = (mAnimCurrentPage + 1) * sideLength;
        int bootom = sideLength;
        Rect src = new Rect(left, top, right, bootom);
        Rect dest = new Rect(-(int) radius, -(int) radius, (int) radius, (int) radius);
        canvas.drawBitmap(mOkBitmap, src, dest, null);
    }

    /**
     * 选择OK
     */
    public void check() {
        if (isCheck || mAnimState != AnimState.ANIM_NULL) {
            return;
        }
        mAnimState = AnimState.ANIM_CHECK;
        mAnimCurrentPage = 0;
        mHandler.sendEmptyMessageAtTime(HANDLER_WHAD, mAnimDuration / ANIM_MAX_PAGE);
        isCheck = true;
    }

    /**
     * 取消选择
     */
    public void unCheck() {
        if (!isCheck || mAnimState != AnimState.ANIM_NULL) {
            return;
        }

        mAnimState = AnimState.ANIM_UNCHECK;
        mAnimCurrentPage = -1;
        mHandler.sendEmptyMessageAtTime(HANDLER_WHAD, mAnimDuration / ANIM_MAX_PAGE);
        isCheck = false;
    }
}
