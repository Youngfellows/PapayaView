package com.speex.studyview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.speex.studyview.aty.SettingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 组合式控件1
     *
     * @param view
     */
    public void settingItemView(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
