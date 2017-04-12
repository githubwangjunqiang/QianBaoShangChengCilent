package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.TianActivityRecyleAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.RecyleViewButtom;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TianTianActivity extends BaseActivity {
    @Bind(R.id.tian_title)
    TitleLayout tianTitle;
    @Bind(R.id.tian_recyleview)
    RecyclerView tianRecyleview;
    @Bind(R.id.tian_layout)
    SwipeRefreshLayout tianLayout;
    private TianActivityRecyleAdapter adapter;

    @Override
    protected void onDestroy() {
        if (adapter != null) {
            adapter = null;
        }
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tian_tian);
        ButterKnife.bind(this);
        init();
        tianLayout.setRefreshing(true);
        setAdapter();
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        tianLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setAdapter();
            }
        });
    }

    /**
     * 写入数据
     */
    private List<String> strings = new ArrayList<>();

    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            strings.add("");
            adapter = new TianActivityRecyleAdapter(this, strings);
            tianRecyleview.setLayoutManager(new LinearLayoutManager(this));
            tianRecyleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            tianRecyleview.setItemAnimator(new DefaultItemAnimator());
            tianRecyleview.setAdapter(adapter);
            tianRecyleview.addOnScrollListener(new RecyleViewButtom(new RecyleViewButtom.Listener(){
                @Override
                public void last(int position) {
                    strings.add("");
                    adapter.notifyItemChanged(strings.size());
                }
            }));
        }
        tianLayout.setRefreshing(false);
    }

    /**
     * 初始化
     */
    private void init() {
        tianLayout.setColorSchemeResources(R.color.white);
        tianLayout.setProgressBackgroundColorSchemeResource(R.color.app_color);
        tianTitle.setCallback(new TitleLayout.Callback(this));
        tianTitle.setTitle("天天美食", true);
    }
}
