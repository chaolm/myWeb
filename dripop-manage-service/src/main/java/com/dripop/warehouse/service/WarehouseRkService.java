package com.dripop.warehouse.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.warehouse.dto.CreateInWarehouse;
import com.dripop.warehouse.dto.CreateMoveWarehouse;
import com.dripop.warehouse.dto.GetAllInWarehouseInfo;
import com.dripop.warehouse.dto.GetInWarehouseInfo;

import java.util.Date;
import java.util.List;

public interface WarehouseRkService {

    /**
     * 根据入库id获取商品入库信息
     *
     * @param id
     * @return
     */
     GetInWarehouseInfo getInWarehouseInfo(Long id);
    /**
     * 创建入库单
     *
     * @param createInWarehouse
     */
    void saveInWarehouse(CreateInWarehouse createInWarehouse);
    /**
     * 创建入库单同时完成移库
     *
     * @param createInWarehouse
     */
    void createInWarehouseAndMove(CreateInWarehouse createInWarehouse);
    /**
     * 查询入库单所有信息
     *
     * @param startDate
     * @param endDate
     * @param pageable
     * @return
     */
    Pagination<GetAllInWarehouseInfo> getAllInWarehouseInfo(Date startDate, Date endDate, Pageable pageable,Long orgId);


}
