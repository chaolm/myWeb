package com.dripop.comment.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class GetAllComment implements Serializable {
    //id
    private Long id;

    //商品评分
    private Integer goodsStarLevel;
    //评论内容
    private String content;
    //评论图片地址
    private String pictureAddr;
    //购买时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date purchaseTime;
    //客户昵称或号码
    private String custName;
    //客户ID
    private Long custId;
    //客户账号
    private String phoneNo;
    //评价状态 1待审核2已通过3已拒绝
    private Integer status;
    //订单id
    private Long orderId;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodsStarLevel() {
        return goodsStarLevel;
    }

    public void setGoodsStarLevel(Integer goodsStarLevel) {
        this.goodsStarLevel = goodsStarLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictureAddr() {
        return pictureAddr;
    }

    public void setPictureAddr(String pictureAddr) {
        this.pictureAddr = pictureAddr;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
