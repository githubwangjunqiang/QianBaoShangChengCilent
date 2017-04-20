package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/1/14.
 */
public class KTV implements Serializable{

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [
     * {"
     * sc_id":"4",
     * "store_id":"5",
     * "user_id":"7",
     * "store_name":"王KTV",
     * "store_desccredit":"0.00",
     * "store_logo":"/Public/upload/seller/2017/02-24/58af948daf5b2.jpg",
     * "seo_description":"",
     * "gprice":"110.00",
     * "district":"通州区"
     * ,"goods_list":
     * [
     * {
     * "shop_price":"38.00",
     * "market_price":"38.00",
     * "goods_name":"KTV",
     * "store_cat_id1":"9",
     * "goods_id":"12",
     * "sales_sum":"0",
     * "prom_type":"0",
     * "original_img":""}
     * ,{
     * "shop_price":"80.00",
     * "market_price":"178.00",
     * "goods_name":"周一至周日王KTV团小房欢唱",
     * "store_cat_id1":"10",
     * "goods_id":"13",
     * "sales_sum":"0",
     * "prom_type":"2",
     * "original_img":"/Public/upload/goods/2017/02-24/58afcf5d337d8.jpg"
     * }
     * ]
     * ,
     * "distance":0.5
     * }
     * ]
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
         * sc_id : 4
         * store_id : 5
         * user_id : 7
         * store_name : 王KTV
         * store_desccredit : 0.00
         * store_logo : /Public/upload/seller/2017/02-24/58af948daf5b2.jpg
         * seo_description :
         * gprice : 110.00
         * district : 通州区
         * goods_list : [{"shop_price":"38.00","market_price":"38.00","goods_name":"KTV","store_cat_id1":"9","goods_id":"12","sales_sum":"0","prom_type":"0","original_img":""},{"shop_price":"80.00","market_price":"178.00","goods_name":"周一至周日王KTV团小房欢唱","store_cat_id1":"10","goods_id":"13","sales_sum":"0","prom_type":"2","original_img":"/Public/upload/goods/2017/02-24/58afcf5d337d8.jpg"}]
         * distance : 0.5
         */

        private String sc_id;
        private String store_id;
        private String user_id;
        private String store_name;
        private String store_desccredit;
        private String store_logo;
        private String seo_description;
        private String gprice;
        private String district;
        private double distance;
        private List<GoodsListBean> goods_list;

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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_desccredit() {
            return store_desccredit;
        }

        public void setStore_desccredit(String store_desccredit) {
            this.store_desccredit = store_desccredit;
        }

        public String getStore_logo() {
            return store_logo;
        }

        public void setStore_logo(String store_logo) {
            this.store_logo = store_logo;
        }

        public String getSeo_description() {
            return seo_description;
        }

        public void setSeo_description(String seo_description) {
            this.seo_description = seo_description;
        }

        public String getGprice() {
            return gprice;
        }

        public void setGprice(String gprice) {
            this.gprice = gprice;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean implements Serializable{
            /**
             * shop_price : 38.00
             * market_price : 38.00
             * goods_name : KTV
             * store_cat_id1 : 9
             * goods_id : 12
             * sales_sum : 0
             * prom_type : 0
             * original_img :
             */

            private String shop_price;
            private String market_price;
            private String goods_name;
            private String store_cat_id1;
            private String goods_id;
            private String sales_sum;
            private String prom_type;
            private String original_img;

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

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getStore_cat_id1() {
                return store_cat_id1;
            }

            public void setStore_cat_id1(String store_cat_id1) {
                this.store_cat_id1 = store_cat_id1;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getSales_sum() {
                return sales_sum;
            }

            public void setSales_sum(String sales_sum) {
                this.sales_sum = sales_sum;
            }

            public String getProm_type() {
                return prom_type;
            }

            public void setProm_type(String prom_type) {
                this.prom_type = prom_type;
            }

            public String getOriginal_img() {
                return original_img;
            }

            public void setOriginal_img(String original_img) {
                this.original_img = original_img;
            }
        }
    }
}
