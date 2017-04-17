package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.StorListAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.FoodModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayoutDirection;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StorListActivity extends BaseActivity {

    @Bind(R.id.storlist_title)
    TitleLayout storlistTitle;
    @Bind(R.id.storlist_recyle)
    RecyclerView storlistRecyle;
    @Bind(R.id.storlist_layout)
    SwipyRefreshLayout storlistLayout;
    private StorListAdapter adapter;
    private FoodModel model;
    private String scid;
    private int page;
    private List<Food_Bottom.DataBean> list = new ArrayList<>();

    @Override
    protected void onDestroy() {
        adapter = null;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stor_list);
        ButterKnife.bind(this);
        String s = getIntent().getStringExtra("title");
        scid = getIntent().getStringExtra("scid");
        if (TextUtils.isEmpty(s) || TextUtils.isEmpty(scid)) {
            To.oo("本地传参错误");
            finish();
            return;
        }
        init(s);
        setListener();
        getData();
    }

    /**
     * 获取信息
     */
    private void getData() {
        if (!storlistLayout.isRefreshing()) {
            storlistLayout.setRefreshing(true);
        }
        if (BaiduMapBean.getLocation() == null || TextUtils.isEmpty(Bean.city_id_id)) {
            To.ee("稍后重试，您的手机尚未定位");
            storlistLayout.setRefreshing(false);
            return;
        }

        setCall(model.nearByStore(scid, BaiduMapBean.getLocation().getLongitude() + "",
                BaiduMapBean.getLocation().getLatitude() + "", page, Bean.city_id_id,
                new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        if (page == 0) {
                            list.clear();
                        }
                        list.addAll(((Food_Bottom) obj).getData());
                        setAdapter();
                        page++;
                        storlistLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        if (page == 0) {
                            list.clear();
                            setAdapter();
                            To.ee(obj);
                        }
                        storlistLayout.setRefreshing(false);
                    }
                }));
    }

    /**
     * 监听器
     */
    private void setListener() {
        storlistLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    page = 0;
                    getData();
                } else {
                    getData();
                }
            }
        });
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new StorListAdapter(this, list);
            storlistRecyle.setLayoutManager(new LinearLayoutManager(this));
            storlistRecyle.setAdapter(adapter);
            storlistRecyle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            storlistRecyle.setItemAnimator(new DefaultItemAnimator());
        }
    }

    /**
     * 初始化
     */
    private void init(String title) {
        storlistTitle.setTitle(title, true);
        storlistTitle.setCallback(new TitleLayout.Callback(this));
        storlistLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.white));
        storlistLayout.setProgressBackgroundColor(R.color.app_color);
        model = new FoodModel();
    }

    public static void startActivity(Context context, String title, String scid) {
        Intent intent = new Intent(context, StorListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("scid", scid);
        context.startActivity(intent);
    }

    @Override
    public void upLocation(BDLocation location) {
        if (adapter == null) {
            getData();
        }
    }
}
