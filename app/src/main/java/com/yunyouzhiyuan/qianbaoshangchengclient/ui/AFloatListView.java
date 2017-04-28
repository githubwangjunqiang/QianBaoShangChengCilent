package com.yunyouzhiyuan.qianbaoshangchengclient.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by ${王俊强} on 2017/2/16.
 */

public class AFloatListView extends ListView {

    public AFloatListView(Context context) {
        super(context);
        init();
    }

    public AFloatListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AFloatListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    public AFloatListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

    }

    private int lastY;

    public void setCanScrollDistance(int canScrollDistance) {
        this.canScrollDistance = canScrollDistance;
    }

    private int canScrollDistance;  //可以滚动的最大距离，此处规定为布局中的ImageView的高度，
    // 可以根据需要修改
    private View view;

    public void setView(ViewGroup view) {
        this.view = view;
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        return super.dispatchHoverEvent(event);
    }

    private boolean mScrolling;
    private float touchDownX;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                touchDownX = event.getX();
                mScrolling = false;
                break;
            case MotionEvent.ACTION_MOVE://拖动
                if (Math.abs(touchDownX - event.getX()) >= ViewConfiguration.get(
                        getContext()).getScaledTouchSlop()) {
                    mScrolling = true;
                } else {
                    mScrolling = false;
                }
                break;
//                final float deleteX = Math.abs(x - myDownPosX);
//                final float deleteY = Math.abs(y - myDownPosY);
//                if (deleteX != deleteY) {
//                    return true;
//                }
            case MotionEvent.ACTION_UP://抬起
                mScrolling = false;
                break;
        }
        return mScrolling;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN://按下
                lastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE://拖动
                if (getChildCount() < 1) {
                    return super.onTouchEvent(ev);
                }
                int currentY = (int) ev.getRawY();
                if (currentY < lastY) {
//            View view = ((ViewGroup) getParent());
                    if (view.getScrollY() < canScrollDistance) {
                        view.scrollBy(0, (int) (lastY - currentY));
                        lastY = currentY;
                        if (view.getScrollY() > canScrollDistance) {//防止迅速滑动时，界面滚动距离过大
                            view.scrollTo(0, canScrollDistance);
                        }
                        lastY = currentY;
                        return true;
                    }

                } else if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() == 0) {
                    if (view.getScrollY() > 0) {
                        view.scrollBy(0, (int) (lastY - currentY));
                        lastY = currentY;
                        if (view.getScrollY() < 0) {//防止迅速滑动时，界面滚动距离过大
                            view.scrollTo(0, 0);
                        }
                        return true;
                    }
                }
                lastY = (int) ev.getRawY();
            case MotionEvent.ACTION_UP://抬起
                break;
        }
        return super.onTouchEvent(ev);// 调用super.onTouchEvent会导致ListView滚动

    }
}
