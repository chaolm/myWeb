package com.dripop.order.dto;

import java.io.Serializable;

/**
 * 赠品
 *
 * @author dq
 * @date 2018/3/22 11:32
 */

public class GiftForImeiDto implements Serializable {
    /*赠品ID*/
    private Long giftId;
    /*1 线上商品 2 线下商品*/
    private Integer type;
    /*上架商品id、线下商品*/
    private String refVal;
    /*商品ID*/
    private Long goodsId;
    /*商品名*/
    private String goodsName;
    /*赠送数量*/
    private Integer num;
    /*库存*/
    private Integer stock;

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
