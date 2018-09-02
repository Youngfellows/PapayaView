package com.speex.papayaview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.speex.papayaview.R;
import com.speex.papayaview.view.path.DynamicPathView;

public class DynamicPatActivity extends AppCompatActivity {

    private DynamicPathView mDynamicPathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_pat);
        mDynamicPathView = (DynamicPathView) findViewById(R.id.dpv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDynamicPathView.remove();
    }
}
