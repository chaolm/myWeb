package com.dripop.bean;

/**
 * Created by junhu on 2017/10/10.
 */
public class PayBodyDto {

    private Long orderId;//订单ID
    private Integer payModel;
    private Integer fqNum;//分期期数
    private Integer fqSellerPercent;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    public Integer getFqNum() {
        return fqNum;
    }

    public void setFqNum(Integer fqNum) {
        this.fqNum = fqNum;
    }

    public Integer getFqSellerPercent() {
        return fqSellerPercent;
    }

    public void setFqSellerPercent(Integer fqSellerPercent) {
        this.fqSellerPercent = fqSellerPercent;
    }
}
