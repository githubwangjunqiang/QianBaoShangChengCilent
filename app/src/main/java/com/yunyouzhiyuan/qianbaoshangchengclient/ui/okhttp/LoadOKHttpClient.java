package com.yunyouzhiyuan.qianbaoshangchengclient.ui.okhttp;

import okhttp3.OkHttpClient;

/**
 * Created by ${王俊强} on 2017/2/10.
 */

public class LoadOKHttpClient {
    private static OkHttpClient client;

    public static OkHttpClient loadClient() {
        if (client == null) {
            client = new OkHttpClient();


//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.addInterceptor
//            client = builder.build();
        }
        return client;
    }
}
