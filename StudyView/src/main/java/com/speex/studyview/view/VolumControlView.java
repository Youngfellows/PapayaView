package com.speex.studyview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.speex.studyview.R;
import com.speex.studyview.utils.StringUtils;

/**
 * Created by Byron on 2018/12/5.
 */

public class VolumControlView extends BaseView {
    /**
     * 进度条填充色
     */
    private int mProgressColor;

    /**
     * 进度条背景色
     */
    private int mBackgroundColor;

    /**
     * 圈的宽度
     */
    private int mCircleWidth;

    /**
     * 画笔
     */
    private Paint mPaint;
    private Rect mRect;

    //百分比文字
    private Paint mTextPaint;
    private Rect mTextBound;

    /**
     * 百分比文字大小
     */
    private int mPercentTextSize;

    /**
     * 百分比文字颜色
     */
    private int mPercentTextColor;

    /**
     * 是否显示百分比文字
     */
    private boolean mTextVisible = true;

    /**
     * 当前进度，默认起始时是 3
     */
    private int mCurrentCount = 3;

    /**
     * 中间的图片
     */
    private Bitmap mImage;

    /**
     * 每个进度块间的间隙
     */
    private int mSplitSize;

    /**
     * 进度块总数
     */
    private int mCount;

    /**
     * 圆心角度数
     */
    private int mCentralAangle;


    public VolumControlView(Context context) {
        this(context, null);
    }

    public VolumControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VolumControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, R.styleable.VolumControlView);//初始化属性
    }

    @Override
    public void init(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.init(set, defStyleAttr, attrs);

        //圆环
        mPaint = new Paint();
        mRect = new Rect();
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 定义线段断点形状为圆头
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心

        //中间的文字
        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);//消除锯齿
        mTextPaint.setTextSize(mPercentTextSize);
        mTextPaint.setColor(mPercentTextColor);
    }

    @Override
    public void getAttrs(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.getAttrs(set, defStyleAttr, attrs);
        mProgressColor = mTypedArray.getColor(R.styleable.VolumControlView_progressColor, Color.BLUE);
        mBackgroundColor = mTypedArray.getColor(R.styleable.VolumControlView_backgroundColor, Color.RED);
        mImage = BitmapFactory.decodeResource(getResources(), mTypedArray.getResourceId(R.styleable.VolumControlView_centerPic, 0));
        mCircleWidth = mTypedArray.getDimensionPixelSize(R.styleable.VolumControlView_circleWidth, StringUtils.getPx(mContext, "5"));
        mCount = mTypedArray.getInt(R.styleable.VolumControlView_dotCount, 20);
        mSplitSize = mTypedArray.getInt(R.styleable.VolumControlView_spliteSize, 15);
        mCentralAangle = mTypedArray.getInt(R.styleable.VolumControlView_centralAngle, 360);//默认是圆形，小于360则显示为圆弧
        mPercentTextSize = mTypedArray.getDimensionPixelSize(R.styleable.VolumControlView_textSize, StringUtils.getSp(mContext, 14));
        mPercentTextColor = mTypedArray.getColor(R.styleable.VolumControlView_textColor, Color.BLACK);
        mTextVisible = mTypedArray.getBoolean(R.styleable.VolumControlView_textVisible, true);
        mTypedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;//控件的宽高
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (mTextVisible) {
            //测量文字的宽度
            mTextPaint.getTextBounds("100%", 0, "100%".length(), mTextBound);
        }

        // 由图片和文字决定的宽
        int desireWidth = getPaddingLeft() + getPaddingRight() + Math.max(mTextBound.width(), mImage.getWidth());
        Log.d(TAG, "specWidthSize: " + specWidthSize + " ,desireWidth: " + desireWidth);

        switch (specWidthMode) {
            case MeasureSpec.EXACTLY:
                //宽度设置为 match_parent or 指定尺寸
                Log.i(TAG, "宽度设置为 match_parent or 指定尺寸");
                width = specWidthSize;
                break;
            case MeasureSpec.AT_MOST:
                //宽度设置为 wrap_content
                Log.i(TAG, "宽度设置为 wrap_content");
                width = Math.min(desireWidth, specWidthSize);
                Log.i(TAG, "宽度设置为 wrap_content,width = " + width);
                break;
            case MeasureSpec.UNSPECIFIED:
                //父控件对子控件不加任何束缚，子元素可以得到任意想要的大小，这种MeasureSpec一般是由父控件自身的特性决定的。
                //比如ScrollView，它的子View可以随意设置大小，无论多高，都能滚动显示，这个时候，尺寸就选择自己需要的尺寸size。
                break;
            default:
                width = specWidthSize;
                break;
        }
        Log.d(TAG, "设置的width: " + width);
        //正方形，边长以宽为准
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int center = getWidth() / 2; //获取圆形的x坐标
        int radius = center - mCircleWidth / 2;   //半径
        Log.i(TAG, "center: " + center + " ,radius: " + radius);
//        int radius = center;   //半径

        //根据参数画出每个进度块
        drawOval(canvas, center, radius);

        //计算内切正方形的位置
        int relRadius = radius - mCircleWidth / 2;// 获得内圆的半径

        //内切正方形的距离: 左边 = mCircleWidth + relRadius - √2 / 2
        mRect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        //内切正方形的距离: 顶部 = mCircleWidth + relRadius - √2 / 2
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        mRect.bottom = (int) (mRect.top + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);

        //绘制正方形
//        canvas.drawRect(mRect, mPaint);

        //如果图片比较小，那么根据图片的尺寸放置到正中心
        if (mImage.getWidth() < Math.sqrt(2) * relRadius) {
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getHeight() * 1.0f / 2);
            mRect.right = mRect.left + mImage.getWidth();
            mRect.bottom = mRect.top + mImage.getHeight();
        }

        // 绘图
        canvas.drawBitmap(mImage, null, mRect, mPaint);

        //更新百分比数字
        if (mTextVisible) {
            String percentText = StringUtils.getPercent(mCurrentCount, mCount);
            mTextPaint.getTextBounds(percentText, 0, percentText.length(), mTextBound);
            canvas.drawText(percentText, getWidth() / 2 - mTextBound.width() / 2, getHeight() / 2 + mTextBound.height() / 2, mTextPaint);
        }
    }

    /**
     * 根据参数画出每个进度块
     */
    private void drawOval(Canvas canvas, int centre, int radius) {
        // 用于定义的圆弧的形状和大小的界限
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
//        canvas.drawRect(oval,mPaint);

        //分割块的个数，默认为圆形的分割块数，等于mCount
        int splitCount = mCount;

        /**
         * 开始画弧的起始角度位置，0 度为 x 轴正方向，顺时针开始画弧
         * 此处设置为以 y 轴负方向为起点开始顺时针画弧
         */
        int startAngle = 90 + mSplitSize / 2;
        Log.i(TAG, "startAngle: " + startAngle + " ,mSplitSize: " + mSplitSize);

        if (mCentralAangle < 360 && mCentralAangle > 0) {//半圆弧
            splitCount--;
            startAngle = 270 - mCentralAangle / 2;
        } else {
            mCentralAangle = 360;
        }

        //根据需要画的个数以及间隙计算每个进度块的长度（以圆周长360为基准）
        float itemSize = (mCentralAangle * 1.0f - mSplitSize * splitCount) / mCount;
        Log.i(TAG, "itemSize: " + itemSize);

        // 画进度条
        mPaint.setColor(mProgressColor);
        for (int i = 0; i < mCount; i++) {
            canvas.drawArc(oval, startAngle + i * (itemSize + mSplitSize), itemSize, false, mPaint);
        }

        // 画进度条背景色
        mPaint.setColor(mBackgroundColor);
        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(oval, startAngle + i * (itemSize + mSplitSize), itemSize, false, mPaint); // 根据进度画圆弧
        }
    }

    private float yDown, yMove, delt;
    private int moveCount;

    /**
     * 处理手势
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN");
                yDown = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //不让父 view 拦截触摸事件
                getParent().requestDisallowInterceptTouchEvent(true);
                Log.i(TAG, "ACTION_MOVE");
                //上下滑动动态更改进度条
                yMove = event.getY();
                delt = yMove - yDown;
                moveCount = (int) (delt / 50);

                if (moveCount > 0) {
                    //向下滑
                    for (int i = 0; i < moveCount; i++) {
                        down();
                    }
                } else if (moveCount < 0) {
                    //向上滑
                    for (int i = 0; i < -moveCount; i++) {
                        up();
                    }
                }

                if (Math.abs(moveCount) > 0) {
                    yDown = yMove;
                }
                break;
        }
//        return super.onTouchEvent(event);
        return true;//自己消费事件
    }

    /**
     * 上滑，当前数量 +1
     */
    private void up() {
        if (mCurrentCount < mCount) {
            mCurrentCount++;
            postInvalidate();
        }
    }

    /**
     * 下滑，当前数量 -1
     */
    private void down() {
        if (mCurrentCount > 0) {
            mCurrentCount--;
            postInvalidate();
        }
    }


}
