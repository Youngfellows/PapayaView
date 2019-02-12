package com.speex.studyview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.speex.studyview.R;

import java.util.Date;

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

    private final static int RELEASE_To_REFRESH = 0;
    private final static int PULL_To_REFRESH = 1;
    private final static int REFRESHING = 2;
    private final static int DONE = 3;
    private final static int LOADING = 4;

    // 实际的padding的距离与界面上偏移距离的比例
    private final static int RATIO = 3;
    private View mHeadView;
    private ImageView mArrowImageView;
    private ProgressBar mHeadProgressBar;
    private TextView mHeadTipsTextview;
    private TextView mLastUpdatedTextView;
    private int mHeadContentHeight;
    private int mHeadContentWidth;

    private RotateAnimation mAnimation;
    private RotateAnimation mReverseAnimation;

    // 用于保证startY的值在一个完整的touch事件中只被记录一次
    private boolean isRecored;
    private int mStartY;
    private int mFirstItemIndex;
    private boolean isBack;
    private boolean isRefreshable;
    private int mState;//当前状态

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
        isRefreshable = true;
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        //加载底部的footer
        mFooterView = inflater.inflate(R.layout.footer, null);
        mRlFooter = mFooterView.findViewById(R.id.rl_footer);
        mFooterProgressBar = mFooterView.findViewById(R.id.footer_progress);
        mFooterHint = mFooterView.findViewById(R.id.footer_hint_textview);

        //加载头部布局
        mHeadView = inflater.inflate(R.layout.head, null);
        mArrowImageView = (ImageView) mHeadView.findViewById(R.id.head_arrowImageView);//箭头
        mArrowImageView.setMinimumWidth(70);
        mArrowImageView.setMinimumHeight(50);
        mHeadProgressBar = (ProgressBar) mHeadView.findViewById(R.id.head_progressBar);//进度条
        mHeadTipsTextview = (TextView) mHeadView.findViewById(R.id.head_tipsTextView);//提示语
        mLastUpdatedTextView = (TextView) mHeadView.findViewById(R.id.head_lastUpdatedTextView); //上次更新时间

        //测量headView的宽高
        measureView(mHeadView);
        mHeadContentHeight = mHeadView.getMeasuredHeight();
        mHeadContentWidth = mHeadView.getMeasuredWidth();
        mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
        mHeadView.invalidate();
        Log.d(TAG, "width: " + mHeadContentWidth + " ,height: " + mHeadContentHeight);


        //初始化时隐藏footer
        hideFooter();

        this.addHeaderView(mHeadView, null, false);//加载headView
        this.addFooterView(mFooterView); //加footer

        // 设置滚动监听
        this.setOnScrollListener(this);

        mAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setDuration(250);
        mAnimation.setFillAfter(true);

        mReverseAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseAnimation.setInterpolator(new LinearInterpolator());
        mReverseAnimation.setDuration(200);
        mReverseAnimation.setFillAfter(true);

        mState = DONE;
        isRefreshable = false;//没有刷新
    }

    /**
     * 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
     *
     * @param child
     */
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isRefreshable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mFirstItemIndex == 0 && !isRecored) {
                        isRecored = true;
                        mStartY = (int) event.getY();
                        Log.v(TAG, "在down时候记录当前位置");
                    }
                    break;

                case MotionEvent.ACTION_UP:

                    if (mState != REFRESHING && mState != LOADING) {
                        if (mState == DONE) {
                            // 什么都不做
                        }
                        if (mState == PULL_To_REFRESH) {
                            mState = DONE;
                            changeHeaderViewByState();
                            Log.v(TAG, "由下拉刷新状态，到done状态");
                        }
                        if (mState == RELEASE_To_REFRESH) {
                            mState = REFRESHING;
                            changeHeaderViewByState();
                            onRefresh();
                            Log.v(TAG, "由松开刷新状态，到done状态");
                        }
                    }

                    isRecored = false;
                    isBack = false;

                    break;

                case MotionEvent.ACTION_MOVE:
                    int tempY = (int) event.getY();
                    if (!isRecored && mFirstItemIndex == 0) {
                        Log.v(TAG, "在move时候记录下位置");
                        isRecored = true;
                        mStartY = tempY;
                    }

                    if (mState != REFRESHING && isRecored && mState != LOADING) {

                        // 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

                        // 可以松手去刷新了
                        if (mState == RELEASE_To_REFRESH) {

                            setSelection(0);

                            // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                            if (((tempY - mStartY) / RATIO < mHeadContentHeight)
                                    && (tempY - mStartY) > 0) {
                                mState = PULL_To_REFRESH;
                                changeHeaderViewByState();

                                Log.v(TAG, "由松开刷新状态转变到下拉刷新状态");
                            }
                            // 一下子推到顶了
                            else if (tempY - mStartY <= 0) {
                                mState = DONE;
                                changeHeaderViewByState();

                                Log.v(TAG, "由松开刷新状态转变到done状态");
                            }
                            // 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
                            else {
                                // 不用进行特别的操作，只用更新paddingTop的值就行了
                            }
                        }
                        // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                        if (mState == PULL_To_REFRESH) {

                            setSelection(0);

                            // 下拉到可以进入RELEASE_TO_REFRESH的状态
                            if ((tempY - mStartY) / RATIO >= mHeadContentHeight) {
                                mState = RELEASE_To_REFRESH;
                                isBack = true;
                                changeHeaderViewByState();

                                Log.v(TAG, "由done或者下拉刷新状态转变到松开刷新");
                            }
                            // 上推到顶了
                            else if (tempY - mStartY <= 0) {
                                mState = DONE;
                                changeHeaderViewByState();

                                Log.v(TAG, "由DOne或者下拉刷新状态转变到done状态");
                            }
                        }

                        // done状态下
                        if (mState == DONE) {
                            if (tempY - mStartY > 0) {
                                mState = PULL_To_REFRESH;
                                changeHeaderViewByState();
                            }
                        }

                        // 更新headView的size
                        if (mState == PULL_To_REFRESH) {
                            mHeadView.setPadding(0, -1 * mHeadContentHeight
                                    + (tempY - mStartY) / RATIO, 0, 0);

                        }

                        // 更新headView的paddingTop
                        if (mState == RELEASE_To_REFRESH) {
                            mHeadView.setPadding(0, (tempY - mStartY) / RATIO
                                    - mHeadContentHeight, 0, 0);
                        }

                    }

                    break;
            }
        }
        return super.onTouchEvent(event);
    }


    // 当状态改变时候，调用该方法，以更新界面
    private void changeHeaderViewByState() {
        switch (mState) {
            case RELEASE_To_REFRESH:
                mArrowImageView.setVisibility(View.VISIBLE);
                mHeadProgressBar.setVisibility(View.GONE);
                mHeadTipsTextview.setVisibility(View.VISIBLE);
                mLastUpdatedTextView.setVisibility(View.VISIBLE);

                mArrowImageView.clearAnimation();
                mArrowImageView.startAnimation(mAnimation);

                mHeadTipsTextview.setText("松开刷新");
                Log.v(TAG, "当前状态，松开刷新");
                break;
            case PULL_To_REFRESH:
                mHeadProgressBar.setVisibility(View.GONE);
                mHeadTipsTextview.setVisibility(View.VISIBLE);
                mLastUpdatedTextView.setVisibility(View.VISIBLE);
                mArrowImageView.clearAnimation();
                mArrowImageView.setVisibility(View.VISIBLE);
                // 是由RELEASE_To_REFRESH状态转变来的
                if (isBack) {
                    isBack = false;
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mReverseAnimation);

                    mHeadTipsTextview.setText("下拉刷新");
                } else {
                    mHeadTipsTextview.setText("下拉刷新");
                }
                Log.v(TAG, "当前状态，下拉刷新");
                break;

            case REFRESHING:

                mHeadView.setPadding(0, 0, 0, 0);

                mHeadProgressBar.setVisibility(View.VISIBLE);
                mArrowImageView.clearAnimation();
                mArrowImageView.setVisibility(View.GONE);
                mHeadTipsTextview.setText("正在刷新...");
                mLastUpdatedTextView.setVisibility(View.VISIBLE);

                Log.v(TAG, "当前状态,正在刷新...");
                break;
            case DONE:
                mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);

                mHeadProgressBar.setVisibility(View.GONE);
                mArrowImageView.clearAnimation();
                mArrowImageView.setImageResource(R.mipmap.loadlistview_arrow);
                mHeadTipsTextview.setText("下拉刷新");
                mLastUpdatedTextView.setVisibility(View.VISIBLE);

                Log.v(TAG, "当前状态，done");
                break;
        }
    }

    private void onRefresh() {
        if (mLoadListViewListener != null) {
            mLoadListViewListener.onRefresh();
        }
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
     * 刷新数据完成
     */
    public void onRefreshComplete() {
        mState = DONE;
        mLastUpdatedTextView.setText("最近更新:" + new Date().toLocaleString());
        changeHeaderViewByState();
    }

    /**
     * @param view
     * @param firstVisibleItem 第一个可视的项，这里是整个item都可视的项。被挡住一点都不符合
     * @param visibleItemCount 可视的项的个数
     * @param totalItemCount   总item的个数
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstItemIndex = firstVisibleItem;
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
