package com.dripop.goodspackage.dto;

import java.io.Serializable;

/**
 * Created by liyou on 17-12-6.
 */
public class OpPackageSaleSearchDto implements Serializable {

    private Long onlineId;

    private String typeName;

    private String brandName;

    private String fullName;

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
