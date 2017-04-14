package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/16.
 */
public class ShouCangShop {
    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"store_id":"2","store_name":"扣扣号逛街",
     * "store_logo":"/Uploads/Picture/2017-02-16/20170216131242209.jpg",
     * "collectsum":"1"}]
     */

    private int retcode;
    private String msg;
    private List<ShouCangShop.DataBean> data;

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

    public List<ShouCangShop.DataBean> getData() {
        return data;
    }

    public void setData(List<ShouCangShop.DataBean> data) {
        this.data = data;
    }

    public class DataBean {
        /**
         * "goods_id": "商品id",
         * "goods_name": "商品名称",
         * "original_img": "商品图片",
         * "shop_price": "商品价格"
         * "store_id": "6",
         * "sc_id": "5"
         */

        private String goods_id;
        private String goods_name;
        private String original_img;
        private String shop_price;
        private String store_id;

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getSc_id() {
            return sc_id;
        }

        public void setSc_id(String sc_id) {
            this.sc_id = sc_id;
        }

        private String sc_id;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }
    }

}
