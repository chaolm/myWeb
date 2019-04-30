package com.dripop.dispatchcenter.service.impl;

import com.bean.LogicDelete;
import com.bean.OrderStatus;
import com.bean.ShoppingModel;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.*;
import com.dripop.dispatchcenter.dto.*;
import com.dripop.dispatchcenter.service.DispatchCenterInfoService;
import com.dripop.entity.TGoodsStock;
import com.dripop.entity.TOrderTrack;
import com.dripop.entity.TSysOrg;
import com.dripop.util.OrderUtil;
import com.dripop.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DispatchCenterInfoServiceImpl implements DispatchCenterInfoService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private GoodsImeiDao goodsImeiDao;

    @Autowired
    private GoodsStockDao goodsStockDao;

    @Autowired
    private OrderTrackDao orderTrackDao;

    @Autowired
    private SysOrgDao sysOrgDao;

    @Override
    public Pagination<GetDispatchInfo> getDispatchCenterInfo(GetDispatchCenterReq requDto, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> param = new ArrayList<>();
        Long sysOrg = UserUtil.currentAdminUser().getId();
        sb.append("SELECT\n" +
                "tot.create_time,tot.real_pay,tot.pay_model,\n" +
                "tot.cust_id,tc.phone_no,tso.`name`,tot.org_id,tot.order_no,tot.id,\n" +
                "tot.send_time,\n" +
                "GROUP_CONCAT(tg.full_name) goodsNames,\n" +
                "GROUP_CONCAT(tg.small_pic) imgUrls,\n" +
                "tod.package_id\n" +
                "FROM t_order tot LEFT JOIN t_sys_org tso on tso.org_id = tot.org_id \n" +
                "LEFT JOIN t_customer tc on tc.id = tot.cust_id\n" +
                "LEFT JOIN t_order_detail  tod on tod.order_id = tot.id\n" +
                "LEFT JOIN t_goods tg on tg.id = tod.spu_id " +
                "where tot.status = :status and tot.shipping_model = :shipping_model and tot.logic_delete = :logicDelete ");
        param.add(OrderStatus.WAIT_DELIVERY.getValue());
        param.add(ShoppingModel.DELIVERY.getValue());
        param.add(LogicDelete.NOT_DELETE.getValue());
        if (StringUtil.isNotBlank(requDto.getOrderNo())) {
            sb.append(" and tot.order_no = :orderNo");
            param.add(requDto.getOrderNo());
        }
        if (requDto.getStartTime() != null) {
            sb.append(" and tot.create_time >= :startTime");
            param.add(requDto.getStartTime());
        }
        if (requDto.getEndTime() != null) {
            sb.append(" and tot.create_time <= :endTime");
            param.add(requDto.getEndTime());
        }
        if (requDto.getRealMinPay() != null) {
            sb.append(" and tot.real_pay >= :realMinPay");
            param.add(requDto.getRealMinPay());
        }
        if (requDto.getRealMaxPay() != null) {
            sb.append(" and tot.real_pay <= :realMaxPay");
            param.add(requDto.getRealMaxPay());
        }
        if (requDto.getPayModel() != null) {
            sb.append(" and tot.pay_model = :payModel");
            param.add(requDto.getPayModel());
        }
        if (requDto.getOrgId() != null) {
            sb.append(" and tot.org_id = :orgId");
            param.add(requDto.getOrgId());
        }
        if (StringUtil.isNotBlank(requDto.getPhoneNo())) {
            sb.append(" and tc.phone_no = :phoneNo");
            param.add(requDto.getPhoneNo());
        }
        if (StringUtil.isNotBlank(requDto.getFullName())) {
            sb.append(" and tg.full_name like :fullName ");
            param.add("%" + requDto.getFullName() + "%");
        }
        //过滤派单类型
        //1 待派单 2 已派单（可改派）3 已派单（不可改派）
/*        if (requDto.getStatus() != null && requDto.getStatus() == 1) {
            sb.append(" and tot.org_id = :sysOrg ");
            param.add(sysOrg.getId());
        }*/
        if (requDto.getStatus() != null && requDto.getStatus() == 2) {
            sb.append(" and tot.send_time is NULL ");
        }
        if (requDto.getStatus() != null && requDto.getStatus() == 3) {
            sb.append(" and tot.send_time is NOT NULL");
        }
        sb.append(" GROUP BY tot.id ORDER BY tot.create_time DESC ");
        Pagination<GetDispatchInfo> list = orderDao.findPageBySql(sb.toString(), pageable, GetDispatchInfo.class, param.toArray());
        //返回派单类型
        dispatchStatus(sysOrg, list);
        return list;
    }

    private void dispatchStatus(Long sysOrg, Pagination<GetDispatchInfo> list) {
        StringBuffer sb = null;
        List<Object> list2 = null;
        List<GetDispatchInfo> list1 = list.getItems();
        //2：已派单（可改派） 3已派单（不可改派）
        for (GetDispatchInfo listt : list1) {
            if (listt.getSendTime() == null && !listt.getOrgId().equals(sysOrg)) {
                listt.setStatus(2);
            } else if (listt.getSendTime() != null) {
                listt.setStatus(3);
            }
            //判断是否为套装
            if (listt.getPackageId() != null) {
                sb = new StringBuffer();
                list2 = new ArrayList<>();
                sb.append("select tcp.name,tg.small_pic FROM t_order_detail tod \n" +
                        "LEFT JOIN t_combo_package  tcp on tcp.id = tod.package_id \n" +
                        "LEFT JOIN t_goods_online tgo on tgo.online_id = tcp.online_id \n" +
                        "LEFT JOIN t_goods tg on tg.id = tgo.spu_id where tod.package_id = :packageId and tod.order_id = :orderId ");
                list2.add(listt.getPackageId());
                list2.add(listt.getId());
                DispatchCenterShopInfo dispatchCenterShopInfo = orderDetailDao.findOneBySql(sb.toString(), DispatchCenterShopInfo.class, list2.toArray());
                listt.setGoodsNames(dispatchCenterShopInfo.getName());
                listt.setImgUrls(dispatchCenterShopInfo.getSmallPic());
            }
        }
    }

    @Override
    public List<DispatchShopInfo> queryStock(OrderId orderId, Long orgId) {
        //查询订单中去重后所有的goodid
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<>();
        sb.append("select DISTINCT tod.spu_id from t_order_detail tod where tod.order_id in (:orderId)");
        params.add(orderId.getLongs());
        List<Long> longs = orderDetailDao.findManyBySql(sb.toString(), Long.class, params.toArray());
        //根据订单id查询所有商品信息
        StringBuffer sbb = new StringBuffer();
        List<Object> param = new ArrayList<>();
        sbb.append("select tod.spu_id, tod.num ,tg.full_name from t_order_detail tod \n" +
                "LEFT JOIN t_goods tg on tod.spu_id = tg.id where  tod.order_id in (:orderId)");
        param.add(orderId.getLongs());
        List<DispatchShopInfo> dispatchShopInfo = orderDetailDao.findManyBySql(sbb.toString(), DispatchShopInfo.class, param.toArray());
        //最终返回集合
        List<DispatchShopInfo> lists = new ArrayList<>();
        //循环比较goodid进行数量计算
        for (Long goodId : longs) {
            Integer sum = 0;
            StringBuffer stringBuffer = new StringBuffer();
            List<Object> list = new ArrayList<>();
            stringBuffer.append("select tg.full_name,tg.small_pic,tg.id from t_goods tg where tg.id = :goodId");
            list.add(goodId);
            //最终返回数据dispatchShopInfoo
            DispatchShopInfo dispatchShopInfoo = orderDetailDao.findOneBySql(stringBuffer.toString(), DispatchShopInfo.class, list.toArray());
            for (DispatchShopInfo dispatchShopInfo1 : dispatchShopInfo) {
                if (goodId.equals(dispatchShopInfo1.getSpuId())) {
                    sum = sum + dispatchShopInfo1.getNum();
                    dispatchShopInfoo.setNum(sum);
                }
            }
            lists.add(dispatchShopInfoo);
        }
        //比较库存
        String sql = null;
        List<Object> paramm = null;
        for (int i = 0; i < longs.size(); i++) {
            paramm = new ArrayList<>();
            sql = "select tgi.imei from t_goods_imei  tgi where tgi.status = 2 and tgi.goods_id = :goodsId and tgi.org_id = :orgId and tgi.logic_delete = :logicDelete";
            paramm.add(longs.get(i));
            paramm.add(orgId);
            paramm.add(LogicDelete.NOT_DELETE.getValue());
            List<Imei> listss = goodsImeiDao.findManyBySql(sql, Imei.class, paramm.toArray());
            if (listss.size() >= lists.get(i).getNum()) {
                lists.get(i).setStock("现货");
            } else {
                lists.get(i).setStock("可预定");
            }
        }
        return lists;
    }

    @Transactional
    public void submitDispatcher(OrderId orderId, Long orgId) {
        StringBuffer sb = new StringBuffer();
        //更新订单所属门店
        sb.append("update t_order tot set tot.org_id = :orgId  where tot.id in (:orderId)");
        orderDao.executeBySql(sb.toString(), orgId, orderId.getLongs());
        //修改订单占用对应门店
        List<DispatchShopInfo> list = queryStock(orderId, orgId);
        //更新原先派单门店订单占用
        StringBuffer sbb = new StringBuffer();
        List<Object> list1 = new ArrayList<>();
        String sqls = null;
        sbb.append("select tot.org_id ,tod.spu_id ,tod.num from t_order tot LEFT JOIN t_order_detail tod on tot.id = tod.order_id where tot.id in (:id)");
        list1.add(orderId);
        List<ChangeDispatchInfo> list2 = orderDetailDao.findManyBySql(sbb.toString(), ChangeDispatchInfo.class, list1.toArray());
        for (ChangeDispatchInfo changeDispatchInfo : list2) {
            sqls = "update t_goods_stock tgs set = tgs.order_stock = IFNULL(tgs.order_stock,0) - :num where tgs.goods_id = :goodsId and tgs.org_id = :oldOrgId";
            goodsStockDao.executeBySql(sqls, changeDispatchInfo.getNum(), changeDispatchInfo.getSpuId(), changeDispatchInfo.getOrgId());
        }
        String sql = null;
        TGoodsStock tGoodsStock = null;
        for (DispatchShopInfo lists : list) {
            //更新改派单后门店的订单占用
            List<Object> param = new ArrayList<>();
            String sqlStock = "select tgs.stock from t_goods_stock tgs where  tgs.org_id = :orgId  and tgs.goods_id = :goodId";
            param.add(orgId);
            param.add(lists.getId());
            Integer stock = goodsStockDao.findOneBySql(sqlStock, Integer.class, param.toArray());
            if (stock == null) {
                tGoodsStock = new TGoodsStock();
                tGoodsStock.setOrderStock(0);
                tGoodsStock.setStock(0);
                tGoodsStock.setOrgId(orgId);
                tGoodsStock.setGoodsId(lists.getId());
                goodsStockDao.insert(tGoodsStock);
            }
            sql = "update t_goods_stock tgs set  tgs.order_stock = IFNULL(tgs.order_stock,0) + :num where tgs.goods_id = :goodsId and tgs.org_id = :orgId";
            goodsStockDao.executeBySql(sql, lists.getNum(), lists.getId(), orgId);
        }
        //录入订单跟踪信息
        TSysOrg sysOrg = sysOrgDao.get(orgId);
        TOrderTrack track = null;
        for (Long id : orderId.getLongs()) {
            track = new TOrderTrack();
            track.setOrderId(id);
            track.setUpdateTime(new Date());
            track.setContent(OrderUtil.getOrderTrackMessage(2, sysOrg.getName()));
            orderTrackDao.insert(track);
        }

    }
}




