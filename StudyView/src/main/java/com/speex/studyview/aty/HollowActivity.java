package com.speex.studyview.aty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.speex.studyview.R;
import com.speex.studyview.view.hollow.HollowView;

public class HollowActivity extends AppCompatActivity {

    private HollowView mHollowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hollow);

        mHollowView = (HollowView) findViewById(R.id.hollow);
    }

    /**
     * 进入
     *
     * @param view
     */
    public void enter(View view) {
        mHollowView.setEnterState(true);
    }

    /**
     * 离开
     *
     * @param view
     */
    public void exit(View view) {
        mHollowView.setEnterState(false);
    }
}
