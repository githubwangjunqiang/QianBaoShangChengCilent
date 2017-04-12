package com.yunyouzhiyuan.qianbaoshangchengclient.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.yunyouzhiyuan.qianbaoshangchengclient.util.Utils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by 王俊强 on 2016/11/1.
 */
public class Bannerer implements ViewPager.OnPageChangeListener {
    private List<ImageView> mImageViews;
    private List<String> list;
    private ViewPager viewPager;
    private Activity context;
    private LinearLayout indicator;
    private CallBack callBack;
    private PagerAdapter pagerAdapter;
    private String imageurl;
    private CheckedTextView[] carray;
    private int RID;
    private boolean isuserMover = false;
    private int index;
    private Handler handler;
    private MyFixedSpeedScroller scroller;

    public Bannerer(List<String> list, final ViewPager viewPager, Activity context, LinearLayout indicator, String imageurl, int RID, CallBack callBack) {
        this.list = list;
        this.viewPager = viewPager;
        this.context = context;
        this.indicator = indicator;
        this.imageurl = imageurl;
        this.callBack = callBack;
        this.RID = RID;
        this.mImageViews = new ArrayList<>();
        this.carray = new CheckedTextView[list.size()];
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    if (!isuserMover) {
                        index++;
                        viewPager.setCurrentItem(index, true);
                    }
                    handler.sendEmptyMessageDelayed(0, 4000);
                }
            }
        };
        init();


    }

    private void init() {
        if(indicator.getChildCount()>0){
            indicator.removeAllViews();
        }
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            scroller = new MyFixedSpeedScroller(viewPager.getContext(),
                    new AccelerateInterpolator());
            field.set(viewPager, scroller);
            scroller.setmDuration(500);
        } catch (Exception e) {
        }
        for (int i = 0; i < list.size(); i++) {
            addView(context, indicator, i);
        }
        pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                if (position < 0) {
                    position = position + mImageViews.size();
                }
                int i = position % mImageViews.size();
                ImageView view = mImageViews.get(i);
                ViewParent wp = view.getParent();
                if (wp != null) {
                    ViewGroup p = (ViewGroup) wp;
                    p.removeView(view);
                }
                container.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
            }
        };
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0, true);
        viewPager.addOnPageChangeListener(this);
        carray[po].setChecked(true);
        handler.sendEmptyMessageDelayed(0, 4000);
    }

    public void reMoverHandler() {
        if (handler != null) {
            handler.removeMessages(0);
            handler = null;
        }
    }

    private void addView(Activity context, LinearLayout indicator, int i) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        getImage(imageView, i);
        mImageViews.add(imageView);
        CheckedTextView c = new CheckedTextView(context);
        c.setBackgroundResource(RID);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Utils.dip2px(context,10),Utils.dip2px(context,10));
        lp.setMargins(6, 0, 6, 0);
        c.setLayoutParams(lp);
        indicator.addView(c);
        carray[i] = c;
    }

    private void getImage(ImageView imageView, final int position) {
        ToGlide.url(context,imageurl+list.get(position),imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClickListener(position);
            }
        });
    }

    public interface CallBack {
        void onClickListener(int position);
    }

    private int po;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        index = position;
        carray[po].setChecked(false);
        po = position % list.size();
        carray[po].setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                isuserMover = true;
                scroller.setmDuration(100);
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                scroller.setmDuration(800);
                isuserMover = false;
                break;
        }
    }

    private class MyFixedSpeedScroller extends Scroller {
        private int mDuration = 1000; // default time is 1500ms

        public MyFixedSpeedScroller(Context context) {
            super(context);
        }

        public MyFixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }
    }

}
