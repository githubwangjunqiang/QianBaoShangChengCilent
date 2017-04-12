package com.yunyouzhiyuan.qianbaoshangchengclient.fragment.observer;

import com.baidu.location.BDLocation;

/**
 * Created by ${王俊强} on 2017/2/13.
 */

public class ConcreteSubject extends Subject {

    private static ConcreteSubject instance = null;
    private BDLocation location;

    private ConcreteSubject() {
        // TODO Auto-generated constructor stub
    }

    public static synchronized ConcreteSubject getInstance() {
        if (instance == null) {
            instance = new ConcreteSubject();
        }
        return instance;
    }

    public void change(BDLocation location) {
        this.location = location;
        //状态发生改变，通知各个观察者

        this.nodifyObservers(location);
    }

    public void changeCity() {
        //状态发生改变，通知各个观察者
        this.nodifyCity();
    }
    public void changeimage() {
        //状态发生改变，通知各个观察者
        this.nodifyImage();
    }

    public BDLocation getBdLocation() {
        return location;
    }
}
