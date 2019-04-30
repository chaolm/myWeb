package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "t_goods_type")
public class TGoodsType implements Serializable{

	public interface PRACTICE_CONSTANT{
		Integer YES = 1;//实物商品
		Integer NO = 2;//非实物商品
	}

	@Id
	@GeneratedValue
	protected Long id;

	@Column(name = "name")
	private String name;
	/**
	 * 上级类目id
	 */
	@Column(name = "parent_id")
	private Long parentId;
	/**
	 * 图片
	 */
	@Column(name = "img_url")
	private String imgUrl;

	@Column(name = "full_path")
	private String fullPath;

	@Column(name = "price_range")
	private String priceRange;

	@Column(name = "is_practice")
	private Integer ispractice;

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

	@Column(name = "link_type")
	private Integer linkType;

	@Column(name = "ref_val")
	private String refVal;

	private Integer sort;

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

	public Integer getIspractice() {
		return ispractice;
	}

	public void setIspractice(Integer ispractice) {
		this.ispractice = ispractice;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
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

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public String getRefVal() {
		return refVal;
	}

	public void setRefVal(String refVal) {
		this.refVal = refVal;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
