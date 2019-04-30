package com.dripop.warehouse.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class GetAllInWarehouseInfo implements Serializable {

    private  String imgUrls;
    private  String goodsNames;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private  Long id;
    //入库门店名称
    private String name;

    public String getName() {

        if(name == null ){
            name = "无";
        }return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public String getGoodsNames() {
        return goodsNames;
    }

    public void setGoodsNames(String goodsNames) {
        this.goodsNames = goodsNames;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }



    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
