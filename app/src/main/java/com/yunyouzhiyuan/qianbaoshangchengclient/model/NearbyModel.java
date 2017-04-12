package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Nearby;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Nearby_Fenlei;
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
 * Created by ${王俊强} on 2017/3/16.
 */

public class NearbyModel extends IModel {
    /**
     * 74、获取附近模块子分类
     * get_nearby_cate
     * 传入：无传参
     */
    public Call getFenLei(final AsyncCallBack callBack) {
        Request request = new Request.Builder().url(HTTPURL.get_nearby_cate).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取附近模块子分类" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<Nearby_Fenlei.DataBean> data = new Gson().fromJson(string, Nearby_Fenlei.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 75、获取附近模块下的店铺
     * get_vicinity_store
     * 传入：page    lat1 纬度  lng1 经度  id(二级分类)   city_id(首页所选城市id)
     */
    public Call getBottomData(int page, String lat1, String lng1, String id, String city_id, final AsyncCallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("lat1", lat1).add("lng1", lng1).add("city_id", city_id).add("page", page + "");
        if (!TextUtils.isEmpty(id)) {
            builder.add("id", id);
        }
        Request request = new Request.Builder().url(HTTPURL.get_vicinity_store).post(builder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取附近模块下的店铺" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<Nearby.DataBean> data = new Gson().fromJson(string, Nearby.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }
}
