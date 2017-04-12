package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.fragment_dingdan.CallSetListener;
import com.yunyouzhiyuan.qianbaoshangchengclient.framgent_shoucang.FragmentShouCangtor;
import com.yunyouzhiyuan.qianbaoshangchengclient.framgent_shoucang.Fragmentshoucangshop;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ShouCangActivity extends BaseActivity implements CallSetListener{
    @Bind(R.id.top_ivback)
    ImageView ivback;
    @Bind(R.id.shoucang_tablayout)
    TabLayout shoucangTablayout;
    @Bind(R.id.shoucang_vp)
    ViewPager shoucangVp;
    List<Fragment> fragments = new ArrayList<>();
    List<String> listtitle = new ArrayList<>();

    @Override
    protected void onDestroy() {
        fragments.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_cang);
        ButterKnife.bind(this); if (App.getUserId() == null) {
            To.oo("你还没有登陆");
            finish();
            return;
        }

        init();
        setadapter();
    }

    /**
     * 适配器
     */
    private void setadapter() {
        shoucangVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
                return listtitle.get(position);
            }
        });
        shoucangTablayout.setupWithViewPager(shoucangVp);
    }

    /**
     * 初始化
     */
    private void init() {
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fragments.add(Fragmentshoucangshop.newInstance(0));
        fragments.add(FragmentShouCangtor.newInstance(""));
        listtitle.add(getString(R.string.shoucangshop));
        listtitle.add(getString(R.string.shoucangstor));

    }

    @Override
    public void setFragmentCall(Call call) {
        setCall(call);
    }
}
