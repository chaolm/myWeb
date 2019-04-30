package com.dripop.dto;

import java.util.List;
import java.util.Map;

@Deprecated
public class InterDtoUser implements java.io.Serializable {

     private Long operId;
     private String name;
     private Long orgId;
     private String orgName;
     private String phoneNo;
     private String qq;
     private String wechat;
     private String photo;
     private String businessPost;
     private String rootCode;
     private String qyName;
     private String orgPhoneno;
     private String logo;
     private int utype; // 1门店员工  2门店店长  3区域店员（门店直接上级中的店员）4区域经理（门店直接上级中的主管） 5区域以上总公司以下店员  6区域以上总公司以下主管  7总公司店员 8总公司主管
     private String token;
     private String wxQrcodePrefix; // 邀请店员二维码URL前缀，加上ticket，即为完整的URL
     private String wxAppId; // 微信公众号的appid
     private String wxAppSecret; // 微信公众号的secret
	 private List<Map<String, String>> orgList;
	 private List<Map<String, String>> entity;
	 private String address;

	 private List<String> permissions;
     
	public Long getId() {
		return operId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getOrgPhoneno() {
		return orgPhoneno;
	}
	public void setOrgPhoneno(String orgPhoneno) {
		this.orgPhoneno = orgPhoneno;
	}
	public String getBusinessPost() {
		return businessPost;
	}
	public void setBusinessPost(String businessPost) {
		this.businessPost = businessPost;
	}
	public String getQyName() {
		return qyName;
	}
	public void setQyName(String qyName) {
		this.qyName = qyName;
	}
	public String getRootCode() {
		return rootCode;
	}
	public void setRootCode(String rootCode) {
		this.rootCode = rootCode;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public int getUtype() {
		return utype;
	}
	public void setUtype(int utype) {
		this.utype = utype;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getWxQrcodePrefix() {
		return wxQrcodePrefix;
	}
	public void setWxQrcodePrefix(String wxQrcodePrefix) {
		this.wxQrcodePrefix = wxQrcodePrefix;
	}
	public String getWxAppId() {
		return wxAppId;
	}
	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}
	public String getWxAppSecret() {
		return wxAppSecret;
	}
	public void setWxAppSecret(String wxAppSecret) {
		this.wxAppSecret = wxAppSecret;
	}
	public List<Map<String, String>> getOrgList() {
		return orgList;
	}
	public void setOrgList(List<Map<String, String>> orgList) {
		this.orgList = orgList;
	}
	public List<Map<String, String>> getEntity() {
		return entity;
	}
	public void setEntity(List<Map<String, String>> entity) {
		this.entity = entity;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
}