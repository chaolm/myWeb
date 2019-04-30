package com.dripop.order.dto;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.util.PriceUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 类名及用途
 *
 * @author dq
 * @date 2018/4/16 9:37
 */

public class OrderDetailOPDto  implements Serializable {

    private Long detailId;

    private String imgUrl;

    private String goodsName;

    private Integer salePrice;

    private Integer discountPrice;

    @JSONField(serialize = false)
    private String gift;

    private List<GiftDto> giftReturn;

    private Integer num;

    private Integer numForAfterSale;

    private Long packageId;

    private Integer discountFee;

    private Integer totalFee;

    private Integer discountTotalFee;

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
    //定金可抵用金额
    private Integer depositDiscountAmount;
    //预售价格
    private Integer presellMoney;

    private Integer refundType;
    private String refundTypeStr;
    private Integer orderType;
    private String salePriceStr;
    private Integer goodsSellType;

    public Integer getGoodsSellType() {
        return goodsSellType;
    }

    public void setGoodsSellType(Integer goodsSellType) {
        this.goodsSellType = goodsSellType;
    }

    public String getSalePriceStr() {
        if(salePrice == null){
            salePriceStr = "待发布";
        }
        if(salePrice != null){
            salePriceStr = PriceUtil.getPriceText(salePrice);
        }
        return salePriceStr;
    }

    public void setSalePriceStr(String salePriceStr) {
        this.salePriceStr = salePriceStr;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public String getRefundTypeStr() {
        return refundTypeStr;
    }

    public void setRefundTypeStr(String refundTypeStr) {
        this.refundTypeStr = refundTypeStr;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getPresellMoney() {
        return presellMoney;
    }

    public void setPresellMoney(Integer presellMoney) {
        this.presellMoney = presellMoney;
    }

    public Integer getDepositDiscountAmount() {
        return depositDiscountAmount;
    }

    public void setDepositDiscountAmount(Integer depositDiscountAmount) {
        this.depositDiscountAmount = depositDiscountAmount;
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
        return tailMoney;
    }

    public void setTailMoney(Integer tailMoney) {
        this.tailMoney = tailMoney;
    }

    public String getTailMoneyStr() {
        if(salePrice == null ){
            tailMoneyStr = "待发布";
        }else if(depositDiscountAmount != null){
            tailMoneyStr = PriceUtil.getSimplePriceText(salePrice - depositDiscountAmount);
        }else if(deposit != null){
            tailMoneyStr = PriceUtil.getSimplePriceText(salePrice - deposit);
        }
        return tailMoneyStr;
    }

    public void setTailMoneyStr(String tailMoneyStr) {
        this.tailMoneyStr = tailMoneyStr;
    }

    public Integer getNumForAfterSale() {
        return numForAfterSale;
    }

    public void setNumForAfterSale(Integer numForAfterSale) {
        this.numForAfterSale = numForAfterSale;
    }

    public Integer getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Integer discountFee) {
        this.discountFee = discountFee;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getDiscountTotalFee() {
        return discountTotalFee;
    }

    public void setDiscountTotalFee(Integer discountTotalFee) {
        this.discountTotalFee = discountTotalFee;
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

    public List<GiftDto> getGiftReturn() {
        if(StringUtil.isBlank(gift)) {
            return null;
        }
        return JsonUtil.fromJson(gift, new TypeReference<List<GiftDto>>() {});
    }

    public void setGiftReturn(List<GiftDto> giftReturn) {
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
