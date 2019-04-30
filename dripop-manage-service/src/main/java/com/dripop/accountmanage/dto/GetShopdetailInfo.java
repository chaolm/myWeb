package com.dripop.accountmanage.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.bean.PayModel;
import com.bean.ServiceType;
import com.dripop.core.bean.DateEditor;
import com.dripop.util.PriceUtil;

import java.util.Date;

public class GetShopdetailInfo {
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
    //交易类型
    @JSONField(serialize = false)
    private Integer serviceType;
    //交易类型
    @JSONField(serialize = false)
    private Integer payStatus;
    //最终返回类型
    private String endStatus;

    @JSONField(serialize = false)
    private Integer stagingNum;

    @JSONField(serialize = false)
    private Integer isSellerPoundage;
    //门店id
    private Long orgId;
    //门店名称
    private String name;
    //发货时间
    private Date sendTime;

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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

    public String getOrderNo() {
        if(orderNo == null){
            orderNo = "无";
        }
        return orderNo;
    }

    public String getRealPayY() {
        return PriceUtil.getSimplePriceText(realPay == null ? 0:realPay);
    }

    public void setRealPayY(String realPayY) {
        this.realPayY = realPayY;
    }

    public String getPoundageCostY() {
        return PriceUtil.getSimplePriceText(poundageCost == null ? 0:poundageCost);
    }

    public void setPoundageCostY(String poundageCostY) {
        this.poundageCostY = poundageCostY;
    }

    public String getSettlementMoneyY() {
        return PriceUtil.getSimplePriceText((realPay == null ? 0:realPay) - (poundageCost == null ? 0:poundageCost));
    }

    public void setSettlementMoneyY(String settlementMoneyY) {
        this.settlementMoneyY = settlementMoneyY;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getEndStatus() {
        if(payStatus != null && payStatus == 1) {
            return "订单";
        }else {
            return ServiceType.getName(serviceType);
        }
    }

    public void setEndStatus(String endStatus) {
        this.endStatus = endStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getRealPay() {
        return realPay;
    }

    public void setRealPay(Integer realPay) {
        this.realPay = realPay;
    }

    public String getPayModelText() {

        return  PayModel.getName(payModel, stagingNum, isSellerPoundage);

    }

    public void setPayModelText(String payModelText) {
        this.payModelText = payModelText;
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

    public Integer getPoundageCost() {
        if(poundageCost == null){
            poundageCost = 0;
        }
        return poundageCost;
    }

    public void setPoundageCost(Integer poundageCost) {
        this.poundageCost = poundageCost;
    }

    public Integer getSettlementMoney() {
        if(poundageCost == null){
            poundageCost = 0;
        }
        return realPay - poundageCost;
    }

    public void setSettlementMoney(Integer settlementMoney) {
        this.settlementMoney = settlementMoney;
    }

    public String getPayNo() {

        if(payNo == null){
            payNo = "无";
        }
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }
}
