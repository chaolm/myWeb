package com.dripop.warehouse.service.impl;

import com.bean.ImeiLogType;
import com.bean.ImeiStatus;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.exception.ServiceException;
import com.dripop.dao.*;
import com.dripop.entity.*;
import com.dripop.service.CommonService;
import com.dripop.util.PriceUtil;
import com.dripop.util.UserUtil;
import com.dripop.warehouse.dto.*;
import com.dripop.warehouse.dto.AllWarehouse.GetAllWarehouseId;
import com.dripop.warehouse.dto.AllWarehouse.GetIdByImei;
import com.dripop.warehouse.dto.AllWarehouse.GetImeisInfo;
import com.dripop.warehouse.dto.AllWarehouse.InWarehouseLog;
import com.dripop.warehouse.service.WarehouseRkService;
import org.hibernate.dialect.identity.Oracle12cGetGeneratedKeysDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class WarehouseRkServiceImpl implements WarehouseRkService{
    @Autowired
    private WarehouseRkDetailDao warehouseRkDetailDao;

    @Autowired
    private WarehouseRkDao warehouseRkDao;

    @Autowired
    private GoodsImeiDao goodsImeiDao;

    @Autowired
    private GoodsImeiLogDao goodsImeiLogDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private WarehouseCkDao warehouseCkDao;

    @Autowired
    private WarehouseYkDao warehouseYkDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private GoodsStockDao goodsStockDao;

    @Autowired
    private WarehouseYkServiceImpl warehouseYkService;

    public GetInWarehouseInfo getInWarehouseInfo(Long id) {
        TWarehouseRk rk = warehouseRkDao.get(id);
        GetInWarehouseInfo inWarehouseInfo = new GetInWarehouseInfo();
        inWarehouseInfo.setSupplier(rk.getSupplier());

        String sql = "select tg.full_name goodsName, twrd.purchase_price, twrd.imeis from t_warehouse_rk_detail twrd " +
                "left join t_goods tg on tg.id = twrd.goods_id " +
                "where twrd.warehouse_rk_id = :id";
        List<GetInWarehouseGoodsInfo> goods = warehouseRkDetailDao.findManyBySql(sql, GetInWarehouseGoodsInfo.class, id);
        inWarehouseInfo.setGoods(goods);
        return inWarehouseInfo;
    }

    @Transactional
    public void saveInWarehouse(CreateInWarehouse createInWarehouse) {
        TWarehouseRk rk = new TWarehouseRk();
        rk.setSupplier(createInWarehouse.getSupplier());
        List<Long> goodsIds = new ArrayList<>();
        for (CreateInWarehouseDetailDto detail : createInWarehouse.getGoods()) {
            goodsIds.add(detail.getGoodsId());
        }

        StringBuffer sb = new StringBuffer();
        sb.append("select GROUP_CONCAT(DISTINCT tg.full_name) goodsNames, GROUP_CONCAT(DISTINCT tg.small_pic) imgUrls  from t_goods tg " +
                 "where tg.id in (:goodsIds)");
        List<Map<String, Object>> list = warehouseRkDao.findMapBySql(sb.toString(), goodsIds);
        rk.setGoodsNames(list.get(0).get("goodsNames").toString());
        rk.setImgUrls(list.get(0).get("imgUrls").toString());
        rk.setCreateTime(new Date());
        rk.setCreator(UserUtil.currentAdminUser().getId());
        warehouseRkDao.insert(rk);

        TWarehouseRkDetail rkDetail = null;
        for (CreateInWarehouseDetailDto detail : createInWarehouse.getGoods()) {
            List<String> imeis = goodsImeiDao.findManyBySql("select tgi.imei from t_goods_imei tgi where tgi.imei in (:imeis)",
                    String.class, Arrays.asList(detail.getImeis().split(",")));
            if (!CollectionUtils.isEmpty(imeis)) {
                throw new ServiceException(imeis.get(0)+"imei串号重复");
            }
            rkDetail = new TWarehouseRkDetail();
            rkDetail.setGoodsId(detail.getGoodsId());
            rkDetail.setImeis(detail.getImeis());
            rkDetail.setPurchasePrice(PriceUtil.doubleToInt(detail.getPurchasePrice()));
            rkDetail.setWarehouseRkId(rk.getId());
            saveGoodsImei(detail, rk);
            warehouseRkDetailDao.insert(rkDetail);
        }
    }

    @Transactional
    public void createInWarehouseAndMove(CreateInWarehouse createInWarehouse) {
        TWarehouseRk rk = new TWarehouseRk();
        rk.setSupplier(createInWarehouse.getSupplier());
        List<Long> goodsIds = new ArrayList<>();
        for (CreateInWarehouseDetailDto detail : createInWarehouse.getGoods()) {
            goodsIds.add(detail.getGoodsId());
        }

        StringBuffer sb = new StringBuffer();
        sb.append("select GROUP_CONCAT(DISTINCT tg.full_name) goodsNames, GROUP_CONCAT(DISTINCT tg.small_pic) imgUrls  from t_goods tg " +
                "where tg.id in (:goodsIds)");
        List<Map<String, Object>> list = warehouseRkDao.findMapBySql(sb.toString(), goodsIds);
        rk.setGoodsNames(list.get(0).get("goodsNames").toString());
        rk.setImgUrls(list.get(0).get("imgUrls").toString());
        rk.setCreateTime(new Date());
        rk.setCreator(UserUtil.currentAdminUser().getId());
        warehouseRkDao.insert(rk);

        TWarehouseRkDetail rkDetail = null;
        for (CreateInWarehouseDetailDto detail : createInWarehouse.getGoods()) {
            List<String> imeis = goodsImeiDao.findManyBySql("select tgi.imei from t_goods_imei tgi where tgi.imei in (:imeis)",
                    String.class, Arrays.asList(detail.getImeis().split(",")));
            if (!CollectionUtils.isEmpty(imeis)) {
                throw new ServiceException(imeis.get(0)+"imei串号重复");
            }
            rkDetail = new TWarehouseRkDetail();
            rkDetail.setGoodsId(detail.getGoodsId());
            rkDetail.setImeis(detail.getImeis());
            rkDetail.setPurchasePrice(PriceUtil.doubleToInt(detail.getPurchasePrice()));
            rkDetail.setWarehouseRkId(rk.getId());
            saveGoodsImei(detail, rk);
            warehouseRkDetailDao.insert(rkDetail);
        }

        //进行移库操作
        TWarehouseYk tWarehouseYk = new TWarehouseYk();
        StringBuffer sbb = new StringBuffer();
        sbb.append("SELECT tso.org_id FROM t_sys_org tso where tso.org_type = 0");
        GetAllWarehouseId orgId = goodsDao.findOneBySql(sbb.toString(), GetAllWarehouseId.class);
        tWarehouseYk.setYcOrgId(orgId.getOrgId());
        //获取到每个商品的所有串号
        StringBuffer stringBuffer = new StringBuffer();
        for (CreateInWarehouseDetailDto detail : createInWarehouse.getGoods()) {
            stringBuffer.append(detail.getImeis()).append(",");
        }
        if(stringBuffer.length() > 0) {
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }

        tWarehouseYk.setYrOrgId(createInWarehouse.getYrOrgId());
        tWarehouseYk.setImeis(stringBuffer.toString());
        String[] imeis = stringBuffer.toString().split(",");
        String sql = "select GROUP_CONCAT(DISTINCT tg.full_name) goodsNames, GROUP_CONCAT(DISTINCT tg.small_pic) imgUrls from t_goods_imei tgi " +
                "left join t_goods tg on tg.id = tgi.goods_id " +
                "where tgi.imei in (:imeis)";
        List<Map<String, Object>> listt = warehouseCkDao.findMapBySql(sql, Arrays.asList(imeis));
        tWarehouseYk.setGoodsNames(listt.get(0).get("goodsNames").toString());
        tWarehouseYk.setImgUrls(listt.get(0).get("imgUrls").toString());
        tWarehouseYk.setCreateTime(new Date());
        tWarehouseYk.setCreator(UserUtil.currentAdminUser().getId());

        //移库完成后状态 移库状态 1 移库中 2 确认移库
        tWarehouseYk.setStatus(1);
        warehouseYkDao.insert(tWarehouseYk);

        //对串号表状态进行表更
        sql = "UPDATE t_goods_imei tgi SET tgi.status = :status WHERE tgi.imei in (:imeis)";
        warehouseYkDao.executeBySql(sql, ImeiStatus.ZT.getValue(), Arrays.asList(imeis));
        //记录日志
        TGoodsImeiLog tGoodsImeiLog = null;
        String string = null;
        for(int i = 0 ;i<imeis.length;i++){
            tGoodsImeiLog = new TGoodsImeiLog();
            tGoodsImeiLog.setCreateTime(new Date());
            tGoodsImeiLog.setHandleType(ImeiLogType.MOVE_WAREHOUSE.getValue());
            string = "select  DISTINCT tg.full_name ,tgi.purchase_price ,tgi.supplier FROM t_goods_imei tgi left join t_goods tg on tg.id = tgi.goods_id \n" +
                    "where tgi.imei = :imei" ;
            List<InWarehouseLog> inWarehouseLogs = goodsImeiDao.findManyBySql(string,InWarehouseLog.class,imeis[i]);
            tGoodsImeiLog.setCreator(UserUtil.currentAdminUser().getId());
            tGoodsImeiLog.setGoodsName(inWarehouseLogs.get(0).getFullName());
            tGoodsImeiLog.setPurchasePrice(inWarehouseLogs.get(0).getPurchasePrice());
            tGoodsImeiLog.setSupplier(inWarehouseLogs.get(0).getSupplier());
            tGoodsImeiLog.setImei(imeis[i]);
            tGoodsImeiLog.setYcOrgId("总仓");
            tGoodsImeiLog.setYrOrgId(createInWarehouse.getYrOrgIdName());
            //添加串号对应id到日志表中
            StringBuffer sbbbb = new StringBuffer();
            sbbbb.append("select tgi.id from t_goods_imei tgi where tgi.imei = :imei" );
            GetIdByImei getIdByImei = goodsImeiDao.findOneBySql(sbbbb.toString(),GetIdByImei.class,imeis[i]);
            tGoodsImeiLog.setImeiId(getIdByImei.getId());
            goodsImeiLogDao.insert(tGoodsImeiLog);
        }
    }

    /**
     * 保存商品串号信息
     * @param detail
     * @param rk
     */
    private void saveGoodsImei(CreateInWarehouseDetailDto detail, TWarehouseRk rk) {
        TGoodsImei goodsImei = null;
        String string = null;
        StringBuffer sb = new StringBuffer();
        List<Object> list = new ArrayList<>();
        Long goodId = detail.getGoodsId();
        String [] imeis = detail.getImeis().split(",");
        TSysOrg sysOrg = commonService.findGeneralStore();
        //同步t_goods_stock表数据
        sb.append("select tgs.stock from t_goods_stock tgs where  tgs.org_id = :orgId  and tgs.goods_id = :goodId");
        list.add(sysOrg.getId());
        list.add(goodId);
        Integer stock = goodsStockDao.findOneBySql(sb.toString(),Integer.class,list.toArray());
        if(stock == null){
            TGoodsStock tGoodsStock = new TGoodsStock();
            tGoodsStock.setGoodsId(goodId);
            tGoodsStock.setOrgId(sysOrg.getId());
            tGoodsStock.setStock(imeis.length);
            tGoodsStock.setOrderStock(0);
            goodsStockDao.insert(tGoodsStock);
        }
        if(stock != null){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("update t_goods_stock tgs set stock = tgs.stock + :num where  tgs.org_id = :orgId  and tgs.goods_id = :goodId");
            goodsStockDao.executeBySql(stringBuffer.toString(),imeis.length,sysOrg.getId(),goodId);
        }

        for (String imei : detail.getImeis().split(",")) {
            //同步串号表数据
            goodsImei = new TGoodsImei();
            goodsImei.setStatus(2);
            goodsImei.setPurchasePrice(PriceUtil.doubleToInt(detail.getPurchasePrice()));
            goodsImei.setGoodsId(detail.getGoodsId());
            goodsImei.setImei(imei);
            goodsImei.setSupplier(rk.getSupplier());
            //添加门店，默认为总仓
            goodsImei.setOrgId(sysOrg.getId());
            goodsImei.setCreateTime(new Date());
            goodsImei.setLogicDelete(0);
            goodsImei.setCreator(UserUtil.currentAdminUser().getId());
            goodsImeiDao.insert(goodsImei);

            TGoods goods = goodsDao.get(detail.getGoodsId());
            TGoodsImeiLog tGoodsImeiLog = new TGoodsImeiLog();
            tGoodsImeiLog.setHandleType(ImeiLogType.IN_WAREHOUSE.getValue());
            tGoodsImeiLog.setGoodsName(goods.getFullName());
            tGoodsImeiLog.setImei(imei);
            tGoodsImeiLog.setYcOrgId(rk.getSupplier());
            tGoodsImeiLog.setYrOrgId(sysOrg.getName());
            tGoodsImeiLog.setPurchasePrice(PriceUtil.doubleToInt(detail.getPurchasePrice()));
            tGoodsImeiLog.setSupplier(rk.getSupplier());
            tGoodsImeiLog.setCreateTime(new Date());
            tGoodsImeiLog.setCreator(UserUtil.currentAdminUser().getId());
            tGoodsImeiLog.setImeiId(goodsImei.getId());
            goodsImeiLogDao.insert(tGoodsImeiLog);
        }
    }


    public Pagination<GetAllInWarehouseInfo> getAllInWarehouseInfo(Date startDate, Date endDate, Pageable pageable,Long orgId) {
        StringBuffer sb = new StringBuffer();
        ArrayList params = new ArrayList();
        sb.append("SELECT twr.*,tso.`name` FROM t_warehouse_rk twr LEFT JOIN t_sys_org tso on tso.org_id = twr.org_id where 1=1 ");
        if (startDate != null) {
            sb.append(" and twr.create_time >= :startDate");
            params.add(startDate);
        }
        if (endDate != null) {
            sb.append(" and twr.create_time <= :endDate");
            params.add(endDate);
        }
        if(orgId != null){
            sb.append(" and twr.org_id = :orgId ");
            params.add(orgId);
        }
        sb.append(" ORDER BY twr.create_time DESC ");
        Pagination<GetAllInWarehouseInfo> page = warehouseRkDao.findPageBySql(sb.toString(), pageable, GetAllInWarehouseInfo.class, params.toArray());
        return page;
    }

}
