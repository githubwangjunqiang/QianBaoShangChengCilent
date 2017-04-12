package com.yunyouzhiyuan.qianbaoshangchengclient.util;

import android.widget.AbsListView;

/**
 * Created by ${王俊强} on 2017/2/11.
 */

public abstract class ListViewListener implements AbsListView.OnScrollListener {

    private boolean lvatbottom = false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                // 飞快滑动的状态
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                // 空闲时的状态
                // 此时如果滑到最底部 加载数据
                if (lvatbottom ) {
                    bottom();
                }
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                // 手指拖动的状态
                break;
        }
    }

    public abstract void bottom() ;

    @Override
    public void onScroll(AbsListView view, int f, int v, int t) {
        if (f + v == t) {
            lvatbottom = true;
        } else {
            lvatbottom = false;
        }
    }
}
