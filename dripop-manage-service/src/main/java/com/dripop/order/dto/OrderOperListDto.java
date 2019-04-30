package com.dripop.order.dto;

import java.io.Serializable;
import java.util.List;

public class OrderOperListDto implements Serializable {

    private List<OrderOperDto> goodsOperList;

    private List<OrderOperDto> giftOperList;

    public List<OrderOperDto> getGoodsOperList() {
        return goodsOperList;
    }

    public void setGoodsOperList(List<OrderOperDto> goodsOperList) {
        this.goodsOperList = goodsOperList;
    }

    public List<OrderOperDto> getGiftOperList() {
        return giftOperList;
    }

    public void setGiftOperList(List<OrderOperDto> giftOperList) {
        this.giftOperList = giftOperList;
    }
}
