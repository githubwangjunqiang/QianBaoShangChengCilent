package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/16.
 */

public class ZiFenlei {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"12","mobile_name":"超市","image":"/Public/upload/category/2017/02-16/58a51cda351b1.png"},{"id":"13","mobile_name":"下午茶","image":"/Public/upload/category/2017/02-16/58a51d0326152.png"},{"id":"14","mobile_name":"鲜果","image":"/Public/upload/category/2017/02-16/58a51d2f577d8.png"},{"id":"15","mobile_name":"美食","image":"/Public/upload/category/2017/02-16/58a51d895a014.png"},{"id":"16","mobile_name":"西餐","image":"/Public/upload/category/2017/02-16/58a51dc31ccad.png"},{"id":"17","mobile_name":"精选小吃","image":"/Public/upload/category/2017/02-16/58a51e28c9571.png"},{"id":"18","mobile_name":"鲜花蛋糕","image":"/Public/upload/category/2017/02-16/58a51e692c05d.png"},{"id":"20","mobile_name":"日韩小吃","image":"/Public/upload/category/2017/02-16/58a51ebebcf55.png"}]
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
         * id : 12
         * mobile_name : 超市
         * image : /Public/upload/category/2017/02-16/58a51cda351b1.png
         */

        private String id;
        private String mobile_name;
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile_name() {
            return mobile_name;
        }

        public void setMobile_name(String mobile_name) {
            this.mobile_name = mobile_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
