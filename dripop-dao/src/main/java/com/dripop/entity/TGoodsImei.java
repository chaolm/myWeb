package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2018/2/28.
 */
@Entity
@Table(name = "t_goods_imei")
public class TGoodsImei implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "goods_id")
    private Long goodsId;

    private String imei;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "purchase_price")
    private Integer purchasePrice;

    private String supplier;

    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    private Long creator;

    @Column(name = "logic_delete")
    private Integer logicDelete;

    public Integer getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }
}
