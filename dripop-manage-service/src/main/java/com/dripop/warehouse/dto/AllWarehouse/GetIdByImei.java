package com.dripop.warehouse.dto.AllWarehouse;

import java.io.Serializable;

public class GetIdByImei implements Serializable{
    //每个串号对应的id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
