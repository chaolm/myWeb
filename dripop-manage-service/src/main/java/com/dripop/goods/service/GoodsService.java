package com.dripop.goods.service;

import com.alibaba.fastjson.JSONArray;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.entity.*;
import com.dripop.goods.dto.*;

import java.util.List;

/**
 * Created by liyou on 2018/1/10.
 */
public interface GoodsService {

    public Pagination<OpGoodsSearchDto> pageQuery(OpGoodsSearchReq req, Pageable pageable);

    Pagination<OpGoodsSearchDtoForModel> pageQueryForModel(OpGoodsSearchReq req, Pageable pageable);
    /**
     * 设置商品上架信息
     * @param goodsOnline
     */
    public void addOrEdit(TGoodsOnline goodsOnline);

    public TGoods saveGoods(TGoods goods, String images, List<TGoodsParam> goodsParamList);

    public OpGoodsDetailDto getOpGoodsDetail(Long goodsId);

    /**
     * 删除商品
     * @param goodsId
     */
    void deleteGoods(Long goodsId);

    TGoodsOnline findGoodsOnline(Long onlineId);

    /**
     * 获取商品参数信息
     * @param goodsId
     * @param typeId
     * @return
     */
    List<OpParamChannelDto> getOpGoodsParam(Long goodsId, Long typeId);

    void downGoodsOnlineWeb(Long onlineId);

    /**
     * 编辑商品分类
     * @param goodTypeList
     */
    void editGoodsType(JSONArray goodTypeList);

    /**
     * 商品分类详情
     * @return
     * @param id
     */
    List<GoodsClassDto> goodsTypeDetail();

    /**
     * 保存商品访问记录
     * @param tVisit
     */
    void createVisit(TVisit tVisit);

    TGoodsModel getGoodsSpu(Long modelId);

    void saveGoodsSpu(TGoodsModel model, Long modelId);

    void deleteGoodsSpu(Long modelId);

    /**
     * 新建商品索引
     */
    void buildIndex();
}
