package com.dripop.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2018/1/4.
 */
@javax.persistence.Entity
@Table(name = "t_sys_oper_role_rel")
public class TSysOperRoleRel implements Serializable{

    @Id
    @GeneratedValue
    @Column(name="rel_id")
    private Long id;

    @Column(name="oper_id")
    private Long operId;

    @Column(name="role_id")
    private Long roleId;

    @Column(name="domain_id")
    private Integer domainId;

    @Column(name="remark")
    private String remark;

    @Column(name="create_user_id")
    private Long createUserId;

    @Column(name="create_time")
    private Date createTime;

    @Column(name="modify_user_id")
    private Long modifyUserId;

    @Column(name="modify_time")
    private Date modifyTime;

    @Column(name="is_used")
    private Integer isUsed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperId() {
        return operId;
    }

    public void setOperId(Long operId) {
        this.operId = operId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }
}
