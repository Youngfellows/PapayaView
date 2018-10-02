package com.speex.papayaview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.speex.papayaview.R;
import com.speex.papayaview.adapter.AiRecyclerAdapter;
import com.speex.papayaview.adapter.SpaceItemDecoration;
import com.speex.papayaview.bean.ItemBean;

import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<ItemBean> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        initData();
        initView();
    }

    private void initData() {
        mArrayList = new ArrayList<>();
        ItemBean itemBean = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974978237&di=aa67212ea09517b3729c4394e6c6e92d&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1244239179%2C175145384%26fm%3D214%26gp%3D0.jpg", "图片1");
        ItemBean itemBean2 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974984588&di=3aca1b010d7b67b027b69987f3823c52&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F0%2F586364185dbc1.jpg", "图片2");
        ItemBean itemBean3 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974992976&di=6bf3b70ace9b54762620cb1595b75294&imgtype=0&src=http%3A%2F%2Fwww.fahao.cc%2Fuploadfiles%2F201702%2F16%2F20170216023654105.jpg", "图片3");
        ItemBean itemBean4 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534569703&di=fa557bb817a9bc3d64be9400546cb974&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.tuwandata.com%2Fv2%2Fthumb%2Fall%2FMjAxZSwwLDAsNCwzLDEsLTEsMSw%3D%2Fu%2Fwww.tuwan.com%2Fuploads%2Fallimg%2F1503%2F04%2F765-150304143511-50.jpg", "图片4");
        ItemBean itemBean5 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974978237&di=aa67212ea09517b3729c4394e6c6e92d&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1244239179%2C175145384%26fm%3D214%26gp%3D0.jpg", "图片1");
        ItemBean itemBean6 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974984588&di=3aca1b010d7b67b027b69987f3823c52&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F0%2F586364185dbc1.jpg", "图片2");
        ItemBean itemBean7 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974992976&di=6bf3b70ace9b54762620cb1595b75294&imgtype=0&src=http%3A%2F%2Fwww.fahao.cc%2Fuploadfiles%2F201702%2F16%2F20170216023654105.jpg", "图片3");
        ItemBean itemBean8 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534569703&di=fa557bb817a9bc3d64be9400546cb974&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.tuwandata.com%2Fv2%2Fthumb%2Fall%2FMjAxZSwwLDAsNCwzLDEsLTEsMSw%3D%2Fu%2Fwww.tuwan.com%2Fuploads%2Fallimg%2F1503%2F04%2F765-150304143511-50.jpg", "图片4");
        ItemBean itemBean9 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974978237&di=aa67212ea09517b3729c4394e6c6e92d&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1244239179%2C175145384%26fm%3D214%26gp%3D0.jpg", "图片1");
        ItemBean itemBean10 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974984588&di=3aca1b010d7b67b027b69987f3823c52&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F0%2F586364185dbc1.jpg", "图片2");
        ItemBean itemBean11 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974992976&di=6bf3b70ace9b54762620cb1595b75294&imgtype=0&src=http%3A%2F%2Fwww.fahao.cc%2Fuploadfiles%2F201702%2F16%2F20170216023654105.jpg", "图片3");
        ItemBean itemBean12 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534569703&di=fa557bb817a9bc3d64be9400546cb974&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.tuwandata.com%2Fv2%2Fthumb%2Fall%2FMjAxZSwwLDAsNCwzLDEsLTEsMSw%3D%2Fu%2Fwww.tuwan.com%2Fuploads%2Fallimg%2F1503%2F04%2F765-150304143511-50.jpg", "图片4");
        ItemBean itemBean13 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974978237&di=aa67212ea09517b3729c4394e6c6e92d&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1244239179%2C175145384%26fm%3D214%26gp%3D0.jpg", "图片1");
        ItemBean itemBean14 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974984588&di=3aca1b010d7b67b027b69987f3823c52&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F0%2F586364185dbc1.jpg", "图片2");
        ItemBean itemBean15 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974992976&di=6bf3b70ace9b54762620cb1595b75294&imgtype=0&src=http%3A%2F%2Fwww.fahao.cc%2Fuploadfiles%2F201702%2F16%2F20170216023654105.jpg", "图片3");
        ItemBean itemBean16 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534569703&di=fa557bb817a9bc3d64be9400546cb974&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.tuwandata.com%2Fv2%2Fthumb%2Fall%2FMjAxZSwwLDAsNCwzLDEsLTEsMSw%3D%2Fu%2Fwww.tuwan.com%2Fuploads%2Fallimg%2F1503%2F04%2F765-150304143511-50.jpg", "图片4");
        ItemBean itemBean17 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974978237&di=aa67212ea09517b3729c4394e6c6e92d&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1244239179%2C175145384%26fm%3D214%26gp%3D0.jpg", "图片1");
        ItemBean itemBean18 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974984588&di=3aca1b010d7b67b027b69987f3823c52&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F0%2F586364185dbc1.jpg", "图片2");
        ItemBean itemBean19 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974992976&di=6bf3b70ace9b54762620cb1595b75294&imgtype=0&src=http%3A%2F%2Fwww.fahao.cc%2Fuploadfiles%2F201702%2F16%2F20170216023654105.jpg", "图片3");
        ItemBean itemBean20 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534569703&di=fa557bb817a9bc3d64be9400546cb974&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.tuwandata.com%2Fv2%2Fthumb%2Fall%2FMjAxZSwwLDAsNCwzLDEsLTEsMSw%3D%2Fu%2Fwww.tuwan.com%2Fuploads%2Fallimg%2F1503%2F04%2F765-150304143511-50.jpg", "图片4");
        ItemBean itemBean21 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974978237&di=aa67212ea09517b3729c4394e6c6e92d&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1244239179%2C175145384%26fm%3D214%26gp%3D0.jpg", "图片1");
        ItemBean itemBean22 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974984588&di=3aca1b010d7b67b027b69987f3823c52&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F0%2F586364185dbc1.jpg", "图片2");
        ItemBean itemBean23 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533974992976&di=6bf3b70ace9b54762620cb1595b75294&imgtype=0&src=http%3A%2F%2Fwww.fahao.cc%2Fuploadfiles%2F201702%2F16%2F20170216023654105.jpg", "图片3");
        ItemBean itemBean24 = new ItemBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534569703&di=fa557bb817a9bc3d64be9400546cb974&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.tuwandata.com%2Fv2%2Fthumb%2Fall%2FMjAxZSwwLDAsNCwzLDEsLTEsMSw%3D%2Fu%2Fwww.tuwan.com%2Fuploads%2Fallimg%2F1503%2F04%2F765-150304143511-50.jpg", "图片4");

        mArrayList.add(itemBean);
        mArrayList.add(itemBean2);
        mArrayList.add(itemBean3);
        mArrayList.add(itemBean4);
        mArrayList.add(itemBean5);
        mArrayList.add(itemBean6);
        mArrayList.add(itemBean7);
        mArrayList.add(itemBean8);
        mArrayList.add(itemBean9);
        mArrayList.add(itemBean10);
        mArrayList.add(itemBean11);
        mArrayList.add(itemBean12);
        mArrayList.add(itemBean13);
        mArrayList.add(itemBean14);
        mArrayList.add(itemBean15);
        mArrayList.add(itemBean16);
        mArrayList.add(itemBean17);
        mArrayList.add(itemBean18);
        mArrayList.add(itemBean19);
        mArrayList.add(itemBean20);
        mArrayList.add(itemBean21);
        mArrayList.add(itemBean22);
        mArrayList.add(itemBean23);
        mArrayList.add(itemBean24);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        AiRecyclerAdapter aiRecyclerAdapter = new AiRecyclerAdapter(this, mArrayList, new AiRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(RecyclerActivity.this, mArrayList.get(position).getContent(), Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(30));
        mRecyclerView.setAdapter(aiRecyclerAdapter);
    }
}
