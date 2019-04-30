package com.dripop.promotion.dto;

import java.io.Serializable;

/**
 * Created by liyou on 2018/2/5.
 */
public class GiftDto implements Serializable {

    private String imgUrl;

    private String goodsName;

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
}
