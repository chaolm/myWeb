package com.dripop.goods.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyou on 2017/6/12.
 */
public class OpGoodsParamDetailDto implements Serializable {
    private Long paramId;
    private String paramName;
    private String goodsParamVal;
    private Integer sort;
    private Integer isEnum;
    private Integer isMult;
    private Integer isRequired;

    private List<OpParamDataDto> paramDatas;

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

    public String getGoodsParamVal() {
        return goodsParamVal;
    }

    public void setGoodsParamVal(String goodsParamVal) {
        this.goodsParamVal = goodsParamVal;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public List<OpParamDataDto> getParamDatas() {
        return paramDatas;
    }

    public void setParamDatas(List<OpParamDataDto> paramDatas) {
        this.paramDatas = paramDatas;
    }

    public Integer getIsMult() {
        return isMult;
    }

    public void setIsMult(Integer isMult) {
        this.isMult = isMult;
    }
}
