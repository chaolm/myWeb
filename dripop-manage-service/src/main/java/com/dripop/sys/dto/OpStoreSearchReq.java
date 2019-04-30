package com.dripop.sys.dto;

import java.io.Serializable;

/**
 * Created by liyou on 2017/12/12.
 */
public class OpStoreSearchReq implements Serializable {

    private String province;

    private String city;

    private String county;

    private Integer status;

    private String name;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
