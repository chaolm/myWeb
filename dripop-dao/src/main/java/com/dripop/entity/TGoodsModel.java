package com.dripop.entity;

import javax.persistence.*;
import java.util.Date;


/**
 * TGoodsModel entity. @author MyEclipse Persistence Tools
 */

@javax.persistence.Entity
@Table(name = "t_goods_model")
public class TGoodsModel implements java.io.Serializable {

	@Id
	@GeneratedValue
	protected Long id;

	@Transient
	private Long parentId;

	@Column(name = "type_id")
	protected Long typeId;

	@Column(name = "brand_id")
	private Long brandId; // 用于下拉框显示

	@Column(name = "status")
	private Integer status; // 这个字段暂无意义

	@Column(name = "name")
	private String name;

	@Column(name = "modify_user_id")
	protected Long modifyUserId;

	@Column(name = "modify_time")
	protected Date modifyTime;

	@Column(name = "is_used")
	protected Integer isUsed;

	@Column(name = "sell_area")
	private String sellArea;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getSellArea() {
		return sellArea;
	}

	public void setSellArea(String sellArea) {
		this.sellArea = sellArea;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		if (isUsed == null)
			isUsed = 1;
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
}