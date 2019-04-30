package com.dripop.vipcard.wechat.dto;

import java.io.Serializable;

public class QrcodeDto implements Serializable {

       private String card_id;

       private String code;

       private String openid;

       private Boolean is_unique_code;

       private String outer_str;

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Boolean getIs_unique_code() {
        return is_unique_code;
    }

    public void setIs_unique_code(Boolean is_unique_code) {
        this.is_unique_code = is_unique_code;
    }

    public String getOuter_str() {
        return outer_str;
    }

    public void setOuter_str(String outer_str) {
        this.outer_str = outer_str;
    }
}
