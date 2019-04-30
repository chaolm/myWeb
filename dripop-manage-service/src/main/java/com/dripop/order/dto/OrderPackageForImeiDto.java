package com.dripop.order.dto;

import java.util.List;

/**
 * 优惠套餐
 *
 * @author dq
 * @date 2018/3/22 19:51
 */

public class OrderPackageForImeiDto extends OrderDetailDto {

    /*组合套餐订单名称*/
    private String packageName;
    /*套餐详情*/
    private List<OrderPackageDetailForImeiDto> details;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<OrderPackageDetailForImeiDto> getDetails() {
        return details;
    }

    public void setDetails(List<OrderPackageDetailForImeiDto> details) {
        this.details = details;
    }

    @Override
    public List<GiftForImeiDto> getGiftReturn() {
        return super.getGiftReturn();
    }

    @Override
    public void setGiftReturn(List<GiftForImeiDto> giftReturn) {
        super.setGiftReturn(giftReturn);
    }

    @Override
    public String getDiscountPrice() {
        return super.getDiscountPrice();
    }

    @Override
    public void setDiscountPrice(String discountPrice) {
        super.setDiscountPrice(discountPrice);
    }

    @Override
    public String getCouponPrice() {
        return super.getCouponPrice();
    }

    @Override
    public void setCouponPrice(String couponPrice) {
        super.setCouponPrice(couponPrice);
    }

    @Override
    public String getGift() {
        return super.getGift();
    }

    @Override
    public void setGift(String gift) {
        super.setGift(gift);
    }

    @Override
    public Long getPackageId() {
        return super.getPackageId();
    }

    @Override
    public void setPackageId(Long packageId) {
        super.setPackageId(packageId);
    }

    @Override
    public Long getDetailId() {
        return super.getDetailId();
    }

    @Override
    public void setDetailId(Long detailId) {
        super.setDetailId(detailId);
    }

    @Override
    public String getImgUrl() {
        return super.getImgUrl();
    }

    @Override
    public void setImgUrl(String imgUrl) {
        super.setImgUrl(imgUrl);
    }

    @Override
    public String getGoodsName() {
        return super.getGoodsName();
    }

    @Override
    public void setGoodsName(String goodsName) {
        super.setGoodsName(goodsName);
    }

    @Override
    public String getSalePrice() {
        return super.getSalePrice();
    }

    @Override
    public void setSalePrice(String salePrice) {
        super.setSalePrice(salePrice);
    }

    @Override
    public Integer getNum() {
        return super.getNum();
    }

    @Override
    public void setNum(Integer num) {
        super.setNum(num);
    }

    @Override
    public Long getOnlineId() {
        return super.getOnlineId();
    }

    @Override
    public void setOnlineId(Long onlineId) {
        super.setOnlineId(onlineId);
    }

}
