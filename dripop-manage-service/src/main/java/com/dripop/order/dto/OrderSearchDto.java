package com.dripop.order.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单 条件查询 传参使用
 */
public class OrderSearchDto implements Serializable {

  private String orderNo;

  private Date startTime;

  private Date endTime;

  private String goodsNames;

  private Integer startMoney;

  private Integer endMoney;

  private Integer payModel;

  private Integer shippingModel;

  private String custPhoneNo;

  private Integer status;
 //订单类型 订单类型：1普通订单，2保险订单,3营商业务办理订单,4秒杀订单,5预售订单'
  private Integer orderType;

  private String companyCode;

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getGoodsNames() {
        return goodsNames;
    }

    public void setGoodsNames(String goodsNames) {
        this.goodsNames = goodsNames;
    }

    public Integer getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(Integer startMoney) {
        this.startMoney = startMoney;
    }

    public Integer getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(Integer endMoney) {
        this.endMoney = endMoney;
    }

    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    public Integer getShippingModel() {
        return shippingModel;
    }

    public void setShippingModel(Integer shippingModel) {
        this.shippingModel = shippingModel;
    }

    public String getCustPhoneNo() {
        return custPhoneNo;
    }

    public void setCustPhoneNo(String custPhoneNo) {
        this.custPhoneNo = custPhoneNo;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
}
