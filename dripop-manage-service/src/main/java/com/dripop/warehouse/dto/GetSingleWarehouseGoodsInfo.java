package com.dripop.warehouse.dto;

import com.dripop.util.PriceUtil;

import java.io.Serializable;

public class GetSingleWarehouseGoodsInfo implements Serializable {
    private  String imei;
    private  Integer purchasePrice;
    private  String  purchasePriceY;
    private  String supplier;
    private  Integer status;

    //库存状态
    private String stockStatus;
    //是否状态
    private String isInWarehouse;
    public String getStockStatus() {
        if(status == 4){
            stockStatus = "售后";
        }else {
            stockStatus = "正常";
        }
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getIsInWarehouse() {
        if(status == 1){
            isInWarehouse = "在途";
        }
        if(status == 2){
            isInWarehouse = "在库";
        }
        return isInWarehouse;
    }
    public String getPurchasePriceY() {
        return PriceUtil.getPriceText(purchasePrice);
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
