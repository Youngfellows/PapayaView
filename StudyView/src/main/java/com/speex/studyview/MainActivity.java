package com.speex.studyview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.speex.studyview.aty.FlowLayoutActivity;
import com.speex.studyview.aty.RectProgressActivity;
import com.speex.studyview.aty.SettingActivity;
import com.speex.studyview.aty.ViewGroup1Activity;
import com.speex.studyview.aty.ViewGroup2Activity;
import com.speex.studyview.aty.ViewGruop3Activity;

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

    /**
     * 组合式控件2
     *
     * @param view
     */
    public void flowLayout(View view) {
        Intent intent = new Intent(this, FlowLayoutActivity.class);
        startActivity(intent);
    }

    /**
     * 组合式控件2
     *
     * @param view
     */
    public void rectProgress(View view) {
        Intent intent = new Intent(this, RectProgressActivity.class);
        startActivity(intent);
    }

    public void viewGroup1(View view) {
        Intent intent = new Intent(this, ViewGroup1Activity.class);
        startActivity(intent);
    }

    public void viewGroup2(View view) {
        Intent intent = new Intent(this, ViewGroup2Activity.class);
        startActivity(intent);
    }

    public void viewGroup3(View view) {
        Intent intent = new Intent(this, ViewGruop3Activity.class);
        startActivity(intent);
    }


}
