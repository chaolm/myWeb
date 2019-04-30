package com.dripop.statistics.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.core.util.NumberUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 交易分析实体类
 *
 * @author dq
 * @date 2018/3/17 16:55
 */

public class PayAnalyslsDto implements Serializable {
    /*时间*/
    private String time;

    /*支付成功笔数*/
    private Integer successPay;

    /*支付人数*/
    private Integer peoplePayNum;

    /*支付成功金额*/
    private Double pay;

    /*真实支付 分*/
    @JSONField(serialize = false)
    private Integer realPay;

    /*退款成功笔数*/
    private Integer successRefund;

    /*退款人数*/
    private Integer peopleRefundNum;

    /*退款成功金额*/
    private Double refund;

    /*退款 分*/
    @JSONField(serialize = false)
    private Integer totalRefund;


    /*结算金额*/
    private Double settlement;

    /*交易统计集*/
    private List<PayAnalyslsDto> list;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getSuccessPay() {
        return successPay;
    }

    public void setSuccessPay(Integer successPay) {
        this.successPay = successPay;
    }

    public Integer getPeoplePayNum() {
        return peoplePayNum;
    }

    public void setPeoplePayNum(Integer peoplePayNum) {
        this.peoplePayNum = peoplePayNum;
    }

    public Double getPay() {
        if (realPay == null) {
            realPay = 0;
        }
        return NumberUtil.div(new Double(realPay), new Double(100));
    }

    public Integer getSuccessRefund() {
        return successRefund;
    }

    public void setSuccessRefund(Integer successRefund) {
        this.successRefund = successRefund;
    }

    public Integer getPeopleRefundNum() {
        return peopleRefundNum;
    }

    public void setPeopleRefundNum(Integer peopleRefundNum) {
        this.peopleRefundNum = peopleRefundNum;
    }

    public Double getRefund() {
        if (totalRefund == null) {
            totalRefund = 0;
        }
        return NumberUtil.div(new Double(totalRefund), new Double(100));
    }

    public Double getSettlement() {
        return settlement;
    }

    public void setSettlement(Double settlement) {
        this.settlement = settlement;
    }

    public List<PayAnalyslsDto> getList() {
        return list;
    }

    public void setList(List<PayAnalyslsDto> list) {
        this.list = list;
    }

    public Integer getRealPay() {
        return realPay;
    }

    public void setRealPay(Integer realPay) {
        this.realPay = realPay;
    }

    public Integer getTotalRefund() {
        return totalRefund;
    }

    public void setTotalRefund(Integer totalRefund) {
        this.totalRefund = totalRefund;
    }

}
