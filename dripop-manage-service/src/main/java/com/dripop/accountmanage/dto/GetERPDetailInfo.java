package com.dripop.accountmanage.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.bean.PayModel;
import com.dripop.util.PriceUtil;

import java.io.Serializable;
import java.util.Date;

public class GetERPDetailInfo implements Serializable{
    //订单id
    private Long id;
    private String orderNo;
    //创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //商品名称
    private String fullName;
    //门店名称
    private String name;
    //支付类型
    private Integer payModel;

    private String payModelText;
    //交易金额
    private Integer realPay;
    private String realPayY;
    //分期利息
    private Integer poundageCost;
    private String poundageCostY;

    @JSONField(serialize = false)
    private Integer stagingNum;

    @JSONField(serialize = false)
    private Integer isSellerPoundage;
    //结算金额
    private Integer settlementMoney;
    private String settlementMoneyY;

    public String getRealPayY() {
        return PriceUtil.getSimplePriceText(realPay == null ? 0 : realPay);
    }

    public void setRealPayY(String realPayY) {
        this.realPayY = realPayY;
    }

    public String getPoundageCostY() {
        return PriceUtil.getSimplePriceText(poundageCost == null ? 0 : poundageCost);
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

    public String getOrderNo() {
        if(orderNo == null){
            orderNo = "无";
        }
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    public Integer getRealPay() {
        if(realPay == null){
            realPay = 0;
        }
        return realPay;
    }

    public void setRealPay(Integer realPay) {
        this.realPay = realPay;
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
        if(realPay == null){
            realPay = 0;
        }
        return realPay - poundageCost ;
    }

    public void setSettlementMoney(Integer settlementMoney) {
        this.settlementMoney = settlementMoney;
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
}
