package com.dripop.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2017/6/14.
 */
@javax.persistence.Entity
@Table(name = "t_param")
public class TParam implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "channel_id")
    private Long channelId;

    @Column(name = "is_search")
    private Integer isSearch;

    @Column(name = "is_enum")
    private Integer isEnum;

    @Column(name = "is_mult")
    private Integer isMult;

    @Column(name = "is_detail_search")
    private Integer isDetailSearch;

    @Column(name = "prod_detail_show")
    private Integer prodDetailShow;

    @Column(name = "detail_param_show")
    private Integer detailParamShow;

    @Column(name = "is_required")
    private Integer isRequired;

    private Integer sort;

    private Long creator;

    @Column(name = "create_time")
    private Date createTime;

    private Long updater;

    @Column(name = "update_time")
    private Date updateTime;

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

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Integer getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(Integer isSearch) {
        this.isSearch = isSearch;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdater() {
        return updater;
    }

    public void setUpdater(Long updater) {
        this.updater = updater;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsMult() {
        return isMult;
    }

    public void setIsMult(Integer isMult) {
        this.isMult = isMult;
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

    public Integer getDetailParamShow() {
        return detailParamShow;
    }

    public void setDetailParamShow(Integer detailParamShow) {
        this.detailParamShow = detailParamShow;
    }
}
