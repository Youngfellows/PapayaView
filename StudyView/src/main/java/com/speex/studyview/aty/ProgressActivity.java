package com.speex.studyview.aty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.speex.studyview.R;
import com.speex.studyview.view.ProgressBarView;

public class ProgressActivity extends AppCompatActivity {

    private ProgressBarView mProgressBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        mProgressBarView = (ProgressBarView) findViewById(R.id.pbv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressBarView.stopThread(true);
    }
}
