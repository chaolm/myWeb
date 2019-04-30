package com.dripop.warehouse.dto;

import com.dripop.util.PriceUtil;

import java.io.Serializable;

public class ByImeiFindInfo implements Serializable {

    private  String imei;

    private  Integer purchasePrice;
    private  String purchasePriceY;
    private  String supplier;

    private String fullName;

    public String getPurchasePriceY() {
        return PriceUtil.getSimplePriceText(purchasePrice);
    }

    public void setPurchasePriceY(String purchasePriceY) {
        this.purchasePriceY = purchasePriceY;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
