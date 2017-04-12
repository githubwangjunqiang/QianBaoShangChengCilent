package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/6.
 */

public class Hotel_Fagnjian {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"goods_id":"14","goods_name":"清新绿色主题房","original_img":"/Public/upload/goods/2017/03-03/58b8d9fb8d2f1.jpg","keywords":"不含早|无窗    提前预约","shop_price":"238.00","store_count":"20","cat_id2":"50"},{"goods_id":"15","goods_name":"仅售60元！价值120元的酒店流金岁月主题房钟点房3小时，免费WiFi。入住便捷，居住舒适","original_img":"/Public/upload/goods/2017/03-03/58b8db950a8b1.jpg","keywords":"不含早|无窗  提前预约","shop_price":"60.00","store_count":"20","cat_id2":"51"}]
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
         * goods_id : 14
         * goods_name : 清新绿色主题房
         * original_img : /Public/upload/goods/2017/03-03/58b8d9fb8d2f1.jpg
         * keywords : 不含早|无窗    提前预约
         * shop_price : 238.00
         * store_count : 20
         * cat_id2 : 50
         */

        private String goods_id;
        private String goods_name;
        private String original_img;
        private String keywords;
        private String shop_price;
        private String store_count;
        private String cat_id2;

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

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getStore_count() {
            return store_count;
        }

        public void setStore_count(String store_count) {
            this.store_count = store_count;
        }

        public String getCat_id2() {
            return cat_id2;
        }

        public void setCat_id2(String cat_id2) {
            this.cat_id2 = cat_id2;
        }
    }
}
