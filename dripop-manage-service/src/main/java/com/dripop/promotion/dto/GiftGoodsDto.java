package com.dripop.promotion.dto;

import com.dripop.util.PriceUtil;

import java.io.Serializable;

public class GiftGoodsDto implements Serializable {

    private Long promotionId;

    private Integer type;

    private Long onlineId;

    private Integer salePrice;

    private String salePriceText;

    private String fullName;

    /*赠送出去的总数量*/
    private Integer stock;

    /*一次赠送的数量*/
    private Integer num;

    /*商品库存*/
    private Integer goodStock;

    private String picUrl;

    private String typeName;

    private String brandName;

    /*赠送总量*/
    private Integer totalNum;

    public Integer getTotalNum() {
        return totalNum;
    }

    private Integer alrNum;

    /*已经总送的数量*/
    public Integer getAlrNum() {
        if (totalNum != null && stock != null && totalNum > stock) {
            return totalNum - stock;
        }
        return 0;
    }

    public void setAlrNum(Integer alrNum) {
        this.alrNum = alrNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public String getSalePriceText() {
        return PriceUtil.getPriceText(salePrice);
    }

    public void setSalePriceText(String salePriceText) {
        this.salePriceText = salePriceText;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /*发布上线，出于情况，先 返回 用原 stock 赠送总量字段代替 totalNum */
    /*public Integer getStock() {
        return stock;
    }*/

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getGoodStock() {
        return goodStock;
    }

    public void setGoodStock(Integer goodStock) {
        this.goodStock = goodStock;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
