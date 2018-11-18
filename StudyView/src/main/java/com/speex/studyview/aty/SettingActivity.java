package com.speex.studyview.aty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.speex.studyview.R;
import com.speex.studyview.view.SettingItemView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = this.getClass().getSimpleName();
    private SettingItemView mSivItem1;
    private SettingItemView mSivItem2;
    private SettingItemView mSivItem3;
    private SettingItemView mSivItem4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        initListener();
    }

    private void initView() {
        mSivItem1 = (SettingItemView) findViewById(R.id.siv_item1);
        mSivItem2 = (SettingItemView) findViewById(R.id.siv_item2);
        mSivItem3 = (SettingItemView) findViewById(R.id.siv_item3);
        mSivItem4 = (SettingItemView) findViewById(R.id.siv_item4);
    }

    private void initListener() {
        mSivItem1.setOnClickListener(this);
        mSivItem2.setOnClickListener(this);
        mSivItem3.setOnClickListener(this);
        mSivItem4.setOnClickListener(this);

        initImageClickListener();
    }

    private void initImageClickListener() {
        mSivItem1.setImageOnClickListener(new SettingItemView.ImageOnClickListener() {
            @Override
            public void onClick() {
                Log.d(TAG, "点击了条目1图片");
            }
        });
        mSivItem2.setImageOnClickListener(new SettingItemView.ImageOnClickListener() {
            @Override
            public void onClick() {
                Log.d(TAG, "点击了条目2图片");
            }
        });
        mSivItem3.setImageOnClickListener(new SettingItemView.ImageOnClickListener() {
            @Override
            public void onClick() {
                Log.d(TAG, "点击了条目3图片");
            }
        });
        mSivItem4.setImageOnClickListener(new SettingItemView.ImageOnClickListener() {
            @Override
            public void onClick() {
                Log.d(TAG, "点击了条目4图片");
            }
        });

    }


    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick");
        int id = view.getId();
        switch (id) {
            case R.id.siv_item1:
                Log.d(TAG, "点击了条目1");
                break;
            case R.id.siv_item2:
                Log.d(TAG, "点击了条目2");
                break;
            case R.id.siv_item3:
                Log.d(TAG, "点击了条目3");
                break;
            case R.id.siv_item4:
                Log.d(TAG, "点击了条目4");
                break;
            default:
        }
    }
}
