package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.DingDanYouAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.DingDanYouhuiquan;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.YouhuiquanModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayoutDirection;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class YouhuiquanDingdanActivity extends BaseActivity {

    @Bind(R.id.youhuiquandingdan_title)
    TitleLayout title;
    @Bind(R.id.youihuiquandingdan_recyleview)
    RecyclerView recyleview;
    @Bind(R.id.youhuiquandingdan_layout)
    SwipyRefreshLayout layout;
    private YouhuiquanModel model;
    private List<DingDanYouhuiquan.DataBean> list = new ArrayList<>();
    private DingDanYouAdapter adapter;

    @Override
    protected void onDestroy() {
        model = null;
        list.clear();
        adapter = null;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuiquan_dingdan);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 启动activity  user_id    total_price     store_id
     */
    public static void startYouhuiquanDingdanActivity(Activity context, String user_id, String total_price, String store_id) {
        Intent intent = new Intent(context, YouhuiquanDingdanActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("total_price", total_price);
        intent.putExtra("store_id", store_id);
        context.startActivityForResult(intent, Bean.YOUHUIQUAN);
    }

    /**
     * 初始化
     */
    private void init() {
        final String user_id = getIntent().getStringExtra("user_id");
        final String total_price = getIntent().getStringExtra("total_price");
        final String store_id = getIntent().getStringExtra("store_id");
        if (TextUtils.isEmpty(user_id) || TextUtils.isEmpty(total_price) || TextUtils.isEmpty(store_id)) {
            To.oo("本地传参有误");
            finish();
            return;
        }
        title.setTitle(getString(R.string.youhuiquan), true);
        title.setCallback(new TitleLayout.Callback(this));

        layout.setProgressBackgroundColor(R.color.app_color);
        layout.setColorSchemeColors(R.color.white);
        model = new YouhuiquanModel();
        layout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    getdata(user_id, total_price, store_id);
                }
            }
        });
        getdata(user_id, total_price, store_id);
    }

    /**
     * 获取信息
     *
     * @param user_id
     * @param total_price
     * @param store_id
     */
    private void getdata(String user_id, String total_price, String store_id) {
        layout.setRefreshing(true);
        Call dingdanYouhuiquan = model.getDingdanYouhuiquan(user_id, total_price, store_id, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                list.clear();
                list.addAll((List<DingDanYouhuiquan.DataBean>) obj);
                setadapter();
                layout.setRefreshing(false);
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.oo(obj);
                list.clear();
                setadapter();
                layout.setRefreshing(false);
            }
        });
        setCall(dingdanYouhuiquan);
    }

    /**
     * 设置适配器
     */
    private void setadapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new DingDanYouAdapter(this, list, LayoutInflater.from(this), new DingDanYouAdapter.Callback() {
                @Override
                public void onClick(String id, String name) {
                    Intent intent = getIntent();
                    intent.putExtra("youhuiquan_id", id);
                    intent.putExtra("name", name);
                    setResult(Bean.YOUHUIQUAN, intent);
                    finish();
                }
            });
            recyleview.setLayoutManager(new LinearLayoutManager(this));
            recyleview.setAdapter(adapter);
        }
    }
}
