package com.dripop.statistics.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单分析实体类
 *
 * @author dq
 * @date 2018/3/16 10:39
 */

public class OrderAnalyslsDto implements Serializable {
    /*待支付数*/
    private Integer unpaid;
    /*待收货数*/
    private Integer receiving;
    /*已完成订单数*/
    private Integer success;
    /*已取消订单数*/
    private Integer cancel;
    /*退款服务单数*/
    private Integer refund;
    /*退货服务单数*/
    private Integer returnNum;
    /*换货服务单数*/
    private Integer exchange;
    /*时间*/
    private String time;
    /*近几天统计集合*/
    private List<OrderAnalyslsDto> list;

    public Integer getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(Integer unpaid) {
        this.unpaid = unpaid;
    }

    public Integer getReceiving() {
        return receiving;
    }

    public void setReceiving(Integer receiving) {
        this.receiving = receiving;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getCancel() {
        return cancel;
    }

    public void setCancel(Integer cancel) {
        this.cancel = cancel;
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
    }

    public Integer getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
    }

    public Integer getExchange() {
        return exchange;
    }

    public void setExchange(Integer exchange) {
        this.exchange = exchange;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<OrderAnalyslsDto> getList() {
        return list;
    }

    public void setList(List<OrderAnalyslsDto> list) {
        this.list = list;
    }
}
