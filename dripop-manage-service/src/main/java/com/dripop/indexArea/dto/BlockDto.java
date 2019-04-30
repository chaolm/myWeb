package com.dripop.indexArea.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 区块实体类
 *
 * @author dq
 * @date 2018/3/12 16:29
 */

public class BlockDto implements Serializable {
    /*模块名*/
    private String name;
    //跳转类目
    private Long jumpCategory;
    //热搜项
    private String hotSearch;
    //模块英文名称
    private String englishName;
    /*关键字*/
    private  String keyword;
    /*排序字段*/
    private Integer sort;
    /*商品集*/
    private List<IndexAreaFullDto> goodsList;
    /*通栏集*/
    private List<IndexAreaFullDto> channelList;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<IndexAreaFullDto> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<IndexAreaFullDto> goodsList) {
        this.goodsList = goodsList;
    }

    public List<IndexAreaFullDto> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<IndexAreaFullDto> channelList) {
        this.channelList = channelList;
    }
}
