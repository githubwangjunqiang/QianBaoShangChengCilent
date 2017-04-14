package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/14.
 */
public class StorInfoList {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"goods_list":[{"goods_id":"20","cat_id3":"63","goods_name":
     * "仅售98元，价值168元单人洗浴 雲鼎足道，节假日通用，免费WiFi！","
     * original_img":"/Public/upload/goods/2017/03-09/58c0f0be06638.jpg",
     * "shop_price":"168.00","comment_count":"0","sales_sum":"0","name":null,
     * "commentsum":null,"price":"98.00"}]}
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
        private List<GoodsListBean> goods_list;

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean {
            /**
             * goods_id : 20
             * cat_id3 : 63
             * goods_name : 仅售98元，价值168元单人洗浴 雲鼎足道，节假日通用，免费WiFi！
             * original_img : /Public/upload/goods/2017/03-09/58c0f0be06638.jpg
             * shop_price : 168.00
             * comment_count : 0
             * sales_sum : 0
             * name : null
             * commentsum : null
             * price : 98.00
             */

            private String goods_id;
            private String cat_id3;
            private String goods_name;
            private String original_img;
            private String shop_price;
            private String comment_count;
            private String sales_sum;
            private String name;
            private String commentsum;
            private String price;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getCat_id3() {
                return cat_id3;
            }

            public void setCat_id3(String cat_id3) {
                this.cat_id3 = cat_id3;
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

            public String getComment_count() {
                return comment_count;
            }

            public void setComment_count(String comment_count) {
                this.comment_count = comment_count;
            }

            public String getSales_sum() {
                return sales_sum;
            }

            public void setSales_sum(String sales_sum) {
                this.sales_sum = sales_sum;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCommentsum() {
                return commentsum;
            }

            public void setCommentsum(String commentsum) {
                this.commentsum = commentsum;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
