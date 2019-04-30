package com.dripop.warehouse.dto.AllWarehouse;

import java.io.Serializable;

public class GetWarehouseAnalysisDto implements Serializable {
    //门店名称
    private String  name;
    //门店id
    private Long orgId;
    //每个商品总库存数量
    private Integer allNum;
    //订单占用
    private Integer orderUsed;
    //每个商品可用库存
    private Integer canUsedNum;
    //每个商品售后数量
    private Integer saleAfterNum;
    //卖出数量
    private  Long saleNum;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getOrderUsed() {
        return orderUsed;
    }

    public void setOrderUsed(Integer orderUsed) {
        this.orderUsed = orderUsed;
    }

    public Integer getCanUsedNum() {
        return allNum - saleAfterNum;
    }

    public void setCanUsedNum(Integer canUsedNum) {
        this.canUsedNum = canUsedNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    public Integer getSaleAfterNum() {
        return saleAfterNum;
    }

    public void setSaleAfterNum(Integer saleAfterNum) {
        this.saleAfterNum = saleAfterNum;
    }

    public Long getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Long saleNum) {
        this.saleNum = saleNum;
    }
}
