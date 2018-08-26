package com.speex.papayaview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.speex.papayaview.R;
import com.speex.papayaview.view.checkview.CheckPapayaView;

public class CheckActivity extends AppCompatActivity {

    private CheckPapayaView mCheckView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        mCheckView = (CheckPapayaView) findViewById(R.id.cv_check);
    }

    /**
     * 选择OK
     *
     * @param view
     */
    public void checkOk(View view) {
        mCheckView.check();
    }

    /**
     * 取消选择
     *
     * @param view
     */
    public void uncheck(View view) {
        mCheckView.unCheck();
    }
}
