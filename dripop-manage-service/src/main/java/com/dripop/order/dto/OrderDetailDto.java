 package com.dripop.order.dto;

import com.dripop.dto.CommonGiftDto;

import java.io.Serializable;
import java.util.List;

 /**
  * Created by liyou on 2018/3/13.
  */
 public class OrderDetailDto implements Serializable {




     /*赠品集合*/
    protected List<GiftForImeiDto> giftReturn;

     public List<GiftForImeiDto> getGiftReturn() {
         return giftReturn;
     }

     public void setGiftReturn(List<GiftForImeiDto> giftReturn) {
         this.giftReturn = giftReturn;
     }

     /*优惠价*/
     protected String couponPrice;

     public String getCouponPrice() {
         return couponPrice;
     }

     public void setCouponPrice(String couponPrice) {
         this.couponPrice = couponPrice;
     }

     /*商品上架ID*/
     protected Long onlineId;

     public Long getOnlineId() {
         return onlineId;
     }

     public void setOnlineId(Long onlineId) {
         this.onlineId = onlineId;
     }

     private Long detailId;

     private String imgUrl;

     private String goodsName;

     private String salePrice;

     private String discountPrice;

     private String gift;

     private String giftName;

     private Integer giftNum;

     private Integer num;

     private Long packageId;

     private Integer yhPrice;

     private  Integer totalPrice;

     public Integer getYhPrice() {
         return yhPrice;
     }

     public void setYhPrice(Integer yhPrice) {
         this.yhPrice = yhPrice;
     }

     public Integer getTotalPrice() {
         return totalPrice;
     }

     public void setTotalPrice(Integer totalPrice) {
         this.totalPrice = totalPrice;
     }

     public String getDiscountPrice() { return discountPrice; }

     public void setDiscountPrice(String discountPrice) { this.discountPrice = discountPrice; }

     public String getGift() {
         return gift;
     }

     public void setGift(String gift) {
         this.gift = gift;
     }

     public Long getPackageId() {
         return packageId;
     }

     public void setPackageId(Long packageId) {
         this.packageId = packageId;
     }

     public Long getDetailId() {
         return detailId;
     }

     public void setDetailId(Long detailId) {
         this.detailId = detailId;
     }

     public String getImgUrl() {
         return imgUrl;
     }

     public void setImgUrl(String imgUrl) {
         this.imgUrl = imgUrl;
     }

     public String getGoodsName() {
         return goodsName;
     }

     public void setGoodsName(String goodsName) {
         this.goodsName = goodsName;
     }

     public String getSalePrice() {
         return salePrice;
     }

     public void setSalePrice(String salePrice) {
         this.salePrice = salePrice;
     }

     public String getGiftName() {
         return giftName;
     }

     public void setGiftName(String giftName) {
         this.giftName = giftName;
     }

     public Integer getGiftNum() {
         return giftNum;
     }

     public void setGiftNum(Integer giftNum) {
         this.giftNum = giftNum;
     }

     public Integer getNum() {
         return num;
     }

     public void setNum(Integer num) {
         this.num = num;
     }
 }
