package com.dripop.statistics.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计请求参数封装类
 *
 * @author dq
 * @date 2018/3/19 18:34
 */

public class AnalyslsParamsDto implements Serializable {
    /*最近几天时间 数*/
    private Integer time;
    /*商品ID*/
    private Long goodsId;
    /*请求时间 类型 1 最近时间 2 自定义时间*/
    private Integer type;
    /*开始时间*/
    private Date startTime;
    /*结束时间*/
    private Date endTime;
    /*结束时间 字符串*/
    private String afterDate;
    /*开始时间 字符串*/
    private String beforeDate;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public String getAfterDate() {
        return afterDate;
    }

    public void setAfterDate(String afterDate) {
        this.afterDate = afterDate;
    }

    public String getBeforeDate() {
        return beforeDate;
    }

    public void setBeforeDate(String beforeDate) {
        this.beforeDate = beforeDate;
    }
}
