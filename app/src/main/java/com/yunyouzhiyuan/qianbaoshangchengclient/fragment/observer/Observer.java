package com.yunyouzhiyuan.qianbaoshangchengclient.fragment.observer;

import com.baidu.location.BDLocation;

/**
 * Created by ${王俊强} on 2017/2/13.
 * 观察者接口
 */
public interface Observer {
    void upLocation(BDLocation location);
    void upCity();
    void upImage();
    void nodifyPositioning();
    void nodifyDingdan();
}
