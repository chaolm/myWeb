package com.dripop.vipcard.alipay.dto;

import java.io.Serializable;

public class MoreInfo implements Serializable {

    private String title;

    private String url;

    private String[]  descs;

    public String[] getDescs() {
        return descs;
    }

    public void setDescs(String[] descs) {
        this.descs = descs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
