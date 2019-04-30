package com.dripop.vipcard.service.impl;

import com.dripop.vipcard.service.WechatVipCardService;
import com.dripop.vipcard.wechat.WechatVipCardUtil;
import com.dripop.vipcard.wechat.dto.ActionInfoSingDto;
import com.dripop.vipcard.wechat.dto.BaseInfoDto;
import com.dripop.vipcard.wechat.dto.CardDto;
import com.dripop.vipcard.wechat.dto.MemberCardDto;
import org.springframework.stereotype.Service;


@Service
public class WechatVipCardServiceImpl implements WechatVipCardService {


    @Override
    public void createVipCardTemplate(String card_id, CardDto card) {

        String result = null;
        if (card_id != null) {
             WechatVipCardUtil.updateVipCard(card_id,card);
        } else {
             WechatVipCardUtil.createVipCard(card);
        }
    }
     public void  receiveVipCard(ActionInfoSingDto actionInfoSingDto){
         WechatVipCardUtil.receiveCardRecord();
     }

   public void  deleteVipCard(String card_id){
        WechatVipCardUtil.deleteVipCard(card_id);
    }
}
