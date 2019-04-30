package com.dripop.helpFeedback.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 帮助问题全属性
 *
 * @author dq
 * @date 2018/5/31 15:04
 */

public class HelpAllDto implements Serializable{
    /*内容ID*/
    private Long id;

    /*主标题ID*/
    private Long mainId;

    /*主标题*/
    private String mainTitle;

    /*副标题*/
    private String subtitle;

    /*内容*/
    private String content;

    /*修改时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /*问题已解决数*/
    private Integer resolved;

    /*问题未解决数*/
    private Integer unsolved;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getResolved() {
        return resolved;
    }

    public void setResolved(Integer resolved) {
        this.resolved = resolved;
    }

    public Integer getUnsolved() {
        return unsolved;
    }

    public void setUnsolved(Integer unsolved) {
        this.unsolved = unsolved;
    }
}
