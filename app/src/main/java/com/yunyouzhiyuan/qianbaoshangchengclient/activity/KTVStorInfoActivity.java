package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.KTVBiaoqianAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.KTVTimeRecyAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.KTVTuanGouAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.KTVyuyueAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVSort;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVToDingdan;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVYuyue;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.KTVModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.StorModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyListview;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyZoomScrollView;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DialogktvYuyue;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.scrllview.PullToZoomBase;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.scrllview.PullToZoomScrollViewEx;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class KTVStorInfoActivity extends BaseActivity {

    @Bind(R.id.ltv_title_tv)
    TextView tvTitle;
    @Bind(R.id.rl_ktv_scrollview)
    MyZoomScrollView scrollView;
    @Bind(R.id.ktvinfo_ivback_wr)
    ImageView ivbackWr;
    @Bind(R.id.ktvinfo_ivback_banlk)
    ImageView ivBackBk;
    @Bind(R.id.ktvinfo_ivshoucang_wr)
    ImageView ivCangWr;
    @Bind(R.id.ktvinfo_ivshoucang_b)
    ImageView ivCangBk;
    @Bind(R.id.rl_ktv_top)
    RelativeLayout rlKtvTop;
    private ProgressBar progressBar;
    private ImageView zoomView, ivPhone;
    private TextView tvStorname, tvFenshu, tvAddress, tvPingjia;
    private RatingBar ratingBar;
    private RecyclerView recyclerViewDate, recyclerViewBiaoQian;
    private MyListview listviewYungDing, listviewTuanGou;
    private boolean isresehe = false;
    private LoadingDialog loadingDialog;
    private StorInfo info;
    private KTVModel model;
    private List<KTVSort.SpecBean> spList = new ArrayList<>();
    private List<KTVSort.RiqiBean> riqiList = new ArrayList<>();
    private KTVTimeRecyAdapter timeRecyAdapter;
    private KTVBiaoqianAdapter ktvBiaoqianAdapter;
    private List<KTVYuyue.DataBean> list = new ArrayList<>();
    private KTVyuyueAdapter ktVyuyueAdapter;
    private KTVTuanGouAdapter adapter;
    private int indexTime, indexLeing;

    @Override
    protected void onDestroy() {
        loadingDialog = null;
        model = null;
        list.clear();
        list = null;
        spList.clear();
        spList = null;
        riqiList.clear();
        riqiList = null;
        timeRecyAdapter = null;
        ktvBiaoqianAdapter = null;
        ktVyuyueAdapter = null;
        model = null;
        info = null;
        loadingDialog = null;
        adapter = null;
        super.onDestroy();
    }

    public static void startKTVStorInfoActivity(Context context, String storid, String goodsId,
                                                List<KTV.DataBean.GoodsListBean> list) {
        Intent intent = new Intent(context, KTVStorInfoActivity.class);
        intent.putExtra("storid", storid);
        intent.putExtra("goodsId", goodsId);
        intent.putExtra("list", (Serializable) list);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ktvstor_info);
        ButterKnife.bind(this);
        if (TextUtils.isEmpty(getIntent().getStringExtra("storid")) ||
                TextUtils.isEmpty(getIntent().getStringExtra("goodsId")) ||
                TextUtils.isEmpty(App.getUserId())) {
            To.oo("本地传参错误,或者您没有登陆亲");
            finish();
            return;
        }
        init();
        initScrllView();
        setListener();
        getData();
    }

    /**
     * 初始化
     */
    private void init() {
        rlKtvTop.getBackground().setAlpha(0);
        ivBackBk.setAlpha(0f);
        ivCangBk.setAlpha(0f);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        model = new KTVModel();
        tvTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    /**
     * 监听器
     */
    private void setListener() {
        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel:" + info.getData().getStore_phone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info != null) {
                    BaiduMapActivity.startBaiduMapActivity(KTVStorInfoActivity.this,
                            info.getData().getLat(), info.getData().getLng());
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
                    getData();
                }
                if (progressBar.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        scrollView.setaddScrollViewChangedListener(new PullToZoomScrollViewEx.addScrollViewChangedListener() {

            @Override
            public void onInternalScrollChanged(int left, int top, int oldLeft, int oldTop) {
                if (255 > (top / 2)) {
                    ivCangWr.getBackground().setAlpha(255 - top / 2);
                    rlKtvTop.getBackground().setAlpha(top / 2);
                    ivbackWr.setAlpha(1000f - (top / 2) * 4);
                    ivBackBk.setAlpha((top / 2 / 1000f * 3.5f));
                    ivCangWr.setAlpha(1000f - (top / 2) * 4);
                    ivCangBk.setAlpha((top / 2 / 1000f * 3.5f));
                    ivbackWr.getBackground().setAlpha(255 - top / 2);
                    tvTitle.setTextColor(ContextCompat.getColor(KTVStorInfoActivity.this, R.color.white));
                } else {
                    rlKtvTop.getBackground().setAlpha(255);
                    ivbackWr.setAlpha(0f);
                    ivBackBk.setAlpha(1f);
                    ivCangWr.setAlpha(0f);
                    ivCangBk.setAlpha(1f);
                    ivbackWr.getBackground().setAlpha(0);
                    ivCangWr.getBackground().setAlpha(0);
                    tvTitle.setTextColor(ContextCompat.getColor(KTVStorInfoActivity.this, R.color.text_color));
                }
            }
        });
        tvPingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShangjiaPingjiaActivity.startShangjiaPingjiaActivity(KTVStorInfoActivity.this,
                        getIntent().getStringExtra("storid"));
            }
        });
    }

    private int coount = 0;

    private void hideLayout() {
        coount--;
        if (coount < 1) {
            coount = 0;
            loadingDialog.dismiss();
        }
    }

    /**
     * 获取信息
     */
    private void getData() {
        coount = 2;
        getStorinfo();
        getShuXing();
        setTuanGouView();
    }

    /**
     * 写入团购信息
     */
    private void setTuanGouView() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            final List<KTV.DataBean.GoodsListBean> list = (List<KTV.DataBean.GoodsListBean>)
                    getIntent().getSerializableExtra("list");
            if (list.size() > 0) {
                list.remove(0);
            }
            adapter = new KTVTuanGouAdapter(this, list);
            listviewTuanGou.setAdapter(adapter);
            listviewTuanGou.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (list.get(position) != null && info != null && info.getData() != null &&
                            info.getData().getStore_desccredit() != null) {
                        KTVTuanGouActivity.startKTVTuanGouActivity(KTVStorInfoActivity.this,
                                list.get(position), getIntent().getStringExtra("storid"),
                                info.getData().getStore_desccredit());
                    }
                }
            });
        }
    }

    /**
     * 获取属性（预定）
     */
    private void getShuXing() {
        setCall(model.getShuXing(getIntent().getStringExtra("storid"), new IModel.AsyncCallBack() {
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
                setHomeView();
                getYuYue();
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
     * 获取预约房间信息
     */
    private void getYuYue() {
        if (spList == null || spList.size() <= indexLeing || riqiList.size() <= indexTime
                || spList.get(indexLeing).getId() == null || riqiList.get(indexTime).getId() == null) {
            To.oo("获取预约房间参数缺失");
            hideLayout();
            return;
        }
        setCall(model.getYunyue(getIntent().getStringExtra("goodsId"),
                spList.get(indexLeing).getId(), riqiList.get(indexTime).getId(),
                new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        list.clear();
                        list.addAll((List<KTVYuyue.DataBean>) obj);
                        setYuyueAdaptyer();
                        hideLayout();
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        list.clear();
                        setYuyueAdaptyer();
                        hideLayout();
                    }
                }));
    }

    /**
     * 预约时间的adapter
     */
    private void setYuyueAdaptyer() {
        if (ktVyuyueAdapter != null) {
            ktVyuyueAdapter.notifyDataSetChanged();
        } else {
            ktVyuyueAdapter = new KTVyuyueAdapter(this, list);
            listviewYungDing.setAdapter(ktVyuyueAdapter);
            listviewYungDing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (list.get(position) != null) {
                        showTimeDialod(list.get(position));
                    }
                }
            });
        }
    }

    /**
     * 显示预定时间选择框 点击提交订单
     *
     * @param data
     */
    private void showTimeDialod(KTVYuyue.DataBean data) {
        if (riqiList.size() <= indexTime || riqiList.get(indexTime).getDate() == null
                ||data == null) {
            To.oo("参数缺失");
            return;
        }
        new DialogktvYuyue(this, new DialogktvYuyue.CallBack() {
            @Override
            public void loadTime(String key, KTVYuyue.DataBean data, String time) {
                KTVToDingdan dat = new KTVToDingdan();
                dat.setGoods_spec(key);
                dat.setTime(riqiList.get(indexTime).getDate() + " " + time);
                if (data.getPrice() != null) {
                    ToDingdanActivity.startToDingdanActivity(KTVStorInfoActivity.this,
                            spList.get(indexLeing).getItem() + data.getHousename(),
                            getIntent().getStringExtra("storid"),
                            getIntent().getStringExtra("goodsId"), 1 + "",
                            data.getPrice(), data.getPrice(), dat);
                }

            }
        }, riqiList.get(indexTime).getDate(), data).show();
    }

    /**
     * 写入 时间信息
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
            recyclerViewDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewDate.setAdapter(timeRecyAdapter);
        }
    }

    /**
     * 写入 房间类型信息
     */
    private void setHomeView() {
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
            recyclerViewBiaoQian.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewBiaoQian.setAdapter(ktvBiaoqianAdapter);
        }
    }

    /**
     * 获取顶部 店家详情
     */
    private void getStorinfo() {
        setCall(new StorModel().getStorInfo(getIntent().getStringExtra("storid"), App.getUserId(),
                new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        info = (StorInfo) obj;
                        setStorInfoView();
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
     * 吸入店家信息
     */
    private void setStorInfoView() {
        ToGlide.url(this, HTTPURL.IMAGE + info.getData().getStore_logo(), zoomView);
        tvStorname.setText(info.getData().getStore_name() + "");
        ratingBar.setRating(Float.parseFloat(info.getData().getStore_desccredit() + ""));
        tvFenshu.setText(info.getData().getStore_desccredit() + "分");
        tvAddress.setText(info.getData().getLocation() + "");
        ivCangBk.setSelected(info.getData().getCollect() == 1);
        ivCangWr.setSelected(info.getData().getCollect() == 1);
    }

    /**
     * 设置contentView
     */
    private void initScrllView() {
        zoomView = (ImageView) LayoutInflater.from(this).inflate
                (R.layout.foodout_shopinfo_zoomview, null, false);
        View headView = LayoutInflater.from(this).inflate
                (R.layout.shopinfo_head_view_layout, null, false);
        scrollView.setZoomView(zoomView);
        scrollView.setHeaderView(headView);
        View llcontent = LayoutInflater.from(this).inflate
                (R.layout.activity_ktv_storinfo_content, null);
        scrollView.setScrollContentView(llcontent);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth,
                (int) (9.0F * (mScreenWidth / 12.0F)));
        scrollView.setHeaderLayoutParams(localObject);
        //初始化组件
        progressBar = (ProgressBar) headView.findViewById(R.id.hendview_pro);
        ivPhone = (ImageView) llcontent.findViewById(R.id.ktvinfo_ivphone);
        tvStorname = (TextView) llcontent.findViewById(R.id.ktvinfo_tvname);
        tvFenshu = (TextView) llcontent.findViewById(R.id.ktvinfo_tvfenshu);
        tvAddress = (TextView) llcontent.findViewById(R.id.ktvinfo_tvaddress);
        tvPingjia = (TextView) llcontent.findViewById(R.id.ktvinfo_tvpingjia);
        ratingBar = (RatingBar) llcontent.findViewById(R.id.ktvinfo_ratingbar);
        recyclerViewDate = (RecyclerView) llcontent.findViewById(R.id.ktvinfo_recyleviewdate);
        recyclerViewBiaoQian = (RecyclerView) llcontent.findViewById(R.id.ktvinfo_recyleviewbiaoqian);
        listviewYungDing = (MyListview) llcontent.findViewById(R.id.ktvinfo_yudinglistview);
        listviewTuanGou = (MyListview) llcontent.findViewById(R.id.ktvinfo_tuanlistview);
    }


    @OnClick({R.id.ktvinfo_ivback_wr, R.id.ktvinfo_ivback_banlk, R.id.ktvinfo_ivshoucang_wr, R.id.ktvinfo_ivshoucang_b})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ktvinfo_ivback_wr://返回
                finish();
                break;
            case R.id.ktvinfo_ivback_banlk://返回
                finish();
                break;
            case R.id.ktvinfo_ivshoucang_wr://收藏
                toShouCang();
                break;
            case R.id.ktvinfo_ivshoucang_b://收藏
                toShouCang();
                break;
        }
    }

    /**
     * 收藏商家
     */
    private void toShouCang() {
        if (TextUtils.isEmpty(App.getUserId())) {
            To.oo("您还没登陆");
            return;
        }
        loadingDialog.show();
        Call call = new StorModel().toShoCang(App.getUserId(),
                getIntent().getStringExtra("storid"), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.dd(obj);
                if (String.valueOf(obj).contains("收藏")) {
                    ivCangBk.setSelected(true);
                    ivCangWr.setSelected(true);
                } else {
                    ivCangBk.setSelected(false);
                    ivCangWr.setSelected(false);
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.oo(obj);
                loadingDialog.dismiss();
            }
        });
        setCall(call);
    }

}
