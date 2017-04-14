package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

/**
 * Created by ${王俊强} on 2017/2/14.
 */
public class StorInfo {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"store_id":"3","store_name":"
     * 王老板通州","province_id":"1","
     * city_id":"2","district":"188"
     * ,"store_address":"待完善",
     * "store_time":"1487571126",
     * "store_end_time":"0",
     * "store_logo":"/Public/upload/seller/2017/02-28/58b54940799e0.jpg",
     * "store_desccredit":"0.00",
     * "lat":"39.871068",
     * "lng":"116.729844",
     * "location":"北京市市辖区通州区",
     * "avgcomment":null,"collect":1}
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
         * store_id : 3
         * store_name : 王老板通州
         * province_id : 1
         * city_id : 2
         * district : 188
         * store_address : 待完善
         * store_time : 1487571126
         * store_end_time : 0
         * store_logo : /Public/upload/seller/2017/02-28/58b54940799e0.jpg
         * store_desccredit : 0.00
         * lat : 39.871068
         * lng : 116.729844
         * location : 北京市市辖区通州区
         * avgcomment : null
         * collect : 1"collect":"收藏状态"  0 未收藏  1 已收藏
         "store_phone":店铺电话

         */

        private String store_id;
        private String store_name;
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
        private String location;
        private String store_phone;

        public String getStore_phone() {
            return store_phone;
        }

        public void setStore_phone(String store_phone) {
            this.store_phone = store_phone;
        }

        private Object avgcomment;
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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Object getAvgcomment() {
            return avgcomment;
        }

        public void setAvgcomment(Object avgcomment) {
            this.avgcomment = avgcomment;
        }

        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }
    }
}
