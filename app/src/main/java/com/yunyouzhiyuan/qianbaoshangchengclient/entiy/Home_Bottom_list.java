package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/13.
 */
public class Home_Bottom_list {


    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"sc_id":"2","store_id":"1","user_id":"2","store_name":"王老板美食","store_desccredit":"0.00","store_logo":"/Public/upload/seller/2017/03-16/58ca23810badd.jpg","coupon_name":"满100减30","consump_count":"220","prom_name":"立减17","expression":"17","distance":0.51},{"sc_id":"2","store_id":"2","user_id":"3","store_name":"扣扣号逛街","store_desccredit":"0.00","store_logo":"/Uploads/Picture/2017-02-16/20170216131242209.jpg","coupon_name":null,"consump_count":"3","prom_name":null,"expression":null,"distance":0.54},{"sc_id":"1","store_id":"3","user_id":"5","store_name":"王老板通州","store_desccredit":"0.00","store_logo":"/Public/upload/seller/2017/02-28/58b54940799e0.jpg","coupon_name":"优惠券满100减10","consump_count":"48","prom_name":null,"expression":null,"distance":0.51},{"sc_id":"3","store_id":"4","user_id":"6","store_name":"王老实酒店","store_desccredit":"0.00","store_logo":"/Public/upload/seller/2017/03-03/58b8d8e83ae0f.jpg","coupon_name":null,"consump_count":"14","prom_name":null,"expression":null,"distance":0.51},{"sc_id":"4","store_id":"5","user_id":"7","store_name":"王KTV","store_desccredit":"0.00","store_logo":"/Public/upload/seller/2017/02-24/58af948daf5b2.jpg","coupon_name":"满100减50","consump_count":"41","prom_name":null,"expression":null,"distance":20.81},{"sc_id":"5","store_id":"7","user_id":"10","store_name":"王家洗浴中心","store_desccredit":"0.00","store_logo":"/Public/upload/seller/2017/03-09/58c0ef930c2c1.jpg","coupon_name":null,"consump_count":"9","prom_name":null,"expression":null,"distance":0.51},{"sc_id":"6","store_id":"8","user_id":"12","store_name":"顶上功夫","store_desccredit":"0.00","store_logo":"/Public/upload/head_pic/2016/01-20/default_logo.png","coupon_name":null,"consump_count":"0","prom_name":null,"expression":null,"distance":0.51},{"sc_id":"7","store_id":"9","user_id":"13","store_name":"特色世界","store_desccredit":"0.00","store_logo":"/Public/upload/head_pic/2016/01-20/default_logo.png","coupon_name":null,"consump_count":"0","prom_name":null,"expression":null,"distance":0.51},{"sc_id":"1","store_id":"12","user_id":"25","store_name":"清风旭老板","store_desccredit":"0.00","store_logo":"/Public/upload/head_pic/2016/01-20/default_logo.png","coupon_name":null,"consump_count":"1","prom_name":null,"expression":null,"distance":0.01},{"sc_id":"1","store_id":"14","user_id":"30","store_name":"王者荣耀店铺","store_desccredit":"0.00","store_logo":"/Public/upload/head_pic/2016/01-20/default_logo.png","coupon_name":null,"consump_count":"0","prom_name":null,"expression":null,"distance":0.02}]
     */

    private int retcode;
    private String msg;
    private List<DataBean> data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sc_id : 2
         * store_id : 1
         * user_id : 2
         * store_name : 王老板美食
         * store_desccredit : 0.00
         * store_logo : /Public/upload/seller/2017/03-16/58ca23810badd.jpg
         * coupon_name : 满100减30
         * consump_count : 220
         * prom_name : 立减17
         * expression : 17
         * distance : 0.51
         */

        private String sc_id;
        private String store_id;
        private String user_id;
        private String store_name;
        private String store_desccredit;
        private String store_logo;
        private String coupon_name;
        private String consump_count;
        private String prom_name;
        private String expression;
        private double distance;

        public String getSc_id() {
            return sc_id;
        }

        public void setSc_id(String sc_id) {
            this.sc_id = sc_id;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_desccredit() {
            return store_desccredit;
        }

        public void setStore_desccredit(String store_desccredit) {
            this.store_desccredit = store_desccredit;
        }

        public String getStore_logo() {
            return store_logo;
        }

        public void setStore_logo(String store_logo) {
            this.store_logo = store_logo;
        }

        public String getCoupon_name() {
            return coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }

        public String getConsump_count() {
            return consump_count;
        }

        public void setConsump_count(String consump_count) {
            this.consump_count = consump_count;
        }

        public String getProm_name() {
            return prom_name;
        }

        public void setProm_name(String prom_name) {
            this.prom_name = prom_name;
        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }
    }
}
