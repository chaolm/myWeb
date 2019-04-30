package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2018/3/24.
 */
@Entity
@Table(name = "t_goods_notice")
public class TGoodsNotice implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "cust_id")
    private Long custId;

    @Column(name = "cust_phone")
    private String custPhone;

    @Column(name = "online_id")
    private Long onlineId;

    @Column(name = "record_time")
    private Date recordTime;

    @Column(name = "message_notice_flag")
    private Integer messageNoticeFlag;

    @Column(name = "message_notice_time")
    private Date messageNoticeTime;

    @Column(name = "phone_link_flag")
    private Integer phoneLinkFlag;

    @Column(name = "phone_link_oper")
    private Long phoneLinkOper;

    @Column(name = "phone_link_time")
    private Date phoneLinkTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "goods_online_name")
    private String goodsOnlineName;

    @Column(name = "goods_online_price")
    private Integer goodsOnlinePrice;

    @Column(name = "goods_online_num")
    private Integer goodsOnlineNum;

    private String remark;

    @Column(name = "is_used")
    private Integer isUsed;

    @Column(name = "notice_type")
    private Integer noticeType;

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getMessageNoticeFlag() {
        return messageNoticeFlag;
    }

    public void setMessageNoticeFlag(Integer messageNoticeFlag) {
        this.messageNoticeFlag = messageNoticeFlag;
    }

    public Date getMessageNoticeTime() {
        return messageNoticeTime;
    }

    public void setMessageNoticeTime(Date messageNoticeTime) {
        this.messageNoticeTime = messageNoticeTime;
    }

    public Integer getPhoneLinkFlag() {
        return phoneLinkFlag;
    }

    public void setPhoneLinkFlag(Integer phoneLinkFlag) {
        this.phoneLinkFlag = phoneLinkFlag;
    }

    public Long getPhoneLinkOper() {
        return phoneLinkOper;
    }

    public void setPhoneLinkOper(Long phoneLinkOper) {
        this.phoneLinkOper = phoneLinkOper;
    }

    public Date getPhoneLinkTime() {
        return phoneLinkTime;
    }

    public void setPhoneLinkTime(Date phoneLinkTime) {
        this.phoneLinkTime = phoneLinkTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getGoodsOnlineName() {
        return goodsOnlineName;
    }

    public void setGoodsOnlineName(String goodsOnlineName) {
        this.goodsOnlineName = goodsOnlineName;
    }

    public Integer getGoodsOnlinePrice() {
        return goodsOnlinePrice;
    }

    public void setGoodsOnlinePrice(Integer goodsOnlinePrice) {
        this.goodsOnlinePrice = goodsOnlinePrice;
    }

    public Integer getGoodsOnlineNum() {
        return goodsOnlineNum;
    }

    public void setGoodsOnlineNum(Integer goodsOnlineNum) {
        this.goodsOnlineNum = goodsOnlineNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }
}
