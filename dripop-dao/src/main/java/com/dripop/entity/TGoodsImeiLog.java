package com.dripop.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2018/3/8.
 */
@Entity
@Table(name = "t_goods_imei_log")
public class TGoodsImeiLog implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String imei;

    private Long creator;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "handle_type")
    private Integer handleType;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "yc_org_id")
    private  String  ycOrgId;

    @Column(name = "yr_org_id")
    private  String  yrOrgId;

    @Column(name = "purchase_price")
    private Integer purchasePrice;

    @Column(name = "sale_price")
    private Integer salePrice;

    private String supplier;

    @Column(name = "imei_id")
    private Long imeiId;

    public Long getImeiId() {
        return imeiId;
    }

    public void setImeiId(Long imeiId) {
        this.imeiId = imeiId;
    }

    public Integer getHandleType() {
        return handleType;
    }

    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getYcOrgId() {
        return ycOrgId;
    }

    public void setYcOrgId(String ycOrgId) {
        this.ycOrgId = ycOrgId;
    }

    public String getYrOrgId() {
        return yrOrgId;
    }

    public void setYrOrgId(String yrOrgId) {
        this.yrOrgId = yrOrgId;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
