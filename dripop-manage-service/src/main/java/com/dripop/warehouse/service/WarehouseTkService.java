package com.dripop.warehouse.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.warehouse.dto.AjustWarehouseDto;
import com.dripop.warehouse.dto.GetAjustWarehouseReq;
import com.dripop.warehouse.dto.GetImeiChangeInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WarehouseTkService {
    /**
     * 创建调库单
     * @param ajustWarehouseDto
     */
    void saveAjustWarehouse(AjustWarehouseDto ajustWarehouseDto);

    /**
     * 获取调库单列表
     *
     * @param pageNo
     * @param startDate
     * @param endDate
     * @return
     */

    Pagination<GetAjustWarehouseReq> getAllAjustWarehouseInfo(Pageable pageNo, Date startDate, Date endDate,Long orgId);

    /**
     * 根据调库单id获取信息
     *
     * @param id
     * @return
     */
    List<GetImeiChangeInfo> getAjustWarehouseInfo(Long id);



}
