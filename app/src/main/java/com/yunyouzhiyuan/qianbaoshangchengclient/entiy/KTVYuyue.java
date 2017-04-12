package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/8.
 */

public class KTVYuyue {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"key":"11_14_20","price":"158.00","hour":"12:00-18:00","goods_attr":"3","housename":"12:00-18:00内 任选3小时"},{"key":"11_15_20","price":"288.00","hour":"18:00-00:00","goods_attr":"3","housename":"18:00-00:00内 任选3小时"},{"key":"11_16_20","price":"188.00","hour":"00:00-06:00","goods_attr":"3","housename":"00:00-06:00内 任选3小时"}]
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
         * key : 11_14_20
         * price : 158.00
         * hour : 12:00-18:00
         * goods_attr : 3
         * housename : 12:00-18:00内 任选3小时
         */

        private String key;
        private String price;
        private String hour;
        private String goods_attr;
        private String housename;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getGoods_attr() {
            return goods_attr;
        }

        public void setGoods_attr(String goods_attr) {
            this.goods_attr = goods_attr;
        }

        public String getHousename() {
            return housename;
        }

        public void setHousename(String housename) {
            this.housename = housename;
        }
    }
}
