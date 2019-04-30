package com.dripop.helpFeedback.dto;

import com.bean.ApplicationType;

import java.io.Serializable;

/**
 * 主标题
 *
 * @author dq
 * @date 2018/5/31 13:57
 */

public class HelpMainDto implements Serializable{
    /*主标题ID*/
    private Long mainId;

    /*主标题*/
    private String mainTitle;

    /*展示位置类型*/
    private Integer applicationType;

    /*备注*/
    private String remark;

    private String applicationTypeStr;

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

    public Integer getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(Integer applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationTypeStr() {
        return ApplicationType.getName(applicationType);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
