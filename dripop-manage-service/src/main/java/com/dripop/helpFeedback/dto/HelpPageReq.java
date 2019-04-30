package com.dripop.helpFeedback.dto;

import java.io.Serializable;

/**
 * 帮助列表请求
 *
 * @author dq
 * @date 2018/5/31 15:13
 */

public class HelpPageReq implements Serializable {

    /*帮助应用渠道 1 app 2 pc*/
    private Integer helpChannel;
    /*副标题*/
    private String subtitle;
    /*主标题ID*/
    private Long mainId;

    public Integer getHelpChannel() {
        return helpChannel;
    }

    public void setHelpChannel(Integer helpChannel) {
        this.helpChannel = helpChannel;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }
}