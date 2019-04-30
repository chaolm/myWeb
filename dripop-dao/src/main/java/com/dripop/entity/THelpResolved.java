package com.dripop.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户帮助反馈记录表
 *
 * @author dq
 * @date 2018/6/8 18:07
 */
@javax.persistence.Entity
@Table(name = "t_help_resolved")
public class THelpResolved implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "resolved_id")
    private Long resolvedId;

    @Column(name = "specific_id")
    private Long specificId;

    @Column(name = "cust_id")
    private Long custId;

    private Integer type;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "is_delete")
    private Integer isDelete;

    public Long getResolvedId() {
        return resolvedId;
    }

    public void setResolvedId(Long resolvedId) {
        this.resolvedId = resolvedId;
    }

    public Long getSpecificId() {
        return specificId;
    }

    public void setSpecificId(Long specificId) {
        this.specificId = specificId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
