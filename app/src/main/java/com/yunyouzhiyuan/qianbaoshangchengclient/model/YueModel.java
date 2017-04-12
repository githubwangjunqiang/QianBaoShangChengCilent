package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ToKen;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Yuee;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.MD5Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/2/11.
 */

public class YueModel extends IModel {
    /**
     * 获取用户余额和消费记录
     * get_user_consume
     * 传入：user_id  page
     *
     * @return
     */
    public Call getData(String user_id, int page, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("user_id", user_id).add("page", page + "").build();
        Request request = new Request.Builder().url(HTTPURL.get_user_consume).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取用户余额和消费记录" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    callBack.onSucceed(new Gson().fromJson(string, Yuee.class));
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(string));
                }
            }
        });
        return call;
    }

    /**
     * 79、申请提现
     * apply_withdrawals
     * 传入： user_id   money   bank_name （银行名称）  account_bank （帐户银行卡号）
     * account_name（帐户名）
     */
    public Call apply_withdrawals(String user_id, String money, String bank_name,
                                  String account_bank, String account_name, final AsyncCallBack callBack) {
        List<String> list = new ArrayList<>();
        list.add(user_id);
        list.add("apply_withdrawals");
        Date date = new Date();
        list.add(String.valueOf(date.getTime()));
        Collections.sort(list);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i));
        }
        final String s = MD5Utils.md5Code(builder.toString());
        ToKen toKen = new ToKen(s, date.getTime());
        String s1 = new Gson().toJson(toKen);
        FormBody body = new FormBody.Builder()
                .add("user_id", user_id).add("money", money)
                .add("bank_name", bank_name).add("account_bank", account_bank).
                        add("account_name", account_name).build();
        Request request = new Request.Builder().url(HTTPURL.apply_withdrawals).
                addHeader("token", s).addHeader("time", String.valueOf(date.getTime())).post(body).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("申请提现" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    runUiOnSuccess(GetJsonRetcode.getmsg(string), callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 80、获取银行名称
     * getBindBankname
     * 传入：account_bank（银行卡号）
     *
     * @return
     */
    public Call loadBankName(String account_bank, final AsyncCallBack callBack) {
        LogUtils.d("获取银行名称参数" + account_bank);
        FormBody body = new FormBody.Builder().add("account_bank", account_bank).build();
        Request request = new Request.Builder().url(HTTPURL.getBankname).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.v("获取银行名称" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        JSONObject data = jsonObject.getJSONObject("data");
                        String bank_name = data.getString("bank_name");
                        runUiOnSuccess(bank_name, callBack);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runUiOnError("解析出错", callBack);
                    }
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;

    }
}
