package com.speex.studyview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Byron on 2018/12/2.
 */

public class ViewGroupContainer extends ViewGroup {
    private String TAG = this.getClass().getSimpleName();

    public ViewGroupContainer(Context context) {
        super(context);
    }

    public ViewGroupContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public MarginLayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure");
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        Log.i(TAG, "widthMode: " + widthMode + " ,heightMode: " + heightMode);
        Log.i(TAG, "sizeWidth: " + sizeWidth + "px ,sizeHeight: " + sizeHeight + "px");

        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int width = 0;
        int height = 0;

        //子控件的个数
        int cCount = getChildCount();
        int cWidth = 0;//每个子控件的宽度
        int cHeight = 0;//每个子控件的高度
        MarginLayoutParams cParams = null;

        // 用于计算左边两个childView的高度
        int lHeight = 0;
        // 用于计算右边两个childView的高度，最终高度取二者之间大值
        int rHeight = 0;

        // 用于计算上边两个childView的宽度
        int tWidth = 0;
        // 用于计算下面两个childiew的宽度，最终宽度取二者之间大值
        int bWidth = 0;

        /**
         * 根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content时
         */
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            Log.i(TAG, i + " ,cWiddth: " + cWidth + " ,cHeight: " + cHeight);
            Log.i(TAG, i + " ,cParams.leftMargin: " + cParams.leftMargin + " ,cParams.rightMargin: " + cParams.rightMargin);

            // 上面两个childView
            if (i == 0 || i == 1) {
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            //下面两个的宽度
            if (i == 2 || i == 3) {
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            //左边两个的高度
            if (i == 0 || i == 2) {
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

            //右边两个的高度
            if (i == 1 || i == 3) {
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

            Log.i(TAG, i + " ,tWidth: " + tWidth);
            Log.i(TAG, i + " ,bWidth: " + bWidth);
            Log.i(TAG, i + " ,lHeight: " + lHeight);
            Log.i(TAG, i + " ,rHeight: " + rHeight);
        }

        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);


        /**
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接设置为父容器计算的值
         */
        if (MeasureSpec.EXACTLY == widthMode && MeasureSpec.EXACTLY == heightMode) {
            setMeasuredDimension(sizeWidth, sizeHeight);
        } else {
            setMeasuredDimension(width, height);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout");
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;

        MarginLayoutParams cParams = null;

        /**
         * 遍历所有childView根据其宽和高，以及margin进行布局
         */
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            //每个子控件的左上、右下坐标
            int cl = 0, ct = 0, cr = 0, cb = 0;
            switch (i) {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    Log.i(TAG, i + " cl: " + cl + " ,ct: " + ct);
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = cParams.topMargin;
                    Log.i(TAG, i + "getWidth(): " + getWidth() + " ,cParams.leftMargin: " + cParams.leftMargin + " ,cParams.rightMargin:" + cParams.rightMargin);
                    Log.i(TAG, i + " cl: " + cl + " ,ct: " + ct);
                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    Log.i(TAG, i + "getHeight(): " + getHeight() + " ,cParams.bottomMargin: " + cParams.bottomMargin);
                    Log.i(TAG, i + " cl: " + cl + " ,ct: " + ct);
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    Log.i(TAG, i + "getWidth(): " + getWidth() + " ,cParams.leftMargin: " + cParams.leftMargin + " ,cParams.rightMargin:" + cParams.rightMargin);
                    Log.i(TAG, i + "getHeight(): " + getHeight() + " ,cParams.bottomMargin: " + cParams.bottomMargin);
                    Log.i(TAG, i + " cl: " + cl + " ,ct: " + ct);
                    break;
            }

            cr = cl + cWidth;
            cb = cHeight + ct;
            childView.layout(cl, ct, cr, cb);
        }
    }
}
