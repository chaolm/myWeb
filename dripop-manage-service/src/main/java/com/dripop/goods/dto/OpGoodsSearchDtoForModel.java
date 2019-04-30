package com.dripop.goods.dto;

import java.io.Serializable;
import java.util.List;

public class OpGoodsSearchDtoForModel implements Serializable{

    private String typeName;

    private String brandName;

    private Long typeId;

    private Long brandId;

    private String model;

    private Long modelId;

    public Long getModelId() {
        return modelId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    private List<OpGoodsSkuDto> skuList;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<OpGoodsSkuDto> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<OpGoodsSkuDto> skuList) {
        this.skuList = skuList;
    }

}
