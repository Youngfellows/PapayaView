package com.speex.studyview.aty;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.speex.studyview.R;
import com.speex.studyview.bean.CakeBean;
import com.speex.studyview.view.CakeView;

import java.util.ArrayList;
import java.util.List;

public class CakeActivity extends AppCompatActivity {
    private List<CakeBean> mCakeBeanList;
    private String[] names = {"php", "object-c", "c", "c++", "java", "android", "linux"};
    private float[] values = {2f, 2f, 3f, 4f, 5f, 6f, 7f};
    private int[] colArrs = {Color.RED, Color.parseColor("#4ebcd3"), Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.parseColor("#f68b2b"), Color.parseColor("#6fb30d")};//圆弧颜色


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake);

        initData();
        initView();
    }

    private void initData() {
        mCakeBeanList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            CakeBean bean = new CakeBean();
            bean.name = names[i];
            bean.value = values[i];
            bean.color = colArrs[i];
            mCakeBeanList.add(bean);
        }

    }


    private void initView() {
        CakeView cakeView = (CakeView) findViewById(R.id.cv);
        cakeView.setData(mCakeBeanList);

    }
}
