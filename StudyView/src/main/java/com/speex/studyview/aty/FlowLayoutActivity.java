package com.speex.studyview.aty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.speex.studyview.R;
import com.speex.studyview.view.FlowLayout;

import java.util.Random;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout mFlContainer;
    private String[] mStrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        initView();
    }

    private void initView() {
        mFlContainer = (FlowLayout) findViewById(R.id.fl_container);
        mFlContainer.setItemBg(R.drawable.shape_bg);
        mStrs = new String[]{"小南", "飞段", "迪特拉", "干柿鬼鲛", "绝", "角都", "蝎", "大蛇丸", "宇智波鼬", "佩恩", "宇智波带土", "宇智波斑"};
        mFlContainer.setData(mStrs);
    }

    /**
     * 添加一个子控件
     *
     * @param view
     */
    public void add(View view) {
        int i = new Random().nextInt(mStrs.length);
        mFlContainer.addItem(mStrs[i]);
    }

    /**
     * 删除一个子控件
     *
     * @param view
     */
    public void delete(View view) {
        mFlContainer.deleteItem();
    }
}
