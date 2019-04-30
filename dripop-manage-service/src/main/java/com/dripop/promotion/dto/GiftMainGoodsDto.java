package com.dripop.promotion.dto;

import com.dripop.util.PriceUtil;

import java.io.Serializable;

public class GiftMainGoodsDto implements Serializable {
    private Long onlineId;

    private String fullName;

    private String brandName;

    private String picUrl;

    private String typeName;

    private Integer goodStock;

    private Integer salePrice;

    private String salePriceText;

    public String getSalePriceText() {
        return PriceUtil.getPriceText(salePrice);
    }

    public void setSalePriceText(String salePriceText) {
        this.salePriceText = salePriceText;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public Integer getGoodStock() {
        return goodStock;
    }

    public void setGoodStock(Integer goodStock) {
        this.goodStock = goodStock;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }
}
