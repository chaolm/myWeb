package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.statistics.dto.*;
import com.dripop.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 统计controller
 *
 * @author dq
 * @date 2018/3/15 11:03
 */
@Controller
@RequestMapping("statistics")
public class StatisticsController extends BaseController {
    @Autowired
    private StatisticsService statisticsService;

    /**
     * 用户下单省份统计
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("customerSta")
    @ResponseBody
    public ResultInfo customerSta(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        List<CustomerStaDto> list = statisticsService.customerSta();
        return returnSuccess(list);
    }

    /**
     * 订单分析
     * @param request
     * @param params
     * @return
     */
    @PostMapping("orderAnalysls")
    @ResponseBody
    public ResultInfo orderAnalysls(HttpServletRequest request, @RequestBody AnalyslsParamsDto params) {
        OrderAnalyslsDto orderAnalyslsDto = statisticsService.orderAnalysls(params);
        return returnSuccess(orderAnalyslsDto);
    }

    /**
     * 用户访问历史数据
     * @param request
     * @param params
     * @return
     */
    @PostMapping("goodsAnalysls")
    @ResponseBody
    public ResultInfo goodsAnalysls(HttpServletRequest request, @RequestBody AnalyslsParamsDto params) {
        GoodsAnalyslsDto goodsAnalyslsDto= statisticsService.goodsAnalysls(params);
        return returnSuccess(goodsAnalyslsDto);
    }

    /**
     * 商品访问来源分析
     * @param request
     * @param params
     * @return
     */
    @PostMapping("channelAnalysls")
    @ResponseBody
    public ResultInfo channelAnalysls(HttpServletRequest request, @RequestBody AnalyslsParamsDto params) {
        ChannelAnalyslsDto channelAnalyslsDto= statisticsService.channelAnalysls(params);
        return returnSuccess(channelAnalyslsDto);
    }

    /**
     * 交易分析
     * @param request
     * @param params
     * @return
     */
    @PostMapping("payAnalysls")
    @ResponseBody
    public ResultInfo payAnalysls(HttpServletRequest request, @RequestBody AnalyslsParamsDto params) {
        PayAnalyslsDto payAnalyslsDto = statisticsService.payAnalysls(params);
        return returnSuccess(payAnalyslsDto);
    }
}

