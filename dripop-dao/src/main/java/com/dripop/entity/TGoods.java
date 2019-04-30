package com.dripop.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

/**
 * TGoods entity. @author MyEclipse Persistence Tools
 */
@javax.persistence.Entity
@Table(name = "t_goods")
public class TGoods implements java.io.Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "assistant_code")
	private String assistantCode;

	private String model;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "specs")
	private String specs;

	@Column(name = "conduct")
	private String conduct;

	@Column(name = "big_pic")
	private String bigPic;

	@Column(name = "mid_pic")
	private String midPic;

	@Column(name = "up_time")
	private Date upTime;

	@Column(name = "down_time")
	private Date downTime;

	@Column(name = "small_pic")
	private String smallPic;

	@Column(name = "pro_descrip")
	private String proDescrip;

	@Column(name = "description")
	private String description;

	@Column(name = "maintain_desction")
	private String maintainDesction;

	@Column(name = "spec_description")
	private String specDescription;

	@Column(name = "join_way")
	private Integer joinWay;

	@Column(name = "status")
	private Integer status;

	@Column(name = "remark")
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

	@Column(name = "brand_id")
	private Long brandId;

	@Column(name = "type_id")
	private Long typeId;

	@Column(name = "model_id")
	private Long modelId;

	@Transient
	private MultipartFile mainPicFile;

	@Transient
	private Integer onlineGoods;

	@Transient
	private Long markId;

	@Column(name = "barcode")
	private String barcode;

	@Column(name = "keyword")
	private String keyword;

	@Column(name = "settle_price")
	private Integer settlePrice;

	@Column(name = "settle_money")
	private Integer settleMoney;

	@Transient
	private Integer onlineStatus;

	@Transient
	private String onlineCount;

	@Transient
	private String brandName;

	@Transient
	private String typeName;

	private Integer stock;

	@Column(name = "publish_time")
	private Date publishTime;

	@Column(name = "order_stock")
	private Integer orderStock;
	private String color;
	private String memory;
	private String standard;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssistantCode() {
		return this.assistantCode;
	}

	public void setAssistantCode(String assistantCode) {
		this.assistantCode = assistantCode;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getSpecs() {
		return specs;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	public String getConduct() {
		return this.conduct;
	}

	public void setConduct(String conduct) {
		this.conduct = conduct;
	}

	public String getBigPic() {
		return this.bigPic;
	}

	public void setBigPic(String bigPic) {
		this.bigPic = bigPic;
	}

	public String getMidPic() {
		return this.midPic;
	}

	public void setMidPic(String midPic) {
		this.midPic = midPic;
	}

	public Date getUpTime() {
		return this.upTime;
	}

	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}

	public Date getDownTime() {
		return this.downTime;
	}

	public void setDownTime(Date downTime) {
		this.downTime = downTime;
	}

	public String getSmallPic() {
		return this.smallPic;
	}

	public void setSmallPic(String smallPic) {
		this.smallPic = smallPic;
	}

	public String getProDescrip() {
		return this.proDescrip;
	}

	public void setProDescrip(String proDescrip) {
		this.proDescrip = proDescrip;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getMaintainDesction() {
		return maintainDesction;
	}

	public void setMaintainDesction(String maintainDesction) {
		this.maintainDesction = maintainDesction;
	}

	public String getSpecDescription() {
		return specDescription;
	}

	public void setSpecDescription(String specDescription) {
		this.specDescription = specDescription;
	}

	public Integer getJoinWay() {
		return this.joinWay;
	}

	public void setJoinWay(Integer joinWay) {
		this.joinWay = joinWay;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getIsUsed() {
		return this.isUsed;
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

	public Integer getOnlineGoods() {
		return onlineGoods;
	}

	public void setOnlineGoods(Integer onlineGoods) {
		this.onlineGoods = onlineGoods;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Long getMarkId() {
		return markId;
	}

	public void setMarkId(Long markId) {
		this.markId = markId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(String onlineCount) {
		this.onlineCount = onlineCount;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getSettlePrice() {
		return settlePrice;
	}

	public void setSettlePrice(Integer settlePrice) {
		this.settlePrice = settlePrice;
	}

	public Integer getSettleMoney() {
		return settleMoney;
	}

	public void setSettleMoney(Integer settleMoney) {
		this.settleMoney = settleMoney;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getOrderStock() {
		return orderStock;
	}

	public void setOrderStock(Integer orderStock) {
		this.orderStock = orderStock;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
}