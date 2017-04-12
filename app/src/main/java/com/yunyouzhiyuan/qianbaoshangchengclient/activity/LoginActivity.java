package com.yunyouzhiyuan.qianbaoshangchengclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.User;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.IModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.model.LoginModel;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.TitleLayout;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.dialog.LoadingDialog;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.SpService;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean.UPPASSWORD;
import static com.yunyouzhiyuan.qianbaoshangchengclient.util.SpService.getSP;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.title_login)
    TitleLayout titleLogin;
    @Bind(R.id.login_username)
    AutoCompleteTextView loginUsername;
    @Bind(R.id.user_layout)
    TextInputLayout userLayout;
    @Bind(R.id.login_pass)
    EditText loginPass;
    @Bind(R.id.pass_layout)
    TextInputLayout passLayout;
    private LoadingDialog loadingDialog;

    private LoginModel model;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 点击登陆
     */
    public void login(View view) {
        tologin();
    }

    /**
     * 登陆
     */
    private void tologin() {
        final String phone = loginUsername.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            To.oo("手机号为空");
            return;
        }
        final String pas = loginPass.getText().toString().trim();
        if (TextUtils.isEmpty(pas)) {
            To.oo("密码为空");
            return;
        }
        loadingDialog.show();
        Call login = model.login(phone, pas, JPushInterface.getRegistrationID(App.getContext()), new IModel.AsyncCallBack() {
            @Override
            public void onSucceed(final Object obj) {
                if (isFinishing()) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        User user = (User) obj;
                        To.oo(user.getMsg());
                        getSP().saveUid(user.getData().getUser_id(), phone, pas);
//                        App.setUserId(user.getData().getUser_id());
                        App.setUserId(user.getData().getUser_id());
                        loadingDialog.dismiss();
                        setResult(Bean.LOGIN, getIntent());
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
                        To.oo(obj);
                        loadingDialog.dismiss();
                    }
                });
            }
        });
        setCall(login);
    }

    /**
     * 初始化
     */
    private void init() {
        titleLogin.setCallback(new TitleLayout.Callback(this));
        titleLogin.setTitle("登陆", true);
        loadingDialog = new LoadingDialog(this);
        model = new LoginModel();
        loginUsername.setText(SpService.getSP().getphone());
        loginPass.setText(SpService.getSP().getpas());
    }

    /**
     * 忘记密码
     *
     * @param view
     */
    public void unPassWord(View view) {
        UnPassWordActivity.startUnPassWordActivity(this, loginUsername.getText().toString().trim());
    }

    /**
     * 立即注册
     *
     * @param view
     */
    public void register(View view) {
        String phone = loginUsername.getText().toString().trim();
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("phone", phone == null ? "" : phone);
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {//注册返回
            if (data != null) {
                loginUsername.setText(data.getStringExtra("phone"));
            }
        }
        if (requestCode == UPPASSWORD && resultCode == UPPASSWORD && data != null) {
            loginUsername.setText(data.getStringExtra("phone"));
            loginPass.setText(data.getStringExtra("pas"));
            tologin();
        }

    }

}
