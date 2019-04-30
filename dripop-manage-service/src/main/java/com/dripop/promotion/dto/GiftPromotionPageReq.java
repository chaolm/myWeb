package com.dripop.promotion.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2018/2/5.
 */
public class GiftPromotionPageReq implements Serializable {

    private Date startTime;

    private Date endTime;

    private String name;

    private Integer status;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
