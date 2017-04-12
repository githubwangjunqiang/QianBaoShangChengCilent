package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.CookStorInfoShopAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.StorPingjiaAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.CookStorInfoShop;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorPingjia;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ToShopinfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.FoodModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.StorModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyListview;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.ListViewListener;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Utils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.yunyouzhiyuan.qianbaoshangchengclient.R.id.cookstor_ivback;

public class CookStorinfoActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.cookstor_tvname)
    TextView tvname;
    @Bind(R.id.cookstor_rating)
    RatingBar ratingBar;
    @Bind(R.id.cookstor_tvfenshu)
    TextView tvfenshu;
    @Bind(R.id.cookstor_tvaddress)
    TextView tvaddress;
    @Bind(R.id.cookstor_gvshop)
    MyListview gvShop;
    @Bind(R.id.cookstor_tvpingjiafenshu)
    TextView tvpingfenshu;
    @Bind(R.id.cookstor_tvpingjiabar)
    RatingBar ratingBarPing;
    @Bind(R.id.cookstor_tvpinggaoyu)
    TextView tvPingjiaGao;
    @Bind(R.id.cookstor_tvpingpinglv)
    TextView tvHaopinglv;
    @Bind(R.id.cookstor_gvpingjia)
    MyListview gvPingjia;
    @Bind(cookstor_ivback)
    ImageView cookstorIvback;
    @Bind(R.id.cookstor_ivdinahua)
    ImageView cookstorIvdinahua;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.sw_layout)
    SwipeRefreshLayout swLayout;
    @Bind(R.id.cookstor_ivlogo)
    ImageView ivLogo;
    private StorModel model;
    private StorInfo.DataBean data;
    private List<CookStorInfoShop.DataBean> listShop = new ArrayList<>();
    private CookStorInfoShopAdapter adapterShop;
    private FoodModel PingModel;
    private int page = 0;
    private StorPingjiaAdapter adapter;
    private List<StorPingjia.DataBeanX.DataBean> list = new ArrayList<>();
    private boolean islooding = true;
    private int boorhight = 0;
    private boolean isFist = true;

    @Override
    protected void onDestroy() {
        list.clear();
        listShop.clear();
        adapterShop = null;
        adapter = null;
        PingModel = null;
        model = null;
        data = null;
        super.onDestroy();
    }

    public static void startCookStorinfoActivity(Context context, String stor_id) {
        Intent intent = new Intent(context, CookStorinfoActivity.class);
        intent.putExtra("stor_id", stor_id);
        context.startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && boorhight != 0 && isFist) {
            isFist = false;
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.height = (int) (getResources().getDimension(R.dimen.pading_46) + boorhight);
                toolbar.setLayoutParams(layoutParams);
                toolbar.setPadding(Utils.dip2px(App.getContext(), 10), boorhight, Utils.dip2px(App.getContext(), 10), 0);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_storinfo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            getWindow().setAttributes(localLayoutParams);
            boorhight = getStatusBarHeight();
        }
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (TextUtils.isEmpty(getIntent().getStringExtra("stor_id"))) {
            To.oo("本地传参出现问题");
            finish();
            return;
        }
        if (TextUtils.isEmpty(App.getUserId())) {
            To.oo("请先登录");
            finish();
            return;
        }


        init();
        setView();
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                swLayout.setEnabled(verticalOffset >= 0);
            }
        });
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                setView();
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        swLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.white));
        swLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.app_color));
        model = new StorModel();
        PingModel = new FoodModel();

    }

    private int count;

    private void hideLayout() {
        count--;
        if (count < 1) {
            count = 0;
            swLayout.setRefreshing(false);
        }
    }

    /**
     * 获取信息 写入信息
     */
    private void setView() {
        swLayout.setRefreshing(true);
        count = 3;
        getTopStorinfo();
        getShop();
        getPinglun();
    }

    /**
     * 获取评论
     */
    private void getPinglun() {
        Call stor_id = PingModel.getPingList(getIntent().getStringExtra("stor_id"), page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                if (page == 0) {
                    list.clear();
                }
                list.addAll(((StorPingjia.DataBeanX) obj).getData());
                setPingadapter();
                setPingView((StorPingjia.DataBeanX) obj);
                page++;
                islooding = true;
                hideLayout();
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.oo(obj);
                if (page == 0) {
                    list.clear();
                    setPingadapter();
                }
                hideLayout();
            }
        });
        setCall(stor_id);
    }

    /**
     * 写入评分汇信息
     *
     * @param obj
     */
    private void setPingView(StorPingjia.DataBeanX obj) {
        BigDecimal bg = new BigDecimal(Double.parseDouble(obj.getAvgcomment()));
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        tvpingfenshu.setText(f1 + "");
        BigDecimal bg1 = new BigDecimal(obj.getGrate());
        double f11 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        tvHaopinglv.setText("好评率" + f11);
        ratingBarPing.setRating(Float.parseFloat(obj.getAvgcomment() + ""));
        tvPingjiaGao.setText("高于一般的同行");
    }

    /**
     * 设置评论adapter
     */
    private void setPingadapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new StorPingjiaAdapter(this, list);
            gvPingjia.setAdapter(adapter);
            gvPingjia.setOnScrollListener(new ListViewListener() {
                @Override
                public void bottom() {
                    if (islooding) {
                        getPinglun();
                        islooding = false;
                    }
                }
            });
        }
    }

    /**
     * 获取店家商品
     */
    private void getShop() {
        Call call = model.getCookBottom(getIntent().getStringExtra("stor_id"), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                listShop.clear();
                listShop.addAll((List<CookStorInfoShop.DataBean>) obj);
                setShopAdapter();
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
        setCall(call);
    }

    /**
     * 设置商品列表的适配器
     */
    private void setShopAdapter() {
        if (adapterShop != null) {
            adapterShop.notifyDataSetChanged();
        } else {
            adapterShop = new CookStorInfoShopAdapter(this, listShop);
            gvShop.setAdapter(adapterShop);
            gvShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int psition, long id) {
                    if (null == data) {
                        To.oo("店家信息获取失败，请重试");
                        return;
                    }
                    CookStorInfoShop.DataBean dataBean = listShop.get(psition);
                    ToShopinfo toShopinfo = new ToShopinfo();
                    toShopinfo.setImageUrl(dataBean.getOriginal_img());
                    toShopinfo.setGoodsId(dataBean.getGoods_id());
                    toShopinfo.setSalesSum(dataBean.getSales_sum());
                    toShopinfo.setShopNmae(dataBean.getGoods_name());
                    toShopinfo.setShopPingFen(dataBean.getCommentsum());
                    toShopinfo.setShopPrice(dataBean.getShop_price());
                    toShopinfo.setStorType("美食");
                    toShopinfo.setStorId(data.getStore_id());
                    toShopinfo.setStorName(data.getStore_name());
                    ShopInfoActivity.startShopInfoActivity(CookStorinfoActivity.this, toShopinfo);
                }
            });
        }
    }

    /**
     * 获取店家详情
     */
    private void getTopStorinfo() {
        Call stor_id = model.getStorInfo(getIntent().getStringExtra("stor_id"), App.getUserId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                data = ((StorInfo) obj).getData();
                setStorinfoView();
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
        setCall(stor_id);
    }

    /**
     * 写入店铺信息
     */
    private void setStorinfoView() {
        ToGlide.url(this, HTTPURL.IMAGE + data.getStore_logo(), ivLogo);
        tvname.setText(data.getStore_name() + "");
        ratingBar.setRating(Float.parseFloat(data.getStore_desccredit() + ""));
        tvfenshu.setText(data.getStore_desccredit() + "");
        tvaddress.setText(data.getLocation() + "");
        toolbarLayout.setTitle(data.getStore_name());
    }

    @OnClick({cookstor_ivback, R.id.cookstor_tvaddress, R.id.cookstor_ivdinahua, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case cookstor_ivback://返回
                finish();
                break;
            case R.id.cookstor_tvaddress://地址
                if (data != null) {
                    BaiduMapActivity.startBaiduMapActivity(this, data.getLat(), data.getLng());
                }
                break;
            case R.id.cookstor_ivdinahua://打电话
                if (data != null) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + data.getStore_phone()));
                    startActivity(intent);
                }
                break;
            case R.id.fab://更多图片
                To.oo("没有更多图片");
                break;
        }
    }

}
