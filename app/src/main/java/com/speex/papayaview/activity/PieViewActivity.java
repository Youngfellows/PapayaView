package com.speex.papayaview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.speex.papayaview.R;
import com.speex.papayaview.view.PieView;
import com.speex.papayaview.view.pieview.PieData;

import java.util.ArrayList;

public class PieViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_view);
//        PieView pieView = new PieView(this);
//        setContentView(pieView);

        PieView pieView = (PieView) findViewById(R.id.pie_view);
        ArrayList<PieData> datas = new ArrayList<>();
        PieData pieData = new PieData("sloop1", 60);
        PieData pieData2 = new PieData("sloop2", 30);
        PieData pieData3 = new PieData("sloop3", 40);
        PieData pieData4 = new PieData("sloop4", 20);
        PieData pieData5 = new PieData("sloop5", 20);
        PieData pieData6 = new PieData("sloop6", 30);
        PieData pieData7 = new PieData("sloop7", 10);
        PieData pieData8 = new PieData("sloop8", 30);
        PieData pieData9 = new PieData("sloop9", 50);
        PieData pieData10 = new PieData("sloop10", 5);
        PieData pieData11 = new PieData("sloop11", 70);
        datas.add(pieData);
        datas.add(pieData2);
        datas.add(pieData3);
        datas.add(pieData4);
        datas.add(pieData5);
        datas.add(pieData6);
        datas.add(pieData7);
        datas.add(pieData8);
        datas.add(pieData9);
        datas.add(pieData10);
        datas.add(pieData11);
        pieView.setStartAngle(30);
        pieView.setData(datas);
    }
}
