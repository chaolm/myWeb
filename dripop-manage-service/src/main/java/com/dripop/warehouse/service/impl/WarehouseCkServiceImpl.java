package com.dripop.warehouse.service.impl;

import com.bean.ImeiLogType;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.dao.*;
import com.dripop.entity.TGoodsImeiLog;
import com.dripop.entity.TSysOrg;
import com.dripop.entity.TWarehouseCk;
import com.dripop.service.CommonService;
import com.dripop.util.UserUtil;
import com.dripop.warehouse.dto.AllWarehouse.GetAllWarehouseId;
import com.dripop.warehouse.dto.AllWarehouse.GetIdByImei;
import com.dripop.warehouse.dto.AllWarehouse.InWarehouseLog;
import com.dripop.warehouse.dto.CreateMoveWarehouse;
import com.dripop.warehouse.dto.GetAllInWarehouseInfo;
import com.dripop.warehouse.dto.MoveWarehouseInfo;
import com.dripop.warehouse.dto.MoveWarehousePageDto;
import com.dripop.warehouse.service.WarehouseCkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WarehouseCkServiceImpl implements WarehouseCkService{

    @Autowired
    private WarehouseCkDao warehouseCkDao;

    @Autowired
    private GoodsImeiDao goodsImeiDao;

    @Autowired
    private WarehouseRkDao warehouseRkDao;

    @Autowired
    private GoodsImeiLogDao goodsImeiLogDao;

    @Autowired
    private GoodsStockDao goodsStockDao;

    @Autowired
    private CommonService commonService;



    public List<MoveWarehouseInfo> getOutWarehouseInfo(Integer pageNo, Integer id) {
        StringBuffer sb = new StringBuffer();
        ArrayList params = new ArrayList();
        sb.append("SELECT " +
                "twc.goods_names, " +
                "twc.imeis " +
                "FROM " +
                "t_warehouse_ck twc " +
                "WHERE " +
                "twc.id = :id ");
        params.add(id);
        MoveWarehousePageDto moveWarehousePageDto = warehouseCkDao.findOneBySql(sb.toString(), MoveWarehousePageDto.class, params.toArray());
        String name = moveWarehousePageDto.getGoodsNames();
        String imei = moveWarehousePageDto.getImeis();
        String[] names = name.split(",");
        String[] imeis = imei.split(",");
        String sql = null;

        List<MoveWarehouseInfo> moveWarehouseInfos = new ArrayList<>();

        for (int i = 0; i < imeis.length; i++) {
            sql = "SELECT tgi.supplier,tgi.purchase_price ,tg.full_name FROM t_goods_imei tgi  LEFT JOIN t_goods tg  " +
                    "ON  " +
                    "tg.id = tgi.goods_id where tgi.imei = :imei";

            MoveWarehouseInfo moveWarehouseInfo = warehouseCkDao.findOneBySql(sql, MoveWarehouseInfo.class,imeis[i]);
            moveWarehouseInfo.setPurchasePrice(moveWarehouseInfo.getPurchasePrice());
            moveWarehouseInfo.setSupplier(moveWarehouseInfo.getSupplier());
            moveWarehouseInfo.setFullName(moveWarehouseInfo.getFullName());
            moveWarehouseInfo.setImei(imeis[i]);
            moveWarehouseInfos.add(moveWarehouseInfo);

        }


        return moveWarehouseInfos;
    }



    public Pagination<GetAllInWarehouseInfo> getAllOutWarehouseInfo(Date startDate, Date endDate, Pageable pageable,Long ycOrgId) {
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<>();
        sb.append("SELECT\n" +
                "twc.id,twc.goods_names, twc.create_time, twc.img_urls,tso.`name` \n" +
                "FROM \n" +
                "t_warehouse_ck twc LEFT JOIN t_sys_org tso on tso.org_id = twc.org_id where 1 =1 ");
        if (startDate != null) {
            sb.append(" and twc.create_time >= :startDate");
            params.add(startDate);
        }
        if (endDate != null) {
            sb.append(" and twc.create_time <= :endDate");
            params.add(endDate);
        }
        if(ycOrgId != null){
            sb.append(" and twc.org_id = :orgId ");
            params.add(ycOrgId);
        }
        sb.append(" ORDER BY twc.create_time DESC");
        Pagination<GetAllInWarehouseInfo> page = warehouseCkDao.findPageBySql(sb.toString(), pageable, GetAllInWarehouseInfo.class, params.toArray());

        return page;
    }


    @Transactional
    public void saveOutWarehouse(CreateMoveWarehouse createOutWarehouse) {
        TWarehouseCk tWarehouseCk = new TWarehouseCk();
        tWarehouseCk.setImeis(createOutWarehouse.getImeis());

        String[] imeis = createOutWarehouse.getImeis().split(",");
        String sql = "select GROUP_CONCAT(DISTINCT tg.full_name) goodsNames, GROUP_CONCAT(DISTINCT tg.small_pic) imgUrls from t_goods_imei tgi " +
                "left join t_goods tg on tg.id = tgi.goods_id " +
                "where tgi.imei in (:imeis)";
        List<Map<String, Object>> map = warehouseCkDao.findMapBySql(sql, Arrays.asList(imeis));
        tWarehouseCk.setGoodsNames(map.get(0).get("goodsNames").toString());
        tWarehouseCk.setImgUrls(map.get(0).get("imgUrls").toString());
        tWarehouseCk.setCreateTime(new Date());
        tWarehouseCk.setCreator(UserUtil.currentAdminUser().getId());

        warehouseCkDao.insert(tWarehouseCk);

        //进行逻辑删除
        sql = "UPDATE t_goods_imei tgi SET tgi.logic_delete = 1 WHERE tgi.imei in (:imeis)";
        warehouseCkDao.executeBySql(sql, Arrays.asList(imeis));

        //同步t_goods_stock表数据
        sql = "select tgi.goods_id FROM t_goods_imei tgi where tgi.imei in (:imeis)";
        TSysOrg sysOrg = commonService.findGeneralStore();
        List<Long> goodsId= goodsImeiDao.findManyBySql(sql,Long.class,Arrays.asList(imeis));
        for(Long result : goodsId){
            sql = "update t_goods_stock tgs set  stock = tgs.stock - :num where tgs.org_id = :orgId  and tgs.goods_id = :goodId";
            goodsStockDao.executeBySql(sql,1,sysOrg.getId(),result);
        }

        //日志记录
        TGoodsImeiLog tGoodsImeiLog = null;
        StringBuffer sbb = new StringBuffer();
        String string = null;
        for(int i = 0;i < imeis.length;i++){
            tGoodsImeiLog = new TGoodsImeiLog();
            tGoodsImeiLog.setHandleType(ImeiLogType.OUT_WAREHOUSE.getValue());
            string = "select  DISTINCT tg.full_name ,tgi.purchase_price ,tgi.supplier FROM t_goods_imei tgi left join t_goods tg on tg.id = tgi.goods_id \n" +
                "where tgi.imei =:imei " ;
            List<InWarehouseLog> inWarehouseLogs = goodsImeiDao.findManyBySql(string,InWarehouseLog.class,imeis[i]);
            tGoodsImeiLog.setGoodsName(inWarehouseLogs.get(0).getFullName());
            tGoodsImeiLog.setCreateTime(new Date());
            tGoodsImeiLog.setCreator(UserUtil.currentAdminUser().getId());
            tGoodsImeiLog.setYcOrgId("总仓");
            tGoodsImeiLog.setPurchasePrice(inWarehouseLogs.get(0).getPurchasePrice());
            tGoodsImeiLog.setSupplier(inWarehouseLogs.get(0).getSupplier());
            tGoodsImeiLog.setImei(imeis[i]);
            //添加串号对应id到日志表中
            StringBuffer sbbbb = new StringBuffer();
            sbbbb.append("select tgi.id from t_goods_imei tgi where tgi.imei = :imei" );
            GetIdByImei getIdByImei = goodsImeiDao.findOneBySql(sbbbb.toString(),GetIdByImei.class,imeis[i]);
            tGoodsImeiLog.setImeiId(getIdByImei.getId());
            goodsImeiLogDao.insert(tGoodsImeiLog);
        }
    }

}
