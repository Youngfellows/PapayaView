package com.speex.studyview.aty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.speex.studyview.R;

public class ScrollerActivity extends AppCompatActivity {

    private Button mScroollBy;
    private Button mScroollTo;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl_layout);
        mScroollTo = (Button) findViewById(R.id.btn_scroll_to);
        mScroollBy = (Button) findViewById(R.id.btn_scroll_by);
        mScroollTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelativeLayout.scrollTo(-60, -100);
            }
        });
        mScroollBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelativeLayout.scrollBy(-60, -100);
            }
        });
    }
}
