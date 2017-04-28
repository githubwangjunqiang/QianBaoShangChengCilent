package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.Foodout_listview_adapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.FoodInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Freight;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.FoodModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.StorModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.AFloatListView;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.BadgeView;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.ShoppingCartAnim;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DialogCartFood;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.foodout.PinnedHeaderListView;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.foodout.TestSectionedAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.DecimalCalculate;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.glide_image.ToGlide;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.FOODCODE;

public class FoodOutInfoActivity extends BaseActivity {
    private LinearLayout lltop, llView, llbottom, ll_Waiwei;
    private RelativeLayout rllbar;
    private TextView tvcaipin, tvStorname, tvFreight;
    private AFloatListView left_listView;
    private PinnedHeaderListView right_listview;
    private boolean isScroll = true;
    private Foodout_listview_adapter adapter;
    private TextView tvtitle, tvShouCang;
    private ImageView ivBack, ivshoucang;
    private ImageView ivPhoto;
    private String store_id;//店铺id
    private LoadingDialog loadingDialog;
    private FoodModel model;
    private List<FoodInfo.DataBean> list_left = new ArrayList<>();
    private boolean isFist = true;
    private TestSectionedAdapter sectionedAdapter;
    private ShoppingCartAnim shoppingCartAnim;
    private ImageView ivCart;
    private BadgeView badge;
    private Button btnBuy;
    private double zong_Price;
    private int zong_count;
    private StorInfo.DataBean storInfo;
    private double send_price;//起送价格

    @Override
    protected void onDestroy() {
        model = null;
        store_id = null;
        zong_Price = 0.0;
        list_left.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        store_id = getIntent().getStringExtra("store_id");
        if (TextUtils.isEmpty(store_id)) {
            To.oo("店铺id为空");
            finish();
            return;
        }
        if (TextUtils.isEmpty(App.getUserId())) {
            To.oo("请先登录");
            finish();
            return;
        }
        init();
        getData();
        setListener();
    }

    /**
     * 隐藏刷新布局
     */
    private int countLayout;

    private void hideLayout() {
        countLayout--;
        if (countLayout < 1) {
            countLayout = 0;
            loadingDialog.dismiss();
        }
    }

    /**
     * 获取信息
     */
    private void getData() {
        loadingDialog.show();
        countLayout = 4;
        getStorinfo();
        getList();
        getShouCang();
        getFreight();
    }

    /**
     * 获取
     */
    private void getFreight() {
        model.get_send_price(getIntent().getStringExtra("store_id"), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                List<Freight.DataBean> obj1 = (List<Freight.DataBean>) obj;
                send_price = Double.parseDouble(obj1.get(0).getSend_price()==null?"0":obj1.get(0).getSend_price());
                String shipping_price = obj1.get(0).getShipping_price();
                if (!TextUtils.isEmpty(shipping_price)) {
                    tvFreight.setText("配送费：￥：" + shipping_price);
                }
                if (send_price > 0) {
                    btnBuy.setText("￥" + send_price + "起送");
                }
                hideLayout();
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.ee(obj);
                hideLayout();
            }
        });
    }

    /**
     * 获取店家详情信息
     */
    private void getStorinfo() {
        setCall(new StorModel().getStorInfo(getIntent().getStringExtra("store_id"), App.getUserId(),
                new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        storInfo = ((StorInfo) obj).getData();
                        setTopView();
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
     * 获取收藏标识
     */
    private void getShouCang() {
        Call shouCang = model.getShouCang(store_id, App.getUserId(), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                tvShouCang.setText((int) obj == 1 ? "已收藏" : "收藏");
                ivshoucang.setSelected((int) obj == 1);
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.oo(obj);
            }
        });
        setCall(shouCang);
    }

    /**
     * 获取 外卖列表
     */
    private void getList() {
        loadingDialog.show();
        Call list = model.getList(store_id, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                list_left.clear();
                list_left.addAll((List<FoodInfo.DataBean>) obj);
                setData();
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
        setCall(list);
    }

    /**
     * 启动activity
     *
     * @param context     上下文
     * @param description 店铺描述
     */
    public static void startFoodOutInfoActivity(Context context, String description,
                                                String store_id) {
        Intent intent = new Intent(context, FoodOutInfoActivity.class);
        intent.putExtra("description", description);
        intent.putExtra("store_id", store_id);
        context.startActivity(intent);
    }

    /**
     * 添加数据 添加适配器
     */
    private void setData() {
        if (sectionedAdapter == null) {
            if (!list_left.isEmpty()) {
                sectionedAdapter = new TestSectionedAdapter(this, list_left, new TestSectionedAdapter.Callback() {
                    @Override
                    public void addCart(View v, int section, int position) {
                        toCount(true, list_left.get(section).getInfo().get(position));
                        shoppingCartAnim.startAnim(v, ivCart);
                        sectionedAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void deleteCart(View v, int section, int position) {
                        toCount(false, list_left.get(section).getInfo().get(position));
                        sectionedAdapter.notifyDataSetChanged();

                    }
                });
                right_listview.setAdapter(sectionedAdapter);
//                right_listview.setFriction(ViewConfiguration.getScrollFriction() * 8);
            }
        } else {
            sectionedAdapter.notifyDataSetChanged();
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            if (!list_left.isEmpty()) {
                list_left.get(0).setXuanzhong(true);

                adapter = new Foodout_listview_adapter(this, list_left, new Foodout_listview_adapter.Callback() {
                    @Override
                    public void onClick(int position) {
                        isScroll = false;
                        for (int i = 0; i < list_left.size(); i++) {
                            list_left.get(i).setXuanzhong(false);
                        }
                        list_left.get(position).setXuanzhong(true);
                        adapter.notifyDataSetChanged();
                        int rightSection = 0;
                        for (int i = 0; i < position; i++) {
                            rightSection += sectionedAdapter.getCountForSection(i) + 1;
                        }
                        right_listview.setSelection(rightSection);
                    }
                });
                left_listView.setAdapter(adapter);
            }
        }
    }

    /**
     * 点击添加或者 删除 购物项
     *
     * @param addCart 是否添加购物项
     */
    private void toCount(boolean addCart, FoodInfo.DataBean.InfoBean data) {
        if (null == data) {
            ivCart.setSelected(false);
            btnBuy.setSelected(false);
            btnBuy.setText(send_price + "元起送");
            zong_Price = 0.0;
            zong_count = 0;
            badge.setBadgeCount(zong_count);
            return;
        }
        if (addCart) {
            int count = data.getCount();
            count++;
            data.setCount(count);
            double price = Double.parseDouble(data.getShop_price());
            zong_Price = DecimalCalculate.add(zong_Price, price);
            if (zong_Price >= send_price) {
                btnBuy.setText("￥" + zong_Price);
                if (!btnBuy.isSelected()) {
                    btnBuy.setSelected(true);
                }
            } else {
                btnBuy.setText("还差￥" + DecimalCalculate.sub(send_price, zong_Price));
            }


            if (!ivCart.isSelected()) {
                ivCart.setSelected(true);
            }

            zong_count++;
        } else {
            int count = data.getCount();
            count--;
            data.setCount(count);
            zong_count--;
            if (zong_count < 1) {
                ivCart.setSelected(false);
                btnBuy.setSelected(false);
                btnBuy.setText(send_price + "元起送");
                zong_Price = 0.0;
            } else {
                double price = Double.parseDouble(data.getShop_price());
                zong_Price = DecimalCalculate.sub(zong_Price, price);
                if (zong_Price >= send_price) {
                    btnBuy.setText("￥" + zong_Price);
                } else {
                    btnBuy.setSelected(false);
                    btnBuy.setText("还差￥" + DecimalCalculate.sub(send_price, zong_Price));
                }
            }
        }
        badge.setBadgeCount(zong_count);
    }

    /**
     * 写入顶部店家信息
     */
    private void setTopView() {
        tvtitle.setText(storInfo.getStore_name() + "'");
        String stroname = storInfo.getStore_name() + "(" + storInfo.getLocation() + ")\n";
        String description = getIntent().getStringExtra("description");
        String str = stroname + (description == null ? "公告：量大优惠 欢迎惠顾" : description);
        Text_Size.setSize(null, tvStorname, str, 0, storInfo.getStore_name().length(),
                "#ffffff", 14, storInfo.getStore_name().length(), str.length(), "#ffffff", 12);
        ToGlide.urlCircle(this, HTTPURL.IMAGE + storInfo.getStore_logo(), ivPhoto);
    }

    /**
     * 监听器
     */
    private void setListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvShouCang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toShouCang();
            }
        });
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivCart.isSelected()) {//显示购物车
                    toCart();
                }
            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//提交
                if (btnBuy.isSelected() && zong_Price != 0 && zong_count != 0 && storInfo != null) {
                    FoodOutDingdanActivity.startFoodOutDingdanActivity(FoodOutInfoActivity.this,
                            storInfo.getStore_name(), zong_Price + "", store_id, list_left);
                }
            }
        });
        shoppingCartAnim.setCallBack(new ShoppingCartAnim.Listener() {
            @Override
            public void onAnimationEnd() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(ivCart,
                        "scaleX", 1, 1.1f, 0.9f, 1);
                animator.setDuration(300);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivCart,
                        "scaleY", 1, 0.75f, 1.25f, 1);
                animator1.setDuration(300);
                AnimatorSet set = new AnimatorSet();
                set.setInterpolator(new LinearInterpolator());
                set.playTogether(animator, animator1);
                set.start();
            }
        });
        tvcaipin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击评论
                ShangjiaPingjiaActivity.startShangjiaPingjiaActivity(FoodOutInfoActivity.this, store_id);
            }
        });

        right_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView arg0, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (right_listview.getLastVisiblePosition() == (right_listview.getCount() - 1)) {
                            left_listView.setSelection(ListView.FOCUS_DOWN);
                        }

                        // 判断滚动到顶部
                        if (right_listview.getFirstVisiblePosition() == 0) {
                        }

                        break;
                }
            }

            int y = 0;
            int x = 0;
            int z = 0;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < list_left.size(); i++) {
                        if (i == sectionedAdapter.getSectionForPosition(right_listview.getFirstVisiblePosition())) {
                            list_left.get(i).setXuanzhong(true);
                            x = i;
                        } else {
                            list_left.get(i).setXuanzhong(false);
                        }
                    }
                    if (x != y) {
                        adapter.notifyDataSetChanged();
                        y = x;
                        if (y == left_listView.getLastVisiblePosition()) {
                            z = z + 3;
                            left_listView.setSelection(z);
                        }
                        if (x == left_listView.getFirstVisiblePosition()) {
                            z = z - 1;
                            left_listView.setSelection(z);
                        }
                        if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                            left_listView.setSelection(ListView.FOCUS_DOWN);
                        }
                    }
                } else {
                    isScroll = true;
                }
            }
        });
    }

    /**
     * 查看购物车
     */
    private void toCart() {
        DialogCartFood dialogCartFood = new DialogCartFood(FoodOutInfoActivity.this, new DialogCartFood.CallBack() {
            @Override
            public void clearCart() {
                for (int i = 0; i < list_left.size(); i++) {
                    for (int j = 0; j < list_left.get(i).getInfo().size(); j++) {
                        list_left.get(i).getInfo().get(j).setCount(0);
                    }
                }
                sectionedAdapter.notifyDataSetChanged();
                toCount(false, null);
            }
        }, llbottom.getHeight(), list_left);
        dialogCartFood.show();
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
        Call call = new StorModel().toShoCang(App.getUserId(), store_id, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.dd(obj);
                if (String.valueOf(obj).contains("收藏")) {
                    tvShouCang.setText("已收藏");
                    ivshoucang.setSelected(true);
                } else {
                    tvShouCang.setText("收藏");
                    ivshoucang.setSelected(false);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isFist) {
            isFist = false;
            int f = lltop.getHeight();
            float dimension = getResources().getDimension(R.dimen.pading_46);
            float v = f - dimension;
            WindowManager wm = this.getWindowManager();
            int height = wm.getDefaultDisplay().getHeight();
//            int butsContainerHeight = (int) getResources().getDimension(R.dimen.buts_container_height);
            int butsContainerHeight = tvcaipin.getHeight();
//            ViewGroup.LayoutParams layoutParams = left_listView.getLayoutParams();
//            if (layoutParams != null) {
//                layoutParams.height = height - butsContainerHeight - getStatusBarHeight() - (rllbar.getHeight() + llbottom.getHeight());
//                left_listView.setLayoutParams(layoutParams);
//            }
//            ViewGroup.LayoutParams layoutParams1 = right_listview.getLayoutParams();
//            if (layoutParams1 != null) {
//                layoutParams1.height = height - butsContainerHeight - getStatusBarHeight() - (rllbar.getHeight() + llbottom.getHeight());
//                right_listview.setLayoutParams(layoutParams1);
//            }
            ViewGroup.LayoutParams layoutParams = ll_Waiwei.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.height = height - butsContainerHeight - getStatusBarHeight() - (rllbar.getHeight() + llbottom.getHeight());
                ll_Waiwei.setLayoutParams(layoutParams);
            }
            left_listView.setView(llView);
            left_listView.setCanScrollDistance((int) v);
            right_listview.setView(llView);
            right_listview.setCanScrollDistance((int) v);
        }
    }

    /**
     * 初始化组件
     */
    private void init() {
        loadingDialog = new LoadingDialog(this);
        model = new FoodModel();
        lltop = (LinearLayout) findViewById(R.id.food_out_info_topll);
        llView = (LinearLayout) findViewById(R.id.food_out_info_llview);
        ll_Waiwei = (LinearLayout) findViewById(R.id.food_info_llwaiwei);
        rllbar = (RelativeLayout) findViewById(R.id.food_out_info_llbar);
        tvcaipin = (TextView) findViewById(R.id.food_out_info_tvcaipin);
        tvtitle = (TextView) findViewById(R.id.food_top_tvtitle);
        left_listView = (AFloatListView) findViewById(R.id.left_listview);
        right_listview = (PinnedHeaderListView) findViewById(R.id.rite_lisview);
        llbottom = (LinearLayout) findViewById(R.id.foodinfo_llbuttom);
        ivBack = (ImageView) findViewById(R.id.food_top_ivback);
        ivPhoto = (ImageView) findViewById(R.id.food_ivphoto);
        ivCart = (ImageView) findViewById(R.id.food_ivcart);
        ivshoucang = (ImageView) findViewById(R.id.iv_shoucang);
        tvStorname = (TextView) findViewById(R.id.food_tvname);
        tvFreight = (TextView) findViewById(R.id.food_tvfreight);
        tvShouCang = (TextView) findViewById(R.id.food_tvshoucang);
        btnBuy = (Button) findViewById(R.id.food_btnbuy);

        badge = new BadgeView(this);
        badge.setTargetView(ivCart);
        shoppingCartAnim = new ShoppingCartAnim(this, R.drawable.cart, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FOODCODE && resultCode == FOODCODE) {
            finish();
        }
    }
}
