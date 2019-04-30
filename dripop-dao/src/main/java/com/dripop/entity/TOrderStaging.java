package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by liyou on 2018/3/29.
 */
@Entity
@Table(name = "t_order_staging")
public class TOrderStaging implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "real_pay")
    private Integer realPay;

    @Column(name = "staging_num")
    private Integer stagingNum;

    @Column(name = "is_seller_poundage")
    private Integer isSellerPoundage;

    @Column(name = "poundage_cost")
    private Integer poundageCost;

    @Column(name = "staging_totalcost")
    private Integer stagingTotalcost;

    @Column(name = "presell_order_id")
    private Long presellOrderId;

    @Column(name = "stag_type")
    private Integer stagType;

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

    public Integer getRealPay() {
        return realPay;
    }

    public void setRealPay(Integer realPay) {
        this.realPay = realPay;
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
        return poundageCost;
    }

    public void setPoundageCost(Integer poundageCost) {
        this.poundageCost = poundageCost;
    }

    public Integer getStagingTotalcost() {
        return stagingTotalcost;
    }

    public void setStagingTotalcost(Integer stagingTotalcost) {
        this.stagingTotalcost = stagingTotalcost;
    }

    public Long getPresellOrderId() {
        return presellOrderId;
    }

    public void setPresellOrderId(Long presellOrderId) {
        this.presellOrderId = presellOrderId;
    }

    public Integer getStagType() {
        return stagType;
    }

    public void setStagType(Integer stagType) {
        this.stagType = stagType;
    }
}
