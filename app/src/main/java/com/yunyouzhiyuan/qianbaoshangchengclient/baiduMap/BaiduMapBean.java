package com.yunyouzhiyuan.qianbaoshangchengclient.baiduMap;

import com.baidu.location.BDLocation;
import com.yunyouzhiyuan.qianbaoshangchengclient.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by ${王俊强} on 2017/2/13.
 */

public class BaiduMapBean {
    /**
     * 百度地图AK
     */
    public static final String AppKey = "rLZfhMeG7YOD8Swy2G2wfTCbvXFnsHBg";
    private static String WENJIAN = "qianbaoshangcheng.location";

    public static BDLocation getLocation() {
        return location;
    }

    public static BDLocation location;//定位成功 返回的数据类

    public static void setLocation(BDLocation location) {
        BaiduMapBean.location = location;
    }

    /**
     * 写入本地
     */
    public static void saveLoc(){
        if(location!= null){
            saveLocation(new Location(location.getLatitude(),location.getLongitude()));
        }
    }


    /**
     * 持久化到文件中 下次地址依然存在
     * @param location
     */
    public static boolean saveLocation(Location location) {
        boolean s = true;
        try {
            File file = new File(App.getContext().getExternalFilesDir("定位"), WENJIAN);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(location);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            s = false;
        }
        return s;
    }

    /**
     * 从序列化中 读取信息
     *
     * @return
     */
    public static BDLocation getFileLocation() {
        try {
            File file = new File(App.getContext().getExternalFilesDir("定位"), WENJIAN);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Location location = (Location) ois.readObject();
            if (location == null) {
                return null;
            }
            BDLocation bdLocation = new BDLocation();
            bdLocation.setLatitude(location.lat);
            bdLocation.setLongitude(location.log);
            return bdLocation;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Location {
        double lat, log;

        public Location(double lat, double log) {
            this.lat = lat;
            this.log = log;
        }
    }
}
