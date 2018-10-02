package com.speex.papayaview.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.speex.papayaview.R;
import com.speex.papayaview.bean.ItemBean;

import java.util.ArrayList;

/**
 * Created by Byron on 2018/10/2.
 */

public class AiRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<ItemBean> mList;
    private Context mContext;
    private final LayoutInflater mInflater;
    private OnClickListener mOnClickListener;

    public AiRecyclerAdapter(Context context, ArrayList<ItemBean> list, OnClickListener listener) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
        mOnClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_card, null);
        return new AiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AiViewHolder aiViewHolder = (AiViewHolder) holder;
        ItemBean itemBean = mList.get(position);
        Glide.with(mContext).load(itemBean.getUrl()).into(aiViewHolder.mImageView);
        aiViewHolder.mContent.setText(itemBean.getContent());
        aiViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class AiViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mContent;
        public CardView mCardView;

        public AiViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.img);
            mContent = (TextView) itemView.findViewById(R.id.content);
            mCardView = (CardView) itemView.findViewById(R.id.card);
        }
    }

    public interface OnClickListener {
        void onClick(int position);
    }
}
