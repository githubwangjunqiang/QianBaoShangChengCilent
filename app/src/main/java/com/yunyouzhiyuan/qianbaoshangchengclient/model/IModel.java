package com.yunyouzhiyuan.qianbaoshangchengclient.model;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;
import com.yunyouzhiyuan.qianbaoshangchengclient.R;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.okhttp.LoadOKHttpClient;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.okhttp.MainHandler;

import okhttp3.OkHttpClient;

/**
 * Created by ${王俊强} on 2017/2/10.
 */
public class IModel {
    protected OkHttpClient client = LoadOKHttpClient.loadClient();
    protected MainHandler instance = MainHandler.getInstance();

    protected void runUiOnSuccess(final Object obj, final AsyncCallBack callBack) {
        instance.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (obj == null) {
                        callBack.onSucceed("");
                        return;
                    }
                    callBack.onSucceed(obj);
                } catch (RuntimeException r) {
                    r.printStackTrace();
                }

            }
        });
    }

    protected void runUiOnError(final Object obj, final AsyncCallBack callBack) {
        instance.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (obj == null) {
                        callBack.onError(App.getContext().getString(R.string.qingqiushibai));
                        return;
                    }
                    callBack.onError(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface AsyncCallBack {
        void onSucceed(Object obj);

        void onError(Object obj);
    }
}
