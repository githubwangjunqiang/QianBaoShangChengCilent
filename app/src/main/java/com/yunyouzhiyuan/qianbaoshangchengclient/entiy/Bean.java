package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import com.yunyouzhiyuan.qianbaoshangchengclient.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by ${王俊强} on 2017/2/15.
 */

public class Bean {
    public static final int SHEZHI = 11;//我的页面-》设置界面返回码
    public static final int LOGIN = 12;//我的页面-》登陆界面返回码
    public static final int SHEZHI_PHOTO = 13;//设置界面拍照返回码
    public static final int PHOTO_REQUEST_CUT = 14;//设置界面裁剪返回码
    public static final int PHOTO_REQUEST_GALLERY = 15;//设置界面相册返回码
    public static final int ADD_ADDRESS = 16;//地址界面跳转新建地址 返回码
    public static final int DING_ADDRESS = 17;//外卖订单跳转选择地址界面
    public static final String ADDRESS_SAVE = "qianbaoshangcheng_address.address";//默认地址存入的文件名字
    public static final String CITY_ID = "qianbaoshangcheng_ctid.city_id";//默认城市地址的文件名称
    public static final String ADDRESS_TYPE = "address";//默认地址存入的文件夹名称
    public static final int YOUHUIQUAN = 18;//外卖订单跳转优惠券返回码
    public static final int TAKEPHOTO = 19;//评价订单界面 跳转拍照 返回码
    public static final int TAKEPHOTOGALLERY = 20;//评价订单界面 跳转图片选择器 返回码
    public static final int DINGDANWANCHENG = 21;//评价订单界面 评价完成后，返回订单界面返回码
    public static final int CITYIDID = 22;//选择地址界面 返回码 主页选择城市数据
    public static final int UPPASSWORD = 23;//去修改密码界面
    public static final int FOODCODE = 24;//外卖下订单支付完成后返回详情页
    /**
     * 极光推送的key
     */
    public static final String JPush_Appkey = "c02eb34328ef2f7e4f05204f";


    public static final String APPPAKEGER = "com.yunyouzhiyuan.qianbaoshangcheng";//商家端的包名
    public static final String APPURL = "http://a.app.qq.com/o/simple.jsp?pkgname=com.yunyouzhiyuan.liushao";//商家端微下载链接;
    /**
     * 微信的appid
     */
    public static final String WE_XIN_APPID = "wx8944d7288a4b0123";


    private static City_id city_id;//首页地址
    public static String city_id_id = null;

    public static City_id getCity_id() {
        return city_id;
    }

    public static void setCity_id(City_id city) {
        city_id = city;
    }

    /**
     * 持久化到文件中 下次地址依然存在
     */
    public static boolean saveAdress(Address.DataBean data) {

        try {
            File file = new File(App.getContext().getExternalFilesDir(ADDRESS_TYPE), ADDRESS_SAVE);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(data);
            oos.flush();
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 持久化到文件中 首页城市地址
     */
    public static boolean saveCity(City_id city_id) {

        try {
            File file = new File(App.getContext().getExternalFilesDir(ADDRESS_TYPE), CITY_ID);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(city_id);
            oos.flush();
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从序列化中 读取信息
     *
     * @return
     */
    public static Address.DataBean readAdress() {
        try {
            File file = new File(App.getContext().getExternalFilesDir(ADDRESS_TYPE), ADDRESS_SAVE);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Address.DataBean data = (Address.DataBean) ois.readObject();
            if (data == null) {
                return null;
            }
            return data;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从序列化中 读取城市信息
     *
     * @return
     */
    public static City_id readCity() {
        try {
            File file = new File(App.getContext().getExternalFilesDir(ADDRESS_TYPE), CITY_ID);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            City_id data = (City_id) ois.readObject();
            if (data == null) {
                return null;
            }
            return data;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
