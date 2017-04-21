package com.yunyouzhiyuan.qianbaoshangchengclient.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.CookStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.FoodOutInfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.HotelActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.KTVStorInfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.ShopStorinfoActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.WebViewActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.activity.WebViewHuoDongActivity;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.HomeGvAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.HomeMyListviewAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.recyleViewCaidanAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bann;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HomeStore_category;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_Bottom_list;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_HuoDong;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV_list_data;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.HomeModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.Bannerer;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyListview;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MySwLayoutBoth;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.swlayout.SwipyRefreshLayoutDirection;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * wang
 */
public class HomeFragment extends MainFragment {
    @Bind(R.id.vapager)
    ViewPager vapager;
    @Bind(R.id.rrrrrrlllll)
    LinearLayout rrrrrrlllll;
    @Bind(R.id.main_recview)
    RecyclerView mainRecview;
    @Bind(R.id.main_tiantiangengduo)
    TextView mainTiantiangengduo;
    @Bind(R.id.main_gv_meishi)
    GridView mainGvMeishi;
    @Bind(R.id.main_myListview)
    MyListview mainMyListview;
    @Bind(R.id.fragment_home_layout)
    MySwLayoutBoth layout;
    @Bind(R.id.fragment_home_ivhuodong0)
    ImageView ivHuoDong0;
    @Bind(R.id.fragment_home_ivhuodong1)
    ImageView ivHuoDong1;
    private HomeModel model;
    private List<HomeStore_category.DataBean> list = new ArrayList<>();

    private recyleViewCaidanAdapter recyleViewCaidanAdapter;//菜单recyleview 适配器
    private HomeGvAdapter homeGvAdapter;//gridView 的适配器
    private HomeMyListviewAdapter homeMyListviewAdapter;//Mylistview的适配器
    private int page = 0;//底部分页
    private List<Home_Bottom_list.DataBean> listBottom = new ArrayList<>();
    private List<Bann.DataBean> bans = new ArrayList<>();
    private List<Home_HuoDong.DataBean> listHuoDong = new ArrayList<>();
    private boolean isLoading = false;

    @Override
    public void onDestroy() {
        if (bannerer != null) {
            bannerer.reMoverHandler();
        }
        listBottom.clear();
        listBottom = null;
        list.clear();
        list = null;
        model = null;
        bans.clear();
        bans = null;
        super.onDestroy();
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    private View view;
    private Bannerer bannerer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, view);
            init();
            setListener();
            setDate();
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        return view;
    }

    /**
     * 监听器
     */
    private void setListener() {
        layout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {//顶部刷新
                    setDate();
                } else {
                    getMore();
                    LogUtils.d("layout-->执行getMore()");
                }
            }
        });
    }

    /**
     * 写入总数居
     */
    private void setDate() {
        count = 4;
        page = 0;
        layout.setRefreshing(true);
        subject.nodifyPositioning();
        getBanner();
        getStor();
        getMore();
        getHuoDong();
    }

    /**
     * 获取活动接口
     */
    private void getHuoDong() {
        setCalls(model.getHuoDong(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (getContext() == null) {
                    return;
                }
                listHuoDong.clear();
                listHuoDong.addAll((List<Home_HuoDong.DataBean>) obj);
                setHuoDong();
                setGvAdapter();
                hideLayout();
            }

            @Override
            public void onError(Object obj) {
                if (getContext() == null) {
                    return;
                }
                To.oo(obj);
                hideLayout();
            }
        }));
    }

    /**
     * 写入活动界面
     */
    private void setHuoDong() {
        for (int i = 0; i < listHuoDong.size(); i++) {
            switch (i) {
                case 0:
                    ToGlide.url(HomeFragment.this, HTTPURL.IMAGE + listHuoDong.get(i).getAd_code(),
                            ivHuoDong0);
                    ivHuoDong0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebViewHuoDongActivity.startWebViewHuoDongActivity(getActivity(),
                                    listHuoDong.get(0).getAd_link());
                        }
                    });
                    break;
                case 1:
                    ToGlide.url(HomeFragment.this, HTTPURL.IMAGE + listHuoDong.get(i).getAd_code(),
                            ivHuoDong1);
                    ivHuoDong1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebViewHuoDongActivity.startWebViewHuoDongActivity(getActivity(),
                                    listHuoDong.get(1).getAd_link());
                        }
                    });
                    break;
            }
        }
    }

    /**
     * 获取轮播图
     */
    private void getBanner() {
        final Call banner = model.getBanner(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (getContext() == null) {
                    return;
                }
                bans.clear();
                bans.addAll((List<Bann.DataBean>) obj);
                setBanner();
                hideLayout();
            }

            @Override
            public void onError(Object obj) {
                if (getContext() == null) {
                    return;
                }
                To.oo(obj);
                hideLayout();
            }
        });
        setCalls(banner);
    }

    /**
     * 获取地步更多数据
     */
    private void getMore() {
        BDLocation location = BaiduMapBean.getLocation();
        if (location == null) {
            hideLayout();
            return;
        }
        if (Bean.city_id_id == null) {
            return;
        }
        isLoading = true;
        Call list = model.getList(page, location.getLatitude() + "", location.getLongitude() + ""
                , Bean.city_id_id, new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (getContext() == null) {
                            return;
                        }
                        if (page == 0) {
                            listBottom.clear();
                        }
                        listBottom.addAll((List<Home_Bottom_list.DataBean>) obj);
                        setMore();
                        hideLayout();
                        page++;
                    }

                    @Override
                    public void onError(Object obj) {
                        if (getContext() == null) {
                            return;
                        }
                        hideLayout();
                        if (page == 0) {
                            listBottom.clear();
                            To.oo(obj);
                            setMore();
                        }
                    }
                });
        setCalls(list);
    }

    /**
     * 获取店铺分类
     */
    private void getStor() {
        Call stor = model.getStor(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (getContext() == null) {
                    return;
                }
                list.clear();
                list.addAll((List<HomeStore_category.DataBean>) obj);
                initRecyleview();
                hideLayout();
            }

            @Override
            public void onError(Object obj) {
                if (getContext() == null) {
                    return;
                }
                To.oo(getActivity().getString(R.string.huoqushouyefenleishibai) + "\n" + obj + "");
                hideLayout();
            }
        });
        setCalls(stor);
    }

    private int count = 0;

    /**
     * 刷新加载 完成后 关闭刷新提示动画
     */
    private void hideLayout() {
        count--;
        if (count < 1) {
            count = 0;
            layout.setRefreshing(false);
        }
    }

    /**
     * 底部更多
     */
    private void setMore() {
        if (homeMyListviewAdapter != null) {
            homeMyListviewAdapter.notifyDataSetChanged();
        } else {
            homeMyListviewAdapter = new HomeMyListviewAdapter(getContext(), listBottom);
            mainMyListview.setAdapter(homeMyListviewAdapter);
            mainMyListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        switch (listBottom.get(position).getSc_id()) {
            case "1"://美食
                CookStorinfoActivity.startCookStorinfoActivity(getActivity(),
                        listBottom.get(position).getStore_id());
                break;
            case "2"://外卖
                FoodOutInfoActivity.startFoodOutInfoActivity(getActivity(),
                        listBottom.get(position).getDistance() + "",
                        listBottom.get(position).getStore_id());
                break;
            case "3"://酒店
                HotelActivity.startHotelActivity(getActivity(), listBottom.get(position).getSc_id());
                break;
            case "4"://KTV
                toKtv(position);
                break;
            default:
                ShopStorinfoActivity.startShopStorinfoActivity(getActivity()
                        , listBottom.get(position).getStore_id());
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
        setCalls(model.getHotelData(listBottom.get(position).getStore_id(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (getContext() == null) {
                    return;
                }
                KTV_list_data.DataBean data = (KTV_list_data.DataBean) obj;
                List<KTV.DataBean.GoodsListBean> list = new ArrayList<KTV.DataBean.GoodsListBean>();
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
                    list.add(dataData);
                }
                if (data.getYuding() == null || list.isEmpty()) {
                    To.ee("该店铺的资料尚未完善");
                    layout.setRefreshing(false);
                    return;
                }
                KTVStorInfoActivity.startKTVStorInfoActivity(getActivity(),
                        listBottom.get(position).getStore_id(),
                        data.getYuding().getGoods_id(),
                        list);
                layout.setRefreshing(false);
            }

            @Override
            public void onError(Object obj) {
                if (getContext() == null) {
                    return;
                }
                To.ee(obj);
                layout.setRefreshing(false);
            }
        }));
    }

    /**
     * GridView的适配器
     */
    private void setGvAdapter() {
        if (homeGvAdapter != null) {
            homeGvAdapter.notifyDataSetChanged();
        } else {
            homeGvAdapter = new HomeGvAdapter(getContext(), listHuoDong);
            mainGvMeishi.setAdapter(homeGvAdapter);
            mainGvMeishi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WebViewHuoDongActivity.startWebViewHuoDongActivity(getActivity(),
                            listHuoDong.get(position + 2).getAd_link());
                }
            });
        }
    }

    /**
     * recyleview初始化 加载数据
     */
    private void initRecyleview() {
        if (recyleViewCaidanAdapter != null) {
            recyleViewCaidanAdapter.notifyDataSetChanged();
        } else {
            recyleViewCaidanAdapter = new recyleViewCaidanAdapter(getContext(), list);
            mainRecview.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.HORIZONTAL, false));
            mainRecview.setAdapter(recyleViewCaidanAdapter);
        }
    }

    /**
     * 初始化
     */
    private void init() {
        layout.setProgressBackgroundColor(R.color.app_color);
        layout.setColorSchemeResources(R.color.white);
        model = new HomeModel();
    }

    /**
     * 设置轮播图
     */
    private void setBanner() {
        if (bannerer != null) {
            bannerer.reMoverHandler();
            bannerer = null;
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < bans.size(); i++) {
            if (bans.get(i).getAd_code() != null) {
                list.add(bans.get(i).getAd_code());
            }
        }
        bannerer = new Bannerer(list, vapager, getContext(), rrrrrrlllll, HTTPURL.IMAGE, R.drawable.banner_c, new Bannerer.CallBack() {
            @Override
            public void onClickListener(int position) {
                WebViewActivity.startWebViewActivity(getActivity(), bans.get(position).getAd_link(), "网页");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void upLocation(BDLocation location) {
        if (homeMyListviewAdapter == null && view != null && !isLoading) {
            page = 0;
            getMore();
            LogUtils.d("upLocation-->getMore()");
        }
    }

    @Override
    public void upCity() {
        if (view != null) {
            page = 0;
            layout.setRefreshing(true);
            getMore();
            LogUtils.d("upCity-->getMore()");
        }
    }
}
