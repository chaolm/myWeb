package com.dripop.warehouse.dto.AllWarehouse;

import java.io.Serializable;

public class GetAjustWarehouse implements Serializable {
    private String imgUrls;
    private String goodsNames;

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getGoodsNames() {
        return goodsNames;
    }

    public void setGoodsNames(String goodsNames) {
        this.goodsNames = goodsNames;
    }
}
