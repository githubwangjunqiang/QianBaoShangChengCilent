package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/11.
 */
public class HomeStore_category {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"sc_name":"美食","sc_id":"1","sc_icon":"/Public/upload/store_class/2017/02-16/58a504b2cde7e.png"},{"sc_name":"外卖","sc_id":"2","sc_icon":"/Public/upload/store_class/2017/02-16/58a504eb5e6ca.png"},{"sc_name":"酒店","sc_id":"3","sc_icon":"/Public/upload/store_class/2017/02-16/58a505072d5a8.png"},{"sc_name":"KTV","sc_id":"4","sc_icon":"/Public/upload/store_class/2017/02-16/58a5052678a68.png"},{"sc_name":"电影","sc_id":"5","sc_icon":"/Public/upload/store_class/2017/02-16/58a50558200da.png"}]
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

    public class DataBean {
        /**
         * sc_name : 美食
         * sc_id : 1
         * sc_icon : /Public/upload/store_class/2017/02-16/58a504b2cde7e.png
         */

        private String sc_name;
        private String sc_id;
        private String sc_icon;

        public String getSc_name() {
            return sc_name;
        }

        public void setSc_name(String sc_name) {
            this.sc_name = sc_name;
        }

        public String getSc_id() {
            return sc_id;
        }

        public void setSc_id(String sc_id) {
            this.sc_id = sc_id;
        }

        public String getSc_icon() {
            return sc_icon;
        }

        public void setSc_icon(String sc_icon) {
            this.sc_icon = sc_icon;
        }
    }
}
