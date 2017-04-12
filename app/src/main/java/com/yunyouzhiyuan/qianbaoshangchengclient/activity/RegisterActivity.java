package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.LoginModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Time_down;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.Title_layout)
    TitleLayout titleLayout;
    @Bind(R.id.register_username)
    EditText registerUsername;
    @Bind(R.id.register_code)
    EditText registerCode;
    @Bind(R.id.register_pass)
    EditText registerPass;
    @Bind(R.id.register_layout)
    TextInputLayout registerLayout;
    @Bind(R.id.register_pass1)
    EditText registerPass1;
    @Bind(R.id.register_layout1)
    TextInputLayout registerLayout1;
    @Bind(R.id.register_btnok)
    Button registerBtnok;
    @Bind(R.id.register_tvcode)
    TextView tvcode;
    private Time_down time_down;
    private LoadingDialog loadingDialog;
    private LoginModel model;

    @Override
    protected void onDestroy() {
        titleLayout = null;
        if (time_down != null) {
            time_down.cancel();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
        setListener();
    }

    private void setListener() {
        tvcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCode();
            }
        });
        registerBtnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toregister();
            }
        });
    }

    /**
     * 去注册
     */
    private void toregister() {
        final String phone = registerUsername.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            To.oo("电话号码为空");
            return;
        }
        String code = registerCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            To.oo("验证码为空");
            return;
        }
        final String pas = registerPass.getText().toString().trim();
        if (TextUtils.isEmpty(pas)) {
            To.oo("请设置密码");
            return;
        }
        String pas1 = registerPass1.getText().toString().trim();
        if (TextUtils.isEmpty(pas1)) {
            To.oo("请确认密码");
            return;
        }
        if (!TextUtils.equals(pas, pas1)) {
            To.oo("确认密码和设置密码两次输入不一致");
            return;
        }
        loadingDialog.show();
        Call uregister = model.uregister(phone, pas, pas1, code, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(final Object obj) {
                if (isFinishing()) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        To.oo(obj);
                        Intent intent = getIntent();
                        intent.putExtra("phone", phone);
                        intent.putExtra("pas", pas);
                        setResult(RESULT_OK, intent);
                        loadingDialog.dismiss();
                        finish();
                    }
                });
            }

            @Override
            public void onError(final Object obj) {
                if (isFinishing()) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                        To.oo(obj);
                    }
                });
            }
        });
        setCall(uregister);
    }

    /**
     * 获取验证码
     */
    private void loadCode() {
        String phone = registerUsername.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            To.oo("请填写手机号码");
            return;
        }
        loadingDialog.show();
        Call code = model.code(phone, new IModel.AsyncCallBack() {

            @Override
            public void onSucceed(final Object object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        To.oo(object);
                        if (time_down != null) {
                            time_down.cancel();
                        }
                        time_down = new Time_down(60 * 1000, 1 * 1000, tvcode);
                        time_down.start();
                        loadingDialog.dismiss();
                    }
                });
            }

            @Override
            public void onError(final Object object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        To.oo(object);
                        loadingDialog.dismiss();
                    }
                });
            }
        });
        setCall(code);
    }


    /**
     * 初始化
     */
    private void init() {
        titleLayout.setCallback(new TitleLayout.Callback(this));
        titleLayout.setTitle("注册", true);
        loadingDialog = new LoadingDialog(this);
        model = new LoginModel();
        String phone = getIntent().getStringExtra("phone");
        registerUsername.setText(phone);
    }
}
