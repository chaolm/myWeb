package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2018/3/8.
 */
@Entity
@Table(name = "t_warehouse_tk_detail")
public class TWarehouseTkDetail implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "warehouse_tk_id")
    private Long warehouseTkId;

    private Integer type;

    @Column(name = "old_imei")
    private String oldImei;

    @Column(name = "new_imei")
    private String newImei;

    @Column(name = "old_supplier")
    private String oldSupplier;

    @Column(name = "new_supplier")
    private String newSupplier;

    @Column(name = "old_purchase_price")
    private Integer oldPurchasePrice;

    @Column(name = "new_purchase_price")
    private Integer newPurchasePrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehouseTkId() {
        return warehouseTkId;
    }

    public void setWarehouseTkId(Long warehouseTkId) {
        this.warehouseTkId = warehouseTkId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOldImei() {
        return oldImei;
    }

    public void setOldImei(String oldImei) {
        this.oldImei = oldImei;
    }

    public String getNewImei() {
        return newImei;
    }

    public void setNewImei(String newImei) {
        this.newImei = newImei;
    }

    public String getOldSupplier() {
        return oldSupplier;
    }

    public void setOldSupplier(String oldSupplier) {
        this.oldSupplier = oldSupplier;
    }

    public String getNewSupplier() {
        return newSupplier;
    }

    public void setNewSupplier(String newSupplier) {
        this.newSupplier = newSupplier;
    }

    public Integer getOldPurchasePrice() {
        return oldPurchasePrice;
    }

    public void setOldPurchasePrice(Integer oldPurchasePrice) {
        this.oldPurchasePrice = oldPurchasePrice;
    }

    public Integer getNewPurchasePrice() {
        return newPurchasePrice;
    }

    public void setNewPurchasePrice(Integer newPurchasePrice) {
        this.newPurchasePrice = newPurchasePrice;
    }
}
