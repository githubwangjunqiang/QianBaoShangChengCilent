package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.HTTPURL;
import com.yunyouzhiyuan.qianbaoshangchengclient.entiy.Pingjia;
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
 * Created by ${王俊强} on 2017/3/14.
 */

public class EvaluateModel extends IModel {
    /**
     * 68、获取我的评论
     * get_my_comment
     * 传入：user_id   page
     *
     * @return
     */
    public Call getData(String user_id, int page, final AsyncCallBack callBack) {
        LogUtils.d("获取我的评论参数=" + HTTPURL.get_my_comment + "?user_id=" + user_id + "&page=" + page);
        FormBody body = new FormBody.Builder().add("user_id", user_id).add("page", page + "").build();
        Request request = new Request.Builder().url(HTTPURL.get_my_comment).post(body).build();
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
                LogUtils.d("获取我的评论" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    List<Pingjia.DataBean> data = new Gson().fromJson(string, Pingjia.class).getData();
                    runUiOnSuccess(data, callBack);
                } else {
                    runUiOnError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
        return call;
    }
}
