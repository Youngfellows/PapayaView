package com.speex.papayaview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.speex.papayaview.activity.AlarmActivity;
import com.speex.papayaview.activity.AlarmClockDialActivity;
import com.speex.papayaview.activity.CanvasControlActivity;
import com.speex.papayaview.activity.CanvasSaveActivity;
import com.speex.papayaview.activity.CanvasTestActivity;
import com.speex.papayaview.activity.CheckActivity;
import com.speex.papayaview.activity.ClockActivity;
import com.speex.papayaview.activity.DrawTextActivity;
import com.speex.papayaview.activity.DrawTextOnPathActivity;
import com.speex.papayaview.activity.DynamicPatActivity;
import com.speex.papayaview.activity.PathViewActivity;
import com.speex.papayaview.activity.PictureActivity;
import com.speex.papayaview.activity.PieViewActivity;
import com.speex.papayaview.activity.ScaleRulerActivity;
import com.speex.papayaview.utils.ScreenSizeUtil;

public class MainActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
