package com.dripop.goodspackage.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.util.PriceUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyou on 2017/8/4.
 */
public class OpComboGoodsDto implements Serializable {

    private Long onlineId;

    private String typeName;

    private String brandName;

    private String fullName;

    @JSONField(serialize = false)
    private Integer price;

    private String priceText;

    private List<OpComboPackageDto> packageList;

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public List<OpComboPackageDto> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<OpComboPackageDto> packageList) {
        this.packageList = packageList;
    }
}
