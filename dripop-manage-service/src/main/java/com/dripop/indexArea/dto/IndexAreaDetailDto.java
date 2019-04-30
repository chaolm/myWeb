package com.dripop.indexArea.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 专区详情实体类
 *
 * @author dq
 * @date 2018/3/12 16:28
 */

public class IndexAreaDetailDto implements Serializable {

    //数据归属渠道  1：PC商城  2：android、IOS、H5
    private Integer dataType;
    /*模板ID*/
    private Long id;
    /*排序字段*/
    private Integer sort;
    /*专区名称*/
    private String areaName;
    /*模板类型*/
    private Integer areaType;
    /*banner*/
    private List<IndexAreaFullDto> bannerList;
    /*区块*/
    private List<BlockDto> blockList;
    /*编辑模式*/
    private Integer editType;

    //品牌馆配置
    //
    private String imgUrl;
    private String content;
    private String englishName;

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public List<IndexAreaFullDto> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<IndexAreaFullDto> bannerList) {
        this.bannerList = bannerList;
    }

    public List<BlockDto> getBlockList() {
        return blockList;
    }

    public void setBlockList(List<BlockDto> blockList) {
        this.blockList = blockList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }

    public Integer getEditType() {
        return editType;
    }

    public void setEditType(Integer editType) {
        this.editType = editType;
    }
}

