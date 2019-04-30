package com.dripop.order.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class ServerSingleBaseDto implements Serializable {


    /**
     * 退货原因框
     */
    /*服务单类型*/
    private Integer serviceType;

    /*退款全额*/
    private Integer totalRefund;

    /*退货联系方式*/
    private String contactPhone;

    /* 退货/ 换货原因*/
    private String concelReason;

    /*是否拆封*/
    private Integer isOpen;

    /*退货/退货 说明---------------*/
    private String cancelDesc;

    /*图片*/
    private String imgUrl;


    /**
     * 审核拒绝原因框
     */
    /*审核拒绝原因*/
    private String refuseReason;


    /**
     * 订单基本信息栏
     */

    /*服务单号*/
    private String serverNo;

    /*售后申请时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

    /*售后处理时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /*完成时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date opAuditTime;

    /*服务单类型*/
    private Integer opStatus;

    /*售后单id*/
    private Long cancelId;

    /*用户账号*/
    private String custPhoneNo;


    /**
     * 退款信息框
     */
    /*退款人*/
    private String refundName;

    /*退款方式*/
    private Integer refundType;

    /*退款时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date refundTime;

    /*退款金额*/
    private Integer refundPrice;


    /**
     * 退货 或 换货 至
     */
    /*换货人 地址*/
    private String deliverAddr;

    /*联系方式*/
    // private  String deliverPhoneNo;

    /*退货人名字*/
    // private String deliverName;

    /**
     * 服务门店
     */
    /*门店名称*/
    private String orgName;

    /*门店电话*/
    private String orgPhoneNO;

    /*售后订单中 订单id*/
    private Long orderId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date requestCancelTime;

    private Integer status;

    private String refundTypeStr;
    private Integer orderType;

    public String getRefundTypeStr() {
        return refundTypeStr;
    }

    public void setRefundTypeStr(String refundTypeStr) {
        this.refundTypeStr = refundTypeStr;
    }

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

    public Date getRequestCancelTime() {
        return requestCancelTime;
    }

    public void setRequestCancelTime(Date requestCancelTime) {
        this.requestCancelTime = requestCancelTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getTotalRefund() {
        return totalRefund;
    }

    public void setTotalRefund(Integer totalRefund) {
        this.totalRefund = totalRefund;
    }

    public Integer getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Integer refundPrice) {
        this.refundPrice = refundPrice;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getConcelReason() {
        return concelReason;
    }

    public void setConcelReason(String concelReason) {
        this.concelReason = concelReason;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getCancelDesc() {
        return cancelDesc;
    }

    public void setCancelDesc(String cancelDesc) {
        this.cancelDesc = cancelDesc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getServerNo() {
        return serverNo;
    }

    public void setServerNo(String serverNo) {
        this.serverNo = serverNo;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getOpAuditTime() {
        return opAuditTime;
    }

    public void setOpAuditTime(Date opAuditTime) {
        this.opAuditTime = opAuditTime;
    }

    public Integer getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(Integer opStatus) {
        this.opStatus = opStatus;
    }

    public Long getCancelId() {
        return cancelId;
    }

    public void setCancelId(Long cancelId) {
        this.cancelId = cancelId;
    }

    public String getCustPhoneNo() {
        return custPhoneNo;
    }

    public void setCustPhoneNo(String custPhoneNo) {
        this.custPhoneNo = custPhoneNo;
    }

    public String getRefundName() {
        return refundName;
    }

    public void setRefundName(String refundName) {
        this.refundName = refundName;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public String getDeliverAddr() {
        return deliverAddr;
    }

    public void setDeliverAddr(String deliverAddr) {
        this.deliverAddr = deliverAddr;
    }
/*

    public String getDeliverPhoneNo() {
        return deliverPhoneNo;
    }

    public void setDeliverPhoneNo(String deliverPhoneNo) {
        this.deliverPhoneNo = deliverPhoneNo;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }
*/

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgPhoneNO() {
        return orgPhoneNO;
    }

    public void setOrgPhoneNO(String orgPhoneNO) {
        this.orgPhoneNO = orgPhoneNO;
    }
}
