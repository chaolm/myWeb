package com.dripop.warehouse.dto.AllWarehouse;

import java.io.Serializable;

public class GetImeisInfo implements Serializable {
    private String imei;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
