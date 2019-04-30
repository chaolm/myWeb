package com.dripop.order.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 发货请求
 *
 * @author dq
 * @date 2018/3/24 10:08
 */

public class DeliverOperReq implements Serializable {
    /*订单ID*/
    private Long orderId;
    /*快递ID*/
    private Long expressId;
    /*快递单号*/
    private String expressNo;
    /*1 发货 2 核销*/
    private Integer type;
    /*订单详情信息*/
    private List<DeliveryGoodsDto> deliveryGoodsList;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getExpressId() {
        return expressId;
    }

    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public List<DeliveryGoodsDto> getDeliveryGoodsList() {
        return deliveryGoodsList;
    }

    public void setDeliveryGoodsList(List<DeliveryGoodsDto> deliveryGoodsList) {
        this.deliveryGoodsList = deliveryGoodsList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}