package com.yunyouzhiyuan.qianbaoshangchengclient.model;


import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Food_Bottom;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/2/28.
 */

public class CookModel extends IModel {
    /**
     * 27、获取店铺列表信息 (首页下边附近店铺)
     get_store_list
     传入：page    lat1 纬度  lng1 经度  id(二级分类)   city_id(首页所选城市id)  （sc_id(一级分类)  获取二级分类下店铺参数     ）
     例如：get_store_list?id=2&lng=116.73245&lat=39.867184&page=0&city_id=2&sc_id=2
     */
    public Call nearByStore(String sc_id, String class_2, String lng, String lat, int page,
                            String city_id, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("sc_id", sc_id).add("id", class_2).add("lng1", lng)
                .add("lat1", lat).add("page", page + "").add("city_id", city_id).build();



        LogUtils.i("获取附近店铺参数=" + HTTPURL.nearbyStore + "?id=" + sc_id + "&lng=" + lng + "&lat=" + lat +
                "&page=" + page + "&city_id=" + city_id + "&class_2=" + class_2);

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

}
