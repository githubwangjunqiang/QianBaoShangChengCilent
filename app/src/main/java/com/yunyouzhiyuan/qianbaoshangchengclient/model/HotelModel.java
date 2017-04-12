package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bann;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.CityHotel;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Hotel;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Hotel_Fagnjian;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Hotel_Fenlei;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Hotel_storinfo;
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
 * Created by ${王俊强} on 2017/3/2.
 */

public class HotelModel extends IModel {
    /**
     * 60、获取酒店三个分类
     * get_three_hotelcate
     * 无传入
     * 返回： "mobile_name": "分类名称",
     *
     * @return
     */
    public Call getFenlei(final AsyncCallBack callBack) {
        Request request = new Request.Builder().url(HTTPURL.get_three_hotelcate).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取酒店三个分类" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<Hotel_Fenlei.DataBean> data = new Gson().fromJson(string, Hotel_Fenlei.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }

            }
        });
        return call;
    }

    /**
     * 获取酒店附近店铺
     * nearbyStore
     * 传入：id（分类id） lng(经度) lat(纬度) page   例：/id/15/lng/116.5/lat/40
     */
    public Call nearByStore(String sc_id, String lng, String lat, int page, String class_2, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("id", sc_id).add("lng", lng).add("lat", lat).add("page", page + "").add("class_2", class_2).build();
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
                    Food_Bottom food_bottom = new Gson().fromJson(string, Food_Bottom.class);
                    runUiOnSuccess(food_bottom, callBack);
                } else {
                    String s = GetJsonRetcode.getmsg(string);
                    runUiOnError(s, callBack);
                }
            }
        });
        return call;
    }

    /**
     * 获取全国地址列表
     * get_country_area
     * 传入：page
     *
     * @return
     */
    public Call getList(int page, final AsyncCallBack callBack) {
        FormBody bod = new FormBody.Builder().add("page", page + "").build();
        Request request = new Request.Builder().url(HTTPURL.get_country_area).post(bod).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取全国地址列表" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<CityHotel.DataBean> list = new Gson().fromJson(string, CityHotel.class).getData();
                    runUiOnSuccess(list, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 50、获取搜索条件下的酒店列表
     * search_hotel_list
     * 传入：sc_id（分类id） level（地址级数） id（地址id） page（页码 默认0） class_2(日租房或钟点房id)
     * lng 搜索传入的经度  lat搜索传入的纬度  keyword(关键字) price（价格 以，隔开）
     */
    public Call toLoadHotel(String sc_id, String level, String id, String page,
                            String class_2, String lng, String lat, String keyword,
                            String price, final AsyncCallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("sc_id", sc_id);
        builder.add("level", level);
        builder.add("id", id);
        builder.add("page", page);
        builder.add("class_2", class_2);
        builder.add("lng", lng);
        builder.add("lat", lat);
        if (!TextUtils.isEmpty(keyword)) {
            builder.add("keyword", keyword);
        }
        if (!TextUtils.isEmpty(price)) {
            builder.add("price", price);
        }
        Request request = new Request.Builder().url(HTTPURL.search_hotel_list).post(builder.build()).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取搜索条件下的酒店列表" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<Hotel.DataBean> data = new Gson().fromJson(string, Hotel.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 62、获取酒店店铺信息
     * about_hotel_store
     * 传入：store_id  user_id
     */
    public Call getInfo(String store_id, String user_id, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("store_id", store_id).add("user_id", user_id).build();
        Request request = new Request.Builder().url(HTTPURL.about_hotel_store).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取酒店店铺信息=" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    Hotel_storinfo.DataBean data = new Gson().fromJson(string, Hotel_storinfo.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 51、获取酒店房间信息
     * goods_hotel_houses
     * 传入：store_id
     */
    public Call getFangjian(String store_id, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("store_id", store_id).build();
        Request request = new Request.Builder().url(HTTPURL.goods_hotel_houses).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取酒店房间信息=" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<Hotel_Fagnjian.DataBean> data = new Gson().fromJson(string, Hotel_Fagnjian.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 63、获取酒店轮播图
     * get_hotel_banner
     * 返回：ad_code(banner地址)   ad_link(banner链接)
     */
    public Call getBanner(final AsyncCallBack callBack) {
        Request request = new Request.Builder().url(HTTPURL.get_hotel_banner).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    Bann bann = new Gson().fromJson(string, Bann.class);
                    runUiOnSuccess(bann.getData(), callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }
}
