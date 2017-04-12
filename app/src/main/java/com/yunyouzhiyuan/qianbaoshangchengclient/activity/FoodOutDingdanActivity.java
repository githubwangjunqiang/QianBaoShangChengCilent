package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.adapter.FoodOutDingdanAdapter;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Address;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.DingDanOver;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.FoodDingdan;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.FoodInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.PaymentRequest;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.FoodModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.PingModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.MyListview;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DiaLogDingdanOk;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Text_Size;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.DING_ADDRESS;
import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.FOODCODE;
import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.YOUHUIQUAN;


public class FoodOutDingdanActivity extends BaseActivity {

    @Bind(R.id.fooddingdan_title)
    TitleLayout title;
    @Bind(R.id.fooddingdan_tvaddres)
    TextView tvAddress;
    @Bind(R.id.fooddingdan_tvyouhuiquan)
    TextView tvYouhuiquan;
    @Bind(R.id.fooddingdan_tvstorname)
    TextView tvStoreName;
    @Bind(R.id.fooddingdan_listview)
    MyListview listview;
    @Bind(R.id.fooddingdan_tvprice)
    TextView tvPrice;
    @Bind(R.id.fooddingdan_tvbuttom_price)
    TextView tvBottomPrice;
    @Bind(R.id.fooddingdanscrollview)
    ScrollView scrollView;
    @Bind(R.id.fooddingdan_btnok)
    Button btnOk;
    @Bind(R.id.fooddingdan_rg0)
    RadioButton fooddingdanRg0;
    @Bind(R.id.fooddingdan_rg1)
    RadioButton fooddingdanRg1;
    @Bind(R.id.fooddingdan_rg)
    RadioGroup fooddingdanRg;
    private FoodOutDingdanAdapter adapter;
    private String address_id;
    private String youhuiquan_id;
    private FoodModel model;
    private LoadingDialog loadingDialog;
    private List<FoodInfo.DataBean.InfoBean> datass = new ArrayList<>();

    @Override
    protected void onDestroy() {
        adapter = null;
        address_id = null;
        model = null;
        datass.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_out_dingdan);
        ButterKnife.bind(this);
        String storName = getIntent().getStringExtra("storName");
        String price = getIntent().getStringExtra("price");
        String stor_id = getIntent().getStringExtra("stor_id");
        List<FoodInfo.DataBean> list = (List<FoodInfo.DataBean>) getIntent().getSerializableExtra("list");
        if (TextUtils.isEmpty(storName) || TextUtils.isEmpty(price) || TextUtils.isEmpty(stor_id) || list.isEmpty() || App.getContext() == null) {
            To.oo("传参有误");
            finish();
            return;
        }
        init(storName, price, list);
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    clearCall();
                    dialog.dismiss();
                }
                return false;
            }
        });
        fooddingdanRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.fooddingdan_rg0://支付宝
                        channel = ZHI;
                        break;
                    case R.id.fooddingdan_rg1://微信
                        channel = WE;
                        break;
                }
            }
        });
    }

    /**
     * 初始化
     *
     * @param storName
     * @param price
     * @param list
     */
    private void init(String storName, String price, List<FoodInfo.DataBean> list) {
        title.setTitle("提交订单", true);
        title.setCallback(new TitleLayout.Callback(this));
        tvStoreName.setText("\t" + storName);
        tvPrice.setText("￥" + price);
        tvBottomPrice.setText("待支付\t￥:" + price);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getInfo().size(); j++) {
                if (list.get(i).getInfo().get(j).getCount() > 0) {
                    datass.add(list.get(i).getInfo().get(j));
                }
            }
        }
        adapter = new FoodOutDingdanAdapter(this, datass);
        listview.setAdapter(adapter);
        Address.DataBean dataBean = Bean.readAdress();
        if (null != dataBean) {
            setAddress(dataBean);
        }
        model = new FoodModel();
        loadingDialog = new LoadingDialog(this);
        scrollView.smoothScrollTo(0, 0);
    }

    @OnClick({R.id.fooddingdan_tvaddres, R.id.fooddingdan_tvyouhuiquan, R.id.fooddingdan_btnok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fooddingdan_tvaddres://点击添加地址
                startActivityForResult(new Intent(FoodOutDingdanActivity.this, AddressActivity.class).putExtra("isdingdan", true), DING_ADDRESS);
                break;
            case R.id.fooddingdan_tvyouhuiquan://点击优惠券
                YouhuiquanDingdanActivity.startYouhuiquanDingdanActivity(this, App.getUserId(), getIntent().getStringExtra("price"), getIntent().getStringExtra("stor_id"));
                break;
            case R.id.fooddingdan_btnok://提交
                tijiao(false);
                break;
        }
    }

    /**
     * 点击提交
     */
    private void tijiao(final boolean isSubMit) {
        if (TextUtils.isEmpty(address_id)) {
            To.oo("请选择地址");
            return;
        }
        if (datass.size() < 1) {
            To.oo("服务器异常，数据丢失");
            return;
        }
        List<FoodDingdan> datas = new ArrayList<>();
        for (int i = 0; i < datass.size(); i++) {
            FoodDingdan data = new FoodDingdan();
            data.setGoods_id(datass.get(i).getGoods_id());
            data.setGoods_num(datass.get(i).getCount() + "");
            datas.add(data);
        }


        String fromData = new Gson().toJson(datas);
        LogUtils.d("外卖订单json 数据" + fromData);
        loadingDialog.show();
        Call call = model.toDingdan(App.getUserId(), getIntent().getStringExtra("price"),
                getIntent().getStringExtra("stor_id")
                , address_id, youhuiquan_id, fromData, isSubMit, new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        if (isSubMit) {
                            zhiFu(String.valueOf(obj));
                        } else {
                            toOkDingdan(obj);
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

    /**
     * 确认订单
     *
     * @param obj
     */
    private void toOkDingdan(Object obj) {
        DingDanOver.DataBean data = (DingDanOver.DataBean) obj;

        new DiaLogDingdanOk(this, new DiaLogDingdanOk.CallBack() {
            @Override
            public void callOk() {
                tijiao(true);
            }

            @Override
            public void callOver() {

            }
        }, data).show();
    }

    /**
     * 启动activity
     */
    public static void startFoodOutDingdanActivity(Activity context, String storName, String price, String stor_id, List<FoodInfo.DataBean> list) {
        Intent intent = new Intent(context, FoodOutDingdanActivity.class);
        intent.putExtra("storName", storName);
        intent.putExtra("price", price);
        intent.putExtra("stor_id", stor_id);
        intent.putExtra("list", (Serializable) list);
        context.startActivityForResult(intent, FOODCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case DING_ADDRESS://地址返回
                if (resultCode == DING_ADDRESS) {
                    Address.DataBean data = (Address.DataBean) intent.getSerializableExtra("address");
                    setAddress(data);
                }
                break;
            case YOUHUIQUAN://优惠券返回
                if (resultCode == YOUHUIQUAN) {
                    if (intent == null) {
                        tvYouhuiquan.setText("没有可用的优惠券");
                    } else {
                        youhuiquan_id = intent.getStringExtra("youhuiquan_id");
                        tvYouhuiquan.setText("您已经选择本店的优惠券了，在结算的时候，会自动使用优惠券");
                    }
                }
                break;
            default:
                break;
        }
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                boolean isfinsh = false;
                String title = null;
                String result = intent.getExtras().getString("pay_result");
                if ("success".equals(result)) {
                    title = "支付成功";
                    isfinsh = true;
                } else if ("fail".equals(result)) {
                    title = "支付失败";
                } else if ("cancel".equals(result)) {
                    title = "取消支付";
                } else if ("invalid".equals(result)) {
                    title = "支付插件未安装（微信客户端是否安装？）";
                }
                // 处理返回值
                // "success" - 支付成功
                // "fail"    - 支付失败
                // "cancel"  - 取消支付
                // "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
                String errorMsg = intent.getExtras().getString("error_msg"); // 错误信息
                showMsg(title, errorMsg, isfinsh);
            }
        }
    }

    /**
     * 显示 支付返回 对话框
     *
     * @param result
     * @param extraMsg
     * @param isfinsh
     */
    private void showMsg(String result, String extraMsg, final boolean isfinsh) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isfinsh) {
                    setResult(FOODCODE);
                    finish();
                }
            }
        });
        builder.setTitle("黔宝商城提醒您：");
        builder.setMessage(isfinsh ? result : "支付失败" );
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    /**
     * 写入地址
     *
     * @param data
     */
    private void setAddress(Address.DataBean data) {
        address_id = data.getAddress_id();
        String name = "\t\t" + data.getConsignee() + "\t\t" + data.getMobile() + "\n\t\t";
        String str = name + data.getAddress_name() + "\t\t" + data.getAddress();
        Text_Size.setSize(null, tvAddress, str, 0, name.length(), "#646464", 14, name.length(), str.length(), "#bb646464", 12);
    }

    private String WE = "wx";//微信
    private String ZHI = "alipay";//支付宝
    private String channel = ZHI;

    /**
     * 跳转支付
     *
     * @param obj
     */
    private void zhiFu(String obj) {
        loadingDialog.show();
        PaymentRequest request = new PaymentRequest(channel,
                Double.valueOf(getIntent().getStringExtra("price")), obj);

        String json = new Gson().toJson(request);
        LogUtils.i("支付传参json" + json);
        if (TextUtils.isEmpty(json)) {
            To.ee("解析channel错误请重试");
            loadingDialog.dismiss();
            return;
        }
        setCall(new PingModel().getJson(json, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (TextUtils.isEmpty(String.valueOf(obj))) {
                    To.ee("URL无法获取charge");
                } else {
                    Pingpp.createPayment(FoodOutDingdanActivity.this, String.valueOf(obj));
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object obj) {
                To.ee("URL无法获取charge");
                loadingDialog.dismiss();
            }
        }));
    }
}
