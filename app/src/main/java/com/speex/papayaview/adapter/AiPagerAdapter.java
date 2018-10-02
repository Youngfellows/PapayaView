package com.speex.papayaview.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.speex.papayaview.R;
import com.speex.papayaview.bean.CardItem;

import java.util.ArrayList;

/**
 * Created by Byron on 2018/10/2.
 */

public class AiPagerAdapter extends PagerAdapter implements CardAdapter {
    private Context mContext;
    private ArrayList<Integer> mArrayList;
    private float mBaseElevation;
    private ArrayList<CardView> mCardViews;

    public AiPagerAdapter(Context context) {
        mContext = context;
        mArrayList = new ArrayList<>();
        mCardViews = new ArrayList<>();
    }

    public void addCardItem(CardItem cardItem) {
        mArrayList.add(cardItem.getResImg());
        mCardViews.add(null);
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mCardViews.get(position);
    }

    @Override
    public int getCount() {
        return mArrayList != null ? mArrayList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        View view = View.inflate(mContext, R.layout.item_view_pager_card, container);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view_pager_card, container, false);
        container.addView(view);

        //绑定视图
        ImageView imageView = (ImageView) view.findViewById(R.id.img);
        imageView.setImageResource(mArrayList.get(position));

        CardView cardView = (CardView) view.findViewById(R.id.cardview);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }
        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mCardViews.set(position, cardView);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mCardViews.set(position, null);
    }
}
