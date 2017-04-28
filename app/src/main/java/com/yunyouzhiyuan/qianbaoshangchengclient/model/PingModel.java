package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/3/15.
 */

public class PingModel extends IModel {
    /**
     * * 获取sharge对象的解析方法
     *
     * @param json 需要解析的json 串
     * @return
     * @return* @throws IOException
     */
    public Call getJson(String json, final AsyncCallBack callBack) {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);
        Request request = new Request.Builder().url(HTTPURL.ping).post(body).build();
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
                LogUtils.i("ping++返回" + string);
                runUiOnSuccess(string, callBack);
            }
        });
        return call;
    }
}
