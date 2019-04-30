package com.dripop.goods.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.goods.dto.GoodsNoticeDto;
import com.dripop.goods.dto.GoodsNoticePageReq;

/**
 * Created by liyou on 2018/3/24.
 */
public interface GoodsNoticeService {

    /**
     * 到货通知列表
     * @param reqDto
     * @param pageable
     * @return
     */
    Pagination<GoodsNoticeDto> page(GoodsNoticePageReq reqDto, Pageable pageable);
}
