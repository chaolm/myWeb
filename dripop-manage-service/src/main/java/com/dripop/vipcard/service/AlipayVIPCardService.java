package com.dripop.vipcard.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.vipcard.dto.VipCardDto;

public interface AlipayVIPCardService {

    void updateVIPCardTemplate(JSONObject jsonObject) throws AlipayApiException;

    Pagination<VipCardDto>  queryVIPCardRecord(String keyWord, Integer receivePlat, Pageable pageable);
}
