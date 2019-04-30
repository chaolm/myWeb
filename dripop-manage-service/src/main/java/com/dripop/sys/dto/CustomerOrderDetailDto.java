package com.dripop.sys.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.PipedReader;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CustomerOrderDetailDto implements Serializable{

    private Long orderId;

    private  String orderNo;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer status;

    private Integer shippingModel;

    private Integer payModel;

    private String goodsNames;

    private  String imgUrls;

    private Integer realPay;

    private String custPhoneNo;

    /*收货人名字*/
    @JSONField(serialize =false)
    private String deliverName;

    /*收货人号码*/
    @JSONField(serialize =false)
    private String deliverPhoneNo;

    /*收货人地址*/
    @JSONField(serialize =false)
    private String deliverAddr;


    /*'是否需要发票 1 需要 2 不需要',*/
    @JSONField(serialize =false)
    private Integer needInvoice;

    /*'纳税人识别号',*/
    @JSONField(serialize =false)
    private String invoiceNumber;

    /*发票内容',*/
    @JSONField(serialize =false)
    private String invoiceContent;

    /*'发票类型 1：普通类型',*/
    @JSONField(serialize =false)
    private Integer invoiceType;
    //订单类型
    private Integer orderType;

    private Integer payStatus;
    private Integer depositPayStatus;
    private String statusStr;

    private String companyCode;

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getDepositPayStatus() {
        return depositPayStatus;
    }

    public void setDepositPayStatus(Integer depositPayStatus) {
        this.depositPayStatus = depositPayStatus;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(Integer needInvoice) {
        this.needInvoice = needInvoice;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    /*'发票抬头',*/
    @JSONField(serialize =false)
    private String invoiceName;

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

    public String getDeliverPhoneNo() {
        return deliverPhoneNo;
    }

    public void setDeliverPhoneNo(String deliverPhoneNo) {
        this.deliverPhoneNo = deliverPhoneNo;
    }

    public String getDeliverAddr() {
        return deliverAddr;
    }

    public void setDeliverAddr(String deliverAddr) {
        this.deliverAddr = deliverAddr;
    }

    public String getCustPhoneNo() {
        return custPhoneNo;
    }

    public void setCustPhoneNo(String custPhoneNo) {
        this.custPhoneNo = custPhoneNo;
    }

    public Integer getRealPay() {
        return realPay;
    }

    public void setRealPay(Integer realPay) {
        this.realPay = realPay;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShippingModel() {
        return shippingModel;
    }

    public void setShippingModel(Integer shippingModel) {
        this.shippingModel = shippingModel;
    }

    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    public String getGoodsNames() {
        return goodsNames;
    }

    public void setGoodsNames(String goodsNames) {
        this.goodsNames = goodsNames;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
}
