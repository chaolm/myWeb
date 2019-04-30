package com.dripop.warehouse.dto.AllWarehouse;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.util.PriceUtil;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class GetImeiFollowInfo  implements Serializable {
    private Long id;
    private Long imeiId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String handleType;
    private String goodsName;
    private  String  ycOrgId;
    private  String  yrOrgId;
    private Integer purchasePrice;
    private String purchasePriceY;
    private String supplier;
    private Integer salePrice;
    private String salePriceY;
    private String  imei;

    public String getSalePriceY() {
        return PriceUtil.getSimplePriceText(salePrice);
    }

    public void setSalePriceY(String salePriceY) {
        this.salePriceY = salePriceY;
    }

    public String getPurchasePriceY() {
        return PriceUtil.getSimplePriceText(purchasePrice);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getYcOrgId() {
        return ycOrgId;
    }

    public void setYcOrgId(String ycOrgId) {
        this.ycOrgId = ycOrgId;
    }

    public String getYrOrgId() {
        return yrOrgId;
    }

    public void setYrOrgId(String yrOrgId) {
        this.yrOrgId = yrOrgId;
    }



    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Long getImeiId() {
        return imeiId;
    }

    public void setImeiId(Long imeiId) {
        this.imeiId = imeiId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getHandleType() {
        return handleType;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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
}
