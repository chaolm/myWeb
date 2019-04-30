package com.dripop.goods.dto;

import com.bean.IsUsed;
import com.dripop.util.PriceUtil;

import java.io.Serializable;

public class OpGoodsSkuDto implements Serializable {

    private Long goodsId;

    private Long onlineId;

    private Integer status;

    private String statusText;

    private String fullName;

    private Long modelId;

    private Integer salePrice;

    private String model;

    private String salePriceText;

    private Integer stock;
    //商品销售类型 1是普通商品，2 定金预售 3 预约预售 4 全款预售'
    private Integer goodsSellType;
    private Integer presellMoney;
    private String presellMoneyStr;

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getPresellMoney() {
        return presellMoney;
    }

    public void setPresellMoney(Integer presellMoney) {
        this.presellMoney = presellMoney;
    }

    public String getPresellMoneyStr() {
        return PriceUtil.getSimplePriceText(presellMoney);
    }

    public void setPresellMoneyStr(String presellMoneyStr) {
        this.presellMoneyStr = presellMoneyStr;
    }

    public Integer getGoodsSellType() {
        return goodsSellType;
    }

    public void setGoodsSellType(Integer goodsSellType) {
        this.goodsSellType = goodsSellType;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
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

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public String getSalePriceText() {
        if(null != this.salePrice) {
            return String.format("%.2f", this.salePrice * 1.0 / 100);
        }
        return salePriceText;
    }

    public void setSalePriceText(String salePriceText) {
        this.salePriceText = salePriceText;
    }

    public String getStatusText() {
        if(status == null || status.equals(IsUsed.NOT_USED.getValue())) {
            return "未上架";
        }else {
            return "已上架";
        }
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

}
