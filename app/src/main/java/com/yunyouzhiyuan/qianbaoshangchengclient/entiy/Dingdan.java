package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/7.
 */
public class Dingdan implements Serializable {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"order_list":[{"order_id":"340","order_sn":"201702101523101210","order_status":"0","pay_status":"1","shipping_status":"1","store_id":"1","is_comment":"0","goods_list":[{"rec_id":"435","order_id":"340","goods_id":"45","goods_name":"华为 HUAWEI C199S 麦芒3S 电信4G智能手机FDD-LTE /TD-LTE/CDMA2000/GSM（麦芒金）","goods_sn":"TP0000045","goods_num":"1","market_price":"2099.00","goods_price":"1999.00","cost_price":"0.00","member_goods_price":"1999.00","give_integral":"0","spec_key":"11_13_21","spec_key_name":"网络:4G 内存:16G 屏幕:触屏","bar_code":"","is_comment":"0","prom_type":"0","prom_id":"0","is_send":"0","delivery_id":"0","sku":"","store_id":"1","commission":"0","is_checkout":"0","deleted":"0","distribut":"0.00","original_img":"/Public/upload/goods/2016/01-13/5695e913ed253.jpg"}],"store_name":"TPSHP旗舰店"}]}
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

    public class DataBean {
        private List<OrderListBean> order_list;

        public List<OrderListBean> getOrder_list() {
            return order_list;
        }

        public void setOrder_list(List<OrderListBean> order_list) {
            this.order_list = order_list;
        }

        public class OrderListBean {
            /**
             * order_id : 340
             * order_sn : 201702101523101210
             * order_status : 0
             * pay_status : 1
             * shipping_status : 1
             * store_id : 1
             * is_comment : 0
             * goods_list : [{"rec_id":"435","order_id":"340","goods_id":"45","goods_name":"华为 HUAWEI C199S 麦芒3S 电信4G智能手机FDD-LTE /TD-LTE/CDMA2000/GSM（麦芒金）","goods_sn":"TP0000045","goods_num":"1","market_price":"2099.00","goods_price":"1999.00","cost_price":"0.00","member_goods_price":"1999.00","give_integral":"0","spec_key":"11_13_21","spec_key_name":"网络:4G 内存:16G 屏幕:触屏","bar_code":"","is_comment":"0","prom_type":"0","prom_id":"0","is_send":"0","delivery_id":"0","sku":"","store_id":"1","commission":"0","is_checkout":"0","deleted":"0","distribut":"0.00","original_img":"/Public/upload/goods/2016/01-13/5695e913ed253.jpg"}]
             * store_name : TPSHP旗舰店
             * private String order_amount;
             */

            private String order_id;
            private String add_time;
            private int refund;

            public int getRefund() {
                return refund;
            }

            public void setRefund(int refund) {
                this.refund = refund;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            private String order_sn;
            private String order_status;
            private String pay_status;
            private String shipping_status;
            private String store_id;
            private String is_comment;
            private String store_name;
            private String order_amount;

            public String getOrder_amount() {
                return order_amount;
            }

            public void setOrder_amount(String order_amount) {
                this.order_amount = order_amount;
            }

            private List<GoodsListBean> goods_list;

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            public String getPay_status() {
                return pay_status;
            }

            public void setPay_status(String pay_status) {
                this.pay_status = pay_status;
            }

            public String getShipping_status() {
                return shipping_status;
            }

            public void setShipping_status(String shipping_status) {
                this.shipping_status = shipping_status;
            }

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public String getIs_comment() {
                return is_comment;
            }

            public void setIs_comment(String is_comment) {
                this.is_comment = is_comment;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public List<GoodsListBean> getGoods_list() {
                return goods_list;
            }

            public void setGoods_list(List<GoodsListBean> goods_list) {
                this.goods_list = goods_list;
            }

            public class GoodsListBean {
                /**
                 * rec_id : 435
                 * order_id : 340
                 * goods_id : 45
                 * goods_name : 华为 HUAWEI C199S 麦芒3S 电信4G智能手机FDD-LTE /TD-LTE/CDMA2000/GSM（麦芒金）
                 * goods_sn : TP0000045
                 * goods_num : 1
                 * market_price : 2099.00
                 * goods_price : 1999.00
                 * cost_price : 0.00
                 * member_goods_price : 1999.00
                 * give_integral : 0
                 * spec_key : 11_13_21
                 * spec_key_name : 网络:4G 内存:16G 屏幕:触屏
                 * bar_code :
                 * is_comment : 0
                 * prom_type : 0
                 * prom_id : 0
                 * is_send : 0
                 * delivery_id : 0
                 * sku :
                 * store_id : 1
                 * commission : 0
                 * is_checkout : 0
                 * deleted : 0
                 * distribut : 0.00
                 * original_img : /Public/upload/goods/2016/01-13/5695e913ed253.jpg
                 */

                private String rec_id;
                private String order_id;
                private String goods_id;
                private String goods_name;
                private String goods_sn;
                private String goods_num;
                private String market_price;
                private String goods_price;
                private String cost_price;
                private String member_goods_price;
                private String give_integral;
                private String spec_key;
                private String spec_key_name;
                private String bar_code;
                private String is_comment;
                private String prom_type;
                private String prom_id;
                private String is_send;
                private String delivery_id;
                private String sku;
                private String store_id;
                private String commission;
                private String is_checkout;
                private String deleted;
                private String distribut;
                private String original_img;

                public String getRec_id() {
                    return rec_id;
                }

                public void setRec_id(String rec_id) {
                    this.rec_id = rec_id;
                }

                public String getOrder_id() {
                    return order_id;
                }

                public void setOrder_id(String order_id) {
                    this.order_id = order_id;
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

                public String getGoods_sn() {
                    return goods_sn;
                }

                public void setGoods_sn(String goods_sn) {
                    this.goods_sn = goods_sn;
                }

                public String getGoods_num() {
                    return goods_num;
                }

                public void setGoods_num(String goods_num) {
                    this.goods_num = goods_num;
                }

                public String getMarket_price() {
                    return market_price;
                }

                public void setMarket_price(String market_price) {
                    this.market_price = market_price;
                }

                public String getGoods_price() {
                    return goods_price;
                }

                public void setGoods_price(String goods_price) {
                    this.goods_price = goods_price;
                }

                public String getCost_price() {
                    return cost_price;
                }

                public void setCost_price(String cost_price) {
                    this.cost_price = cost_price;
                }

                public String getMember_goods_price() {
                    return member_goods_price;
                }

                public void setMember_goods_price(String member_goods_price) {
                    this.member_goods_price = member_goods_price;
                }

                public String getGive_integral() {
                    return give_integral;
                }

                public void setGive_integral(String give_integral) {
                    this.give_integral = give_integral;
                }

                public String getSpec_key() {
                    return spec_key;
                }

                public void setSpec_key(String spec_key) {
                    this.spec_key = spec_key;
                }

                public String getSpec_key_name() {
                    return spec_key_name;
                }

                public void setSpec_key_name(String spec_key_name) {
                    this.spec_key_name = spec_key_name;
                }

                public String getBar_code() {
                    return bar_code;
                }

                public void setBar_code(String bar_code) {
                    this.bar_code = bar_code;
                }

                public String getIs_comment() {
                    return is_comment;
                }

                public void setIs_comment(String is_comment) {
                    this.is_comment = is_comment;
                }

                public String getProm_type() {
                    return prom_type;
                }

                public void setProm_type(String prom_type) {
                    this.prom_type = prom_type;
                }

                public String getProm_id() {
                    return prom_id;
                }

                public void setProm_id(String prom_id) {
                    this.prom_id = prom_id;
                }

                public String getIs_send() {
                    return is_send;
                }

                public void setIs_send(String is_send) {
                    this.is_send = is_send;
                }

                public String getDelivery_id() {
                    return delivery_id;
                }

                public void setDelivery_id(String delivery_id) {
                    this.delivery_id = delivery_id;
                }

                public String getSku() {
                    return sku;
                }

                public void setSku(String sku) {
                    this.sku = sku;
                }

                public String getStore_id() {
                    return store_id;
                }

                public void setStore_id(String store_id) {
                    this.store_id = store_id;
                }

                public String getCommission() {
                    return commission;
                }

                public void setCommission(String commission) {
                    this.commission = commission;
                }

                public String getIs_checkout() {
                    return is_checkout;
                }

                public void setIs_checkout(String is_checkout) {
                    this.is_checkout = is_checkout;
                }

                public String getDeleted() {
                    return deleted;
                }

                public void setDeleted(String deleted) {
                    this.deleted = deleted;
                }

                public String getDistribut() {
                    return distribut;
                }

                public void setDistribut(String distribut) {
                    this.distribut = distribut;
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
}
