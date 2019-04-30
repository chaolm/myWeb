package com.dripop.warehouse.dto;

import com.dripop.util.PriceUtil;

import java.io.Serializable;

public class GetInWarehouseGoodsInfo implements Serializable {

    private String goodsName;

    private  Integer purchasePrice;
    private  String purchasePriceY;
    private String imeis;

    public String getPurchasePriceY() {
        return PriceUtil.getSimplePriceText(purchasePrice);
    }

    public void setPurchasePriceY(String purchasePriceY) {
        this.purchasePriceY = purchasePriceY;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getImeis() {
        return imeis;
    }

    public void setImeis(String imeis) {
        this.imeis = imeis;
    }
}
