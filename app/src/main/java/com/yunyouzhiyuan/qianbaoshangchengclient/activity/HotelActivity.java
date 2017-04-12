package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.HotelRecyleAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap.BaiduMapBean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bann;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Hotel_Fenlei;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.HotelModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.Bannerer;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.DividerItemDecoration;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MySwLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DiaLogHotelGuan;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DialogHoteCity;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DialogHoteData;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DialogHotePrice;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DialogHotelTime;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.RecyleViewButtom;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class HotelActivity extends BaseActivity {
    @Bind(R.id.vapager)
    ViewPager vapager;
    @Bind(R.id.rrrrrrlllll)
    LinearLayout rrrrrrlllll;
    @Bind(R.id.hotel_rbquanri)
    RadioButton hotelRbquanri;
    @Bind(R.id.hotel_rbzhongdian)
    RadioButton hotelRbzhongdian;
    @Bind(R.id.activity_hotel_top_rv)
    RadioGroup activityHotelTopRv;
    @Bind(R.id.hotel_tvaddress)
    TextView hotelTvaddress;
    @Bind(R.id.hotel_time)
    TextView hotelTime;
    @Bind(R.id.hotel_tvsousuo)
    TextView hotelTvsousuo;
    @Bind(R.id.hotel_price)
    TextView hotelPrice;
    @Bind(R.id.hotel_btnok)
    Button hotelBtnok;
    @Bind(R.id.hotel_shoucang)
    TextView hotelShoucang;
    @Bind(R.id.hotel_dingdan)
    TextView hotelDingdan;
    @Bind(R.id.hotel_rbbuttom0)
    RadioButton hotelRbbuttom0;
    @Bind(R.id.hotel_rbbuttom1)
    RadioButton hotelRbbuttom1;
    @Bind(R.id.hotel_rbbuttom2)
    RadioButton hotelRbbuttom2;
    @Bind(R.id.hotel_rgbuttom)
    RadioGroup hotelRgbuttom;
    @Bind(R.id.hotel_appbar)
    AppBarLayout hotelAppbar;
    @Bind(R.id.hotel_recyleview)
    RecyclerView recyclerView;
    @Bind(R.id.hotel_layout)
    MySwLayout hotelLayout;
    @Bind(R.id.hotel_title)
    TitleLayout titleLayout;
    private Bannerer bannerer;
    private HotelRecyleAdapter adapter;
    private int page = 0;
    private List<Food_Bottom.DataBean> list_bottom = new ArrayList<>();
    private boolean isloading = true;
    private HotelModel model;
    private List<Hotel_Fenlei.DataBean> list_fenlei = new ArrayList<>();
    private int classid = 0;//底部数据分类 id
    private LoadingDialog loadingDialog;
    private int fistPrice = -1, lastPrice = -1;//用户选择价格
    private String addressId, addressLinv;//地址id  二季id
    private String guanJianZi;
    private int class_id2 = 0;//0=全日制  1=钟点房

    @Override
    protected void onDestroy() {
        if (bannerer != null) {
            bannerer.reMoverHandler();
            bannerer = null;
        }
        if (adapter != null) {
            adapter = null;
        }
        if (titleLayout != null) {
            titleLayout = null;
        }
        list_bottom.clear();
        list_bottom = null;
        model = null;
        list_fenlei.clear();
        list_fenlei = null;
        loadingDialog = null;
        addressId = null;
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    /**
     * 启动activity
     */
    public static void startHotelActivity(Context context, String sc_id) {
        Intent intent = new Intent(context, HotelActivity.class);
        intent.putExtra("sc_id", sc_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        ButterKnife.bind(this);
        if (TextUtils.isEmpty(getIntent().getStringExtra("sc_id"))) {
            To.ee("本地传参有误，scid为空");
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
        hotelLayout.setProgressBackgroundColorSchemeResource(R.color.app_color);
        hotelLayout.setColorSchemeResources(R.color.white);
        model = new HotelModel();
        loadingDialog = new LoadingDialog(this);
        String t = "目的地：\t\t\t\t请选择地址";
        Text_Size.setSize(this, hotelTvaddress, t, 0, 3, "#aa646464", 13, 3, t.length(), "#646464", 13);
        String a = "搜索：\t\t\t\t我的附近位置/酒店/关键词";
        Text_Size.setSizeThress(this, hotelTvsousuo, a, 0, 2, "#aa646464", 13, 2, 11, "#646464", 13, 11, a.length(), "#aa646464", 12);
    }

    /**
     * 监听器
     */
    private void setListener() {
        titleLayout.setCallback(new TitleLayout.Callback(this));
        hotelLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                setView();
            }
        });
        hotelAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                hotelLayout.setEnabled(verticalOffset >= 0);
            }
        });
        hotelRgbuttom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.hotel_rbbuttom0://品质出游
                        tofemlei(0);
                        break;
                    case R.id.hotel_rbbuttom1://情侣专享
                        tofemlei(1);
                        break;
                    case R.id.hotel_rbbuttom2://客栈青旅
                        tofemlei(2);
                        break;
                    default:
                        break;
                }
            }
        });
        activityHotelTopRv.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.hotel_rbquanri://全日制
                        class_id2 = 0;
                        break;
                    case R.id.hotel_rbzhongdian://钟点房
                        class_id2 = 1;
                        break;
                }
            }
        });

        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    LogUtils.d("点击返回");
                    clearCall();
                }
                return false;
            }
        });
    }

    /**
     * 点三个分类
     *
     * @param fenlei
     */
    private void tofemlei(int fenlei) {
        loadingDialog.show();
        page = 0;
        classid = fenlei;
        getBottom();
    }

    /**
     * 写入信息
     */
    private void setView() {
        hotelLayout.setRefreshing(true);
        getFenLei();
        getbanner();
    }

    /**
     * 获取轮播图
     */
    private void getbanner() {
        setCall(model.getBanner(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                setBanner((List<Bann.DataBean>) obj);
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                if (adapter != null) {
                    hotelLayout.setRefreshing(false);
                }
            }
        }));
    }

    /**
     * 获取分类
     */
    private void getFenLei() {
        setCall(model.getFenlei(new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                list_fenlei.clear();
                list_fenlei.addAll(((List<Hotel_Fenlei.DataBean>) obj));
                setFenlei();
                getBottom();
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.oo(obj);
                if (bannerer != null) {
                    hotelLayout.setRefreshing(false);
                }
            }
        }));
    }

    /**
     * 写入分类数据
     */
    private void setFenlei() {
        if (!list_fenlei.isEmpty() && list_fenlei.size() >= 3) {
            hotelRbbuttom0.setText(list_fenlei.get(0).getMobile_name());
            hotelRbbuttom1.setText(list_fenlei.get(1).getMobile_name());
            hotelRbbuttom2.setText(list_fenlei.get(2).getMobile_name());
        }
    }

    /**
     * 获取底部店家列表
     */
    private void getBottom() {
        if (BaiduMapBean.getLocation() == null) {
            if (bannerer != null) {
                hotelLayout.setRefreshing(false);
            }
            loadingDialog.dismiss();
            return;
        }
        if (list_fenlei.isEmpty() || list_fenlei.size() < 3) {
            To.oo("后台返回分类错误");
            loadingDialog.dismiss();
            return;
        }
        Call call = model.nearByStore(getIntent().getStringExtra("sc_id"),
                BaiduMapBean.getLocation().getLongitude() + "",
                BaiduMapBean.getLocation().getLatitude() + "",
                page, list_fenlei.get(classid).getId(), new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if(!isFinishing()){
                            if (page == 0) {
                                list_bottom.clear();
                            }
                            list_bottom.addAll(((Food_Bottom) obj).getData());
                            setButtomRecyle();
                            page++;
                            if (bannerer != null) {
                                hotelLayout.setRefreshing(false);
                            }
                            isloading = true;
                            loadingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Object obj) {
                        if (!isFinishing()) {
                            if (page == 0 && list_bottom != null) {
                                To.oo(obj);
                                list_bottom.clear();
                                setButtomRecyle();
                                isloading = true;
                            }
                            if (bannerer != null) {
                                hotelLayout.setRefreshing(false);
                            }
                            loadingDialog.dismiss();
                        }
                    }
                });
        setCall(call);
    }

    /**
     * 写入底部数据
     */
    private void setButtomRecyle() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new HotelRecyleAdapter(this, list_bottom);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            recyclerView.addOnScrollListener(new RecyleViewButtom(new RecyleViewButtom.Listener() {
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
     * 轮播图
     */
    private void setBanner(final List<Bann.DataBean> list) {
        if (bannerer != null) {
            bannerer.reMoverHandler();
            bannerer = null;
        }
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            strings.add(list.get(i).getAd_code());
        }
        bannerer = new Bannerer(strings, vapager, this, rrrrrrlllll, HTTPURL.IMAGE,
                R.drawable.banner_c, new Bannerer.CallBack() {
            @Override
            public void onClickListener(int position) {
                WebViewActivity.startWebViewActivity(HotelActivity.this,
                        list.get(position).getAd_link(), null);
            }
        });
        if (adapter != null) {
            hotelLayout.setRefreshing(false);
        }
    }

    @Override//定位成功
    public void upLocation(BDLocation location) {
        if (adapter == null) {
            getBottom();
        }
    }

    @OnClick({R.id.hotel_rbquanri, R.id.hotel_rbzhongdian, R.id.hotel_tvaddress,
            R.id.hotel_time, R.id.hotel_tvsousuo, R.id.hotel_price, R.id.hotel_btnok,
            R.id.hotel_shoucang, R.id.hotel_dingdan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hotel_tvaddress://目的地
                toCityList();
                break;
            case R.id.hotel_time://住店时间
                toOutDialogTime();
                break;
            case R.id.hotel_tvsousuo://添加搜索条件，输入汉字
                showSousuoDialog();
                break;
            case R.id.hotel_price://点击价格
                showDialogPrice();
                break;
            case R.id.hotel_btnok://点击确定
                toOutOk();
                break;
            case R.id.hotel_shoucang://我的收藏
                startActivity(new Intent(this, ShouCangActivity.class));
                break;
            case R.id.hotel_dingdan://我的订单
                DingDanActivity.startDingdanActivity(this, 0);
                break;
        }
    }

    /**
     * 显示输入关键字的输入框dialog
     */
    private void showSousuoDialog() {
        DiaLogHotelGuan diaLogHotelGuan = new DiaLogHotelGuan(this, new DiaLogHotelGuan.CallBack() {
            @Override
            public void callBack(String string) {
                guanJianZi = string;
                String str = null;
                if (TextUtils.isEmpty(string)) {
                    str = "搜索：\t\t\t\t我的附近位置/酒店/关键词";
                } else {
                    str = "关键字：\t\t\t\t" + string;
                }
                Text_Size.setSize(App.getContext(), hotelTvsousuo, str, 0, 8, "#bb646464",
                        13, 8, str.length(), "#646464", 13);
            }
        }, guanJianZi);
        diaLogHotelGuan.show();
    }

    /**
     * 点击确定按钮 查找酒店
     * private int fistPrice = -1, lastPrice = -1;//用户选择价格
     * private String addressId, addressLinv;//地址id  二季id
     * private String guanJianZi;
     * private int class_id2 = 0;//0=全日制  1=钟点房
     */
    private void toOutOk() {
        if (TextUtils.isEmpty(addressId) || TextUtils.isEmpty(addressLinv)) {
            To.oo("请选择地址");
            return;
        }
        if (class_id2 == 0 && count == -1) {
            To.oo("请选择时间");
            return;
        }
        if (BaiduMapBean.getLocation() == null) {
            To.oo("尚未定位成功，请查看本应用是否被禁止使用位置服务呢亲");
            return;
        }
        String pr = "";
        if (fistPrice != -1 && lastPrice != -1) {
            pr = fistPrice + "," + lastPrice;
        }
        DialogHoteData dialogHoteData = new DialogHoteData(this, model,
                getIntent().getStringExtra("sc_id"), addressLinv,
                addressId, class_id2 + "", guanJianZi, pr);
        dialogHoteData.show();
        dialogHoteData.setCallback(new DialogHoteData.Callback() {
            @Override
            public void callback(String storid) {
                HotelStorInfoActivity.startHotelStorInfoActivity(HotelActivity.this, storid,
                        fistTime, lastTime, count, class_id2);
            }
        });
    }

    /**
     * 去选择地址 目的地
     */
    private void toCityList() {
        DialogHoteCity dialogHoteCity = new DialogHoteCity(this, model, new DialogHoteCity.CallBack() {
            @Override
            public void callBack(String city_id, String id, String name) {
                addressId = city_id;
                addressLinv = id;
                String str = "目的地：\t\t\t\t" + name;
                Text_Size.setSize(App.getContext(), hotelTvaddress, str,
                        0, 8, "#bb646464", 13, 8, str.length(), "#646464", 13);
            }
        });
        dialogHoteCity.show();
    }

    /**
     * 显示价格
     */
    private void showDialogPrice() {
        DialogHotePrice dialogHotePrice = new DialogHotePrice(HotelActivity.this,
                new DialogHotePrice.CallBack() {
                    @Override
                    public void loadPrice(int fPrice, int lPrice) {
                        fistPrice = fPrice;
                        lastPrice = lPrice;
                        hotelPrice.setText("星级价格：\t\t\t\t" + fistPrice + "元---" + lastPrice + "元");
                    }

                    @Override
                    public void noPrice() {
                        fistPrice = -1;
                        lastPrice = -1;
                        hotelPrice.setText("星级价格：\t\t\t\t不限价格\t\t不限星级");
                    }
                });
        dialogHotePrice.show();
    }

    /**
     * 打开时间选择器
     */
    private String fistTime = "钟点房", lastTime = "钟点房";
    private int count = -1;

    private void toOutDialogTime() {
        if (class_id2 != 0) {//不是全日制 不用选择时间
            To.oo("钟点房不用选择时间");
            return;
        }
        new DialogHotelTime(this, new DialogHotelTime.CallBack() {

            @Override
            public void loadTime(int fy, int fm, int fd, int ly, int lm, int ld, int cou) {
                fistTime = fy + "-" + fm + "-" + fd;
                lastTime = ly + "-" + lm + "-" + ld;
                count = cou;
                String string = fistTime + "住店\n" + lastTime + "离店,共住：" + count + "晚";
                hotelTime.setText(string);
            }
        }).show();
    }
}
