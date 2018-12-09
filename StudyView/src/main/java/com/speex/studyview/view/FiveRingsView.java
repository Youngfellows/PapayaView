package com.speex.studyview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.speex.studyview.utils.DpUtil;

/**
 * Created by Byron on 2018/12/9.
 */

public class FiveRingsView extends ViewGroup {
    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private TextPaint mTextPaint;//绘制文本的画笔
    private int mStartX;//圆环起始X轴
    private int mStartY;//圆环起始Y轴
    private int mWidth;//ViewGroup宽
    private int mHeight;//ViewGroup的高


    public FiveRingsView(Context context) {
        this(context, null);
    }

    public FiveRingsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FiveRingsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        setBackgroundColor(Color.GRAY);
//        setBackgroundColor(Color.TRANSPARENT);
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(Color.BLACK);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //触发所有子View的onMeasure函数去测量宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //MeasureSpec封装了父View传递给子View的布局要求
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (wMode) {
            case MeasureSpec.EXACTLY:
                //控件宽度设置为match_parent或具体值
                mWidth = wSize;
                break;
            case MeasureSpec.AT_MOST:
                //控件宽度设置为wrap_content
                //这里应该先计算所有子view的宽度，暂时先写死
                mWidth = wSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        switch (hMode) {
            case MeasureSpec.EXACTLY:
                //控件高度设置为match_parent或具体值
                mHeight = hSize;
                break;
            case MeasureSpec.AT_MOST:
                //控件高度设置为wrap_content
                //这里应该先计算所有子view的高度，暂时先写死
                mHeight = hSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        Log.d(TAG, "mWidth: " + mWidth + " ,mHeight: " + mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childNum = getChildCount();
        mStartX = mStartY = 0;//圆的起始位置XY坐标
        Log.d(TAG, "childNum = " + childNum);
        int gap = 10;//同一行圆圈之间的间隔
        int screenWidth = DpUtil.getScreenSizeWidth(mContext);//屏幕宽度
        int firstTotalWidth = 0;//第一行子View的总宽度
        int secondTotalWidth = 0;//第二行子View的总宽度
        for (int i = 0; i < childNum; i++) {
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();//子控件的宽度
            if (i <= 2) {
                //前三个总宽度
                firstTotalWidth += childWidth;
            } else {
                //后两个总宽度
                secondTotalWidth += childWidth;
            }
        }

        //前3个的距离左边的距离
        int leftFMargin = (screenWidth - firstTotalWidth - gap * 2) / 2;
        //后2个距离左边的距离
        int leftSMargin = (screenWidth - secondTotalWidth - gap) / 2;
        for (int i = 0; i < childNum; i++) {
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            if (i <= 2) {
                //排列前三个圆圈
                if (i == 0) {
                    childView.layout(leftFMargin + mStartX, mStartY, leftFMargin + mStartX + childWidth, mStartY + childHeight);
                    mStartX += childWidth;
                } else {
                    childView.layout(leftFMargin + mStartX + gap, mStartY, leftFMargin + mStartX + gap + childWidth, mStartY + childHeight);
                    mStartX += (childWidth + gap);
                }

                if (i == 2) {
                    mStartX = 0;
                    mStartY += childHeight / 2;
                }
            } else {
                //排列后两个圆圈
                if (i == 3) {
                    childView.layout(leftSMargin + mStartX, mStartY, leftSMargin + mStartX + childWidth, mStartY + childHeight);
                    mStartX += childWidth;
                } else {
                    childView.layout(leftSMargin + mStartX + gap, mStartY, leftSMargin + mStartX + gap + childWidth, mStartY + childHeight);
                    mStartX += (childWidth + gap);
                }
            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int screenWidth = DpUtil.getScreenSizeWidth(mContext);
        //绘制文字
        String upStr = "同一个世界，同一个梦想";
        String downStr = "One World,One Dream";
        canvas.drawText(upStr, (screenWidth - mTextPaint.measureText(upStr)) / 2, getCircleHeight() / 2 * 3 + 60, mTextPaint);
        canvas.drawText(downStr, (screenWidth - mTextPaint.measureText(downStr)) / 2, getCircleHeight() / 2 * 3 + 120, mTextPaint);
    }

    /**
     * 获得圆环高度
     *
     * @return 圆环高度
     */
    private int getCircleHeight() {
        //5个圆环大小是一样的，这里就直接取第一个了
        View childView = getChildAt(0);
        int childHeight = childView.getMeasuredHeight();
        return childHeight;
    }
}
