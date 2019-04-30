package com.dripop.entity;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 访问记录表
 *
 * @author dq
 * @date 2018/3/17 10:46
 */
@Entity
@Table(name = "t_visit")
public class TVisit implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "c_ip")
    private String cIp;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "visit_type")
    private Integer visitType;

    @Column(name = "flatform_type")
    private Integer flatformType;

    @Column(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getcIp() {
        return cIp;
    }

    public void setcIp(String cIp) {
        this.cIp = cIp;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getVisitType() {
        return visitType;
    }

    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    public Integer getFlatformType() {
        return flatformType;
    }

    public void setFlatformType(Integer flatformType) {
        this.flatformType = flatformType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
