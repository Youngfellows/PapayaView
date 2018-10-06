package com.speex.papayaview.view.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Byron on 2018/10/6.
 */

public class PathUse3View extends View {
    private String TAG = this.getClass().getSimpleName();
    private int mWidth, mHeigth;
    private Paint mPaint;

    public PathUse3View(Context context) {
        super(context);
        init();
    }

    public PathUse3View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathUse3View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(30);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeigth = h;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //阴阳鱼
        fish(canvas);

        //布尔运算测试
        testOp(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void testOp(Canvas canvas) {
        canvas.save();
        int x = 80;
        int r = 100;

        canvas.translate(mWidth / 2, 0);

        Path path1 = new Path();
        Path path2 = new Path();
        Path pathOpResult = new Path();

        path1.addCircle(-x, 0, r, Path.Direction.CW);
        path2.addCircle(x, 0, r, Path.Direction.CW);

        pathOpResult.op(path1, path2, Path.Op.DIFFERENCE);//差集(Path1中减去Path2后剩下的部分)
        canvas.translate(0, 200);
        canvas.drawText("差集", 240, 0, mPaint);
        canvas.drawPath(pathOpResult, mPaint);

        pathOpResult.op(path1, path2, Path.Op.REVERSE_DIFFERENCE);//差集(Path2中减去Path1后剩下的部分)
        canvas.translate(0, 300);
        canvas.drawText("差集2", 240, 0, mPaint);
        canvas.drawPath(pathOpResult, mPaint);

        pathOpResult.op(path1, path2, Path.Op.INTERSECT);//交集
        canvas.translate(0, 300);
        canvas.drawText("交集", 240, 0, mPaint);
        canvas.drawPath(pathOpResult, mPaint);

        pathOpResult.op(path1, path2, Path.Op.UNION);//并集
        canvas.translate(0, 300);
        canvas.drawText("并集", 240, 0, mPaint);
        canvas.drawPath(pathOpResult, mPaint);

        pathOpResult.op(path1, path2, Path.Op.XOR);//异或
        canvas.translate(0, 300);
        canvas.drawText("异或", 240, 0, mPaint);
        canvas.drawPath(pathOpResult, mPaint);
        canvas.restore();
    }

    /**
     * 阴阳鱼
     *
     * @param canvas
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void fish(Canvas canvas) {
        canvas.save();
        canvas.translate(200, 400);

        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();
        Path path4 = new Path();

        path1.addCircle(0, 0, 200, Path.Direction.CW);
        path2.addRect(0, -200, 200, 200, Path.Direction.CW);
        path3.addCircle(0, -100, 100, Path.Direction.CW);
        path4.addCircle(0, 100, 100, Path.Direction.CCW);


        path1.op(path2, Path.Op.DIFFERENCE);//（差集）Path1中减去Path2后剩下的部分
        path1.op(path3, Path.Op.UNION);//并集--包含Path1，包含Path3
        path1.op(path4, Path.Op.DIFFERENCE);//（差集）Path1中减去Path4后剩下的部分
        canvas.drawPath(path1, mPaint);
        canvas.restore();
    }
}
