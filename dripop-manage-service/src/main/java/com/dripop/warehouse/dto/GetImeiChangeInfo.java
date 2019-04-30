package com.dripop.warehouse.dto;

import com.dripop.util.PriceUtil;

import java.io.Serializable;

public class GetImeiChangeInfo implements Serializable {
    private String oldImei;
    private String newImei;
    private String oldSupplier;
    private String newSupplier;
    private Integer oldPurchasePrice;
    private String oldPurchasePriceY;
    private Integer newPurchasePrice;
    private String newPurchasePriceY;
    private String fullName;
    private Integer type;

    public String getOldPurchasePriceY() {
        return PriceUtil.getSimplePriceText(oldPurchasePrice);
    }

    public void setOldPurchasePriceY(String oldPurchasePriceY) {
        this.oldPurchasePriceY = oldPurchasePriceY;
    }

    public String getNewPurchasePriceY() {
        return PriceUtil.getSimplePriceText(newPurchasePrice);
    }

    public void setNewPurchasePriceY(String newPurchasePriceY) {
        this.newPurchasePriceY = newPurchasePriceY;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOldImei() {
        return oldImei;
    }

    public void setOldImei(String oldImei) {
        this.oldImei = oldImei;
    }

    public String getNewImei() {
        return newImei;
    }

    public void setNewImei(String newImei) {
        this.newImei = newImei;
    }

    public String getOldSupplier() {
        return oldSupplier;
    }

    public void setOldSupplier(String oldSupplier) {
        this.oldSupplier = oldSupplier;
    }

    public String getNewSupplier() {
        return newSupplier;
    }

    public void setNewSupplier(String newSupplier) {
        this.newSupplier = newSupplier;
    }

    public Integer getOldPurchasePrice() {
        return oldPurchasePrice;
    }

    public void setOldPurchasePrice(Integer oldPurchasePrice) {
        this.oldPurchasePrice = oldPurchasePrice;
    }

    public Integer getNewPurchasePrice() {
        return newPurchasePrice;
    }

    public void setNewPurchasePrice(Integer newPurchasePrice) {
        this.newPurchasePrice = newPurchasePrice;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
