package com.speex.papayaview.view.path;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.speex.papayaview.R;

/**
 * Created by Byron on 2018/10/6.
 */

public class PathMeasureView extends View {
    private String TAG = this.getClass().getSimpleName();
    private int mWidth, mHeigth;
    private Paint mPaint;

    private float mCurrentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度
    private float[] mPos;                // 当前点的实际位置
    private float[] mTan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作

    public PathMeasureView(Context context) {
        super(context);
        init();
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);

        //初始化箭头相关
        initArrow();
    }

    /**
     * 初始化箭头相关
     */
    private void initArrow() {
        mPos = new float[2];
        mTan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;//缩放图片
        mBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.arrow, options);
        mMatrix = new Matrix();
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
        //测量Path1
        measure1(canvas);

        //截取路径
        getSegment(canvas);

        //下一条曲线
        nextContour(canvas);

        //移动的箭头
        roteArrow(canvas);
    }

    /**
     * 移动的箭头
     *
     * @param canvas
     */
    private void roteArrow(Canvas canvas) {
        canvas.save();
        canvas.translate(250 + mWidth / 2, 600);

        Path path = new Path();
        path.addCircle(0, 0, 200, Path.Direction.CW);

        PathMeasure measure = new PathMeasure(path, false);//创建 PathMeasure

        mCurrentValue += 0.005;//计算当前的位置在总长度上的比例[0,1]
        if (mCurrentValue >= 1) {
            mCurrentValue = 0;
        }

        // 获取当前位置的坐标以及趋势
        measure.getPosTan(measure.getLength() * mCurrentValue, mPos, mTan);

        mMatrix.reset();//重置Matrix
        float degrees = (float) (Math.atan2(mTan[1], mTan[0]) * 180.0 / Math.PI);//计算图片旋转角度
        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2); // 旋转图片
        mMatrix.postTranslate(mPos[0] - mBitmap.getWidth() / 2, mPos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

        canvas.drawPath(path, mPaint);//绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);//绘制箭头
        canvas.restore();
    }

    private void nextContour(Canvas canvas) {
        canvas.save();
        canvas.translate(300, 600);

        Path path = new Path();
        path.addRect(-100, -100, 100, 100, Path.Direction.CW);
        canvas.drawPath(path, mPaint);

        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
        canvas.drawPath(path, mPaint);

        PathMeasure measure = new PathMeasure(path, false);// 将Path与PathMeasure关联
        float length1 = measure.getLength();//获得第一条路径的长度
        measure.nextContour(); //跳转到下一条路径

        float length2 = measure.getLength();//得第二条路径的长度
        Log.i(TAG, "length1: " + length1);
        Log.i(TAG, "length2: " + length2);
        canvas.restore();

        invalidate();//重绘页面
    }

    /**
     * 截取路径
     *
     * @param canvas
     */
    private void getSegment(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 2, 300);

        Path path = new Path();
        RectF rectF = new RectF(-200, -200, 200, 200);
        path.addRect(rectF, Path.Direction.CW);

        Path dst = new Path();//将 Path 与 PathMeasure 关联
        dst.lineTo(-300, -300);// 在 dst 中添加一条线段

        PathMeasure measure = new PathMeasure(path, false);//将 Path 与 PathMeasure 关联
//        measure.getSegment(200, 600, dst, true);//截取路径
        measure.getSegment(200, 600, dst, false);//截取路径,不使用 startMoveTo, 保持 dst 的连续性

        canvas.drawPath(dst, mPaint);
        canvas.restore();
    }

    private void measure1(Canvas canvas) {
        canvas.save();
        canvas.translate(100, 100);

        Path path = new Path();
        path.lineTo(0, 200);
        path.lineTo(200, 200);
        path.lineTo(200, 0);

        PathMeasure measure1 = new PathMeasure(path, false);//不闭合
        PathMeasure measure2 = new PathMeasure(path, true);//闭合

        Log.i(TAG, "measure1: length = " + measure1.getLength());
        Log.i(TAG, "measure2: length = " + measure2.getLength());

        canvas.drawPath(path, mPaint);
        canvas.restore();
    }
}
