package com.speex.papayaview.view.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Byron on 2018/10/6.
 */

public class PathUse2View extends View {
    private String TAG = this.getClass().getSimpleName();
    private int mWidth, mHeigth;
    private Paint mPaint;

    public PathUse2View(Context context) {
        super(context);
        init();
    }

    public PathUse2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathUse2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeigth = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //rLineTo使用
        rLineTo(canvas);

        //奇偶规则使用
        jiOuGuiZe(canvas);
    }

    /**
     * 奇偶规则使用
     *
     * @param canvas
     */
    private void jiOuGuiZe(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 2, mHeigth / 2);
        Path path = new Path();

//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
////        path.setFillType(Path.FillType.EVEN_ODD);//设置Path填充模式为 奇偶规则
//        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);//反奇偶规则
//
//        RectF rectF = new RectF(-200, -200, 200, 200);
//        path.addRect(rectF, Path.Direction.CW);
//        canvas.drawPath(path, mPaint);
//        canvas.restore();


        mPaint.setStyle(Paint.Style.FILL);
        // 添加小正方形 (通过这两行代码来控制小正方形边的方向,从而演示不同的效果)
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
//        path.addRect(-200, -200, 200, 200, Path.Direction.CCW);

        // 添加大正方形
        path.addRect(-400, -400, 400, 400, Path.Direction.CCW);

        path.setFillType(Path.FillType.WINDING);// 设置Path填充模式为非零环绕规则

        canvas.drawPath(path, mPaint);
        canvas.restore();
    }

    private void rLineTo(Canvas canvas) {
        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(100, 200);
        canvas.drawPath(path, mPaint);

        Path path2 = new Path();
        path2.moveTo(100, 100);
        path2.rLineTo(100, 200);
        canvas.drawPath(path2, mPaint);
    }
}
