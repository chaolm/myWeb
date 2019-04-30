package com.dripop.indexArea.service;

import com.dripop.entity.TStaticData;
import com.dripop.indexArea.dto.IndexAreaDetailDto;
import com.dripop.indexArea.dto.IndexAreaDto;
import com.dripop.indexArea.dto.StaticDataDetail;

import java.util.List;

/**
 * 首页、专区 service
 *
 * @author dq
 * @date 2018/3/12 14:12
 */

public interface IndexAreaService {
    /**
     * 保存广告图
     *
     * @param extendParams
     */
    void saveStaticDate(TStaticData extendParams);

    /**
     * 保存PC商城登陆页面背景图片
     * @param imgUrl
     */
    void saveBackPicture(String imgUrl);

    /**

     * PC商城获取登陆背景图片
     * @return
     */
    String getLoginBackPic();

    /**
     * 删除广告图
     *
     * @param id
     */
    void deleteStaticDate(Integer id);

    /**
     * 新增首页、专区
     * @param indexAreaDetailDto
     */
    void saveArea(IndexAreaDetailDto indexAreaDetailDto);

    /**
     * 根据首页或专区ID删除
     *
     * @param id
     */
    void deleteArea(Long id);

    /**
     * 广告图详情
     * @return
     */
    StaticDataDetail staticDateDetail();

    /**
     * 专区列表
     * @return
     */
    List<IndexAreaDto> areaList(Integer dataType);

    /**
     * 首页专区详情
     * @param areaId
     * @return
     */
    IndexAreaDetailDto areaDetail(Long areaId,Integer dataType);

    /**
     * 专区排序
     * @param id
     * @param sort
     */
    void areaSort(Long id, Integer sort);
}
