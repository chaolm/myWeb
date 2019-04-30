package com.dripop.promotion.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.entity.TCustomer;
import com.dripop.promotion.dto.*;

import java.util.List;

/**
 * Created by liyou on 2018/2/5.
 */
public interface CardService {

    /**
     * 卡券列表
     * @param reqDto
     * @param pageable
     * @return
     */
    Pagination<CardPageDto> cardPage(CardPageReq reqDto, Pageable pageable);

    /**
     * 卡券状态设置
     * @param id
     * @param status
     */
    void setting(Long id, Integer status);

    void delete(Long id);


    /**
     * 添加或者修改 优惠券
      * @param dto
     */
    void savaOrUpdateCard(CardDetailDto dto);

    /**
     * 优惠券详情
     * @param cardId
     * @return
     */
    CardDetailDto findCardDetail(Long cardId);

    /**
     * 用户领取记录
     * @param cardId
     * @return
     */
    List<UserClaimRecordDto> getUCRList(Long cardId);

    List<TCustomer>  selectCustomer(SelectCustomerDto dto);


    List<Long> getCustIdList(List<String> phoneList);

}
