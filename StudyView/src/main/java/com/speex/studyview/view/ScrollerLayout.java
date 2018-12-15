package com.speex.studyview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Byron on 2018/12/15.
 */

public class ScrollerLayout extends ViewGroup {
    private String TAG = this.getClass().getSimpleName();

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    /**
     * 手机按下时的屏幕坐标
     */
    private float mXDown;

    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float mXLastMove;

    /**
     * 界面可滚动的左边界
     */
    private int leftBorder;

    /**
     * 界面可滚动的右边界
     */
    private int rightBorder;

    public ScrollerLayout(Context context) {
        this(context, null);
    }

    public ScrollerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScroller(context);//初始化滚动事件
    }

    private void initScroller(Context context) {
        mScroller = new Scroller(context); // 第一步，创建Scroller的实例
        // 获取TouchSlop值
        ViewConfiguration configuration = ViewConfiguration.get(context);
//        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mTouchSlop = configuration.getScaledPagingTouchSlop();
        Log.d(TAG, "mTouchSlop: " + mTouchSlop);
    }

    /**
     * 测量，设置宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = 0;
        int height = 0;
        //子控件数量
        int childCount = getChildCount();
        Log.d(TAG, "wSize: " + wSize + " ,hSize: " + hSize + " ,childCount: " + childCount);

        switch (wMode) {
            case MeasureSpec.EXACTLY:
                //宽度设置为match_parent或者具体值
                width = wSize;
                break;
            case MeasureSpec.AT_MOST:
                //宽度设置为wrap_content或者具体值
                //获取子控件的宽度，设置最大的为ViewGroup的宽度
                for (int i = 0; i < childCount; i++) {
                    View childView = getChildAt(i);
                    int childWidth = childView.getMeasuredWidth();
                    int childHeight = childView.getMeasuredHeight();
                    Log.d(TAG, i + " ,childWidth: " + childWidth + " ,childHeight: " + childHeight);
                    height = Math.max(width, childHeight);
                }
                break;
            default:
                width = wSize;
                break;
        }

        switch (hMode) {
            case MeasureSpec.EXACTLY:
                //高度设置为match_parent或者具体值
                height = hSize;
                break;
            case MeasureSpec.AT_MOST:
                //高度设置为wrap_content或者具体值
                //获取子控件的高度，设置最大的为ViewGroup的高度
                for (int i = 0; i < childCount; i++) {
                    View childView = getChildAt(i);
                    int childWidth = childView.getMeasuredWidth();
                    int childHeight = childView.getMeasuredHeight();
                    Log.d(TAG, i + " ,childWidth: " + childWidth + " ,childHeight: " + childHeight);
                    height = Math.max(height, childHeight);
                }
                break;
            default:
                height = hSize;
                break;
        }
        //设置控件的宽高
        Log.d(TAG, "width: " + width + " ,height: " + height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                int childWidth = childView.getMeasuredWidth();//子控件宽
                int childHeight = childView.getMeasuredHeight();//子控件高
                Log.d(TAG, "onLayout " + i + " ,childWidth: " + childWidth + " ,childHeight: " + childHeight);
                //设置子控件的位置
                // 为ScrollerLayout中的每一个子控件在水平方向上进行布局
                childView.layout(i * childWidth, 0, (i + 1) * childWidth, childHeight);
            }

            // 初始化左右边界值
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(childCount - 1).getRight();
            Log.d(TAG, "leftBorder: " + leftBorder + " ,rightBorder: " + rightBorder);
        }
    }

    /**
     * 拦截事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onInterceptTouchEvent  down ");
                mXDown = event.getRawX();//按下位置
                mXLastMove = mXDown;//上次移动位置i
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onInterceptTouchEvent  move ");
                mXMove = event.getRawX();//移动X位置
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;//上次移动位置
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onInterceptTouchEvent  up ");

                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    /**
     * 消费事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int width = getWidth();
        int scrollX;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent  down ");
//                mXDown = event.getRawX();//按下位置
//                mXLastMove = mXDown;//上次移动位置
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrolledX = (int) (mXLastMove - mXMove);
                scrollX = getScrollX();
                Log.d(TAG, "onTouchEvent  move : scrolledX = " + scrolledX + " ,scrollX = " + scrollX + " ,width = " + width + " ,leftBorder = " + leftBorder + " ,rightBorder = " + rightBorder);
                if (scrollX + scrolledX < leftBorder) {
                    scrollTo(leftBorder, 0);//第一个不滑动
                    return true;
                } else if (scrolledX + scrollX + width > rightBorder) {
                    scrollTo(rightBorder - width, 0);//最后一个不滑动
                    return true;
                }
                scrollBy(scrolledX, 0);//滑动
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent  up ");
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                scrollX = getScrollX();
                int targetIndex = (scrollX + width / 2) / width;
                int dx = targetIndex * width - scrollX;
                Log.d(TAG, "targetIndex = " + targetIndex + " ,dx = " + dx);
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(scrollX, 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
