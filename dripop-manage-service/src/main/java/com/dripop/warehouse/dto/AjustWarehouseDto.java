package com.dripop.warehouse.dto;

import java.io.Serializable;
import java.util.List;

public class AjustWarehouseDto implements Serializable {

    /**
     * 串号恢复，多个串号用,号拼接
     */
    private String imeis;

    /**
     * 串号更换集合
     */
    private List<AjustWarehouseChangeDto> changeImeis;

    public String getImeis() {
        return imeis;
    }

    public void setImeis(String imeis) {
        this.imeis = imeis;
    }

    public List<AjustWarehouseChangeDto> getChangeImeis() {
        return changeImeis;
    }

    public void setChangeImeis(List<AjustWarehouseChangeDto> changeImeis) {
        this.changeImeis = changeImeis;
    }
}
