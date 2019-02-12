package com.speex.studyview.aty;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.speex.studyview.R;
import com.speex.studyview.view.LoadListView;

import java.util.ArrayList;

public class LoadActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private LoadListView mLoadListView;
    private ArrayList<String> mDatas;
    private ArrayAdapter<String> mArrayAdapter;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        initData();
        initView();
        initListener();
    }

    private void initView() {
        mHandler = new Handler(Looper.getMainLooper());
        mLoadListView = (LoadListView) findViewById(R.id.llv);
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        mLoadListView.setAdapter(mArrayAdapter);
        mLoadListView.setLoadListViewListener(new LoadListView.ILoadListViewListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "onRefresh: 正在刷新");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //下拉刷新
                        mDatas.add(0, "下拉刷新的数据");
                        mArrayAdapter.notifyDataSetChanged();
                        mLoadListView.onRefreshComplete();
                    }
                }, 1000 * 3);
            }

            @Override
            public void onLoadMore() {
                Log.i(TAG, "onLoadMore: 正在加载");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //加载更多
                        mDatas.add(mDatas.size(), "加载更多的数据");
                        mArrayAdapter.notifyDataSetChanged();
                        mLoadListView.loadMoreCompleted();//加载完成
                    }
                }, 1000 * 3);
            }
        });
    }

    private void initListener() {

    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            mDatas.add("数据" + i);
        }
    }
}
