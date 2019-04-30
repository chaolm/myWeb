package com.dripop.service;

import com.dripop.entity.TOrder;
import com.dripop.entity.TOrderDetail;
import com.dripop.entity.TStaticData;
import com.dripop.entity.TSysOrg;

import java.util.List;

/**
 * Created by liyou on 2017/9/21.
 */
public interface CommonService {

    List<TStaticData> findDataByCode(String code);

    TStaticData getDataByCode(String code);

    public TStaticData findDataByCodeVal(String code, String val);

    public TStaticData findDataByCodeName(String code, String name);

    /**
     * 查询总仓信息
     * @return
     */
    TSysOrg findGeneralStore();

    /**
     * 购买商品，减库存操作
     * @param goodsId
     * @param orgId
     * @param num
     * @return 1 库存不足 2 无货 3 成功
     */
    Integer subtractStock(Long goodsId, Long orgId, Integer num);

    /**
     * 释放库存
     * @param orderDetail
     * @param num
     */
    void releaseStock(Long orgId, TOrderDetail orderDetail, Integer num);

    /**
     * 释放库存
     * @param order
     */
    void releaseStock(TOrder order);

    /**
     * 线上商品库存处理
     * @param num
     * @param l
     * @param orgId
     */
    void stockHandle(Integer num, long onlineId, Long orgId);
}
