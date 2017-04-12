package com.yunyouzhiyuan.qianbaoshangchengclient.ui.okhttp;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ${王俊强} on 2017/2/27.
 */

public class CommonRequset {
    public static Request postRequset(String httpurl, RequestParams params) {
        Request.Builder builder = new Request.Builder();
        FormBody.Builder body = new FormBody.Builder();
        if(params != null){
            for (Map.Entry<String, String> entry : params.getMaps().entrySet()) {
                body.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.url(httpurl).post(body.build()).build();
    }

    public static Request getRequset(String httpurl, RequestParams params) {
        Request.Builder builder = new Request.Builder();
        StringBuilder urlBuilder = new StringBuilder(httpurl).append("?");
        if(params != null){
            for (Map.Entry<String, String> entry : params.getMaps().entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return builder.url(urlBuilder.deleteCharAt(urlBuilder.length() - 1).toString()).get().build();
    }
    /**
     * 文件上传请求
     *
     * @return
     */
    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");

    public static Request createMultiPostRequest(String url, RequestParams params) {

        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.getMapsobj().entrySet()) {
                if (entry.getValue() instanceof File) {
                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(FILE_TYPE, (File) entry.getValue()));
                } else if (entry.getValue() instanceof String) {

                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null, (String) entry.getValue()));
                }
            }
        }
        return new Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build();
    }
}
