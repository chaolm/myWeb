package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2017/3/13.
 */
@Entity
@Table(name = "t_express_history")
public class TExpressHistory implements Serializable {

    /**
     * 快递记录订单类型
     */
    public interface EXPRESS_SERVER_TYPE {

        public final static int SEND = 1; //寄貨
        public final static int RECEIPT = 2; //收貨

    }

    /**
     * 快递记录订单类型
     */
    public interface EXPRESS_TYPE {

        public final static int ORDER = 1; //订单
        public final static int CANCEL_ORDER = 2; //售后单

    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "rel_id")
    private Long relId;

    private Integer type;

    private Integer state;//物流状态 1 揽件 2 在途 3 签收 4 问题件

    private String data;//物流轨迹

    private Long operId;//操作员ID

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "express_no")
    private String expressNo;

    @Column(name = "express_id")
    private Long expressId;

    @Column(name = "server_type")
    private Integer serverType;

    @Column(name = "last_elogistics_time")
    private Date lastElogisticsTime;//最后一次物流更新时间

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public Long getExpressId() {
        return expressId;
    }

    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelId() {
        return relId;
    }

    public void setRelId(Long relId) {
        this.relId = relId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastElogisticsTime() {
        return lastElogisticsTime;
    }

    public void setLastElogisticsTime(Date lastElogisticsTime) {

        this.lastElogisticsTime = lastElogisticsTime;
    }

    public Long getOperId() {
        return operId;
    }

    public void setOperId(Long operId) {
        this.operId = operId;
    }
}
