package com.dripop.order.dto;

import java.io.Serializable;

/*物流信息*/
public class LogisticsDto implements Serializable {
    private String name;

    private String phone;

    private String expressNo;

    private String data;

    public Integer serverType;

    /*售后服务订单 物流信息类型： 1 寄货，2： 收货*/
    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
