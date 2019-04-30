package com.dripop.vipcard.wechat.dto;

import java.io.Serializable;

public class SkuDto implements Serializable {
    private  Integer quantity=10000;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
