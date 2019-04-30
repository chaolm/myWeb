package com.dripop.goodspackage.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.goodspackage.dto.*;

import java.util.List;
import java.util.Map;

/**
 * Created by liyou on 2017/8/4.
 */
public interface ComboPackageService {

    /**
     * 组合套装配置
     * @param comboPackSettingDto
     * @throws Exception
     */
    void setting(OpComboPackSettingDto comboPackSettingDto);

    /**
     * 配置组合套装的商品列表
     * @param pageable
     * @param condition
     * @return
     */
    Pagination<OpComboGoodsDto> opComboPackageGoodsPage(Pageable pageable, OpComboPackageGoodsReq condition);

    /**
     * 根据商品上架id获取组合套装信息
     * @param onlineId
     * @return
     */
    public List<ComboPackageDto> getComboPackageByOnlineId(Long onlineId);

    /**
     * 根据id获取组合套装信息
     * @param id
     * @return
     */
    public List<ComboPackageDto> getComboPackageById(Long id);

    /**
     * 商品组合套装列表
     * @param onlineId
     * @return
     */
    List<OpComboPackageDto> opComboPackageList(Long onlineId);

    /**
     * 根据onlineId删除组合套装
     * @param onlineId
     */
    void deteleComboPackage(Long onlineId);

    /**
     * 商品组合套装信息
     * @param onlineId
     * @return
     */
    Map opComboPackageInfo(Long onlineId);
}
