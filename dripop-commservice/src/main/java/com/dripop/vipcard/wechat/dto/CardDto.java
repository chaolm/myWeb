package com.dripop.vipcard.wechat.dto;
import java.io.Serializable;

public class CardDto implements Serializable {

    private String  card_type= "MEMBER_CARD";

   private MemberCardDto member_card;

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public MemberCardDto getMember_card() {
        return member_card;
    }

    public void setMember_card(MemberCardDto member_card) {
        this.member_card = member_card;
    }
}
