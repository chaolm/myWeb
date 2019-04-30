package com.dripop.goodspackage.dto;

import java.io.Serializable;

/**
 * Created by liyou on 2017/8/3.
 */
public class ComboGoodsDto implements Serializable {

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private String imgUrl;

    /**
     * 赠品信息
     */
    private String gift;

    /**
     * 商品上架id
     */
    private Long onlineId;

    /**
     * 已选文字说明
     */
    private String selectText;

    /**
     * 是否为实物 1 实物 2 非实物
     */
    private Integer isPractice;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public Long getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Long onlineId) {
        this.onlineId = onlineId;
    }

    public String getSelectText() {
        return selectText;
    }

    public void setSelectText(String selectText) {
        this.selectText = selectText;
    }

    public Integer getIsPractice() {
        return isPractice;
    }

    public void setIsPractice(Integer isPractice) {
        this.isPractice = isPractice;
    }
}
