package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

/**
 * Created by ${王俊强} on 2017/3/1.
 */
public class DingDanOver {

    /**
     * retcode : 3000
     * msg : 计算成功！
     * data : {"postFee":0,"couponFee":10,"balance":0,"payables":118,"goodsFee":128,"order_prom_amount":0,"packetFee":"0","packet_id":0,"shoppingCard_id":0,"store_order_prom_id":0,"store_order_prom_amount":0,"store_order_amount":118,"shoppingCard_money":"0.00","store_shipping_price":null,"store_coupon_price":"10.00","store_point_count":null,"store_balance":0,"store_goods_price":128}
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
         * postFee : 0
         * couponFee : 10
         * balance : 0
         * payables : 118
         * goodsFee : 128
         * order_prom_amount : 0
         * packetFee : 0
         * packet_id : 0
         * shoppingCard_id : 0
         * store_order_prom_id : 0
         * store_order_prom_amount : 0
         * store_order_amount : 118
         * shoppingCard_money : 0.00
         * store_shipping_price : null
         * store_coupon_price : 10.00
         * store_point_count : null
         * store_balance : 0
         * store_goods_price : 128
         */

        private int postFee;
        private Object couponFee;
        private Object balance;
        private double payables;
        private Object goodsFee;
        private Object order_prom_amount;
        private String packetFee;
        private Object packet_id;
        private Object shoppingCard_id;
        private Object store_order_prom_id;
        private Object store_order_prom_amount;
        private Object store_order_amount;
        private String shoppingCard_money;
        private Object store_shipping_price;
        private String store_coupon_price;
        private Object store_point_count;
        private Object store_balance;
        private Object store_goods_price;

        public int getPostFee() {
            return postFee;
        }

        public void setPostFee(int postFee) {
            this.postFee = postFee;
        }

        public Object getCouponFee() {
            return couponFee;
        }

        public void setCouponFee(Object couponFee) {
            this.couponFee = couponFee;
        }

        public Object getBalance() {
            return balance;
        }

        public void setBalance(Object balance) {
            this.balance = balance;
        }

        public double getPayables() {
            return payables;
        }

        public void setPayables(double payables) {
            this.payables = payables;
        }

        public Object getGoodsFee() {
            return goodsFee;
        }

        public void setGoodsFee(Object goodsFee) {
            this.goodsFee = goodsFee;
        }

        public Object getOrder_prom_amount() {
            return order_prom_amount;
        }

        public void setOrder_prom_amount(Object order_prom_amount) {
            this.order_prom_amount = order_prom_amount;
        }

        public String getPacketFee() {
            return packetFee;
        }

        public void setPacketFee(String packetFee) {
            this.packetFee = packetFee;
        }

        public Object getPacket_id() {
            return packet_id;
        }

        public void setPacket_id(Object packet_id) {
            this.packet_id = packet_id;
        }

        public Object getShoppingCard_id() {
            return shoppingCard_id;
        }

        public void setShoppingCard_id(Object shoppingCard_id) {
            this.shoppingCard_id = shoppingCard_id;
        }

        public Object getStore_order_prom_id() {
            return store_order_prom_id;
        }

        public void setStore_order_prom_id(Object store_order_prom_id) {
            this.store_order_prom_id = store_order_prom_id;
        }

        public Object getStore_order_prom_amount() {
            return store_order_prom_amount;
        }

        public void setStore_order_prom_amount(Object store_order_prom_amount) {
            this.store_order_prom_amount = store_order_prom_amount;
        }

        public Object getStore_order_amount() {
            return store_order_amount;
        }

        public void setStore_order_amount(Object store_order_amount) {
            this.store_order_amount = store_order_amount;
        }

        public String getShoppingCard_money() {
            return shoppingCard_money;
        }

        public void setShoppingCard_money(String shoppingCard_money) {
            this.shoppingCard_money = shoppingCard_money;
        }

        public Object getStore_shipping_price() {
            return store_shipping_price;
        }

        public void setStore_shipping_price(Object store_shipping_price) {
            this.store_shipping_price = store_shipping_price;
        }

        public String getStore_coupon_price() {
            return store_coupon_price;
        }

        public void setStore_coupon_price(String store_coupon_price) {
            this.store_coupon_price = store_coupon_price;
        }

        public Object getStore_point_count() {
            return store_point_count;
        }

        public void setStore_point_count(Object store_point_count) {
            this.store_point_count = store_point_count;
        }

        public Object getStore_balance() {
            return store_balance;
        }

        public void setStore_balance(Object store_balance) {
            this.store_balance = store_balance;
        }

        public Object getStore_goods_price() {
            return store_goods_price;
        }

        public void setStore_goods_price(Object store_goods_price) {
            this.store_goods_price = store_goods_price;
        }
    }
}
