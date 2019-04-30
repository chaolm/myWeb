package com.dripop.promotion.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.bean.ActivityStatus;
import com.dripop.core.util.NumberUtil;
import com.dripop.core.util.StringUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by liyou on 2018/2/5.
 */
public class CardPageDto implements Serializable {

    private Long id;

    private String name;


    private BigDecimal cardVal;

    private String cardValText;

    private Integer receiveType;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date useEndTime;

    private Integer useDays;

    private Integer cardStatus;

    private String statusText;

    private Integer cardType;

    private Integer minUsePrice;

    private Date useStartTime;

    public BigDecimal getCardVal() {
        return cardVal;
    }

    public Integer getCardType() {
        return cardType;
    }

    public Integer getMinUsePrice() {
        return minUsePrice;
    }

    public Date getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(Date useStartTime) {
        this.useStartTime = useStartTime;
    }

    public void setMinUsePrice(Integer minUsePrice) {
        this.minUsePrice = minUsePrice;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getCardValText() {
        StringBuffer sb=new StringBuffer();
        String minPrices = StringUtil.subZeroAndDot(String.valueOf(NumberUtil.div(minUsePrice.doubleValue(), new Double(100))));
        if(cardType==1){
            String cardVals =StringUtil.subZeroAndDot(String.valueOf(NumberUtil.div(cardVal.doubleValue(), new Double(100))));
            sb.append(cardVals + "元 " + "(满" + minPrices + "使用)");
        }
        if(cardType==2){
            sb.append(StringUtil.subZeroAndDot(cardVal.toString()) + "折 " + "(满" +minPrices+ "使用)");
        }
        return sb.toString();
    }

    public void setCardValText(String cardValText) {
        this.cardValText = cardValText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardVal(BigDecimal cardVal) {
        this.cardVal = cardVal;
    }

    public Integer getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(Integer receiveType) {
        this.receiveType = receiveType;
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

    public Date getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(Date useEndTime) {
        this.useEndTime = useEndTime;
    }

    public Integer getUseDays() {
        return useDays;
    }

    public void setUseDays(Integer useDays) {
        this.useDays = useDays;
    }

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        if (receiveType == 4) {
            this.cardStatus = 4;
        } else {
            this.cardStatus = cardStatus;
        }
    }

    public String getStatusText() {
        return ActivityStatus.getName(this.cardStatus);
    }

  /*  public void setStatusText(String statusText) {
        this.statusText = statusText;
    }*/
}
