package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.HomeMyListviewAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_Bottom_list;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV_list_data;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.HomeModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.SouSuoModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayoutDirection;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SouSuoActivity extends BaseActivity {

    @Bind(R.id.sousuoa_ibback)
    ImageButton ibBack;
    @Bind(R.id.sousuoa_etsousuo)
    EditText etSouSuo;
    @Bind(R.id.sousuoa_toptvsouuo)
    TextView tvSousuo;
    @Bind(R.id.sousuo_recyleview)
    ListView listView;
    @Bind(R.id.sousuo_layout)
    SwipyRefreshLayout layout;
    private SouSuoModel model;
    private List<Home_Bottom_list.DataBean> list = new ArrayList<>();
    private HomeMyListviewAdapter homeMyListviewAdapter;//Mylistview的适配器

    @Override
    protected void onDestroy() {
        model = null;
        list.clear();
        list = null;
        homeMyListviewAdapter = null;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo);
        ButterKnife.bind(this);
        init();
        setListener();
    }

    /**
     * 初始化
     */
    private void init() {
        layout.setColorSchemeColors(R.color.white);
        layout.setProgressBackgroundColor(R.color.app_color);
        model = new SouSuoModel();
    }

    /**
     * 监听器
     */
    private void setListener() {
        layout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    setView();
                }
            }
        });
        etSouSuo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean isOK = true;
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH://搜索
                        setView();
                        hintKbTwo();
                        break;
                    default:
                        isOK = false;
                        break;
                }
                return isOK;
            }
        });
    }

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 获取数据 刷新界面
     */
    private void setView() {
        if (!layout.isRefreshing()) {
            layout.setRefreshing(true);
        }
        if (BaiduMapBean.getLocation() == null || Bean.city_id_id == null) {
            To.ee("尚未定位成功无法计算距离，或者你没选择城市");
            layout.setRefreshing(false);
            return;
        }
        String trim = etSouSuo.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            To.ee("请输入关键字");
            layout.setRefreshing(false);
            return;
        }
        setCall(model.getData(trim, Bean.city_id_id, String.valueOf(BaiduMapBean.getLocation().getLongitude()),
                String.valueOf(BaiduMapBean.getLocation().getLatitude()), new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        list.clear();
                        list.addAll((List<Home_Bottom_list.DataBean>) obj);
                        setAdapter();
                        layout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        To.ee(obj);
                        layout.setRefreshing(false);
                    }
                }));
    }

    @Override
    public void upLocation(BDLocation location) {
        super.upLocation(location);
    }

    @Override
    public void upCity() {
        super.upCity();
    }

    @OnClick({R.id.sousuoa_ibback, R.id.sousuoa_toptvsouuo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sousuoa_ibback://返回
                finish();
                overridePendingTransition(0, 0);
                break;
            case R.id.sousuoa_toptvsouuo://点击搜索
                hintKbTwo();
                setView();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(0, 0);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 底部更多
     */
    private void setAdapter() {
        if (homeMyListviewAdapter != null) {
            homeMyListviewAdapter.notifyDataSetChanged();
        } else {
            homeMyListviewAdapter = new HomeMyListviewAdapter(this, list);
            listView.setAdapter(homeMyListviewAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    toStartInfoActviity(position);
                }
            });
        }
    }

    /**
     * 底部列表 点击跳转不同的详情页
     *
     * @param position
     */
    private void toStartInfoActviity(int position) {
        switch (list.get(position).getSc_id()) {
            case "1"://美食
                CookStorinfoActivity.startCookStorinfoActivity(this,
                        list.get(position).getStore_id());
                break;
            case "2"://外卖
                FoodOutInfoActivity.startFoodOutInfoActivity(this,
                        list.get(position).getDistance() + "",
                        list.get(position).getStore_id());
                break;
            case "3"://酒店
                HotelActivity.startHotelActivity(this, list.get(position).getSc_id());
                break;
            case "4"://KTV
                toKtv(position);
                break;
            case "5"://休闲娱乐
                ShopStorinfoActivity.startShopStorinfoActivity(this
                        , list.get(position).getStore_id());
                break;
            default:
                ShopStorinfoActivity.startShopStorinfoActivity(this
                        , list.get(position).getStore_id());
                break;
        }
    }

    /**
     * 跳转KTV
     *
     * @param position
     */
    private void toKtv(final int position) {
        layout.setRefreshing(true);
        setCall(new HomeModel().getHotelData(list.get(position).getStore_id(),
                new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        KTV_list_data.DataBean data = (KTV_list_data.DataBean) obj;
                        List<KTV.DataBean.GoodsListBean> lists = new ArrayList<KTV.DataBean.GoodsListBean>();
                        KTV.DataBean.GoodsListBean dataData = new KTV.DataBean.GoodsListBean();
                        for (KTV_list_data.DataBean.TuangouBean ddata : data.getTuangou()) {
                            dataData.setGoods_id(ddata.getGoods_id());
                            dataData.setGoods_name(ddata.getGoods_name());
                            dataData.setMarket_price(ddata.getMarket_price());
                            dataData.setSales_sum(ddata.getSales_sum());
                            dataData.setShop_price(ddata.getShop_price());
                            dataData.setStore_cat_id1(ddata.getStore_cat_id1());
                            dataData.setOriginal_img(ddata.getOriginal_img());
                            dataData.setProm_type(ddata.getProm_type());
                            lists.add(dataData);
                        }

                        KTVStorInfoActivity.startKTVStorInfoActivity(SouSuoActivity.this,
                                list.get(position).getStore_id(), data.getYuding().getGoods_id(),
                                lists);
                        layout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        To.ee(obj);
                        layout.setRefreshing(false);
                    }
                }));
    }
}
