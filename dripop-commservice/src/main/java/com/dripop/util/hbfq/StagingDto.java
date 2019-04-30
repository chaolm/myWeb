package com.dripop.util.hbfq;

import java.util.List;

/**
 * Created by junhu on 2017/4/11.
 */
public class StagingDto {
    //是否支持花呗分期
    private Integer isUseStaging;
    //最高支持免息分期期数
    private Integer stagingNum;
    //花呗分期描述信息
    private String stagingDesc;
    //分期列表
    private List<StagingItemDto> stagingItemList;
    //是否多商品免息
    private Integer stagingTip;
    public Integer getIsUseStaging() {
        return isUseStaging;
    }

    public void setIsUseStaging(Integer isUseStaging) {
        this.isUseStaging = isUseStaging;
    }

    public Integer getStagingNum() {
        return stagingNum;
    }

    public void setStagingNum(Integer stagingNum) {
        this.stagingNum = stagingNum;
    }

    public String getStagingDesc() {
        return stagingDesc;
    }

    public void setStagingDesc(String stagingDesc) {
        this.stagingDesc = stagingDesc;
    }

    public List<StagingItemDto> getStagingItemList() {
        return stagingItemList;
    }

    public void setStagingItemList(List<StagingItemDto> stagingItemList) {
        this.stagingItemList = stagingItemList;
    }

    public Integer getStagingTip() {
        return stagingTip;
    }

    public void setStagingTip(Integer stagingTip) {
        this.stagingTip = stagingTip;
    }
}
