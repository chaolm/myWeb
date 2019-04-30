package com.dripop.order.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.util.PriceUtil;

import java.io.Serializable;
import java.util.Date;

public class ServerPageDto implements Serializable {

    private String serverNo;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

    private String goodsNames;

    private String imgUrls;

    private Integer cancelPrice;

    /*   private Integer cancelType;*/

    private String custPhoneNo;


    private Integer serviceType;

    private Integer opStatus;

    private Long cancelId;

    private Date endTime;

    /* private Long orderId;*/

    private Integer payModel;

    private Integer status;

    //订单类型 订单类型：1普通订单，2保险订单,3营商业务办理订单,4秒杀订单,5预售订单'
    private Integer orderType;

    /**
     * 2.1版本增加字段
     * @return
     */
    private String payModelStr;

    public String getPayModelStr() {
        return payModelStr;
    }

    public void setPayModelStr(String payModelStr) {
        this.payModelStr = payModelStr;
    }
    /*//定金
    private Integer deposit;
    private String depositStr;
    //尾款
    private Integer tailMoney;
    private String tailMoneyStr;
    //定金可抵用金额
    private Integer depositDiscountAmount;
    //预售价格
    private Integer presellMoney;



    public Integer getPresellMoney() {
        return presellMoney;
    }

    public void setPresellMoney(Integer presellMoney) {
        this.presellMoney = presellMoney;
    }

    public Integer getDepositDiscountAmount() {
        return depositDiscountAmount;
    }

    public void setDepositDiscountAmount(Integer depositDiscountAmount) {
        this.depositDiscountAmount = depositDiscountAmount;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public String getDepositStr() {
        if(deposit == null){
            depositStr = "无";
        }else {
            PriceUtil.getSimplePriceText(deposit);
        }
        return depositStr;
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
        if(presellMoney == null || presellMoney == 0){
            tailMoneyStr = "待发布";
        }else if(depositDiscountAmount != null){
            tailMoneyStr = PriceUtil.getSimplePriceText(presellMoney - depositDiscountAmount);
        }else {
            tailMoneyStr = PriceUtil.getSimplePriceText(presellMoney - deposit);
        }
        return tailMoneyStr;
    }

    public void setTailMoneyStr(String tailMoneyStr) {
        this.tailMoneyStr = tailMoneyStr;
    }*/

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private String priceText;

    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    public String getPriceText() {
        return PriceUtil.getPriceText(cancelPrice);
    }

    public void setPriceText(String priceText) {
        this.priceText = priceText;
    }

  /*  public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }*/

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

    public Integer getCancelPrice() {
        return cancelPrice;
    }

    public void setCancelPrice(Integer cancelPrice) {
        this.cancelPrice = cancelPrice;
    }

    /*public Integer getCancelType() {
        return cancelType;
    }

    public void setCancelType(Integer cancelType) {
        this.cancelType = cancelType;
    }*/

    public String getCustPhoneNo() {
        return custPhoneNo;
    }

    public void setCustPhoneNo(String custPhoneNo) {
        this.custPhoneNo = custPhoneNo;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
