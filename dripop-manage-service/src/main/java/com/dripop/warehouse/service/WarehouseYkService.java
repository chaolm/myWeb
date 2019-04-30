package com.dripop.warehouse.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.warehouse.dto.CreateMoveWarehouse;
import com.dripop.warehouse.dto.GetAllMoveWarehouseInfo;
import com.dripop.warehouse.dto.MoveWarehouseInfo;

import java.util.Date;
import java.util.List;

public interface WarehouseYkService {
    /**
     * 根据入库id获取商品移库信息
     *
     * @param pageNo
     * @param id
     * @return
     */
     List<MoveWarehouseInfo> getMoveWarehouseInfo(Integer pageNo, Integer id);
    /**
     * 获取所有移库商品
     *
     * @param
     * @return
     */
    Pagination<GetAllMoveWarehouseInfo> getAllMoveWarehouseInfo(Date startDate, Date endDate, Pageable pageable,Long ycOrgId,Long yrOrgId);
    /**
     * 创建移库单
     *
     * @param createMoveWarehouse
     */
    void saveMoveWarehouse(CreateMoveWarehouse createMoveWarehouse);

    /**
     * 取消和确认移库操作
     * @param id
     * @param status
     */
    void confirmAndCancelMove(Long id, Integer status);


}
