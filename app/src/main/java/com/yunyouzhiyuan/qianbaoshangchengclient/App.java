package com.yunyouzhiyuan.qianbaoshangchengclient;

import android.app.Application;
import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by wangjunqiang on 2017/1/5.
 */
public class App extends Application {
    private static Context context;//全局上下文
    private static String userId;//用户uid
    public LocationClient mLocationClient;//百度地图的控制器类

    @Override
    public void onCreate() {
        context = this;
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        mLocationClient = new LocationClient(this);
        SDKInitializer.initialize(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        App.userId = userId;
    }

    public static Context getContext() {
        return context;
    }

}
