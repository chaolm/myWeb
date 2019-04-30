package com.dripop.entity;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * TGoodsOnline entity. @author MyEclipse Persistence Tools
 */
@javax.persistence.Entity
@Table(name = "t_goods_online")
public class TGoodsOnline implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "online_id")
	private Long id;

	@Column(name = "spu_id")
	private Long goodsId;

	@Column(name = "sale_price")
	private Integer salePrice;

	@Column(name = "min_price")
	private Integer minPrice;

	@Column(name = "money")
	private Integer money;

	@Column(name = "start_time")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	@Column(name = "end_time")
	private Date endTime;

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

	@Transient
	private Long areaId;

	@Column(name = "purchase_price")
	private Integer netPrice; //采购价

	@Column(name = "is_reject")
	private Integer isReject;
	private Integer stock;

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	@Transient
	private Integer isAddContract;

	@Transient
	private Integer contractMoney;

	@Transient
	private String orgValues;

	@Transient
	private String promotionIds;

	@Column(name = "sales_volume")
	private Integer salesVolume;

//	@Column(name = "show_name")
//	private String showName;

	@Column(name = "sale_point")
	private String salePoint;

	@Column(name = "show_toc")
	private Integer showToc;

	@Column(name = "official_price")
	private Integer officialPrice;

	@Column(name = "others_price")
	private Integer othersPrice;

	// add by zhangjunhu 2017-04-10 新增是否支持花呗，支持开始时间，支持结束时间，支持期数字段

	@Column(name = "is_use_staging")
	private Integer isUseStaging;

	@Column(name = "staging_start_time")
	private Date stagingStartTime;

	@Column(name = "staging_end_time")
	private Date stagingEndTime;

	@Column(name = "staging_num")
	private Integer stagingNum;
	// 销售配置字段

	@Column(name = "op_paymodel")
	private String opPayModel;
	//是否赠送积分,1不赠送2赠送,默认1
	@Column(name = "is_towards_point")
	private Integer isTowardsPoint;
	// add by zhangjunhu 2017-05-10 begin 添加预售上架商品相关属性 是否预售商品，预售开始时间，预售结束时间，预计可发货时间
	@Column(name = "goods_sell_type")
	private Integer goodsSellType;

	@Column(name = "presell_start_time")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date presellStartTime;

	@Column(name = "presell_end_time")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date presellEndTime;

	@Column(name = "estimated_delivery_time")
	private Date estimatedDeliveryTime;
	// 尾款支付开始时间,尾款支付结束时间,商品预售价格,预售商品定金,定金可抵用金额,定金是否可退（默认定金可退）1可退，2不可退
	@Column(name = "balance_payment_start_time")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date balancePaymentStartTime;

	@Column(name = "balance_payment_end_time")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date balancePaymentEndTime;

	@Column(name = "presell_money")
	private Integer presellMoney;

	@Column(name = "deposit")
	private Integer deposit;

	@Column(name = "deposit_discount_amount")
	private Integer depositDiscountAmount;

	@Column(name = "is_rejected_deposit")
	private Integer isRejectedDeposit;
	// add by zhangjunhu 2017-05-10 end 添加预售上架商品相关属性

	public Integer getOfficialPrice() {
		return officialPrice;
	}

	public void setOfficialPrice(Integer officialPrice) {
		this.officialPrice = officialPrice;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}


	// Constructors

	/** default constructor */
	public TGoodsOnline() {
	}

	/** full constructor */
	public TGoodsOnline(Long goodsId, Integer salePrice, Integer minPrice,
                        Integer money, Date startTime, Date endTime,
                        String remark, Long createUserId, Date createTime,
                        Long modifyUserId, Date modifyTime, Integer isUsed,
                        String rootCode, Integer netPrice, Integer isAddContract, Integer contractMoney,Integer stock) {
		this.goodsId = goodsId;
		this.salePrice = salePrice;
		this.minPrice = minPrice;
		this.money = money;
		this.startTime = startTime;
		this.endTime = endTime;
		this.remark = remark;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.modifyUserId = modifyUserId;
		this.modifyTime = modifyTime;
		this.isUsed = isUsed;
		this.isAddContract = isAddContract;
		this.contractMoney = contractMoney;
		this.stock = stock;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getMinPrice() {
		return this.minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Integer getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(Integer netPrice) {
		this.netPrice = netPrice;
	}

	public Integer getIsAddContract() {
		return isAddContract;
	}

	public void setIsAddContract(Integer isAddContract) {
		this.isAddContract = isAddContract;
	}

	public Integer getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(Integer contractMoney) {
		this.contractMoney = contractMoney;
	}

	public Integer getIsReject() {
		return isReject;
	}

	public void setIsReject(Integer isReject) {
		this.isReject = isReject;
	}

	public String getOrgValues() {
		return orgValues;
	}

	public void setOrgValues(String orgValues) {
		this.orgValues = orgValues;
	}

	public String getPromotionIds() {
		return promotionIds;
	}

	public void setPromotionIds(String promotionIds) {
		this.promotionIds = promotionIds;
	}

//	public String getShowName() {
//		return showName;
//	}
//
//	public void setShowName(String showName) {
//		this.showName = showName;
//	}

	public String getSalePoint() {
		return salePoint;
	}

	public void setSalePoint(String salePoint) {
		this.salePoint = salePoint;
	}

	public Integer getShowToc() {
		if (showToc == null)
			showToc= 0;
		return showToc;
	}

	public void setShowToc(Integer showToc) {
		this.showToc = showToc;
	}

	public Integer getOthersPrice() {
		return othersPrice;
	}

	public void setOthersPrice(Integer othersPrice) {
		this.othersPrice = othersPrice;
	}

	public Integer getIsUseStaging() {
		return isUseStaging;
	}

	public void setIsUseStaging(Integer isUseStaging) {
		this.isUseStaging = isUseStaging;
	}

	public Date getStagingStartTime() {
		return stagingStartTime;
	}

	public void setStagingStartTime(Date stagingStartTime) {
		this.stagingStartTime = stagingStartTime;
	}

	public Date getStagingEndTime() {
		return stagingEndTime;
	}

	public void setStagingEndTime(Date stagingEndTime) {
		this.stagingEndTime = stagingEndTime;
	}

	public Integer getStagingNum() {
		return stagingNum;
	}

	public void setStagingNum(Integer stagingNum) {
		this.stagingNum = stagingNum;
	}

	public String getOpPayModel() {
		return opPayModel;
	}

	public void setOpPayModel(String opPayModel) {
		this.opPayModel = opPayModel;
	}

	public Integer getIsTowardsPoint() {
		if(isTowardsPoint == null)
			isTowardsPoint = 1;
		return isTowardsPoint;
	}

	public void setIsTowardsPoint(Integer isTowardsPoint) {
		this.isTowardsPoint = isTowardsPoint;
	}
	public Integer getGoodsSellType() {
		return goodsSellType;
	}

	public void setGoodsSellType(Integer goodsSellType) {
		this.goodsSellType = goodsSellType;
	}

	public Date getPresellStartTime() {
		return presellStartTime;
	}

	public void setPresellStartTime(Date presellStartTime) {
		this.presellStartTime = presellStartTime;
	}

	public Date getPresellEndTime() {
		return presellEndTime;
	}

	public void setPresellEndTime(Date presellEndTime) {
		this.presellEndTime = presellEndTime;
	}

	public Date getEstimatedDeliveryTime() {
		return estimatedDeliveryTime;
	}

	public void setEstimatedDeliveryTime(Date estimatedDeliveryTime) {
		this.estimatedDeliveryTime = estimatedDeliveryTime;
	}

	public Date getBalancePaymentStartTime() {
		return balancePaymentStartTime;
	}

	public void setBalancePaymentStartTime(Date balancePaymentStartTime) {
		this.balancePaymentStartTime = balancePaymentStartTime;
	}

	public Date getBalancePaymentEndTime() {
		return balancePaymentEndTime;
	}

	public void setBalancePaymentEndTime(Date balancePaymentEndTime) {
		this.balancePaymentEndTime = balancePaymentEndTime;
	}

	public Integer getPresellMoney() {
		return presellMoney;
	}

	public void setPresellMoney(Integer presellMoney) {
		this.presellMoney = presellMoney;
	}

	public Integer getDeposit() {
		return deposit;
	}

	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}

	public Integer getDepositDiscountAmount() {
		return depositDiscountAmount;
	}

	public void setDepositDiscountAmount(Integer depositDiscountAmount) {
		this.depositDiscountAmount = depositDiscountAmount;
	}

	public Integer getIsRejectedDeposit() {
		return isRejectedDeposit;
	}

	public void setIsRejectedDeposit(Integer isRejectedDeposit) {
		this.isRejectedDeposit = isRejectedDeposit;
	}
}