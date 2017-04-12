package com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.YueModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class Dialogwithdrawals extends BaseDiaLog {
    private CallBack callBack;
    private TextView tvPrice, tvBankName;
    private Button btnOk;
    private EditText etPrice, etBank, etUserName;
    private Button btnBankName;
    private String price;
    private LinearLayout llTop;
    private YueModel model;
    private List<Call> calls = new ArrayList<>();
    private LoadingDialog loadingDialog;
    private String bankName;

    /**
     * 操作view
     */
    private void init() {
        tvPrice = (TextView) findViewById(R.id.dialog_tixiantvprice);
        tvBankName = (TextView) findViewById(R.id.dialog_tixiantvbangkname);
        btnOk = (Button) findViewById(R.id.dialog_tixianbtn);
        btnBankName = (Button) findViewById(R.id.dialog_tixianbtnbankname);
        etPrice = (EditText) findViewById(R.id.dialog_tixianetnumber);
        etBank = (EditText) findViewById(R.id.dialog_tixianetbank);
        etUserName = (EditText) findViewById(R.id.dialog_tixianetname);
        llTop = (LinearLayout) findViewById(R.id.dialog_lltixian);
        tvBankName.setText("银行名称：");
        tvPrice.setText("我的余额：￥" + price);
        loadingDialog = new LoadingDialog(getContext());
    }

    public Dialogwithdrawals(Context context, CallBack callBack, String price, YueModel model) {
        super(context, R.style.dialogWindowAnim);
        this.callBack = callBack;
        this.price = price;
        this.model = model;
        setContentView(R.layout.dialog_tixian);
        setCanceledOnTouchOutside(false);
    }

    public interface CallBack {
        void onSuccess();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnOk.isSelected()) {
                    toWithDrawals();
                } else {
                    To.oo("先获取银行卡名称");
                }

            }
        });
        btnBankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLoadBankName();
            }
        });
        loadingDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                for (Call call : calls) {
                    call.cancel();
                }
                return false;
            }
        });
        etBank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnOk.setSelected(false);
                tvBankName.setText("银行名称：");
                bankName = null;

            }
        });
        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && Double.parseDouble(price) < Double.parseDouble(s.toString())) {
                    To.oo("不能超过您的余额");
                    etPrice.setText("");
                }
            }
        });
    }

    /**
     * 获取银行卡名称
     */
    private void toLoadBankName() {
        String trim = etBank.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            To.oo("请输入银行卡号");
            return;
        }
        loadingDialog.show();
        calls.add(model.loadBankName(trim, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                tvBankName.setText("银行名称：" + obj);
                bankName = (String) obj;
                btnOk.setSelected(true);
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object obj) {
                To.ee(obj);
                loadingDialog.dismiss();
            }
        }));
    }

    /**
     * 点击提交
     */
    private void toWithDrawals() {
        if (TextUtils.isEmpty(App.getUserId())) {
            To.oo("请登录");
            return;
        }
        String money = etPrice.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            To.ss(etPrice, "请输入金额");
            return;
        }
        if (TextUtils.isEmpty(bankName)) {
            To.ss(btnBankName, "请先核对银行名称");
            return;
        }

        String account_bank = etBank.getText().toString().trim();
        if (TextUtils.isEmpty(account_bank)) {
            To.ss(etBank, "请输入银行卡号");
            return;
        }
        String account_name = etUserName.getText().toString().trim();
        if (TextUtils.isEmpty(account_name)) {
            To.ss(etUserName, "请输入账户名");
            return;
        }
        loadingDialog.show();
        calls.add(model.apply_withdrawals(App.getUserId(), money, bankName, account_bank,
                account_name, new IModel.AsyncCallBack() {
                    @Override
                    public void onSucceed(Object obj) {
                        To.dd(obj);
                        loadingDialog.dismiss();
                        dismiss();
                        if (callBack != null) {
                            callBack.onSuccess();
                        }
                    }

                    @Override
                    public void onError(Object obj) {
                        To.ee(obj);
                        loadingDialog.dismiss();
                    }
                }));
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialogWindowAnim);
    }

    @Override
    public View setView() {
        return llTop;
    }

    @Override
    public void dismiss() {
        for (Call c : calls) {
            c.cancel();
        }
        calls.clear();
        calls = null;
        model = null;
        price = null;
        System.gc();
        super.dismiss();
    }


}
