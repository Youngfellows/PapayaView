package com.speex.studyview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Byron on 2018/11/21.
 */

public class RectProgressBar extends View {
    private String TAG = this.getClass().getSimpleName();
    private Context mContext;

    //可配置项
    private int bgColor = Color.GRAY;
    private int upColor = Color.RED;
    private int txtColor = Color.GREEN;
    private int pro_width = 8;
    private int txtSize = 15;
    private int pro_num = 0;


    private Paint bgPaint;
    private Paint upPaint;
    private Paint txtPaint;
    private int height;
    private int width;
    private Rect rect;
    private float top;
    private float bottom;
    private int baseLineY = -1;

    public RectProgressBar(Context context) {
        this(context, null);
    }

    public RectProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RectProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(bgColor);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth((float) (dip2px(mContext, pro_width * 2)));
//        bgPaint.setStrokeWidth(1);

        upPaint = new Paint();
        upPaint.setAntiAlias(true);
        upPaint.setColor(upColor);
        upPaint.setStyle(Paint.Style.FILL);

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setColor(txtColor);
        txtPaint.setTextSize((float) (dip2px(mContext, txtSize)));
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        txtPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = txtPaint.getFontMetrics();
        //为基线到字体上边框的距离
        top = fontMetrics.top;
        //为基线到字体下边框的距离
        bottom = fontMetrics.bottom;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        //计算文字所处的
        Log.i(TAG, "width: " + width + "px ,height: " + height + "px");
        Log.i(TAG, "width: " + px2dip(mContext, width) + "dp ,height: " + px2dip(mContext, height) + "dp");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rect == null) {
            rect = new Rect(0, 0, width, height);
        }

        if (baseLineY < 0) {
            baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        }
        Log.i(TAG, "baseLineY = " + baseLineY + " ,rect.centerY() = " + rect.centerY() + " ,top = " + top + " ,bottom = " + bottom);

        //画背景条
        canvas.drawRect(rect, bgPaint);
        //画文字
        canvas.drawText(pro_num + "%", rect.centerX(), baseLineY, txtPaint);
        canvas.drawCircle(rect.centerX(), baseLineY, 10, upPaint);
        //画进度
        if (pro_num >= 0 && pro_num <= 25) {//0-25进度，只画上边
            drawTop(canvas, pro_num);
        } else if (pro_num > 25 && pro_num <= 50) {//25-50,画上边和右边
            drawTop(canvas, 25);
            drawRight(canvas, pro_num);
        } else if (pro_num > 50 && pro_num <= 75) {//50-75,除了左边都要画
            drawTop(canvas, 25);
            drawRight(canvas, 50);
            drawBottom(canvas, pro_num);
        } else if (pro_num > 70 && pro_num <= 100) {//75-100，四条边都要画
            drawTop(canvas, 25);
            drawRight(canvas, 50);
            drawBottom(canvas, 75);
            drawLeft(canvas, pro_num);
        } else {
            throw new RuntimeException("the rate must between 0 and 100");
        }
    }

    /**
     * 设置背景框颜色
     *
     * @param color
     */
    public void setBgColor(int color) {
        bgColor = color;
        bgPaint.setColor(bgColor);
        invalidate();
    }

    /**
     * 设置进度条颜色
     *
     * @param color
     */
    public void setUpColor(int color) {
        upColor = color;
        upPaint.setColor(upColor);
        invalidate();
    }

    /**
     * 设置文本颜色
     *
     * @param color
     */
    public void setTxtColor(int color) {
        txtColor = color;
        txtPaint.setColor(txtColor);
        invalidate();
    }

    /**
     * 设置进度条宽度
     *
     * @param widthDp dp单位
     */
    public void setPro_width(int widthDp) {
        pro_width = widthDp;
        bgPaint.setStrokeWidth((float) (dip2px(mContext, pro_width * 2)));
//        bgPaint.setStrokeWidth(1);
        invalidate();
    }

    /**
     * 设置文本字体大小
     *
     * @param sizeSp sp单位
     */
    public void setTextSize(int sizeSp) {
        txtSize = sizeSp;
        txtPaint.setTextSize((float) (dip2px(mContext, txtSize)));
        invalidate();
    }

    /**
     * 设置进度
     *
     * @param pro_num
     */
    public void setPro_num(int pro_num) {
        this.pro_num = pro_num;
        invalidate();
    }

    private void drawLeft(Canvas canvas, float pro_num) {
        canvas.drawRect(
                0
                , (1 - (pro_num - 75) * 1f / 25) * (height - (float) (dip2px(mContext, pro_width)))
                , (float) (dip2px(mContext, pro_width))
                , height - (float) (dip2px(mContext, pro_width))
                , upPaint);
    }

    private void drawBottom(Canvas canvas, float pro_num) {
        canvas.drawRect(
                (1 - ((pro_num - 50) * 1f / 25)) * (width - (float) (dip2px(mContext, pro_width)))
                , height - (float) (dip2px(mContext, pro_width))
                , width - (float) (dip2px(mContext, pro_width))
                , height
                , upPaint);
    }

    private void drawRight(Canvas canvas, float pro_num) {
        canvas.drawRect(
                width - (float) (dip2px(mContext, pro_width))
                , (float) (dip2px(mContext, pro_width))
                , width
                , (pro_num - 25) * 1f / 25 * (height - (float) (dip2px(mContext, pro_width))) + (float) (dip2px(mContext, pro_width))
                , upPaint);
    }

    private void drawTop(Canvas canvas, float pro_num) {
        canvas.drawRect((float) (dip2px(mContext, pro_width))
                , 0
                , (pro_num * 1f / 25 * (width - (float) (dip2px(mContext, pro_width)))) + (float) (dip2px(mContext, pro_width))
                , (float) (dip2px(mContext, pro_width))
                , upPaint);
    }

    public double dip2px(Context context, double dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return dpValue * (double) density + 0.5D;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
//    public static int dip2px(Context context, float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
