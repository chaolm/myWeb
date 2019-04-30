package com.dripop.vipcard.wechat.dto;

import java.io.Serializable;

public class ActionInfoSingDto implements Serializable {

    private QrcodeDto card;

    public QrcodeDto getCard() {
        return card;
    }

    public void setCard(QrcodeDto card) {
        this.card = card;
    }

}
