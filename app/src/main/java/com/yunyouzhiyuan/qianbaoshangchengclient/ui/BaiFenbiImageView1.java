package com.yunyouzhiyuan.qianbaoshangchengclient.ui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by ${王俊强} on 2017/1/9.
 */
public class BaiFenbiImageView1 extends android.support.v7.widget.AppCompatImageView {
    public BaiFenbiImageView1(Context context) {
        super(context);
    }

    public BaiFenbiImageView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaiFenbiImageView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, (int) (widthMeasureSpec*1.2));
    }
}
