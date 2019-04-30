package com.dripop.warehouse.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.warehouse.dto.*;
import com.dripop.warehouse.dto.AllWarehouse.*;

import java.util.List;

public interface WarehouseAllService {

    /**
     * 查看所有商品在仓库中详情
     *
     * @param reqDto
     * @param pageable
     * @return
     */
    Pagination<WarehouseAllSearchDto> getAllGoodsInfo(AllWarehouseReq reqDto, Pageable pageable);

    /**
     * 得到某商品在仓库所有详情
     *
     * @param
     * @param pageable
     * @return
     */
    Pagination<GetSingleWarehouseGoodsInfo> getSingleWarehouseGoodsInfo(GetSingleWarehouseInfo queto, Pageable pageable);

    /**
     * 据串号查找商品信息
     *
     * @param imeis
     * @return
     */
    List<ByImeiFindInfo> byImeiFindInfo(String imeis, Integer type, Integer updateImeiType);

    /**
     * 得到门店
     *
     * @param
     * @return
     */
    List<GetShop> returgetShop();

    /**
     * 获取供货商
     *
     * @return
     */
    List<GetSupplier> getSupplier();

    /**
     * 获取串号跟踪记录
     *
     * @param imeis
     * @return
     */
   List<GetImeiFollowInfo> getImeiFollowInfo(String imeis);

    /**
     * 库存分析
     * @param page
     * @return
     */
    Pagination<WarehouseAllSearchAnalysisDto> warehouseAnalysis(Pageable page,AllWarehouseReq reqDto);

    /**
     * 根据商品id获取商品库存分析
     * @param goodId
     * @return
     */
    List<GetWarehouseAnalysisDto> warehouseAnalysisByGoodId(Long goodId);

    /**
     * 设置报警值
     * @param getWarnValueReq
     */
    void setWarnValue(GetWarnValueReq getWarnValueReq);
}
