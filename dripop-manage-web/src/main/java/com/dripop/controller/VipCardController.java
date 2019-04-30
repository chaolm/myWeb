package com.dripop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.dripop.bean.CommonConstants;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.RedisUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.vipcard.alipay.dto.VipCardParam;
import com.dripop.vipcard.dto.VipCardDto;
import com.dripop.vipcard.service.AlipayVIPCardService;
import com.dripop.vipcard.service.WechatVipCardService;
import com.dripop.vipcard.wechat.WechatVipCardUtil;
import com.dripop.vipcard.wechat.dto.ActionInfoSingDto;
import com.dripop.vipcard.wechat.dto.CardDto;
import com.dripop.vipcard.wechat.dto.MemberCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("vip")
public class VipCardController extends BaseController{

    @Autowired
    private AlipayVIPCardService alipayVipCardService;

    @Autowired
    private WechatVipCardService wechatVipCardService;

    /**
     * 会员卡管理页面接口
     * @param reqJson
     * @return
     */
    @RequestMapping("getVipCardTemplate")
    public ResultInfo getVipCardTemplate(@RequestBody JSONObject reqJson) {
        String cardInfoCache = RedisUtil.INSTANCE.get(CommonConstants.ALIPAY_VIP_CARD);
        JSONObject result = new JSONObject();
        result.put("cardParam", JsonUtil.fromJson(cardInfoCache, new TypeReference<VipCardParam>(){}));
        return returnSuccess(result);
    }

    /**
     * 模板创建更新接口
     * @param reqJson
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("createVIPCardTemplate")
    public ResultInfo createVIPCardTemplate(@RequestBody JSONObject reqJson) throws AlipayApiException {

        alipayVipCardService.updateVIPCardTemplate(reqJson);
        return returnSuccess();
    }

    /**
     * 会员卡领卡记录查询
     * @param reqJson
     * @return
     */
    @RequestMapping("queryVIPCardRecord")
    public ResultInfo queryVIPCardRecord(@RequestBody JSONObject reqJson) {
        String keyWord = reqJson.getString("keyWord");
        Integer receivePlat = reqJson.getInteger("receivePlat");
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<VipCardDto> result = alipayVipCardService.queryVIPCardRecord(keyWord, receivePlat, pageable);
        return returnSuccess(result);
    }



    @RequestMapping("createWechatVIPCard")
    @ResponseBody
    public ResultInfo createWechatVipCard(@RequestBody JSONObject reqJson,MultipartFile logoUrlFile,MultipartFile backgroundUrlFile) throws Exception  {
        String logoUrl = reqJson.getString("logoUrl");
        String backgroundUrl = reqJson.getString("backgroundUrl");
        String card_id=reqJson.getString("card_id");

        CardDto cardDto = JsonUtil.fromJson(JsonUtil.toFullJson(reqJson.get("card")), CardDto.class);

//        String logoUrlStr=WechatVipCardUtil.uploadVipCardImg(logoUrlFile);
//        String backgroundUrlStr = WechatVipCardUtil.uploadVipCardImg(backgroundUrlFile);
//
//        Map backgroundMap = JsonUtil.fromJson(backgroundUrlStr);
//        String backgroundUrl = (String) backgroundMap.get("url");
//        createCardDto.setBackground_pic_url(backgroundUrl);
//
//        Map logoUrlMap = JsonUtil.fromJson(backgroundUrlStr);
//        String logoUrl = (String) logoUrlMap.get("url");
//        createCardDto.setBackground_pic_url(logoUrl);

        wechatVipCardService.createVipCardTemplate(card_id,cardDto);
        return returnSuccess();
    }


    @RequestMapping("receiveVipCard")
    @ResponseBody
    public ResultInfo receiveVipCard(@RequestBody JSONObject reqJson){
        ActionInfoSingDto actionInfoSingDto=JsonUtil.fromJson("actionInfoSingDto",ActionInfoSingDto.class);
        wechatVipCardService.receiveVipCard(actionInfoSingDto);
        return returnSuccess();
    }


    @RequestMapping("deleteVipCard")
    @ResponseBody
    public ResultInfo deleteVipCard(@RequestBody JSONObject reqJson){
        String card_id = reqJson.getString("card_id");
        wechatVipCardService.deleteVipCard(card_id);
        return returnSuccess();
    }
}
