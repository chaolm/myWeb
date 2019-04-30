package com.dripop.goodspackage.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.util.PriceUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyou on 2017/8/3.
 */
public class ComboPackageDto implements Serializable {

    /**
     * 组合套装id
     */
    private Long packageId;

    /**
     * 套装名称
     */
    private String packageName;

    /**
     * 是否有库存（1 有库存 2 无库存）
     */
    private Integer hasStock;

    /**
     * 套装价格
     */
    @JSONField(serialize = false)
    private Integer price;

    private String priceText;

    private List<ComboGoodsDto> goodsList;

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

    public List<ComboGoodsDto> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ComboGoodsDto> goodsList) {
        this.goodsList = goodsList;
    }

    public Integer getHasStock() {
        return hasStock;
    }

    public void setHasStock(Integer hasStock) {
        this.hasStock = hasStock;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPriceText() {
        return PriceUtil.getPriceText(price);
    }
}
