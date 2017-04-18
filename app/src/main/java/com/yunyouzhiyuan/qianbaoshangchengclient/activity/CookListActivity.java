package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.CooklistAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.CookModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CookListActivity extends BaseActivity {

    @Bind(R.id.cooklist_title)
    TitleLayout title;
    @Bind(R.id.cooklist_recyleview)
    RecyclerView recyclerView;
    @Bind(R.id.refresh)
    MaterialRefreshLayout layout;
    private CookModel model;
    private LoadingDialog loadingDialog;
    private List<Food_Bottom.DataBean> list = new ArrayList<>();
    private int page = 0;
    private CooklistAdapter adapter;

    private String type;

    public static void startCookListActivity(Context context, String sc_id, String class_2, String type) {
        Intent intent = new Intent(context, CookListActivity.class);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("class_2", class_2);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_list_actiovity);
        ButterKnife.bind(this);
        if (TextUtils.isEmpty(getIntent().getStringExtra("sc_id")) ||
                TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            To.ee("本地传参失败");
            finish();
            return;
        }
        init();
        setListener();
        getData();
    }

    private void setListener() {
        layout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...
                page = 0;
                getData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
                getData();
            }
        });
    }

    private void getData() {
        if (BaiduMapBean.getLocation() == null) {
            To.ee("亲你的手机尚未定位成功");
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            return;
        }
        setCall(model.nearByStore(getIntent().getStringExtra("sc_id"),
                getIntent().getStringExtra("class_2"),
                BaiduMapBean.getLocation().getLongitude() + "",
                BaiduMapBean.getLocation().getLatitude() + "",
                page, Bean.city_id_id,
                new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        recyclerView.setVisibility(View.VISIBLE);
                        if (page == 0) {
                            list.clear();
                            layout.finishRefresh();
                        } else {
                            layout.finishRefreshLoadMore();
                        }
                        list.addAll(((Food_Bottom) obj).getData());
                        page++;
                        setAdapter();
                        if (loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        if (page == 0) {
                            To.ee(obj);
                            list.clear();
                            setAdapter();
                            layout.finishRefresh();
                        } else {
                            layout.finishRefreshLoadMore();
                        }
                        if (loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }
                }));
    }

    /**
     * adapter 适配器
     */
    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new CooklistAdapter(this, list, type);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));

        }
    }

    /**
     * 初始化
     */
    private void init() {
        model = new CookModel();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        title.setTitle("列表详情", true);
        title.setCallback(new TitleLayout.Callback(this));
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void upCity() {
        page = 0;
        getData();
    }
}
