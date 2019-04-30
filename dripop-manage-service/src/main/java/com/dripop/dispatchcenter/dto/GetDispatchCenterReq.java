package com.dripop.dispatchcenter.dto;

import java.io.Serializable;
import java.util.Date;

public class GetDispatchCenterReq implements Serializable {
    //订单编号
    private String orderNo;
    //下单时间
    private Date startTime;
    //下单时间
    private Date endTime;
    //商品名称
    private String fullName;
    //订单金额
    private Integer realMinPay;
    //订单金额
    private Integer realMaxPay;
    //支付方式
    private Integer payModel;
    //门店id
    private Long orgId;
    //用户id
    private Long custId;
    //用户账号
    private String phoneNo;
    //派单类型 1 待派单 2 已派单（可改派）3 已派单（不可改派）
    private Integer status;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getRealMinPay() {
        return realMinPay;
    }

    public void setRealMinPay(Integer realMinPay) {
        this.realMinPay = realMinPay;
    }

    public Integer getRealMaxPay() {
        return realMaxPay;
    }

    public void setRealMaxPay(Integer realMaxPay) {
        this.realMaxPay = realMaxPay;
    }

    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }
}
