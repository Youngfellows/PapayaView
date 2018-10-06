package com.speex.papayaview.view.path;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.speex.papayaview.view.enums.State;

/**
 * Created by Byron on 2018/10/6.
 */

public class SearchView extends View {
    private String TAG = this.getClass().getSimpleName();

    // 画笔
    private Paint mPaint;

    // View 宽高
    private int mViewWidth;
    private int mViewHeight;

    // 当前的状态(非常重要)
    private State mCurrentState = State.NONE;

    // 放大镜与外部圆环
    private Path path_srarch;
    private Path path_circle;

    // 测量Path 并截取部分的工具
    private PathMeasure mMeasure;

    // 默认的动效周期 2s
    private int defaultDuration = 2000;

    // 判断是否已经搜索结束
    private boolean isOver = false;

    private int count = 0;

    // 控制各个过程的动画
    private ValueAnimator mStartingAnimator;
    private ValueAnimator mSearchingAnimator;
    private ValueAnimator mEndingAnimator;

    // 动画数值(用于控制动画状态,因为同一时间内只允许有一种状态出现,具体数值处理取决于当前状态)
    private float mAnimatorValue = 0;

    // 动效过程监听器
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;

    // 用于控制动画状态转换
    private Handler mAnimatorHandler;

    public SearchView(Context context) {
        super(context);
        initAll();
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAll();
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAll();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSearch(canvas);
    }


    private void initAll() {
        initPaint();

        initPath();

        initListener();

        initHandler();

        initAnimator();

        /**
         * 进入开始动画
         */
        startAnimator();
    }

    /**
     * 进入开始动画
     */
    private void startAnimator() {
        mCurrentState = State.STARTING;
        mStartingAnimator.start();
    }

    private void initAnimator() {
        mStartingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(defaultDuration);
        mSearchingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(defaultDuration);
        mEndingAnimator = ValueAnimator.ofFloat(1, 0).setDuration(defaultDuration);

        mStartingAnimator.addUpdateListener(mUpdateListener);
        mSearchingAnimator.addUpdateListener(mUpdateListener);
        mEndingAnimator.addUpdateListener(mUpdateListener);

        mStartingAnimator.addListener(mAnimatorListener);
        mSearchingAnimator.addListener(mAnimatorListener);
        mEndingAnimator.addListener(mAnimatorListener);
    }

    private void initHandler() {
        mAnimatorHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (mCurrentState) {
                    case NONE:
                        //默认状态
                        break;
                    case STARTING:
                        // 从开始动画转换好搜索动画
                        Log.i(TAG, "从开始动画转换好搜索动画");
                        isOver = false;
                        mCurrentState = State.SEARCHING;//搜索
                        mStartingAnimator.removeAllListeners();
                        mSearchingAnimator.start();
                        break;
                    case SEARCHING:
                        //搜索
                        Log.i(TAG, "如果搜索已经结束 则进入结束动画 isOver = " + isOver);
                        //如果搜索未结束 则继续执行搜索动画
                        if (!isOver) {
                            //继续搜索
                            mSearchingAnimator.start();
                            count++;
                            if (count > 2) {
                                isOver = true;
                            }
                        } else {
                            // 如果搜索已经结束 则进入结束动画
                            mCurrentState = State.ENDING;
                            mEndingAnimator.start();
                        }
                        break;
                    case ENDING:
                        Log.i(TAG, "从结束动画转变为无状态: ");
                        //结束
                        // 从结束动画转变为无状态
                        mCurrentState = State.NONE;
                        break;
                }
            }
        };
    }

    private void initListener() {
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                Log.d(TAG, "当前状态: mCurrentState = " + mCurrentState);
                invalidate(); //刷新界面
            }
        };

        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //Handle发消息通知动画状态更新
                mAnimatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }

    private void initPath() {
        path_srarch = new Path();
        path_circle = new Path();

        mMeasure = new PathMeasure();

        // 注意,不要到360度,否则内部会自动优化,测量不能取到需要的数值
        RectF oval1 = new RectF(-50, -50, 50, 50);// 放大镜圆环
        path_srarch.addArc(oval1, 45, 359.9f);

        RectF oval2 = new RectF(-100, -100, 100, 100);//外部圆环(移动的圆环)
        path_circle.addArc(oval2, 45, -359.9f);

        float[] pos = new float[2];
        mMeasure.setPath(path_circle, false);  // 放大镜把手的位置
        mMeasure.getPosTan(0, pos, null);

        path_srarch.lineTo(pos[0], pos[1]); // 放大镜把手起点位置

        Log.i(TAG, "pos -->> pos[0] = " + pos[0] + " , pos[1] = " + pos[1]);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
    }

    private void drawSearch(Canvas canvas) {
        mPaint.setColor(Color.WHITE);

        canvas.translate(mViewWidth / 2, mViewHeight / 2);

        //绘制背景
        canvas.drawColor(Color.parseColor("#0082D7"));

        switch (mCurrentState) {
            case NONE:
                //默认状态
                canvas.drawPath(path_srarch, mPaint);//放大镜
//                canvas.drawPath(path_circle, mPaint);//外部圆环
                break;
            case STARTING:
                //开始
                mMeasure.setPath(path_srarch, false);
                Path dst = new Path();
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue, mMeasure.getLength(), dst, true);//获取路径
                canvas.drawPath(dst, mPaint);
                break;
            case SEARCHING:
                //搜索
                mMeasure.setPath(path_circle, false);
                Path dst2 = new Path();
                float stop = mMeasure.getLength() * mAnimatorValue;
                float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * 200f));
                Log.i(TAG, "stop: " + stop + " ,start: " + start);
                mMeasure.getSegment(start, stop, dst2, true);//获取路径
                canvas.drawPath(dst2, mPaint);
                break;
            case ENDING:
                //结束
                mMeasure.setPath(path_srarch, false);
                Path dst3 = new Path();
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue, mMeasure.getLength(), dst3, true);//获取路径
                canvas.drawPath(dst3, mPaint);
                break;
        }

    }
}
