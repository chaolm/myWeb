package com.dripop.vipcard.service;

import com.dripop.vipcard.wechat.dto.ActionInfoSingDto;
import com.dripop.vipcard.wechat.dto.CardDto;

public interface WechatVipCardService {

     void createVipCardTemplate(String card_id, CardDto cardDto);

     void  receiveVipCard(ActionInfoSingDto actionInfoSingDto);

     void  deleteVipCard(String card_id);

     

}
