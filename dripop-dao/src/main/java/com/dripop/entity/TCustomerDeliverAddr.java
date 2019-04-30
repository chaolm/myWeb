package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_customer_deliver_addr")
public class TCustomerDeliverAddr implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "cust_id")
	private Long custId;

	private String address;

	private String name;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "default_flag")
	private Integer defaultFlag;

	@Column(name = "is_used")
	private Integer isUsed;

	private String province;

	private String city;

	private String county;

	@Column(name = "post_code")
	private String postCode;

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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(Integer defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public Integer getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
}
