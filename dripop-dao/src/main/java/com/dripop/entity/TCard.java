package com.dripop.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.core.util.NumberUtil;
import com.dripop.core.util.StringUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by liyou on 2018/2/2.
 */
@Entity
@Table(name = "t_card")
public class TCard implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "card_type")
    private Integer cardType;

    @Column(name = "card_val")
    private BigDecimal cardVal;

    @Column(name = "min_use_price")
    private Integer minUsePrice;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "use_start_time")
    private Date useStartTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "use_end_time")
    private Date useEndTime;

    @Column(name = "useDays")
    private Integer useDays;

    @Column(name = "receive_type")
    private Integer receiveType;

      @JSONField(format = "yyyy-MM-dd HH:mm:ss")
     @Column(name = "start_time")
    private Date startTime;

     @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "xl_type")
    private Integer xlType;

    @Column(name = "card_num")
    private Integer cardNum;

    private Integer status;

    private Long creator;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "use_time_type")
    private Integer useTimeType;

    @Column(name = "card_remain_num")
    private Integer cardRemainNum;

    private Long modifier;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "logic_delete")
    private Integer logicDelete;



    public String getCardValText() {
        if (cardType == 1) {//满减
            return StringUtil.subZeroAndDot(String.valueOf(NumberUtil.div(cardVal.doubleValue(), new Double(100))));
        } else {//折扣券
            return StringUtil.subZeroAndDot(String.valueOf(cardVal));
        }
    }

    public String getMinUsePriceText() {

        return StringUtil.subZeroAndDot(String.valueOf(NumberUtil.div(minUsePrice.doubleValue(), new Double(100))));
    }

    public Integer getCardRemainNum() {
        return cardRemainNum;
    }

    public void setCardRemainNum(Integer cardRemainNum) {
        this.cardRemainNum = cardRemainNum;
    }

    public Integer getUseTimeType() {
        return useTimeType;
    }

    public void setUseTimeType(Integer useTimeType) {
        this.useTimeType = useTimeType;
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

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public BigDecimal getCardVal() {
        return cardVal;
    }

    public void setCardVal(BigDecimal cardVal) {
        this.cardVal = cardVal;
    }

    public Integer getMinUsePrice() {
        return minUsePrice;
    }

    public void setMinUsePrice(Integer minUsePrice) {
        this.minUsePrice = minUsePrice;
    }

    public Date getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(Date useStartTime) {
        this.useStartTime = useStartTime;
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

    public Integer getXlType() {
        return xlType;
    }

    public void setXlType(Integer xlType) {
        this.xlType = xlType;
    }

    public Integer getCardNum() {
        return cardNum;
    }

    public void setCardNum(Integer cardNum) {
        this.cardNum = cardNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(Integer logicDelete) {
        this.logicDelete = logicDelete;
    }
}
