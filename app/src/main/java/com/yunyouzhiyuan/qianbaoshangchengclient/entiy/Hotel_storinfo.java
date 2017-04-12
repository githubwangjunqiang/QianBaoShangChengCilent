package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

/**
 * Created by ${王俊强} on 2017/3/6.
 */

public class Hotel_storinfo {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"store_id":"4","store_name":"王老实酒店","store_phone":"12345678","province_id":"1","city_id":"2","district":"188","store_address":"待完善","store_time":"1487653305","store_end_time":"0","store_logo":"/Public/upload/seller/2017/03-03/58b8d8e83ae0f.jpg","store_desccredit":"0.00","lat":"39.871068","lng":"116.729844","distance":12253.04,"city":"北京市","location":"北京市北京市通州区","avgcomment":0,"commentcount":"0","collect":0}
     */

    private int retcode;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public  class DataBean {
        /**
         * store_id : 4
         * store_name : 王老实酒店
         * store_phone : 12345678
         * province_id : 1
         * city_id : 2
         * district : 188
         * store_address : 待完善
         * store_time : 1487653305
         * store_end_time : 0
         * store_logo : /Public/upload/seller/2017/03-03/58b8d8e83ae0f.jpg
         * store_desccredit : 0.00
         * lat : 39.871068
         * lng : 116.729844
         * distance : 12253.04
         * city : 北京市
         * location : 北京市北京市通州区
         * avgcomment : 0
         * commentcount : 0
         * collect : 0
         */

        private String store_id;
        private String store_name;
        private String store_phone;
        private String province_id;
        private String city_id;
        private String district;
        private String store_address;
        private String store_time;
        private String store_end_time;
        private String store_logo;
        private String store_desccredit;
        private String lat;
        private String lng;
        private double distance;
        private String city;
        private String location;
        private int avgcomment;
        private String commentcount;
        private int collect;

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_phone() {
            return store_phone;
        }

        public void setStore_phone(String store_phone) {
            this.store_phone = store_phone;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getStore_address() {
            return store_address;
        }

        public void setStore_address(String store_address) {
            this.store_address = store_address;
        }

        public String getStore_time() {
            return store_time;
        }

        public void setStore_time(String store_time) {
            this.store_time = store_time;
        }

        public String getStore_end_time() {
            return store_end_time;
        }

        public void setStore_end_time(String store_end_time) {
            this.store_end_time = store_end_time;
        }

        public String getStore_logo() {
            return store_logo;
        }

        public void setStore_logo(String store_logo) {
            this.store_logo = store_logo;
        }

        public String getStore_desccredit() {
            return store_desccredit;
        }

        public void setStore_desccredit(String store_desccredit) {
            this.store_desccredit = store_desccredit;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getAvgcomment() {
            return avgcomment;
        }

        public void setAvgcomment(int avgcomment) {
            this.avgcomment = avgcomment;
        }

        public String getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(String commentcount) {
            this.commentcount = commentcount;
        }

        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }
    }
}
