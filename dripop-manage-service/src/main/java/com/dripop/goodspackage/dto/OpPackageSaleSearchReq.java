package com.dripop.goodspackage.dto;

import java.io.Serializable;

/**
 * Created by liyou on 17-12-6.
 */
public class OpPackageSaleSearchReq implements Serializable {

    private Long typeId;

    private Long brandId;

    private String fullName;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
