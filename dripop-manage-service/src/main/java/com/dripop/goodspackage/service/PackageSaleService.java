package com.dripop.goodspackage.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.goodspackage.dto.OpPackageSaleSearchDto;
import com.dripop.goodspackage.dto.OpPackageSaleSearchReq;

import java.util.List;

/**
 * Created by shery on 2017/8/1.
 */
public interface PackageSaleService {
    /**
     * 保存套餐销售
     * @param mainOnlineIds  主商品上架ID,多个之间以英文逗号隔开
     * @param recomOnlineIds 推荐商品集合
     */
    public void savePackage(String mainOnlineIds, String recomOnlineIds);

    /**
     * 查询推荐套餐列表
     * @param reqDto
     * @param pageable
     * @return
     */
    Pagination<OpPackageSaleSearchDto> getPackageSaleListPage(OpPackageSaleSearchReq reqDto, Pageable pageable);

    /**
     * (套餐销售)op获取主商品套餐详情信息
     * @param onlineId
     * @param type
     * @return
     */
    List<OpPackageSaleSearchDto> getMainGoodsPackageSaleInfo(Long onlineId, Integer type);

    /**
     * 删除销售套装
     * @param onlineId
     */
    void deletePackageSale(Long onlineId);
}
