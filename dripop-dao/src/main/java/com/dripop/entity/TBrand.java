package com.dripop.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * TBrand entity. @author MyEclipse Persistence Tools
 */

@javax.persistence.Entity
@Table(name = "t_brand")
public class TBrand implements Serializable {

	@Id
	@GeneratedValue
	protected Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "img_url")
	private String imgUrl;

	@Column(name = "sort_num")
	private Integer sortNum;

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
	
	// 只是显示用的列
	@Transient
	private MultipartFile mainPicFile; // 用于图片上传
	@Transient
	private Long brandId; // 用于下拉框显示
	@Transient
	private String status; //0为禁用,1为启用

	/** default constructor */
	public TBrand() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
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

	public MultipartFile getMainPicFile() {
		return mainPicFile;
	}

	public void setMainPicFile(MultipartFile mainPicFile) {
		this.mainPicFile = mainPicFile;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}