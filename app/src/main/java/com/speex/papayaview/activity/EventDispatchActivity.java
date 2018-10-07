package com.speex.papayaview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.speex.papayaview.R;
import com.speex.papayaview.view.event.EventButton;

public class EventDispatchActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private EventButton mEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dispatch);
        initDispatchEvent();
    }

    private void initDispatchEvent() {
        mEventButton = (EventButton) findViewById(R.id.btn_event);
        mEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: xxxxxxxxxxx");
            }
        });

        mEventButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i(TAG, "onLongClick: yyyyyyyyyyyy");
                return false;
            }
        });
        mEventButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.w(TAG, "action: " + event.getAction());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.w(TAG, "onTouch ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.w(TAG, "onTouch ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.w(TAG, "onTouch ACTION_UP");
                        break;
                    default:
                        break;
                }
//                return true;//自己消费Touch事件,不执行View的onTouchEvent
                return false;//自己不消费Touch事件,执行View的onTouchEvent
            }
        });
    }
}
