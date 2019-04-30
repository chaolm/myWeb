package com.dripop.order.dto;

import java.io.Serializable;
import java.util.Date;

public class ReceiptDto implements Serializable {

    /*门店名称*/
    private String orgName;

    /*门店电话*/
    private String orgPhoneNo;

    /*门店地址*/
    private String orgAddr;





   /*会员收货地址*/
    private String deliverAddr;

   /*收货人名*/
    private String deliverName;

    /*收货人手机号*/
    private String deliverPhoneNo;

    /*收货时间*/
    private String recieveTime;

    public String getRecieveTime() {
        return recieveTime;
    }

    public void setRecieveTime(String recieveTime) {
        this.recieveTime = recieveTime;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgPhoneNo() {
        return orgPhoneNo;
    }

    public void setOrgPhoneNo(String orgPhoneNo) {
        this.orgPhoneNo = orgPhoneNo;
    }

    public String getOrgAddr() {
        return orgAddr;
    }

    public void setOrgAddr(String orgAddr) {
        this.orgAddr = orgAddr;
    }


    public String getDeliverAddr() {
        return deliverAddr;
    }

    public void setDeliverAddr(String deliverAddr) {
        this.deliverAddr = deliverAddr;
    }

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
}
