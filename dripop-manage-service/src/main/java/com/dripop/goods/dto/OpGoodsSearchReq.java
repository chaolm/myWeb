package com.dripop.goods.dto;

import java.io.Serializable;

/**
 * Created by liyou on 2017/12/1.
 */
public class OpGoodsSearchReq implements Serializable {

    private Long typeId;

    private Long brandId;

    private Integer status;

    private String fullName;

    private Integer goodsSellType;
    //值为1时，筛选掉预后商品
    private Integer filterPresell;

    public Integer getFilterPresell() {
        return filterPresell;
    }

    public void setFilterPresell(Integer filterPresell) {
        this.filterPresell = filterPresell;
    }

    public Integer getGoodsSellType() {
        return goodsSellType;
    }

    public void setGoodsSellType(Integer goodsSellType) {
        this.goodsSellType = goodsSellType;
    }

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
}
