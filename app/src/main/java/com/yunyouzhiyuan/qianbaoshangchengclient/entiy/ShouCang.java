package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/15.
 */
public class ShouCang {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"store_id":"2","store_name":"扣扣号逛街","store_logo":"/Uploads/Picture/2017-02-16/20170216131242209.jpg","collectsum":"1"}]
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

    public  class DataBean {
        /**
         * store_id : 2
         * store_name : 扣扣号逛街
         * store_logo : /Uploads/Picture/2017-02-16/20170216131242209.jpg
         * collectsum : 1
         */

        private String store_id;
        private String store_name;
        private String store_logo;
        private String collectsum;
        private String sc_id;

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

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_logo() {
            return store_logo;
        }

        public void setStore_logo(String store_logo) {
            this.store_logo = store_logo;
        }

        public String getCollectsum() {
            return collectsum;
        }

        public void setCollectsum(String collectsum) {
            this.collectsum = collectsum;
        }
    }
}
