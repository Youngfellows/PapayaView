package com.speex.studyview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.speex.studyview.bean.CakeBean;
import com.speex.studyview.utils.DpUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Byron on 2018/12/8.
 * 饼状图
 */
public class CakeView extends BaseView {
    private List<CakeBean> mCakeBeanList;  //装载的饼状圆数据
    private RectF mCRectF;  //画圆的矩形
    private RectF mRRectF;   //右边的小矩形
    private Paint mPaint;//画笔
    private int mCWidth;//圆的宽度
    private int mCHeight;//圆的高度
    private float mRotateDegree;//每个圆弧的起始角度
    private float mSumValue = 0;//所有值的和
    private float mDiameter;//圆的直径
    private float mTextY;//绘制文字的Y坐标

    private float mRectHeight = 40;//矩形的高度40px
    private float mRectWidth = 80;//矩形的高度80px
    private float mMargin = 40;//矩形和圆的距离


    public CakeView(Context context) {
        this(context, null);
    }

    public CakeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CakeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init(attrs, defStyleAttr, null);
        init();
    }

    private void init() {
        mCakeBeanList = new ArrayList<>();
        mCRectF = new RectF();
        mRRectF = new RectF();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//消除居住
        mPaint.setDither(true);//防抖动
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //MeasureSpec封装了父View传递给子View的布局要求
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.d(TAG, "onMeasure specWidthSize: " + specWidthSize + " ,specHeightSize: " + specHeightSize);

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                //相当于match_parent或者一个具体值
                mCWidth = specWidthSize;
                break;
            case MeasureSpec.AT_MOST:
                // 相当于wrap_content 默认会充满父View
                // 可以根据子View的大小来计算父View大小，这里先写死大小
                mCWidth = (int) DpUtil.dp2px(mContext, 400.0f);
                break;
            case MeasureSpec.UNSPECIFIED:
                //很少会用到
                break;
            default:
                mCWidth = specWidthSize;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                mCHeight = specHeightSize;
                break;
            case MeasureSpec.AT_MOST:
                mCHeight = (int) DpUtil.dp2px(mContext, 400.0f);
                break;
            case MeasureSpec.UNSPECIFIED:
                //很少会用到
                break;
            default:
                mCHeight = specHeightSize;
                break;
        }
        Log.d(TAG, "onMeasure mCWidth: " + mCWidth + " ,mCHeight: " + mCHeight);

        //存储测量好的宽和高
        setMeasuredDimension(mCWidth, mCHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDiameter = Math.min(mCWidth, mCHeight);
        Log.d(TAG, "mDiameter = " + mDiameter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mTextY = 0;
        //设置圆形绘制的范围
        mCRectF.set(0, 0, mDiameter, mDiameter);

        //画布中心X坐标向右移动（控件宽度-圆直径）之差的八分之一的距离
        //画布中心Y坐标向下移动（控件宽度-圆直径）之差的二分之一的距离
        canvas.translate((mCWidth - mDiameter) / 8, (mCHeight - mDiameter) / 2);
//        canvas.drawRect(mCRectF, mPaint);

        if (mCakeBeanList != null && mCakeBeanList.size() > 0 && Float.compare(mSumValue, 0.0f) != 0) {
            for (int i = 0; i < mCakeBeanList.size(); i++) {
                CakeBean cakeBean = mCakeBeanList.get(i);
                //画圆弧
                mPaint.setColor(cakeBean.color);
                canvas.drawArc(mCRectF, mRotateDegree, cakeBean.degree, true, mPaint);
                mRotateDegree += cakeBean.degree;

                //绘制矩形和文字
                drawRectAndText(canvas, cakeBean);
            }
        }
    }

    /**
     * 绘制矩形和文字
     *
     * @param canvas
     * @param cakeBean
     */
    private void drawRectAndText(Canvas canvas, CakeBean cakeBean) {
        mRRectF = new RectF();
        //设置画矩形的范围
        float left = mDiameter + mMargin;
        float rigth = mDiameter + mMargin + mRectWidth;
        float bottom = mTextY + mRectHeight;
        mRRectF.set(left, mTextY, rigth, bottom);
        canvas.drawRect(mRRectF, mPaint);

        //设置字体颜色
        mPaint.setColor(Color.BLACK);
        //设置文字大小
        mPaint.setTextSize(30);
        //画文字
        canvas.drawText(cakeBean.name + "(" + new DecimalFormat(".00").format(cakeBean.value / mSumValue * 100) + "%)", rigth + 10, mTextY + 30, mPaint);

        //改变高度
        mTextY += mRectHeight;
    }

    /**
     * 设置饼状图的数据
     *
     * @param beans
     */
    public void setData(List<CakeBean> beans) {
        if (beans == null || beans.size() <= 0) {
            return;
        }
        for (int i = 0; i < beans.size(); i++) {
            CakeBean cakeBean = beans.get(i);
            mSumValue += cakeBean.value;
        }

        for (int i = 0; i < beans.size(); i++) {
            CakeBean cakeBean = beans.get(i);
            cakeBean.degree = cakeBean.value / mSumValue * 360;
            mCakeBeanList.add(cakeBean);
        }
        invalidate();
    }

    /**
     * 设置起始角度
     *
     * @param startDegree
     */
    public void setStartDegree(float startDegree) {
        this.mRotateDegree = startDegree;
        invalidate();
    }
}
