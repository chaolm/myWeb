package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_order")
public class TOrder implements Serializable {

    public interface NEED_INVOICE {
        public static final int YES = 1;//需要
        public static final int NO = 2;//不需要
    }

    /**
     * 订单是否已发货
     */
    public interface IS_DELIVER {
        public static final int YES = 1;//已发货
        public static final int NO = 0;//未发货
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "cust_id")
    private Long custId;

    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "pay_time")
    private Date payTime;

    @Column(name = "complete_time")
    private Date completeTime;

    private Date lastModifyTime;

    @Column(name = "close_time")
    private Date closeTime;

    @Column(name = "pro_num")
    private Integer proNum;

    @Column(name = "total_num")
    private Integer totalNum;

    @Column(name = "delivery_num")
    private Integer deliveryNum;

    @Column(name = "refuse_num")
    private Integer refuseNum;

    @Column(name = "de_refuse_num")
    private Integer deRefuseNum;

    @Column(name = "original_price")
    private Integer salePrice;

    @Column(name = "real_pay")
    private Integer realPay;

    @Column(name = "coupon_price")
    private Integer couponPrice;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "give_points")
    private Integer givePoints;

    @Column(name = "shipping_model")
    private Integer shippingModel;

    @Column(name = "express_type")
    private Integer expressType;

    @Column(name = "express_no")
    private String expressNo;

    @Column(name = "express_address")
    private Long expressAddress;

    @Column(name = "express_status")
    private Integer expressStatus;

    @Column(name = "pay_model")
    private Integer payModel;

    @Column(name = "pay_status")
    private Integer payStatus;

    @Column(name = "pay_no")
    private String payNo;

    @Column(name = "payer_name")
    private String payerName;

    @Column(name = "pickup_time")
    private String pickupTime;

//	@Column(name = "invoice_id")
//	private Long invoiceId;

    private String appid;


    @Column(name = "need_invoice")
    private Integer needInvoice;

    @Column(name = "invoice_company_name")
    private String companyName;

    @Column(name = "invoice_type")
    private Integer invoiceType;

    @Column(name = "invoice_content")
    private String invoiceContent;

    @Column(name = "invoice_name")
    private String invoiceName;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    private String remark;

    @Column(name = "order_channel")
    private Integer orderChannel;

    @Column(name = "take_goods_code")
    private String takeGoodsCode;

    @Column(name = "service_person_id")
    private Long servicePersonId;

    @Column(name = "send_time")
    private Date sendTime;


    @Column(name = "logic_delete")
    private Integer logicDelete;
    @Column(name = "presell_order_id")
    private Long presellOrderId;
    //订单类型
    @Column(name = "order_type")
    private Integer orderType;
    //预售支付
    @Column(name = "deposit_pay_time")
    private Date depositPayTime;
    @Column(name = "deposit_pay_model")
    private Integer depositPayModel;
    @Column(name = "deposit_payer_name")
    private String depositPayerName;
    @Column(name = "deposit_pay_status")
    private Integer depositPayStatus;
    @Column(name = "deposit_pay_no")
    private String depositPayNo;
    @Column(name = "deposit_no")
    private String depositNo;
    @Column(name = "deposit_pay")
    private Integer depositPay;
    @Column(name = "presell_pay_stage")
    private Integer presellPayStage;
    @Transient
    @Column(name = "balance_payment_end_time")
    private Date balancePaymentEndTime;
    @Transient
    @Column(name = "order_detail_id")
    private String orderDetailId;
    @Transient
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "is_rejected_deposit")
    private Integer isRejectedDeposit;

    @Column(name = "company_code")
    private String companyCode;

    @Transient
    @Column(name = "client_id")
    private String clientId;
    @Transient
    @Column(name = "goods_name")
    private String goodsName;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getIsRejectedDeposit() {
        return isRejectedDeposit;
    }

    public void setIsRejectedDeposit(Integer isRejectedDeposit) {
        this.isRejectedDeposit = isRejectedDeposit;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Date getBalancePaymentEndTime() {
        return balancePaymentEndTime;
    }

    public void setBalancePaymentEndTime(Date balancePaymentEndTime) {
        this.balancePaymentEndTime = balancePaymentEndTime;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getPresellOrderId() {
        return presellOrderId;
    }

    public void setPresellOrderId(Long presellOrderId) {
        this.presellOrderId = presellOrderId;
    }

    public String getTakeGoodsCode() {
        return takeGoodsCode;
    }

    public void setTakeGoodsCode(String takeGoodsCode) {
        this.takeGoodsCode = takeGoodsCode;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public Integer getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public Integer getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(Integer needInvoice) {
        this.needInvoice = needInvoice;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
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

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
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

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
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

    public Integer getExpressType() {
        return expressType;
    }

    public void setExpressType(Integer expressType) {
        this.expressType = expressType;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public Long getExpressAddress() {
        return expressAddress;
    }

    public void setExpressAddress(Long expressAddress) {
        this.expressAddress = expressAddress;
    }

    public Integer getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(Integer expressStatus) {
        this.expressStatus = expressStatus;
    }

    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }

    public Long getServicePersonId() {
        return servicePersonId;
    }

    public void setServicePersonId(Long servicePersonId) {
        this.servicePersonId = servicePersonId;
    }

    public Integer getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(Integer deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getRefuseNum() {
        return refuseNum;
    }

    public void setRefuseNum(Integer refuseNum) {
        this.refuseNum = refuseNum;
    }

    public Integer getDeRefuseNum() {
        return deRefuseNum;
    }

    public void setDeRefuseNum(Integer deRefuseNum) {
        this.deRefuseNum = deRefuseNum;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Date getDepositPayTime() {
        return depositPayTime;
    }

    public void setDepositPayTime(Date depositPayTime) {
        this.depositPayTime = depositPayTime;
    }

    public Integer getDepositPayModel() {
        return depositPayModel;
    }

    public void setDepositPayModel(Integer depositPayModel) {
        this.depositPayModel = depositPayModel;
    }

    public String getDepositPayerName() {
        return depositPayerName;
    }

    public void setDepositPayerName(String depositPayerName) {
        this.depositPayerName = depositPayerName;
    }

    public Integer getDepositPayStatus() {
        return depositPayStatus;
    }

    public void setDepositPayStatus(Integer depositPayStatus) {
        this.depositPayStatus = depositPayStatus;
    }

    public String getDepositPayNo() {
        return depositPayNo;
    }

    public void setDepositPayNo(String depositPayNo) {
        this.depositPayNo = depositPayNo;
    }

    public String getDepositNo() {
        return depositNo;
    }

    public void setDepositNo(String depositNo) {
        this.depositNo = depositNo;
    }

    public Integer getDepositPay() {
        return depositPay;
    }

    public void setDepositPay(Integer depositPay) {
        this.depositPay = depositPay;
    }

    public Integer getPresellPayStage() {
        return presellPayStage;
    }

    public void setPresellPayStage(Integer presellPayStage) {
        this.presellPayStage = presellPayStage;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
}