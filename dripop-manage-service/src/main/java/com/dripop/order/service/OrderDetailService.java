package com.dripop.order.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.order.dto.*;
import com.dripop.sys.dto.CustomerOrderDetailDto;

/**
 * creator:liuyfian
 * 关于订单
 */
public interface OrderDetailService {

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderSingleDto singleOrderDetail(Long orderId);

    /**
     * 全部 订单列表
     * @param orderSearchDto
     * @param pageable
     * @return
     */
    Pagination<CustomerOrderDetailDto> allOrderPage(OrderSearchDto orderSearchDto, Pageable pageable);

    /**
     * 售后服务订单 分页查询
     * @param serverPageDto
     * @param pageable
     * @return
     */
    Pagination<ServerPageDto> allServerOrderPage(ServerPageDto serverPageDto, Pageable pageable);

    /*售后订单详情查询*/
   // ServerSingleDto singleServerOrder(Long cancelId);

    /**
     * 后台售后详情
     * @param cancelOrderId
     * @return
     */
    ServerSingleDto cancelDetail(Long cancelOrderId);

    /**
     * 商品串号信息
     * @param orderId
     * @return
     */
    DeliveryOperDto  goodsImeis(Long orderId);
}
