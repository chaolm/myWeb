package com.dripop.vipcard.wechat.dto;

import java.io.Serializable;

public class SwipeCardDto implements Serializable {

    private Boolean is_swipe_card=true;

    public Boolean getIs_swipe_card() {
        return is_swipe_card;
    }

    public void setIs_swipe_card(Boolean is_swipe_card) {
        this.is_swipe_card = is_swipe_card;
    }


}
