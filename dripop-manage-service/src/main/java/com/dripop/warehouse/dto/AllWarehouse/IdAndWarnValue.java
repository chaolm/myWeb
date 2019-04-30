package com.dripop.warehouse.dto.AllWarehouse;

import java.io.Serializable;

public class IdAndWarnValue implements Serializable {
    //商品id
    private Long id;
    //报警值
    private Long warnValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarnValue() {
        return warnValue;
    }

    public void setWarnValue(Long warnValue) {
        this.warnValue = warnValue;
    }
}
