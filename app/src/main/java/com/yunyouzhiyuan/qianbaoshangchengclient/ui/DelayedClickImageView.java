package com.yunyouzhiyuan.qianbaoshangchengclient.ui;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;

/**
 * Created by ${王俊强} on 2017/4/11.
 */

public class DelayedClickImageView extends android.support.v7.widget.AppCompatImageView {
    public DelayedClickImageView(Context context) {
        super(context);
    }

    public DelayedClickImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DelayedClickImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        setClickable(false);
        new Handler().postDelayed(new Runnable() {
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
        }, 500);
        return super.performClick();
    }
}
