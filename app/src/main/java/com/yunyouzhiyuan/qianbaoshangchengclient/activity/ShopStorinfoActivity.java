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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.StorinfoRecyleAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorInfoList;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.StorModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.AutoImageView;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.RecyleViewButtom;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ShopStorinfoActivity extends BaseActivity {
    @Bind(R.id.stor_title)
    TitleLayout storTitle;
    @Bind(R.id.stor_image)
    AutoImageView storImage;
    @Bind(R.id.stor_tvname)
    TextView storTvname;
    @Bind(R.id.stor_ratinbar)
    RatingBar storRatinbar;
    @Bind(R.id.stor_tvfenshu)
    TextView storTvfenshu;
    @Bind(R.id.stor_tvadress)
    TextView storTvadress;
    @Bind(R.id.stor_tvtime)
    TextView storTvtime;
    @Bind(R.id.stor_ivxing)
    ImageView storIvxing;
    @Bind(R.id.stor_appbar)
    AppBarLayout storAppbar;
    @Bind(R.id.stor_recyleview)
    RecyclerView storRecyleview;
    @Bind(R.id.stor_layout)
    SwipeRefreshLayout storLayout;
    private StorinfoRecyleAdapter adapter;
    private StorModel model;
    private String stor_id;
    private StorInfo storInfo;
    private List<StorInfoList.DataBean.GoodsListBean> list = new ArrayList();
    private int page = 0;
    private boolean islooding = true;

    @Override
    protected void onDestroy() {
        if (adapter != null) {
            adapter = null;
        }
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_storinfo);
        ButterKnife.bind(this);
        if (App.getUserId() == null) {
            finish();
            To.oo("您还未登录");
            return;
        }
        stor_id = getIntent().getStringExtra("stor_id");
        if (TextUtils.isEmpty(stor_id)) {
            To.oo("店铺id为空");
            finish();
            return;
        }
        init();
        setListener();
        storLayout.setRefreshing(true);
        setView();
        storLayout.setRefreshing(false);
    }

    public static void startShopStorinfoActivity(Context context, String stor_id) {
        Intent intent = new Intent(context, ShopStorinfoActivity.class);
        intent.putExtra("stor_id", stor_id);
        context.startActivity(intent);
    }

    /**
     * 监听器
     */
    private void init() {
        storLayout.setColorSchemeResources(R.color.white);
        storLayout.setProgressBackgroundColorSchemeResource(R.color.app_color);
        storTitle.setCallback(new TitleLayout.Callback(this));
        storTitle.setTitle("店铺详情", true);
        model = new StorModel();
    }

    /**
     * 监听器
     */
    private void setListener() {
        storAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                storLayout.setEnabled(verticalOffset >= 0);
            }
        });
        storLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setView();
                storLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 写入信息
     */
    private void setView() {
        if (stor_id == null || model == null) {
            storLayout.setRefreshing(false);
            return;
        }
        count = 1;
        page = 0;
        getTopData();
    }

    /**
     * 获取底部数据
     */
    private void getBottomData() {
        Call scall = model.storeGoods(stor_id, page, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                if (page == 0) {
                    list.clear();
                }
                list.addAll((List<StorInfoList.DataBean.GoodsListBean>) obj);
                page++;
                setAdapter();
                hideLayout();
                islooding = true;
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                if (page == 0) {
                    To.oo(obj);
                    islooding = true;
                }
                hideLayout();
            }
        });
        setCall(scall);
    }

    /**
     * 关闭刷新布局
     */
    private int count = 0;

    private void hideLayout() {
        count--;
        if (count < 1) {
            storLayout.setRefreshing(false);
        }
    }

    /**
     * 获取顶部信息
     */
    private void getTopData() {
        Call storcall = model.getStorInfo(stor_id, App.getUserId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                storInfo = (StorInfo) obj;
                setTopData();
                getBottomData();
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
        setCall(storcall);
    }

    /**
     * 适配器
     */
    private void setAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new StorinfoRecyleAdapter(this, list,  storInfo);
            storRecyleview.setLayoutManager(new LinearLayoutManager(this));
            storRecyleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            storRecyleview.setItemAnimator(new DefaultItemAnimator());
            storRecyleview.setAdapter(adapter);
            storRecyleview.addOnScrollListener(new RecyleViewButtom(new RecyleViewButtom.Listener() {

                @Override
                public void last(int position) {
                    if (islooding) {
                        islooding = false;
                        getBottomData();
                    }
                }
            }));
            storIvxing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toShouCang();
                }
            });
        }
    }

    /**
     * 去收藏
     */
    private void toShouCang() {
        storLayout.setRefreshing(true);
        Call call = model.toShoCang(App.getUserId(), stor_id, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.dd(obj);
                int collect = storInfo.getData().getCollect();
                if (collect == 1) {//已收藏
                    storInfo.getData().setCollect(0);
                } else {
                    storInfo.getData().setCollect(1);
                }
                setShoucang();
                storLayout.setRefreshing(false);
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.oo(obj);
                storLayout.setRefreshing(false);
            }
        });
        setCall(call);
    }

    /**
     * 写入顶部信息
     */
    private void setTopData() {
        ToGlide.urlRound(this, HTTPURL.IMAGE + storInfo.getData().getStore_logo(), storImage, 6);
        storTvname.setText(storInfo.getData().getStore_name());
        storRatinbar.setRating(Float.parseFloat(storInfo.getData().getStore_desccredit()));
        storTvfenshu.setText(storInfo.getData().getStore_desccredit());
        storTvadress.setText(storInfo.getData().getStore_address());
        storTvtime.setText("营业时间" + storInfo.getData().getStore_time() + "-" + storInfo.getData().getStore_end_time());
        setShoucang();
    }

    /**
     * 设置收藏的按钮背景图片
     */
    private void setShoucang() {
        int collect = storInfo.getData().getCollect();
        if (1 == collect) {//已收藏
            storIvxing.setSelected(true);
        } else {
            storIvxing.setSelected(false);
        }
    }
}
