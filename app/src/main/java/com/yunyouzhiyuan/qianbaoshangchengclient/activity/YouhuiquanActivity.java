package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.YouhuiquanAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Youhuiquan;
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

public class YouhuiquanActivity extends BaseActivity {

    @Bind(R.id.youihuiquan_title)
    TitleLayout youihuiquanTitle;
    @Bind(R.id.youihuiquan_recyleview)
    RecyclerView youihuiquanRecyleview;
    @Bind(R.id.youihuiquan_layout)
    SwipyRefreshLayout youihuiquanLayout;
    @Bind(R.id.yoiuhuiquan_rb0)
    RadioButton yoiuhuiquanRb0;
    @Bind(R.id.yoiuhuiquan_rb1)
    RadioButton yoiuhuiquanRb1;
    @Bind(R.id.yoiuhuiquan_rb2)
    RadioButton yoiuhuiquanRb2;
    @Bind(R.id.yoiuhuiquan_rg)
    RadioGroup yoiuhuiquanRg;
    private YouhuiquanAdapter adapter;
    private YouhuiquanModel model;
    private int type = 0;// 优惠券类型（0 未使用  1已使用 2已过期）
    private int page = 0;
    private List<Youhuiquan.DataBean> list = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuiquan);
        ButterKnife.bind(this);
        init();
        setListener();
        setAdapter();
        getDate();
    }

    /**
     * 监听器
     */
    private void setListener() {

        youihuiquanLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    page = 0;
                    getDate();
                } else {
                    getDate();
                }
            }
        });
        yoiuhuiquanRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.yoiuhuiquan_rb0://未使用
                        type = 0;
                        break;
                    case R.id.yoiuhuiquan_rb1://已使用
                        type = 1;
                        break;
                    case R.id.yoiuhuiquan_rb2://已过期
                        type = 2;
                        break;
                }
                page = 0;
                getDate();
            }
        });

    }




    /**
     * 写入数据
     */
    private void getDate() {
        if (!youihuiquanLayout.isRefreshing()) {
            youihuiquanLayout.setRefreshing(true);
        }
        Call getdata = model.getdata(App.getUserId(), type, page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(final Object obj) {
                if (isFinishing()) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (youihuiquanLayout.isRefreshing()) {
                            youihuiquanLayout.setRefreshing(false);
                        }
                        List<Youhuiquan.DataBean> data = ((Youhuiquan) obj).getData();
                        if (page == 0) {
                            adapter.setDate(data, type);
                        } else {
                            adapter.addDate(data, type);
                        }
                        page++;
                    }
                });
            }

            @Override
            public void onError(final Object obj) {
                if (isFinishing()) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        To.oo(obj);
                        if (youihuiquanLayout.isRefreshing()) {
                            youihuiquanLayout.setRefreshing(false);
                        }
                        if (page == 0) {
                            adapter.setDate(list, type);
                        }
                    }
                });
            }
        });
        setCall(getdata);
    }

    /**
     * 设置适配器
     */

    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new YouhuiquanAdapter(this, list);
            youihuiquanRecyleview.setAdapter(adapter);
            youihuiquanRecyleview.setLayoutManager(new LinearLayoutManager(this));
            youihuiquanRecyleview.setItemAnimator(new DefaultItemAnimator());
        }
    }

    /**
     * 初始化
     */
    private void init() {
        youihuiquanTitle.setTitle(getString(R.string.youhuiquan), true);
        youihuiquanTitle.setCallback(new TitleLayout.Callback(this));
        youihuiquanLayout.setColorSchemeResources(R.color.white);
        youihuiquanLayout.setProgressBackgroundColor(R.color.app_color);
        model = new YouhuiquanModel();
    }
}
