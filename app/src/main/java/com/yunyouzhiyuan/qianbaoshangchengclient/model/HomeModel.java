package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bann;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Bean;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.City_id;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HomeStore_category;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_Bottom_list;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_HuoDong;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.KTV_list_data;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.SpService;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL.get_store_category;

/**
 * Created by ${王俊强} on 2017/2/11.
 */

public class HomeModel extends IModel {

    /**
     * 获取首页店铺分类
     * get_store_category
     */
    public Call getStor(final AsyncCallBack callBack) {
        Request request = new Request.Builder().url(get_store_category).get().build();
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
            public void onResponse(final Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runUiOnError(response.message(), callBack);
                    return;
                }

                String string = response.body().string();
                LogUtils.d("获取首页店铺分类" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    final HomeStore_category homeStore_category = new Gson().fromJson(string, HomeStore_category.class);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(homeStore_category.getData());
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
     * 、获取店铺列表信息
     * get_store_list
     * 传入：page   lat1 纬度  lng1 经度
     * city_id(首页所选城市id)
     *
     * @return
     */
    public Call getList(int page, String lat1, String lng1, String city_id, final AsyncCallBack callBack) {
        LogUtils.i("首页home底部列表参数=" + page + "/" + lat1 + "/" + lng1 + "/");
        FormBody body = new FormBody.Builder().add("page", page + "").
                add("lat1", lat1).add("lng1", lng1)
                .add("city_id", city_id).build();
        Request request = new Request.Builder().url(HTTPURL.get_store_list).post(body).build();
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
            public void onResponse(final Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runUiOnError(response.message(), callBack);
                    return;
                }

                String string = response.body().string();
                LogUtils.d("获取店铺列表信息=" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    final List<Home_Bottom_list.DataBean> data = new Gson().fromJson(string, Home_Bottom_list.class).getData();
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(data);
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

    /***
     * 获取首页banner
     * get_index_banner
     *
     * @return
     */
    public Call getBanner(final AsyncCallBack callBack) {
        Request request = new Request.Builder().url(HTTPURL.get_index_banner).get().build();
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
                if (!response.isSuccessful()) {
                    runUiOnError(response.message(), callBack);
                    return;
                }

                String string = response.body().string();
                LogUtils.d("获取首页banner" + string);
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
     * 66、获取开通店铺地址列表
     * get_run_store_adr
     */
    public Call getCity(final AsyncCallBack callBack) {
        Request request = new Request.Builder().url(HTTPURL.get_run_store_adr).get().build();
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
                LogUtils.d("获取开通店铺地址列表" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    City_id city_id = new Gson().fromJson(string, City_id.class);
                    Bean.saveCity(city_id);
                    Bean.city_id_id = SpService.getSP().getCityid();
                    runUiOnSuccess(city_id, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 67、获取首页店铺列表中酒店信息
     * get_index_store_info
     * 传入：store_id
     *
     * @return
     */
    public Call getHotelData(String store_id, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("store_id", store_id).build();
        Request request = new Request.Builder().url(HTTPURL.get_index_store_info).post(body).build();
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
                LogUtils.d("获取首页店铺列表中酒店信息" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    KTV_list_data.DataBean data = new Gson().fromJson(string, KTV_list_data.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 76、获取首页活动接口
     * get_actity_takeaway
     * 返回：ad_link (活动链接) ad_code（活动图片）
     *
     * @return
     */
    public Call getHuoDong(final AsyncCallBack callBack) {
        Request request = new Request.Builder().url(HTTPURL.get_actity_index).get().build();
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
                LogUtils.d("获取首页活动接口=" + string);
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
}
