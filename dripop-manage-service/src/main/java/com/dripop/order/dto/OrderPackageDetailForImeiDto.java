package com.dripop.order.dto;

import com.dripop.core.util.StringUtil;
import com.dripop.util.PriceUtil;

import java.io.Serializable;

/**
 * 套餐详情
 *
 * @author dq
 * @date 2018/3/22 19:55
 */

public class OrderPackageDetailForImeiDto implements Serializable {
    /*商品图片*/
    private String imgUrl;
    /*商品名*/
    private String goodsName;
    /*商品上架ID*/
    private Long onlineId;
    /*赠品ID*/
    private Long giftId;
    /*商品售价*/
    private String salePrice;

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

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public String getSalePrice() {
        return PriceUtil.getPriceText(StringUtil.isBlank(salePrice) ? 0 : Integer.parseInt(salePrice));
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }
}
