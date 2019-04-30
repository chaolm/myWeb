package com.dripop.dto;

import java.io.Serializable;

public class PriceRangeDto implements Serializable {
    private String price;

    private String priceName;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }
}
