package com.yunyouzhiyuan.qianbaoshangchengclient.fragment.observer;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.ui.okhttp.MainHandler;
import com.yunyouzhiyuan.qianbaoshangchengclient.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/13.
 * 被观察者的基类
 */

public abstract class Subject {
    private static List<Observer> list = new ArrayList<>();//保存观察者的对象

    /**
     * 注册观察者对象
     *
     * @param observer 观察者对象
     */
    public void attach(Observer observer) {

        list.add(observer);
        LogUtils.w("注册一个观察者");
    }

    /**
     * 删除观察者对象
     *
     * @param observer 观察者对象
     */
    public void detach(Observer observer) {

        list.remove(observer);
        LogUtils.w("删除观察者对象");
    }

    /**
     * 通知所有注册的观察者对象
     */
    public void nodifyObservers(final BDLocation location) {
        MainHandler mainHandler = MainHandler.getInstance();
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                for (Observer observer : list) {
                    if(observer != null){
                        observer.upLocation(location);
                    }
                }
            }
        });

    }

    /**
     * 通知所有注册的观察者对象
     */
    public void nodifyCity() {
        MainHandler mainHandler = MainHandler.getInstance();
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                for (Observer observer : list) {
                    if(observer != null){
                        observer.upCity();
                    }

                }
            }
        });

    }

    /**
     * 通知所有注册的观察者对象
     */
    public void nodifyImage() {
        MainHandler mainHandler = MainHandler.getInstance();
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                for (Observer observer : list) {
                    if(observer != null){
                        observer.upImage();
                    }
                }
            }
        });

    }
    /**
     * 通知所有注册的观察者对象 接收到订单验证通知
     */
    public void nodifyDingdan() {
        MainHandler mainHandler = MainHandler.getInstance();
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                for (Observer observer : list) {
                    if(observer != null){
                        observer.nodifyDingdan();
                    }
                }
            }
        });

    }

    /**
     * 通知所有注册的观察者去定位
     */
    public void nodifyPositioning() {
        MainHandler mainHandler = MainHandler.getInstance();
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                for (Observer observer : list) {
                    if(observer != null){
                        observer.nodifyPositioning();
                    }
                }
            }
        });

    }

    public void clearObserver() {
        list.clear();
    }
}
