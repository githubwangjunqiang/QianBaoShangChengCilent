package com.yunyouzhiyuan.qianbaoshangchengclient.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by ${王俊强} on 2017/4/11.
 */

public class DelayedClickTextView extends android.support.v7.widget.AppCompatTextView {

    public void setTime(long time) {
        this.time = time;
    }

    private long time = 500;


    public DelayedClickTextView(Context context) {
        super(context);
    }

    public DelayedClickTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DelayedClickTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {

        setClickable(false);
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (isLaidOut()) {
                        setClickable(true);
                    }
                } else {
                    setClickable(true);
                }
            }
        }, time);
        return super.performClick();
    }


}
