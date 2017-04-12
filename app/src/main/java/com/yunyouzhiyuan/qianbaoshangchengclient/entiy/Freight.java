package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/7.
 */

public class Freight {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"shipping_price":"0.00","send_price":"0.00"}]
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
         * shipping_price : 0.00
         * send_price : 0.00
         */

        private String shipping_price;
        private String send_price;

        public String getShipping_price() {
            return shipping_price;
        }

        public void setShipping_price(String shipping_price) {
            this.shipping_price = shipping_price;
        }

        public String getSend_price() {
            return send_price;
        }

        public void setSend_price(String send_price) {
            this.send_price = send_price;
        }
    }
}
