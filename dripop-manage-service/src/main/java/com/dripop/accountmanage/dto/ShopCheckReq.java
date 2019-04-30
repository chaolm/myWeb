package com.dripop.accountmanage.dto;

import java.io.Serializable;
import java.util.Date;

public class ShopCheckReq implements Serializable {
   //对账开始时间
    private Date startDate;
    //对账结束时间
    private Date endDate;
    //账单到明细所传peymodel
    private Integer allPayModel;
    //交易渠道类型
    private Integer paymodel;
    //如果为花呗
    private Integer divideNum;
    private Integer isSellerPoundage;
    //查询日和月类型
    private Integer queryStatus;

    //明细接受参数
    //商品名称
    private String shopName;
    //订单号
    private String orderNo;
    //门店id
    private Long shopId;
    //时间（查询那一天）
    private Date date;
    //交易类型
    //为1时为订单，其他为取消、退款或者退货
    private Integer status;
    //
    private Integer cancelStatus;
    //交易类型为取消时，在传状态
    private Integer  childStatus;
    //流水号
    private String payNo;

    public Integer getAllPayModel() {
        return allPayModel;
    }

    public void setAllPayModel(Integer allPayModel) {
        this.allPayModel = allPayModel;
    }

    public Integer getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(Integer cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public Integer getDivideNum() {
        return divideNum;
    }

    public void setDivideNum(Integer divideNum) {
        this.divideNum = divideNum;
    }

    public Integer getIsSellerPoundage() {
        return isSellerPoundage;
    }

    public void setIsSellerPoundage(Integer isSellerPoundage) {
        this.isSellerPoundage = isSellerPoundage;
    }

    public Integer getChildStatus() {
        return childStatus;
    }

    public void setChildStatus(Integer childStatus) {
        this.childStatus = childStatus;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPaymodel() {
        return paymodel;
    }

    public void setPaymodel(Integer paymodel) {
        this.paymodel = paymodel;
    }

    public Integer getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(Integer queryStatus) {
        this.queryStatus = queryStatus;
    }
}
