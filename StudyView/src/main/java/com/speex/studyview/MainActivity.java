package com.speex.studyview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.speex.studyview.aty.CakeActivity;
import com.speex.studyview.aty.CircleImageActivity;
import com.speex.studyview.aty.DrawPanActivity;
import com.speex.studyview.aty.FiveRingsActivity;
import com.speex.studyview.aty.FlowLayoutActivity;
import com.speex.studyview.aty.HollowActivity;
import com.speex.studyview.aty.LoadActivity;
import com.speex.studyview.aty.MixImageActivity;
import com.speex.studyview.aty.PhotoDrawActivity;
import com.speex.studyview.aty.ProgressActivity;
import com.speex.studyview.aty.RandomNumberActivity;
import com.speex.studyview.aty.RectProgressActivity;
import com.speex.studyview.aty.RecyclerLoadActivity;
import com.speex.studyview.aty.ScrollerActivity;
import com.speex.studyview.aty.SettingActivity;
import com.speex.studyview.aty.SinActivity;
import com.speex.studyview.aty.TextImageActivity;
import com.speex.studyview.aty.ViewGroup1Activity;
import com.speex.studyview.aty.ViewGroup2Activity;
import com.speex.studyview.aty.ViewGruop3Activity;
import com.speex.studyview.aty.VolomControlActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 组合式控件1
     *
     * @param view
     */
    public void settingItemView(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    /**
     * 组合式控件2
     *
     * @param view
     */
    public void flowLayout(View view) {
        Intent intent = new Intent(this, FlowLayoutActivity.class);
        startActivity(intent);
    }

    /**
     * 组合式控件2
     *
     * @param view
     */
    public void rectProgress(View view) {
        Intent intent = new Intent(this, RectProgressActivity.class);
        startActivity(intent);
    }

    public void viewGroup1(View view) {
        Intent intent = new Intent(this, ViewGroup1Activity.class);
        startActivity(intent);
    }

    public void viewGroup2(View view) {
        Intent intent = new Intent(this, ViewGroup2Activity.class);
        startActivity(intent);
    }

    public void viewGroup3(View view) {
        Intent intent = new Intent(this, ViewGruop3Activity.class);
        startActivity(intent);
    }

    /**
     * 自定义属性
     * 点击刷新四位随机数
     *
     * @param view
     */
    public void randomNumber(View view) {
        Intent intent = new Intent(this, RandomNumberActivity.class);
        startActivity(intent);
    }

    /**
     * 带文字的图片
     *
     * @param view
     */
    public void textImage(View view) {
        Intent intent = new Intent(this, TextImageActivity.class);
        startActivity(intent);
    }

    /**
     * 环形进度
     *
     * @param view
     */
    public void circleProgress(View view) {
        Intent intent = new Intent(this, ProgressActivity.class);
        startActivity(intent);
    }

    /**
     * 声音控制
     *
     * @param view
     */
    public void volumeControl(View view) {
        Intent intent = new Intent(this, VolomControlActivity.class);
        startActivity(intent);
    }


    /**
     * 饼状图
     *
     * @param view
     */
    public void cakeView(View view) {
        Intent intent = new Intent(this, CakeActivity.class);
        startActivity(intent);
    }

    /**
     * 奥运五环
     *
     * @param view
     */
    public void fiveRings(View view) {
        Intent intent = new Intent(this, FiveRingsActivity.class);
        startActivity(intent);
    }

    /**
     * 滚动事件
     *
     * @param view
     */
    public void scrollEvent(View view) {
        Intent intent = new Intent(this, ScrollerActivity.class);
        startActivity(intent);
    }

    /**
     * 组合控件
     *
     * @param view
     */
    public void mixImageText(View view) {
        Intent intent = new Intent(this, MixImageActivity.class);
        startActivity(intent);
    }


    /**
     * 圆形图片
     *
     * @param view
     */
    public void circleImage(View view) {
        Intent intent = new Intent(this, CircleImageActivity.class);
        startActivity(intent);
    }


    /**
     * 下拉刷新，上滑加载
     *
     * @param view
     */
    public void pullToRefresh(View view) {
        Intent intent = new Intent(this, LoadActivity.class);
        startActivity(intent);
    }

    /**
     * RecyclerView下拉刷新，上滑加载
     *
     * @param view
     */
    public void pullToRefreshRV(View view) {
        Intent intent = new Intent(this, RecyclerLoadActivity.class);
        startActivity(intent);
    }

    /**
     * SurfaceView绘Sin曲线
     *
     * @param view
     */
    public void drawSin(View view) {
        Intent intent = new Intent(this, SinActivity.class);
        startActivity(intent);
    }

    /**
     * SurfaceView绘制绘图板
     *
     * @param view
     */
    public void drawPan(View view) {
        Intent intent = new Intent(this, DrawPanActivity.class);
        startActivity(intent);
    }

    /**
     * 绘制图片
     *
     * @param view
     */
    public void drawPhoto(View view) {
        Intent intent = new Intent(this, PhotoDrawActivity.class);
        startActivity(intent);
    }

    /**
     * 绘制镂空圆
     *
     * @param view
     */
    public void hollowCircle(View view) {
        Intent intent = new Intent(this, HollowActivity.class);
        startActivity(intent);
    }

}
