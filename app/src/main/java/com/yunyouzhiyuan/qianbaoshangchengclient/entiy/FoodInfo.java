package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/23.
 */

public class FoodInfo implements Serializable{

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"cat_id":"1","cat_name":"热销","info":[{"goods_id":"1","goods_name":"特色炒面","shop_price":"20.00","market_price":"20.00","store_count":"50"},{"goods_id":"2","goods_name":"尖椒茄子汆面","shop_price":"17.00","market_price":"17.00","store_count":"160"}]},{"cat_id":"2","cat_name":"热菜系列","info":[{"goods_id":"4","goods_name":"干锅千叶豆腐","shop_price":"22.90","market_price":"23.90","store_count":"100"}]},{"cat_id":"3","cat_name":"凉菜系列","info":[{"goods_id":"5","goods_name":"手撕大拌菜","shop_price":"18.90","market_price":"20.90","store_count":"50"}]},{"cat_id":"4","cat_name":"汤系列","info":[{"goods_id":"6","goods_name":"酸辣汤","shop_price":"18.90","market_price":"20.90","store_count":"50"}]},{"cat_id":"5","cat_name":"主食","info":[{"goods_id":"7","goods_name":"米饭","shop_price":"3.00","market_price":"3.00","store_count":"100"}]},{"cat_id":"6","cat_name":"酒水","info":[{"goods_id":"3","goods_name":"怡宝矿泉水","shop_price":"0.00","market_price":"3.00","store_count":"90"}]}]
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

    public static class DataBean implements Serializable{
        /**
         * cat_id : 1
         * cat_name : 热销
         * info : [{"goods_id":"1","goods_name":"特色炒面","shop_price":"20.00","market_price":"20.00","store_count":"50"},{"goods_id":"2","goods_name":"尖椒茄子汆面","shop_price":"17.00","market_price":"17.00","store_count":"160"}]
         */

        private String cat_id;
        private String cat_name;
        private List<InfoBean> info;
        private boolean isXuanzhong;

        public boolean isXuanzhong() {
            return isXuanzhong;
        }

        public void setXuanzhong(boolean xuanzhong) {
            isXuanzhong = xuanzhong;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean implements Serializable{
            /**
             * goods_id : 1
             * goods_name : 特色炒面
             * shop_price : 20.00
             * market_price : 20.00
             * store_count : 50
             */

            private String goods_id;
            private String goods_name;
            private String shop_price;
            private String market_price;
            private String store_count;
            private String original_img;
            private int count;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getOriginal_img() {
                return original_img;
            }

            public void setOriginal_img(String original_img) {
                this.original_img = original_img;
            }

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

            public String getShop_price() {
                return shop_price;
            }

            public void setShop_price(String shop_price) {
                this.shop_price = shop_price;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public String getStore_count() {
                return store_count;
            }

            public void setStore_count(String store_count) {
                this.store_count = store_count;
            }
        }
    }
}
