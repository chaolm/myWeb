package com.dripop.promotion.dto;

import com.dripop.entity.TCard;
import java.io.Serializable;
import java.util.List;

public class CardDetailDto implements Serializable{


    private TCard card;

    private List<CardGoodsDto> cardGoodsDtos;

    private List<UserClaimRecordDto> userClaimRecordDtoList;

    private List<CardGoodsDto>  businessGoodsDtos;

    public List<CardGoodsDto> getBusinessGoodsDtos() {
        return businessGoodsDtos;
    }

    public void setBusinessGoodsDtos(List<CardGoodsDto> businessGoodsDtos) {
        this.businessGoodsDtos = businessGoodsDtos;
    }

    private List<Long> customerIds;

    public TCard getCard() {
        return card;
    }

    public void setCard(TCard card) {
        this.card = card;
    }

    public List<CardGoodsDto> getCardGoodsDtos() {
        return cardGoodsDtos;
    }

    public void setCardGoodsDtos(List<CardGoodsDto> cardGoodsDtos) {
        this.cardGoodsDtos = cardGoodsDtos;
    }

    public List<UserClaimRecordDto> getUserClaimRecordDtoList() {
        return userClaimRecordDtoList;
    }

    public void setUserClaimRecordDtoList(List<UserClaimRecordDto> userClaimRecordDtoList) {
        this.userClaimRecordDtoList = userClaimRecordDtoList;
    }

    public List<Long> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }
}
