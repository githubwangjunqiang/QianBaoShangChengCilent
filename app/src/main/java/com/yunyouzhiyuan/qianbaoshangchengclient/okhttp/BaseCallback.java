package com.yunyouzhiyuan.qianbaoshangchengclient.okhttp;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Created by ${王俊强} on 2017/3/24.
 */

public interface BaseCallback {


    public abstract class ComonCallback<T> implements BaseCallback {

        static Type getType(Class<?> tClass) {
            Type type = tClass.getGenericSuperclass();
            if (type instanceof Class) {
                return null;
            }

            ParameterizedType parameterizedType = (ParameterizedType) type;
            return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
        }

        Type type;

        public ComonCallback() {
            this.type = getType(this.getClass());
        }

        /**
         * 回调成功
         */
        protected abstract void onSuccess(T t);

        /**
         * 回调失败
         */
        protected abstract void onError(int code, String msg);

        /**
         * 回调失败 请求为链接通
         */
        protected abstract void onFailure(Call call, IOException e);

    }

}
