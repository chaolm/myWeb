package com.dripop.warehouse.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class GetAllMoveWarehouseInfo implements Serializable {
    //移库id
    private Long id;
    private  String imgUrls;
    private String goodsNames;
    private  Integer status;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //入库仓名称
    private String rkName;
    private  Long yrOrgId;
    //出库仓名称
    private  String ckName;
    private Long ycOrgId;
    private String imeis;

    public Long getYcOrgId() {
        return ycOrgId;
    }

    public void setYcOrgId(Long ycOrgId) {
        this.ycOrgId = ycOrgId;
    }

    public String getCkName() {
        return ckName;
    }

    public void setCkName(String ckName) {
        this.ckName = ckName;
    }

    public String getImeis() {
        return imeis;
    }

    public void setImeis(String imeis) {
        this.imeis = imeis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRkName() {
        return rkName;
    }

    public void setRkName(String rkName) {
        this.rkName = rkName;
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
}
