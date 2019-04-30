package com.dripop.goodspackage.dto;

import java.io.Serializable;

/**
 * Created by liyou on 2017/8/3.
 */
public class ComboGoodsFullDto implements Serializable {

    /**
     * 组合套装id
     */
    private Long packageId;

    /**
     * 套装名称
     */
    private String packageName;

    /**
     * 商品上架id
     */
    private Long onlineId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private String imgUrl;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 优惠金额
     */
    private Integer discountMoney;

    /**
     * 商品售价
     */
    private Integer salePrice;

    /**
     * 赠品信息
     */
    private String gift;

    /**
     * 是否为实物 1 实物 2 非实物
     */
    private Integer isPractice;

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public Integer getIsPractice() {
        return isPractice;
    }

    public void setIsPractice(Integer isPractice) {
        this.isPractice = isPractice;
    }
}
