package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_customer_vip")
public class TCustomerVip implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "customer_vip_name")
    private String customerVipName;

    @Column(name = "customer_vip_number")
    private String customerVipNumber;

    @Column(name = "customer_vip_phone")
    private String customerVipPhone;

    @Column(name = "receive_time")
    private Date receiveTime;

    @Column(name = "receive_platform")
    private Integer receivePlatform;

    @Column(name = "customer_vip_integration")
    private Integer customerVipIntegration;

    @Column(name = "alipay_vip_number")
    private String alipayVipNumber;

    @Column(name = "card_detail_url")
    private String cardDetailUrl;

    public Long getId() {
        return id;
    }

    public String getCardDetailUrl() {
        return cardDetailUrl;
    }

    public void setCardDetailUrl(String cardDetailUrl) {
        this.cardDetailUrl = cardDetailUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlipayVipNumber() {
        return alipayVipNumber;
    }

    public void setAlipayVipNumber(String alipayVipNumber) {
        this.alipayVipNumber = alipayVipNumber;
    }

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
