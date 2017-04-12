package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.User;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/2/10.
 */

public class LoginModel extends IModel {

    /**
     * 21、注册
     * uregister
     * 传入：mobile    password   password2   code
     * 返回：user_id
     */
    public Call uregister(String mobile, String password, String password2,
                          String code, final AsyncCallBack callBack) {
        RequestBody body = new FormBody.Builder()
                .add("mobile", mobile)
                .add("password", password)
                .add("password2", password2)
                .add("code", code)
                .build();
        Request request = new Request.Builder()
                .url(HTTPURL.uregister)
                .post(body)
                .build();
//        Response response = client.newCall(request).execute();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String re = response.body().string();
                if (GetJsonRetcode.getRetcode(re) == 2000) {
                    callBack.onSucceed(GetJsonRetcode.getmsg(re));
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(re));
                }
            }
        });
        return call;
    }

    /**
     * 注册发送验证码
     * ssendMsgRegist
     * 传入：phonenum
     * 返回：code
     */
    public Call code(String phonenum, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder()
                .add("phonenum", phonenum).build();
        Request request = new Request.Builder()
                .url(HTTPURL.code)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                LogUtils.d(re);
                if (GetJsonRetcode.getRetcode(re) == 2000) {
                    callBack.onSucceed(GetJsonRetcode.getname("data", re));
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(re));
                }
            }
        });
        return call;
    }

    /**
     * 登录：
     * ulogin
     * 传入：mobile  password
     * 返回：user_id
     */
    public Call login(String mobile, String password, String device_token,final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("mobile", mobile)
                .add("password", password).add("device_token",device_token).build();
        LogUtils.d(HTTPURL.ulogin + "?mobile=" + mobile + "&password=" + password);
        Request request = new Request.Builder().url(HTTPURL.ulogin).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d(e.toString());
                callBack.onError("请求失败");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                LogUtils.d(re);
                if (GetJsonRetcode.getRetcode(re) == 2000) {
                    callBack.onSucceed(new Gson().fromJson(re, User.class));
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(re));
                }
            }
        });
        return call;
    }

    /**
     * 5、忘记密码发送验证码
     * ssendVerCode_login
     * 传入：mobile
     * {"retcode":2000,"msg":"验证码已发送，请注意查收","data":{"code":4219}}
     *
     * @return
     */
    public Call getPasCode(String mobile, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("mobile", mobile).build();
        Request request = new Request.Builder().url(HTTPURL.ssendVerCode_login).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("忘记密码发送验证码" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    try {
                        JSONObject object = new JSONObject(string);
                        JSONObject data = object.getJSONObject("data");
                        String code = data.getString("code");
                        runUiOnSuccess(code, callBack);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                    }
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 6、忘记密码中的修改密码
     * schangePassword
     * 传入：mobile   code   password（密码）  pwd（确认密码）
     *
     * @return
     */
    public Call upPassWord(String mobile, String code, String password, String pwd,
                           final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("mobile", mobile).add("code", code)
                .add("password", password).add("pwd", pwd).build();
        Request request = new Request.Builder().url(HTTPURL.schangePassword).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                String getmsg = GetJsonRetcode.getmsg(string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    runUiOnSuccess(getmsg, callBack);
                } else {
                    runUiOnError(getmsg, callBack);
                }
            }
        });
        return call;
    }
}
