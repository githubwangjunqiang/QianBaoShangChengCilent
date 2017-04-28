package com.yunyouzhiyuan.qianbaoshangchengclient.ui.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by ${王俊强} on 2017/2/10.
 */

public class LoadOKHttpClient {
    private static OkHttpClient client;

    public static OkHttpClient loadClient() {
        if (client == null) {
            synchronized (LoadOKHttpClient.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
                            .writeTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                            .readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                            .connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                            .build();
                }
            }
        }
        return client;
    }
}
