package com.dripop.vipcard.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class VipCardDto implements Serializable{

    private String customerVipName;

    private String customerVipNumber;

    private String customerVipPhone;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    private Integer receivePlatform;

    private Integer customerVipIntegration;

    public String getCustomerVipName() {
        return customerVipName;
    }

    public void setCustomerVipName(String customerVipName) {
        this.customerVipName = customerVipName;
    }

    public String getCustomerVipNumber() {
        return customerVipNumber;
    }

    public void setCustomerVipNumber(String customerVipNumber) {
        this.customerVipNumber = customerVipNumber;
    }

    public String getCustomerVipPhone() {
        return customerVipPhone;
    }

    public void setCustomerVipPhone(String customerVipPhone) {
        this.customerVipPhone = customerVipPhone;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Integer getReceivePlatform() {
        return receivePlatform;
    }

    public void setReceivePlatform(Integer receivePlatform) {
        this.receivePlatform = receivePlatform;
    }

    public Integer getCustomerVipIntegration() {
        return customerVipIntegration;
    }

    public void setCustomerVipIntegration(Integer customerVipIntegration) {
        this.customerVipIntegration = customerVipIntegration;
    }
}
