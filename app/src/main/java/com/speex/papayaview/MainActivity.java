package com.speex.papayaview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.speex.papayaview.activity.AlarmActivity;
import com.speex.papayaview.activity.AlarmClockDialActivity;
import com.speex.papayaview.activity.Bezier2Activity;
import com.speex.papayaview.activity.Bezier3Activity;
import com.speex.papayaview.activity.CanvasControlActivity;
import com.speex.papayaview.activity.CanvasSaveActivity;
import com.speex.papayaview.activity.CanvasTestActivity;
import com.speex.papayaview.activity.CheckActivity;
import com.speex.papayaview.activity.ClockActivity;
import com.speex.papayaview.activity.CoordinateSpaceActivity;
import com.speex.papayaview.activity.DragBallActivity;
import com.speex.papayaview.activity.DragViewActivity;
import com.speex.papayaview.activity.DrawTextActivity;
import com.speex.papayaview.activity.DrawTextOnPathActivity;
import com.speex.papayaview.activity.DynamicPatActivity;
import com.speex.papayaview.activity.EventDispatchActivity;
import com.speex.papayaview.activity.Gesture1Activity;
import com.speex.papayaview.activity.LoveCircleActivity;
import com.speex.papayaview.activity.MagicActivity;
import com.speex.papayaview.activity.MagicCircleActivity;
import com.speex.papayaview.activity.MultiTouchActivity;
import com.speex.papayaview.activity.PathMeasureActivity;
import com.speex.papayaview.activity.PathUseActivity;
import com.speex.papayaview.activity.PathViewActivity;
import com.speex.papayaview.activity.PhotoScaleActivity;
import com.speex.papayaview.activity.PictureActivity;
import com.speex.papayaview.activity.PieViewActivity;
import com.speex.papayaview.activity.RecyclerActivity;
import com.speex.papayaview.activity.RemoteControlActivity;
import com.speex.papayaview.activity.ScaleRulerActivity;
import com.speex.papayaview.activity.SearchViewActivity;
import com.speex.papayaview.activity.UsePath2Activity;
import com.speex.papayaview.activity.UsePath3Activity;
import com.speex.papayaview.activity.ViewPagerActivity;
import com.speex.papayaview.activity.WaterRippleActivity;
import com.speex.papayaview.utils.ScreenSizeUtil;
import com.speex.papayaview.view.event.EventButton;

public class MainActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private EventButton mEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initDispatchEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ScreenSizeUtil.getWidthHeigth1(this, new ScreenSizeUtil.ScreenSizeCallback() {
            @Override
            public void onSize(int width, int heigth) {
                Log.i(TAG, "onSize: 1 width = " + width + " ,heigth = " + heigth);
            }
        });

        ScreenSizeUtil.getWidthHeigth2(this, new ScreenSizeUtil.ScreenSizeCallback() {
            @Override
            public void onSize(int width, int heigth) {
                Log.i(TAG, "onSize: 2 width = " + width + " ,heigth = " + heigth);
            }
        });

        ScreenSizeUtil.getWidthHeigth3(this, new ScreenSizeUtil.ScreenSizeCallback() {
            @Override
            public void onSize(int width, int heigth) {
                Log.i(TAG, "onSize: 3 width = " + width + " ,heigth = " + heigth);
            }
        });

        ScreenSizeUtil.getWidthHeigth4(this, new ScreenSizeUtil.ScreenSizeCallback() {
            @Override
            public void onSize(int width, int heigth) {
                Log.i(TAG, "onSize: 4 width = " + width + " ,heigth = " + heigth);
            }
        });
    }

    /**
     * Canvas画布使用
     *
     * @param view
     */
    public void useCanvas(View view) {
        Intent intent = new Intent(this, CanvasTestActivity.class);
        startActivity(intent);
    }

    /**
     * Canvas画布使用二(时钟)
     *
     * @param view
     */
    public void useCanvas2(View view) {
        Intent intent = new Intent(this, ClockActivity.class);
        startActivity(intent);
    }

    /**
     * 饼状图
     *
     * @param view
     */
    public void pieViewShow(View view) {
        Intent intent = new Intent();
        intent.setClass(this, PieViewActivity.class);
        startActivity(intent);
    }

    /**
     * 画布的基本操作
     * 平移、缩放、旋转、错切
     *
     * @param view
     */
    public void controlCanvas(View view) {
        Intent intent = new Intent();
        intent.setClass(this, CanvasControlActivity.class);
        startActivity(intent);
    }

    /**
     * 画布保存状态与恢复
     *
     * @param view
     */
    public void canvasSave(View view) {
        Intent intent = new Intent(this, CanvasSaveActivity.class);
        startActivity(intent);
    }

    /**
     * 闹钟表盘
     *
     * @param view
     */
    public void alarmClockDial(View view) {
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
    }

    /**
     * 绘制刻度尺
     *
     * @param view
     */
    public void scaleRuler(View view) {
        Intent intent = new Intent(this, ScaleRulerActivity.class);
        startActivity(intent);
    }

    /**
     * 闹钟表盘
     *
     * @param view
     */
    public void clockScale(View view) {
        Intent intent = new Intent(this, AlarmClockDialActivity.class);
        startActivity(intent);
    }

    /**
     * 打钩动画
     *
     * @param view
     */
    public void checkView(View view) {
        Intent intent = new Intent();
        intent.setClass(this, CheckActivity.class);
        startActivity(intent);
    }

    /**
     * Picture录屏
     *
     * @param view
     */
    public void picture(View view) {
        Intent intent = new Intent(this, PictureActivity.class);
        startActivity(intent);
    }

    /**
     * 绘制文本
     *
     * @param view
     */
    public void drawText(View view) {
        Intent intent = new Intent();
        intent.setClass(this, DrawTextActivity.class);
        startActivity(intent);
    }

    /**
     * 绘制路径文本
     *
     * @param view
     */
    public void drawTextOnPath(View view) {
        Intent intent = new Intent();
        intent.setClass(this, DrawTextOnPathActivity.class);
        startActivity(intent);
    }

    /**
     * Path的基本使用
     *
     * @param view
     */
    public void pathUse(View view) {
        Intent intent = new Intent();
        intent.setClass(this, PathViewActivity.class);
        startActivity(intent);
    }

    /**
     * 动态贝塞尔曲线
     *
     * @param view
     */
    public void dynamicPath(View view) {
        Intent intent = new Intent();
        intent.setClass(this, DynamicPatActivity.class);
        startActivity(intent);
    }

    /**
     * @param view
     */
    public void pathUse2(View view) {
        Intent intent = new Intent();
        intent.setClass(this, PathUseActivity.class);
        startActivity(intent);
    }

    /**
     * 二阶贝塞尔曲线
     *
     * @param view
     */
    public void bezier2(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Bezier2Activity.class);
        startActivity(intent);
    }

    /**
     * 三阶贝塞尔曲线
     *
     * @param view
     */
    public void bezier3(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Bezier3Activity.class);
        startActivity(intent);
    }

    /**
     * 三阶贝塞尔爱心圆
     *
     * @param view
     */
    public void loveCircle(View view) {
        Intent intent = new Intent();
        intent.setClass(this, LoveCircleActivity.class);
        startActivity(intent);
    }


    /**
     * 贝塞尔弹性的圆形
     *
     * @param view
     */
    public void magicCircle(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MagicCircleActivity.class);
        startActivity(intent);
    }

    /**
     * 贝塞尔弹性的圆形
     *
     * @param view
     */
    public void magicCircle2(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MagicActivity.class);
        startActivity(intent);
    }

    /**
     * 水波纹
     *
     * @param view
     */
    public void waterRipple(View view) {
        Intent intent = new Intent(this, WaterRippleActivity.class);
        startActivity(intent);
    }

    /**
     * Recycler列表
     *
     * @param view
     */
    public void useRecycler(View view) {
        Intent intent = new Intent(this, RecyclerActivity.class);
        startActivity(intent);
    }

    /**
     * ViewPager轮播图
     *
     * @param view
     */
    public void useViewPager(View view) {
        Intent intent = new Intent(this, ViewPagerActivity.class);
        startActivity(intent);
    }

    /**
     * Path使用二
     *
     * @param view
     */
    public void usePath2(View view) {
        Intent intent = new Intent(this, UsePath2Activity.class);
        startActivity(intent);
    }

    public void usePath3(View view) {
        Intent intent = new Intent(this, UsePath3Activity.class);
        startActivity(intent);
    }

    /**
     * path测量使用
     *
     * @param view
     */
    public void pathMeasure(View view) {
        Intent intent = new Intent(this, PathMeasureActivity.class);
        startActivity(intent);
    }

    /**
     * 放大镜
     *
     * @param view
     */
    public void searchView(View view) {
        Intent intent = new Intent(this, SearchViewActivity.class);
        startActivity(intent);
    }

    /**
     * 事件分发
     *
     * @param view
     */
    public void eventDispatch(View view) {
        Intent intent = new Intent(this, EventDispatchActivity.class);
        startActivity(intent);
    }

    /**
     * 手势是否在圆形区域
     *
     * @param view
     */
    public void gestureBall(View view) {
        Intent intent = new Intent(this, Gesture1Activity.class);
        startActivity(intent);
    }

    /**
     * 手势触摸的坐标系与画布坐标系不统一
     *
     * @param view
     */
    public void coordinateSpace(View view) {
        Intent intent = new Intent(this, CoordinateSpaceActivity.class);
        startActivity(intent);
    }

    /**
     * 遥控器按钮
     *
     * @param view
     */
    public void remoteControl(View view) {
        Intent intent = new Intent(this, RemoteControlActivity.class);
        startActivity(intent);
    }

    /**
     * 多点触控
     *
     * @param view
     */
    public void multiTouch(View view) {
        Intent intent = new Intent(this, MultiTouchActivity.class);
        startActivity(intent);
    }

    /**
     * 多指拖动图片
     *
     * @param view
     */
    public void dragView(View view) {
        Intent intent = new Intent(this, DragViewActivity.class);
        startActivity(intent);
    }

    /**
     * 拖动的小球
     *
     * @param view
     */
    public void dragBall(View view) {
        Intent intent = new Intent(this, DragBallActivity.class);
        startActivity(intent);
    }

    /**
     * 图片缩放
     *
     * @param view
     */
    public void scalePhoto(View view) {
        Intent intent = new Intent(this, PhotoScaleActivity.class);
        startActivity(intent);
    }

}
