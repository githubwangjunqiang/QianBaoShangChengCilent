package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.KTVExpanDableAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.KtvGvAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_HuoDong;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.KTVModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyListview_E;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayoutDirection;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class KTVActivity extends BaseActivity {

    @Bind(R.id.ktv_title)
    TitleLayout ktvTitle;
    @Bind(R.id.ktv_ivtop)
    ImageView ktvIvtop;
    @Bind(R.id.ktv_gvtop)
    GridView ktvGvtop;
    @Bind(R.id.ktv_tvtuijian)
    TextView ktvTvtuijian;
    @Bind(R.id.ktv_listview)
    MyListview_E ktvListview;
    @Bind(R.id.ktv_layout)
    SwipyRefreshLayout ktvLayout;
    private KtvGvAdapter gvAdapter;//顶部gv的适配器
    private KTVExpanDableAdapter ktvExpanDableAdapter;//底部数据expanblelistview 的适配器
    private KTVModel model;
    private int page = 0;
    private List<KTV.DataBean> list = new ArrayList<>();
    private LoadingDialog loadingDialog;
    private List<Home_HuoDong.DataBean> listHuoDong = new ArrayList<>();

    public static void startKTVActivity(Context context, String sc_id) {
        Intent intent = new Intent(context, KTVActivity.class);
        intent.putExtra("sc_id", sc_id);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        gvAdapter = null;
        ktvExpanDableAdapter = null;
        listHuoDong.clear();
        listHuoDong = null;
        list.clear();
        list = null;
        model = null;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ktv);
        ButterKnife.bind(this);
        if (TextUtils.isEmpty(getIntent().getStringExtra("sc_id"))) {
            To.oo("本地传参有误");
            finish();
            return;
        }
        init();
        setListener();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        setView();

    }

    /**
     * 初始化
     */
    private void init() {
        ktvLayout.setColorSchemeResources(R.color.white);
        ktvLayout.setProgressBackgroundColor(R.color.app_color);
        model = new KTVModel();
    }

    /**
     * 监听器
     */
    private void setListener() {
        ktvTitle.setCallback(new TitleLayout.Callback(this));
        ktvLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {//顶部刷新
                    page = 0;
                    getListData();
                } else {
                    getListData();
                }
            }
        });
        ktvGvtop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    private int count = 0;

    /**
     * 写入信息
     */
    private void setView() {
        count = 3;
        getTuijian();
        getListData();
        getHuoDong();
    }

    /**
     * 获取活动
     */
    private void getHuoDong() {
        setCall(model.getHuoDong(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                listHuoDong.clear();
                listHuoDong.addAll((List<Home_HuoDong.DataBean>) obj);
                setGv();
                hideLayout();
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.oo(obj);
                hideLayout();
            }
        }));
    }

    /**
     * 获取推荐的商家
     */
    private void getTuijian() {
        hideLayout();
        setGv();
    }

    /**
     * 关闭刷新界面
     */
    private void hideLayout() {
        count--;
        if (count < 1) {
            count = 0;
            loadingDialog.dismiss();
            if (ktvLayout.isRefreshing()) {
                ktvLayout.setRefreshing(false);
            }
        }
    }

    /**
     * 获取ktv店家列表
     */
    private void getListData() {
        BDLocation location = BaiduMapBean.getLocation();
        if (location == null) {
            To.oo("亲手机还没定位成功");
            hideLayout();
            return;
        }
        setCall(model.getList(getIntent().getStringExtra("sc_id"), page, location.getLongitude(),
                location.getLatitude(), new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        if (page == 0) {
                            list.clear();
                        }
                        list.addAll((List<KTV.DataBean>) obj);
                        setElistview();
                        page++;
                        hideLayout();
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        if (page == 0) {
                            To.oo(obj);
                            list.clear();
                            setElistview();
                        }
                        hideLayout();
                    }
                }));

    }

    /**
     * 底部ExpandableListView 填充数据
     */
    private void setElistview() {
        if (ktvExpanDableAdapter != null) {
            ktvExpanDableAdapter.notifyDataSetChanged();
        } else {
            ktvExpanDableAdapter = new KTVExpanDableAdapter(this, list);
            ktvListview.setAdapter(ktvExpanDableAdapter);
            ktvListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                    if (list.get(groupPosition).getGoods_list() != null &&
                            !list.get(groupPosition).getGoods_list().isEmpty()) {
                        KTVStorInfoActivity.startKTVStorInfoActivity(KTVActivity.this,
                                list.get(groupPosition).getStore_id(),
                                list.get(groupPosition).getGoods_list().get(0).getGoods_id()
                                , list.get(groupPosition).getGoods_list());
                    }
                    return true;
                }
            });
        }
        for (int i = 0; i < ktvExpanDableAdapter.getGroupCount(); i++) {
            ktvListview.expandGroup(i);
        }

    }

    /**
     * 顶部gv数据
     */
    private void setGv() {
        if (gvAdapter != null) {
            gvAdapter.notifyDataSetChanged();
        } else {
            gvAdapter = new KtvGvAdapter(this, listHuoDong);
            ktvGvtop.setAdapter(gvAdapter);
            ktvGvtop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WebViewHuoDongActivity.startWebViewHuoDongActivity(KTVActivity.this,
                            listHuoDong.get(position).getAd_link());
                }
            });
        }
    }

    @Override
    public void upLocation(BDLocation location) {
        if (ktvExpanDableAdapter == null) {
            getListData();
        }
    }
}
