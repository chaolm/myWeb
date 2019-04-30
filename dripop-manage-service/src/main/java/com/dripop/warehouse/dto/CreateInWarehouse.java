package com.dripop.warehouse.dto;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.List;

public class CreateInWarehouse implements Serializable{

    private  String supplier;
    private List<CreateInWarehouseDetailDto> goods;
    private Long yrOrgId;
    private String yrOrgIdName;

    public String getYrOrgIdName() {
        return yrOrgIdName;
    }

    public void setYrOrgIdName(String yrOrgIdName) {
        this.yrOrgIdName = yrOrgIdName;
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

    public List<CreateInWarehouseDetailDto> getGoods() {
        return goods;
    }

    public void setGoods(List<CreateInWarehouseDetailDto> goods) {
        this.goods = goods;
    }
}
