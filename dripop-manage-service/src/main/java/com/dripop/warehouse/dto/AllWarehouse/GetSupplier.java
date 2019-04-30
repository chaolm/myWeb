package com.dripop.warehouse.dto.AllWarehouse;

import java.io.Serializable;

public class GetSupplier implements Serializable {
    private String supplier;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
