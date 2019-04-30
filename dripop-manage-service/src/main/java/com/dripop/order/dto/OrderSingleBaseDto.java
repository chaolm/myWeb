package com.dripop.order.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.entity.TCard;
import com.dripop.util.PriceUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderSingleBaseDto implements Serializable {
 

    private Long orderId;

    /*订单编号*/
    private String orderNo;

    /*会员id*/
    private Long custId;

    /*订单的进行状态*/
    private Integer status;

    /*订单创建时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

     /*支付时间*/
     @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /*订单完成时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date completeTime;


    /*订单关闭时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

    /*商品总数量*/
    private Integer proNum;


    /*原价*/
    private Integer originalPrice;

    /*商品实付价*/
    private Integer realPay;

    /*优惠券抵扣价格*/
    private Integer couponPrice;



    /*赠送积分*/
    private Integer givePoints;

    /*送货方式*/
    private Integer shippingModel;



    /*支付方式*/
    private Integer payModel;

    /*付款人名字*/
    private String payerName;


    /*组织id*/
    private Long orgId;


    /*税号*/
    private String invoiceNumber;

    /*发票抬头*/
    private String invoiceName;

    /*发票内容*/
    private String invoiceContent;

    /*发票类型*/
    private Integer invoiceType;

    /*用户账号*/
    private String custPhoneNo;

    /*提货时间*/
    private String pickUpTime;

    /*支付状态*/
    private Integer payStatus;

    /**/
    private Integer deliveryNum;

    private Integer pdStatus;

    @JSONField(serialize=false)
    private Long expressAddress;

    /**
     * 2.1版本增加字段
     * @return
     */
    //定金支付
    private Integer deposit;
    private String depositStr;
    //尾款支付
    private Integer tailMoney;
    private String tailMoneyStr;
    //定金支付状态    1:完成   0:待支付
    private Integer depositPayStatus;
    //订单类型        1:普通订单 5:预售订单
    private Integer orderType;


    //定金付款信息
    private Integer depositPayModel;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date depositPayTime;
    //定金支付金额复用上面字段
    private String depositNo;
    //支付人
    private String depositPayerName;
    //尾款支付金额复用上面字段

    public String getDepositPayerName() {
        return depositPayerName;
    }

    public void setDepositPayerName(String depositPayerName) {
        this.depositPayerName = depositPayerName;
    }

    public Integer getDepositPayModel() {
        return depositPayModel;
    }

    public void setDepositPayModel(Integer depositPayModel) {
        this.depositPayModel = depositPayModel;
    }

    public Date getDepositPayTime() {
        return depositPayTime;
    }

    public void setDepositPayTime(Date depositPayTime) {
        this.depositPayTime = depositPayTime;
    }

    public String getDepositNo() {
        return depositNo;
    }

    public void setDepositNo(String depositNo) {
        this.depositNo = depositNo;
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

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public String getDepositStr() {
        return PriceUtil.getSimplePriceText(deposit);
    }

    public void setDepositStr(String depositStr) {
        this.depositStr = depositStr;
    }

    public Integer getTailMoney() {
        return tailMoney;
    }

    public void setTailMoney(Integer tailMoney) {
        this.tailMoney = tailMoney;
    }

    public String getTailMoneyStr() {
        if(realPay == null){
            tailMoneyStr = "待发布";
        }else{
            tailMoneyStr = PriceUtil.getSimplePriceText(realPay - (deposit == null ? 0:deposit));
        }
        return tailMoneyStr;
    }

    public void setTailMoneyStr(String tailMoneyStr) {
        this.tailMoneyStr = tailMoneyStr;
    }

    public Integer getPdStatus() {
        return pdStatus;
    }

    public void setPdStatus(Integer pdStatus) {
        this.pdStatus = pdStatus;
    }
    public Integer getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(Integer deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getCustPhoneNo() {
        return custPhoneNo;
    }

    public void setCustPhoneNo(String custPhoneNo) {
        this.custPhoneNo = custPhoneNo;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Integer getProNum() {
        return proNum;
    }

    public void setProNum(Integer proNum) {
        this.proNum = proNum;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getRealPay() {
        return realPay;
    }

    public void setRealPay(Integer realPay) {
        this.realPay = realPay;
    }

    public Integer getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Integer couponPrice) {
        this.couponPrice = couponPrice;
    }



    public Integer getGivePoints() {
        return givePoints;
    }

    public void setGivePoints(Integer givePoints) {
        this.givePoints = givePoints;
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

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public Long getExpressAddress() {
        return expressAddress;
    }

    public void setExpressAddress(Long expressAddress) {
        this.expressAddress = expressAddress;
    }
}
