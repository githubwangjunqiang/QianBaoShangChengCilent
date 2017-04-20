package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.CookActivityButtomAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.CookActivityTopTowAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.CookRecyleViewTopOneAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.TuiJian;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ZiFenlei;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.FoodModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.StorModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.ZiFenleiModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MySwLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.RecyleViewButtom;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class CookActivity extends BaseActivity {

    @Bind(R.id.activity_cook_recview0)
    RecyclerView activityCookRecview0;
    @Bind(R.id.activity_cook_recview1)
    RecyclerView activityCookRecview1;
    @Bind(R.id.activity_cook_recview)
    RecyclerView activityCookRecview;
    @Bind(R.id.activity_cook_tvxuan0)
    TextView activityCookTvxuan0;
    @Bind(R.id.activity_cook_layout)
    MySwLayout layout;
    @Bind(R.id.cook_title)
    TitleLayout titleLayout;
    @Bind(R.id.activity_cook_appbar)
    AppBarLayout appBarLayout;
    private CookRecyleViewTopOneAdapter cookRecyleViewTopOneAdapter;//顶部第一个
    private CookActivityTopTowAdapter cookActivityTopTowAdapter;//顶部第2个
    private CookActivityButtomAdapter cookActivityButtomAdapter;//底部适配器
    private List<ZiFenlei.DataBean> list = new ArrayList<>();
    private int page = 0;
    private List<Food_Bottom.DataBean> list_bottom = new ArrayList<>();
    private boolean isloading = true;

    @Override
    protected void onDestroy() {
        list.clear();
        list_bottom.clear();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    /**
     * 启动activity
     */
    public static void startCookActivity(Context context, String sc_id) {
        Intent intent = new Intent(context, CookActivity.class);
        intent.putExtra("sc_id", sc_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        ButterKnife.bind(this);
        if (TextUtils.isEmpty(getIntent().getStringExtra("sc_id"))) {
            To.oo("分类id为空，请返回重新刷新数据");
            finish();
            return;
        }
        init();
        setdata();
        setListener();

    }

    /**
     * 关闭刷新layout
     */
    private int count = 0;

    private void hideLayout() {
        count--;
        if (count < 1) {
            count = 0;
            layout.setRefreshing(false);
        }
    }

    /**
     * 监听器
     */
    private void setListener() {
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setdata();
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                layout.setEnabled(verticalOffset >= 0);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void init() {
        layout.setProgressBackgroundColorSchemeResource(R.color.app_color);
        layout.setColorSchemeResources(R.color.white);
        titleLayout.setCallback(new TitleLayout.Callback(this));
    }

    /**
     * 写入信息
     */
    private void setdata() {
        layout.setRefreshing(true);
        count = 3;
        page = 0;
        getTopOne();
        getTopTow();
        getBottomData();

    }

    /**
     * 获取推荐的商家
     */
    private void getTopTow() {
        if (Bean.city_id_id == null) {
            hideLayout();
            return;
        }
        setCall(new StorModel().getTuiJian(getIntent().getStringExtra("sc_id"), Bean.city_id_id, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (!isFinishing()) {
                    setTopTow((List<TuiJian.DataBean>) obj);
                    hideLayout();
                }
            }

            @Override
            public void onError(Object obj) {
                if (!isFinishing()) {
                    To.oo(obj);
                    hideLayout();
                }
            }
        }));
    }

    /**
     * 获取底部信息列表
     */
    private void getBottomData() {
        if (BaiduMapBean.getLocation() == null) {
            hideLayout();
            return;
        }
        if (Bean.city_id_id == null) {
            hideLayout();
            return;
        }
        Call call = new FoodModel().nearByStore(getIntent().getStringExtra("sc_id"),
                BaiduMapBean.getLocation().getLongitude() + "",
                BaiduMapBean.getLocation().getLatitude() + "", page,
                Bean.city_id_id, new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (!isFinishing()) {
                            if (page == 0) {
                                list_bottom.clear();
                            }
                            list_bottom.addAll(((Food_Bottom) obj).getData());
                            setbuttomreclyview();
                            page++;
                            hideLayout();
                            isloading = true;
                        }
                    }

                    @Override
                    public void onError(Object obj) {
                        if (!isFinishing()) {
                            if (page == 0) {
                                To.oo(obj);
                                list_bottom.clear();
                                setbuttomreclyview();
                                isloading = true;
                            }
                            hideLayout();
                        }

                    }
                });
        setCall(call);
    }

    /**
     * 获取顶部菜单信息
     */
    private void getTopOne() {
        Call top = new ZiFenleiModel().getTop(getIntent().getStringExtra("sc_id"), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                list.clear();
                list.addAll(((ZiFenlei) obj).getData());
                setTopOne();
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
        });
        setCall(top);
    }

    /**
     * 底部数据
     */

    private void setbuttomreclyview() {
        if (cookActivityButtomAdapter != null) {
            cookActivityButtomAdapter.notifyDataSetChanged();
        } else {
            cookActivityButtomAdapter = new CookActivityButtomAdapter(this, list_bottom, "美食");
            activityCookRecview.setLayoutManager(new LinearLayoutManager(this));
            activityCookRecview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            activityCookRecview.setItemAnimator(new DefaultItemAnimator());
            activityCookRecview.setAdapter(cookActivityButtomAdapter);
            activityCookRecview.addOnScrollListener(new RecyleViewButtom(new RecyleViewButtom.Listener() {
                @Override
                public void last(int position) {
                    if (isloading) {
                        isloading = false;
                        getBottomData();
                    }
                }
            }));
        }
    }

    /**
     * 顶部第2个 recyleview adapter
     */
    private void setTopTow(List<TuiJian.DataBean> list) {
        if (cookActivityTopTowAdapter != null) {
            cookActivityTopTowAdapter.notifyDataSetChanged();
        } else {
            cookActivityTopTowAdapter = new CookActivityTopTowAdapter(this, list, "cook");
            activityCookRecview1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            activityCookRecview1.setItemAnimator(new DefaultItemAnimator());
            activityCookRecview1.setAdapter(cookActivityTopTowAdapter);
        }
    }

    /**
     * 顶部第一个 recyleview adapter
     */
    private void setTopOne() {
        if (cookRecyleViewTopOneAdapter != null) {
            cookRecyleViewTopOneAdapter.notifyDataSetChanged();
        } else {
            cookRecyleViewTopOneAdapter = new CookRecyleViewTopOneAdapter(this, list,
                    getIntent().getStringExtra("sc_id"));
            activityCookRecview0.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false));
            activityCookRecview0.setItemAnimator(new DefaultItemAnimator());
            activityCookRecview0.setAdapter(cookRecyleViewTopOneAdapter);
        }
    }

    @Override
    public void upLocation(BDLocation location) {
        if (cookActivityButtomAdapter == null) {
            getBottomData();
        }
    }

    @Override
    public void upCity() {
        LogUtils.i("观察者美食页接到消息 刷新数据");
        page = 0;
        getBottomData();
        getTopTow();
    }
}
