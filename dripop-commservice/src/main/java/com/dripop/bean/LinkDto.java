package com.dripop.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.util.LinkUtil;
import com.dripop.util.PriceUtil;

import java.io.Serializable;

/**
 * Created by liyou on 2018/1/15.
 */
public class LinkDto implements Serializable {

    @JSONField(serialize = false)
    private Integer type;

    @JSONField(serialize = false)
    private String refVal;

    private String link;

    private String name;

    private String val;

    private Integer sort;

    private Integer salePrice;

    private String salePriceStr;

    private Integer goodsSellType;

    private Integer presellMoney;

    private Integer deposit;

    private String goodsName;
    /**
     * 标签 暂存优惠信息
     */
    private String label;
    /*领券类型*/
    private Integer cardType;
    private Integer minUsePrice;
    private String minUsePriceStr;
    private String remark;

    private String goodsImage;

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getSalePriceStr() {
        return PriceUtil.getPriceText(this.salePrice);
    }

    public void setSalePriceStr(String salePriceStr) {
        this.salePriceStr = salePriceStr;
    }

    public Integer getGoodsSellType() {
        return goodsSellType;
    }

    public void setGoodsSellType(Integer goodsSellType) {
        this.goodsSellType = goodsSellType;
    }

    public Integer getPresellMoney() {
        return presellMoney;
    }

    public void setPresellMoney(Integer presellMoney) {
        this.presellMoney = presellMoney;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Integer getMinUsePrice() {
        return minUsePrice;
    }

    public void setMinUsePrice(Integer minUsePrice) {
        this.minUsePrice = minUsePrice;
    }

    public String getMinUsePriceStr() {
        return minUsePriceStr = PriceUtil.getSimplePriceText(minUsePrice);
    }

    public void setMinUsePriceStr(String minUsePriceStr) {
        this.minUsePriceStr = minUsePriceStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRefVal() {
        return refVal;
    }

    public void setRefVal(String refVal) {
        this.refVal = refVal;
    }

    public String getLink() {
        return LinkUtil.getAppLinkUrl(type, refVal);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }
}
