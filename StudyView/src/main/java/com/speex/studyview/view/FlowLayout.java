package com.speex.studyview.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Byron on 2018/11/19.
 */

public class FlowLayout extends ViewGroup {
    private int LEFT_SPACING = 20;//左边间距
    private int TOP_SPACING = 20;//上边间距
    private Context mContext;
    private int mBgResId;
    private String[] mStrs;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量所有子控件,一定要先调用这个方法，否则child.geyMessureHeight/Width方法是不会返回正确结果
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //解决高度Wrap_content问题
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //记录计算后的真实高度
        int realHeight = 0;
        //临时变量记录排列的宽度
        int width = 0;
        //说明高度是wrap_content
        if (heightMode == MeasureSpec.AT_MOST) {
            for (int i = 0; i < getChildCount(); i++) {
                //取出第一个子控件的高度作为父控件高度的初始值
                if (i == 0) {
                    realHeight = getChildAt(i).getMeasuredHeight() + TOP_SPACING;
                }
                View child = getChildAt(i);
                //每次循环将width累加
                width += child.getMeasuredWidth();
                //如果累加结果大于父控件宽度，那么将父控件的高度增加一次
                if (width > this.getMeasuredWidth()) {
                    realHeight += child.getMeasuredHeight() + TOP_SPACING;
                    //父控件高度增加后，将临时变量width归零
                    width = 0;
                    i--;//下次循环开始前，要将指针前移1个，避免漏算当前子控件
                }
                //测量父控件的高度
                setMeasuredDimension(getMeasuredWidth(), realHeight);
            }
        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = LEFT_SPACING;//公用左边界临时变量
        //第一行的top是0
        int top = TOP_SPACING;
        //获取子控件的总数量
        int childCount = getChildCount();

        //循环布置子控件
        for (int i = 0; i < childCount; i++) {
            //获取子控件
            View child = getChildAt(i);
            //如果子控件累计的宽度已经大于父控件的宽度，那么就需要另起一行进行布置
            if (left + child.getMeasuredWidth() > this.getMeasuredWidth()) {
                left = LEFT_SPACING;
                top += child.getMeasuredHeight() + TOP_SPACING;
            }
            //布置子控件
            child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
            //每布置一个子控件则将left按照刚布置的控件的宽度增加
            left += child.getMeasuredWidth() + LEFT_SPACING;
        }
    }

    /**
     * 设置子控件的边框
     *
     * @param bgResId
     */
    public void setItemBg(int bgResId) {
        this.mBgResId = bgResId;
    }
    //添加控件

    public void setData(String[] strs) {
        this.mStrs = strs;

        for (int i = 0; i < mStrs.length; i++) {
            TextView view = new TextView(mContext);
            view.setText(mStrs[i]);//设置内容
            try {
                view.setBackgroundResource(mBgResId);
            } catch (Exception e) {
                e.printStackTrace();
                view.setBackgroundColor(Color.WHITE);
            }
            view.setPadding(5, 5, 5, 5);
            //添加到父容器
            this.addView(view);
        }
        invalidate();
    }

    /**
     * 添加一个子控件
     *
     * @param s
     */
    public void addItem(String s) {
        TextView view = new TextView(mContext);
        view.setText(s);
        try {
            view.setBackgroundResource(mBgResId);
        } catch (Exception e) {
            e.printStackTrace();
            view.setBackgroundColor(Color.WHITE);
        }
        view.setPadding(5, 5, 5, 5);
        this.addView(view);
        invalidate();
    }

    /**
     * 删除一个子控件
     */
    public void deleteItem() {
        this.removeView(this.getChildAt(this.getChildCount() - 1));
    }

}
