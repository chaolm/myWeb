package com.dripop.indexArea.dto;


import java.io.Serializable;


/**
 * 专区列表实体类
 *
 * @author dq
 * @date 2018/3/12 16:20
 */

public class IndexAreaDto implements Serializable {
    /*专区ID*/
    private Long areaId;
    /*专区名*/
    private String name;
    /*专区排序*/
    private Integer sort;

    private String content;

    private String imgUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
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
}

