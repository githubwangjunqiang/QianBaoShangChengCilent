package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

/**
 * Created by ${王俊强} on 2017/3/15.
 */

public class PaymentRequest {
    private String channel;//支付方式
    private double order_amount;//价格
    private String order_sn;//订单号

    public PaymentRequest(String channel, double order_amount, String order_sn) {
        this.channel = channel;
        this.order_amount = order_amount;
        this.order_sn = order_sn;
    }
}
