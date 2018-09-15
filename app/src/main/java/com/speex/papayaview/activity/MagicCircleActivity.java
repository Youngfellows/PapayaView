package com.speex.papayaview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.speex.papayaview.R;
import com.speex.papayaview.view.bezier.LoveCircleView;

public class MagicCircleActivity extends AppCompatActivity {

    private LoveCircleView mMagicCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_circle);
        mMagicCircleView = (LoveCircleView) findViewById(R.id.magic_view);

    }

    /**
     * 开始动画
     * @param view
     */
    public void start(View view) {
        mMagicCircleView.startAnimation();
    }
}
