package com.dripop.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@javax.persistence.Entity
@Table(name = "t_account")
public class TAccount implements Serializable {
    @Id
    @GeneratedValue
    protected Long id;
    //订单id
    @Column(name = "order_id")
    private Long orderId;
    //交易时间
    @Column(name = "trade_time")
    private Date tradeTime;
    //订单号
    @Column(name = "order_no")
    private String orderNo;
    //流水号
    @Column(name = "pay_no")
    private String payNo;
    //交易类型
    @Column(name = "trade_type")
    private String tradeType;
    //详细支付方式
    @Column(name = "trade_channel_text")
    private String tradeChannelText;
    //支付方式
    @Column(name = "trade_channel")
    private Integer tradeChannel;
    //交易金额
    @Column(name = "trade_money")
    private Long tradeMoney;
    //手续费
    @Column(name = "service_money")
    private Long serviceMoney;
    //分期利息
    @Column(name = "stage_money")
    private Long stageMoney;
    //结算金额
    @Column(name = "settlement_money")
    private Long settlementMoney;
    //门店id
    @Column(name = "org_id")
    private Long orgId;
    //门店名称
    @Column(name = "org_name")
    private String orgName;
    //发货时间
    @Column(name = "send_time")
    private Date sendTime;
    //对账流向
    @Column(name = "money_flow")
    private Integer moneyFlow;
    //支付状态
    @Column(name = "pay_status")
    private Integer payStatus;
    //分期数
    @Column(name = "staging_num")
    private Integer stagingNum;
    //分期数
    @Column(name = "is_seller_poundage")
    private Integer isSellerPoundage;
    //分期数
    @Column(name = "service_type")
    private Integer serviceType;

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getStagingNum() {
        return stagingNum;
    }

    public void setStagingNum(Integer stagingNum) {
        this.stagingNum = stagingNum;
    }

    public Integer getIsSellerPoundage() {
        return isSellerPoundage;
    }

    public void setIsSellerPoundage(Integer isSellerPoundage) {
        this.isSellerPoundage = isSellerPoundage;
    }

    public Integer getMoneyFlow() {
        return moneyFlow;
    }

    public void setMoneyFlow(Integer moneyFlow) {
        this.moneyFlow = moneyFlow;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Long getOrgId() {
        return orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeChannelText() {
        return tradeChannelText;
    }

    public void setTradeChannelText(String tradeChannelText) {
        this.tradeChannelText = tradeChannelText;
    }

    public Integer getTradeChannel() {
        return tradeChannel;
    }

    public void setTradeChannel(Integer tradeChannel) {
        this.tradeChannel = tradeChannel;
    }

    public Long getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(Long tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public Long getServiceMoney() {
        return serviceMoney;
    }

    public void setServiceMoney(Long serviceMoney) {
        this.serviceMoney = serviceMoney;
    }

    public Long getStageMoney() {
        return stageMoney;
    }

    public void setStageMoney(Long stageMoney) {
        this.stageMoney = stageMoney;
    }

    public Long getSettlementMoney() {
        return settlementMoney;
    }

    public void setSettlementMoney(Long settlementMoney) {
        this.settlementMoney = settlementMoney;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
