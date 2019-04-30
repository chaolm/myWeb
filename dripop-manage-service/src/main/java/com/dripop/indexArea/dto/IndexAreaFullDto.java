package com.dripop.indexArea.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 首页专区 全属性
 *
 * @author dq
 * @date 2018/3/12 16:35
 */

public class IndexAreaFullDto implements Serializable {
    /*模板ID*/
    private Long id;
    /*上级id*/
    @JSONField(serialize = false)
    private Long parentId;
    /*模板名称*/
    private String name;
    /*图片地址*/
    private String imgUrl;
    /*关联类型 1 无 2 商品 3 聚合页*/
    private Integer linkType;
    /*关联id  或者跳转链接*/
    private String refVal;
    /*排序字段*/
    private Integer sort;
    /*关键词*/
    private String keyword;
    @JSONField(serialize = false)
    private Integer type;
    /*商品名称*/
    private String goodsName;

    private Integer salePrice;
    /*卡券类型*/
    private Integer cardType;
    /*卡券面额*/
    private BigDecimal cardVal;
    /*满多少元使用值*/
    private Integer minUsePrice;
    /*商品栏名称价格隐藏字段 0不隐藏 1隐藏*/
    private Integer hide;

    private Long jumpCategory;
    private String hotSearch;
    private String englishName;
    private Long onlineId;

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Long getJumpCategory() {
        return jumpCategory;
    }

    public void setJumpCategory(Long jumpCategory) {
        this.jumpCategory = jumpCategory;
    }

    public String getHotSearch() {
        return hotSearch;
    }

    public void setHotSearch(String hotSearch) {
        this.hotSearch = hotSearch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public String getRefVal() {
        return refVal;
    }

    public void setRefVal(String refVal) {
        this.refVal = refVal;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
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

    public Integer getHide() {
        return hide;
    }

    public void setHide(Integer hide) {
        this.hide = hide;
    }
}
