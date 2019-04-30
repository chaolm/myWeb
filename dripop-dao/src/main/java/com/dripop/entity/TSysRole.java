package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * TSysRole entity. @author MyEclipse Persistence Tools
 */

@javax.persistence.Entity
@Table(name = "t_sys_role")
public class TSysRole implements Serializable {

	// Fields
	@Id
	@GeneratedValue
	@Column(name = "role_id")
	protected Long roleId;

	@Column(name = "name")
	private String name;

	private String remark;

	@Column(name = "create_user_id")
	private Long createUserId;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "modify_user_id")
	private Long modifyUserId;

	@Column(name = "modify_time")
	private Date modifyTime;

	@Column(name = "is_used")
	private Integer isUsed;

	@Column(name = "is_init")
	private Integer isInit;

	/** default constructor */
	public TSysRole() {
	}

	public Integer getIsInit() {
		return isInit;
	}

	public void setIsInit(Integer isInit) {
		this.isInit = isInit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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