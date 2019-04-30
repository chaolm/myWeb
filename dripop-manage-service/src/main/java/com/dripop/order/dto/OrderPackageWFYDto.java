package com.dripop.order.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.dto.CommonGiftDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyou on 2018/3/14.
 */
public class OrderPackageWFYDto implements Serializable {

    private Long detailId;

    private String packageName;

    private Long packageId;

    private Integer salePrice;

    private Integer discountPrice;

    @JSONField(serialize = false)
    private String gift;

    private List<CommonGiftDto> giftReturn;

    private Integer num;

    private List<OrderPackageDetailDto> details;

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

    public Integer getSalePrice() {
        if(discountPrice != null) {
            return discountPrice;
        }
        return salePrice;
    }

    public List<OrderPackageDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<OrderPackageDetailDto> details) {
        this.details = details;
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

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public List<CommonGiftDto> getGiftReturn() {
        return giftReturn;
    }

    public void setGiftReturn(List<CommonGiftDto> giftReturn) {
        this.giftReturn = giftReturn;
    }
}
