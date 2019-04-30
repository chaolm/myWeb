package com.dripop.warehouse.dto;

import java.io.Serializable;
import java.util.List;

public class MoveWarehousePageDto  implements Serializable{

    private  Long ycOrgId;
    private  Long yrOrgId;
    private String goodsNames;

    private String imeis;
    private String imgUrls;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGoodsNames() {
        return goodsNames;
    }

    public void setGoodsNames(String goodsNames) {
        this.goodsNames = goodsNames;
    }

    public String getImeis() {
        return imeis;
    }

    public void setImeis(String imeis) {
        this.imeis = imeis;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public Long getYcOrgId() {
        return ycOrgId;
    }

    public void setYcOrgId(Long ycOrgId) {
        this.ycOrgId = ycOrgId;
    }

    public Long getYrOrgId() {
        return yrOrgId;
    }

    public void setYrOrgId(Long yrOrgId) {
        this.yrOrgId = yrOrgId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    private  String supplier;
}
