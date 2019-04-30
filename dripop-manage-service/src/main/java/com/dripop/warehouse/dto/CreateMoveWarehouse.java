package com.dripop.warehouse.dto;

import java.io.Serializable;

public class CreateMoveWarehouse implements Serializable{
    private long ycOrgId;

    private long yrOrgId;

    private String yrOrgIdName;

    public String getYrOrgIdName() {
        return yrOrgIdName;
    }

    public void setYrOrgIdName(String yrOrgIdName) {
        this.yrOrgIdName = yrOrgIdName;
    }

    private String imeis;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getYcOrgId() {
        return ycOrgId;
    }

    public void setYcOrgId(long ycOrgId) {
        this.ycOrgId = ycOrgId;
    }

    public long getYrOrgId() {
        return yrOrgId;
    }

    public void setYrOrgId(long yrOrgId) {
        this.yrOrgId = yrOrgId;
    }

    public String getImeis() {
        return imeis;
    }

    public void setImeis(String imeis) {
        this.imeis = imeis;
    }
}
