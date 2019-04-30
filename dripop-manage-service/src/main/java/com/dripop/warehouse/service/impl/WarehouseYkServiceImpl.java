package com.dripop.warehouse.service.impl;

import com.bean.ImeiLogType;
import com.bean.ImeiStatus;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.dao.*;
import com.dripop.entity.TGoodsImeiLog;
import com.dripop.entity.TGoodsStock;
import com.dripop.entity.TSysOrg;
import com.dripop.entity.TWarehouseYk;
import com.dripop.service.CommonService;
import com.dripop.util.UserUtil;
import com.dripop.warehouse.dto.AllWarehouse.GetAllWarehouseId;
import com.dripop.warehouse.dto.AllWarehouse.GetIdByImei;
import com.dripop.warehouse.dto.AllWarehouse.InWarehouseLog;
import com.dripop.warehouse.dto.CreateMoveWarehouse;
import com.dripop.warehouse.dto.GetAllMoveWarehouseInfo;
import com.dripop.warehouse.dto.MoveWarehouseInfo;
import com.dripop.warehouse.dto.MoveWarehousePageDto;
import com.dripop.warehouse.service.WarehouseYkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WarehouseYkServiceImpl implements WarehouseYkService {
    @Autowired
    private WarehouseYkDao warehouseYkDao;

    @Autowired
    private WarehouseCkDao warehouseCkDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private SysOrgDao sysOrgDao;

    @Autowired
    private GoodsImeiDao goodsImeiDao;

    @Autowired
    private GoodsImeiLogDao goodsImeiLogDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private GoodsStockDao goodsStockDao;

    public List<MoveWarehouseInfo> getMoveWarehouseInfo(Integer pageNo, Integer id) {
        StringBuffer sb = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        sb.append("select  twy.goods_names, twy.imeis,twy.status,twy.yr_org_id from t_warehouse_yk twy where twy.id = :id");
        params.add(id);
        MoveWarehousePageDto page = warehouseYkDao.findOneBySql(sb.toString(), MoveWarehousePageDto.class, params.toArray());
        String[] imei = page.getImeis().split(",");

        String sql = "select tgi.supplier,tgi.purchase_price ,tg.full_name,tgi.imei from t_goods_imei tgi LEFT JOIN t_goods tg on tg.id = tgi.goods_id where tgi.imei in (:imei)";
        List<MoveWarehouseInfo> moveWarehouseInfos = warehouseYkDao.findManyBySql(sql, MoveWarehouseInfo.class,Arrays.asList(imei));
        return moveWarehouseInfos;
    }

    @Transactional
    public void confirmAndCancelMove(Long id, Integer status) {
        String string = null;
        List<Object> params = new ArrayList<>();
        string = "SELECT twy.imeis FROM t_warehouse_yk twy where twy.id = :id";
        params.add(id);
        String imei = warehouseYkDao.findOneBySql(string, String.class, params.toArray());
        String[] imeis = imei.split(",");
        //1代表取消移出
        if (status == 1) {
            //更改串号表状态
            string = "update t_goods_imei tgi set tgi.`status` = :status where tgi.imei in (:imeis)";
            goodsImeiDao.executeBySql(string,ImeiStatus.ZK.getValue(), Arrays.asList(imeis));
            //删除移库单记录
            warehouseYkDao.delete(id);
            //删除记录表
            string = "delete from t_goods_imei_log where handle_type = :status and imei in (:imeis)";
            goodsImeiLogDao.executeBySql(string,ImeiLogType.MOVE_WAREHOUSE.getValue(),Arrays.asList(imeis));
        }
        //2代表确认移入
        if (status == 2) {
            String sql = null;
            //修改门店,串号状态
            TSysOrg sysOrg = commonService.findGeneralStore();
            string ="update t_goods_imei tgi set tgi.org_id = :orgId , tgi.status = :status where tgi.imei in (:imeis)";
            List<Object> param = new ArrayList<>();
            goodsImeiDao.executeBySql(string,sysOrg.getId(),ImeiStatus.ZK.getValue(),Arrays.asList(imeis));
            //修改移库单中状态
            string = "update t_warehouse_yk twy  set twy.`status` = 2 where twy.id = :id ";
            warehouseYkDao.executeBySql(string,id);
            //同步t_goods_stock表数据
            sql = "select twy.yc_org_id from t_warehouse_yk twy where twy.id = :ycOrgId ";
            Long ycOrgid = warehouseYkDao.findOneBySql(sql,Long.class,id);
            sql = "select tgi.goods_id FROM t_goods_imei tgi where tgi.imei in (:imeis)";
            List<Long> goodsId= goodsImeiDao.findManyBySql(sql,Long.class,Arrays.asList(imeis));
            //stock表进行数据同步先校验是否存在此总仓此商品
            for(Long result :goodsId){
                StringBuffer sbStock = new StringBuffer();
                List<Object> listStock = new ArrayList<>();
                sbStock.append("select tgs.stock from t_goods_stock tgs where  tgs.org_id = :orgId  and tgs.goods_id = :goodId");
                listStock.add(sysOrg.getId());
                listStock.add(result);
                Integer stock = goodsStockDao.findOneBySql(sbStock.toString(),Integer.class,listStock.toArray());
                if(stock == null){
                    TGoodsStock tGoodsStock = new TGoodsStock();
                    tGoodsStock.setGoodsId(result);
                    tGoodsStock.setOrgId(sysOrg.getId());
                    tGoodsStock.setStock(1);
                    tGoodsStock.setOrderStock(0);
                    goodsStockDao.insert(tGoodsStock);
                    sql = "update t_goods_stock tgs set  stock = tgs.stock - :num where tgs.org_id = :orgId  and tgs.goods_id = :goodId";
                    goodsStockDao.executeBySql(sql,1,ycOrgid,result);
                }
                if(stock != null){
                        //对仓库库存做修改
                        sql = "update t_goods_stock tgs set  stock = tgs.stock + :num where tgs.org_id = :orgId  and tgs.goods_id = :goodId";
                        goodsStockDao.executeBySql(sql,1,sysOrg.getId(),result);
                        sql = "update t_goods_stock tgs set  stock = tgs.stock - :num where tgs.org_id = :orgId  and tgs.goods_id = :goodId";
                        goodsStockDao.executeBySql(sql,1,ycOrgid,result);
                }
            }
        }
    }

    public Pagination<GetAllMoveWarehouseInfo> getAllMoveWarehouseInfo(Date startDate, Date endDate, Pageable pageable,Long ycOrgId,Long yrOrgId) {
        StringBuffer sb = new StringBuffer();
        ArrayList params = new ArrayList();
        sb.append("SELECT twy.id, twy.create_time,twy.goods_names, twy.imeis,twy.img_urls,twy. STATUS,twy.yr_org_id ,twy.yc_org_id, \n" +
                "(SELECT tso.`name`  FROM t_sys_org tso where tso.org_id= twy.yr_org_id) rkName, " +
                "(SELECT tso.`name`  FROM t_sys_org tso where tso.org_id= twy.yc_org_id) ckName " +
                "FROM t_warehouse_yk twy LEFT JOIN t_sys_org  tso on twy.yc_org_id=tso.org_id and twy.yr_org_id =tso.org_id\n" +
                "where  1 = 1 ");
        if(ycOrgId != null){
            sb.append(" and twy.yc_org_id = :ycOrgId ");
            params.add(ycOrgId);
        }
        if(yrOrgId != null){
            sb.append(" and twy.yr_org_id = :yrOrgId");
            params.add(yrOrgId);
        }
        if (startDate != null) {
            sb.append(" and twy.create_time >= :startDate");
            params.add(startDate);
        }
        if (endDate != null) {
            sb.append(" and twy.create_time <= :endDate");
            params.add(endDate);

        }
        sb.append(" ORDER BY twy.create_time DESC ");
        Pagination<GetAllMoveWarehouseInfo> page = warehouseYkDao.findPageBySql(sb.toString(), pageable, GetAllMoveWarehouseInfo.class, params.toArray());
        List<GetAllMoveWarehouseInfo> lists = page.getItems();
        return page;

    }

    @Transactional
    public void saveMoveWarehouse(CreateMoveWarehouse createMoveWarehouse) {
        TWarehouseYk tWarehouseYk = new TWarehouseYk();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT tso.org_id FROM t_sys_org tso where tso.org_type = 0");
        GetAllWarehouseId orgId = goodsDao.findOneBySql(sb.toString(), GetAllWarehouseId.class);
        tWarehouseYk.setYcOrgId(orgId.getOrgId());
        tWarehouseYk.setYrOrgId(createMoveWarehouse.getYrOrgId());
        tWarehouseYk.setImeis(createMoveWarehouse.getImeis());
        String[] imeis = createMoveWarehouse.getImeis().split(",");
        String sql = "select GROUP_CONCAT(DISTINCT tg.full_name) goodsNames, GROUP_CONCAT(DISTINCT tg.small_pic) imgUrls from t_goods_imei tgi " +
                "left join t_goods tg on tg.id = tgi.goods_id " +
                "where tgi.imei in (:imeis)";
        List<Map<String, Object>> list = warehouseCkDao.findMapBySql(sql, Arrays.asList(imeis));
        tWarehouseYk.setGoodsNames(list.get(0).get("goodsNames").toString());
        tWarehouseYk.setImgUrls(list.get(0).get("imgUrls").toString());
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
        for (int i = 0; i < imeis.length; i++) {
            tGoodsImeiLog = new TGoodsImeiLog();
            tGoodsImeiLog.setCreateTime(new Date());
            tGoodsImeiLog.setHandleType(ImeiLogType.MOVE_WAREHOUSE.getValue());
            string = "select  DISTINCT tg.full_name ,tgi.purchase_price ,tgi.supplier FROM t_goods_imei tgi left join t_goods tg on tg.id = tgi.goods_id \n" +
                    "where tgi.imei = :imei " ;
            List<InWarehouseLog> inWarehouseLogs = goodsImeiDao.findManyBySql(string, InWarehouseLog.class,imeis[i]);
            tGoodsImeiLog.setCreator(UserUtil.currentAdminUser().getId());
            tGoodsImeiLog.setGoodsName(inWarehouseLogs.get(0).getFullName());
            tGoodsImeiLog.setPurchasePrice(inWarehouseLogs.get(0).getPurchasePrice());
            tGoodsImeiLog.setSupplier(inWarehouseLogs.get(0).getSupplier());
            tGoodsImeiLog.setImei(imeis[i]);
            tGoodsImeiLog.setYcOrgId("总仓");
            tGoodsImeiLog.setYrOrgId(createMoveWarehouse.getYrOrgIdName());
            //添加串号对应id到日志表中
            StringBuffer sbbbb = new StringBuffer();
            sbbbb.append("select tgi.id from t_goods_imei tgi where tgi.imei = :imei " );
            GetIdByImei getIdByImei = goodsImeiDao.findOneBySql(sbbbb.toString(), GetIdByImei.class,imeis[i]);
            tGoodsImeiLog.setImeiId(getIdByImei.getId());
            goodsImeiLogDao.insert(tGoodsImeiLog);
        }
    }


}
