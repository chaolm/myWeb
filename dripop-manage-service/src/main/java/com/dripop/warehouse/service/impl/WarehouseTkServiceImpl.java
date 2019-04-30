package com.dripop.warehouse.service.impl;

import com.bean.ImeiLogType;
import com.bean.ImeiStatus;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.*;
import com.dripop.entity.TGoodsImeiLog;
import com.dripop.entity.TSysOrg;
import com.dripop.entity.TWarehouseTk;
import com.dripop.entity.TWarehouseTkDetail;
import com.dripop.service.CommonService;
import com.dripop.util.PriceUtil;
import com.dripop.util.UserUtil;
import com.dripop.warehouse.dto.*;
import com.dripop.warehouse.dto.AllWarehouse.GetAjustWarehouse;
import com.dripop.warehouse.dto.AllWarehouse.GetIdByImei;
import com.dripop.warehouse.service.WarehouseTkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WarehouseTkServiceImpl implements WarehouseTkService {

    @Autowired
    private WarehouseTkDao warehouseTkDao;

    @Autowired
    private WarehouseTkDetailDao warehouseTkDetailDao;

    @Autowired
    private GoodsImeiDao goodsImeiDao;

    @Autowired
    private GoodsImeiLogDao goodsImeiLogDao;

    @Autowired
    private SysOrgDao sysOrgDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private GoodsStockDao goodsStockDao;

    @Override
    public List<GetImeiChangeInfo> getAjustWarehouseInfo(Long id) {

        StringBuffer sb = new StringBuffer();
        List<Object> param = new ArrayList<>();
        sb.append("select tg.full_name, twtd.old_purchase_price,twtd.old_supplier,twtd.old_imei,\n" +
                "twtd.new_purchase_price,twtd.new_supplier,twtd.new_imei ,twtd.type from t_warehouse_tk_detail twtd \n" +
                "left join t_goods_imei tgi on tgi.imei = twtd.new_imei \n" +
                "left join t_goods tg on tg.id = tgi.goods_id \n" +
                "where twtd.warehouse_tk_id = :id order by twtd.type");
        param.add(id);
        return warehouseTkDetailDao.findManyBySql(sb.toString(), GetImeiChangeInfo.class, param.toArray());

    }

    @Transactional

    public void saveAjustWarehouse(AjustWarehouseDto ajustWarehouseDto) {
        TWarehouseTk tWarehouseTk = new TWarehouseTk();
        TSysOrg sysOrg = commonService.findGeneralStore();
        StringBuffer sb = new StringBuffer();
        List<String> imeiList = new ArrayList<>();
        if(StringUtil.isNotBlank(ajustWarehouseDto.getImeis())) {
            for (String imei : ajustWarehouseDto.getImeis().split(",")) {
                imeiList.add(imei);
            }
        }
        //检验前台传入新串号是否之间重复
        List<Object>  imeiRepeat = new ArrayList<>();
        if(ajustWarehouseDto.getChangeImeis() != null && ajustWarehouseDto.getChangeImeis().size() > 0) {
            for (AjustWarehouseChangeDto changeDto : ajustWarehouseDto.getChangeImeis()) {
                imeiList.add(changeDto.getOldImei());
                imeiRepeat.add(changeDto.getNewImei());
            }
        }
        //检验前台传入新串号是否之间重复
        List<Object> imeiRepeatAfter = removeDuplicate(imeiRepeat);
        if(imeiRepeat.size() != imeiRepeatAfter.size()){
            throw  new ServiceException("输入新串号重复");
        }
        if(imeiRepeat.size() > 0) {
            //检验仓库中的重复数据
            StringBuffer sbCheck = new StringBuffer();
            sbCheck.append(" select imei from t_goods_imei tgi where tgi.imei in ( :imeiCheck) ");
            List<String> imeisSult = goodsImeiDao.findManyBySql(sbCheck.toString(), String.class, imeiRepeat);
            if (imeisSult.size() > 0) {
                throw new ServiceException("输入新串号重复");
            }
        }
        sb.append("select GROUP_CONCAT(DISTINCT tg.full_name) goodsNames,GROUP_CONCAT(DISTINCT tg.small_pic) imgUrls  from t_goods_imei tgi " +
                "left join t_goods tg on tg.id = tgi.goods_id " +
                "where tgi.imei in ( :imeis)");
        List<Map<String, Object>> list = warehouseTkDao.findMapBySql(sb.toString(),imeiList);

        //根据imei查图片 名称
        tWarehouseTk.setGoodsNames(list.get(0).get("goodsNames").toString());
        tWarehouseTk.setImgUrls(list.get(0).get("imgUrls").toString());
        tWarehouseTk.setCreateTime(new Date());
        tWarehouseTk.setCreator(UserUtil.currentAdminUser().getId());
        tWarehouseTk.setOrgId(sysOrg.getId());
        warehouseTkDao.insert(tWarehouseTk);

        TWarehouseTkDetail tkDetail = null;
        String sql = null;
        TGoodsImeiLog tGoodsImeiLog = null;
        //串号恢复
        if (!ajustWarehouseDto.getImeis().isEmpty()) {
            for (String imei : ajustWarehouseDto.getImeis().split(",")) {
                tkDetail = new TWarehouseTkDetail();
                tkDetail.setWarehouseTkId(tWarehouseTk.getId());
                tkDetail.setNewImei(imei);
                //根据串号找到对应旧的供货商、原价、名称
                sql = "SELECT tgi.purchase_price , tgi.supplier ,tg.full_name , tgi.org_id ,tgi.goods_id FROM\n" +
                        " t_goods_imei tgi LEFT JOIN t_goods tg on tgi.goods_id = tg.id where tgi.imei = :imei" ;
                List<GetInfoByImei> getInfoByImei = warehouseTkDao.findManyBySql(sql, GetInfoByImei.class,imei);
                tkDetail.setNewPurchasePrice(getInfoByImei.get(0).getPurchasePrice());
                tkDetail.setNewSupplier(getInfoByImei.get(0).getSupplier());
                //2:串号恢复
                tkDetail.setType(2);
                warehouseTkDetailDao.insert(tkDetail);
                //串号恢复同步stock表
                sql = "update t_goods_stock tgs set  stock = tgs.stock + :num where tgs.org_id = :orgId  and tgs.goods_id = :goodId";
                goodsStockDao.executeBySql(sql,1,getInfoByImei.get(0).getOrgId(),getInfoByImei.get(0).getGoodsId());
                //日志记录
                Long OrId = getInfoByImei.get(0).getOrgId();
                sql = "select tso.`name` from t_sys_org tso where tso.org_id = " + OrId;

                GetInfoByImei getInfoByImei1 = sysOrgDao.findOneBySql(sql, GetInfoByImei.class);
                tGoodsImeiLog = new TGoodsImeiLog();
                tGoodsImeiLog.setCreator(UserUtil.currentAdminUser().getId());
                tGoodsImeiLog.setHandleType(ImeiLogType.IMEIS_RECOVER.getValue());
                tGoodsImeiLog.setCreateTime(new Date());
                tGoodsImeiLog.setGoodsName(getInfoByImei.get(0).getFullName());
                tGoodsImeiLog.setYcOrgId(getInfoByImei1.getName());
                tGoodsImeiLog.setYrOrgId(getInfoByImei1.getName());
                tGoodsImeiLog.setPurchasePrice(getInfoByImei.get(0).getPurchasePrice());
                tGoodsImeiLog.setSupplier(getInfoByImei.get(0).getSupplier());
                tGoodsImeiLog.setImei(imei);
                //添加串号对应id到日志表中
                StringBuffer sbbbb = new StringBuffer();
                sbbbb.append("select tgi.id from t_goods_imei tgi where tgi.imei = :imei"  );
                GetIdByImei getIdByImei = goodsImeiDao.findOneBySql(sbbbb.toString(), GetIdByImei.class,imei);
                tGoodsImeiLog.setImeiId(getIdByImei.getId());
                goodsImeiLogDao.insert(tGoodsImeiLog);
            }
        }
        //串号恢复后恢复状态
        String[] imeiss = ajustWarehouseDto.getImeis().split(",");
        String strings = "update t_goods_imei tgi set tgi.`status` = :status where tgi.imei in (:imeiss)";
        goodsImeiDao.executeBySql(strings, ImeiStatus.ZK.getValue(), Arrays.asList(imeiss));
        //串号更换
        if (!ajustWarehouseDto.getChangeImeis().isEmpty()) {
            for (AjustWarehouseChangeDto changeDto : ajustWarehouseDto.getChangeImeis()) {
                tkDetail = new TWarehouseTkDetail();
                tkDetail.setNewImei(changeDto.getNewImei());
                tkDetail.setNewSupplier(changeDto.getNewSupplier());
                tkDetail.setNewPurchasePrice(PriceUtil.doubleToInt(changeDto.getNewPurchasePrice()));
                tkDetail.setWarehouseTkId(tWarehouseTk.getId());
                String oldImei = changeDto.getOldImei();
                tkDetail.setOldImei(oldImei);
                //根据串号找到对应旧的供货商、原价、名称
                sql = "SELECT tgi.purchase_price , tgi.supplier,tg.full_name , tgi.org_id  FROM\n" +
                        " t_goods_imei tgi LEFT JOIN t_goods tg on tgi.goods_id = tg.id where tgi.imei = :oldImei" ;
                List<GetInfoByImei> getInfoByImei = warehouseTkDao.findManyBySql(sql, GetInfoByImei.class,oldImei);
                tkDetail.setOldPurchasePrice(getInfoByImei.get(0).getPurchasePrice());
                tkDetail.setOldSupplier(getInfoByImei.get(0).getSupplier());
                //1:串号更换
                tkDetail.setType(1);
                warehouseTkDetailDao.insert(tkDetail);
                //日志记录
                Long OrId = getInfoByImei.get(0).getOrgId();
                sql = "select tso.`name` from t_sys_org tso where tso.org_id = :orid"  ;

                GetInfoByImei getInfoByImei1 = sysOrgDao.findOneBySql(sql, GetInfoByImei.class,OrId);
                tGoodsImeiLog = new TGoodsImeiLog();
                tGoodsImeiLog.setCreator(UserUtil.currentAdminUser().getId());
                tGoodsImeiLog.setHandleType(ImeiLogType.IMEIS_CHAGNGE.getValue());
                tGoodsImeiLog.setCreateTime(new Date());
                tGoodsImeiLog.setGoodsName(getInfoByImei.get(0).getFullName());
                tGoodsImeiLog.setYcOrgId(getInfoByImei1.getName());
                tGoodsImeiLog.setYrOrgId(getInfoByImei1.getName());
                tGoodsImeiLog.setPurchasePrice(PriceUtil.doubleToInt(changeDto.getNewPurchasePrice()));
                tGoodsImeiLog.setSupplier(changeDto.getNewSupplier());
                tGoodsImeiLog.setImei(changeDto.getNewImei());
                //添加串号对应id到日志表中
                StringBuffer sbbbb = new StringBuffer();
                sbbbb.append("select tgi.id from t_goods_imei tgi where tgi.imei = :imei" );
                GetIdByImei getIdByImei = goodsImeiDao.findOneBySql(sbbbb.toString(), GetIdByImei.class,oldImei);
                tGoodsImeiLog.setImeiId(getIdByImei.getId());
                goodsImeiLogDao.insert(tGoodsImeiLog);
            }
        }
        //串号更换、进价跟换、供货商更换
        String string = null;
        for (AjustWarehouseChangeDto changeDto : ajustWarehouseDto.getChangeImeis()) {
            String oldImei = changeDto.getOldImei();
            String newImei = changeDto.getNewImei();
            String newSupplier = changeDto.getNewSupplier();
            Integer newPurchasePrice = PriceUtil.doubleToInt(changeDto.getNewPurchasePrice());
            string = "update t_goods_imei set supplier = :newSupplier,purchase_price = :newPurchasePrice,imei = :newImei where imei = :oldImei";
            goodsImeiDao.executeBySql(string, newSupplier, newPurchasePrice, newImei, oldImei);
        }
    }

    @Override
    public Pagination<GetAjustWarehouseReq> getAllAjustWarehouseInfo(Pageable pageNo, Date startDate, Date endDate,Long orgId) {
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<>();
        sb.append("SELECT twt.id ,twt.img_urls,twt.goods_names,twt.create_time,tso.`name` \n" +
                "FROM t_warehouse_tk twt \n" +
                "LEFT JOIN t_sys_org tso \n" +
                "on tso.org_id = twt.org_id where 1 = 1 ");
        if (startDate != null) {
            sb.append(" and twt.create_time >= :startDate");
            params.add(startDate);
        }
        if (endDate != null) {
            sb.append(" and twt.create_time <= :endDate");
            params.add(endDate);
        }
        if(orgId != null){
            sb.append(" and twt.org_id = :orgId");
            params.add(orgId);
        }
        sb.append(" ORDER BY twt.create_time DESC ");
        Pagination<GetAjustWarehouseReq> page = warehouseTkDao.findPageBySql(sb.toString(), pageNo, GetAjustWarehouseReq.class, params.toArray());
        return page;
    }

    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }
}
