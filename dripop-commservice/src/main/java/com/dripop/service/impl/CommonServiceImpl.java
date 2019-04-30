package com.dripop.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.bean.IsUsed;
import com.bean.LogicDelete;
import com.bean.ShoppingModel;
import com.dripop.core.bean.Filter;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.RedisUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.*;
import com.dripop.dto.CommonGiftDto;
import com.dripop.entity.*;
import com.dripop.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyou on 2017/9/21.
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private StaticDataDao staticDataDao;

    @Autowired
    private SysOrgDao sysOrgDao;

    @Autowired
    private ComboPackageDao comboPackageDao;

    @Autowired
    private GoodsStockDao goodsStockDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private  GoodsOnlineDao  goodsOnlineDao;

    public List<TStaticData> findDataByCode(String code) {
        String key = "static_data_" + code;
        List<TStaticData> list = RedisUtil.INSTANCE.get(key, new TypeReference<List<TStaticData>>() {
        });
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("code", code));
        ql.and(Filter.eq("logicDelete", LogicDelete.NOT_DELETE.getValue()));
        ql.add("order by sort");
        list = staticDataDao.findManyByJpql(ql);
        RedisUtil.INSTANCE.set(key, list, 24 * 60 * 60);
        return list;
    }

    public TStaticData getDataByCode(String code) {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("code", code));
        ql.and(Filter.eq("logicDelete", LogicDelete.NOT_DELETE.getValue()));
        TStaticData staticData = staticDataDao.findOneByJpql(ql);
        return staticData;
    }

    public TStaticData findDataByCodeVal(String code, String val) {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("code", code));
        ql.and(Filter.eq("val", val));
        ql.and(Filter.eq("logicDelete", LogicDelete.NOT_DELETE.getValue()));
        return staticDataDao.findOneByJpql(ql);
    }

    public TStaticData findDataByCodeName(String code, String name) {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("code", code));
        ql.and(Filter.eq("name", name));
        ql.and(Filter.eq("logicDelete", LogicDelete.NOT_DELETE.getValue()));
        return staticDataDao.findOneByJpql(ql);
    }

    public TSysOrg findGeneralStore() {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("orgType", 0));
        ql.and(Filter.eq("isUsed", IsUsed.USED.getValue()));
        return sysOrgDao.findOneByJpql(ql);
    }

    @Transactional
    public Integer subtractStock(Long goodsId, Long orgId, Integer num) {
       /* QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("orgId", orgId));
        ql.and(Filter.eq("goodsId", goodsId));
        TGoodsStock goodsStock = goodsStockDao.findOneByJpql(ql);
        if(goodsStock == null) {
            goodsStock = new TGoodsStock();
            goodsStock.setOrgId(orgId);
            goodsStock.setGoodsId(goodsId);
            goodsStock.setStock(0);
            goodsStock.setOrderStock(0);
            goodsStockDao.insert(goodsStock);
        }
        Integer count = goodsDao.executeBySql("update t_goods_stock tgs, " +
                        "(select sum(tgs.stock-tgs.order_stock) stock, tgs.goods_id from t_goods_stock tgs group by tgs.goods_id) t " +
                        "set tgs.order_stock = tgs.order_stock + :goodsNum " +
                        "where tgs.goods_id = t.goods_id and t.stock - :goodsNum >= 0 and tgs.org_id = :orgId and tgs.goods_id = :goodsId",
                num, orgId, goodsId);
        if(count == 0) {
            Long stock = goodsStockDao.findOneBySql("select sum(tgs.stock-tgs.order_stock) stock from t_goods_stock tgs where tgs.goods_id = :goodsId", Long.class, goodsId);
            if(stock > 0) {
                return 1;
            }else {
                return 2;
            }
        }else {
            return 3;
        }*/
        Integer stockInt = goodsStockDao.findOneBySql("SELECT tgo.stock FROM t_goods_online tgo LEFT JOIN t_goods tg ON tgo.spu_id = tg.id\n" +
                    "WHERE tg.id = :goodsId", Integer.class, goodsId);
        Long   stock = stockInt == null ? 0 : (long) stockInt.intValue();

        if (stock == null || stock <= 0) {
            return 2;
        } else if (stock - num < 0 && stock > 0) {
            return 1;
        } else {
            return 3;
        }
    }

    @Transactional
    public void releaseStock(Long orgId, TOrderDetail orderDetail, Integer num) {
        if (num == null) {
            num = orderDetail.getNum();
        }
        if (num > orderDetail.getNum()) {
            throw new ServiceException("库存释放数量不匹配");
        }
        Map<Long, Integer> onlineIdMap = new HashMap<>();
        List<Long> packageRels = null;
        if (orderDetail.getPackageId() != null) {
            packageRels = comboPackageDao.findManyBySql("select t.online_id from t_combo_package_rel t where t.package_id = :packageId",
                    Long.class, orderDetail.getPackageId());
            for (Long packageRel : packageRels) {
                if (!onlineIdMap.containsKey(packageRel)) {
                    onlineIdMap.put(packageRel, 0);
                }
                onlineIdMap.put(packageRel, onlineIdMap.get(packageRel) + num);
            }
        } else {
            if (!onlineIdMap.containsKey(orderDetail.getOnlineId())) {
                onlineIdMap.put(orderDetail.getOnlineId(), 0);
            }
            onlineIdMap.put(orderDetail.getOnlineId(), onlineIdMap.get(orderDetail.getOnlineId()) + num);
        }

        if(StringUtil.isNotBlank(orderDetail.getGift())) {
            List<CommonGiftDto> gifts = null;
            try {
                gifts = JsonUtil.fromJson(orderDetail.getGift(), new TypeReference<List<CommonGiftDto>>() {});
            }catch (Exception e) {
                e.printStackTrace();
            }
            if(!CollectionUtils.isEmpty(gifts)) {
                for (CommonGiftDto giftDto : gifts) {
                    if(!onlineIdMap.containsKey(giftDto.getOnlineId())) {
                        onlineIdMap.put(giftDto.getOnlineId(), 0);
                    }
                    onlineIdMap.put(giftDto.getOnlineId(), onlineIdMap.get(giftDto.getOnlineId()) + (num * giftDto.getNum()));
                }
            }
        }

        String sql = null;
        for (Long onlineId : onlineIdMap.keySet()) {
            sql = "UPDATE t_goods_online tgo SET tgo.stock =  tgo.stock + :stockNum WHERE tgo.online_id = :onlineId";
            goodsStockDao.executeBySql(sql, onlineIdMap.get(onlineId), onlineId);
        }
    }

    @Transactional
    public void releaseStock(TOrder order) {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("orderId", order.getId()));
        List<TOrderDetail> details = orderDetailDao.findManyByJpql(ql);
        for (TOrderDetail detail : details) {
            releaseStock(order.getOrgId(), detail, null);
        }
    }

    @Override
    public void stockHandle(Integer num, long onlineId, Long orgId) {
        goodsDao.executeBySql("UPDATE t_goods_online tgo SET tgo.stock = tgo.stock - :num WHERE tgo.online_id = :onlineId",
                num, onlineId);
        TGoodsStock goodsStock = goodsStockDao.findOneBySql("SELECT tgs.id FROM t_goods_stock tgs LEFT JOIN t_goods_online tgo ON tgs.goods_id = tgo.spu_id WHERE tgo.online_id = :onlineId and tgs.org_id = :orgId ", TGoodsStock.class, onlineId, orgId);
        if (goodsStock==null){
            goodsStock = new TGoodsStock();
            goodsStock.setGoodsId(goodsOnlineDao.get(onlineId).getGoodsId());
            goodsStock.setOrgId(orgId);
            goodsStock.setStock(0);
            goodsStock.setOrderStock(0);
            goodsStockDao.insert(goodsStock);
        }
        goodsDao.executeBySql("UPDATE t_goods_stock tgs SET tgs.order_stock = tgs.order_stock + :num WHERE\n" +
                        " tgs.org_id = :orgId and tgs.goods_id =  (SELECT tgo.spu_id from t_goods_online tgo WHERE tgo.online_id = :onlineId)",
                num, orgId, onlineId);
    }
}
