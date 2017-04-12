package com.yunyouzhiyuan.qianbaoshangchengclient.ui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by 王俊强 on 2016/8/19 0019.
 */
public class MyListview extends ScrollListView {
    public MyListview(Context context) {
        super(context);
    }
    public MyListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyListview(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
