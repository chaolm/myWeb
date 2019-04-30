package com.dripop.order.dto;

import com.alibaba.fastjson.TypeReference;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.StringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyou on 2018/3/14.
 */
public class OrderPackageDto implements Serializable {

    private Long detailId;

    private String packageName;

    private Long packageId;

    private String salePrice;

    private String discountPrice;

    private String gift;

    private List<GiftDto> giftReturn;

    private String giftName;

    private Integer giftNum;

    private Integer num;

    private Integer totalPrice;

    private Integer yhPrice;

    private Integer discountFee;

    private Integer totalFee;

    private Integer discountTotalFee;

    public Integer getYhPrice() {
        if(yhPrice==null){
            return 0;
        }
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

    private List<OrderPackageDetailDto> details;

    public String getDiscountPrice() { return discountPrice; }

    public void setDiscountPrice(String discountPrice) { this.discountPrice = discountPrice; }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
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

    public List<OrderPackageDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<OrderPackageDetailDto> details) {
        this.details = details;
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

    public Integer getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Integer discountFee) {
        this.discountFee = discountFee;
    }
}
