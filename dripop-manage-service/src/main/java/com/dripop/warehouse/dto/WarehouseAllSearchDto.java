package com.dripop.warehouse.dto;

import java.io.Serializable;

public class WarehouseAllSearchDto implements Serializable {
    //商品类目
    private String typeName;
    //品牌
    private String brandName;
    //商品名称
    private String goodsName;

    //每个商品总库存数量
    private Integer allNum;

    //每个商品在途数量
    private Integer wayNum;
    //每个商品在库数量
    private Integer inWarehouseNum;
    //每个商品售后数量
    private Integer saleAfterNum;
   //商品id
    private Long goodsId;
    //警报值
    private Long warnNum;
    //卖出数量
    private  Long saleNum;

    public Long getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Long saleNum) {
        this.saleNum = saleNum;
    }

    public Long getWarnNum() {
        return warnNum;
    }

    public void setWarnNum(Long warnNum) {
        this.warnNum = warnNum;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    public Integer getWayNum() {
        return wayNum;
    }

    public void setWayNum(Integer wayNum) {
        this.wayNum = wayNum;
    }

    public Integer getInWarehouseNum() {
        return inWarehouseNum;
    }

    public void setInWarehouseNum(Integer inWarehouseNum) {
        this.inWarehouseNum = inWarehouseNum;
    }

    public Integer getSaleAfterNum() {
        return saleAfterNum;
    }

    public void setSaleAfterNum(Integer saleAfterNum) {
        this.saleAfterNum = saleAfterNum;
    }
}
