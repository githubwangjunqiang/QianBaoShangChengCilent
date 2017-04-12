package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.DingDanOver;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTVToDingdan;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Order_So;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.PaymentRequest;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.DingDanModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.PingModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.DiaLogDingdanOk;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.FOODCODE;
import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.YOUHUIQUAN;

public class ToDingdanActivity extends BaseActivity {

    @Bind(R.id.todingdan_tvname)
    TextView tvname;
    @Bind(R.id.todingdan_tvdanjia)
    TextView tvdanjia;
    @Bind(R.id.todingdan_tvxiaoji)
    TextView tvxiaoji;
    @Bind(R.id.todingdan_tvyouhuiquan)
    TextView tvYouhuiquan;
    @Bind(R.id.todingdan_tvzongjia)
    TextView tvzongjia;
    @Bind(R.id.todingdan_tvnumber)
    TextView tvNumber;
    @Bind(R.id.tijiaodingdan_title)
    TitleLayout titleLayout;
    @Bind(R.id.todingdan_tvyouhui2)
    TextView tvyouhui2;
    @Bind(R.id.todingdan_etcontent)
    EditText etContent;
    @Bind(R.id.todingdan_rg)
    RadioGroup todingdanRg;
    @Bind(R.id.todingdan_btntijiao)
    Button todingdanBtntijiao;
    private String youhuiquan_id;
    private DingDanModel model;
    private LoadingDialog loadingDialog;

    private String WE = "wx";//微信
    private String ZHI = "alipay";//支付宝
    private String channel = ZHI;

    public static void startToDingdanActivity(Activity context, String goodsname,
                                              String storid, String goods_id,
                                              String goods_number, String price,
                                              String zongPrice, KTVToDingdan data) {
        Intent intent = new Intent(context, ToDingdanActivity.class);
        intent.putExtra("goods_id", goods_id);
        intent.putExtra("storid", storid);
        intent.putExtra("goods_number", goods_number);
        intent.putExtra("price", price);
        intent.putExtra("zongPrice", zongPrice);
        intent.putExtra("goodsname", goodsname);
        intent.putExtra("data", data);
        context.startActivityForResult(intent, Bean.FOODCODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_dingdan);
        ButterKnife.bind(this);

        if (TextUtils.isEmpty(getIntent().getStringExtra("goods_id")) ||
                TextUtils.isEmpty(getIntent().getStringExtra("goods_number")) ||
                TextUtils.isEmpty(getIntent().getStringExtra("price")) ||
                TextUtils.isEmpty(getIntent().getStringExtra("zongPrice")) ||
                TextUtils.isEmpty(getIntent().getStringExtra("goodsname"))) {
            To.oo("本地参数有误");
            finish();
            return;
        }
        init();
        setListener();

    }

    /**
     * 监听器
     */
    private void setListener() {
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 50) {
                    To.oo("只能输入50个字符");
                }
            }
        });
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    clearCall();
                    To.oo("请求被您取消了");
                }
                return false;
            }
        });
        todingdanRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.todingdan_rg0://支付宝
                        channel = ZHI;
                        break;
                    case R.id.todingdan_rg1://微信
                        channel = WE;
                        break;
                }
            }
        });
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    clearCall();
                }
                return false;
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        titleLayout.setTitle(getResources().getString(R.string.tijiaodingdao), true);
        titleLayout.setCallback(new TitleLayout.Callback(this));
        tvname.setText(getIntent().getStringExtra("goodsname"));
        tvdanjia.setText("￥:" + getIntent().getStringExtra("price"));
        tvNumber.setText(getIntent().getStringExtra("goods_number") + "件");
        tvxiaoji.setText("￥:" + getIntent().getStringExtra("price"));
        tvyouhui2.setText("(未使用优惠券)");
        tvzongjia.setText("￥:" + getIntent().getStringExtra("zongPrice"));

        model = new DingDanModel();
        loadingDialog = new LoadingDialog(this);
    }

    @OnClick({R.id.todingdan_tvyouhuiquan, R.id.todingdan_btntijiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.todingdan_tvyouhuiquan://优惠券
                if (tvYouhuiquan.getText().toString().trim().contains("点击清除")) {
                    tvYouhuiquan.setText("选择优惠券");
                    youhuiquan_id = null;
                    tvyouhui2.setText("未使用优惠券");
                } else {
                    YouhuiquanDingdanActivity.startYouhuiquanDingdanActivity(this, App.getUserId(), getIntent().getStringExtra("price"), getIntent().getStringExtra("storid"));
                }
                break;
            case R.id.todingdan_btntijiao://提交
                toOutDingdan(false);
                break;
        }
    }

    /**
     * 提交订单
     *
     * @param isTow true=提交订单   false=确认订单（finish）
     */
    private void toOutDingdan(final boolean isTow) {
        String time = null;
        String sp = null;
        if (getIntent().getSerializableExtra("data") != null) {
            KTVToDingdan data = (KTVToDingdan) getIntent().getSerializableExtra("data");
            time = data.getTime();
            sp = data.getGoods_spec();
            LogUtils.d("类型事=" + sp);
        }
        loadingDialog.show();
        Call call = model.outDingDan(App.getUserId(), getIntent().getStringExtra("goods_id")
                , getIntent().getStringExtra("goods_number"),
                etContent.getText().toString().trim(), youhuiquan_id, isTow, sp, time,
                new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        loadingDialog.dismiss();
                        if (isTow) {
                            zhiFu(((Order_So.DataBean) obj).getMaster_order_sn());
                        } else {
                            toOkDingdan(obj);
                        }
                    }

                    @Override
                    public void onError(Object obj) {
                        if (isFinishing()) {
                            return;
                        }
                        To.ee(obj);
                        loadingDialog.dismiss();
                    }
                });
        setCall(call);
    }

    /**
     * 支付
     *
     * @param master_order_sn
     */
    private void zhiFu(String master_order_sn) {
        loadingDialog.show();
        PaymentRequest request = new PaymentRequest(channel,
                Double.parseDouble(getIntent().getStringExtra("price")), master_order_sn);
        String json = new Gson().toJson(request);
        if (TextUtils.isEmpty(json)) {
            To.ee("解析channel错误请重试");
            loadingDialog.dismiss();
            return;
        }
        setCall(new PingModel().getJson(json, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                if (TextUtils.isEmpty(String.valueOf(obj))) {
                    To.ee("URL无法获取charge");
                } else {
                    Pingpp.createPayment(ToDingdanActivity.this, String.valueOf(obj));
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.ee("URL无法获取charge");
                loadingDialog.dismiss();
            }
        }));
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
                toOutDingdan(true);
            }

            @Override
            public void callOver() {

            }
        }, data).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case YOUHUIQUAN://优惠券返回
                if (resultCode == YOUHUIQUAN || data != null) {
                    youhuiquan_id = data.getStringExtra("youhuiquan_id");
                    String name = data.getStringExtra("name");
                    tvyouhui2.setText("(使用一张优惠券:" + name + ")");
                    tvYouhuiquan.setText("已选择优惠券，点击清除");
                }
                break;
        }
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                boolean isfinsh = false;
                String title = null;
                String result = data.getExtras().getString("pay_result");
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
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
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
        builder.setMessage(isfinsh ? result + "\n您可以在我的订单中查看订单详情和验证二维码" : "支付失败");
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
