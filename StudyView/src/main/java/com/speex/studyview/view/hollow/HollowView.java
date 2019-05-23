package com.speex.studyview.view.hollow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.speex.studyview.R;
import com.speex.studyview.utils.StringUtils;
import com.speex.studyview.view.BaseView;


/**
 * 镂空
 */
public class HollowView extends BaseView {
    private String TAG = this.getClass().getSimpleName();

    /**
     * 镂空半径
     */
    private int mHollowRadius;

    /**
     * 镂空的X坐标
     */
    private int mHollowCenterX;

    /**
     * 镂空的Y坐标
     */
    private int mHollowCenterY;

    /**
     * 镂空颜色
     */
    private int mHollowColor;

    /**
     * 画笔
     */
    private Paint mHollowPaint;

    public HollowView(Context context) {
        this(context, null);
    }

    public HollowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HollowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, R.styleable.HollowView);
    }

    @Override
    public void init(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.init(set, defStyleAttr, attrs);
        initAttr();
    }

    @Override
    public void getAttrs(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.getAttrs(set, defStyleAttr, attrs);
        mHollowRadius = mTypedArray.getDimensionPixelSize(R.styleable.HollowView_hollow_radius, StringUtils.getDip(mContext, 50));
        mHollowCenterX = mTypedArray.getDimensionPixelSize(R.styleable.HollowView_hollow_centerX, StringUtils.getDip(mContext, 50));
        mHollowCenterY = mTypedArray.getDimensionPixelSize(R.styleable.HollowView_hollow_centerY, StringUtils.getDip(mContext, 50));
        mHollowColor = mTypedArray.getColor(R.styleable.MixTextImage_mix_title_color, Color.BLACK);
        mTypedArray.recycle();

        Log.d(TAG, "半径: " + mHollowRadius + " ,圆心(" + mHollowCenterX + "," + mHollowCenterY + "),颜色: " + mHollowColor);
    }

    /**
     * 初始化画笔
     */
    private void initAttr() {
        mHollowPaint = new Paint();
        mHollowPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mHollowPaint.setColor(Color.parseColor("#7f000000"));//百分之50
        //        mHollowPaint.setColor(Color.parseColor("#32000000"));//百分之20
        //        mHollowPaint.setColor(Color.parseColor("#c8000000"));//百分之80

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);//关闭硬件加速
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHollowCenterX = getWidth() / 2;
        mHollowCenterY = getHeight() / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先绘制背景
        canvas.drawRect(0, 0, getWidth(), getHeight(), mHollowPaint);

        //绘制镂空圆
        //从绘制模式图片得知 这种模式会导致挖空的区域消失
        Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        mHollowPaint.setXfermode(mXfermode);
        canvas.drawCircle(mHollowCenterX, mHollowCenterY, mHollowRadius, mHollowPaint);
        mHollowPaint.setXfermode(null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.w(TAG, "按下 ACTION_DOWN ,坐标(" + x + " ," + y + ") ,圆心(" + mHollowCenterX + " ," + mHollowCenterY + ")");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "移动 ACTION_MOVE ,坐标(" + x + " ," + y + ") ,圆心(" + mHollowCenterX + " ," + mHollowCenterY + ")");
                break;
            case MotionEvent.ACTION_UP:
                Log.w(TAG, "松开 ACTION_UP ,坐标(" + x + " ," + y + ") ,圆心(" + mHollowCenterX + " ," + mHollowCenterY + ")");
                mHollowRadius = (int) Math.min(Math.abs(x - mHollowCenterX), Math.abs(y - mHollowCenterY));
                Log.w(TAG, "缩放的半径: mHollowRadius = " + mHollowRadius);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;

            default:
                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }
}
