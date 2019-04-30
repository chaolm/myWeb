package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by liyou on 2017/8/3.
 */
@javax.persistence.Entity
@Table(name = "t_combo_package_rel")
public class TComboPackageRel implements Serializable {

    public interface TYPE_CONSTANT{
        int MAIN_GOODS = 1;//主商品
        int NOT_MAIN_GOODS = 2;//附属商品
    }

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 组合套装id
     */
    @Column(name = "package_id")
    private Long packageId;

    /**
     * 商品上架id
     */
    @Column(name = "online_id")
    private Long onlineId;

    /**
     * 类型（1 主商品 2 附属商品）
     */
    private Integer type;

    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
