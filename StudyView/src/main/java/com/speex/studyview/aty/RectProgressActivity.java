package com.speex.studyview.aty;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.speex.studyview.R;
import com.speex.studyview.view.RectProgressBar;

import java.util.Timer;

public class RectProgressActivity extends AppCompatActivity {

    private RectProgressBar progressBar;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rect_progress);

        progressBar = (RectProgressBar) findViewById(R.id.rpb_pb);
        progressBar.setBgColor(Color.BLACK);
        progressBar.setUpColor(Color.GREEN);
        progressBar.setPro_width(20);
        progressBar.setTextSize(20);
        progressBar.setTxtColor(Color.RED);
    }

    /**
     * 开始动画
     *
     * @param view
     */
    public void startAnim(View view) {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int x = (int) animation.getAnimatedValue();
                progressBar.setPro_num(x);
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000);
        animator.start();
    }
}
