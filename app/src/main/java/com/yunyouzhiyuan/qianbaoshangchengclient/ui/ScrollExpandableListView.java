package com.yunyouzhiyuan.qianbaoshangchengclient.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView;

/**
 * Created by ${王俊强} on 2017/3/30.
 */

public class ScrollExpandableListView extends ExpandableListView {
    public ScrollExpandableListView(Context context) {
        super(context);
        init();
    }

    public ScrollExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScrollExpandableListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 设置 mYmaxOverScrollY 根据屏幕的不同分辨率
     */
    private void init() {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        float density = metrics.density;
        mYmaxOverScrollY = (int) density * mYmaxOverScrollY;
    }

    private int mYmaxOverScrollY = 100;

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mYmaxOverScrollY, isTouchEvent);
    }
}
