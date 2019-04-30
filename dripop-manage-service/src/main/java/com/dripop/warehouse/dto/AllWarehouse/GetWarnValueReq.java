package com.dripop.warehouse.dto.AllWarehouse;

import java.io.Serializable;
import java.util.List;

public class GetWarnValueReq implements Serializable{
    private List<IdAndWarnValue> list;

    public List<IdAndWarnValue> getList() {
        return list;
    }

    public void setList(List<IdAndWarnValue> list) {
        this.list = list;
    }
}
