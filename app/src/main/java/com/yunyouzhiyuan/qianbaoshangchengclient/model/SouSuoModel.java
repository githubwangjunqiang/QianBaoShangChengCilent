package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Home_Bottom_list;
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

public class SouSuoModel extends IModel {
    /**
     * 70、获取搜索店铺
     * searchstorebykeyword
     * 传入： keyword(关键字)   city_id （所在城市id）    lng1  （经度）    lat1（纬度）
     *
     * @return
     */
    public Call getData(String keyword, String city_id, String lng1, String lat1, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("keyword", keyword).add("city_id", city_id)
                .add("lng1", lng1).add("lat1", lat1).build();
        Request request = new Request.Builder().url(HTTPURL.searchstorebykeyword).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runUiOnError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("获取搜索店铺" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<Home_Bottom_list.DataBean> data = new Gson().fromJson(string, Home_Bottom_list.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }
}
