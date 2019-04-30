package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2017/8/3.
 */
@javax.persistence.Entity
@Table(name = "t_combo_package")
public class TComboPackage implements Serializable {

    public interface STOCK_CONSTANT{
        int HAS_STOCK = 1;//有库存
        int NO_STOCK = 2;//无库存
    }

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 套装名称
     */
    private String name;

    /**
     * 主商品上架id
     */
    @Column(name = "online_id")
    private Long onlineId;

    /**
     * 优惠金额
     */
    @Column(name = "discount_money")
    private Integer discountMoney;

    /**
     * 总库存
     */
    private Integer stock;

    /**
     * 剩余库存
     */
    @Column(name = "remaining_stock")
    private Integer remainingStock;

    private Integer sort;

    @Column(name = "create_time")
    private Date createTime;

    private Long creator;

    @Column(name = "update_time")
    private Date updateTime;

    private Long updater;

    @Column(name = "is_used")
    private Integer isUsed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getRemainingStock() {
        return remainingStock;
    }

    public void setRemainingStock(Integer remainingStock) {
        this.remainingStock = remainingStock;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdater() {
        return updater;
    }

    public void setUpdater(Long updater) {
        this.updater = updater;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }
}
