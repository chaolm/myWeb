 package com.dripop.order.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.dto.CommonGiftDto;
import com.dripop.util.PriceUtil;

import java.io.Serializable;
import java.util.List;

 /**
 * Created by liyou on 2018/3/13.
 */
public class OrderGoodsDto implements Serializable {

     private Long detailId;

     private String imgUrl;

     private String goodsName;

     private Long onlineId;

     public Long getOnlineId() { return onlineId; }

     public void setOnlineId(Long onlineId) { this.onlineId = onlineId; }

     private Integer salePrice;

     private Integer discountPrice;

     @JSONField(serialize = false)
     private String gift;

     private List<CommonGiftDto> giftReturn;

     private Integer num;

     private Long packageId;


     /**
      * 2.1版本增加字段
      * @return
      */
     //定金
     private Integer deposit;
     private String depositStr;
     //尾款
     private Integer tailMoney;
     private String tailMoneyStr;

     //真实实付
     private Integer realPay;
     private Integer goodsSellType;

     public Integer getGoodsSellType() {
         return goodsSellType;
     }

     public void setGoodsSellType(Integer goodsSellType) {
         this.goodsSellType = goodsSellType;
     }

     public Integer getRealPay() {
         return realPay;
     }

     public void setRealPay(Integer realPay) {
         this.realPay = realPay;
     }

     public Integer getDeposit() {
         return deposit;
     }

     public void setDeposit(Integer deposit) {
         this.deposit = deposit;
     }

     public String getDepositStr() {
         if(deposit == null){
             depositStr = "无";
         }else {
             depositStr = PriceUtil.getSimplePriceText(deposit);
         }
         return depositStr;
     }

     public void setDepositStr(String depositStr) {
         this.depositStr = depositStr;
     }

     public Integer getTailMoney() {
         if(realPay != null){
             tailMoney = realPay - (deposit == null ? 0 :deposit);
         }
         return tailMoney;
     }

     public void setTailMoney(Integer tailMoney) {
         this.tailMoney = tailMoney;
     }

     public String getTailMoneyStr() {
         if(realPay == null){
             tailMoneyStr = "待发布";
         }
         if(realPay != null){
             tailMoneyStr = PriceUtil.getSimplePriceText(realPay - (deposit == null ? 0 : deposit));
         }
         return tailMoneyStr;
     }

     public void setTailMoneyStr(String tailMoneyStr) {
         this.tailMoneyStr = tailMoneyStr;
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

     public Integer getSalePrice() {
         if(discountPrice != null) {
             return discountPrice;
         }
         return salePrice;
     }

     public void setSalePrice(Integer salePrice) {
         this.salePrice = salePrice;
     }

     public Integer getDiscountPrice() {
         return discountPrice;
     }

     public void setDiscountPrice(Integer discountPrice) {
         this.discountPrice = discountPrice;
     }

     public String getGift() {
         return gift;
     }

     public void setGift(String gift) {
         this.gift = gift;
     }

     public List<CommonGiftDto> getGiftReturn() {
         return giftReturn;
     }

     public void setGiftReturn(List<CommonGiftDto> giftReturn) {
         this.giftReturn = giftReturn;
     }

     public Integer getNum() {
         return num;
     }

     public void setNum(Integer num) {
         this.num = num;
     }

     public Long getPackageId() {
         return packageId;
     }

     public void setPackageId(Long packageId) {
         this.packageId = packageId;
     }
 }
