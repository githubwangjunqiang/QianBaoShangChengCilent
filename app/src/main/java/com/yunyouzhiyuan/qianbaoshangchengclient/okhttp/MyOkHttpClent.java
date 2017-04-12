package com.yunyouzhiyuan.qianbaoshangchengclient.okhttp;

import android.os.Build;
import android.text.TextUtils;
import android.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ${王俊强} on 2017/3/24.
 */
public class MyOkHttpClent {

    private Builder mBuilder;

    /**
     * 私有构造方法 不允许外部来new
     */
    public MyOkHttpClent(Builder mBuilder) {
        this.mBuilder = mBuilder;
    }

    /**
     * 获取 okhttp 框架的 Request
     *
     * @return
     */
    public Request getRequest() {
        Request request = null;
        Request.Builder builder = new Request.Builder();

        if ("GET".equals(mBuilder.methob)) {
            builder.get();
            builder.url(getMosaicUrl(mBuilder.url));
            request = builder.build();
        } else if ("POST".equals(mBuilder.methob)) {
            RequestBody requestBody = getRequestBody();
            if (requestBody != null) {
                builder.post(requestBody);
                builder.url(mBuilder.url);
                request = builder.build();
            }
        }
        return request;
    }

    /**
     * get方法 如果用户add参数的话 我们给用户拼接起来 返回去
     *
     * @return
     */
    private String getMosaicUrl(String urlold) {
        StringBuilder builder = new StringBuilder(urlold);

        if (mBuilder.arrayMap != null) {
            builder.append("?");
            for (ArrayMap.Entry<String, Object> entry : mBuilder.arrayMap.entrySet()) {
                builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            builder.deleteCharAt(builder.length() - 1);
            mBuilder.arrayMap.clear();
            mBuilder.arrayMap = null;
        }
        if (mBuilder.hashMap != null) {
            builder.append("?");
            for (ArrayMap.Entry<String, Object> entry : mBuilder.hashMap.entrySet()) {
                builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            builder.deleteCharAt(builder.length() - 1);
            mBuilder.hashMap.clear();
            mBuilder.hashMap = null;
        }
        return builder.toString();
    }

    /**
     * 获取 RequestBody
     *
     * @return
     */
    private RequestBody getRequestBody() {
        if (mBuilder.isJsonParams) {//如果是json 字符串
            try {
                JSONObject jsonObject = new JSONObject();
                if (mBuilder.arrayMap != null) {
                    for (ArrayMap.Entry<String, Object> entry : mBuilder.arrayMap.entrySet()) {
                        jsonObject.put(entry.getKey(), entry.getValue());
                    }
                    mBuilder.arrayMap.clear();
                    mBuilder.arrayMap = null;
                } else if (mBuilder.hashMap != null) {
                    for (ArrayMap.Entry<String, Object> entry : mBuilder.hashMap.entrySet()) {
                        jsonObject.put(entry.getKey(), entry.getValue());
                    }
                    mBuilder.hashMap.clear();
                    mBuilder.hashMap = null;
                }
                return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString());


            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {//不是json 字符串  是表单形式
            FormBody.Builder builder = new FormBody.Builder();
            if (mBuilder.arrayMap != null) {
                for (ArrayMap.Entry<String, Object> entry : mBuilder.arrayMap.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue().toString());
                }
                mBuilder.arrayMap.clear();
                mBuilder.arrayMap = null;
            } else if (mBuilder.hashMap != null) {
                for (ArrayMap.Entry<String, Object> entry : mBuilder.hashMap.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue().toString());
                }
                mBuilder.hashMap.clear();
                mBuilder.hashMap = null;
            }
            return builder.build();
        }

    }

    /**
     * 链式调用完毕 发起请求
     */
    public Call enqueue(BaseCallback callback) {
        if (callback instanceof BaseCallback.ComonCallback) {
            Call request = HttpManager.getInstance().request(this, (BaseCallback.ComonCallback) callback);
            return request;
        } else {
            throw new NullPointerException("The incoming callback interface must be a subclass of " +
                    "BaseCallback ComonCallback");
        }
    }


    /**
     * 创建Builder 用来 返回链式调用
     *
     * @return
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * 静态内部类 Builder 用来链式创建 MyOkHttpClent
     */
    public static class Builder {
        private String url;//请求网络的地址 全拼
        private String methob;//请求网络的方法
        private boolean isJsonParams;//是否是 传递json 字符串

        private ArrayMap<String, Object> arrayMap;
        private HashMap<String, Object> hashMap;

        private Builder() {
            methob = "GET";
        }

        /**
         * 链式调用完毕
         *
         * @return 返回 MyOkHttpClent对象
         */
        public MyOkHttpClent build() {
            return new MyOkHttpClent(this);
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * get方法
         *
         * @return Builder
         */
        public Builder get() {
            methob = "GET";
            return this;
        }

        /**
         * post方法
         *
         * @return Builder
         */
        public Builder post() {
            methob = "POST";
            return this;
        }

        /**
         * json 上传一段json 文本
         *
         * @return Builder
         */
        public Builder json() {
            isJsonParams = true;
            return post();
        }

        /**
         * 添加 post 方法的请求体参数
         *
         * @return builder
         */
        public Builder addParam(String key, Object vaLue) {
            if (vaLue instanceof String) {
                if (TextUtils.isEmpty((String) vaLue)) {
                    return this;
                }
            }
            if (TextUtils.isEmpty(key)) {
                throw new NullPointerException("Parameter key cannot be null");
            } else if (vaLue == null) {
                return this;
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (arrayMap == null) {
                        arrayMap = new ArrayMap<>();
                    }
                    arrayMap.put(key, vaLue);
                } else {
                    if (hashMap == null) {
                        hashMap = new HashMap<>();
                    }
                    hashMap.put(key, vaLue);
                }
                return this;
            }
        }
    }
}
