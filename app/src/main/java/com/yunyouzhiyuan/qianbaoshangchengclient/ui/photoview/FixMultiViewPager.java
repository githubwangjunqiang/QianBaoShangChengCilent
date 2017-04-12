/*
 * FixMultiViewPager 2016-12-26
 * Copyright (c) 2016 suzeyu Co.Ltd. All right reserved
 */
package com.yunyouzhiyuan.qianbaoshangchengclient.ui.photoview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

/**
 * Author :  suzeyu
 * Time   :  2016-12-26  上午1:41
 * ClassDescription : 对多点触控场景时, {@link ViewPager#onInterceptTouchEvent(MotionEvent)}中
 * pointerIndex = -1. 发生IllegalArgumentException: pointerIndex out of range 处理
 */
public class FixMultiViewPager extends ViewPager {

    public FixMultiViewPager(Context context) {
        super(context);
    }

    public FixMultiViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            LogUtils.e("onInterceptTouchEvent() ", ex);
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof DragPhotoView) {
            return v.canScrollHorizontally(-dx);
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
