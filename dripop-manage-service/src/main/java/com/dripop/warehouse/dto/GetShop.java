package com.dripop.warehouse.dto;

import java.io.Serializable;

public class GetShop implements Serializable {
    private String name;
    private Long  orgId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
