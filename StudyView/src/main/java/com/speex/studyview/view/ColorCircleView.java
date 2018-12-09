package com.speex.studyview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.speex.studyview.R;
import com.speex.studyview.utils.DpUtil;
import com.speex.studyview.utils.StringUtils;

/**
 * Created by Byron on 2018/12/9.
 */

public class ColorCircleView extends BaseView {

    private int mCircleColor;
    private int mStrokeWidth;
    private Paint mPaint;

    public ColorCircleView(Context context) {
        this(context, null);
    }

    public ColorCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, R.styleable.ColorCircleView);
    }

    @Override
    public void init(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.init(set, defStyleAttr, attrs);
        mPaint = new Paint();
        mPaint.setColor(mCircleColor);
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setDither(true);//防抖动
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void getAttrs(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.getAttrs(set, defStyleAttr, attrs);
        //圆的半径
        mCircleColor = mTypedArray.getColor(R.styleable.ColorCircleView_circle_color, Color.RED);
        //圆的宽度
        mStrokeWidth = mTypedArray.getDimensionPixelSize(R.styleable.ColorCircleView_stroke_width, StringUtils.getDip(mContext, 3));
        Log.d(TAG, "strokeWidth: " + mStrokeWidth + " ,circleColor: " + mCircleColor);
        mTypedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "wSize = " + wSize + " ,hSize = " + hSize);

        int width = 0;
        int height = 0;


        switch (wMode) {
            case MeasureSpec.EXACTLY:
                //宽度设置为match_parent或者具体值
                width = wSize;
                break;
            case MeasureSpec.AT_MOST:
                //高度设置为wrap_content
                width = (int) DpUtil.dp2px(mContext, 100);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                width = wSize;
                break;
        }

        switch (hMode) {
            case MeasureSpec.EXACTLY:
                //高度设置为match_parent或者具体值
                height = hSize;
                break;
            case MeasureSpec.AT_MOST:
                height = (int) DpUtil.dp2px(mContext, 100);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                height = hSize;
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        float radius = (Math.min(measuredWidth, measuredHeight) - mPaint.getStrokeWidth()) / 2;
        Log.d(TAG, "radius = " + radius + " ,measuredWidth = " + measuredWidth + " ,measuredHeight = " + measuredHeight);
        canvas.drawCircle(measuredWidth / 2, measuredHeight / 2, radius, mPaint);
    }
}
