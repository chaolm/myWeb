package com.dripop.warehouse.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.warehouse.dto.CreateMoveWarehouse;
import com.dripop.warehouse.dto.GetAllInWarehouseInfo;
import com.dripop.warehouse.dto.GetInWarehouseInfo;
import com.dripop.warehouse.dto.MoveWarehouseInfo;

import java.util.Date;
import java.util.List;

public interface WarehouseCkService {

    /**
     * 根据入库id获取商品出库信息
     *
     * @param pageNo
     * @param id
     * @return
     */
    List<MoveWarehouseInfo> getOutWarehouseInfo(Integer pageNo, Integer id);

    /**
     * 获取所有出库信息
     *
     * @param startDate
     * @param endDate
     * @param pageable
     * @return
     */
    Pagination<GetAllInWarehouseInfo> getAllOutWarehouseInfo(Date startDate, Date endDate, Pageable pageable,Long ycOrgId);

    /**
     * 创建出库单
     *
     * @param createOutWarehouse
     */
    void saveOutWarehouse(CreateMoveWarehouse createOutWarehouse);

}
