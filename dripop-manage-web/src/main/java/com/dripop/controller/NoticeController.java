package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.JsonUtil;
import com.dripop.goods.dto.GoodsNoticeDto;
import com.dripop.goods.dto.GoodsNoticePageReq;
import com.dripop.goods.service.GoodsNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liyou on 2018/3/24.
 */
@Controller
@RequestMapping("notice")
public class NoticeController extends BaseController {

    @Autowired
    private GoodsNoticeService goodsNoticeService;

    /**
     * 到货通知列表
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("page")
    @ResponseBody
    public ResultInfo page(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        GoodsNoticePageReq reqDto = JsonUtil.fromJson(reqJson, GoodsNoticePageReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<GoodsNoticeDto> page = goodsNoticeService.page(reqDto, pageable);
        return returnSuccess(page);
    }
}
