package com.dripop.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * TSysOper entity. @author MyEclipse Persistence Tools
 */

@javax.persistence.Entity
@Table(name = "t_sys_oper")
public class TSysOper implements java.io.Serializable {

	@Id
	@GeneratedValue
	@Column(name = "oper_id")
	protected Long operId;

	@Column(name = "remark")
	protected String remark;

	@Column(name = "create_user_id")
	protected Long createUserId;

	@Column(name = "create_time")
	protected Date createTime;

	@Column(name = "modify_user_id")
	protected Long modifyUserId;

	@Column(name = "modify_time")
	protected Date modifyTime;

	@Column(name = "is_used")
	protected Integer isUsed;

	@Column(name = "oper_name")
	protected String operName;

	@Column(name = "login_name")
	protected String loginName;

	@Column(name = "password")
	protected String password;

	@Column(name = "phone_no")
	protected String phoneNo;

	@Column(name = "org_id")
	protected Long orgId;

	@Column(name = "client_id")
	protected String clientId;

	@Column(name = "sex")
	protected Integer sex;

	@Column(name = "qq")
	protected String qq;

	@Column(name = "wechat")
	protected String wechat;

	@Column(name = "adder")
	protected String adder;

	@Column(name = "photo")
	protected String photo;

	@Column(name = "status")
	protected Integer status;

	@Column(name = "erp_oper_id")
	protected String erpOperId;

	@Column(name = "client_os")
	protected Integer clientOS; // 客户端的操作系统环境

	private Integer type;

	@Transient
	private String checkCode;//验证码

	private String salt;

	private String birthday;

	@Column(name = "ip_address")
	private String ipAddress;
	@Column(name = "is_init")
	private Integer isInit;

	@Transient
	private List<String> permissions;

	public Integer getIsInit() {
		return isInit;
	}

	public void setIsInit(Integer isInit) {
		this.isInit = isInit;
	}

	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
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
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getAdder() {
		return adder;
	}
	public void setAdder(String adder) {
		this.adder = adder;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getErpOperId() {
		return erpOperId;
	}
	public void setErpOperId(String erpOperId) {
		this.erpOperId = erpOperId;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Integer getClientOS() {
		return clientOS;
	}
	public void setClientOS(Integer clientOS) {
		this.clientOS = clientOS;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
}