package com.dripop.goodspackage.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.util.PriceUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyou on 2017/8/4.
 */
public class OpComboPackageDto implements Serializable {

    private Long packageId;

    private String packageName;

    private Integer stock;

    @JSONField(serialize = false)
    private Integer price;

    private String priceText;

    @JSONField(serialize = false)
    private Integer discountMoney;

    private String discountMoneyText;

    private List<OpComboGoodsDto> goodsList;

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPriceText() {
        return PriceUtil.getSimplePriceText(price);
    }

    public void setPriceText(String priceText) {
        this.priceText = priceText;
    }

    public List<OpComboGoodsDto> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OpComboGoodsDto> goodsList) {
        this.goodsList = goodsList;
    }

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }

    public String getDiscountMoneyText() {
        return PriceUtil.getSimplePriceText(discountMoney);
    }

    public void setDiscountMoneyText(String discountMoneyText) {
        this.discountMoneyText = discountMoneyText;
    }
}
