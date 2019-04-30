package com.dripop.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 商品操作记录
 *
 * @author dq
 * @date 2018/3/23 12:05
 */
@javax.persistence.Entity
@Table(name = "t_order_oper")
public class TOrderOper {
    @Id
    @GeneratedValue
    private Long id;

    /*订单详情ID*/
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    /*上架商品ID*/
    @Column(name = "online_id")
    private Long onlineId;

    /*赠品ID*/
    @Column(name = "gift_id")
    private Long giftId;

    /*商品串号*/
    private String imei;

    /*商品状态 1 发货、核销  2 拒收*/
    private Integer status;

    /*操作类型 2 送货上门 3 到店自提*/
    private Integer type;

    /*同批次串号*/
    private Long uuid;

    /*物流信息ID*/
    @Column(name = "express_id")
    private Long expressId;

    /*操作员ID*/
    @Column(name = "oper_id")
    private Long operId;

    @Column(name = "create_time")
    private Date createTime;

    /*修改时间*/
    @Column(name = "update_time")
    private Date updateTime;

    /*区分订单与售后 1订单 2售后退货 3售后换货 4售后发货'*/
    @Column(name = "service_type")
    private Integer serviceType;

    /*订单详情套装操作批次号*/
    private String batch;

    /*关联ID*/
    @Column(name = "relation_id")
    private Long relationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOperId() {
        return operId;
    }

    public void setOperId(Long operId) {
        this.operId = operId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getExpressId() {
        return expressId;
    }

    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }
}
