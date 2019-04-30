package com.dripop.sys.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台消息管理实体类
 *
 * @author dq
 * @date 2018/3/19 10:29
 */

public class SysmsgManageDto implements Serializable {
    /*id*/
    private Long id;

    /*标题*/
    private String title;

    /*副标题*/
    private String content;

    /*开始时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /*结束时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    /*跳转链接*/
    private String link;

    /*撤销字段  1 未撤销 2 撤销*/
    @JSONField(serialize = false)
    private Integer isRepeal;

    /*操作类型  1 删除 2 撤销 3 已撤销*/
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getIsRepeal() {
        return isRepeal;
    }

    public void setIsRepeal(Integer isRepeal) {
        this.isRepeal = isRepeal;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
