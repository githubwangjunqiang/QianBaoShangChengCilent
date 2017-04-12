package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/16.
 */
public class Bann {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"ad_code":"/Public/upload/ad/2017/02-16/58a50e65efbd5.jpg","ad_link":"www.baidu.com"},{"ad_code":"/Public/upload/ad/2017/02-16/58a50e86c1ce6.jpg","ad_link":"www.sina.com"},{"ad_code":"/Public/upload/ad/2017/02-16/58a50f5968342.jpg","ad_link":"www.hao123.com"}]
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
         * ad_code : /Public/upload/ad/2017/02-16/58a50e65efbd5.jpg
         * ad_link : www.baidu.com
         */

        private String ad_code;
        private String ad_link;

        public String getAd_code() {
            return ad_code;
        }

        public void setAd_code(String ad_code) {
            this.ad_code = ad_code;
        }

        public String getAd_link() {
            return ad_link;
        }

        public void setAd_link(String ad_link) {
            this.ad_link = ad_link;
        }
    }
}
