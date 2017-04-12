package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.CookActivityButtomAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.CookActivityTopTowAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.FoodRecyleViewTopOneAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bann;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_HuoDong;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.TuiJian;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ZiFenlei;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.FoodModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.StorModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.ZiFenleiModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.Bannerer;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MySwLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.RecyleViewButtom;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class FoodOutActivity extends BaseActivity {

    @Bind(R.id.food_out_titlelayout)
    TitleLayout titleLayout;
    @Bind(R.id.vapager)
    ViewPager vapager;
    @Bind(R.id.rrrrrrlllll)
    LinearLayout rrrrrrlllll;
    @Bind(R.id.food_out_rec_menv)
    RecyclerView foodOutRecMenv;
    @Bind(R.id.food_out_layout)
    MySwLayout layout;
    @Bind(R.id.food_out_appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.food_out_rec_info)
    RecyclerView foodOutRecInfo;
    @Bind(R.id.food_out_tvfujin)
    TextView foodOutTvfujin;
    @Bind(R.id.food_out_xindian_rec_buttom)
    RecyclerView foodOutXindianRecButtom;
    @Bind(R.id.food_out_rl_ivhuodong0)
    ImageView ivHuodong0;
    @Bind(R.id.food_out_rl_ivhuodong1)
    ImageView ivHuodong1;
    @Bind(R.id.food_out_rl_ivhuodong2)
    ImageView ivHuodong2;
    @Bind(R.id.food_out_rl_ivhuodong3)
    ImageView ivHuodong3;
    @Bind(R.id.food_out_rl_ivhuodong4)
    ImageView ivHuodong4;
    private Bannerer bannerer;
    private FoodRecyleViewTopOneAdapter adapterMenv;//菜单recyleview 的适配器
    private CookActivityTopTowAdapter activityTopTowAdapter;//横猾图片的recyleview 的适配器
    private CookActivityButtomAdapter cookActivityButtomAdapter;//底部适配器
    private String sc_id;
    private List<ZiFenlei.DataBean> list = new ArrayList<>();
    private FoodModel model;
    private int page = 0;
    private List<Food_Bottom.DataBean> list_bottom = new ArrayList<>();
    private List<Bann.DataBean> bans = new ArrayList<>();
    private List<Home_HuoDong.DataBean> listHuoDong = new ArrayList<>();

    @Override
    protected void onDestroy() {
        if (bannerer != null) {
            bannerer.reMoverHandler();
            bannerer = null;
        }
        if (adapterMenv != null) {
            adapterMenv = null;
        }
        if (cookActivityButtomAdapter != null) {
            cookActivityButtomAdapter = null;
        }
        if (activityTopTowAdapter != null) {
            activityTopTowAdapter = null;
        }
        listHuoDong.clear();
        listHuoDong = null;
        list.clear();
        list = null;
        list_bottom.clear();
        list_bottom = null;
        super.onDestroy();
    }

    /**
     * 启动activity
     */
    public static void startFoodOutActivity(Context context, String sc_id) {
        Intent intent = new Intent(context, FoodOutActivity.class);
        intent.putExtra("sc_id", sc_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_out);
        ButterKnife.bind(this);
        init();
        setView();
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                layout.setEnabled(verticalOffset >= 0);
            }
        });
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setView();
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        sc_id = getIntent().getStringExtra("sc_id");
        if (TextUtils.isEmpty(sc_id)) {
            To.oo("分类id为空，请返回重新刷新数据");
            finish();
            return;
        }
        layout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.app_color));
        layout.setColorSchemeResources(R.color.white);
        titleLayout.setCallback(new TitleLayout.Callback(this));
        model = new FoodModel();
    }

    /**
     * 写入所有数据
     */
    private void setView() {
        count = 5;
        getHuoDong();
        getBanner();
        getMenu();
        getTuiojian();
        getBottom();
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
        for (int i = 0; i < listHuoDong.size(); i++) {
            switch (i) {
                case 0:
                    ToGlide.url(this, HTTPURL.IMAGE + listHuoDong.get(i).getAd_code(), ivHuodong0);
                    break;
                case 1:
                    ToGlide.url(this, HTTPURL.IMAGE + listHuoDong.get(i).getAd_code(), ivHuodong1);
                    break;
                case 2:
                    ToGlide.url(this, HTTPURL.IMAGE + listHuoDong.get(i).getAd_code(), ivHuodong2);
                    break;
                case 3:
                    ToGlide.url(this, HTTPURL.IMAGE + listHuoDong.get(i).getAd_code(), ivHuodong3);
                    break;
                case 4:
                    ToGlide.url(this, HTTPURL.IMAGE + listHuoDong.get(i).getAd_code(), ivHuodong4);
                    break;
            }
        }
    }

    /**
     * 获取推荐的商家
     */
    private void getTuiojian() {
        if (Bean.city_id_id == null) {
            hideLayout();
            return;
        }
        setCall(new StorModel().getTuiJian(getIntent().getStringExtra("sc_id"), Bean.city_id_id, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                setHenghuaRecview((List<TuiJian.DataBean>) obj);
                hideLayout();
            }

            @Override
            public void onError(Object obj) {
                To.oo(obj);
                hideLayout();
            }
        }));
    }

    /**
     * 获取轮播
     */
    private void getBanner() {
        Call banner = model.getBanner(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                bans.clear();
                bans.addAll((List<Bann.DataBean>) obj);
                setBanner();
                hideLayout();
            }

            @Override
            public void onError(Object obj) {
                To.oo(obj);
                hideLayout();
            }
        });
        setCall(banner);
    }

    /**
     * 获取底部数据
     */
    private void getBottom() {
        if (BaiduMapBean.getLocation() == null) {
            hideLayout();
            return;
        }
        if (Bean.city_id_id == null) {
            hideLayout();
            return;
        }
        Call call = model.nearByStore(sc_id, BaiduMapBean.getLocation().getLongitude() + "",
                BaiduMapBean.getLocation().getLatitude() + "",
                page, Bean.city_id_id, new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (page == 0) {
                            list_bottom.clear();
                        }
                        list_bottom.addAll(((Food_Bottom) obj).getData());
                        setButtomRecview();
                        page++;
                        hideLayout();
                    }

                    @Override
                    public void onError(Object obj) {
                        if (page == 0) {
                            To.oo(obj);
                            list_bottom.clear();
                            setButtomRecview();
                        }
                        hideLayout();
                    }
                });
        setCall(call);
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
     * 获取菜单
     */
    private void getMenu() {
        Call top = new ZiFenleiModel().getTop(sc_id, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                list.clear();
                list.addAll(((ZiFenlei) obj).getData());
                setMenv();
                hideLayout();
            }

            @Override
            public void onError(Object obj) {
                To.oo(obj);
                hideLayout();
            }
        });
        setCall(top);
    }


    /**
     * 设置底部数据集合 附近店家
     */
    private void setButtomRecview() {
        if (cookActivityButtomAdapter != null) {
            cookActivityButtomAdapter.notifyDataSetChanged();
        } else {
            cookActivityButtomAdapter = new CookActivityButtomAdapter(this, list_bottom, "外卖");
            foodOutXindianRecButtom.setLayoutManager(new LinearLayoutManager(this));
            foodOutXindianRecButtom.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            foodOutXindianRecButtom.setItemAnimator(new DefaultItemAnimator());
            foodOutXindianRecButtom.setAdapter(cookActivityButtomAdapter);
            foodOutXindianRecButtom.addOnScrollListener(new RecyleViewButtom(new RecyleViewButtom.Listener() {
                @Override
                public void last(int position) {
                    getBottom();
                }
            }));
        }
    }

    /**
     * 设置横猾的图片 recyleview 的数据
     *
     * @param obj
     */
    private void setHenghuaRecview(List<TuiJian.DataBean> obj) {
        if (activityTopTowAdapter != null) {
            activityTopTowAdapter.notifyDataSetChanged();
        } else {
            activityTopTowAdapter = new CookActivityTopTowAdapter(this, obj, "food");
            foodOutRecInfo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            foodOutRecInfo.setAdapter(activityTopTowAdapter);
        }
    }

    /**
     * 设置菜单 适配器
     */
    private void setMenv() {
        if (adapterMenv != null) {
            adapterMenv.notifyDataSetChanged();
        } else {
            adapterMenv = new FoodRecyleViewTopOneAdapter(this, list, getIntent().getStringExtra("sc_id"));
            foodOutRecMenv.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false));
            foodOutRecMenv.setAdapter(adapterMenv);
        }
    }

    /**
     * 设置轮播图
     */
    private void setBanner() {
        if (bannerer != null) {
            bannerer.reMoverHandler();
            bannerer = null;
        }
        List<String> st = new ArrayList<>();
        for (int i = 0; i < bans.size(); i++) {
            st.add(bans.get(i).getAd_code());
        }
        bannerer = new Bannerer(st, vapager, this, rrrrrrlllll, HTTPURL.IMAGE, R.drawable.banner_c, new Bannerer.CallBack() {
            @Override
            public void onClickListener(int position) {
                WebViewActivity.startWebViewActivity(FoodOutActivity.this, bans.get(position).getAd_link(), null);
            }
        });
    }


    @Override
    public void upLocation(BDLocation location) {
        if (cookActivityButtomAdapter == null) {
            getBottom();
        }
    }

    @Override
    public void upCity() {
        getBottom();
        getTuiojian();
    }

    @OnClick({R.id.food_out_rl_ivhuodong0, R.id.food_out_rl_ivhuodong1, R.id.food_out_rl_ivhuodong2,
            R.id.food_out_rl_ivhuodong3, R.id.food_out_rl_ivhuodong4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.food_out_rl_ivhuodong0://活动1
                if(!listHuoDong.isEmpty()&& listHuoDong.size()>0){
                    WebViewHuoDongActivity.startWebViewHuoDongActivity(this,listHuoDong.get(0)
                            .getAd_link());
                }
                break;
            case R.id.food_out_rl_ivhuodong1://活动2
                if(!listHuoDong.isEmpty()&& listHuoDong.size()>1){
                    WebViewHuoDongActivity.startWebViewHuoDongActivity(this,listHuoDong.get(1)
                            .getAd_link());
                }
                break;
            case R.id.food_out_rl_ivhuodong2://活动3
                if(!listHuoDong.isEmpty()&& listHuoDong.size()>2){
                    WebViewHuoDongActivity.startWebViewHuoDongActivity(this,listHuoDong.get(2)
                            .getAd_link());
                }
                break;
            case R.id.food_out_rl_ivhuodong3://活动4
                if(!listHuoDong.isEmpty()&& listHuoDong.size()>3){
                    WebViewHuoDongActivity.startWebViewHuoDongActivity(this,listHuoDong.get(3)
                            .getAd_link());
                }
                break;
            case R.id.food_out_rl_ivhuodong4://活动5
                if(!listHuoDong.isEmpty()&& listHuoDong.size()>4){
                    WebViewHuoDongActivity.startWebViewHuoDongActivity(this,listHuoDong.get(4)
                            .getAd_link());
                }
                break;
        }
    }
}
