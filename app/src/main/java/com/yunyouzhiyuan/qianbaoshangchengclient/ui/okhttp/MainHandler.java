package com.yunyouzhiyuan.qianbaoshangchengclient.ui.okhttp;

import android.os.Handler;
import android.os.Looper;


/**
 * Created by ${王俊强} on 2017/2/10.
 */

public class MainHandler extends Handler {
    private static volatile MainHandler instance;

    public static MainHandler getInstance() {
        if (null == instance) {
            synchronized (MainHandler.class) {
                if (null == instance) {
                    instance = new MainHandler();
                }
            }
        }
        return instance;
    }
    private MainHandler() {
        super(Looper.getMainLooper());
    }
}
