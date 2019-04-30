package com.dripop.goodspackage.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by liyou on 2017/8/4.
 */
public class OpComboPackSettingDetailDto implements Serializable {

    private String onlineIds;

    private String packageName;

    private BigDecimal discountMoney;

    private Integer stock;

    public String getOnlineIds() {
        return onlineIds;
    }

    public void setOnlineIds(String onlineIds) {
        this.onlineIds = onlineIds;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
