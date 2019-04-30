package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2018/3/8.
 */
@Entity
@Table(name = "t_warehouse_yk")
public class TWarehouseYk implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "yc_org_id")
    private Long ycOrgId;

    @Column(name = "yr_org_id")
    private Long yrOrgId;

    @Column(name = "status")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "img_urls")
    private String imgUrls;

    @Column(name = "goods_names")
    private String goodsNames;

    private String imeis;

    private Long creator;

    @Column(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getYcOrgId() {
        return ycOrgId;
    }

    public void setYcOrgId(Long ycOrgId) {
        this.ycOrgId = ycOrgId;
    }

    public Long getYrOrgId() {
        return yrOrgId;
    }

    public void setYrOrgId(Long yrOrgId) {
        this.yrOrgId = yrOrgId;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getGoodsNames() {
        return goodsNames;
    }

    public void setGoodsNames(String goodsNames) {
        this.goodsNames = goodsNames;
    }

    public String getImeis() {
        return imeis;
    }

    public void setImeis(String imeis) {
        this.imeis = imeis;
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
