package com.dripop.vipcard.wechat.dto;

import java.io.Serializable;

public class DataInfoDto implements Serializable {
     private String type="DATE_TYPE_PERMANENT";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
