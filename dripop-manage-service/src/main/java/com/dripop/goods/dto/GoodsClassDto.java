package com.dripop.goods.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dripop.util.LinkUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类实体类
 *
 * @author dq
 * @date 2018/3/12 19:44
 */

public class GoodsClassDto implements Serializable {

    private Long id;

    @JSONField(serialize = false)
    private Long classId;

    private String name;

    private String imgUrl;

    private String linkUrl;

    private Integer sort;

    private Integer linkType;

    @JSONField(serialize = false)
    private String refVal;

    private Long parentId;

    @JSONField(serialize = false)
    private String fullPath;

    private List<com.dripop.goods.dto.GoodsClassDto> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public List<com.dripop.goods.dto.GoodsClassDto> getItems() {
        return items;
    }

    public void setItems(List<com.dripop.goods.dto.GoodsClassDto> items) {
        this.items = items;
    }

    public String getLinkUrl() {
        return refVal;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}