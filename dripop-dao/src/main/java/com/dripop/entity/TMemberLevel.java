package com.dripop.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by liyou on 2017/12/15.
 */
@javax.persistence.Entity
@Table(name = "t_member_level")
public class TMemberLevel implements Serializable {

    @Id
    private Long id;

    private String name;

    @Column(name = "growth_value_start")
    private Integer growthValueStart;

    @Column(name = "growth_value_end")
    private Integer growthValueEnd;

    private String color;

    private String remark;

    @Column(name = "is_used")
    private Integer isUsed;

    private Integer grade;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "background_url")
    private String backgroundUrl;

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

    public Integer getGrowthValueStart() {
        return growthValueStart;
    }

    public void setGrowthValueStart(Integer growthValueStart) {
        this.growthValueStart = growthValueStart;
    }

    public Integer getGrowthValueEnd() {
        return growthValueEnd;
    }

    public void setGrowthValueEnd(Integer growthValueEnd) {
        this.growthValueEnd = growthValueEnd;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }
}
