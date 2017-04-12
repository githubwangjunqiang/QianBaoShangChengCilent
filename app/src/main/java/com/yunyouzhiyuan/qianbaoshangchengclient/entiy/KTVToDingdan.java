package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.io.Serializable;

/**
 * Created by ${王俊强} on 2017/3/8.
 */

public class KTVToDingdan implements Serializable{
    private String storid;
    private String goodsis;
    private String number;
    private String goods_spec ;
    private String time ;

    public KTVToDingdan(String storid, String goodsis, String number, String goods_spec, String time) {
        this.storid = storid;
        this.goodsis = goodsis;
        this.number = number;
        this.goods_spec = goods_spec;
        this.time = time;
    }

    public KTVToDingdan() {

    }

    public String getStorid() {

        return storid;
    }

    public void setStorid(String storid) {
        this.storid = storid;
    }

    public String getGoodsis() {
        return goodsis;
    }

    public void setGoodsis(String goodsis) {
        this.goodsis = goodsis;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGoods_spec() {
        return goods_spec;
    }

    public void setGoods_spec(String goods_spec) {
        this.goods_spec = goods_spec;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
