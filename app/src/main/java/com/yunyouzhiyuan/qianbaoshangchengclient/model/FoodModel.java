package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bann;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.DingDanOver;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.FoodInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Freight;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_HuoDong;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorPingjia;
import com.yunyouzhiyuan.qianbaoshangchengclient.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangchengclient.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.To;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/2/16.
 */

public class FoodModel extends IModel {
    /**
     * 获取附近店铺
     * nearbyStore
     * 传入：id（分类id） lng(经度) lat(纬度) page   例：/id/15/lng/116.5/lat/40
     * city_id (首页所选城市id)
     */
    public Call nearByStore(String sc_id, String lng, String lat, int page,
                            String city_id, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("id", sc_id).add("lng", lng)
                .add("lat", lat).add("page", page + "").add("city_id", city_id).build();
        LogUtils.i("获取附近店铺参数=" + HTTPURL.nearbyStore + "?id=" + city_id + "&lng=" + lng + "&lat=" + lat +
                "&page=" + page + "&city_id=" + city_id);
        Request request = new Request.Builder().url(HTTPURL.nearbyStore).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                instance.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(App.getContext().getString(R.string.qingqiushibai));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取附近店铺" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    final Food_Bottom food_bottom = new Gson().fromJson(string, Food_Bottom.class);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(food_bottom);
                        }
                    });
                } else {
                    final String s = GetJsonRetcode.getmsg(string);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(s);
                        }
                    });
                }
            }
        });
        return call;
    }

    /**
     * 、获取外卖轮播图
     * get_takeaway_banner
     */
    public Call getBanner(final AsyncCallBack callBack) {
        Request request = new Request.Builder().url(HTTPURL.get_takeaway_banner).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                instance.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(App.getContext().getString(R.string.qingqiushibai));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取外卖轮播图=" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    final Bann bann = new Gson().fromJson(string, Bann.class);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(bann.getData());
                        }
                    });
                } else {
                    final String s = GetJsonRetcode.getmsg(string);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(s);
                        }
                    });
                }
            }
        });
        return call;
    }

    /**
     * 获取店铺外卖列表
     * get_takeaway_list
     * 传入：store_id(店铺id)
     *
     * @return
     */
    public Call getList(String store_id, final AsyncCallBack callBack) {
        FormBody boy = new FormBody.Builder().add("store_id", store_id).build();
        Request request = new Request.Builder().url(HTTPURL.get_takeaway_list).post(boy).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                instance.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(App.getContext().getString(R.string.qingqiushibai));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取店铺外卖列表=" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    final FoodInfo bann = new Gson().fromJson(string, FoodInfo.class);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(bann.getData());
                        }
                    });
                } else {
                    final String s = GetJsonRetcode.getmsg(string);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(s);
                        }
                    });
                }
            }
        });
        return call;
    }

    /**
     * 外卖购物车下单
     * cart_go
     * 传入： user_id  total_price  store_id   address_id   (user_money 此参数余额支付已被后台删除) coupon_id  goods_form_data
     * 最后面 加上（act=submit_order）
     */
    public Call toDingdan(String user_id, String total_price, String store_id,
                          String address_id, String coupon_id,
                          String goods_form_data,
                          final boolean isSubMit,
                          final AsyncCallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id", user_id);
        builder.add("total_price", total_price);
        builder.add("store_id", store_id);
        builder.add("address_id", address_id);
        builder.add("goods_form_data", goods_form_data);
        if (isSubMit) {
            builder.add("act", "submit_order");
        }
        if (!TextUtils.isEmpty(coupon_id)) {
            builder.add("coupon_id", coupon_id);
        }
        Request request = new Request.Builder().url(HTTPURL.cart_go).post(builder.build()).build();
        Call call = client.newCall(request);

//
//        RequestBody requestBody = RequestBody.create(
//                MediaType.parse("text/plain;chaset=utf-8"), goods_form_data);

//

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                instance.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(App.getContext().getString(R.string.qingqiushibai));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("外卖购物车下单" + string);
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    final String msg = jsonObject.getString("msg");
                    int retcode = jsonObject.getInt("retcode");
                    if (retcode == 2000) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        final String master_order_sn = data.getString("master_order_sn");
                        runUiOnSuccess(master_order_sn, callBack);
                    } else if (retcode == 3000) {
                        DingDanOver.DataBean data = new Gson().fromJson(string, DingDanOver.class).getData();
                        runUiOnSuccess(data, callBack);
                    } else {
                        runUiOnError(msg, callBack);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError("解析失败");
                        }
                    });
                }


            }
        });
        return call;
    }

    /**
     * 、获取店铺是否收藏
     * is_store_collect
     * 传入：store_id   user_id
     * 返回：（1 收藏   0未收藏）
     */
    public Call getShouCang(String store_id, String user_id, final AsyncCallBack callBack) {

        FormBody body = new FormBody.Builder().add("store_id", store_id).add("user_id", user_id).build();
        final Request request = new Request.Builder().url(HTTPURL.is_store_collect).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取店铺是否收藏" + string);
                try {
                    JSONObject object = new JSONObject(string);
                    String msg = object.getString("msg");
                    int retcode = object.getInt("retcode");
                    if (retcode == 2000) {
                        int data = object.getInt("data");
                        runUiOnSuccess(data, callBack);
                    } else {
                        runUiOnError(msg, callBack);
                    }

                } catch (JSONException e) {
                    runUiOnError(null, callBack);
                }

            }
        });
        return call;
    }

    /**
     * 获取店铺评价
     * get_store_comment
     * get_store_comment
     * 传入：store_id
     */
    public Call getPingList(String store_id, int page, final AsyncCallBack callBack) {
        Request.Builder builder = new Request.Builder();
        FormBody body = new FormBody.Builder().add("store_id", store_id).add("page", page + "").build();
        builder.post(body);
        builder.url(HTTPURL.get_store_comment);
        Request build = builder.build();
        Call call = client.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response re) throws IOException {
                String response = re.body().string();
                LogUtils.d("获取店铺评价" + response);
                if (GetJsonRetcode.getRetcode(response) == 2000) {
                    StorPingjia.DataBeanX list = new Gson().fromJson(response, StorPingjia.class).getData();
                    runUiOnSuccess(list, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(response), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 76、获取外卖活动接口
     * get_actity_takeaway
     *
     * @ren
     */
    public Call getHuoDong(final AsyncCallBack callBack) {
        Request request = new Request.Builder().url(HTTPURL.get_actity_takeaway).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取外卖活动接口=" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<Home_HuoDong.DataBean> data = new Gson().fromJson(string, Home_HuoDong.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 54、获取商家起送价和运费
     * get_send_price
     * 传入：store_id
     */
    public void get_send_price(String store_id, final AsyncCallBack callBack) {
        MyOkHttpClent.newBuilder().url(HTTPURL.get_send_price).post().addParam("store_id", store_id)
                .build().enqueue(new BaseCallback.ComonCallback<Freight>() {
            @Override
            protected void onSuccess(Freight s) {
                if (s.getData() != null && !s.getData().isEmpty()) {
                    callBack.onSucceed(s.getData());
                } else {
                    callBack.onError("返回数据异常");
                }

            }

            @Override
            protected void onError(int code, String msg) {
                callBack.onError(msg);
            }

            @Override
            protected void onFailure(Call call, IOException e) {
                To.ee("网络是不是出错了");
//                callBack.onFailure("是不是网络出问题了");
            }
        });
    }

}
