package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.PaymentRequest;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.PingModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import okhttp3.Call;


/**
 * Created by bob on 2015/1/28.
 */
public class PingDialog extends Dialog {


    private RadioButton rbZhi;
    private RadioButton rbWeixin;
    private RadioGroup radioGroup;
    private TextView tvPrice;
    private Button btnOk;
    private String order_amount;//总价
    private String order_sn;//订单号
    private String WE = "wx";//微信
    private String ZHI = "alipay";//支付宝
    private String channel = ZHI;
    private Call call;
    private Callback callback;
    private LoadingDialog loadingDialog;

    public PingDialog(Context context, String order_amount, String order_sn, Callback callback) {
        super(context, R.style.WinDialog);
        this.order_amount = order_amount;
        this.order_sn = order_sn;
        loadingDialog = new LoadingDialog(context);
        this.callback = callback;
        setContentView(R.layout.dialog_ping);
    }

    public interface Callback {
        void onSuccess(String obj);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        rbZhi = (RadioButton) findViewById(R.id.dialog_ping_rb0);
        rbWeixin = (RadioButton) findViewById(R.id.dialog_ping_rb1);
        radioGroup = (RadioGroup) findViewById(R.id.dialog_ping_rg);
        tvPrice = (TextView) findViewById(R.id.dialog_ping_tvprice);
        btnOk = (Button) findViewById(R.id.dialog_ping_btn);
        tvPrice.setText("订单金额：" + order_amount);
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhiFu();
            }
        });
        loadingDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (call != null && !call.isCanceled()) {
                        call.cancel();
                        call = null;
                    }
                }
                return false;
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.dialog_ping_rb0://支付宝
                        channel = ZHI;
                        break;
                    case R.id.dialog_ping_rb1://微信
                        channel = WE;
                        break;
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        loadingDialog = null;
        if (call != null && !call.isCanceled()) {
            call.cancel();
            call = null;
        }
        super.dismiss();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return false;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 支付
     */
    private void zhiFu() {
        loadingDialog.show();
        PaymentRequest request = new PaymentRequest(channel,
                Double.parseDouble(order_amount), order_sn);
        String json = new Gson().toJson(request);
        LogUtils.d("支付json串"+json);
        if (TextUtils.isEmpty(json)) {
            To.ee("解析channel错误请重试");
            loadingDialog.dismiss();
            return;
        }
        call = new PingModel().getJson(json, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (TextUtils.isEmpty(String.valueOf(obj))) {
                    To.ee("URL无法获取charge");
                    loadingDialog.dismiss();
                } else {
                    if (callback != null) {
                        callback.onSuccess(String.valueOf(obj));
                        loadingDialog.dismiss();
                        dismiss();
                    }
                }
            }

            @Override
            public void onError(Object obj) {
                To.ee("URL无法获取charge");
                loadingDialog.dismiss();
            }
        });
    }

}
