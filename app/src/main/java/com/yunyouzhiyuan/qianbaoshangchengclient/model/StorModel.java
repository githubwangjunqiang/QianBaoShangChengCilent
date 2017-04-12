package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.CookStorInfoShop;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorInfo;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.StorInfoList;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.TuiJian;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import static com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL.get_three_recommend;

/**
 * Created by ${王俊强} on 2017/2/14.
 */

public class StorModel extends IModel {
    /**
     * 获取店铺基本信息
     * about_store
     * 传入：store_id  (店铺id)
     * user_id  yonghu
     */
    public Call getStorInfo(String store_id, String user_id, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("store_id", store_id).add("user_id", user_id).build();
        final Request request = new Request.Builder().url(HTTPURL.about_store).post(body).build();
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
                LogUtils.d("获取店铺基本信息" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    final StorInfo storInfo = new Gson().fromJson(string, StorInfo.class);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(storInfo);
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
     * 获取店铺商品列表
     * storeGoods
     * 传入：store_id (店铺id)  page(默认0)  sort(排序  comprehensive 综合  sales 销量    price价格)
     * cat_id（商品分类id）
     */

    public Call storeGoods(String store_id, int page, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("store_id", store_id).add("page", page + "").build();
        Request request = new Request.Builder().url(HTTPURL.storeGoods).post(body).build();
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
                LogUtils.d("店铺详情底部商品列表  " + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    final StorInfoList storInfoList = new Gson().fromJson(string, StorInfoList.class);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(storInfoList.getData().getGoods_list());
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
     * 收藏与取消
     * collectStoreOrNo
     * 传入：user_id （用户id）  store_id （店铺id）
     *
     * @return
     */
    public Call toShoCang(String user_id, String store_id, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("user_id", user_id).add("store_id", store_id).build();
        Request request = new Request.Builder().url(HTTPURL.collectStoreOrNo).post(body).build();
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
                final String string = response.body().string();
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(GetJsonRetcode.getmsg(string));
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
     * 48、获取美食模块商品列表
     * get_meishi_goods
     * 传入：store_id  （店铺id）  page(默认为0)
     *
     * @return
     */
    public Call getCookBottom(String store_id, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("store_id", store_id).build();
        Request request = new Request.Builder().url(HTTPURL.get_meishi_goods).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取美食模块商品列表" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<CookStorInfoShop.DataBean> list = new Gson().fromJson(string, CookStorInfoShop.class).getData();
                    runUiOnSuccess(list, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 64、获取分类页面推荐的三个店铺
     * get_three_recommend
     * 传入：sc_id    city_id(首页所选城市id)
     *
     * @return
     */
    public Call getTuiJian(String sc_id, String city_id, final AsyncCallBack callBack) {
        LogUtils.d("获取分类页面推荐的三个店铺参数="+sc_id+"/"+city_id);
        FormBody body = new FormBody.Builder().add("sc_id", sc_id).add("city_id", city_id).build();
        Request request = new Request.Builder().url(get_three_recommend).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取分类页面推荐的三个店铺" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<TuiJian.DataBean> data = new Gson().fromJson(string, TuiJian.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }
}
