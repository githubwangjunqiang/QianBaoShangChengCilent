package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/10.
 */

public class TuiJian {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"store_logo":"/Public/upload/seller/2017/02-28/58b54940799e0.jpg","store_id":"3","store_name":"王老板通州","gprice":"110.00"}]
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
         * store_logo : /Public/upload/seller/2017/02-28/58b54940799e0.jpg
         * store_id : 3
         * store_name : 王老板通州
         * gprice : 110.00
         */

        private String store_logo;
        private String store_id;
        private String store_name;
        private String gprice;

        public String getStore_logo() {
            return store_logo;
        }

        public void setStore_logo(String store_logo) {
            this.store_logo = store_logo;
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

        public String getGprice() {
            return gprice;
        }

        public void setGprice(String gprice) {
            this.gprice = gprice;
        }
    }
}
