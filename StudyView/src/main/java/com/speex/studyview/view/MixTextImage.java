package com.speex.studyview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.speex.studyview.R;
import com.speex.studyview.utils.StringUtils;

/**
 * Created by Byron on 2018/12/17.
 */

public class MixTextImage extends LinearLayout {
    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private String mTitle;
    private float mTitleSize;
    private int mTitleColor;
    private int mImageSrc;
    private int mImageBg;
    private int mImageWidth;
    private int mImageHeight;
    private int mImageAlpha;
    private ImageView mImageAvatar;

    private TextView mTvTitle;

    public MixTextImage(Context context) {
        this(context, null);
    }

    public MixTextImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MixTextImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.mix_text_image, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MixTextImage);
        initAttr(typedArray);
        initView();
    }

    private void initView() {
        mImageAvatar = (ImageView) findViewById(R.id.image_avatar);
        mTvTitle = (TextView) findViewById(R.id.tv_title);

        //设置属性
        mImageAvatar.setImageResource(mImageSrc);
        mImageAvatar.setBackgroundResource(mImageBg);
        mImageAvatar.setAlpha(mImageAlpha);
        LayoutParams layoutParams = new LayoutParams(mImageWidth, mImageHeight);
        mImageAvatar.setLayoutParams(layoutParams);//设置图片高度

        mTvTitle.setText(mTitle);
        mTvTitle.setTextColor(mTitleColor);
        mTvTitle.setTextSize(mTitleSize);
    }

    /**
     * @param typedArray
     */
    private void initAttr(TypedArray typedArray) {
        mTitle = typedArray.getString(R.styleable.MixTextImage_mix_title);
        mTitleSize = typedArray.getDimensionPixelSize(R.styleable.MixTextImage_mix_title_size, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mTitleColor = typedArray.getColor(R.styleable.MixTextImage_mix_title_color, Color.BLACK);

        mImageSrc = typedArray.getResourceId(R.styleable.MixTextImage_mix_image_src, 0);
        mImageBg = typedArray.getResourceId(R.styleable.MixTextImage_mix_image_bg, 0);
//        mImageWidth = (int) typedArray.getDimension(R.styleable.MixTextImage_mix_image_width, 25);
//        mImageHeight = (int) typedArray.getDimension(R.styleable.MixTextImage_mix_image_height, 25);
        mImageWidth = (int) typedArray.getDimensionPixelSize(R.styleable.MixTextImage_mix_image_width, StringUtils.getDip(mContext, 48));
        mImageHeight = (int) typedArray.getDimension(R.styleable.MixTextImage_mix_image_height, StringUtils.getDip(mContext, 48));
        mImageAlpha = typedArray.getInt(R.styleable.MixTextImage_mix_image_alpha, 255);
        typedArray.recycle();
        Log.d(TAG, "mTitleSize: " + mTitleSize + " ,mImageWidth: " + mImageWidth + " ,mImageHeight: " + mImageHeight);
    }


    public void setTitle(String title) {
        mTitle = title;
    }

    public void setTitleSize(float titleSize) {
        mTitleSize = titleSize;
    }

    public void setTitleColor(int titleColor) {
        mTitleColor = titleColor;
    }

    public void setImageSrc(int imageSrc) {
        mImageSrc = imageSrc;
    }

    public void setImageBg(int imageBg) {
        mImageBg = imageBg;
    }

    public void setImageWidth(int imageWidth) {
        mImageWidth = imageWidth;
    }

    public void setImageHeight(int imageHeight) {
        mImageHeight = imageHeight;
    }

    public void setImageAlpha(int imageAlpha) {
        mImageAlpha = imageAlpha;
    }
}
