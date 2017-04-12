package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.HotelFangjianAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Hotel_Fagnjian;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Hotel_storinfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.HotelModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.StorModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyListview;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DialogHoteStorInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DialogHotelTime;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.scrllview.PullToZoomBase;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.scrllview.PullToZoomScrollViewEx;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Utils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class HotelStorInfoActivity extends BaseActivity {

    @Bind(R.id.hotelstorinfo_PullToZoomScrollViewEx)
    PullToZoomScrollViewEx scrollView;
    @Bind(R.id.hotelstorinfo_ivback_wr)
    ImageView ivbackW;
    @Bind(R.id.hotelstorinfo_ivback_banlk)
    ImageView ivbackB;
    @Bind(R.id.rltop)
    RelativeLayout rltop;
    @Bind(R.id.hotelstorinfo_ivshoucang_wr)
    ImageView ivshoucang_w;
    @Bind(R.id.hotelstorinfo_ivshoucang_b)
    ImageView ivshoucang_b;
    private int boorhight = 0;
    private boolean isFist = true;
    private Drawable drawable_rl;
    private ImageView zoomView;
    private ProgressBar progressBar;
    private TextView tvAddress, tvPingfen, tvRuzhu, tvNumber, tvLidian, tvPhone;
    private MyListview listview;
    private HotelModel model;
    private Hotel_storinfo.DataBean data;
    private HotelFangjianAdapter adapter;
    private List<Hotel_Fagnjian.DataBean> list = new ArrayList<>();
    private ImageView ivMap;
    private boolean isresehe = false;
    private TextView tvBottomPin;
    private LoadingDialog loadingDialog;
    private LinearLayout llTime;//时间

    @Override
    protected void onDestroy() {
        drawable_rl = null;
        adapter = null;
        list.clear();
        list = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_stor_info);
        ButterKnife.bind(this);
        if (getIntent().getStringExtra("stor_id") == null || App.getUserId() == null ||
                getIntent().getIntExtra("classsid", -1) == -1) {
            To.oo("您还未登录 或者本地传参失败");
            finish();
            return;
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//            boorhight = getStatusBarHeight();
//        }
        init();
        initScrllView();
        setListener();
        getData();
    }

    /**
     * 获取信息 联网
     */
    private void getData() {
        getStorInfo();
        getBottomData();
    }

    /**
     * 获取酒店店家房间的类型
     */
    private void getBottomData() {
        setCall(model.getFangjian(getIntent().getStringExtra("stor_id"), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                list.clear();
                List<Hotel_Fagnjian.DataBean> obj1 = (List<Hotel_Fagnjian.DataBean>) obj;
                for (Hotel_Fagnjian.DataBean data : obj1) {
                    if (TextUtils.equals(data.getCat_id2(), "50") &&
                            getIntent().getIntExtra("classsid", 0) == 0) {//相等全日制
                        list.add(data);
                    } else {
                        list.add(data);
                    }
                }
                setadapter();
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.oo(obj);
            }
        }));
    }

    /**
     * 写入房间信息
     */
    private void setadapter() {
        loadingDialog.dismiss();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new HotelFangjianAdapter(this, list, new HotelFangjianAdapter.Callback() {

                @Override
                public void onClick(String id, String title, String price, int position) {
                    toShouDialog(id, title, price, position);
                }

                @Override
                public void onClickBtn(int position) {
                    toDingdan(position);
                }
            });
            listview.setAdapter(adapter);
        }
    }

    /**
     * 生成订单
     */
    private void toDingdan(int position) {
        Hotel_Fagnjian.DataBean data = list.get(position);
        ToDingdanActivity.startToDingdanActivity(this, data.getGoods_name(), getIntent().getStringExtra("stor_id")
                , data.getGoods_id(), "1", data.getShop_price(), data.getShop_price(), null);
    }

    /**
     * 显示商品详情对话框
     *
     * @param id
     * @param position
     */
    private void toShouDialog(String id, String title, String price, final int position) {
        new DialogHoteStorInfo(HotelStorInfoActivity.this, id,
                new DialogHoteStorInfo.Callback() {
                    @Override
                    public void callback() {
                        toDingdan(position);
                    }
                }, title, price).show();
    }

    /**
     * 获取酒店店家详情
     */
    private void getStorInfo() {
        setCall(model.getInfo(getIntent().getStringExtra("stor_id"), App.getUserId(),
                new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        data = ((Hotel_storinfo.DataBean) obj);
                        setTopView();
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        To.ee(obj);
                    }
                }));
    }

    /**
     * 写入顶部店家信息
     */
    private void setTopView() {
        ToGlide.url(this, HTTPURL.IMAGE + data.getStore_logo(), zoomView);
        tvAddress.setText(data.getLocation() + "");
        String str = data.getAvgcomment() + "分";
        String stre1 = "\n高于85%同类酒店";
        String stre2 = "\n查看" + data.getCommentcount() + "评论";
        String string = str + stre1 + stre2;
        Text_Size.setSizeThress(App.getContext(), tvPingfen, string, 0, str.length(), "#fd3b00", 18,
                str.length(), str.length() + stre1.length(), "#FD7200", 12,
                str.length() + stre1.length(),
                string.length(), "#bb646464", 12);


        setIntentData();

        ivshoucang_w.setSelected(data.getCollect() == 1);
        ivshoucang_b.setSelected(data.getCollect() == 1);


        llTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getIntExtra("classsid", -1) == 0) {//全日制
                    new DialogHotelTime(HotelStorInfoActivity.this, new DialogHotelTime.CallBack() {
                        @Override
                        public void loadTime(int fy, int fm, int fd, int ly, int lm, int ld, int count) {
                            getIntent().putExtra("fistTime", fy + "-" + fm + "-" + fd);
                            getIntent().putExtra("lastTime", ly + "-" + lm + "-" + ld);
                            getIntent().putExtra("count", count);
                            setIntentData();
                        }
                    }).show();
                }
            }
        });
    }

    /**
     * 设置时间显示
     */
    private void setIntentData() {
        tvNumber.setText("共" + (getIntent().getIntExtra("count", -1) == -1 ? 0 :
                getIntent().getIntExtra("count", -1)) + "晚");
        String s = "入住\n" + getIntent().getStringExtra("fistTime");
        Text_Size.setSize(App.getContext(), tvRuzhu, s, 0, 2, "#bb646464", 13, 2, s.length(), "#05a880", 14);
        String ss = "离店\n" + getIntent().getStringExtra("lastTime");
        Text_Size.setSize(App.getContext(), tvLidian, ss, 0, 2, "#bb646464", 13, 2, ss.length(), "#05a880", 14);
    }

    /**
     * 监听器
     */
    private void setListener() {
        tvPhone.setOnClickListener(new View.OnClickListener() {//打电话
            @Override
            public void onClick(View v) {
                if (data != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel:" + data.getStore_phone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        ivMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaiduMapActivity.startBaiduMapActivity(HotelStorInfoActivity.this,
                        data.getLat(), data.getLng());
            }
        });
        scrollView.setaddScrollViewChangedListener(new PullToZoomScrollViewEx.addScrollViewChangedListener() {

            @Override
            public void onInternalScrollChanged(int left, int top, int oldLeft, int oldTop) {
                LogUtils.d("top" + top);
                if (255 > (top / 2)) {
                    ivshoucang_w.getBackground().setAlpha(255 - top / 2);
                    drawable_rl.setAlpha(top / 2);
                    ivbackW.setAlpha(1000f - (top / 2) * 4);
                    ivbackB.setAlpha((top / 2 / 1000f * 3.5f));
                    ivshoucang_w.setAlpha(1000f - (top / 2) * 4);
                    ivshoucang_b.setAlpha((top / 2 / 1000f * 3.5f));
                    ivbackW.getBackground().setAlpha(255 - top / 2);
                } else {
                    drawable_rl.setAlpha(255);
                    ivbackW.setAlpha(0f);
                    ivbackB.setAlpha(1f);
                    ivshoucang_w.setAlpha(0f);
                    ivshoucang_b.setAlpha(1f);
                    ivbackW.getBackground().setAlpha(0);
                    ivshoucang_w.getBackground().setAlpha(0);
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
        tvBottomPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShangjiaPingjiaActivity.startShangjiaPingjiaActivity(HotelStorInfoActivity.this,
                        getIntent().getStringExtra("stor_id"));
            }
        });
        tvPingfen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShangjiaPingjiaActivity.startShangjiaPingjiaActivity(HotelStorInfoActivity.this,
                        getIntent().getStringExtra("stor_id"));
            }
        });


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
                (R.layout.activity_hotel_storinfo_content, null);
        scrollView.setScrollContentView(llcontent);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth,
                (int) (9.0F * (mScreenWidth / 10.0F)));
        scrollView.setHeaderLayoutParams(localObject);
        //初始化组件
        progressBar = (ProgressBar) headView.findViewById(R.id.hendview_pro);

        ivMap = (ImageView) llcontent.findViewById(R.id.hotel_storinfo_mapview);
        tvAddress = (TextView) llcontent.findViewById(R.id.hotel_storinfo_tvaddress);
        tvPingfen = (TextView) llcontent.findViewById(R.id.hotel_storinfo_pingfen);
        tvRuzhu = (TextView) llcontent.findViewById(R.id.hotel_storinfo_tvrutime);
        tvNumber = (TextView) llcontent.findViewById(R.id.hotel_storinfo_tvnumber);
        tvLidian = (TextView) llcontent.findViewById(R.id.hotel_storinfo_tvlidiantime);
        tvPhone = (TextView) llcontent.findViewById(R.id.hotel_storinfo_tvphone);
        llTime = (LinearLayout) llcontent.findViewById(R.id.hotel_storinfo_llnumber);
        listview = (MyListview) llcontent.findViewById(R.id.hotel_storinfo_listview);
        tvBottomPin = (TextView) llcontent.findViewById(R.id.hotel_storinfo_tvpingjia);
    }

    /**
     * 初始化
     */
    private void init() {
        model = new HotelModel();
        drawable_rl = rltop.getBackground();
        drawable_rl.setAlpha(0);
        ivbackB.setAlpha(0f);
        ivshoucang_b.setAlpha(0f);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && boorhight != 0 && isFist) {
            isFist = false;
            ViewGroup.LayoutParams layoutParams = rltop.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.height = (int) (getResources().getDimension(R.dimen.pading_46)
                        + boorhight);
                rltop.setLayoutParams(layoutParams);
                rltop.setPadding(Utils.dip2px(App.getContext(), 10),
                        boorhight, Utils.dip2px(App.getContext(), 10), 0);
            }
        }
    }

    /**
     * 启动酒店店家详情页
     *
     * @param context 上下文
     * @param stor_id 店家id
     */
    public static void startHotelStorInfoActivity(Context context, String stor_id,
                                                  String fistTime, String lastTime,
                                                  int count, int classsid) {
        Intent intent = new Intent(context, HotelStorInfoActivity.class);
        intent.putExtra("stor_id", stor_id);
        intent.putExtra("fistTime", fistTime);
        intent.putExtra("lastTime", lastTime);
        intent.putExtra("count", count);
        intent.putExtra("classsid", classsid);
        context.startActivity(intent);
    }

    @OnClick({R.id.hotelstorinfo_ivback_wr, R.id.hotelstorinfo_ivback_banlk,
            R.id.hotelstorinfo_ivshoucang_wr, R.id.hotelstorinfo_ivshoucang_b})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hotelstorinfo_ivback_wr://返回
                finish();
                break;
            case R.id.hotelstorinfo_ivback_banlk://返回
                finish();
                break;
            case R.id.hotelstorinfo_ivshoucang_wr://收藏
                toShouCang();
                break;
            case R.id.hotelstorinfo_ivshoucang_b://收藏
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
        Call call = new StorModel().toShoCang(App.getUserId(), getIntent().getStringExtra("stor_id"), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.dd(obj);
                if (String.valueOf(obj).contains("收藏")) {
                    ivshoucang_w.setSelected(true);
                    ivshoucang_b.setSelected(true);
                } else {
                    ivshoucang_w.setSelected(false);
                    ivshoucang_b.setSelected(false);
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
