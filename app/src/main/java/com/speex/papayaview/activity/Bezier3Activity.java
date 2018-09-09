package com.speex.papayaview.activity;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.speex.papayaview.R;
import com.speex.papayaview.view.bezier.ThirdOrderBezierView;

public class Bezier3Activity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private RadioGroup mRadioGroup;
    private ThirdOrderBezierView mBezierView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier3);

        mBezierView = (ThirdOrderBezierView) findViewById(R.id.bezier_view);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mBezierView.setMode(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Log.i(TAG, "id1 = " + R.id.radio_btn1);
                Log.i(TAG, "id2 = " + R.id.radio_btn2);
                Log.i(TAG, "checkedId = " + checkedId);
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                Log.i(TAG, radioButton.getText() + "");
                if (checkedId == R.id.radio_btn1) {
                    mBezierView.setMode(true);
                } else {
                    mBezierView.setMode(false);
                }
            }
        });
    }
}
