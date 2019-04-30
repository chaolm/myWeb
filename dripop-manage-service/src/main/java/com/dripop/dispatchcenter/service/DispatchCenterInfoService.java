package com.dripop.dispatchcenter.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.dispatchcenter.dto.DispatchShopInfo;
import com.dripop.dispatchcenter.dto.GetDispatchCenterReq;
import com.dripop.dispatchcenter.dto.GetDispatchInfo;
import com.dripop.dispatchcenter.dto.OrderId;

import java.util.List;

public interface DispatchCenterInfoService {

    /**
     * 获取派单中心信息
     * @param pageable
     * @return
     */
    Pagination<GetDispatchInfo> getDispatchCenterInfo(GetDispatchCenterReq requDto,Pageable pageable);

    /**
     * 查询库存
     * @param orderId
     */
    List<DispatchShopInfo> queryStock(OrderId orderId, Long orgId);

    /**
     * 确认派单或改派
     * @param orderId
     * @param orgId
     */
    void submitDispatcher(OrderId orderId, Long orgId);
}
