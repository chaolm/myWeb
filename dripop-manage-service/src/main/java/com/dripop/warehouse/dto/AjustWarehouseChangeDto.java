package com.dripop.warehouse.dto;

import java.io.Serializable;

public class AjustWarehouseChangeDto implements Serializable {

    /**
     * 旧串号
     */
    private String oldImei;
    private String newImei;

    private String newSupplier;

    private Double newPurchasePrice;

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

    public String getNewSupplier() {
        return newSupplier;
    }

    public void setNewSupplier(String newSupplier) {
        this.newSupplier = newSupplier;
    }

    public Double getNewPurchasePrice() {
        return newPurchasePrice;
    }

    public void setNewPurchasePrice(Double newPurchasePrice) {
        this.newPurchasePrice = newPurchasePrice;
    }
}
