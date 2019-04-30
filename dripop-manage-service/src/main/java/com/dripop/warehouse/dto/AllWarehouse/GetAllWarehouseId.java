package com.dripop.warehouse.dto.AllWarehouse;

import java.io.Serializable;

public class GetAllWarehouseId implements Serializable {

    private long orgId;

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }
}
