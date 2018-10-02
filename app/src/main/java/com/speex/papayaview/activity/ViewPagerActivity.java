package com.speex.papayaview.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.speex.papayaview.R;
import com.speex.papayaview.adapter.AiPagerAdapter;
import com.speex.papayaview.bean.CardItem;
import com.speex.papayaview.transformer.ScaleTransformer;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        initView();
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setPageMargin(50);
        mViewPager.setOffscreenPageLimit(3);
        AiPagerAdapter aiPagerAdapter = new AiPagerAdapter(this);
        aiPagerAdapter.addCardItem(new CardItem(R.mipmap.img1));
        aiPagerAdapter.addCardItem(new CardItem(R.mipmap.img2));
        aiPagerAdapter.addCardItem(new CardItem(R.mipmap.img3));
        aiPagerAdapter.addCardItem(new CardItem(R.mipmap.img4));

        mViewPager.setPageTransformer(false, new ScaleTransformer(mViewPager, aiPagerAdapter));
        mViewPager.setAdapter(aiPagerAdapter);
    }
}
