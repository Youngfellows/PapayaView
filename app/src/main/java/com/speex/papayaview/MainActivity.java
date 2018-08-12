package com.speex.papayaview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.speex.papayaview.activity.CanvasTestActivity;
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
}
