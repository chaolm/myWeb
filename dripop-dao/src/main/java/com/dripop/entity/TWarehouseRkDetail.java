package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2018/3/8.
 */
@Entity
@Table(name = "t_warehouse_rk_detail")
public class TWarehouseRkDetail implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "warehouse_rk_id")
    private Long warehouseRkId;

    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "purchase_price")
    private Integer purchasePrice;

    private String imeis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehouseRkId() {
        return warehouseRkId;
    }

    public void setWarehouseRkId(Long warehouseRkId) {
        this.warehouseRkId = warehouseRkId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getImeis() {
        return imeis;
    }

    public void setImeis(String imeis) {
        this.imeis = imeis;
    }
}
