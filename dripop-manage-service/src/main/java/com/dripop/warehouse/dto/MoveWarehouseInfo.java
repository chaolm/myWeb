package com.dripop.warehouse.dto;

import com.dripop.util.PriceUtil;

import java.io.Serializable;

public class MoveWarehouseInfo implements Serializable {
    private String supplier;
    private Integer purchasePrice;
    private String purchasePriceY;
    private String fullName;
    private  String imei;
    private Integer status;
    //3:移入 4：移出      当上面字段为1时  判断此字段
    private Integer statusChild;

    public String getPurchasePriceY() {
        return PriceUtil.getSimplePriceText(purchasePrice);
    }

    public void setPurchasePriceY(String purchasePriceY) {
        this.purchasePriceY = purchasePriceY;
    }

    public Integer getStatusChild() {
        return statusChild;
    }

    public void setStatusChild(Integer statusChild) {
        this.statusChild = statusChild;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
