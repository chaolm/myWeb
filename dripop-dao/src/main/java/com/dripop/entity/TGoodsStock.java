package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by liyou on 2017/12/14.
 */
@javax.persistence.Entity
@Table(name = "t_goods_stock")
public class TGoodsStock implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "goods_id")
    private Long goodsId;

    @Transient
    private Long onlineId;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "erp_org_id")
    private String erpOrgId;

    private Integer stock;
    @Column(name = "order_stock")
    private Integer orderStock;

    public Integer getOrderStock() {
        return orderStock;
    }

    public void setOrderStock(Integer orderStock) {
        this.orderStock = orderStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getErpOrgId() {
        return erpOrgId;
    }

    public void setErpOrgId(String erpOrgId) {
        this.erpOrgId = erpOrgId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
