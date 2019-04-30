package com.dripop.goodspackage.dto;

import java.io.Serializable;

/**
 * Created by liyou on 2017/8/8.
 */
public class OpComboPackageGoodsReq implements Serializable {

    private Long typeId;

    private Long brandId;

    private String goodsName;

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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
