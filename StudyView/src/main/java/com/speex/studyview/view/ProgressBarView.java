package com.speex.studyview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.speex.studyview.R;
import com.speex.studyview.utils.StringUtils;

/**
 * Created by Byron on 2018/12/3.
 */

public class ProgressBarView extends BaseView {

    private static final int DEFAULT_SIZE = 150;//view 默认边长（dp）
    private static final int DEFAULT_SPEED = 20;//默认速度
    private int mProgressColor;//进度条背景
    private int mBackgroundColor;//圆环背景
    private int mProgressWidth; //圆环宽度
    private int mPercentTextColor;//字体颜色
    private int mPercentTextSize;  //字体大小
    private Paint mPaint;
    private RectF mOval;
    private Paint mTextPaint;
    private Rect mTextBound;

    /**
     * 当前进度
     */
    private int mProgress;

    /**
     * 是否停止绘制
     */
    private boolean stopThread = false;

    /**
     * 速度
     */
    private int mSpeed;

    public ProgressBarView(Context context) {
        this(context, null);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, R.styleable.ProgressBarView);//初始化属性
    }

    /**
     * 初始化属性设置
     *
     * @param set
     * @param defStyleAttr
     * @param attrs
     */
    @Override
    public void init(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.init(set, defStyleAttr, attrs);

        mOval = new RectF(); //圆环
        mPaint = new Paint();//画笔
        mPaint.setStrokeWidth(mProgressWidth);//设置圆环的宽度
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mPaint.setColor(mBackgroundColor);//圆环颜色

        mTextPaint = new Paint();//绘制文字的画笔
        mTextBound = new Rect();//测量文字的矩形
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mPercentTextColor);
        mTextPaint.setTextSize(mPercentTextSize);//设置绘制文字的大小

        //初始化绘制
        initSpeed();
    }

    /**
     * 初始化绘制
     */
    private void initSpeed() {
        // 绘图线程
        new Thread() {
            public void run() {
                while (!stopThread) {
                    if (mProgress == 360) {//进度条跑完一圈后交换颜色
                        mProgress = 0;
                        int tempColor = mProgressColor;
                        mProgressColor = mBackgroundColor;
                        mBackgroundColor = tempColor;
                    }
                    mProgress++;//加 1 操作放在 mProgress == 360 后面，使百分比可以显示 100% ，再从 0% 开始
                    postInvalidate();//更新

                    //速度
                    try {
                        if (mSpeed > 0) {
                            Thread.sleep(500 / mSpeed);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * 获取设置的属性值
     *
     * @param set
     * @param defStyleAttr
     * @param attrs
     */
    @Override
    public void getAttrs(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.getAttrs(set, defStyleAttr, attrs);
        //进度条背景
        mProgressColor = mTypedArray.getColor(R.styleable.ProgressBarView_progressColor, Color.GREEN);
        //圆环背景
        mBackgroundColor = mTypedArray.getColor(R.styleable.ProgressBarView_backgroundColor, Color.RED);
        //圆环宽度
        mProgressWidth = mTypedArray.getDimensionPixelSize(R.styleable.ProgressBarView_circleWidth, StringUtils.getDip(mContext, 20));
        //字体颜色
        mPercentTextColor = mTypedArray.getColor(R.styleable.ProgressBarView_textColor, Color.WHITE);
        //字体大小
        mPercentTextSize = mTypedArray.getDimensionPixelSize(R.styleable.ProgressBarView_textSize, StringUtils.getSp(mContext, 15));
        //速度
        mSpeed = mTypedArray.getInt(R.styleable.ProgressBarView_speed, DEFAULT_SPEED);
        mTypedArray.recycle();
        Log.d(TAG, "getAttrs mProgressWidth: " + mProgressWidth + " ,mPercentTextSize: " + mPercentTextSize);
    }

    /**
     * 测量获取控件的大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;//控件宽高相同

        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);//宽的约束测量模式
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);//宽度

        //默认的宽高
        int desireWidth = getPaddingLeft() + getPaddingRight() + StringUtils.getDip(mContext, DEFAULT_SIZE);
        if (MeasureSpec.EXACTLY == specWidthMode) {
            //宽度设置 match_parent or 指定尺寸
            width = specWidthSize;
        } else if (MeasureSpec.AT_MOST == specWidthMode) {
            //宽度设置 wrap_content
            width = Math.min(desireWidth, specWidthSize);
        } else if (MeasureSpec.UNSPECIFIED == specWidthMode) {
            //父控件对子控件不加任何束缚，子元素可以得到任意想要的大小，这种MeasureSpec一般是由父控件自身的特性决定的。
            //比如ScrollView，它的子View可以随意设置大小，无论多高，都能滚动显示，这个时候，尺寸就选择自己需要的尺寸size。
        } else {
            width = specWidthSize;//默认值
        }

        //正方形，边长以宽为准
        Log.d(TAG, "设置的圆环的宽高: (" + width + " ," + width + ")");
        setMeasuredDimension(width, width);
    }

    /**
     * 绘制控件
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        // 获取圆心的x坐标
        int center = getWidth() / 2;
        int centerY = getHeight() / 2;
        // 半径：圆心到圆环中心的距离
        int paddingLeft = getPaddingLeft();
        int radius = center - paddingLeft - mProgressWidth / 2;
        Log.d(TAG, "圆心: (" + center + " ," + centerY + ") 半径: " + radius + " ,paddingLeft: " + paddingLeft);
//        canvas.drawCircle(center, centerY, radius, mPaint);//绘制圆环

        //开始画弧
        // 用于定义的圆弧的形状和大小的界限
        mOval.set(center - radius, center - radius, center + radius, center + radius);
        //canvas.drawRect(mOval, mPaint);

        //画笔在圆环的中心线上
        //画进度条
        mPaint.setColor(mProgressColor); // 设置进度条的颜色
        // canvas.drawCircle(centre, centre, radius, mPaint); // 画圆环
        canvas.drawArc(mOval, -90, mProgress, false, mPaint);//画弧

        //画背景条
        mPaint.setColor(mBackgroundColor); // 设置背景条的颜色
        canvas.drawArc(mOval, mProgress - 90, 360 - mProgress, false, mPaint);//圆弧

        //更新百分比数字
        String mPercentText = StringUtils.getPercent(mProgress, 360);
        mTextPaint.getTextBounds(mPercentText, 0, mPercentText.length(), mTextBound);
        canvas.drawText(mPercentText, getWidth() / 2 - mTextBound.width() / 2, getHeight() / 2 + mTextBound.height() / 2, mTextPaint);
    }


    /**
     * 设置速度
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        mSpeed = speed;
    }

    /**
     * 关闭线程
     *
     * @param stop
     */
    public void stopThread(boolean stop) {
        stopThread = stop;
    }
}
