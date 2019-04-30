package com.dripop.goods.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by liyou on 2017/6/12.
 */
public class OpGoodsParamFullDetailDto implements Serializable {

    @JSONField(serialize = false)
    private Long channelId;
    private String channelName;
    private Long paramId;
    private String paramName;
    private String paramVal;
    private String goodsParamVal;
    private Integer isEnum;
    private Integer isMult;
    private Integer isRequired;
    private Integer isSearch;
    private Integer isDetailSearch;
    private Integer isDetailShow;
    private Integer prodDetailShow;
    private Integer detailParamShow;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Integer getIsEnum() {
        return isEnum;
    }

    public void setIsEnum(Integer isEnum) {
        this.isEnum = isEnum;
    }

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }

    public String getParamVal() {
        return paramVal;
    }

    public void setParamVal(String paramVal) {
        this.paramVal = paramVal;
    }

    public Integer getIsMult() {
        return isMult;
    }

    public void setIsMult(Integer isMult) {
        this.isMult = isMult;
    }

    public String getGoodsParamVal() {
        return goodsParamVal;
    }

    public void setGoodsParamVal(String goodsParamVal) {
        this.goodsParamVal = goodsParamVal;
    }

    public Integer getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(Integer isSearch) {
        this.isSearch = isSearch;
    }

    public Integer getIsDetailSearch() {
        return isDetailSearch;
    }

    public void setIsDetailSearch(Integer isDetailSearch) {
        this.isDetailSearch = isDetailSearch;
    }

    public Integer getProdDetailShow() {
        return prodDetailShow;
    }

    public void setProdDetailShow(Integer prodDetailShow) {
        this.prodDetailShow = prodDetailShow;
    }

    public Integer getIsDetailShow() {
        return isDetailShow;
    }

    public void setIsDetailShow(Integer isDetailShow) {
        this.isDetailShow = isDetailShow;
    }

    public Integer getDetailParamShow() {
        return detailParamShow;
    }

    public void setDetailParamShow(Integer detailParamShow) {
        this.detailParamShow = detailParamShow;
    }
}
