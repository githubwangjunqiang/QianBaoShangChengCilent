package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ToShopinfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.ShopModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyZoomScrollView;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.scrllview.PullToZoomBase;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.scrllview.PullToZoomScrollViewEx;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Utils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


public class ShopInfoActivity extends BaseActivity {
    @Bind(R.id.shopinfo_layout)
    MyZoomScrollView scrollView;
    @Bind(R.id.shopinfo_ivxing)
    ImageView ivShouCang;
    @Bind(R.id.ivback)
    ImageView ivback;
    @Bind(R.id.rltop)
    RelativeLayout rltop;
    @Bind(R.id.tvtitle)
    TextView tvtitle;
    private ImageView zoomView;
    @Bind(R.id.shopinfo_tvbuy)
    TextView tvbuy;
    private ProgressBar progressBar;
    private WebView mWebView;
    private ToShopinfo shopinfo;
    private TextView tvName, tvFenshu, tvPrice, tvYiShou, tvStorName, tvStorPingjia;
    private RatingBar ratingBar;
    private ShopModel model;
    private int boorhight = 0;//状态栏高度
    private Drawable drawable;
    private Drawable drawableback;
    private Drawable tvdrawable;
    private boolean isFist = true;
    private boolean isresehe = false;

    /**
     * 初始化操作
     */
    private void init() {
        zoomView = (ImageView) LayoutInflater.from(this).inflate(R.layout.foodout_shopinfo_zoomview, null, false);
        View headView = LayoutInflater.from(this).inflate(R.layout.shopinfo_head_view_layout, null, false);
        scrollView.setZoomView(zoomView);
        scrollView.setHeaderView(headView);
        View llcontent = LayoutInflater.from(this).inflate(R.layout.activity_shopinfop_content, null);
        scrollView.setScrollContentView(llcontent);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 9.0F)));
        scrollView.setHeaderLayoutParams(localObject);

        //初始化组件
        progressBar = (ProgressBar) headView.findViewById(R.id.hendview_pro);
        mWebView = (WebView) llcontent.findViewById(R.id.shopinfo_webview);
        tvName = (TextView) llcontent.findViewById(R.id.shopinfo_tvname);
        ratingBar = (RatingBar) llcontent.findViewById(R.id.shopinfo_bar);
        tvFenshu = (TextView) llcontent.findViewById(R.id.shopinfo_tvstars);
        tvPrice = (TextView) llcontent.findViewById(R.id.shopinfo_tvprice);
        tvYiShou = (TextView) llcontent.findViewById(R.id.shopinfo_tvnumber);
        tvStorName = (TextView) llcontent.findViewById(R.id.shopinfo_tvstorname);
        tvStorPingjia = (TextView) llcontent.findViewById(R.id.shopinfo_tvshopingjia);


        model = new ShopModel();
        drawable = rltop.getBackground();
        drawableback = ivback.getBackground();
        tvdrawable = tvtitle.getBackground();
        drawable.setAlpha(0);
        drawableback.setAlpha(250);
        tvdrawable.setAlpha(250);


    }

    public static void startShopInfoActivity(Context context, ToShopinfo shopinfo) {
        Intent intent = new Intent(context, ShopInfoActivity.class);
        intent.putExtra("shopinfo", shopinfo);
        context.startActivity(intent);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && boorhight != 0 && isFist) {
            isFist = false;
            ViewGroup.LayoutParams layoutParams = rltop.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.height = Utils.dip2px(App.getContext(), 46) + boorhight;
                rltop.setLayoutParams(layoutParams);
                rltop.setPadding(Utils.dip2px(App.getContext(), 10), boorhight, Utils.dip2px(App.getContext(), 10), 0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        if (drawable != null) {
            drawable = null;
        }
        if (drawableback != null) {
            drawableback = null;
        }
        if (tvdrawable != null) {
            tvdrawable = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            boorhight = getStatusBarHeight();
        }
        shopinfo = (ToShopinfo) getIntent().getSerializableExtra("shopinfo");
        if (null == shopinfo) {
            To.oo("本地传参错误请重试");
            finish();
            return;
        }
        init();
        setWebView();
        setView();
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        scrollView.setaddScrollViewChangedListener(new PullToZoomScrollViewEx.addScrollViewChangedListener() {
            @Override
            public void onInternalScrollChanged(int left, int top, int oldLeft, int oldTop) {
                if (255 > (top / 3)) {
                    drawable.setAlpha(top / 3);
                    drawableback.setAlpha(255 - (top / 3));
                    tvdrawable.setAlpha(255 - (top / 3));
                } else {
                    drawable.setAlpha(255);
                    drawableback.setAlpha(0);
                    tvdrawable.setAlpha(0);
                }
            }
        });
        scrollView.setOnPullZoomListener(new PullToZoomBase.OnPullZoomListener() {
            @Override
            public void onPullZooming(int newScrollValue) {
                if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                if (!isresehe && (newScrollValue / 2 < (-180))) {
                    isresehe = !isresehe;
                }
            }

            @Override
            public void onPullZoomEnd() {
                if (isresehe) {
                    isresehe = !isresehe;
                    setView();
                }
                if (progressBar.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        tvStorPingjia.setOnClickListener(new View.OnClickListener() {//点击评价
            @Override
            public void onClick(View v) {
                ShangjiaPingjiaActivity.startShangjiaPingjiaActivity(ShopInfoActivity.this,
                        shopinfo.getStorId());
            }
        });
        tvStorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat = shopinfo.getLat();
                String lng = shopinfo.getLng();

                if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)) {
                    To.oo("店家经纬度缺失");
                    return;
                }
                BaiduMapActivity.startBaiduMapActivity(ShopInfoActivity.this, lat, lng);
            }
        });
    }

    /**
     * 写入数据
     */
    private void setView() {
        setdata();
        mWebView.loadUrl(HTTPURL.goodshinfo + "?goods_id=" + shopinfo.getGoodsId());
        getShoucang();
    }

    /**
     * 获取收藏状态
     */
    private void getShoucang() {
        Call call = model.loadShopShou(shopinfo.getGoodsId(), App.getUserId(),
                new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        int data = (int) obj;
                        ivShouCang.setSelected(data == 1);
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        To.oo(obj);
                    }
                });
        setCall(call);
    }

    /**
     * 设置webview
     */
    private void setWebView() {
        WebSettings webSettings = mWebView.getSettings();
        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//关闭webview中缓存
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    /**
     * 写入信息
     */
    private void setdata() {
        ToGlide.url(this, HTTPURL.IMAGE + shopinfo.getImageUrl(), zoomView);
        tvName.setText(shopinfo.getShopNmae() + "");
        tvFenshu.setText(shopinfo.getShopPingFen() + "分");
        ratingBar.setRating(shopinfo.getShopPingFen());
        tvPrice.setText("￥" + shopinfo.getShopPrice());
        setnumberText("及时送\n已售" + shopinfo.getSalesSum(), tvYiShou);
        tvStorName.setText("\t" + shopinfo.getStorName()+"点击查看位置");
    }


    public static void setnumberText(String str, TextView textView) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        BackgroundColorSpan span = new BackgroundColorSpan(Color.parseColor("#A9CE87"));
        builder.setSpan(span, 0, 3, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan span1 = new ForegroundColorSpan(Color.parseColor("#ffffff"));
        builder.setSpan(span1, 0, 3, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(builder);
    }

    @OnClick({R.id.shopinfo_ivxing, R.id.shopinfo_tvbuy, R.id.ivback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shopinfo_ivxing://收藏
                toShouCang();
                break;
            case R.id.shopinfo_tvbuy://立即购买
                ToDingdanActivity.startToDingdanActivity(this,
                        shopinfo.getShopNmae(),
                        shopinfo.getStorId(),
                        shopinfo.getGoodsId(), "1",
                        shopinfo.getShopPrice()
                        , shopinfo.getShopPrice(), null);
                break;
            case R.id.ivback://返回
                finish();
                break;
        }
    }

    /**
     * 店家收藏商品
     */
    private void toShouCang() {
        if (model == null || shopinfo == null || App.getContext() == null) {
            To.oo("token过期");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        ivShouCang.setClickable(false);
        Call call = model.shouCangShop(App.getUserId(), shopinfo.getGoodsId(),
                ivShouCang.isSelected() ? 1 : 0, new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        To.dd(obj);
                        ivShouCang.setSelected(!ivShouCang.isSelected());
                        progressBar.setVisibility(View.GONE);
                        ivShouCang.setClickable(true);
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        To.oo(obj);
                        ivShouCang.setClickable(true);
                        progressBar.setVisibility(View.GONE);
                    }
                });
        setCall(call);
    }


}
