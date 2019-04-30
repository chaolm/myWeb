package com.dripop.accountmanage.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.util.PriceUtil;

import java.io.Serializable;
import java.util.Date;

public class GetshopInfoAfter implements Serializable {
    //创建订单时间
    @JSONField(format = "yyyy-MM-dd")
    private Date time;
    //创建订单时间
    @JSONField(format = "yyyy-MM")
    private Date timeMonth;
    @JSONField(serialize = false)
    private Date timee;
    @JSONField(serialize = false)
    private Date tootime;
    //本期结算金额
    private Long settlementMoney;
    private String settlementMoneyY;
    //交易金额
    private Long realPay;
    private String realPayY;
    //本期分期利息
    private Long poundageCost;
    private String poundageCostY;
    //交易渠道
    private Object payModel;
    //交易渠道名称
    private String payModelName;
    //卖出商品数
    private Integer deliveryNum;
    //退货商品数
    private Integer cancelNum;
    //操作状态
    private String statusName;

    public Date getTootime() {
        return tootime;
    }

    public void setTootime(Date tootime) {
        this.tootime = tootime;
    }

    public String getSettlementMoneyY() {
        return PriceUtil.getSimplePriceText(realPay - poundageCost);
    }

    public void setSettlementMoneyY(String settlementMoneyY) {
        this.settlementMoneyY = settlementMoneyY;
    }

    public String getRealPayY() {
        return PriceUtil.getSimplePriceText(realPay);
    }

    public void setRealPayY(String realPayY) {
        this.realPayY = realPayY;
    }

    public String getPoundageCostY() {
        return PriceUtil.getSimplePriceText(poundageCost);
    }

    public void setPoundageCostY(String poundageCostY) {
        this.poundageCostY = poundageCostY;
    }

    public Date getTimeMonth() {
        return timeMonth;
    }

    public void setTimeMonth(Date timeMonth) {
        this.timeMonth = timeMonth;
    }

    public String getPayModelName() {
        return payModelName;
    }

    public void setPayModelName(String payModelName) {
        this.payModelName = payModelName;
    }

    public Date getTimee() {
        return timee;
    }

    public void setTimee(Date timee) {
        this.timee = timee;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getSettlementMoney() {
        return settlementMoney;
    }

    public void setSettlementMoney(Long settlementMoney) {
        this.settlementMoney = settlementMoney;
    }

    public Long getRealPay() {

        if(realPay == null){
            realPay = 0L;
        }
        return realPay;
    }

    public void setRealPay(Long realPay) {
        this.realPay = realPay;
    }

    public Long getPoundageCost() {
        if(poundageCost == null){
            poundageCost = 0L;
        }
        return poundageCost;
    }

    public void setPoundageCost(Long poundageCost) {
        this.poundageCost = poundageCost;
    }

    public Object getPayModel() {
        return payModel;
    }

    public void setPayModel(Object payModel) {
        this.payModel = payModel;
    }

    public Integer getDeliveryNum() {

        if(deliveryNum == null){
            deliveryNum = 0;
        }return deliveryNum;
    }

    public void setDeliveryNum(Integer deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public Integer getCancelNum() {

        if(cancelNum == null){
            cancelNum = 0;
        }return cancelNum;
    }

    public void setCancelNum(Integer cancelNum) {
        this.cancelNum = cancelNum;
    }
}
