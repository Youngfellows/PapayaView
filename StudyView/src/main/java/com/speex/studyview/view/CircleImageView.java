package com.speex.studyview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.speex.studyview.R;
import com.speex.studyview.utils.StringUtils;

import java.io.InputStream;

/**
 * Created by Byron on 2018/12/23.
 * 圆角图片
 */

public class CircleImageView extends BaseView {

    private Bitmap mBitmapSrc;
    private int mBorderRadius;
    private int mType;
    private static int TYPE_CIRCLE = 0;//圆形
    private static int TYPE_ROUND = 1;//圆角

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, R.styleable.CircleImageView);
    }

    @Override
    public void init(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.init(set, defStyleAttr, attrs);
    }

    @Override
    public void getAttrs(AttributeSet set, int defStyleAttr, int[] attrs) {
        super.getAttrs(set, defStyleAttr, attrs);
        int resourceId = mTypedArray.getResourceId(R.styleable.CircleImageView_src, 0);
        //图片资源
        mBitmapSrc = BitmapFactory.decodeResource(getResources(), resourceId);
        //圆角半径
        mBorderRadius = mTypedArray.getDimensionPixelSize(R.styleable.CircleImageView_border_radius, StringUtils.getDip(mContext, 5));
        //类型--圆角or矩形
        mType = mTypedArray.getInt(R.styleable.CircleImageView_type, 0);
        mTypedArray.recycle();
        Log.i(TAG, "mBorderRadius: " + mBorderRadius + " ,mType: " + mType);
    }

    /**
     * 设置控件的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;//宽
        int height = 0;//高
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                //宽度设置为match_parent,或者设置成具体值
                width = wSize;
                break;
            case MeasureSpec.AT_MOST:
                //宽度设置为match_parent,或者设置成具体值
                //由图片决定的宽
                int paddingLeft = getPaddingLeft();
                int paddingRight = getPaddingRight();
                int srcWidth = mBitmapSrc.getWidth();
                Log.i(TAG, "paddingLeft: " + paddingLeft + " ,paddingRight: " + paddingRight + " ,srcWidth: " + srcWidth);
                width = paddingLeft + paddingRight + srcWidth;
                break;
            default:
                width = wSize;
                break;
        }

        switch (hMode) {
            case MeasureSpec.EXACTLY:
                //高度设置为match_parent,或者设置成具体值
                height = hSize;
                break;
            case MeasureSpec.AT_MOST:
                //高度设置为match_parent,或者设置成具体值
                //由图片决定的高
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                int srcHeight = mBitmapSrc.getHeight();
                Log.i(TAG, "paddingTop: " + paddingTop + " ,paddingBottom: " + paddingBottom + " ,srcHeight: " + srcHeight);
                height = paddingTop + paddingBottom + srcHeight;
                break;
            default:
                height = hSize;
                break;
        }

        //设置宽高
        Log.i(TAG, "width: " + width + " ,height: " + height);
        setMeasuredDimension(width, height);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        if (mType == TYPE_CIRCLE) {
            //绘制圆形图片
            int minSize = Math.min(width, height);
            Log.i(TAG, "minSize: " + minSize);
            //长度如果不一致，按小的值进行压缩
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(mBitmapSrc, minSize, minSize, false);
            createCircleBitmap(scaledBitmap, minSize);
//            canvas.drawBitmap(createCircleBitmap(canvas, scaledBitmap, minSize), 0, 0, null);
        } else if (mType == TYPE_ROUND) {
            //绘制圆角图片
        }
    }

    /**
     * 获取圆形图片
     *
     * @param source
     * @param min
     * @return
     */
    private Bitmap createCircleBitmap(Bitmap source, int min) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        //产生一个同样大小的画布
        Canvas canvas = new Canvas(target);
        //首先绘制圆形
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);

        //使用SRC_IN，参考上面的说明
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //绘制图片
        canvas.drawBitmap(target, 0, 0, paint);
        return source;
    }

    public static Bitmap readBitmapFromResource(Context context, int resId) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inPreferredConfig = Bitmap.Config.ARGB_8888;
        op.inDither = false;
        op.inScaled = false;

        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, op);
    }

}
