package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.io.Serializable;

/**
 * Created by ${王俊强} on 2017/3/1.
 */

public class ToShopinfo implements Serializable {
    private String imageUrl;//图片地址
    private String shopNmae;//商品名称
    private String goodsId;//商品id
    private float shopPingFen;//商品分数
    private String shopPrice;//商品单价
    private String salesSum;//已售出数量
    private String storName;//所属店铺名称
    private String storType;//所属店铺类型 是否 五大独立模块
    private String storId;//所属店铺id
    private String lat;


    private String lng;

    public ToShopinfo() {
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public ToShopinfo(String imageUrl, String shopNmae, String goodsId,
                      float shopPingFen, String shopPrice,
                      String salesSum, String storName,
                      String storType, String storId, String lat, String lng
    ) {
        this.imageUrl = imageUrl;
        this.shopNmae = shopNmae;
        this.goodsId = goodsId;
        this.shopPingFen = shopPingFen;
        this.shopPrice = shopPrice;
        this.salesSum = salesSum;
        this.storName = storName;
        this.storType = storType;
        this.storId = storId;
        this.lat = lat;
        this.lng = lng;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShopNmae() {
        return shopNmae;
    }

    public void setShopNmae(String shopNmae) {
        this.shopNmae = shopNmae;
    }

    public float getShopPingFen() {
        return shopPingFen;
    }

    public void setShopPingFen(float shopPingFen) {
        this.shopPingFen = shopPingFen;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getSalesSum() {
        return salesSum;
    }

    public void setSalesSum(String salesSum) {
        this.salesSum = salesSum;
    }

    public String getStorName() {
        return storName;
    }

    public void setStorName(String storName) {
        this.storName = storName;
    }

    public String getStorType() {
        return storType;
    }

    public void setStorType(String storType) {
        this.storType = storType;
    }

    public String getStorId() {
        return storId;
    }

    public void setStorId(String storId) {
        this.storId = storId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

}
