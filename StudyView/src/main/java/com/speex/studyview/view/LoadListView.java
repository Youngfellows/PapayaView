package com.speex.studyview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.speex.studyview.R;

/**
 * Created by Byron on 2019/1/8.
 */

public class LoadListView extends ListView implements AbsListView.OnScrollListener {
    private String TAG = this.getClass().getSimpleName();
    private ILoadListViewListener mLoadListViewListener;
    private int mLastVisibleItem;// 最后一个可见项
    private int mTotalItems;//所有项
    private boolean isLoading = false;//是否正在加载更多
    private Context mContext;
    private View mFooterView;
    private ProgressBar mFooterProgressBar;
    private TextView mFooterHint;
    private RelativeLayout mRlFooter;

    public LoadListView(Context context) {
        super(context);
        Log.i(TAG, "LoadListView: 1");
        init(context);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "LoadListView: 2");
        init(context);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "LoadListView: 3");
        init(context);
    }

    /**
     * 设置监听
     *
     * @param loadListViewListener
     */
    public void setLoadListViewListener(ILoadListViewListener loadListViewListener) {
        mLoadListViewListener = loadListViewListener;
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        //加载底部的footer
        mFooterView = inflater.inflate(R.layout.footer, null);
        mRlFooter = mFooterView.findViewById(R.id.rl_footer);
        mFooterProgressBar = mFooterView.findViewById(R.id.footer_progress);
        mFooterHint = mFooterView.findViewById(R.id.footer_hint_textview);

        //初始化时隐藏footer
        hideFooter();

        //加footer
        this.addFooterView(mFooterView);
        // 设置滚动监听
        this.setOnScrollListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    /**
     * @param view
     * @param scrollState 1.SCROLL_STATE_IDLE 不滚动时的状态，通常会在滚动停止时监听到此状态
     *                    2.SCROLL_STATE_TOUCH_SCROLL 正在滚动的状态
     *                    3.SCROLL_STATE_FLING 用力快速滑动时可监听到此值
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.i(TAG, "onScrollStateChanged: scrollState = " + scrollState);
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                Log.i(TAG, "不滚动时的状态");
                if (mLastVisibleItem == mTotalItems) {
                    //判断不是正在加载
                    if (!isLoading) {
                        isLoading = true;
                        showFooter();//显示footer
                        if (mLoadListViewListener != null) {
                            mLoadListViewListener.onLoadMore();//回调，正在加载更多
                        }
                    }
                }
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                Log.i(TAG, "正在滚动的状态");
                break;
            case SCROLL_STATE_FLING:
                Log.i(TAG, "用力快速滑动");
                break;
            default:
                break;
        }
    }

    /**
     * 显示footer
     */
    private void showFooter() {
        mRlFooter.setVisibility(VISIBLE);
    }

    /**
     * 隐藏footer
     */
    private void hideFooter() {
        mRlFooter.setVisibility(INVISIBLE);
    }

    /**
     * 加载更多完成
     */
    public void loadMoreCompleted() {
        isLoading = false;//没有正在加载
        hideFooter();
    }

    /**
     * @param view
     * @param firstVisibleItem 第一个可视的项，这里是整个item都可视的项。被挡住一点都不符合
     * @param visibleItemCount 可视的项的个数
     * @param totalItemCount   总item的个数
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mLastVisibleItem = firstVisibleItem + visibleItemCount;
        mTotalItems = totalItemCount;
        Log.i(TAG, "onScroll 第一个可视的项: " + firstVisibleItem + " ,最后一个可见项: " + mLastVisibleItem + " ,可视的项的个数: " + visibleItemCount + " ,总item的个数: " + totalItemCount);
    }

    public interface ILoadListViewListener {
        /**
         * 正在下拉刷新
         */
        void onRefresh();

        /**
         * 正在加载更多
         */
        void onLoadMore();
    }
}
