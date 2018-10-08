package com.speex.papayaview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.speex.papayaview.R;
import com.speex.papayaview.view.gesture.RegionClickView;

public class Gesture1Activity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private RegionClickView mRegionClickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture1);
        mRegionClickView = (RegionClickView) findViewById(R.id.region_view);
        mRegionClickView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                float x = event.getX();
                float y = event.getY();
//        Log.i(TAG, "坐标:(" + x + "," + y + ")");
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        Log.w(TAG, "onTouch ACTION_DOWN 按下 坐标:(" + x + "," + y + ")");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.w(TAG, "onTouch ACTION_MOVE 移动 坐标:(" + x + "," + y + ")");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.w(TAG, "onTouch ACTION_UP 松开 坐标:(" + x + "," + y + ")");
                        break;
                }
                /**
                 * 只要你给View注册了 onClickListener、onLongClickListener、OnContextClickListener
                 * 其中的任何一个监听器
                 * 或者设置了 android:clickable=”true” 就代表这个 View 是可点击的
                 */
//                return true;//消费掉按键事件,不执行View的onTouchEvent
                return false;//不消费掉按键事件(View必须是可点击状态),执行View的onTouchEvent,然后才会执行onClick方法(看源码)
            }
        });

        //设置view是可点击的
        mRegionClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick 点击了View");
            }
        });
    }
}
