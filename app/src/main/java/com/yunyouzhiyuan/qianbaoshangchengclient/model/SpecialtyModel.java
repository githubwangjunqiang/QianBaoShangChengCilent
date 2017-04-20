package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Specialty;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Specialty_Fenlei;
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

public class SpecialtyModel extends IModel {
    /**
     * 71、获取特产子分类
     * get_specialty_cate
     * 传入：sc_id
     *
     * @return
     */
    public Call getFenLei(final AsyncCallBack callBack) {
        Request request = new Request.Builder().url(HTTPURL.get_specialty_cate).get().build();
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
                LogUtils.d("获取特产子分类" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<Specialty_Fenlei.DataBean> data = new Gson().fromJson(string, Specialty_Fenlei.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 73、获取特产分类下店铺
     * get_specialty_store
     * 传入：class_2  （获取子分类店铺必传）  class_3  （获取子分类店铺必传） city_id （所在城市id）
     */
    public Call getBottomData(String class_2, String class_3, String city_id, final AsyncCallBack callBack) {
        FormBody body = new FormBody.Builder().add("class_2", class_2).add("class_3", class_3)
                .add("city_id", city_id).build();
        Request request = new Request.Builder().url(HTTPURL.get_specialty_store).post(body).build();
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
                LogUtils.d("获取特产分类下店铺" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<Specialty.DataBean> data = new Gson().fromJson(string, Specialty.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }
}
