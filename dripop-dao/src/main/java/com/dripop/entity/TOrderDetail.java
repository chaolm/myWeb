package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_order_detail")
public class TOrderDetail implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "order_detail_id")
	private Long id;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "spu_id")
	private Long spuId;

	@Column(name = "original_price")
	private Integer salePrice;

	private Integer num;

//	private String reserve1;
//
//	private String reserve2;
//
//	private String reserve3;

	@Column(name = "online_id")
	private Long onlineId;

	@Column(name = "package_id")
	private Long packageId;

	@Column(name = "discount_price")
	private Integer discountPrice;

	//每商品积分
	@Column(name = "point_value")
	private Integer pointValue;

	private String gift;

	@Column(name = "goods_name")
	private String goodsName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public Integer getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Long getOnlineId() {
		return onlineId;
	}

	public void setOnlineId(Long onlineId) {
		this.onlineId = onlineId;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public Integer getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Integer discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getPointValue() {
		return pointValue;
	}

	public void setPointValue(Integer pointValue) {
		this.pointValue = pointValue;
	}

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}