package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.DingDanYouhuiquan;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Youhuiquan;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/2/11.
 */

public class YouhuiquanModel extends IModel {
    /**
     * 、获取用户优惠券
     * get_user_coupon
     * 传入：user_id  用户id
     * type     优惠券类型（0 未使用  1已使用 2已过期）
     * page     页码（默认0）
     */
    public Call getdata(String user_id, int type, int page, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("user_id", user_id).add("type", type + "").add("page", page + "").build();
        LogUtils.d("优惠券参数=" + type + page);
        Request request = new Request.Builder().url(HTTPURL.get_user_coupon).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runUiOnError(response.message(), callBack);
                    return;
                }

                String string = response.body().string();
                LogUtils.d("优惠券=" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    callBack.onSucceed(new Gson().fromJson(string, Youhuiquan.class));
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(string));
                }
            }
        });
        return call;
    }

    /**
     * 、获取外卖订单可用优惠券
     * get_takeaway_coupon
     * 传入：user_id    total_price     store_id
     */
    public Call getDingdanYouhuiquan(String user_id, String total_price, String store_id, final AsyncCallBack callBack) {
//        FormBody body = new FormBody.Builder().add("user_id", user_id).add("total_price", total_price).add("store_id", store_id).build();
        String s = HTTPURL.get_takeaway_coupon + "?user_id=" + user_id + "&total_price=" + total_price + "&store_id=" + store_id;
        LogUtils.d("可用优惠券参数" + s);
        Request request = new Request.Builder().url(s).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                instance.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError("请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runUiOnError(response.message(), callBack);
                    return;
                }

                final String string = response.body().string();
                LogUtils.d("获取外卖订单可用优惠券=" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    final List<DingDanYouhuiquan.DataBean> data = new Gson().fromJson(string, DingDanYouhuiquan.class).getData();
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(data);
                        }
                    });
                } else {
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(GetJsonRetcode.getmsg(string));
                        }
                    });
                }
            }
        });
        return call;
    }
}
