package com.dripop.promotion.dto;

import java.io.Serializable;
import java.util.List;

public class GiftDetailDto implements Serializable {

        private GiftPromotionDto giftPromotionDto;

        private List<GiftGoodsDto> giftGoodsDtos;

        private List<GiftMainGoodsDto> giftMainGoodsDtos;

    public GiftPromotionDto getGiftPromotionDto() {
        return giftPromotionDto;
    }

    public void setGiftPromotionDto(GiftPromotionDto giftPromotionDto) {
        this.giftPromotionDto = giftPromotionDto;
    }

    public List<GiftGoodsDto> getGiftGoodsDtos() {
        return giftGoodsDtos;
    }

    public void setGiftGoodsDtos(List<GiftGoodsDto> giftGoodsDtos) {
        this.giftGoodsDtos = giftGoodsDtos;
    }

    public List<GiftMainGoodsDto> getGiftMainGoodsDtos() {
        return giftMainGoodsDtos;
    }

    public void setGiftMainGoodsDtos(List<GiftMainGoodsDto> giftMainGoodsDtos) {
        this.giftMainGoodsDtos = giftMainGoodsDtos;
    }
}
