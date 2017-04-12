package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hr.nipuream.NRecyclerView.view.NRecyclerView;
import com.hr.nipuream.NRecyclerView.view.base.BaseLayout;
import com.hr.nipuream.NRecyclerView.view.impl.RefreshView2;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.EvaluateListActivityAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Pingjia;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.EvaluateModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EvaluateListActivity extends BaseActivity {

    @Bind(R.id.evaluate_title)
    TitleLayout evaluateTitle;
    @Bind(R.id.recyclerMagicView)
    NRecyclerView recyclerMagicView;
    //    @Bind(R.id.recyclerMagicView2)
//    RecyclerView recyclerMagicView2;
    private List<Pingjia.DataBean> list = new ArrayList<>();
    private EvaluateListActivityAdapter adapter;
    private EvaluateModel model;
    private LoadingDialog loadingDialog;
    private int page;


    @Override
    protected void onDestroy() {
        evaluateTitle = null;
        model = null;
        loadingDialog = null;
        adapter = null;
        adapter = null;
        list.clear();
        list = null;
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_list);
        ButterKnife.bind(this);
        if (TextUtils.isEmpty(App.getUserId())) {
            To.ee("您还没登陆");
            finish();
            return;
        }
        init();
        getData();
        setListener();
    }

    /**
     * 获取信息
     */
    private void getData() {
        setCall(model.getData(App.getUserId(), page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                if (page == 0) {
                    list.clear();
                    recyclerMagicView.endRefresh();
                } else {
                    recyclerMagicView.endLoadingMore();
                }
                list.addAll((List<Pingjia.DataBean>) obj);
                setAdapter();
                page++;

                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                recyclerMagicView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                if (page == 0) {
                    list.clear();
                    To.ee(obj);
                    setAdapter();
                    recyclerMagicView.endRefresh();
//                    recyclerMagicView.setPullLoadEnable(false);
//                    ViewGroup errorView = (ViewGroup) LayoutInflater.from(EvaluateListActivity.this).inflate(R.layout.load_error,(ViewGroup) findViewById(android.R.id.content),false);
//                    recyclerMagicView.setErrorView(errorView,false);
                    recyclerMagicView.setVisibility(View.GONE);
                } else {
                    recyclerMagicView.endLoadingMore();
                }
                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }
        }));
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        recyclerMagicView.setOnRefreshAndLoadingListener(new BaseLayout.RefreshAndLoadingListener() {
            @Override
            public void refresh() {
                page = 0;
                getData();
            }

            @Override
            public void load() {
                getData();
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
            adapter = new EvaluateListActivityAdapter(this, list);
            recyclerMagicView.setLayoutManager(new LinearLayoutManager(this));
            recyclerMagicView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST), 2);
            ViewGroup bottomView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.bottom_layout, (ViewGroup) findViewById(android.R.id.content), false);
            recyclerMagicView.setBottomView(bottomView);
            recyclerMagicView.setRefreshView(new RefreshView2(this));
            recyclerMagicView.setAdapter(adapter);
        }
    }

    /**
     * 初始化
     */
    private void init() {
        evaluateTitle.setCallback(new TitleLayout.Callback(this));
        evaluateTitle.setTitle(getResources().getString(R.string.pingjialiebiao), true);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        model = new EvaluateModel();
    }
}
