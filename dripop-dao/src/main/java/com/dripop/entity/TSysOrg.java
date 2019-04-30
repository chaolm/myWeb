package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * TSysOrg entity. @author MyEclipse Persistence Tools
 */

@javax.persistence.Entity
@Table(name = "t_sys_org")
public class TSysOrg implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "org_id")
	private Long id;

	@Transient
	private String orgCode;

	private String name;

	@Column(name = "short_name")
	private String shortName;

	@Column(name = "pid")
	private Long pid;

	@Column(name = "address")
	private String address;
	@Transient
	private Integer isLeaf;

	@Column(name = "link_man")
	private String linkMan;

	@Column(name = "link_phone")
	private String linkPhone;

	@Column(name = "lng",precision = 10,scale = 7)
	private String lng;

	@Column(name = "lat",precision = 10,scale = 7)
	private String lat;
	@Transient
	private String areaCode;

	@Column(name = "traffic")
	private String traffic;

	@Column(name = "description")
	private String description;

	@Column(name = "erp_bm")
	private String erpOrgCode;


	@Column(name = "org_type")
	private Integer orgType;

	@Column(name = "photo")
	private String photo;

	/**
	 * 1有效 2停用
	 */
	@Column(name = "status")
	private Integer status;


	@Column(name = "erp_id")
	private String erpId;//ERP对应的门店ID
	
	// 只是显示用的列
	@Transient
	private String viewPName; //父组织名称
	@Transient
	private String viewAreaName; //地区名称
	//新增相关字段

	@Column(name = "province")
	private String province;

	@Column(name = "city")
	private String city;

	@Column(name = "open_time")
	private String openTime;

	@Column(name = "share_type")
	private Integer shareType;

	@Column(name = "store_type")
	private Integer storeType;

	@Column(name = "is_visible")
	private Integer isVisible;

	@Column(name = "county")
	private String county;

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

	@Column(name = "storeaddress_photo")
	private String storeaddressPhoto;

	private String logo;

	@Column(name = "bind_phone")
	private String bindPhone;

    @Column(name = "is_pick")
    private String isPick;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getViewPName() {
		return viewPName;
	}

	public void setViewPName(String viewPName) {
		this.viewPName = viewPName;
	}

	public String getViewAreaName() {
		return viewAreaName;
	}

	public void setViewAreaName(String viewAreaName) {
		this.viewAreaName = viewAreaName;
	}

	public String getErpOrgCode() {
		return erpOrgCode;
	}

	public void setErpOrgCode(String erpOrgCode) {
		this.erpOrgCode = erpOrgCode;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
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


	public String getErpId() {
		return erpId;
	}

	public void setErpId(String erpId) {
		this.erpId = erpId;
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

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public Integer getShareType() {
		return shareType;
	}

	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}

	public Integer getStoreType() {
		return storeType;
	}

	public void setStoreType(Integer storeType) {
		this.storeType = storeType;
	}

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;

	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getStoreaddressPhoto() {
		return storeaddressPhoto;
	}

	public void setStoreaddressPhoto(String storeaddressPhoto) {
		this.storeaddressPhoto = storeaddressPhoto;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBindPhone() {
		return bindPhone;
	}

	public void setBindPhone(String bindPhone) {
		this.bindPhone = bindPhone;
	}

    public String getIsPick() {
        return isPick;
    }

    public void setIsPick(String isPick) {
        this.isPick = isPick;
    }
}