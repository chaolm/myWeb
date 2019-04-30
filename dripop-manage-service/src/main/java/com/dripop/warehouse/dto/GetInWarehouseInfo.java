package com.dripop.warehouse.dto;

import java.io.Serializable;
import java.util.List;

public class GetInWarehouseInfo implements Serializable {

    private String supplier;

    private List<GetInWarehouseGoodsInfo> goods;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<GetInWarehouseGoodsInfo> getGoods() {
        return goods;
    }

    public void setGoods(List<GetInWarehouseGoodsInfo> goods) {
        this.goods = goods;
    }
}
