package com.dripop.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2017/12/27.
 */
@javax.persistence.Entity
@Table(name = "t_customer_collect")
public class TCustomerCollect implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "online_id")
    private Long onlineId;

    @Column(name = "cust_id")
    private Long custId;

    @Column(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }
}
