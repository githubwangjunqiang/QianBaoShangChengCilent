package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ShouCang;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.ShouCangShop;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/2/15.
 */

public class ShouCangModel extends IModel {
    /**
     * 获取我的收藏di安普
     * get_my_collect
     * 传入：user_id   type（默认1 代表商品  2 代表店铺）
     *
     * @return
     */
    public Call getData(String user_id, final int type, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("user_id", user_id).add("type", type + "").build();
        Request request = new Request.Builder().url(HTTPURL.get_my_collect).post(body).build();
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
                LogUtils.d("获取我的收藏shop" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    final ShouCang shouCang = new Gson().fromJson(string, ShouCang.class);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(shouCang);
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
     * 获取我的收藏商品
     * get_my_collect
     * 传入：user_id   type（默认1 代表商品  2 代表店铺）
     *
     * @return
     */
    public Call getDataShop(String user_id, final int type, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("user_id", user_id).add("type", type + "").build();
        Request request = new Request.Builder().url(HTTPURL.get_my_collect).post(body).build();
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
                LogUtils.d("获取我的收藏shop" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    final ShouCangShop shouCang = new Gson().fromJson(string, ShouCangShop.class);
                    instance.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucceed(shouCang);
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
}
