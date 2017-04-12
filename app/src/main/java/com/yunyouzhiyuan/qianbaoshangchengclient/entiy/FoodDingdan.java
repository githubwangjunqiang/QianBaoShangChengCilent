package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.io.Serializable;

/**
 * Created by ${王俊强} on 2017/2/24.
 */
public class FoodDingdan implements Serializable{
    private String goods_id;
    private String goods_num;

    public FoodDingdan() {
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }
}
