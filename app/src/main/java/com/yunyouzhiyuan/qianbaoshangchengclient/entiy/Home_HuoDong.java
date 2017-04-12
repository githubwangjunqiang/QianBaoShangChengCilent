package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/17.
 */

public class Home_HuoDong {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"ad_code":"/Public/upload/ad/2017/03-17/58cb95b3b93db.jpg","ad_link":""},{"ad_code":"/Public/upload/ad/2017/03-17/58cb96436da76.jpg","ad_link":""},{"ad_code":"/Public/upload/ad/2017/03-17/58cb97317f808.jpg","ad_link":""},{"ad_code":"/Public/upload/ad/2017/03-17/58cb97bc5c04e.jpg","ad_link":""},{"ad_code":"/Public/upload/ad/2017/03-17/58cb97c974a9d.jpg","ad_link":""}]
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
         * ad_code : /Public/upload/ad/2017/03-17/58cb95b3b93db.jpg
         * ad_link :
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
