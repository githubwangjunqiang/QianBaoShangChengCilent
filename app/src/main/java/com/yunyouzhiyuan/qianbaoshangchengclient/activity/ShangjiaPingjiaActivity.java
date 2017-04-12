package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.StorPingjiaAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorPingjia;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.FoodModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.ScrollListView;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.ListViewListener;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ShangjiaPingjiaActivity extends BaseActivity {

    @Bind(R.id.ping_title)
    TitleLayout pingTitle;
    @Bind(R.id.ping_listview)
    ScrollListView pingListview;
    @Bind(R.id.ping_layout)
    SwipeRefreshLayout layout;
    private FoodModel model;
    private int page = 0;
    private StorPingjiaAdapter adapter;
    private List<StorPingjia.DataBeanX.DataBean> list = new ArrayList<>();
    private boolean idlooding = true;

    @Override
    protected void onDestroy() {
        list.clear();
        list = null;
        adapter = null;
        model = null;
        super.onDestroy();
    }

    /**
     * qidongactivity
     *
     * @param context
     * @param storId
     */
    public static void startShangjiaPingjiaActivity(Context context, String storId) {
        Intent intent = new Intent(context, ShangjiaPingjiaActivity.class);
        intent.putExtra("stor_id", storId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangjia_pingjia);
        ButterKnife.bind(this);
        if (getIntent().getStringExtra("stor_id") == null) {
            To.oo("本地传参错误");
            finish();
            return;
        }
        init();
        setListener();
        getdata();
        setadapter();
    }

    /**
     * 设置适配器
     */
    private void setadapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new StorPingjiaAdapter(this, list);
            pingListview.setAdapter(adapter);
        }
    }

    /**
     * 获取信息
     */
    private void getdata() {
        layout.setRefreshing(true);
        Call stor_id = model.getPingList(getIntent().getStringExtra("stor_id"), page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                if (page == 0) {
                    list.clear();
                }
                list.addAll(((StorPingjia.DataBeanX) obj).getData());
                setadapter();
                page++;
                layout.setRefreshing(false);
                idlooding = true;
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.oo(obj);
                if (page == 0) {
                    if (list != null) {
                        list.clear();
                        setadapter();
                        pingListview.setEmptyView(findViewById(R.id.ping_tve));
                    }
                }
                layout.setRefreshing(false);

            }
        });
        setCall(stor_id);
    }

    /**
     * 监听器
     */
    private void setListener() {
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                getdata();
            }
        });
        pingListview.setOnScrollListener(new ListViewListener() {
            @Override
            public void bottom() {
                if (idlooding) {
                    idlooding = false;
                    getdata();
                }
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        layout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.app_color));
        layout.setColorSchemeResources(R.color.white);
        pingTitle.setTitle(getString(R.string.pinglunlist), true);
        pingTitle.setCallback(new TitleLayout.Callback(this));
        model = new FoodModel();
    }
}
