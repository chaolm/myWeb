package com.dripop.order.dto;

import com.dripop.util.PriceUtil;

import java.io.Serializable;

/**
 * Created by liyou on 2018/3/14.
 */
public class OrderPackageDetailDto implements Serializable {

    private String imgUrl;

    private String goodsName;

    private Integer singleSalePrice;

    private Integer salePrice;

    public Integer getSingleSalePrice() {
        return singleSalePrice;
    }

    public void setSingleSalePrice(Integer singleSalePrice) {
        this.singleSalePrice = singleSalePrice;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }
}
