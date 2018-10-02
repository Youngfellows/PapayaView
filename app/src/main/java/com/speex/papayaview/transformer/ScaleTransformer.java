package com.speex.papayaview.transformer;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.speex.papayaview.adapter.AiPagerAdapter;
import com.speex.papayaview.adapter.CardAdapter;

/**
 * Created by Byron on 2018/10/2.
 */

public class ScaleTransformer implements ViewPager.PageTransformer, ViewPager.OnPageChangeListener {
    private String TAG = this.getClass().getSimpleName();
    private ViewPager mViewPager;
    private AiPagerAdapter mAdapter;
    private float mLastOffset;
    private boolean mScalingEnabled = true;

    public ScaleTransformer(ViewPager viewPager, AiPagerAdapter adapter) {
        mViewPager = viewPager;
        mAdapter = adapter;
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void transformPage(View page, float position) {
//        Log.i(TAG, "transformPage: position = " + position + " ,page = " + page.toString());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled: position = " + position + " ,positionOffset = " + positionOffset + " ,positionOffsetPixels = " + positionOffsetPixels);
        boolean isGoingLeft = mLastOffset > positionOffset;
        int realCurrentPosition;
        int nextPosition;
        float realOffset;
        float baseElevation = mAdapter.getBaseElevation();

        if (isGoingLeft) {
            Log.d(TAG, "左移 ");
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        } else {
            Log.d(TAG, "右移");
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        // Avoid crash on overscroll
        if (nextPosition > mAdapter.getCount() - 1
                || realCurrentPosition > mAdapter.getCount() - 1) {
            return;
        }

        CardView currentCard = mAdapter.getCardViewAt(realCurrentPosition);

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            if (mScalingEnabled) {
                currentCard.setScaleX((float) (1 + 0.2 * (1 - realOffset)));
                currentCard.setScaleY((float) (1 + 0.2 * (1 - realOffset)));
            }
            currentCard.setCardElevation((baseElevation + baseElevation * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
        }

        CardView nextCard = mAdapter.getCardViewAt(nextPosition);

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (mScalingEnabled) {
                nextCard.setScaleX((float) (1 + 0.2 * (realOffset)));
                nextCard.setScaleY((float) (1 + 0.2 * (realOffset)));
            }
            nextCard.setCardElevation((baseElevation + baseElevation * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (realOffset)));
        }

        mLastOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {
        Log.i(TAG, "onPageSelected: position = " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.i(TAG, "onPageScrollStateChanged: state = " + state);
    }
}