package com.dripop.util.hbfq;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by junhu on 2017/4/11.
 */
public class StagingItemDto {

    //分期期数
    private Integer itemStagingNum;
    //是否支持花呗分期
    private Integer isStaging;
    //每期支付金额
    private String terminallyPay;
    //手续费
    private String poundageCost;

    //显示是否含手续费
    @JSONField(serialize = false)
    private String stagingText;
    //总手续费
    @JSONField(serialize = false)
    private Integer poundageCostTotal;
    //支付总金额
    @JSONField(serialize = false)
    private Integer terminallyPayTotal;

    public Integer getItemStagingNum() {
        return itemStagingNum;
    }

    public void setItemStagingNum(Integer itemStagingNum) {
        this.itemStagingNum = itemStagingNum;
    }

    public Integer getIsStaging() {
        return isStaging;
    }

    public void setIsStaging(Integer isStaging) {
        this.isStaging = isStaging;
    }

    public String getTerminallyPay() {
        return terminallyPay;
    }

    public void setTerminallyPay(String terminallyPay) {
        this.terminallyPay = terminallyPay;
    }

    public String getPoundageCost() {
        return poundageCost;
    }

    public void setPoundageCost(String poundageCost) {
        this.poundageCost = poundageCost;
    }

    public Integer getPoundageCostTotal() {
        return poundageCostTotal;
    }

    public void setPoundageCostTotal(Integer poundageCostTotal) {
        this.poundageCostTotal = poundageCostTotal;
    }

    public Integer getTerminallyPayTotal() {
        return terminallyPayTotal;
    }

    public void setTerminallyPayTotal(Integer terminallyPayTotal) {
        this.terminallyPayTotal = terminallyPayTotal;
    }

    public String getStagingText() {
        return stagingText;
    }

    public void setStagingText(String stagingText) {
        this.stagingText = stagingText;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(null == obj) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        StagingItemDto dto = (StagingItemDto)obj;
        return this.isStaging == dto.isStaging && this.itemStagingNum == dto.itemStagingNum;
    }
}
