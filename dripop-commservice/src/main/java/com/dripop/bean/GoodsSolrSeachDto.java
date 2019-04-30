package com.dripop.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * solr 商品查询
 *
 * @author dq
 * @date 2018/6/7 17:18
 */

public class GoodsSolrSeachDto implements Serializable{
    /*商品上架ID*/
    private Long onlineId;
    /*商品名称*/
    private String goodsName;
    /*关键字*/
    private String keyword;
    /*品牌ID*/
    private Long brandId;
    /*品牌名*/
    private String brandName;
    /*类型ID*/
    private Long typeId;
    /*类型名*/
    private String typeName;
    /*卖点*/
    private String remark;
    /*图片地址*/
    private String imgUrl;
    /*售价*/
    private Integer salePrice;
    /**/
    private Long reviewNum;
    /**/
    private Long praiseRate;
    /*卡券类型*/
    private Integer cardType;
    /*卡券优惠值*/
    private BigDecimal cardVal;
    /*优惠门槛*/
    private Integer minUsePrice;
    /*商品类型*/
    private Integer goodsSellType;
    /*定金*/
    private Integer deposit;
    /*预售价*/
    private Integer presellMoney;
    /*预售开始时间*/
    private Date presellStartTime;
    /*预售结束时间*/
    private Date presellEndTime;
    /*发布时间*/
    private Date publishTime;
    /*库存*/
    private Integer stock;
    /*销量*/
    private Integer saleNum;
    /*等级*/
    private Integer goodsLevel;
    private String fullPath;
    /*参数*/
    private String paramVal;

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Long getReviewNum() {
        return reviewNum;
    }

    public void setReviewNum(Long reviewNum) {
        this.reviewNum = reviewNum;
    }

    public Long getPraiseRate() {
        return praiseRate;
    }

    public void setPraiseRate(Long praiseRate) {
        this.praiseRate = praiseRate;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public BigDecimal getCardVal() {
        return cardVal;
    }

    public void setCardVal(BigDecimal cardVal) {
        this.cardVal = cardVal;
    }

    public Integer getMinUsePrice() {
        return minUsePrice;
    }

    public void setMinUsePrice(Integer minUsePrice) {
        this.minUsePrice = minUsePrice;
    }

    public Integer getGoodsSellType() {
        return goodsSellType;
    }

    public void setGoodsSellType(Integer goodsSellType) {
        this.goodsSellType = goodsSellType;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Integer getPresellMoney() {
        return presellMoney;
    }

    public void setPresellMoney(Integer presellMoney) {
        this.presellMoney = presellMoney;
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

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Integer getGoodsLevel() {
        return goodsLevel;
    }

    public void setGoodsLevel(Integer goodsLevel) {
        this.goodsLevel = goodsLevel;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getParamVal() {
        return paramVal;
    }

    public void setParamVal(String paramVal) {
        this.paramVal = paramVal;
    }
}
