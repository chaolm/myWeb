package com.dripop.alipay.dto;

import java.io.Serializable;

/**
 * Created by junhu on 2017/10/20.
 */
public class AlipayRefundDto implements Serializable {
    private String orderNo; //商户订单号
    private String refundAmount;//退款金额
    private Long outRequestNo;//退款请求号
    private String reason;//退款原因
    private String departmentId;//部门（门店）ID
    private Long applayUserId;//退款申请人
    private String appId;//appId
    private String payNo;//支付流水号

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Long getOutRequestNo() {
        return outRequestNo;
    }

    public void setOutRequestNo(Long outRequestNo) {
        this.outRequestNo = outRequestNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Long getApplayUserId() {
        return applayUserId;
    }

    public void setApplayUserId(Long applayUserId) {
        this.applayUserId = applayUserId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }
}
