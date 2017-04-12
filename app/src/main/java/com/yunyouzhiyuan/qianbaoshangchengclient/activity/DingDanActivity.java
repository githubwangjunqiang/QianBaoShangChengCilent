package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment_dingdan.AllFragment;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment_dingdan.DaizhifuFragment;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment_dingdan.JinXingzhongFragment;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment_dingdan.ReFundFragment;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment_dingdan.YiWanchengragment;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class DingDanActivity extends BaseActivity  {

    @Bind(R.id.diandan_title)
    TitleLayout diandanTitle;
    @Bind(R.id.diandan_tablayout)
    TabLayout diandanTablayout;
    @Bind(R.id.diandan_vp)
    ViewPager diandanVp;
    private List<Fragment> fragments = new ArrayList<>(5);
    private List<String> strings = new ArrayList<>(5);

    @Override
    protected void onDestroy() {
        fragments.clear();
        strings.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_dan);
        ButterKnife.bind(this);
        if (null != getIntent()) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);

                if (TextUtils.isEmpty(content) && TextUtils.isEmpty(App.getUserId())) {
                    To.oo("请登录");
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    return;
                }
            }

        }
        if (TextUtils.isEmpty(App.getUserId())) {
            To.oo("请登录");
            finish();
            return;
        }
        init();
        setadapter();
    }

    public static void startDingdanActivity(Context context, int index) {
        Intent intent = new Intent(context, DingDanActivity.class);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    /**
     * viewpager设置适配器
     */
    private void setadapter() {
        diandanVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return strings.get(position);
            }
        });
        diandanTablayout.setupWithViewPager(diandanVp);
        diandanVp.setOffscreenPageLimit(1);
        diandanVp.setCurrentItem(getIntent().getIntExtra("index", 0));


    }

    /**
     * 初始化
     */
    private void init() {
        diandanTitle.setTitle(getString(R.string.mydingdan), true);
        diandanTitle.setCallback(new TitleLayout.Callback(this));
        strings.add(getString(R.string.quabu));
        strings.add(getString(R.string.daizhifu));
        strings.add(getString(R.string.jinxingzhong));
        strings.add(getString(R.string.yiwancheng));
        strings.add(getString(R.string.tuikuanzdingdan));
        fragments.add(AllFragment.newInstance());
        fragments.add(DaizhifuFragment.newInstance());
        fragments.add(JinXingzhongFragment.newInstance());
        fragments.add(YiWanchengragment.newInstance());
        fragments.add(ReFundFragment.newInstance());
    }

}
