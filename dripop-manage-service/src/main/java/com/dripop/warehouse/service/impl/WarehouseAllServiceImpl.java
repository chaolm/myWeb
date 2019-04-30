package com.dripop.warehouse.service.impl;

import com.bean.ImeiStatus;
import com.bean.IsUsed;
import com.bean.LogicDelete;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.GoodsDao;
import com.dripop.dao.GoodsImeiDao;
import com.dripop.dao.GoodsTypeDao;
import com.dripop.dispatchcenter.dto.Imei;
import com.dripop.entity.TGoodsType;
import com.dripop.entity.TSysOrg;
import com.dripop.service.CommonService;
import com.dripop.util.UserUtil;
import com.dripop.warehouse.dto.*;
import com.dripop.warehouse.dto.AllWarehouse.*;
import com.dripop.warehouse.service.WarehouseAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WarehouseAllServiceImpl implements WarehouseAllService {

    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private GoodsImeiDao goodsImeiDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private GoodsTypeDao goodsTypeDao;

    @Override
    public List<GetSupplier> getSupplier() {
        StringBuffer sb = new StringBuffer();
        sb.append("select DISTINCT (tgi.supplier)  from t_goods_imei tgi");
        List<GetSupplier> list=  goodsImeiDao.findManyBySql(sb.toString(), GetSupplier.class);
        return list;
    }

    @Override
    public List<GetImeiFollowInfo> getImeiFollowInfo(String imeis) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * from t_goods_imei_log  tgil where tgil.imei = :imeis"  );
        List<GetImeiFollowInfo> list = goodsImeiDao.findManyBySql(sb.toString(), GetImeiFollowInfo.class,imeis);
        if(list.isEmpty()){
            throw new ServiceException(imeis+"不存在");
        }
        //串号id
        Long id = list.get(0).getImeiId();
        StringBuffer sbb = new StringBuffer();
        sbb.append("SELECT * from t_goods_imei_log  tgil where tgil.imei_id = :id");
        List<GetImeiFollowInfo> lists= goodsImeiDao.findManyBySql(sbb.toString(), GetImeiFollowInfo.class,id);
        if(lists.isEmpty()){
            throw new ServiceException(imeis+"不存在");
        }
        return lists;
    }

    @Override
    public List<GetWarehouseAnalysisDto> warehouseAnalysisByGoodId(Long goodId) {
        StringBuffer sb = new StringBuffer();
        List<Object> param = new ArrayList<>();
        sb.append("SELECT tso.`name`,tgs.org_id ,count(CASE WHEN tgi. STATUS != 3 and stock > 0 THEN '1' ELSE NULL END) allNum, \n" +
                "tgs.order_stock orderUsed, \n" +
                "count(CASE WHEN tgi. STATUS = 4 and stock > 0 THEN '1' ELSE NULL END) saleAfterNum,\n" +
                "count(CASE WHEN tgi. STATUS = 3  THEN '1' ELSE NULL END) saleNum\n" +
                "FROM  \n" +
                "(select tgs.org_id,tgs.goods_id, sum(tgs.order_stock) order_stock,tgs.stock \n" +
                "from t_goods_stock tgs group by tgs.goods_id,tgs.org_id) tgs\n" +
                "LEFT JOIN t_goods_imei tgi ON tgs.goods_id = tgi.goods_id  \n" +
                "LEFT JOIN t_sys_org tso on tgs.org_id = tso.org_id \n" +
                "where  tgs.goods_id = :goodId  and tgi.logic_delete = :logicDelete  GROUP BY tgs.org_id ");
        param.add(goodId);
        param.add(LogicDelete.NOT_DELETE.getValue());
        List<GetWarehouseAnalysisDto> list = goodsImeiDao.findManyBySql(sb.toString(),GetWarehouseAnalysisDto.class,param.toArray());
        return list;
    }

    @Transactional
    @Override
    public void setWarnValue(GetWarnValueReq getWarnValueReq) {
        List<IdAndWarnValue> lists = getWarnValueReq.getList();
        for(IdAndWarnValue list :lists){
            String  sql = "update t_goods tg set tg.warning = :warnValue where tg.id = :id";
            goodsImeiDao.executeBySql(sql,list.getWarnValue(),list.getId());
        }
    }

    @Override
    public Pagination<WarehouseAllSearchAnalysisDto> warehouseAnalysis(Pageable page,AllWarehouseReq reqDto) {
        StringBuffer sb = new StringBuffer();
        List<Object> paramList = new ArrayList<>();
        sb.append("SELECT tg.id goodsId,tgy.`name` typeName ,tb.`name` brandName,tg.full_name goodsName, " +
                "count(CASE WHEN tgi. STATUS != 3 THEN '1' ELSE NULL END) allNum, \n" +
                "IFNULL(tgs.order_stock,0) orderUsed, \n" +
                "count(CASE WHEN tgi. STATUS = 4 THEN '1' ELSE NULL END) saleAfterNum,\n" +
                "count(CASE WHEN tgi. STATUS = 3 THEN '1' ELSE NULL END) saleNum, \n" +
                "IFNULL(tg.warning,0) warnNum FROM t_goods tg \n" +
                "LEFT JOIN (SELECT count(CASE WHEN tgi. STATUS != 3 THEN '1' ELSE NULL END) allNum,id from t_goods_imei tgi)result on  result.id = tg.id\n" +
                "LEFT JOIN (select tgs.goods_id, sum(tgs.order_stock) order_stock from t_goods_stock tgs group by tgs.goods_id) tgs on tgs.goods_id = tg.id \n" +
                "LEFT JOIN t_goods_imei tgi ON tg.id = tgi.goods_id and tgi.logic_delete = :logicDelete \n" +
                "LEFT JOIN t_goods_type tgy ON tgy.id = tg.type_id \n" +
                "LEFT JOIN t_brand tb ON tb.id = tg.brand_id " +
                "where tg.is_used = :isUsed ");
        paramList.add(LogicDelete.NOT_DELETE.getValue());
        paramList.add(IsUsed.USED.getValue());
        if (reqDto.getTypeId() != null) {
            TGoodsType goodsType = goodsTypeDao.get(reqDto.getTypeId());
            sb.append("and tgy.full_path like :typeId ");
            paramList.add(goodsType.getFullPath() + "%");
        }
        if (reqDto.getBrandId() != null) {
            sb.append(" and tb.id = :brandId");
            paramList.add(reqDto.getBrandId());
        }
        if (StringUtil.isNotBlank(reqDto.getGoodName())) {
            sb.append(" and tg.full_name like :goodName ");
            paramList.add("%" + reqDto.getGoodName() + "%");
        }
        sb.append(" GROUP BY tg.id order by allNum desc, tg.model_id desc, tg.id desc ");
        Pagination<WarehouseAllSearchAnalysisDto> pagination = goodsImeiDao.findPageBySql(sb.toString(), page, WarehouseAllSearchAnalysisDto.class,paramList.toArray());
        for(WarehouseAllSearchAnalysisDto warehouseAllSearchAnalysisDto :pagination.getItems()){
            Integer result = warehouseAllSearchAnalysisDto.getAllNum() - warehouseAllSearchAnalysisDto.getSaleAfterNum();
            if(result < warehouseAllSearchAnalysisDto.getOrderUsed()){
                warehouseAllSearchAnalysisDto.setType(1);
            }else {
                warehouseAllSearchAnalysisDto.setType(2);
            }
        }
        return pagination;
    }

    public Pagination<WarehouseAllSearchDto> getAllGoodsInfo(AllWarehouseReq reqDto, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        ArrayList<Object> paramList = new ArrayList<>();
        TSysOrg sysOrg = commonService.findGeneralStore();
        sb.append("SELECT tg.id goodsId,tgy.`name` typeName ,tb.`name` brandName,tg.full_name goodsName,count(CASE WHEN tgi. STATUS != 3 THEN '1' ELSE NULL END) allNum, " +
                "count(CASE WHEN tgi. STATUS = 1 THEN '1' ELSE NULL END) wayNum, " +
                "count(CASE WHEN tgi. STATUS = 2 THEN '1' ELSE NULL END)inWarehouseNum, " +
                "count(CASE WHEN tgi. STATUS = 4 THEN '1' ELSE NULL END) saleAfterNum " +
                "FROM t_goods tg " +
                "LEFT JOIN (SELECT count(CASE WHEN tgi. STATUS != 3 THEN '1' ELSE NULL END) allNum,id from t_goods_imei tgi)result on  result.id = tg.id\n " +
                "LEFT JOIN t_goods_imei tgi ON tg.id = tgi.goods_id and tgi.org_id  = :orgId and tgi.logic_delete = :logicDelete " +
                "LEFT JOIN t_goods_type tgy ON tgy.id = tg.type_id " +
                "LEFT JOIN t_brand tb ON tb.id = tg.brand_id " +
                "where tg.is_used = :isUsed ");
        paramList.add(sysOrg.getId());
        paramList.add(LogicDelete.NOT_DELETE.getValue());
        paramList.add(IsUsed.USED.getValue());
        if (reqDto.getTypeId() != null) {
            TGoodsType goodsType = goodsTypeDao.get(reqDto.getTypeId());
            sb.append("and tgy.full_path like :typeId ");
            paramList.add(goodsType.getFullPath() + "%");
        }
        if (reqDto.getBrandId() != null) {
            sb.append(" and tb.id = :brandId");
            paramList.add(reqDto.getBrandId());
        }
        if (StringUtil.isNotBlank(reqDto.getGoodName())) {
            sb.append(" and tg.full_name like :goodName ");
            paramList.add("%" + reqDto.getGoodName() + "%");
        }
        if(reqDto.getStatus() != null){
            sb.append(" and tgi.status = :status");
            paramList.add(reqDto.getStatus());
        }
        sb.append(" GROUP BY tg.id order by allNum desc, tg.model_id desc, tg.id desc\n ");
        return goodsDao.findPageBySql(sb.toString(), pageable, WarehouseAllSearchDto.class, paramList.toArray());
    }

    public Pagination<GetSingleWarehouseGoodsInfo> getSingleWarehouseGoodsInfo(GetSingleWarehouseInfo requto, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<>();
        sb.append("SELECT tgi.imei, tgi.purchase_price, tgi.supplier, tgi.`status` " +
                "FROM t_goods_imei tgi where tgi.goods_id = :goodId and tgi.org_id = :orgId and tgi.status in ( 1,2,4 ) and tgi.logic_delete = :logicDelete ");
        params.add(requto.getGoodId());
        params.add(requto.getOrgId());
        params.add(LogicDelete.NOT_DELETE.getValue());
        if (requto.getMinPrice() != null) {
            sb.append(" and tgi.purchase_price >= :minPrice");
            params.add(requto.getMinPrice()*100);
        }
        if (requto.getMaxPrice() != null) {
            sb.append(" and tgi.purchase_price <= :maxPrice");
            params.add(requto.getMaxPrice()*100);
        }
        if (requto.getImei() != null) {
            sb.append(" and tgi.imei = :imei");
            params.add(requto.getImei());
        }
        if (requto.getStockStatus() != null && requto.getStockStatus() == 4) {
            sb.append(" and tgi.status = :status");
            params.add(requto.getStockStatus());
        }
        if(requto.getStockStatus() != null && requto.getStockStatus() == 1){
            sb.append(" and tgi.status != :status");
            params.add(ImeiStatus.SERVICE.getValue());
        }
        if(requto.getIsInWarehouse() != null && requto.getIsInWarehouse() == 1){
            sb.append(" and tgi.status = :status");
            params.add(requto.getIsInWarehouse());
        }
        if(requto.getIsInWarehouse() != null && requto.getIsInWarehouse() == 2){
            sb.append(" and tgi.status = :status");
            params.add(requto.getIsInWarehouse());
        }
        if (StringUtil.isNotEmpty(requto.getSupplier())) {
            sb.append(" and tgi.supplier = :supplier");
            params.add(requto.getSupplier());
        }
        return goodsImeiDao.findPageBySql(sb.toString(), pageable, GetSingleWarehouseGoodsInfo.class, params.toArray());
    }


    public List<ByImeiFindInfo> byImeiFindInfo(String imeis, Integer type, Integer updateImeiType) {
        if(!StringUtil.isNotBlank(imeis)){
            throw new ServiceException("请输入串号");
        }
        StringBuffer sb = new StringBuffer();
        ArrayList<Object> params = new ArrayList();
        sb.append("SELECT " +
                "tgi.imei, " +
                "tgi.purchase_price, " +
                "tgi.supplier, " +
                "tg.full_name " +
                "FROM " +
                "t_goods_imei tgi, " +
                "t_goods tg " +
                "WHERE " +
                "tgi.goods_id = tg.id " +
                "AND tgi.imei in (:imeis) AND tgi.org_id =:sysOrg and tgi.logic_delete = :logicDelete ");
        params.add(Arrays.asList(imeis.split(",")));
        TSysOrg sysOrg = commonService.findGeneralStore();
        params.add(sysOrg);
        params.add(0);
        if (type == 1 ||  type == 3) {
            sb.append(" AND tgi.status in (2,3,4) ");
        }
        if(type == 2){
            sb.append(" AND tgi.status in (2,3,4) AND tgi.logic_delete = 0 ");
        }
        if (type == 4) {
            sb.append(" AND tgi.status in (4) ");
        }

        List<ByImeiFindInfo> byImeiFindInfos = goodsImeiDao.findManyBySql(sb.toString(), ByImeiFindInfo.class, params.toArray());
        if(byImeiFindInfos.isEmpty()){
            throw new ServiceException(imeis+"串号状态不符合或者串号不属于总仓或者串号已出库");
        }
        return byImeiFindInfos;
    }

    public List<GetShop> returgetShop() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT  tso.`name`,tso.org_id FROM t_sys_org tso where tso.org_type = 3");
        return goodsDao.findManyBySql(sb.toString(), GetShop.class);
    }

}
