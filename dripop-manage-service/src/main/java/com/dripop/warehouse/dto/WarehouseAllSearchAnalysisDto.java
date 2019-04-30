package com.dripop.warehouse.dto;

import java.io.Serializable;

public class WarehouseAllSearchAnalysisDto implements Serializable {
    //商品类目
    private String typeName;
    //品牌
    private String brandName;
    //商品名称
    private String goodsName;

    //每个商品总库存数量
    private Integer allNum;
    //订单占用
    private Integer orderUsed;
    //每个商品在途数量

    private Integer saleAfterNum;
    //商品id
    private Long goodsId;
    //警报值
    private Integer warnNum;
    //卖出数量
    private  Integer saleNum;
    //可用库存
    private Integer canUsedNum;
    //根据报警值判断是否提示  1:提示 2 ：不提示
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    public Integer getOrderUsed() {
        return orderUsed;
    }

    public void setOrderUsed(Integer orderUsed) {
        this.orderUsed = orderUsed;
    }

    public Integer getSaleAfterNum() {
        return saleAfterNum;
    }

    public void setSaleAfterNum(Integer saleAfterNum) {
        this.saleAfterNum = saleAfterNum;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getWarnNum() {
        return warnNum;
    }

    public void setWarnNum(Integer warnNum) {
        this.warnNum = warnNum;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Integer getCanUsedNum() {
        return allNum  - saleAfterNum;
    }

    public void setCanUsedNum(Integer canUsedNum) {
        this.canUsedNum = canUsedNum;
    }
}
