package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/13.
 */

public class KTV_list_data {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"yuding":{"goods_id":"12"},"tuangou":[{"goods_id":"13","goods_name":"周一至周日王KTV团小房欢唱","store_cat_id1":"10","shop_price":"80.00","market_price":"178.00","sales_sum":"0","original_img":"/Public/upload/goods/2017/02-24/58afcf5d337d8.jpg","prom_type":"0"}]}
     */

    private int retcode;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * yuding : {"goods_id":"12"}
         * tuangou : [{"goods_id":"13","goods_name":"周一至周日王KTV团小房欢唱","store_cat_id1":"10","shop_price":"80.00","market_price":"178.00","sales_sum":"0","original_img":"/Public/upload/goods/2017/02-24/58afcf5d337d8.jpg","prom_type":"0"}]
         */

        private YudingBean yuding;
        private List<TuangouBean> tuangou;

        public YudingBean getYuding() {
            return yuding;
        }

        public void setYuding(YudingBean yuding) {
            this.yuding = yuding;
        }

        public List<TuangouBean> getTuangou() {
            return tuangou;
        }

        public void setTuangou(List<TuangouBean> tuangou) {
            this.tuangou = tuangou;
        }

        public static class YudingBean {
            /**
             * goods_id : 12
             */

            private String goods_id;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }
        }

        public static class TuangouBean {
            /**
             * goods_id : 13
             * goods_name : 周一至周日王KTV团小房欢唱
             * store_cat_id1 : 10
             * shop_price : 80.00
             * market_price : 178.00
             * sales_sum : 0
             * original_img : /Public/upload/goods/2017/02-24/58afcf5d337d8.jpg
             * prom_type : 0
             */

            private String goods_id;
            private String goods_name;
            private String store_cat_id1;
            private String shop_price;
            private String market_price;
            private String sales_sum;
            private String original_img;
            private String prom_type;

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

            public String getStore_cat_id1() {
                return store_cat_id1;
            }

            public void setStore_cat_id1(String store_cat_id1) {
                this.store_cat_id1 = store_cat_id1;
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

            public String getSales_sum() {
                return sales_sum;
            }

            public void setSales_sum(String sales_sum) {
                this.sales_sum = sales_sum;
            }

            public String getOriginal_img() {
                return original_img;
            }

            public void setOriginal_img(String original_img) {
                this.original_img = original_img;
            }

            public String getProm_type() {
                return prom_type;
            }

            public void setProm_type(String prom_type) {
                this.prom_type = prom_type;
            }
        }
    }
}
