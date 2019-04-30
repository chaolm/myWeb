package com.dripop.order.dto;

import java.io.Serializable;

/**
 * 商品串号信息
 *
 * @author dq
 * @date 2018/3/24 10:12
 */

public class GoodsImeiDto implements Serializable {
    /*订单详情ID*/
    private Long orderDetailId;
    /*串号*/
    private String imei;
    /*商品上架ID*/
    private Long onlineId;
    /*赠品ID*/
    private Long giftId;
    /*商品名称*/
    private String goodsName;
    /*商品图片*/
    private String imgUrl;
    /*发货类型 1 已发货 2 无货 3 未发货 4 核销 5 未核销 6 未出库 7 已拒收 8 已出库*/
    private String deliveryType;
    /*发货类型 */
    private Integer type;
    /*商品类型 1 正常商品 2 赠品*/
    private Integer goodsType;


    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }
}
