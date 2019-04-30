package com.dripop.goods.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.entity.TGoodsType;
import com.dripop.goods.dto.OpTypeDto;

import java.util.List;

public interface GoodsTypeService {

    /**
     * 新增类目
     * @param goodsType
     * @param brandIds
     */
    void saveType(TGoodsType goodsType, String brandIds);

    /**
     * 修改类目
     * @param goodsType
     * @param brandIds
     */
    void updateType(TGoodsType goodsType, String brandIds);

    TGoodsType findById(Long id);

    /**
     * 删除类目
     * @param id
     */
    void deleteType(Long id);

    /**
     * 分页查询类目列表
     * @param name
     * @param pageable
     * @return
     */
    Pagination<OpTypeDto> pageType(String name, Pageable pageable);

    /**
     * 根据上级类目ID查询类目列表
     * @param parentId
     * @return
     */
    List<TGoodsType> listType(Long parentId);

    List<TGoodsType> listTypeSort(Integer isFull);
}
