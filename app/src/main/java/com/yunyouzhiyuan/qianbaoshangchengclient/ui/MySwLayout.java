package com.yunyouzhiyuan.qianbaoshangchengclient.ui;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ${王俊强} on 2017/2/28.
 */

public class MySwLayout extends SwipeRefreshLayout {



    private float myDownPosX = 0;
    private float myDownPosY = 0;

    public MySwLayout(Context context) {
        super(context);
    }

    public MySwLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN://按下
                myDownPosX = x;
                myDownPosY = y;
                break;
            case MotionEvent.ACTION_MOVE://拖动
                final float deleteX = Math.abs(x - myDownPosX);
                final float deleteY = Math.abs(y - myDownPosY);
                if (deleteX > deleteY) {
                    return false;
                }

                break;
            case MotionEvent.ACTION_UP://抬起
                break;
        }


        return super.onInterceptTouchEvent(ev);
    }
}
