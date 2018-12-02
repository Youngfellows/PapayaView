package com.speex.studyview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.speex.studyview.R;
import com.speex.studyview.utils.StringUtils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Byron on 2018/12/2.
 */

public class RandomNumberView extends BaseView {

    private String mTitleText;//绘制的文本
    private int mTitleTextColor;//绘制文本的字体颜色
    private int mTitleTextSize;//绘制字体大小
    private Paint mPaint;//画笔
    private Rect mTextBound;//绘制时控制文本绘制的范围

    public RandomNumberView(Context context) {
        this(context, null);
    }

    public RandomNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RandomNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, R.styleable.RandomNumberView);//获取自定义属性
    }


    /**
     * 获得绘制文本的宽高
     *
     * @param set
     * @param defStyleAttr
     * @param attrs
     */
    @Override
    public void init(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.init(set, defStyleAttr, attrs);

        //获得绘制文本的宽高
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(mTitleTextSize);
        mTextBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextBound); //获得绘制文本的宽高

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText = randomText();
                postInvalidate();//刷新界面
            }
        });
    }

    /**
     * 获得4位随机数
     *
     * @return
     */
    private String randomText() {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        int randomInt;
        while (set.size() < 4) {
            randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }
        return sb.toString();
    }

    /**
     * 获取自定义属性值
     *
     * @param set
     * @param defStyleAttr
     * @param attrs
     */
    @Override
    public void getAttrs(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.getAttrs(set, defStyleAttr, attrs);
        mTitleText = mTypedArray.getString(R.styleable.RandomNumberView_titleText);
        mTitleTextColor = mTypedArray.getColor(R.styleable.RandomNumberView_titleTextColor, Color.BLACK);
        mTitleTextSize = mTypedArray.getDimensionPixelSize(R.styleable.RandomNumberView_titleTextSize, StringUtils.getSp(mContext, 16));
        Log.i(TAG, "titleText: " + mTitleText + " ,titleTextColor: " + mTitleTextColor + " ,titleTextSize:" + mTitleTextSize);
        mTypedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //解决控件的宽高是wrap_content时绘制的是占满整个屏幕的
        //或者控件的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.d(TAG, "onMeasure: widthMode = " + widthMode + ",widthMode = " + heightMode + " ,widthSize = " + widthSize + " ,heightSize = " + heightSize);

        //期望设置的控件的宽高
        int width = 0, height = 0;

        //如果宽设置为具体的值或者march_parent
        if (MeasureSpec.EXACTLY == widthMode) {
            width = widthSize;
        } else {
            //宽设置为wrap_content
            mPaint.setTextSize(mTitleTextSize);
            //获取文本的宽
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextBound);
            int textWidth = mTextBound.width();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            Log.d(TAG, "textWidth: " + textWidth + " ,paddingLeft: " + paddingLeft + " ,paddingRight: " + paddingRight);
            int desiredWidth = paddingLeft + textWidth + paddingRight;
            width = desiredWidth;
        }

        //如果高设置为具体的值或者march_parent
        if (MeasureSpec.EXACTLY == heightMode) {
            height = heightSize;
        } else {
            //高设置为wrap_content
            //宽设置为wrap_content
            mPaint.setTextSize(mTitleTextSize);
            //获取文本的宽
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextBound);
            int textHeight = mTextBound.height();
            int paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            Log.d(TAG, "textHeight: " + textHeight + " ,paddingTop: " + paddingTop + " ,paddingBottom: " + paddingBottom);
            int desiredHeight = paddingTop + textHeight + paddingBottom;
            height = desiredHeight;
        }
        //设置控件的大小
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        int measuredWidth = getMeasuredWidth();//控件的宽
        int measuredheight = getMeasuredHeight();//控件的高
        Log.i(TAG, "控件的宽:" + measuredWidth + " ,控件的高:" + measuredheight);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, measuredWidth, measuredheight, mPaint);

        //绘制文本
        mPaint.setColor(mTitleTextColor);
        int width = getWidth();
        int height = getHeight();
        int titleWidth = mTextBound.width();
        int titleHeight = mTextBound.height();

        int start = width / 2 - titleWidth / 2;
        int end = height / 2 + titleHeight / 2;
        Log.i(TAG, "控件宽高 width: " + width + " ,height: " + height);
        Log.i(TAG, "文本宽高 titleWidth: " + titleWidth + " ,titleHeight: " + titleHeight);
        Log.i(TAG, "文本的起始位置 start: " + start + " ,end: " + end);
        canvas.drawText(mTitleText, start, end, mPaint);//绘制文本
    }
}
