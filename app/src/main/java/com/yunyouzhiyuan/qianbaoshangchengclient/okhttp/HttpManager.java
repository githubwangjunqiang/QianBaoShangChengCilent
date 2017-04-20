package com.yunyouzhiyuan.qianbaoshangchengclient.okhttp;

import android.os.Handler;

import com.google.gson.Gson;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${王俊强} on 2017/3/24.
 */
public class HttpManager {
    private static HttpManager manager;//单利模式下的HttpManager  网络管理类

    private OkHttpClient okHttpClient;//OkHttpClient 的实例

    private Handler handler;//线程切换的handler

    private Gson mGson;//解析框架

    /**
     * 私有构造方法
     */
    private HttpManager() {
        initOkHttpClient();
        handler = new Handler();
        mGson = new Gson();
    }

    /**
     * 单利模式 懒汉式 单利模式 添加线程安全管理
     *
     * @return HttpManager
     */
    public static HttpManager getInstance() {
        if (manager == null) {
            synchronized (HttpManager.class) {
                if (manager == null) {
                    manager = new HttpManager();
                }
            }
        }
        return manager;
    }

    /**
     * 创建OkHttpClient 实例
     */
    private void initOkHttpClient() {
        okHttpClient = new OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();
    }

    /**
     * 执行请求
     */
    public Call request(MyOkHttpClent clent, final BaseCallback.ComonCallback callback) {
        //对callback 判空
        if (callback == null) {
            throw new NullPointerException("BaseCallback callback not null");
        }


        Request request = clent.getRequest();
        if (request == null) {
            senOnErrorMsg(callback, 0, "构建参数错误");
            return null;
        }
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                senOnFailureMsg(callback, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    LogUtils.LongShitou(string);
                    if (callback.type == null || callback.type == String.class) {
                        senOnSuccessMsg(callback, string);
                    } else {
                        try {
                            JSONObject object = new JSONObject(string);
                            int retcode = object.getInt("retcode");
                            if (2000 == retcode) {
                                senOnSuccessMsg(callback, mGson.fromJson(string, callback.type));
                            } else {
                                String msg = object.getString("msg");
                                senOnErrorMsg(callback, 0, msg == null ? "" : msg);
                            }
                        } catch (Exception e) {
                            senOnErrorMsg(callback, 0, "解析失败" + e.getLocalizedMessage());
                        }
                    }


                    if (response.body() != null) {
                        response.body().close();
                    }

                } else {
                    senOnErrorMsg(callback, response.code(), response.message());
                }
            }
        });
        return call;
    }

    /**
     * 子线程传递主线程 失败消息
     */
    private void senOnFailureMsg(final BaseCallback.ComonCallback callback, final Call call, final IOException io) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(call, io);
            }
        });
    }

    /**
     * 子线程传递主线程 失败消息
     */
    private void senOnErrorMsg(final BaseCallback.ComonCallback callback, final int code, final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(code, msg);
            }
        });
    }

    /**
     * 子线程传递主线程 成功消息
     */
    private void senOnSuccessMsg(final BaseCallback.ComonCallback callback, final Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(object);
            }
        });
    }
}
