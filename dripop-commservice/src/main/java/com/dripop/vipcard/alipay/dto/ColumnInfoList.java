package com.dripop.vipcard.alipay.dto;

import java.io.Serializable;

public class ColumnInfoList implements Serializable{

    private String code;

    private String  operate_type;

    private String title;

    private  String platForm;

    private String value;

    private MoreInfo more_info;

    public String getPlatForm() {
        return platForm;
    }

    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOperate_type() {
        return operate_type;
    }

    public void setOperate_type(String operate_type) {
        this.operate_type = operate_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MoreInfo getMore_info() {
        return more_info;
    }

    public void setMore_info(MoreInfo more_info) {
        this.more_info = more_info;
    }
}
