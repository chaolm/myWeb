package com.dripop.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "t_sys_role_grante")
public class TSysRoleGrante implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "grante_id")
    protected Long granteId;

    @Column(name = "role_id")
    private Long roleId;

    private String remark;

    @Column(name = "ent_id")
    private Long entId;

    @Column(name = "grante_rel")
    private Integer granteRel;

    @Column(name = "domain_id")
    private Integer domainId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "modify_user_id")
    private Long modifyUserId;

    @Column(name = "is_used")
    private Integer isUsed;

    @Column(name = "root_code")
    private String rootCode;

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Long getGranteId() {
        return granteId;
    }

    public void setGranteId(Long granteId) {
        this.granteId = granteId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }

    public Integer getGranteRel() {
        return granteRel;
    }

    public void setGranteRel(Integer granteRel) {
        this.granteRel = granteRel;
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getRootCode() {
        return rootCode;
    }

    public void setRootCode(String rootCode) {
        this.rootCode = rootCode;
    }
}
