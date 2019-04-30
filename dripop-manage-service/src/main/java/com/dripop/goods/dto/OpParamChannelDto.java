package com.dripop.goods.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyou on 2017/6/12.
 */
public class OpParamChannelDto implements Serializable {
    private String channelName;
    private List<OpGoodsParamDetailDto> goodsParams;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<OpGoodsParamDetailDto> getGoodsParams() {
        return goodsParams;
    }

    public void setGoodsParams(List<OpGoodsParamDetailDto> goodsParams) {
        this.goodsParams = goodsParams;
    }
}
