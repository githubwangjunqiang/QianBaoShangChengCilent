package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.KTVBiaoqianAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.KTVTimeRecyAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.TuanListAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVSort;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVToDingdan;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVTuanList;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.KTVModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyListview;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyRecyleView;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KTVTuanGouActivity extends BaseActivity {

    @Bind(R.id.coollapsingtoolbarlayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ktvtuangou_tvprice)
    TextView tvPrice;
    @Bind(R.id.ktvtuangou_btnok)
    Button btnOk;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.tuangou_ratingbar)
    RatingBar ratingBar;
    @Bind(R.id.tuangou_tvfenshu)
    TextView tvfenshu;
    @Bind(R.id.tuangou_tvpinglun)
    TextView tvPingfen;
    @Bind(R.id.tuangou_recyletime)
    MyRecyleView recyclerViewTime;
    @Bind(R.id.tuangou_recyletbiaoqian)
    MyRecyleView recyclerViewBiaoqian;
    @Bind(R.id.tuangou_mylistview)
    MyListview listview;
    @Bind(R.id.tuangou_webview)
    WebView webView;
    @Bind(R.id.tuangou_ivtop)
    ImageView ivTopImage;
    private List<KTVSort.SpecBean> spList = new ArrayList<>();
    private List<KTVSort.RiqiBean> riqiList = new ArrayList<>();
    private List<KTVTuanList.DataBean> list = new ArrayList<>();
    private KTVTimeRecyAdapter timeRecyAdapter;
    private KTVBiaoqianAdapter ktvBiaoqianAdapter;
    private TuanListAdapter adapter;
    private LoadingDialog loadingDialog;
    private KTVModel model;
    private KTV.DataBean.GoodsListBean data;
    private int indexTime, indexLeing;

    @Override
    protected void onDestroy() {
        spList.clear();
        riqiList.clear();
        list.clear();
        spList = null;
        riqiList = null;
        timeRecyAdapter = null;
        list = null;
        ktvBiaoqianAdapter = null;
        adapter = null;
        data = null;
        loadingDialog = null;
        model = null;
        super.onDestroy();
    }

    public static void startKTVTuanGouActivity(Context context, KTV.DataBean.GoodsListBean list,
                                               String storId, String fen) {
        Intent intent = new Intent(context, KTVTuanGouActivity.class);
        intent.putExtra("list", list);
        intent.putExtra("storId", storId);
        intent.putExtra("fen", fen);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ktvtuan_gou);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (TextUtils.isEmpty(getIntent().getStringExtra("storId")) ||
                getIntent().getSerializableExtra("list") == null ||
                TextUtils.isEmpty(getIntent().getStringExtra("fen"))) {
            To.oo("本地传参错误");
            finish();
            return;
        }

        init();
        setListener();
        getData();
    }

    /**
     * 获取信息
     */
    private void getData() {
        loadingDialog.show();
        setTopView();
        getShuxing();
        webView.loadUrl(HTTPURL.goodshinfo + "?goods_id=" + data.getGoods_id());
    }

    /**
     * 写入顶部团购信息
     */
    private void setTopView() {
        data = (KTV.DataBean.GoodsListBean) getIntent().getSerializableExtra("list");
        ToGlide.url(this, HTTPURL.IMAGE + data.getOriginal_img(), ivTopImage);
        String str = "￥" + data.getShop_price() + "\t门市价:￥" + data.getMarket_price();
        Text_Size.setXiehuaXianTow(tvPrice, str, 0, data.getShop_price().length() + 1, R.color.blue_p,
                R.dimen.text_size_16sp, data.getShop_price().length() + 1, str.length(), R.color.text_color_alp,
                R.dimen.text_size_12sp);
        ratingBar.setRating(Float.parseFloat(getIntent().getStringExtra("fen")));
        tvfenshu.setText(getIntent().getStringExtra("fen") + "分");
        collapsingToolbarLayout.setTitle("团购详情");
    }

    /**
     * 获取属性 团购属性 星期几 日期
     */
    private void getShuxing() {
        if (data == null || data.getGoods_id() == null) {
            loadingDialog.dismiss();
            return;
        }
        setCall(model.getTuanGouShuxing(data.getGoods_id(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                spList.clear();
                spList.addAll(((KTVSort) obj).getSpec());
                riqiList.clear();
                riqiList.addAll(((KTVSort) obj).getRiqiBeen());
                setTimeView();
                setLeixingView();
                getYuYue();
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.oo(obj);
                loadingDialog.dismiss();
            }
        }));
    }

    /**
     * 获取房间信息
     */
    private void getYuYue() {
        LogUtils.d("团购点击时间帅选：data=" + data + "data.getGoods_id()=" + data.getGoods_id()
                + "spList.size()=" + spList.size() + "indexLeing=" + indexLeing + "riqiList.size()=" + riqiList.size()
                + "indexTime=" + indexTime + "spList.get(indexLeing).getId()=" + spList.get(indexLeing).getId()
                + "riqiList.get(indexTime).getId()=" + riqiList.get(indexTime).getId());
        if (data == null || data.getGoods_id() == null || spList.size() <= indexLeing ||
                riqiList.size() <= indexTime
                || spList.get(indexLeing).getId() == null || riqiList.get(indexTime).getId() == null) {
            loadingDialog.dismiss();
            list.clear();
            setAdapter();
            To.ee("参数缺失");
            return;
        }
        setCall(model.getTuanList(data.getGoods_id(),
                spList.get(indexLeing).getId(), riqiList.get(indexTime).getId()
                , new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        list.clear();
                        list.addAll((List<KTVTuanList.DataBean>) obj);
                        setAdapter();
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        To.oo(obj);
                        list.clear();
                        setAdapter();
                        loadingDialog.dismiss();
                    }
                }));
    }

    /**
     * 根据参数值 写入房间列表
     */
    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new TuanListAdapter(this, list);
            listview.setAdapter(adapter);
        }
    }

    /**
     * 写入类型recyleview
     */
    private void setLeixingView() {
        if (ktvBiaoqianAdapter != null) {
            ktvBiaoqianAdapter.notifyDataSetChanged();
        } else {
            ktvBiaoqianAdapter = new KTVBiaoqianAdapter(this, spList, new KTVBiaoqianAdapter.Callback() {
                @Override
                public void onClick(int position) {
                    indexLeing = position;
                    clearCall();
                    getYuYue();
                }
            });
            recyclerViewBiaoqian.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewBiaoqian.setAdapter(ktvBiaoqianAdapter);
        }
    }

    /**
     * 写入时间recyleview
     */
    private void setTimeView() {
        if (timeRecyAdapter != null) {
            timeRecyAdapter.notifyDataSetChanged();
        } else {
            timeRecyAdapter = new KTVTimeRecyAdapter(riqiList, this, new KTVTimeRecyAdapter.Callback() {
                @Override
                public void onClick(int position) {
                    indexTime = position;
                    clearCall();
                    getYuYue();
                }
            });
            recyclerViewTime.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewTime.setAdapter(timeRecyAdapter);
        }
    }

    /**
     * 监听器
     */
    private void setListener() {
    }

    /**
     * 初始化操作
     */
    private void init() {
        loadingDialog = new LoadingDialog(this);
        model = new KTVModel();
        WebSettings webSettings = webView.getSettings();
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
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @OnClick({R.id.iv_tuangouback, R.id.ktvtuangou_btnok, R.id.tuangou_tvpinglun})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tuangouback://返回
                finish();
                break;
            case R.id.ktvtuangou_btnok://购买
                toShopping();
                break;
            case R.id.tuangou_tvpinglun://品论
                ShangjiaPingjiaActivity.startShangjiaPingjiaActivity(
                        this, getIntent().getStringExtra("storId"));
                break;
        }
    }

    /**
     * 点击购买
     */
    private void toShopping() {

        if (riqiList.size() <= indexTime || spList.size() <= indexLeing || riqiList.get(indexTime).getDate() == null
                || spList.get(indexLeing).getId() == null || data == null || data.getGoods_name() == null
                || data.getGoods_id() == null || data.getShop_price() == null || data.getShop_price() == null) {
            return;
        }
        KTVToDingdan dat = new KTVToDingdan();
        dat.setGoods_spec(list.get(0).getKey());
        dat.setTime(riqiList.get(indexTime).getDate() + " " + spList.get(indexLeing).getId());
        ToDingdanActivity.startToDingdanActivity(this, data.getGoods_name(), getIntent().
                        getStringExtra("storId"), data.getGoods_id(), "1", data.getShop_price(),
                data.getShop_price(), dat);
    }
}
