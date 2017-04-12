package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.yunyouzhiyuan.qianbaoshangchengclient.util.MyAnimtor;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.Time_down;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.UPPASSWORD;

public class UnPassWordActivity extends BaseActivity {


    @Bind(R.id.unpass_title)
    TitleLayout unpassTitle;
    @Bind(R.id.un_username)
    EditText unUsername;
    @Bind(R.id.un_pass_code)
    EditText unPassCode;
    @Bind(R.id.un_tvcode)
    TextView unTvcode;
    @Bind(R.id.un_pass)
    EditText unPass;
    @Bind(R.id.pass_layout)
    TextInputLayout passLayout;
    @Bind(R.id.un_pass1)
    EditText unPass1;
    @Bind(R.id.pass_layout1)
    TextInputLayout passLayout1;
    @Bind(R.id.unpass_btnok)
    Button unpassBtnok;
    private String phone;
    private LoadingDialog loadingDialog;
    private LoginModel model;
    private CountDownTimer start;

    public static void startUnPassWordActivity(Activity context, String phone) {
        Intent intent = new Intent(context, UnPassWordActivity.class);
        if (!TextUtils.isEmpty(phone)) {
            intent.putExtra("phone", phone);
        }
        context.startActivityForResult(intent, UPPASSWORD);
    }

    @Override
    protected void onDestroy() {
        if (start != null) {
            start.cancel();
            start = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_pass_word);
        ButterKnife.bind(this);
        phone = getIntent().getStringExtra("phone");
        unUsername.setText(phone);
        init();
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        unTvcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });
        unpassBtnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUpPas();
            }
        });
    }

    /**
     * 修改密码
     */
    private void getUpPas() {
        final String trim = unUsername.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            MyAnimtor.getAnimator_DX(unUsername).start();
            To.ee("请输入手机号");
            return;
        }
        String pasCode = unPassCode.getText().toString().trim();
        if (TextUtils.isEmpty(pasCode)) {
            MyAnimtor.getAnimator_DX(unPassCode).start();
            To.ee("请填写验证码");
            return;
        }
        final String pas1 = unPass.getText().toString().trim();
        if (TextUtils.isEmpty(pas1)) {
            To.ee("请输入正确格式的密码");
            MyAnimtor.getAnimator_DX(unPass).start();
            return;
        }
        String pas2 = unPass1.getText().toString().trim();
        if (TextUtils.isEmpty(pas2)) {
            MyAnimtor.getAnimator_DX(unPass1).start();
            To.ee("请输入确认新密码");
            return;
        }
        if (!TextUtils.equals(pas1, pas2)) {
            MyAnimtor.getAnimator_DX(unPass1).start();
            MyAnimtor.getAnimator_DX(unPass).start();
            To.ee("您两次输入的密码不一致");
            return;
        }
        loadingDialog.show();
        setCall(model.upPassWord(trim, pasCode, pas1, pas2, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                To.dd(obj);
                Intent intent = getIntent();
                intent.putExtra("phone", trim);
                intent.putExtra("pas", pas1);
                setResult(UPPASSWORD, intent);
                loadingDialog.dismiss();
                finish();
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                String string = obj.toString();
                if (TextUtils.equals("设置失败", string)) {
                    To.ee("您修改的密码就是您以前的密码，无需重复设置");
                } else {
                    To.ee(string);
                }
                loadingDialog.dismiss();
            }
        }));
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String trim = unUsername.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            To.oo("请填写手机号");
            return;
        }
        unTvcode.setClickable(false);
        loadingDialog.show();
        setCall(model.getPasCode(trim, new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(Object obj) {
                if (isFinishing()) {
                    return;
                }
                if (start != null) {
                    start.cancel();
                    start = null;
                }
                start = new Time_down(1000 * 60, 1000, unTvcode).start();
                To.oo("验证码：" + obj);
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Object obj) {
                if (isFinishing()) {
                    return;
                }
                loadingDialog.dismiss();
                To.oo(obj);
                unTvcode.setClickable(true);
            }
        }));
    }

    /**
     * 初始化
     */
    private void init() {
        unpassTitle.setCallback(new TitleLayout.Callback(this));
        unpassTitle.setTitle("重置密码", true);
        loadingDialog = new LoadingDialog(this);
        model = new LoginModel();
    }
}
