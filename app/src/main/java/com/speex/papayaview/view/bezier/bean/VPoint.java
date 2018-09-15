package com.speex.papayaview.view.bezier.bean;

import android.graphics.PointF;

/**
 * Created by Byron on 2018/9/15.
 * X轴控制点
 */

public class VPoint {
    public float x;//X轴数据点x坐标
    public float y;//X轴数据点y坐标

    public PointF top = new PointF();//X轴数据点上边控制点
    public PointF bottom = new PointF();//X轴数据点下边控制点

    public void setX(float x) {
        this.x = x;
        top.x = x;
        bottom.x = x;
    }

    /**
     * 压缩X轴上下两个控制点的y轴坐标
     *
     * @param offset
     */
    public void adjustY(float offset) {
        top.y -= offset;
        bottom.y += offset;
    }

    /**
     * 向右边平移
     *
     * @param offset
     */
    public void adjustAllX(float offset) {
        this.x += offset;
        top.x += offset;
        bottom.x += offset;
    }
}
