package com.dripop.warehouse.dto;

import java.io.Serializable;
import java.util.Date;

public class GetSingleWarehouseInfo implements Serializable{

    private Long goodId;
    private Long orgId;
    private Integer minPrice;
    private Integer maxPrice;
    private String supplier;
    private Integer status;
    private String imei;
    private Integer stockStatus;
    private Integer isInWarehouse;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Integer stockStatus) {
        this.stockStatus = stockStatus;
    }

    public Integer getIsInWarehouse() {
        return isInWarehouse;
    }

    public void setIsInWarehouse(Integer isInWarehouse) {
        this.isInWarehouse = isInWarehouse;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getGoodId() {
        return goodId;

    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }
}
