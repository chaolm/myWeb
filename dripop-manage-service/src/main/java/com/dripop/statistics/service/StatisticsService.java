package com.dripop.statistics.service;

import com.dripop.statistics.dto.*;

import java.util.List;

/**
 * 统计业务层 service
 *
 * @author dq
 * @date 2018/3/15 11:06
 */

public interface StatisticsService {
    /**
     * 用户下单省份统计
     *
     * @return
     */
    List<CustomerStaDto> customerSta();

    /**
     * 订单分析
     *
     *
     * @param date
     * @param time
     * @param startTime
     *@param endTime @return
     */
    OrderAnalyslsDto orderAnalysls( AnalyslsParamsDto params);

    /**
     * 商品访问用户分析
     *
     * @param time
     * @param goodsId
     * @param type
     *@param endTime
     * @param startTime @return
     */
    GoodsAnalyslsDto goodsAnalysls(AnalyslsParamsDto params);

    /**
     * 商品访问来源分析
     *
     * @param time
     * @param goodsId
     * @return
     */
    ChannelAnalyslsDto channelAnalysls(AnalyslsParamsDto params);

    /**
     * 交易统计
     * @param time
     * @return
     */
    PayAnalyslsDto payAnalysls(AnalyslsParamsDto params);
}
