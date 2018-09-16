package com.speex.papayaview.view.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.speex.papayaview.view.bezier.bean.HPoint;
import com.speex.papayaview.view.bezier.bean.VPoint;

/**
 * Created by Byron on 2018/9/15.
 * 三次贝塞尔曲线之弹性的圆
 */

public class MagicCircleView extends View {
    private String TAG = this.getClass().getSimpleName();
    private static final float C = 0.551915024494f;// 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置
    private Paint mPaint;//绘制贝塞尔圆的画笔
    private int mCenterX, mCenterY;//中心点
    private PointF mCenter;//中心点
    private float mCircleRadius;//圆的半径
    private float mDifference;//圆形的控制点与数据点的差值
    private float mStretchDistance;//X方向拉长距离
    private float mExtendVerticalDistance;//Y方向伸缩距离
    private float mMaxLength;//最大平移距离
    private float cDistance;
    private float mInterpolatedTime;//时间变化值

    private float mDuration = 1000f;//动画时间
    private float mCurrent = 0;//当前已进行时长
    private int mCount = 100;//将时长总共划分多少份
    private float mPiece = mDuration / mCount;//每一份的时长

    //四个数据点
    private HPoint p1, p3;//Y轴控制点
    private VPoint p2, p4;//X轴控制点
    private Path mPath;//路径

    private boolean isStartAnimation = false;//动画是否执行

    public MagicCircleView(Context context) {
        super(context);
        init();
    }

    public MagicCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MagicCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        int height = getHeight();
        mCenterX = width / 2;
        mCenterY = height / 2;

        Log.i(TAG, "onLayout:（" + mCenterX + "," + mCenterY + ")");
        //初始化贝塞尔圆相关
        mCircleRadius = Math.min(width, height) / 18;//圆的半径
        mDifference = C * mCircleRadius;//圆形的控制点与数圆的半径据点的差值

//        mode10(); //初始化数据点和控制点坐标
        mStretchDistance = mCircleRadius;
        mExtendVerticalDistance = mCircleRadius * (3 / 5f);
        mMaxLength = width - 2 * mCircleRadius;
        cDistance = mDifference * 0.45f;
    }

    private void init() {
        //绘制贝塞尔圆画笔
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);

        //初始化路径
        mPath = new Path();

        //初始化数据点和控制点
        p1 = new HPoint();
        p3 = new HPoint();

        p2 = new VPoint();
        p4 = new VPoint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: hhhhhhhhhhhhhhhhhhhhh");
        mPath.reset();//重置路径

        drawCoordinateSystem(canvas);       //绘制坐标系

        if (mInterpolatedTime >= 0 && mInterpolatedTime <= 0.2) {
            model1(mInterpolatedTime); //模式1
        } else if (mInterpolatedTime > 0.2 && mInterpolatedTime <= 0.5) {
            model2(mInterpolatedTime);//模式2
        } else if (mInterpolatedTime > 0.5 && mInterpolatedTime <= 0.8) {
            model3(mInterpolatedTime);//模式3
        } else if (mInterpolatedTime > 0.8 && mInterpolatedTime <= 0.9) {
            model4(mInterpolatedTime);//模式4
        } else if (mInterpolatedTime > 0.9 && mInterpolatedTime <= 1) {
            model5(mInterpolatedTime);//模式5
        }

        float offset = mMaxLength * (mInterpolatedTime - 0.2f);
        offset = offset > 0 ? offset : 0;
        p1.adjustAllX(offset);
        p2.adjustAllX(offset);
        p3.adjustAllX(offset);
        p4.adjustAllX(offset);

        drawAuxiliaryLine(canvas);//绘制辅助线和控制点

        drawBezierMagicCircle(canvas);//绘制贝塞尔弹性圆

//        changeControlPointPosition();//改变控制点的坐标,绘制动态的心
    }

    private void model5(float time) {
        model4(0.9f);
        time = time - 0.9f;
        p4.adjustAllX((float) (Math.sin(Math.PI * time * 10f) * (2 / 10f * mCircleRadius)));
    }

    private void model4(float time) {
        model3(0.8f);
        time = (time - 0.8f) * 10;
        p4.adjustAllX(mStretchDistance / 2 * time);
    }

    private void model3(float time) {
        model2(0.5f);
        time = (time - 0.5f) * (10f / 3);
        p1.adjustAllX(mStretchDistance / 2 * time);
        p3.adjustAllX(mStretchDistance / 2 * time);
        p2.adjustY(-cDistance * time);
        p4.adjustY(-cDistance * time);

        p4.adjustAllX(mStretchDistance / 2 * time);
    }

    private void model2(float time) {
        model1(0.2f);
        time = (time - 0.2f) * (10f / 3);
        p1.adjustAllX(mStretchDistance / 2 * time);
        p3.adjustAllX(mStretchDistance / 2 * time);
        p2.adjustY(cDistance * time);
        p4.adjustY(cDistance * time);
    }

    /**
     * 模式1是一个圆
     */
    private void model1(float time) {
        model0(); //初始化数据点和控制点坐标
        p2.setX(mCircleRadius + mStretchDistance * time * 5);
    }

    /**
     * 1.改变控制点的坐标
     * 2.绘制动态的心
     */
    private void changeControlPointPosition() {
        mCurrent += mPiece;
        if (mCurrent < mDuration) {
            p1.y -= 120 / mCount;  //改变p1控制点的y坐标

            //第2、3象限控制点变化
            p3.left.y += 80 / mCount;
            p3.right.y += 80 / mCount;

            p2.bottom.x -= 20 / mCount;
            p4.bottom.x += 20 / mCount;

            postInvalidateDelayed((long) mPiece);
        }
    }

    /**
     * 绘制贝塞尔弹性圆
     *
     * @param canvas
     */
    private void drawBezierMagicCircle(Canvas canvas) {
        canvas.save();//保存画布
//        canvas.translate(mCenterX, mCenterY);//平移画布到中央
        canvas.translate(mCircleRadius, mCenterY);//平移画布到中央
        canvas.scale(1, -1);//缩放画布,Y轴向上
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);

        //绘制贝塞尔弹性圆
        mPath.moveTo(p1.x, p1.y);
        mPath.cubicTo(p1.right.x, p1.right.y, p2.top.x, p2.top.y, p2.x, p2.y); //绘制第一象限曲线
        mPath.cubicTo(p2.bottom.x, p2.bottom.y, p3.right.x, p3.right.y, p3.x, p3.y); //绘制第二象限曲线
        mPath.cubicTo(p3.left.x, p3.left.y, p4.bottom.x, p4.bottom.y, p4.x, p4.y);  //绘制第三象限曲线
        mPath.cubicTo(p4.top.x, p4.top.y, p1.left.x, p1.left.y, p1.x, p1.y);   //绘制第四象限曲线

        canvas.drawPath(mPath, mPaint);
        canvas.restore();//恢复画布
    }

    /**
     * 绘制辅助线和控制点
     *
     * @param canvas
     */
    private void drawAuxiliaryLine(Canvas canvas) {
        canvas.save();//保存画布
//        canvas.translate(mCenterX, mCenterY);//平移画布到中央
        canvas.translate(mCircleRadius, mCenterY);//平移画布到中央
        canvas.scale(1, -1);//缩放画布,Y轴向上
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.GRAY);

        //绘制数据点
        canvas.drawPoint(p1.x, p1.y, mPaint);
        canvas.drawPoint(p3.x, p3.y, mPaint);
        canvas.drawPoint(p2.x, p2.y, mPaint);
        canvas.drawPoint(p4.x, p4.y, mPaint);

        //绘制控制点
        canvas.drawPoint(p1.left.x, p1.left.y, mPaint);
        canvas.drawPoint(p3.left.x, p3.left.y, mPaint);
        canvas.drawPoint(p1.right.x, p1.right.y, mPaint);
        canvas.drawPoint(p3.right.x, p3.right.y, mPaint);

        canvas.drawPoint(p2.top.x, p2.top.y, mPaint);
        canvas.drawPoint(p2.bottom.x, p2.bottom.y, mPaint);
        canvas.drawPoint(p4.top.x, p4.top.y, mPaint);
        canvas.drawPoint(p4.bottom.x, p4.bottom.y, mPaint);

        //绘制控制线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(p1.left.x, p1.left.y, p1.x, p1.y, mPaint);
        canvas.drawLine(p1.x, p1.y, p1.right.x, p1.right.y, mPaint);

        canvas.drawLine(p2.top.x, p2.top.y, p2.x, p2.y, mPaint);
        canvas.drawLine(p2.x, p2.y, p2.bottom.x, p2.bottom.y, mPaint);

        canvas.drawLine(p3.left.x, p3.left.y, p3.x, p3.y, mPaint);
        canvas.drawLine(p3.x, p3.y, p3.right.x, p3.right.y, mPaint);

        canvas.drawLine(p4.top.x, p4.top.y, p4.x, p4.y, mPaint);
        canvas.drawLine(p4.x, p4.y, p4.bottom.x, p4.bottom.y, mPaint);

        canvas.restore();//恢复画布
    }

    /**
     * 模式0是一个圆
     */
    private void model0() {
        //设置Y轴数据点及旁边的控制点
        Log.i(TAG, "mode10: mCircleRadius = " + mCircleRadius);
        p1.setY(mCircleRadius);
        p3.setY(-mCircleRadius);
        p1.x = 0;
        p3.x = 0;

        //p1 p3的控制点
        p1.left.x = -mDifference;
        p3.left.x = -mDifference;
        p1.right.x = mDifference;
        p3.right.x = mDifference;

        //设置X轴数据点及旁边的控制点
        p2.setX(mCircleRadius);
        p4.setX(-mCircleRadius);
        p2.y = 0;
        p4.y = 0;

        //p2 p4的控制点
        p2.top.y = mDifference;
        p4.top.y = mDifference;
        p2.bottom.y = -mDifference;
        p4.bottom.y = -mDifference;
    }

    /**
     * 绘制坐标系
     *
     * @param canvas
     */
    private void drawCoordinateSystem(Canvas canvas) {
        canvas.save();//保存画布
//        canvas.translate(mCenterX, mCenterY);//平移画布到中央
        canvas.translate(mCircleRadius, mCenterY);//平移画布到中央
        canvas.scale(1, -1);//缩放画布,Y轴向上
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        canvas.drawPoint(0, 0, mPaint);
        int length = Math.max(mCenterX, mCenterY) * 2;
        canvas.drawLine(0, -length, 0, length, mPaint);//Y轴
        canvas.drawLine(-length, 0, length, 0, mPaint);//X轴
        canvas.restore();//恢复画布
    }

    /**
     * 开始执行动画
     */
    public void startAnimation() {
        Log.d(TAG, "startAnimation: xxxxxxxxxx isStartAnimation = " + isStartAnimation);
        if (!isStartAnimation) {
            mPath.reset();
            mInterpolatedTime = 0;
            MoveAnimation animation = new MoveAnimation();
            animation.setDuration((long) mDuration);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Log.d(TAG, "onAnimationStart: ");
                    isStartAnimation = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.d(TAG, "onAnimationEnd: ");
                    isStartAnimation = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            startAnimation(animation);
        } else {
            Log.e(TAG, "startAnimation 正在执行动画");
        }
    }

    /**
     * 平移动画
     */
    private class MoveAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            Log.d(TAG, "interpolatedTime: " + interpolatedTime);
            mInterpolatedTime = interpolatedTime;
            invalidate();
        }
    }

}
