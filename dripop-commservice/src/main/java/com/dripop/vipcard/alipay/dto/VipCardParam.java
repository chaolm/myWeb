package com.dripop.vipcard.alipay.dto;

import java.io.Serializable;
import java.util.List;

public class VipCardParam implements Serializable {

    private String logoId;

    private String logoUrl;

    private String backgroundId;

    private String backgroundUrl;

    private String templateId;

    private List<ColumnInfoList> columnInfoList;

    public List<ColumnInfoList> getColumnInfoList() {
        return columnInfoList;
    }

    public void setColumnInfoList(List<ColumnInfoList> columnInfoList) {
        this.columnInfoList = columnInfoList;
    }

    //领卡投放链接
    private String receiveLinkUrl;

    public String getLogoId() {
        return logoId;
    }

    public void setLogoId(String logoId) {
        this.logoId = logoId;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(String backgroundId) {
        this.backgroundId = backgroundId;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getReceiveLinkUrl() {
        return receiveLinkUrl;
    }

    public void setReceiveLinkUrl(String receiveLinkUrl) {
        this.receiveLinkUrl = receiveLinkUrl;
    }
}
