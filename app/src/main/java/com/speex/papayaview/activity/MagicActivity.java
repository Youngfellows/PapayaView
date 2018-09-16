package com.speex.papayaview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.speex.papayaview.R;
import com.speex.papayaview.view.bezier.MagicCircleView;

public class MagicActivity extends AppCompatActivity {

    private MagicCircleView mMagicCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic);
        mMagicCircleView = (MagicCircleView) findViewById(R.id.magic_view);
    }

    public void start(View view) {
        mMagicCircleView.startAnimation();
    }
}
