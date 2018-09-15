package com.speex.papayaview.view.bezier.bean;

import android.graphics.PointF;

/**
 * Created by Byron on 2018/9/15.
 * Y轴数据点
 * p1,p3
 */

public class HPoint {
    public float x;//Y轴数据点x坐标
    public float y;//Y轴数据点y坐标

    public PointF left = new PointF();//Y轴数据点左边控制点
    public PointF right = new PointF();//Y轴数据点右边控制点

    public void setY(float y) {
        this.y = y;
        left.y = y;//左边控制点y坐标
        right.y = y;//右边控制点y坐标
    }

    /**
     * Y轴上数据点和控制点整体向右移动
     *
     * @param offset
     */
    public void adjustAllX(float offset) {
        this.x += offset;//数据点移动
        left.x += offset;//左边控制点向右边移动,y轴不变
        right.x += offset;//右边控制点右边移动,y轴不变
    }
}
