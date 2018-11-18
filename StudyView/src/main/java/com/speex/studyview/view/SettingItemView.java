package com.speex.studyview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.speex.studyview.R;

/**
 * Created by Byron on 2018/11/18.
 */

public class SettingItemView extends FrameLayout {
    private String TAG = this.getClass().getSimpleName();
    private View mView;
    private TextView mTvLeft;
    private TextView mTvRight;
    private ImageView mImage;
    private ImageOnClickListener mImageOnClickListener;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mView = View.inflate(context, R.layout.setting_item, null);
        initView(context);
        initAttrs(context, attrs);
        initListener();
        this.addView(mView);
    }

    private void initListener() {
        mImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick mImageOnClickListener = " + mImageOnClickListener);
                if (mImageOnClickListener != null) {
                    mImageOnClickListener.onClick();
                }
            }
        });
    }

    /**
     * 获取attr.xml文件中的额自定义属性集
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);

        //获取属性值
        int imgId = array.getResourceId(R.styleable.SettingItemView_Img, R.mipmap.right);
        String textLeft = array.getString(R.styleable.SettingItemView_LeftText);
        String textRight = array.getString(R.styleable.SettingItemView_RightText);

        //给控件赋值
        mTvLeft.setText(textLeft);
        mTvRight.setText(textRight);
        mImage.setImageResource(imgId);
    }

    private void initView(Context context) {
        mTvLeft = (TextView) mView.findViewById(R.id.tv_left);
        mTvRight = (TextView) mView.findViewById(R.id.tv_right);
        mImage = (ImageView) mView.findViewById(R.id.iv_img);
    }

    public void setImageOnClickListener(ImageOnClickListener imageOnClickListener) {
        this.mImageOnClickListener = imageOnClickListener;
    }

    public interface ImageOnClickListener {
        void onClick();
    }
}
