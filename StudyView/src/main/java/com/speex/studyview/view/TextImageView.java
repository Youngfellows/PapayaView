package com.speex.studyview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import com.speex.studyview.R;

/**
 * Created by Byron on 2018/12/2.
 */

public class TextImageView extends BaseView {

    private String mTextTitle;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private int mImageScale;
    private Bitmap mImageBitmap;
    private Paint mPaint;
    private Rect mRect;
    private Rect mTextBound;
    private int mWidth;//控件的宽
    private int mHeight;//控件的高

    public TextImageView(Context context) {
        this(context, null);
    }

    public TextImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, R.styleable.TextImageView);//获取设置的属性
    }

    /**
     * 设置属性值
     *
     * @param set
     * @param defStyleAttr
     * @param attrs
     */
    @Override
    public void init(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.init(set, defStyleAttr, attrs);

        //初始化
        mPaint = new Paint();
        mRect = new Rect(); //外边框
        mTextBound = new Rect();  //文字边框

        // 计算了描绘字体需要的范围
        mPaint.setTextSize(mTitleTextSize);
        mPaint.getTextBounds(mTextTitle, 0, mTextTitle.length(), mTextBound);
    }

    /**
     * 获取设置的属性
     *
     * @param set
     * @param defStyleAttr
     * @param attrs
     */
    @Override
    public void getAttrs(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.getAttrs(set, defStyleAttr, attrs);
        //文本
        mTextTitle = mTypedArray.getString(R.styleable.TextImageView_titleText);
        //字体颜色
        mTitleTextColor = mTypedArray.getColor(R.styleable.TextImageView_titleTextColor, Color.CYAN);
        //字体大小
        mTitleTextSize = mTypedArray.getDimensionPixelSize(R.styleable.TextImageView_titleTextSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        //图片填充模式
        mImageScale = mTypedArray.getInt(R.styleable.TextImageView_imageScalType, 0);
        int imageResourceId = mTypedArray.getResourceId(R.styleable.TextImageView_imageSrc, 0);
        //图片
        mImageBitmap = BitmapFactory.decodeResource(getResources(), imageResourceId);

        Log.i(TAG, "mTextTitle: " + mTextTitle + " ,mTitleTextColor: " + mTitleTextColor + " ,mTitleTextSize: " + mTitleTextSize + " ,mImageScale: " + mImageScale);
        mTypedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取宽高的测量模式及宽高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "widthSize: " + widthSize + " ,heightSize: " + heightSize);
        //设置宽度
        //宽度设置为 match_parent , 或者具体值
        if (MeasureSpec.EXACTLY == widthMode) {
            mWidth = widthSize;
        } else {
            // 由图片决定的宽
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int imageWidth = mImageBitmap.getWidth();
            int desireByImgWidth = paddingLeft + paddingRight + imageWidth;
            Log.d(TAG, "desireByImgWidth: " + desireByImgWidth + " ,paddingLeft: " + paddingLeft + " ,paddingRight: " + paddingRight + " ,imageWidth: " + imageWidth);

            // 由字体决定的宽
            int textWidth = mTextBound.width();
            Log.d(TAG, "textWidth: " + textWidth);
            int desireByTextWidth = paddingLeft + paddingRight + textWidth;
            Log.d(TAG, "desireByTextWidth: " + desireByTextWidth);

            //控件的宽是wrap_content
            if (MeasureSpec.AT_MOST == widthMode) {
                int desireWidth = Math.max(desireByImgWidth, desireByTextWidth);
                Log.i(TAG, "desireWidth: " + desireWidth);
                mWidth = Math.min(desireWidth, widthSize);//控件宽
            }
        }

        //设置高度
        //高度设置为 match_parent , 或者具体值
        if (MeasureSpec.EXACTLY == heightMode) {
            mHeight = heightSize;
        } else {
            // 由图片决定的宽
            int paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            int imageHeigth = mImageBitmap.getHeight();
            int desireByImgHeigth = paddingTop + paddingBottom + imageHeigth;
            Log.d(TAG, "desireByImgHeigth: " + desireByImgHeigth + " ,paddingTop: " + paddingTop + " ,paddingBottom: " + paddingBottom + " ,imageHeigth: " + imageHeigth);

            // 由字体决定的宽
            int textHeigth = mTextBound.height();
            Log.d(TAG, "textHeigth: " + textHeigth);
            int desireHeigth = paddingTop + paddingBottom + textHeigth + imageHeigth;
            Log.d(TAG, "desireHeigth: " + desireHeigth);

            //控件的宽是wrap_content
            if (MeasureSpec.AT_MOST == heightMode) {
                mHeight = Math.min(heightSize, desireHeigth);//控件宽
                Log.i(TAG, "AT_MOST mHeight: " + mHeight);
            }
        }

        Log.i(TAG, "mWidth: " + mWidth + " ,mHeight: " + mHeight);
        //设置宽高
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        //边框
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        Log.d(TAG, "ondraw: measuredWidth: " + measuredWidth + " ,measureHeight: " + measuredHeight);
        canvas.drawRect(0, 0, measuredWidth, measuredHeight, mPaint);

        //绘制图片和文字
        mRect.left = getPaddingLeft();
        mRect.top = getPaddingTop();
        mRect.right = mWidth - getPaddingRight();
        mRect.bottom = mHeight - getPaddingBottom();

        Log.i(TAG, "mWidth: " + mWidth + " ,mHeight: " + mHeight + " ,getPaddingLeft(): " + getPaddingLeft() + " ,getPaddingTop(): " + getPaddingTop() + " ,getPaddingRight(): " + getPaddingRight() + " ,getPaddingBottom(): " + getPaddingBottom());

        mPaint.setColor(mTitleTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        if (mTextBound.width() > mWidth) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils
                    .ellipsize(mTextTitle, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(), TextUtils.TruncateAt.END)
                    .toString();
            Log.d(TAG, "msg: " + msg);
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        } else {
            //正常情况，将字体居中
            canvas.drawText(mTextTitle, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }

        //图片的底部
        mRect.bottom -= mTextBound.height();

        if (mImageScale == 0) {
            canvas.drawBitmap(mImageBitmap, null, mRect, mPaint);//在指定位置绘制图片
        } else {
            //计算居中的矩形范围
            mRect.left = mWidth / 2 - mImageBitmap.getWidth() / 2;
            mRect.right = mWidth / 2 + mImageBitmap.getWidth() / 2;
            mRect.top = (mHeight - mTextBound.height()) / 2 - mImageBitmap.getHeight() / 2;
            mRect.bottom = (mHeight - mTextBound.height()) / 2 + mImageBitmap.getHeight() / 2;
            canvas.drawBitmap(mImageBitmap, null, mRect, mPaint);
        }

    }
}
