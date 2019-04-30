package com.dripop.vipcard.wechat.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class PayInfoDto implements Serializable {
    SwipeCardDto swipeCardDto=new SwipeCardDto();
    private JSON swipe_card=(JSON)JSON.toJSON(swipeCardDto);

    public JSON getSwipe_card() {
        return swipe_card;
    }

    public void setSwipe_card(JSON swipe_card) {
        this.swipe_card = swipe_card;
    }
}
