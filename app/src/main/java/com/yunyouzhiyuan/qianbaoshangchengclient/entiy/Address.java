package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/15.
 */
public class Address implements Serializable{

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"address_id":"35","user_id":"1","consignee":"张谷泉","province":"5827","city":"6088","district":"6099","address":"南山区西丽镇留仙大道1001号","twon":"6104","mobile":"15872123653","is_default":"1","address_name":"辽宁省大连市西岗区"}]
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

    public  class DataBean implements Serializable{
        /**
         * address_id : 35
         * user_id : 1
         * consignee : 张谷泉
         * province : 5827
         * city : 6088
         * district : 6099
         * address : 南山区西丽镇留仙大道1001号
         * twon : 6104
         * mobile : 15872123653
         * is_default : 1
         * address_name : 辽宁省大连市西岗区
         */

        private String address_id;
        private String user_id;
        private String consignee;
        private String province;
        private String city;
        private String district;
        private String address;
        private String twon;
        private String mobile;
        private String is_default;
        private String address_name;

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTwon() {
            return twon;
        }

        public void setTwon(String twon) {
            this.twon = twon;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getAddress_name() {
            return address_name;
        }

        public void setAddress_name(String address_name) {
            this.address_name = address_name;
        }
    }
}
