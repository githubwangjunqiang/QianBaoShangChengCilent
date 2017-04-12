package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.zoomtoTouchImage.ExtendedViewPager;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.zoomtoTouchImage.TouchImageView;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/8/15 0015.
 */
public class TapViewpagerActivity extends BaseActivity {
    private ExtendedViewPager vp;
    private Adapter adapter;
    private TextView tvnumber;
    private List<String> paths = new ArrayList<>();
    private List<Uri> uris = new ArrayList<>();
    private int index;
    private boolean uri;

    @Override
    protected void onDestroy() {
        paths.clear();
        uris.clear();
        uris = null;
        paths = null;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tapviewpager);
        uri = getIntent().getBooleanExtra("uri", false);
        if (uri) {
            uris = (List<Uri>) getIntent().getSerializableExtra("data");
        } else {
            paths = (List<String>) getIntent().getSerializableExtra("data");
        }

        index = getIntent().getIntExtra("index", 0);
        tvnumber = (TextView) findViewById(R.id.tvnumber);
        vp = (ExtendedViewPager) findViewById(R.id.tapactivity_vp);
        getdate();
    }

    /**
     * 启动这个activity
     *
     * @param list
     * @param inde
     */
    public static void startActivity(Context context, List<String> list, int inde) {
        Intent intent = new Intent(context, TapViewpagerActivity.class);
        intent.putExtra("data", (Serializable) list);
        intent.putExtra("index", inde);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
        }
    }

    /**
     * 启动这个activity
     *
     * @param list
     * @param inde
     */
    public static void startActivityUril(Context context, List<Uri> list, int inde) {
        Intent intent = new Intent(context, TapViewpagerActivity.class);
        intent.putExtra("data", (Serializable) list);
        intent.putExtra("index", inde);
        intent.putExtra("uri", true);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.alpha_scale_in0, R.anim.alpha_scale_out0);
        }
    }

    private void getdate() {
        adapter = new Adapter();
        vp.setAdapter(adapter);
        vp.setCurrentItem(index);
        if (uri) {
            tvnumber.setText((index == 0 ? 1 : index + 1) + "/" + uris.size());
        } else {
            tvnumber.setText((index == 0 ? 1 : index + 1) + "/" + paths.size());
        }
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (uri) {
                    if (position > uris.size()) {
                        position = 1;
                    }
                    tvnumber.setText(position + 1 + "/" + uris.size());
                } else {
                    if (position > paths.size()) {
                        position = 1;
                    }
                    tvnumber.setText(position + 1 + "/" + paths.size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void getbitmap(final ImageView im, int i) {
        if (uri) {
            ToGlide.url(this, uris.get(i), im);
        } else {
            ToGlide.url(this, HTTPURL.IMAGE + paths.get(i), im);
        }
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
            }
        });
    }

    private class Adapter extends PagerAdapter {


        @Override
        public int getCount() {
            if (uri) {
                return uris.size();
            } else {
                return paths.size();
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TouchImageView view = new TouchImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            getbitmap(view, position);
            container.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //返回事件
            finish();
            overridePendingTransition(R.anim.alpha_scale_in1, R.anim.alpha_scale_out1);
            return true;
        }
        return false;
    }
}
