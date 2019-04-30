package com.dripop.order.dto;

import com.dripop.entity.TOrderOper;

import java.io.Serializable;
import java.util.List;

/**
 * @author dq
 * @date 2018/3/24 10:17
 */

public class DeliveryGoodsDto implements Serializable {
    /*订单详情ID*/
    private Long orderDetailId;
    /*套装名称*/
    private String packageName;
    /*剩余库存*/
    private Integer stock;
    /*商品件数*/
    private Integer num;
    /*已发货数量*/
    private Integer imeiNum;
    /*发货串号信息*/
    private List<TOrderOper> imeiList;
    /*商品信息*/
    private List<GoodsImeiDto> goodsImeiDtos;

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public List<GoodsImeiDto> getGoodsImeiDtos() {
        return goodsImeiDtos;
    }

    public void setGoodsImeiDtos(List<GoodsImeiDto> goodsImeiDtos) {

        this.goodsImeiDtos = goodsImeiDtos;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getImeiNum() {
        return imeiNum;
    }

    public void setImeiNum(Integer imeiNum) {
        this.imeiNum = imeiNum;
    }

    public List<TOrderOper> getImeiList() {
        return imeiList;
    }

    public void setImeiList(List<TOrderOper> imeiList) {
        this.imeiList = imeiList;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
