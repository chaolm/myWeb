package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.JsonUtil;
import com.dripop.entity.TGift;
import com.dripop.entity.TGiftPromotion;
import com.dripop.promotion.dto.*;
import com.dripop.promotion.service.GiftPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liyou on 2018/2/5.
 */
@Controller
@RequestMapping("gift")
public class GiftPromotionController extends BaseController {

    @Autowired
    private GiftPromotionService giftPromotionService;

    /**
     * 赠品促销列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("page")
    @ResponseBody
    public ResultInfo page(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        GiftPromotionPageReq reqDto = JsonUtil.fromJson(reqJson, GiftPromotionPageReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<GiftPromotionPageDto> page = giftPromotionService.page(reqDto, pageable);
        return returnSuccess(page);
    }

    /**
     * 赠品促销状态设置
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("setting")
    @ResponseBody
    public ResultInfo setting(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        Integer status = reqJson.getInteger("status");
        giftPromotionService.setting(id, status);
        return returnSuccess();
    }

    /**
     * 赠品促销删除
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        giftPromotionService.delete(id);
        return returnSuccess();
    }


    /**
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("saveOrUpdateGift")
    @ResponseBody
    public ResultInfo saveOrUpdateGift(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        GiftDetailDto dto = JsonUtil.fromJson(reqJson, GiftDetailDto.class);
        giftPromotionService.saveOrUpdateGift(dto);
        return returnSuccess();
    }


    @PostMapping("findGiftDetail")
    @ResponseBody
    public ResultInfo findGiftDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        TGiftPromotion giftPromotion = JsonUtil.fromJson(reqJson, TGiftPromotion.class);
        GiftDetailDto dto = giftPromotionService.findGiftDetail(giftPromotion.getId());
        return returnSuccess(dto);
    }

}
