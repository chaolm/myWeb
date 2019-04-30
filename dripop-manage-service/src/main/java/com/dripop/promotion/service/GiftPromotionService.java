package com.dripop.promotion.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.promotion.dto.GiftDetailDto;
import com.dripop.promotion.dto.GiftPromotionPageDto;
import com.dripop.promotion.dto.GiftPromotionPageReq;

/**
 * Created by liyou on 2018/2/5.
 */
public interface GiftPromotionService {

    /**
     * 赠品促销列表
     *
     * @param reqDto
     * @param pageable
     * @return
     */
    Pagination<GiftPromotionPageDto> page(GiftPromotionPageReq reqDto, Pageable pageable);

    /**
     * 赠品促销状态设置
     *
     * @param id
     * @param status
     */
    void setting(Long id, Integer status);

    /**
     * 赠品促销删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 赠品详情
     *
     * @param promotionId
     * @return
     */
    GiftDetailDto findGiftDetail(Long promotionId);

    /**
     * 添加或者 修改 赠品配置
     * @param giftDetailDto
     */
    void saveOrUpdateGift(GiftDetailDto giftDetailDto);






}
