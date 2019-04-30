package com.dripop.vipcard.wechat.dto;

import java.io.Serializable;

public class ActionInfoListDto implements Serializable {
    private MultipleCardDto multiple_card;

    public MultipleCardDto getMultiple_card() {
        return multiple_card;
    }

    public void setMultiple_card(MultipleCardDto multiple_card) {
        this.multiple_card = multiple_card;
    }
}
