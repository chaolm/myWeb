package com.dripop.accountmanage.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.util.PriceUtil;

import java.io.Serializable;
import java.util.Date;

public class GetShopDetailAfter implements Serializable {
    //订单id
    private Long id;
    //
    private String orderNo;
    //创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //交易渠道
    private Integer payModel;
    private String payModelText;
    //交易金额
    private Integer realPay;
    private String realPayY;
    //分期利息
    private Integer poundageCost;
    private String poundageCostY;
    //结算金额
    private Integer settlementMoney;
    private String settlementMoneyY;
    //流水号
    private String payNo;
    //最终返回类型
    private String endStatus;
    //门店id
    private Long orgId;
    //门店名称
    private String name;
    //发货时间
    private Date sendTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    public String getPayModelText() {
        return payModelText;
    }

    public void setPayModelText(String payModelText) {
        this.payModelText = payModelText;
    }

    public Integer getRealPay() {
        return realPay;
    }

    public void setRealPay(Integer realPay) {
        this.realPay = realPay;
    }

    public String getRealPayY() {
        return PriceUtil.getSimplePriceText(realPay == null ? 0:realPay);    }

    public void setRealPayY(String realPayY) {
        this.realPayY = realPayY;
    }

    public Integer getPoundageCost() {
        return poundageCost;
    }

    public void setPoundageCost(Integer poundageCost) {
        this.poundageCost = poundageCost;
    }

    public String getPoundageCostY() {
        return PriceUtil.getSimplePriceText(poundageCost == null ? 0:poundageCost);
    }

    public void setPoundageCostY(String poundageCostY) {
        this.poundageCostY = poundageCostY;
    }

    public Integer getSettlementMoney() {
        return settlementMoney;
    }

    public void setSettlementMoney(Integer settlementMoney) {
        this.settlementMoney = settlementMoney;
    }

    public String getSettlementMoneyY() {
        return PriceUtil.getSimplePriceText((realPay == null ? 0:realPay) - (poundageCost == null ? 0:poundageCost));
    }

    public void setSettlementMoneyY(String settlementMoneyY) {
        this.settlementMoneyY = settlementMoneyY;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(String endStatus) {
        this.endStatus = endStatus;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
