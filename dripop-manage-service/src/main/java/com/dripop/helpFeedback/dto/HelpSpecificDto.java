package com.dripop.helpFeedback.dto;

import java.io.Serializable;

/**
 * 帮助内容
 *
 * @author dq
 * @date 2018/5/31 14:54
 */

public class HelpSpecificDto implements Serializable {

    /*内容ID*/
    private Long id;

    /*主标题ID*/
    private Long mainId;

    /*主标题*/
    private String mainTitle;

    /*副标题*/
    private String subtitle;

    /*帮助内容*/
    private String content;

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
}
