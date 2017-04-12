package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.HappyGv0Adapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.HappyGv1Adapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.HappyGv2Adapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.HappyGv3Adapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.HappyGv4Adapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.HappyRecyleAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_HuoDong;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.TuiJian;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ZiFenlei;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.FoodModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.HappyModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.StorModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.ZiFenleiModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyGridView;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.RecyleViewButtom;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HappyActivity extends BaseActivity {

    @Bind(R.id.happy_title)
    TitleLayout happyTitle;
    @Bind(R.id.happy_gv0)
    GridView happyGv0;
    @Bind(R.id.happy_gv1)
    MyGridView happyGv1;
    @Bind(R.id.happy_gv2)
    GridView happyGv2;
    @Bind(R.id.happy_gv3)
    GridView happyGv3;
    @Bind(R.id.happy_gv4)
    GridView happyGv4;
    @Bind(R.id.happy_appbar)
    AppBarLayout happyAppbar;
    @Bind(R.id.happy_recyle)
    RecyclerView happyRecyle;
    @Bind(R.id.happy_layout)
    SwipeRefreshLayout happyLayout;
    private HappyGv0Adapter happyGv0Adapter;//顶部第一个 gridview 适配器
    private HappyGv1Adapter happyGv1Adapter;//顶部第二个 gridview 适配器
    private HappyGv2Adapter happyGv2Adapter;//顶部第三个 gridview 适配器
    private HappyGv3Adapter happyGv3Adapter;//顶部第四个 gridview 适配器
    private HappyGv4Adapter happyGv4Adapter;//顶部第五个 gridview 适配器
    private HappyRecyleAdapter happyRecyleAdapter;//recyleview 的适配器
    private int page;
    private List<ZiFenlei.DataBean> list = new ArrayList<>();
    private List<Food_Bottom.DataBean> list_bottom = new ArrayList<>();
    private boolean isloading = true;
    private List<Home_HuoDong.DataBean> listHuoDong = new ArrayList<>();

    public static void startHappyActivity(Context context, String sc_id) {
        Intent intent = new Intent(context, HappyActivity.class);
        intent.putExtra("sc_id", sc_id);
        context.startActivity(intent);
    }

    private HappyModel model;

    @Override
    protected void onDestroy() {
        listHuoDong.clear();
        list_bottom.clear();
        list.clear();
        list = null;
        list_bottom = null;
        listHuoDong = null;
        happyGv0Adapter = null;
        happyRecyleAdapter = null;
        happyGv1Adapter = null;
        happyGv2Adapter = null;
        happyGv3Adapter = null;
        happyGv4Adapter = null;
        model = null;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happy);
        ButterKnife.bind(this);
        if (getIntent().getStringExtra("sc_id").isEmpty()) {
            To.oo("本地传参有误 请刷新界面");
            finish();
            return;
        }
        init();
        setListener();
        setView();
    }

    /**
     * 初始化
     */
    private void init() {
        happyTitle.setCallback(new TitleLayout.Callback(this));
        happyLayout.setProgressBackgroundColorSchemeResource(R.color.app_color);
        happyLayout.setColorSchemeResources(R.color.white);
        model = new HappyModel();
    }

    /**
     * 监听器
     */
    private void setListener() {
        happyAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                happyLayout.setEnabled(verticalOffset >= 0);
            }
        });
        happyLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setView();
                happyLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 写入信息
     */
    private void setView() {
        count = 3;
        page = 0;
        getGv0();
        setGv1();
        getGv4();
        getBottom();
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
                setHuoDong();
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
     * 写入活动数据
     */
    private void setHuoDong() {
        setGv2();
        setGv3();
    }

    /**
     * 获取推荐商家
     */
    private void getGv4() {
        if (Bean.city_id_id == null) {
            hideLayout();
            return;
        }
        setCall(new StorModel().getTuiJian(getIntent().getStringExtra("sc_id"),
                Bean.city_id_id, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                setGv4((List<TuiJian.DataBean>) obj);
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
     * 获取菜单 gridview
     */
    private void getGv0() {
        setCall(new ZiFenleiModel().getTop(getIntent().getStringExtra("sc_id"),
                new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                list.clear();
                list.addAll(((ZiFenlei) obj).getData());
                if (list.size() > 4) {
                    setGv0();
                    setGv1();
                }
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
     * 关闭刷新layout
     */
    private int count = 0;

    private void hideLayout() {
        count--;
        if (count < 1) {
            count = 0;
            happyLayout.setRefreshing(false);
        }
    }

    /**
     * 获取底部数据
     */
    private void getBottom() {
        if (BaiduMapBean.getLocation() == null) {
            To.oo("亲定位还没成功，稍后呈现数据");
            hideLayout();
            return;
        }
        if (TextUtils.isEmpty(Bean.city_id_id)) {
            hideLayout();
            return;
        }
        new FoodModel().nearByStore(getIntent().getStringExtra("sc_id"),
                BaiduMapBean.getLocation().getLongitude() + "",
                BaiduMapBean.getLocation().getLatitude() + "",
                page, Bean.city_id_id, new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        if (page == 0) {
                            list_bottom.clear();
                        }
                        list_bottom.addAll(((Food_Bottom) obj).getData());
                        setRecyle();
                        page++;
                        hideLayout();
                        isloading = true;
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        if (page == 0) {
                            To.oo(obj);
                            list_bottom.clear();
                            setRecyle();
                            isloading = true;
                        }
                        hideLayout();

                    }
                });
    }

    /**
     * 底部recyle 的数据写入
     */
    private void setRecyle() {
        if (happyRecyleAdapter != null) {
            happyRecyleAdapter.notifyDataSetChanged();
        } else {
            happyRecyleAdapter = new HappyRecyleAdapter(this, list_bottom);
            happyRecyle.setLayoutManager(new LinearLayoutManager(this));
            happyRecyle.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));
            happyRecyle.setItemAnimator(new DefaultItemAnimator());
            happyRecyle.setAdapter(happyRecyleAdapter);
            happyRecyle.addOnScrollListener(new RecyleViewButtom(new RecyleViewButtom.Listener() {
                @Override
                public void last(int position) {
                    if (isloading) {
                        isloading = false;
                        getBottom();
                    }
                }
            }));
        }

    }

    /**
     * 顶部第五个 gridview 适配器
     *
     * @param obj
     */
    private void setGv4(List<TuiJian.DataBean> obj) {
        if (happyGv4Adapter != null) {
            happyGv4Adapter.notifyDataSetChanged();
        } else {
            happyGv4Adapter = new HappyGv4Adapter(this, obj);
            happyGv4.setAdapter(happyGv4Adapter);
        }
    }

    /**
     * 顶部第四个 gridview 适配器
     */
    private void setGv3() {
        if (happyGv3Adapter != null) {
            happyGv3Adapter.notifyDataSetChanged();
        } else {
            happyGv3Adapter = new HappyGv3Adapter(this, listHuoDong);
            happyGv3.setAdapter(happyGv3Adapter);
            happyGv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WebViewHuoDongActivity.startWebViewHuoDongActivity(HappyActivity.this,
                            listHuoDong.get(position + 3).getAd_link());
                }
            });
        }
    }

    /**
     * 顶部第三个 gridview 适配器
     */
    private void setGv2() {
        if (happyGv2Adapter != null) {
            happyGv2Adapter.notifyDataSetChanged();
        } else {
            happyGv2Adapter = new HappyGv2Adapter(this, listHuoDong);
            happyGv2.setAdapter(happyGv2Adapter);
            happyGv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WebViewHuoDongActivity.startWebViewHuoDongActivity(HappyActivity.this,
                            listHuoDong.get(position).getAd_link());
                }
            });
        }
    }

    /**
     * 顶部第二 个 gridview 适配器
     */
    private void setGv1() {
        if (happyGv1Adapter != null) {
            happyGv1Adapter.notifyDataSetChanged();
        } else {
            happyGv1Adapter = new HappyGv1Adapter(this, list, getIntent().getStringExtra("sc_id"));
            happyGv1.setAdapter(happyGv1Adapter);
        }
    }

    /**
     * 顶部第一个 gridview 适配器
     */
    private void setGv0() {
        if (happyGv0Adapter != null) {
            happyGv0Adapter.notifyDataSetChanged();
        } else {
            happyGv0Adapter = new HappyGv0Adapter(this, list, getIntent().getStringExtra("sc_id"));
            happyGv0.setAdapter(happyGv0Adapter);
        }
    }

    @Override
    public void upLocation(BDLocation location) {
        if (happyRecyleAdapter != null) {
            getBottom();
        }
    }

    @Override
    public void upCity() {
        getBottom();
        getGv4();
    }
}
