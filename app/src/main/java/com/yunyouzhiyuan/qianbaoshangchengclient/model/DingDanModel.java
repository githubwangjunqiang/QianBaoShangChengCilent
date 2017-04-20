package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.DingDanOver;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Dingdan;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Order_So;
import com.yunyouzhiyuan.qianbaoshangchengclient.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangchengclient.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/2/11.
 */

public class DingDanModel extends IModel {

    /**
     * .获取用户订单
     * order_list
     * 传入：user_id  page  type（0全部  1待支付  2进行中 3已完成）
     * user_id/1/page/0/type/0
     *
     * @return
     */
    public Call getDate(String user_id, int page, int type, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("user_id", user_id).add("page", page + "").add("type", type + "").build();
        Request request = new Request.Builder().url(HTTPURL.order_list).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
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
                LogUtils.d("获取用户订单=" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(new Gson().fromJson(string, Dingdan.class));
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

    /**
     * 添加评论
     * add_comment
     * 传入：user_id  order_id  store_id goods_rank   content     img
     */
    public Call addComment(String user_id, String order_id, String store_id, String goods_rank, String content, String img, final AsyncCallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder();
        if (!TextUtils.isEmpty(img)) {
            builder.add("img", img);
        }
        builder.add("user_id", user_id);
        builder.add("order_id", order_id);
        builder.add("goods_rank", goods_rank);
        builder.add("content", content);
        builder.add("store_id", store_id);
        LogUtils.d("评价订单 参数=" + builder.toString());
        Request request = new Request.Builder().url(HTTPURL.add_comment).post(builder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runUiOnError(response.message(), callBack);
                    return;
                }

                String string = response.body().string();
                LogUtils.d("添加评论=" + string);
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
     * 46、立即购   提交订单（单个商品立即购）
     * addOrder
     * 传入：user_id （用户id）  goods_id  （商品id）  goods_num （商品数量）
     * goods_spec （商品属性）
     * address_id （地址id）    shipping_code （物流编号）  user_note  （给卖家留言）
     * coupon_id （优惠券id）   user_money （使用余额）
     * act/submit_order
     */
    public Call outDingDan(String user_id, String goods_id, String goods_num,
                           String user_note, String coupon_id, boolean isTow, String goods_spec,
                           String time, final AsyncCallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id", user_id);
        builder.add("goods_id", goods_id);
        builder.add("goods_num", goods_num);
        if (!TextUtils.isEmpty(time)) {
            builder.add("time", time);
        }
        if (!TextUtils.isEmpty(goods_spec)) {
            builder.add("goods_spec", goods_spec);
        }
        if (!TextUtils.isEmpty(coupon_id)) {
            builder.add("coupon_id", coupon_id);
        }
        if (!TextUtils.isEmpty(user_note)) {
            builder.add("user_note", user_note);
        }
        if (isTow) {
            builder.add("act", "submit_order");
        }
        Request request = new Request.Builder().url(HTTPURL.addOrder).post(builder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runUiOnError(response.message(), callBack);
                    return;
                }

                String string = response.body().string();
                LogUtils.d("立即购提交订单" + string);
                if (GetJsonRetcode.getRetcode(string) == 3000) {
                    DingDanOver.DataBean data = new Gson().fromJson(string, DingDanOver.class).getData();
                    runUiOnSuccess(data, callBack);
                } else if (GetJsonRetcode.getRetcode(string) == 2000) {
                    Order_So.DataBean data = new Gson().fromJson(string, Order_So.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 26.获取用户退款订单
     * return_order_list
     * 传入：user_id  page
     */
    public Call getReFund(String user_id, String page, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HTTPURL.return_order_list).post()
                .addParam("user_id", user_id)
                .addParam("page", page).build().enqueue(new BaseCallback.ComonCallback<Dingdan>() {
                    @Override
                    protected void onSuccess(Dingdan s) {
                        callBack.onSucceed(s);
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onError("网络是不是有问题呢亲？");
                    }
                });

    }

    /**
     * 27-1、退款接口
     * return_goods
     * 传入：user_id     order_id   order_sn    goods_id   reason    imgs   store_id
     */
    public Call deleteReFund(String user_id, String order_id, String order_sn, String goods_id,
                             String reason, String imgs, String store_id, final AsyncCallBack callBack) {

        LogUtils.d("退款接口参数=" + user_id + "/" + order_id + "/" + order_sn + "/" + goods_id + "/" + reason);
        return MyOkHttpClent.newBuilder().url(HTTPURL.return_goods).post().addParam("user_id", user_id)
                .addParam("order_id", order_id).addParam("order_sn", order_sn).addParam("goods_id", goods_id)
                .addParam("reason", reason).addParam("imgs", imgs)
                .addParam("store_id",store_id).build().enqueue(new BaseCallback.ComonCallback<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if (GetJsonRetcode.getRetcode(s) == 2000) {
                            callBack.onSucceed(GetJsonRetcode.getmsg(s));
                        } else {
                            callBack.onError(GetJsonRetcode.getmsg(s));
                        }
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onError("网络是不是没有呢亲");
                    }
                });

    }


}
