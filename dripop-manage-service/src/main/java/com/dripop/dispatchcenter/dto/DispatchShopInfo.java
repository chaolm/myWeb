package com.dripop.dispatchcenter.dto;

import java.io.Serializable;

public class DispatchShopInfo implements Serializable{
    //商品id
    private Long spuId;
    //商品名称
    private String fullName;
    //商品数量
    private Integer num;
    //库存
    private String stock;
    //商品id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
