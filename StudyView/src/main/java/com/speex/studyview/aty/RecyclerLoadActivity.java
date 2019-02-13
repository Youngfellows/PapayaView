package com.speex.studyview.aty;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.papaya.recyclerview.LFRecyclerView;
import com.papaya.recyclerview.OnItemClickListener;
import com.speex.studyview.R;
import com.speex.studyview.adapter.LoadRecyclerAdapter;

import java.util.ArrayList;

public class RecyclerLoadActivity extends AppCompatActivity implements OnItemClickListener, LFRecyclerView.LFRecyclerViewListener,LFRecyclerView.LFRecyclerViewScrollChange {
    private LFRecyclerView recycleview;
    private boolean b;
    private ArrayList<String> list;
    private LoadRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_load);
        list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("数据" + i);
        }

        recycleview = (LFRecyclerView) findViewById(R.id.recycleview);
        recycleview.setLoadMore(true);
        recycleview.setRefresh(true);
        recycleview.setNoDateShow();
        recycleview.setAutoLoadMore(true);
        recycleview.setOnItemClickListener(this);
        recycleview.setLFRecyclerViewListener(this);
        recycleview.setScrollChangeListener(this);
        recycleview.setItemAnimator(new DefaultItemAnimator());
        adapter = new LoadRecyclerAdapter(list);
        recycleview.setAdapter(adapter);

        TextView tv = new TextView(this);
        tv.setText("这是头部");
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.RED);
        recycleview.setHeaderView(tv);
        tv = new TextView(this);
        tv.setText("这是底部");
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.RED);
        recycleview.setFootView(tv);
    }

    @Override
    public void onClick(int position) {
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(int po) {
        Toast.makeText(this, "Long:" + po, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b = !b;
                list.add(0, "刷新的数据" + "==onRefresh");
                recycleview.stopRefresh(b);
                adapter.notifyItemInserted(0);
                adapter.notifyItemRangeChanged(0, list.size());

            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recycleview.stopLoadMore();
                list.add(list.size(), "加载的数据" + "==onLoadMore");
//                list.add(list.size(), "leefeng.me" + "==onLoadMore");
                adapter.notifyItemRangeInserted(list.size() - 1, 1);

            }
        }, 2000);
    }

    @Override
    public void onRecyclerViewScrollChange(View view, int i, int i1) {

    }
}
