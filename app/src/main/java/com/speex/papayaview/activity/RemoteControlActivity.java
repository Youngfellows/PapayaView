package com.speex.papayaview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.speex.papayaview.R;
import com.speex.papayaview.view.gesture.RemoteControlMenu;

public class RemoteControlActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        RemoteControlMenu rvControl = (RemoteControlMenu) findViewById(R.id.rv_control);
        rvControl.setListener(new RemoteControlMenu.MenuListener() {
            @Override
            public void onCenterCliched() {
                Log.i(TAG, "确认");
                Toast.makeText(RemoteControlActivity.this, "确认", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpCliched() {
                Log.i(TAG, "向上");
                Toast.makeText(RemoteControlActivity.this, "向上", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCliched() {
                Log.i(TAG, "右边");
                Toast.makeText(RemoteControlActivity.this, "右边", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownCliched() {
                Log.i(TAG, "向下");
                Toast.makeText(RemoteControlActivity.this, "向下", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLeftCliched() {
                Log.i(TAG, "左边");
                Toast.makeText(RemoteControlActivity.this, "左边", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
