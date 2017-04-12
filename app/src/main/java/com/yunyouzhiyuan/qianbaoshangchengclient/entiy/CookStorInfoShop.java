package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/28.
 */

public class CookStorInfoShop {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"goods_id":"8","cat_id3":"41","goods_name":"自助晚餐套餐","original_img":"/Public/upload/goods/2017/02-20/58aaa207c543d.jpg","keywords":"周一至周日 | 有赠品","prom_type":"2","shop_price":"138.00","goods_content":"&lt;p&gt;商品详情&lt;/p&gt;","goods_remark":"购买须知","comment_count":"0","sales_sum":"0","intro":"营业时间：周一至周五午市：11:00-15:00\r\n                 周一至周五夜市：17:00-22:00\r\n                 周六至周日：11:00 - 22:00\r\n月消费人数：1400 \r\nWIFI","price":"128.00","commentsum":0}]
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
         * goods_id : 8
         * cat_id3 : 41
         * goods_name : 自助晚餐套餐
         * original_img : /Public/upload/goods/2017/02-20/58aaa207c543d.jpg
         * keywords : 周一至周日 | 有赠品
         * prom_type : 2
         * shop_price : 138.00
         * goods_content : &lt;p&gt;商品详情&lt;/p&gt;
         * goods_remark : 购买须知
         * comment_count : 0
         * sales_sum : 0
         * intro : 营业时间：周一至周五午市：11:00-15:00
         周一至周五夜市：17:00-22:00
         周六至周日：11:00 - 22:00
         月消费人数：1400
         WIFI
         * price : 128.00
         * commentsum : 0
         */

        private String goods_id;
        private String cat_id3;
        private String goods_name;
        private String original_img;
        private String keywords;
        private String prom_type;
        private String shop_price;
        private String goods_content;
        private String goods_remark;
        private String comment_count;
        private String sales_sum;
        private String intro;
        private String price;
        private float commentsum;

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

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getProm_type() {
            return prom_type;
        }

        public void setProm_type(String prom_type) {
            this.prom_type = prom_type;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getGoods_content() {
            return goods_content;
        }

        public void setGoods_content(String goods_content) {
            this.goods_content = goods_content;
        }

        public String getGoods_remark() {
            return goods_remark;
        }

        public void setGoods_remark(String goods_remark) {
            this.goods_remark = goods_remark;
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

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public float getCommentsum() {
            return commentsum;
        }

        public void setCommentsum(float commentsum) {
            this.commentsum = commentsum;
        }
    }
}
