package com.dripop.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bean.DeliveryType;
import com.bean.ShoppingModel;
import com.dripop.core.bean.Filter;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.*;
import com.dripop.dto.CommonGiftDto;
import com.dripop.entity.*;
import com.dripop.order.dto.*;
import com.dripop.order.service.OrderDetailService;
import com.dripop.service.CommonService;
import com.dripop.sys.dto.CustomerOrderDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ComboPackageDao comboPackageDao;

    @Autowired
    private CustomerDeliverAddrDao deliverAddrDao;

    @Autowired
    private SysOrgDao orgDao;

    @Autowired
    private ExpressDao expressDao;

    @Autowired
    private OrderOperDao operDao;

    @Autowired
    private OrderCancelSubmitDao cancelSubmitDao;

    @Autowired
    private  GoodsDao goodsDao;

    @Autowired
    private OrderOperDao orderOperDao;

    @Autowired
    private GoodsStockDao goodsStockDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ExpressHistoryDao expressHistoryDao;

    @Autowired
    private CancelOrderDetailDao cancelOrderDetailDao;

    @Override
    public OrderSingleDto singleOrderDetail(Long orderId) {
        OrderSingleDto orderSingleDto = new OrderSingleDto();
        List<Object> parms = new ArrayList<>();
        StringBuffer sb = new StringBuffer();

        /*订单基本信息*/
        sb.append("SELECT tod.id as orderId, tod.cust_id, tod.real_pay, tod.`status`, tod.pay_status, tod.give_points, tod.pay_time, tod.complete_time, " +
                "tod.close_time,tod.pickup_time pickUpTime, tod.pro_num, tod.pay_model, tod.payer_name, tod.pay_no, tod.coupon_price, tod.invoice_content, tod.invoice_name, tod.invoice_number, " +
                "tod.invoice_type, tod.original_price, tod.create_time, tod.order_no, tod.shipping_model, tod.org_id, tc.phone_no custPhoneNo, tod.service_person_id, " +
                "tod.delivery_num, tod.express_address, " +
                "tod.deposit_pay deposit, tod.deposit_pay_status,tod.order_type, " +
                "tod.deposit_pay_model,tod.deposit_pay_time,tod.deposit_payer_name,tod.deposit_no " +
                "FROM  t_order tod " +
                "LEFT JOIN t_customer AS tc ON tod.cust_id = tc.id " +
                "WHERE tod.id =:orderId ");
        parms.add(orderId);
        OrderSingleBaseDto orderSingleBaseDto = orderDao.findOneBySql(sb.toString(), OrderSingleBaseDto.class, parms.toArray());

        /*改派情况处理*/
        TSysOrg sysOrg = commonService.findGeneralStore();
        if(orderSingleBaseDto!=null && orderSingleBaseDto.getShippingModel()!=null){
            if(orderSingleBaseDto.getShippingModel().equals(ShoppingModel.DELIVERY.getValue())){
                if(orderSingleBaseDto.getDeliveryNum()==null||orderSingleBaseDto.getDeliveryNum()==0){
                    orderSingleBaseDto.setPdStatus(sysOrg.getId().equals(orderSingleBaseDto.getOrgId()) ? 1 : 2);
                }else {
                    orderSingleBaseDto.setPdStatus(3);
                }
            }
        }

        /*订单基本信息*/
        orderSingleDto.setOrderSingleBaseDto(orderSingleBaseDto);

        /*商品信息详情*/
        orderSingleDto.setOrderDetails(orderGoodsDetail(orderId));

        /*订单的物流信息*/
        orderSingleDto.setLogisticsDtos(getLogisticsDtos(orderId));

        /*包裹信息*/
        orderSingleDto.setOperListDtos(getOperList(orderId));

        /*售后服务信息*/
        orderSingleDto.setServerPageDtos(getOrderServerList(orderId));

        /*订单的服务信息*/
        if (orderSingleBaseDto != null && orderSingleBaseDto.getOrgId() != null) {
            orderSingleDto.setServeMessage(getServeMessage(orderSingleBaseDto.getOrgId()));
        }

        /*收货信息*/
        if (orderSingleBaseDto != null && orderSingleBaseDto.getShippingModel() != null) {
            orderSingleDto.setReceiptDto(getReceiptDto(orderSingleBaseDto));
        }
        return orderSingleDto;
    }

    /**
     * 订单的物流信息
     * */
    private List<LogisticsDto> getLogisticsDtos(Long orderId) {
        String sb = "SELECT tes.`name`, tes.phone, teh.express_no, teh.`data` from t_express_history teh LEFT JOIN t_express tes on teh.express_id = tes.express_id WHERE teh.rel_id =" + orderId + " and teh.type = 1";
        return expressDao.findManyBySql(sb, LogisticsDto.class);
    }


    /**
     * 订单的服务信息
     * */
    private ReceiptDto getServeMessage(Long orgId) {
        String sb = "select name as orgName,link_phone as orgPhoneNo from  t_sys_org where org_id=" + orgId + "";
        return orgDao.findOneBySql(sb, ReceiptDto.class);
    }

    /**
     * 收货信息
     * */
    private ReceiptDto getReceiptDto(OrderSingleBaseDto orderSingleBaseDto) {
        ReceiptDto receiptDto = null;
        StringBuffer sb = new StringBuffer();
        if (orderSingleBaseDto.getShippingModel().equals(ShoppingModel.DELIVERY.getValue())) {
            sb.append("select name deliverName, CONCAT(province,city,county,address) deliverAddr, phone_no deliverPhoneNo from t_customer_deliver_addr where id = :expressAddress ");
            receiptDto = deliverAddrDao.findOneBySql(sb.toString(), ReceiptDto.class, orderSingleBaseDto.getExpressAddress());
        } else if (orderSingleBaseDto.getShippingModel().equals(ShoppingModel.SELF_PICK_UP.getValue())) {
            sb.append("select name orgName, address orgAddr,link_phone orgPhoneNo from t_sys_org where org_id = :orgId ");
            receiptDto = orgDao.findOneBySql(sb.toString(), ReceiptDto.class, orderSingleBaseDto.getOrgId());
        }
        if (orderSingleBaseDto.getPickUpTime() != null) {
            receiptDto.setRecieveTime(orderSingleBaseDto.getPickUpTime());
        }
        return receiptDto;
    }

    /**
     * 订单的包裹信息
     * */
    private List<OrderOperListDto> getOperList(Long orderId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT uuid FROM t_order_oper top LEFT JOIN t_order_detail tod ON tod.order_detail_id=top.order_detail_id " +
                "LEFT JOIN t_order too ON too.id=tod.order_id WHERE top.uuid IS NOT NULL  AND tod.order_id=" + orderId + " GROUP BY top.uuid");
        List<Long> uuids = operDao.findManyBySql(sb.toString(), Long.class);

        List<OrderOperListDto> operListDtos = new ArrayList<>();
        for (Long uuid : uuids){
            sb.setLength(0);
            sb.append("SELECT top.type operType, top.uuid, tg.full_name goodsName FROM t_order_oper top " +
                    "LEFT JOIN t_goods_online tgo ON tgo.online_id = top.online_id " +
                    "LEFT JOIN t_goods tg ON tg.id = tgo.spu_id " +
                    "LEFT JOIN t_order_detail todd ON todd.order_detail_id = top.order_detail_id " +
                    "WHERE todd.order_id = :orderId AND top.uuid = :uuid  AND top.online_id IS NOT NULL");
            List<OrderOperDto> goodsOperList = operDao.findManyBySql(sb.toString(), OrderOperDto.class, orderId, uuid);
            sb.setLength(0);

            sb.append("SELECT top.type operType, top.uuid, tgf.ref_val giftName, tgf.type giftType FROM t_order_oper top " +
                    "LEFT JOIN t_gift tgf ON tgf.id = top.gift_id " +
                    "LEFT JOIN t_order_detail todd ON todd.order_detail_id = top.order_detail_id " +
                    "WHERE todd.order_id =" + orderId + " AND top.uuid=" + uuid +" AND top.gift_id IS NOT NULL ");
            List<OrderOperDto> giftOperList = operDao.findManyBySql(sb.toString(), OrderOperDto.class);

                for (OrderOperDto orderOperDto : giftOperList) {
                    if (orderOperDto.getGiftType() != null) {
                        if (orderOperDto.getGiftType() == 1) {
                            Long onlineId = Long.valueOf(orderOperDto.getGiftName());
                            sb.setLength(0);
                            sb.append("SELECT tg.full_name FROM t_goods tg " +
                                    " LEFT JOIN t_goods_online tgo ON tgo.spu_id=tg.id " +
                                    " WHERE tgo.online_id=" + onlineId + "");
                            TGoods goods = goodsDao.findOneBySql(sb.toString(), TGoods.class);
                            orderOperDto.setGiftName(goods.getFullName());
                        }
                        
                    }
                }


            OrderOperListDto orderOperListDto = new OrderOperListDto();
            orderOperListDto.setGoodsOperList(goodsOperList);
            orderOperListDto.setGiftOperList(giftOperList);
            operListDtos.add(orderOperListDto);
        }

        return operListDtos;

    }

    /**
     * 订单详情中 售后服务信息
     * @param orderId
     * @return
     */
    private List<ServerPageDto> getOrderServerList(Long orderId) {
        StringBuffer sb = new StringBuffer();
        List<Object> parms = new ArrayList<>();
        sb.append("SELECT tocs.cancel_time,  tocs.service_type, tocs.total_refund as cancelPrice,  tocs.op_status AS opStatus, \n" +
                "tocs.cancel_order_id as cancelId,tocs.cancel_order_no as serverNo, tc.phone_no as custPhoneNo, \n" +
                "tod.order_id orderId, \n" +
                "tocs.status, GROUP_CONCAT( CASE  WHEN tcp.id IS NULL THEN   tg.full_name  ELSE tcp. NAME  END   )  goodsNames,  GROUP_CONCAT(tg.small_pic) imgUrls, \n" +
                "tor.pay_model payModel," +
                "CONCAT_WS(',',tor.pay_model,tor.deposit_pay_model) payModelStr,\n" +
                "tor.order_type " +
                "FROM  t_order_cancel_submit tocs LEFT JOIN t_customer tc ON tc.id = tocs.cust_id\n" +
                "LEFT JOIN t_cancel_order_detail tcod ON tcod.cancel_order_id = tocs.cancel_order_id \n" +
                "LEFT JOIN t_order tor ON tor.id = tocs.order_id \n" +
                "LEFT JOIN t_order_detail tod ON tcod.order_detail_id = tod.order_detail_id\n" +
                "LEFT JOIN t_combo_package tcp ON tcp.id = tod.package_id\n" +
                "LEFT JOIN t_goods tg ON tg.id = tod.spu_id  where tod.order_id = :orderId " +
                "GROUP BY tocs.cancel_order_id");
        List<ServerPageDto> serverOrderList = cancelSubmitDao.findManyBySql(sb.toString(), ServerPageDto.class,orderId);
        return serverOrderList;
    }

    public Pagination<CustomerOrderDetailDto> allOrderPage(OrderSearchDto orderSearchDto, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> parms=new ArrayList<>();
        sb.append("SELECT GROUP_CONCAT(tg.small_pic) imgUrls, GROUP_CONCAT(detail.goods_name) goodsNames, " +
                "tod.id orderId, tod.order_no orderNo, tod.create_time, tod.`status`, tc.phone_no custPhoneNo, tod.shipping_model, " +
                "tod.real_pay, tod.pay_model, tcda.`name` deliverName, CONCAT(tcda.province,tcda.city,tcda.county,tcda.address) deliverAddr, "+
                "tcda.phone_no deliverPhoneNo, tod.need_invoice, tod.invoice_number, tod.invoice_content, tod.invoice_type, tod.invoice_name, " +
                "tod.order_type,tod.pay_status,tod.deposit_pay_status, tod.company_code "+
                "FROM t_order tod LEFT JOIN t_order_detail detail ON tod.id = detail.order_id " +
                "LEFT JOIN t_combo_package tcp ON tcp.id = detail.package_id " +
                "LEFT JOIN t_customer tc ON tc.id=tod.cust_id "+
                "LEFT JOIN t_goods_online tgdo ON tgdo.online_id=detail.online_id "+
                "LEFT JOIN t_customer_deliver_addr tcda ON tcda.id=tod.express_address "+
                "LEFT JOIN t_goods tg ON tg.id =tgdo.spu_id where 1=1 ");

        if (StringUtil.isNotBlank(orderSearchDto.getOrderNo())) {
            sb.append(" and tod.order_no like :orderNo");
            parms.add("%" + orderSearchDto.getOrderNo() + "%");
        }
        if (StringUtil.isNotBlank(orderSearchDto.getCompanyCode())) {
            sb.append(" and tod.company_code = :companyCode");
            parms.add(orderSearchDto.getCompanyCode());
        }
        if (orderSearchDto.getStartTime() != null) {
            sb.append(" and tod.create_time > :startTime");
            parms.add(orderSearchDto.getStartTime());
        }
        if (orderSearchDto.getEndTime() != null) {
            sb.append(" and tod.create_time < :endTime");
            parms.add(orderSearchDto.getEndTime());
        }
        if (StringUtil.isNotBlank(orderSearchDto.getGoodsNames())) {
            sb.append(" and (tcp.name like :goodsNames or tg.full_name like :goodsNames) ");
            parms.add("%" + orderSearchDto.getGoodsNames() + "%");
        }
        if (orderSearchDto.getStartMoney() != null) {
            sb.append(" and tod.real_pay >= :startMoney");
            parms.add(orderSearchDto.getStartMoney());
        }
        if (orderSearchDto.getEndMoney() != null) {
            sb.append(" and tod.real_pay <= :endMoney");
            parms.add(orderSearchDto.getEndMoney());
        }
        if (orderSearchDto.getPayModel() != null) {
            sb.append(" and tod.pay_model=:payModel ");
            parms.add(orderSearchDto.getPayModel());
        }
        if (orderSearchDto.getShippingModel() != null) {
            sb.append(" and tod.shipping_model=:shippingModel ");
            parms.add(orderSearchDto.getShippingModel());
        }
        if (StringUtil.isNotBlank(orderSearchDto.getCustPhoneNo())) {
            sb.append(" and tc.phone_no like:custPhoneNo");
            parms.add("%" + orderSearchDto.getCustPhoneNo() + "%");
        }
        if (orderSearchDto.getStatus() != null) {
            sb.append(" and tod.status=:status");
            parms.add(orderSearchDto.getStatus());
        }
        if(orderSearchDto.getOrderType() != null){
            sb.append(" and tod.order_type = :orderType ");
            parms.add(orderSearchDto.getOrderType());
        }
        sb.append(" GROUP BY tod.id ORDER BY tod.create_time DESC");
        Pagination<CustomerOrderDetailDto> pagination=orderDao.findPageBySql(sb.toString(),pageable,CustomerOrderDetailDto.class,parms.toArray());
        for(CustomerOrderDetailDto customerOrderDetailDto :pagination.getItems()){
            if(customerOrderDetailDto.getStatus() == 2){
                customerOrderDetailDto.setStatusStr("待收货");
            }else if(customerOrderDetailDto.getStatus() == 3){
                customerOrderDetailDto.setStatusStr("已完成");
            }else if(customerOrderDetailDto.getStatus() == 4){
                customerOrderDetailDto.setStatusStr("已取消");
            }else if(customerOrderDetailDto.getStatus() == 1){
                if(customerOrderDetailDto.getOrderType() == 1){
                    customerOrderDetailDto.setStatusStr("待支付");
                }
                if(customerOrderDetailDto.getOrderType() == 5){
                    if(customerOrderDetailDto.getDepositPayStatus() == 0){
                        customerOrderDetailDto.setStatusStr("待支付定金");
                    }
                    if(customerOrderDetailDto.getDepositPayStatus() == 1 && customerOrderDetailDto.getPayStatus() == 0){
                        customerOrderDetailDto.setStatusStr("待支付尾款");
                    }
                }

            }
        }
        return pagination;
    }

    public Pagination<ServerPageDto> allServerOrderPage(ServerPageDto serverPageDto, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> parms = new ArrayList<>();
        sb.append("SELECT tocs.cancel_time, tocs.service_type, tocs.total_refund cancelPrice, tocs.op_status, tocs.cancel_order_id cancelId," +
                "tocs.cancel_order_no serverNo, tc.phone_no custPhoneNo, tod.order_id orderId, tocs.status, " +
                "GROUP_CONCAT(CASE WHEN tcp.id IS NULL THEN tg.full_name ELSE tcp.NAME END) goodsNames, " +
                "GROUP_CONCAT(tg.small_pic) imgUrls, CONCAT_WS(',',tor.pay_model,tor.deposit_pay_model) payModelStr "+
                "FROM t_order_cancel_submit tocs " +
                "LEFT JOIN t_customer tc ON tc.id = tocs.cust_id " +
                "LEFT JOIN t_cancel_order_detail tcod ON tcod.cancel_order_id = tocs.cancel_order_id " +
                "LEFT JOIN t_order tor ON tor.id = tocs.order_id " +
                "LEFT JOIN t_order_detail tod ON tcod.order_detail_id = tod.order_detail_id " +
                "LEFT JOIN t_combo_package tcp ON tcp.id = tod.package_id " +
                "LEFT JOIN t_goods tg ON tg.id = tod.spu_id where 1=1 ");
        if (serverPageDto.getCancelTime() != null) {
            sb.append(" and tocs.cancel_time>:cancelTime");
            parms.add(serverPageDto.getCancelTime());
        }

        if (serverPageDto.getEndTime() != null) {
            sb.append(" and  tocs.cancel_time< :endTime");
            parms.add(serverPageDto.getEndTime());
        }

        if (serverPageDto.getServerNo() != null) {
            sb.append(" and tocs.cancel_order_no like:serverNo");
            parms.add("%" + serverPageDto.getServerNo() + "%");
        }

        if (serverPageDto.getCustPhoneNo() != null) {
            sb.append(" and tc.phone_no like :custPhoneNo");
            parms.add("%" + serverPageDto.getCustPhoneNo() + "%");
        }

        if (serverPageDto.getOpStatus() != null) {
            sb.append(" and tocs.op_status=:opStatus");
            parms.add(serverPageDto.getOpStatus());
        }

        if (serverPageDto.getServiceType() != null) {
            sb.append(" and tocs.service_type=:serviceType");
            parms.add(serverPageDto.getServiceType());
        }
        if(serverPageDto.getStatus()!=null && serverPageDto.getStatus()==0){
            sb.append("   and tocs.service_type=4 and tocs.status=1 or tocs.status=2");
        }
        if(serverPageDto.getOrderType() != null){
            sb.append(" and tor.order_type = :orderType ");
            parms.add(serverPageDto.getOrderType());
        }
        sb.append("  group by tocs.cancel_order_id ORDER BY tocs.cancel_time DESC ");
        Pagination<ServerPageDto> pagination = cancelSubmitDao.findPageBySql(sb.toString(), pageable, ServerPageDto.class, parms.toArray());
        return pagination;
    }

    @Override
    public ServerSingleDto cancelDetail(Long cancelId) {
        ServerSingleDto serverSingleDto=new ServerSingleDto();
        StringBuffer sb=new StringBuffer();
        sb.append("SELECT tocs.service_type,tocs.total_refund,tocs.cancel_reason concelReason,tocs.is_open,tocs.contact_phone,\n" +
                "CONCAT_WS(',',tocs.reserve1,tocs.reserve2,tocs.reserve3,tocs.reserve4,tocs.reserve5,tocs.reserve6) imgUrl,\n" +
                "tocs.cancel_desc,tocs.refuse_reason,tocs.cancel_order_id cancelId,tocs.cancel_time,tocs.audit_time,tocs.op_audit_time,tocs.op_status,\n" +
                "tocs.order_id, tocs.status,tocs.request_cancel_time,tocs.cancel_order_no serverNo,tc.phone_no refundName, " +
                "tod.pay_model refundType,CONCAT_WS(',',tod.pay_model,tod.deposit_pay_model) refundTypeStr,  " +
                "tod.order_type, " +
                "tocs.cancel_time refundTime, tocs.total_refund refundPrice,tc.phone_no custPhoneNo,tso.`name` orgName,tso.link_phone orgPhoneNO, \n" +
                "CONCAT(tso.province,tso.city,tso.county,tso.address) deliverAddr \n" +
                "FROM\n" +
                " t_order_cancel_submit tocs \n" +
                "LEFT JOIN t_order tod ON tod.id = tocs.order_id\n" +
                "LEFT JOIN t_customer tc ON tc.id=tocs.cust_id\n" +
                "LEFT JOIN t_sys_org tso ON tso.org_id=tocs.org_id\n" +
                "WHERE tocs.cancel_order_id = :orderId GROUP BY tocs.cancel_order_id ");
        ServerSingleBaseDto baseDto = cancelSubmitDao.findOneBySql(sb.toString(), ServerSingleBaseDto.class,cancelId);
        /*基础信息*/
        serverSingleDto.setServerSingleBaseDto(baseDto);

        //查询物流信息
        serverSingleDto.setExpressMsgDtos(getexpressList(cancelId));

        //查询售后单商品详情信息
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("cancelOrderId", cancelId));
        List<TCancelOrderDetail> OrderCanceiList = cancelOrderDetailDao.findManyByJpql(ql);
        //查询售后单所申请的所有订单详情id
        List<Long> detaillIds = new ArrayList<Long>();
        for (TCancelOrderDetail cancelOrderDetail : OrderCanceiList) {
            detaillIds.add(cancelOrderDetail.getOrderDetailId());
        }


        serverSingleDto.setOrderDetails(getOrderDetails(detaillIds));
        return serverSingleDto;
    }

    /**
     * 售后--物流信息
     * @param cancelId
     * @return
     */
    private List<ExpressMsgDto> getexpressList(Long cancelId) {
        String sb = "SELECT teh.express_no as expressNo, teh.data as data, te.name as expressName, te.phone as expressPhone " +
                "from t_express_history teh left join t_express te on te.express_id=teh.express_id where teh.rel_id=" + cancelId + " ORDER BY teh.create_time ASC ";
        return expressHistoryDao.findManyBySql(sb, ExpressMsgDto.class);
    }

    /**
     * 售后--查询售后单商品详情信息
     * */
    private List getOrderDetails(List detaillIds){
        //去重
        Set<Long> unique = new HashSet<Long>(detaillIds);
        List orderDetailList = new ArrayList();
        for (Long detailId : unique) {
            //查询订单详情信息
            StringBuffer sqlForDetail = new StringBuffer("SELECT tod.original_price as salePrice, tod.discount_price as discountPrice, tod.order_detail_id as detailId,\n" +
                    "tg.small_pic as imgUrl, tg.full_name as goodsName, \n" +
                    "tod.num as num, tod.gift as gift, tod.package_id as packageId,\n" +
                    "tgo.deposit,tgo.deposit_discount_amount,tgo.goods_sell_type, " +
                    "tor.pay_model refundType,CONCAT_WS(',',tor.pay_model,tor.deposit_pay_model) refundTypeStr, " +
                    " tor.order_type " +
                    "from t_order_detail tod\n" +
                    "LEFT JOIN t_order tor on tor.id = tod.order_id \n" +
                    "left join t_goods_online tgo on tgo.online_id=tod.online_id \n" +
                    "left join t_goods tg on tgo.spu_id=tg.id  " +
                    " where tod.order_detail_id= :detailId ");
            OrderDetailOPDto orderDetailDto = orderDetailDao.findOneBySql(sqlForDetail.toString(), OrderDetailOPDto.class, detailId);
            //对订单详情理的list数据进行是否是package套装组合判断，是的话就剔除然后封装OrderPackageDto到list中
            if (orderDetailDto.getPackageId() == null) {
                //当前订单详情所申请的数量
                if (orderDetailDto.getDiscountPrice() != null) {
                    if(orderDetailDto.getSalePrice() != null ){
                        orderDetailDto.setDiscountFee((orderDetailDto.getSalePrice() - orderDetailDto.getDiscountPrice()) * orderDetailDto.getNum());
                        orderDetailDto.setTotalFee(orderDetailDto.getSalePrice() * orderDetailDto.getNum());
                        orderDetailDto.setDiscountTotalFee(orderDetailDto.getTotalFee() - orderDetailDto.getDiscountFee());
                    }
                } else {
                    orderDetailDto.setDiscountFee(0);
                    if(orderDetailDto.getSalePrice() != null){
                        orderDetailDto.setTotalFee(orderDetailDto.getSalePrice() * orderDetailDto.getNum());
                        orderDetailDto.setDiscountTotalFee(orderDetailDto.getTotalFee());
                    }
                }
                orderDetailList.add(orderDetailDto);
            } else {
                //获取package数据
                StringBuffer sqlForPackage = new StringBuffer("SELECT tod.order_detail_id as detailId, " +
                        " tod.package_id, tod.num, tp.name as packageName, " +
                        "  tod.original_price as salePrice, tod.discount_price as discountPrice, tod.gift " +
                        " from t_order_detail tod " +
                        " left join t_combo_package tp on tp.id = tod.package_id " +
                        " where tod.order_detail_id= :detailId");
                OrderPackageDto packageDto = orderDetailDao.findOneBySql(sqlForPackage.toString(), OrderPackageDto.class, detailId);
                //获取package下的所有商品数据
                StringBuffer sqlForPackageDetail = new StringBuffer("SELECT tg.small_pic as imgUrl, tg.full_name as goodsName, " +
                        " tgo.sale_price as salePrice from t_combo_package_rel tpr " +
                        " left join t_goods_online tgo on tpr.online_id=tgo.online_id " +
                        " left join t_goods tg on tg.id=tgo.spu_id " +
                        " where tpr.package_id = :package_id ");
                List<OrderPackageDetailDto> orderPackageDetailDtos = comboPackageDao.findManyBySql(sqlForPackageDetail.toString(), OrderPackageDetailDto.class, orderDetailDto.getPackageId());
                packageDto.setDetails(orderPackageDetailDtos);
                //当前订单详情所申请的数量
                packageDto.setNum(Collections.frequency(detaillIds, detailId));
                if (packageDto.getDiscountPrice() != null) {
                    if(packageDto.getSalePrice() != null){
                        packageDto.setDiscountFee((Integer.parseInt(packageDto.getSalePrice()) - Integer.parseInt(packageDto.getDiscountPrice())) * packageDto.getNum());
                        packageDto.setTotalFee(Integer.parseInt(packageDto.getSalePrice()) * packageDto.getNum());
                        packageDto.setDiscountTotalFee(packageDto.getTotalFee() - packageDto.getDiscountFee());
                    }
                } else {
                    packageDto.setDiscountFee(0);
                    if(packageDto.getSalePrice() != null){
                        packageDto.setTotalFee(Integer.parseInt(packageDto.getSalePrice()) * packageDto.getNum());
                        packageDto.setDiscountTotalFee(packageDto.getTotalFee());
                    }
                }
                orderDetailList.add(packageDto);
            }
        }
        return orderDetailList;
    }

    public DeliveryOperDto goodsImeis(Long orderId) {

        DeliveryOperDto deliveryOperDto = new DeliveryOperDto();
        TOrder tOrder = orderDao.get(orderId);
        if(tOrder!=null){
            deliveryOperDto.setShippingModel(tOrder.getShippingModel());
        }
        //详情商品信息
        List<OrderPackageForImeiDto> orderPackageList = goodsDetail(orderId);

        //封装优惠套餐商品
        setPackage(orderPackageList);
        if (orderPackageList == null || orderPackageList.isEmpty()) {
            throw new ServiceException(orderId + ":订单无商品信息");
        }

        //商品处理
        List<DeliveryGoodsDto> deliveryGoodsDtos = goodsHandle(orderPackageList);

        //回显封装
        returnHandle(deliveryGoodsDtos, deliveryOperDto);

        return deliveryOperDto;
    }

    /**
     * 商品详情信息
     *
     * @param orderId
     * @return
     */
    private List<OrderPackageForImeiDto> goodsDetail(Long orderId) {
        StringBuffer orderDetailSql = new StringBuffer("SELECT tod.online_id onlineId , tod.order_detail_id as detailId, " +
                "tg.small_pic as imgUrl, tg.full_name as goodsName, " +
                "tod.num as num, tod.gift as gift, tod.package_id as packageId " +
                "from t_order_detail tod " +
                "left join t_goods_online tgo on tgo.online_id=tod.online_id " +
                "left join t_goods tg on tgo.spu_id=tg.id " +
                "where tod.order_id= :order_id");
        List<OrderPackageForImeiDto> orderPackageList = orderDetailDao.findManyBySql(orderDetailSql.toString(), OrderPackageForImeiDto.class, orderId);
        return orderPackageList;
    }


    /**
     * 封装优惠套餐商品
     *
     * @param orderDetailList
     */
    private void setPackage(List<OrderPackageForImeiDto> orderDetailList) {
        //对订单详情理的list数据进行是否是package套装组合判断，是的话就剔除然后封装OrderPackageDto到list中
        //过滤掉属于package的orderDetail
        List<OrderPackageForImeiDto> removeList = new ArrayList<>();
        //package信息
        List<OrderPackageForImeiDto> addList = new ArrayList<>();
        if (orderDetailList != null && !orderDetailList.isEmpty()) {
            for (OrderPackageForImeiDto detailDto : orderDetailList) {

                if (detailDto.getPackageId() != null) {
                    //获取package数据
                    StringBuffer sqlForPackage = new StringBuffer("SELECT tod.order_detail_id as detailId, " +
                            " tod.package_id as packageId, tod.num as num, tp.name as packageName, " +
                            "  tod.original_price as salePrice, CASE when tod.discount_price IS NULL THEN tod.original_price ELSE tod.discount_price end as discountPrice, tod.gift as gift ," +
                            "tod.original_price-(CASE when tod.discount_price IS NULL THEN tod.original_price ELSE tod.discount_price end)  as  couponPrice  " +
                            " from t_order_detail tod " +
                            " left join t_combo_package tp on tp.id = tod.package_id " +
                            " where tod.order_detail_id= :detailId");
                    OrderPackageForImeiDto packageDto = orderDetailDao.findOneBySql(sqlForPackage.toString(), OrderPackageForImeiDto.class, detailDto.getDetailId());
                    //获取赠品,将赠品内容解析到传输数据实体类中
                    List<GiftForImeiDto> giftDto = JSON.parseArray(packageDto.getGift(), GiftForImeiDto.class);
                    if (giftDto != null && !giftDto.isEmpty()) {
                        packageDto.setGiftReturn(giftDto);
                        packageDto.setGift(null);
                    }
                    //获取package下的所有商品数据
                    StringBuffer sqlForPackageDetail = new StringBuffer("SELECT tgo.online_id onlineId,tgo.sale_price salePrice , tg.small_pic as imgUrl, tg.full_name as goodsName " +
                            " from t_combo_package_rel tpr " +
                            " left join t_goods_online tgo on tpr.online_id=tgo.online_id " +
                            " left join t_goods tg on tg.id=tgo.spu_id " +
                            " where tpr.package_id= :package_id ");
                    List<OrderPackageDetailForImeiDto> OrderPackageDetailDtos = comboPackageDao.findManyBySql(sqlForPackageDetail.toString(), OrderPackageDetailForImeiDto.class, detailDto.getPackageId());

                    packageDto.setDetails(OrderPackageDetailDtos);
                    //判断是package就去除掉当前的OrderDetailDto对象，添加OrderPackageDto数据到订单详情list中
                    removeList.add(detailDto);
                    addList.add(packageDto);
                }
                //获取赠品,将赠品内容解析到传输数据实体类中
                List<GiftForImeiDto> giftDto = JSON.parseArray(detailDto.getGift(), GiftForImeiDto.class);
                if (giftDto != null && !giftDto.isEmpty()) {
                    detailDto.setGiftReturn(giftDto);
                    detailDto.setGift(null);
                }

            }
        }
        orderDetailList.removeAll(removeList);
        orderDetailList.addAll(addList);
    }
    /**
     * 商品处理
     *
     * @param orderPackageList
     */
    private List<DeliveryGoodsDto> goodsHandle(List<OrderPackageForImeiDto> orderPackageList) {
        List<DeliveryGoodsDto> deliveryGoodsDtos = new ArrayList<>();
        DeliveryGoodsDto deliveryGoodsDto = null;
        List<GoodsImeiDto> goodsImeiDtos = null;
        GoodsImeiDto giftImeiDto = null;
        GoodsImeiDto goodsImeiDto = null;
        List<Long> onlineIds = null;
        for (OrderPackageForImeiDto orderPackageDto : orderPackageList) {
            deliveryGoodsDto = new DeliveryGoodsDto();
            deliveryGoodsDto.setOrderDetailId(orderPackageDto.getDetailId());
            deliveryGoodsDto.setNum(orderPackageDto.getNum());
            deliveryGoodsDto.setPackageName(orderPackageDto.getPackageName());
            goodsImeiDtos = new ArrayList<>();
            onlineIds = new ArrayList<>();
            if (orderPackageDto.getPackageId() == null) {//普通商品
                onlineIds.add(orderPackageDto.getOnlineId());
                goodsImeiDto = new GoodsImeiDto();
                goodsImeiDto.setOrderDetailId(orderPackageDto.getDetailId());
                goodsImeiDto.setOnlineId(orderPackageDto.getOnlineId());
                goodsImeiDto.setGoodsName(orderPackageDto.getGoodsName());
                goodsImeiDto.setImgUrl(orderPackageDto.getImgUrl());
                goodsImeiDtos.add(goodsImeiDto);
                if(orderPackageDto.getGiftReturn()!=null){
                    for (GiftForImeiDto giftDto : orderPackageDto.getGiftReturn()) {
                        if (giftDto.getType() == 1) {//线上商品
                            onlineIds.add(Long.parseLong(giftDto.getRefVal()));
                            for (Integer i = 0; i < giftDto.getNum(); i++) {
                                giftImeiDto = new GoodsImeiDto();
                                giftImeiDto.setOrderDetailId(orderPackageDto.getDetailId());
                                giftImeiDto.setGiftId(giftDto.getGiftId());
                                giftImeiDto.setOnlineId(Long.parseLong(giftDto.getRefVal()));
                                giftImeiDto.setGoodsName(giftDto.getGoodsName());
                                goodsImeiDtos.add(giftImeiDto);
                            }
                        } else {
                            for (Integer i = 0; i < giftDto.getNum(); i++) {
                                giftImeiDto = new GoodsImeiDto();
                                giftImeiDto.setOrderDetailId(orderPackageDto.getDetailId());
                                giftImeiDto.setGoodsName(giftDto.getGoodsName());
                                giftImeiDto.setGiftId(giftDto.getGiftId());
                                goodsImeiDtos.add(giftImeiDto);
                            }
                        }
                    }

                }

                //库存查询
                Integer stock = getStock(onlineIds);

                //已售件数
                List<TOrderOper> imeiNum = imeiList(orderPackageDto.getDetailId(), orderPackageDto.getOnlineId());
                StringBuffer imeiNumSql = new StringBuffer("SELECT * FROM t_order_oper too WHERE too.order_detail_id = :detailId and too.service_type = 1  ");
                List<TOrderOper> imeiList = orderOperDao.findManyBySql(imeiNumSql.toString(), TOrderOper.class, orderPackageDto.getDetailId());
                deliveryGoodsDto.setGoodsImeiDtos(goodsImeiDtos);
                deliveryGoodsDto.setStock(stock);
                if (imeiNum == null || imeiNum.isEmpty()) {
                    deliveryGoodsDto.setImeiNum(0);
                } else {
                    deliveryGoodsDto.setImeiNum(imeiNum.size());
                }
                deliveryGoodsDto.setImeiList(imeiList == null ? new ArrayList<TOrderOper>() : imeiList);
                deliveryGoodsDtos.add(deliveryGoodsDto);
            } else {//优惠套装
                for (OrderPackageDetailForImeiDto orderPackageDetailDto : orderPackageDto.getDetails()) {
                    onlineIds.add(orderPackageDetailDto.getOnlineId());
                    goodsImeiDto = new GoodsImeiDto();
                    goodsImeiDto.setOrderDetailId(orderPackageDto.getDetailId());
                    goodsImeiDto.setOnlineId(orderPackageDetailDto.getOnlineId());
                    goodsImeiDto.setGoodsName(orderPackageDetailDto.getGoodsName());
                    goodsImeiDto.setImgUrl(orderPackageDetailDto.getImgUrl());
                    goodsImeiDtos.add(goodsImeiDto);
                }
                if(orderPackageDto!=null && orderPackageDto.getGiftReturn()!=null){
                    for (GiftForImeiDto giftDto : orderPackageDto.getGiftReturn()) {
                        if (giftDto.getType() == 1) {//线上商品
                            onlineIds.add(Long.parseLong(giftDto.getRefVal()));
                            for (Integer i = 0; i < giftDto.getNum(); i++) {
                                giftImeiDto = new GoodsImeiDto();
                                giftImeiDto.setOrderDetailId(orderPackageDto.getDetailId());
                                giftImeiDto.setGiftId(giftDto.getGiftId());
                                giftImeiDto.setOnlineId(Long.parseLong(giftDto.getRefVal()));
                                giftImeiDto.setGoodsName(giftDto.getGoodsName());
                                goodsImeiDtos.add(giftImeiDto);
                            }
                        } else {
                            for (Integer i = 0; i < giftDto.getNum(); i++) {
                                giftImeiDto = new GoodsImeiDto();
                                giftImeiDto.setOrderDetailId(orderPackageDto.getDetailId());
                                giftImeiDto.setGoodsName(giftDto.getGoodsName());
                                giftImeiDto.setGiftId(giftDto.getGiftId());
                                goodsImeiDtos.add(giftImeiDto);
                            }
                        }
                    }

                }

                //库存查询
                Integer stock = getStock(onlineIds);
                //已售件数
                List<TOrderOper> imeiNum = imeiList(orderPackageDto.getDetailId(), orderPackageDto.getDetails().get(0).getOnlineId());
                StringBuffer imeiNumSql = new StringBuffer("SELECT * FROM t_order_oper too WHERE too.order_detail_id = :detailId and too.service_type = 1  ");
                List<TOrderOper> imeiList = orderOperDao.findManyBySql(imeiNumSql.toString(), TOrderOper.class, orderPackageDto.getDetailId());
                deliveryGoodsDto.setGoodsImeiDtos(goodsImeiDtos);
                deliveryGoodsDto.setStock(stock);
                deliveryGoodsDto.setImeiList(imeiList == null ? new ArrayList<TOrderOper>() : imeiList);
                if (imeiNum == null || imeiNum.isEmpty()) {
                    deliveryGoodsDto.setImeiNum(0);
                } else {
                    deliveryGoodsDto.setImeiNum(imeiNum.size());
                }
                deliveryGoodsDtos.add(deliveryGoodsDto);
            }
        }
        return deliveryGoodsDtos;
    }

    /**
     * 库存
     *
     * @param onlineIds
     * @return
     */
    private Integer getStock(List<Long> onlineIds) {
        StringBuffer stockSql = new StringBuffer("select MIN(vgs.normalStock) as stock from v_goods_stock vgs LEFT JOIN t_goods_online tgo ON vgs.goodsId = tgo.spu_id WHERE tgo.online_id in (:onlineId) ");
        Long stock = goodsStockDao.findOneBySql(stockSql.toString(), Long.class,onlineIds);
        return stock == null ? 0 : stock.intValue();
    }


    /**
     * 已发货信息
     *
     * @param detailId
     * @param onlineId
     */
    private List<TOrderOper> imeiList(Long detailId, Long onlineId) {
        StringBuffer imeiNumSql = new StringBuffer("SELECT * FROM t_order_oper too WHERE too.gift_id is null and too.order_detail_id = :detailId and too.service_type = 1  AND too.online_id = :onlineId ");
        List<TOrderOper> imeiList = orderOperDao.findManyBySql(imeiNumSql.toString(), TOrderOper.class, detailId, onlineId);
        return imeiList;
    }

    /**
     * 回显数据封装
     *
     * @param deliveryGoodsDtos
     * @param deliveryOper
     */
    private void returnHandle(List<DeliveryGoodsDto> deliveryGoodsDtos, DeliveryOperDto deliveryOper) {
        List<DeliveryGoodsDto> deliveryGoodsDtos1 = new ArrayList<>();
        if (deliveryGoodsDtos != null && !deliveryGoodsDtos.isEmpty()) {
            DeliveryGoodsDto deliveryGoodsDto1 = null;
            GoodsImeiDto goodsImeiDto1 = null;
            List<GoodsImeiDto> goodsImeiDtos1 = null;
            String imei = "";
            for (DeliveryGoodsDto goodsDto : deliveryGoodsDtos) {
                int num = goodsDto.getNum() - goodsDto.getImeiNum();//需发货数
                int stockNum = goodsDto.getStock() - num;//负数为缺货数
                if (goodsDto.getStock()<=0){
                    stockNum = -num;
                }

                for (int in = 0; in < goodsDto.getImeiNum(); in++) {
                    deliveryGoodsDto1 = new DeliveryGoodsDto();
                    goodsImeiDtos1 = new ArrayList<>();
                    String mark = null;
                    int count = 0;
                    for (GoodsImeiDto imeiDto : goodsDto.getGoodsImeiDtos()) {
                        count++;
                        boolean flag = false;
                        goodsImeiDto1 = new GoodsImeiDto();
                        goodsImeiDto1.setOnlineId(imeiDto.getOnlineId());
                        goodsImeiDto1.setGiftId(imeiDto.getGiftId());
                        goodsImeiDto1.setGoodsType(imeiDto.getGoodsType());
                        goodsImeiDto1.setImgUrl(imeiDto.getImgUrl());
                        goodsImeiDto1.setGoodsName(imeiDto.getGoodsName());
                        goodsImeiDto1.setDeliveryType(DeliveryType.DELIVERY.getName());
                        goodsImeiDto1.setType(DeliveryType.DELIVERY.getValue());
                        if (deliveryOper.getShippingModel() == 3) {//核查
                            goodsImeiDto1.setDeliveryType(DeliveryType.VERIFY.getName());
                            goodsImeiDto1.setType(DeliveryType.VERIFY.getValue());
                        }
                        for (int j = 0; j < goodsDto.getImeiList().size(); j++) {
                            TOrderOper tOrderOper = goodsDto.getImeiList().get(j);
                            if (mark != null) {
                                if (!tOrderOper.getBatch().equals(mark)) {
                                    continue;
                                }
                            }
                            if (((imeiDto.getOnlineId() == null && imeiDto.getGiftId().equals(tOrderOper.getGiftId()))) || imeiDto.getOnlineId().equals(tOrderOper.getOnlineId())
                                    && imeiDto.getOrderDetailId().equals(tOrderOper.getOrderDetailId())) {
                                if (tOrderOper.getStatus() == 2 && !flag) {
                                    flag = true;
                                }
                                imei = tOrderOper.getImei();
                                if (count == 1) {
                                    mark = tOrderOper.getBatch();
                                }
                                goodsDto.getImeiList().remove(j);
                                break;
                            }
                        }
                        if (flag) {
                            goodsImeiDto1.setDeliveryType(DeliveryType.REFUSE.getName());
                            goodsImeiDto1.setType(DeliveryType.REFUSE.getValue());
                        }
                        goodsImeiDto1.setImei(imei);
                        imei = "";
                        goodsImeiDtos1.add(goodsImeiDto1);
                    }
                    deliveryGoodsDto1.setGoodsImeiDtos(goodsImeiDtos1);
                    deliveryGoodsDto1.setOrderDetailId(goodsDto.getOrderDetailId());
                    deliveryGoodsDto1.setPackageName(goodsDto.getPackageName());
                    deliveryGoodsDtos1.add(deliveryGoodsDto1);
                }
                if (stockNum >= 0) {//可发货
                    for (int i = 0; i < num; i++) {
                        deliveryGoodsDto1 = new DeliveryGoodsDto();
                        goodsImeiDtos1 = new ArrayList<>();
                        for (GoodsImeiDto imeiDto : goodsDto.getGoodsImeiDtos()) {
                            goodsImeiDto1 = new GoodsImeiDto();
                            goodsImeiDto1.setGiftId(imeiDto.getGiftId());
                            goodsImeiDto1.setGoodsType(imeiDto.getGoodsType());
                            goodsImeiDto1.setOnlineId(imeiDto.getOnlineId());
                            goodsImeiDto1.setImgUrl(imeiDto.getImgUrl());
                            goodsImeiDto1.setImei("");
                            goodsImeiDto1.setGoodsName(imeiDto.getGoodsName());
                            goodsImeiDto1.setDeliveryType(DeliveryType.UN_DELIVERY.getName());
                            goodsImeiDto1.setType(DeliveryType.UN_DELIVERY.getValue());
                            if (deliveryOper.getShippingModel() == 3) {
                                goodsImeiDto1.setDeliveryType(DeliveryType.UN_VERIFY.getName());
                                goodsImeiDto1.setType(DeliveryType.UN_VERIFY.getValue());
                            }
                            goodsImeiDtos1.add(goodsImeiDto1);
                        }
                        deliveryGoodsDto1.setGoodsImeiDtos(goodsImeiDtos1);
                        deliveryGoodsDto1.setOrderDetailId(goodsDto.getOrderDetailId());
                        deliveryGoodsDto1.setPackageName(goodsDto.getPackageName());
                        deliveryGoodsDtos1.add(deliveryGoodsDto1);
                    }
                } else {
                    //缺货
                    for (int i = 0; i < -stockNum; i++) {
                        deliveryGoodsDto1 = new DeliveryGoodsDto();
                        goodsImeiDtos1 = new ArrayList<>();
                        for (GoodsImeiDto imeiDto : goodsDto.getGoodsImeiDtos()) {
                            goodsImeiDto1 = new GoodsImeiDto();
                            goodsImeiDto1.setGiftId(imeiDto.getGiftId());
                            goodsImeiDto1.setGoodsType(imeiDto.getGoodsType());
                            goodsImeiDto1.setOnlineId(imeiDto.getOnlineId());
                            goodsImeiDto1.setImgUrl(imeiDto.getImgUrl());
                            goodsImeiDto1.setGoodsName(imeiDto.getGoodsName());
                            goodsImeiDto1.setDeliveryType(DeliveryType.OUT.getName());
                            goodsImeiDto1.setType(DeliveryType.OUT.getValue());
                            goodsImeiDtos1.add(goodsImeiDto1);
                        }
                        deliveryGoodsDto1.setGoodsImeiDtos(goodsImeiDtos1);
                        deliveryGoodsDto1.setOrderDetailId(goodsDto.getOrderDetailId());
                        deliveryGoodsDto1.setPackageName(goodsDto.getPackageName());
                        deliveryGoodsDtos1.add(deliveryGoodsDto1);
                    }
                    //可发货
                    for (Integer i = 0; i < goodsDto.getStock(); i++) {
                        deliveryGoodsDto1 = new DeliveryGoodsDto();
                        goodsImeiDtos1 = new ArrayList<>();
                        for (GoodsImeiDto imeiDto : goodsDto.getGoodsImeiDtos()) {
                            goodsImeiDto1 = new GoodsImeiDto();
                            goodsImeiDto1.setGiftId(imeiDto.getGiftId());
                            goodsImeiDto1.setGoodsType(imeiDto.getGoodsType());
                            goodsImeiDto1.setImei("");
                            goodsImeiDto1.setOnlineId(imeiDto.getOnlineId());
                            goodsImeiDto1.setImgUrl(imeiDto.getImgUrl());
                            goodsImeiDto1.setGoodsName(imeiDto.getGoodsName());
                            goodsImeiDto1.setDeliveryType(DeliveryType.UN_DELIVERY.getName());
                            goodsImeiDto1.setType(DeliveryType.UN_DELIVERY.getValue());
                            if (deliveryOper.getShippingModel() == 3) {
                                goodsImeiDto1.setDeliveryType(DeliveryType.UN_VERIFY.getName());
                                goodsImeiDto1.setType(DeliveryType.UN_VERIFY.getValue());
                            }
                            goodsImeiDtos1.add(goodsImeiDto1);
                        }
                        deliveryGoodsDto1.setGoodsImeiDtos(goodsImeiDtos1);
                        deliveryGoodsDto1.setOrderDetailId(goodsDto.getOrderDetailId());
                        deliveryGoodsDto1.setPackageName(goodsDto.getPackageName());
                        deliveryGoodsDtos1.add(deliveryGoodsDto1);
                    }
                }

            }
        }
        deliveryOper.setDeliveryGoodsList(deliveryGoodsDtos1);
    }


    private List orderGoodsDetail(Long orderId) {
        //查询订单详情信息
        StringBuffer sqlForDetail = new StringBuffer("SELECT tod.original_price as salePrice, tgo.online_id as onlineId, tod.discount_price as discountPrice, tod.order_detail_id as detailId, " +
                " tg.small_pic as imgUrl, tg.full_name as goodsName, " +
                " tod.num as num, tod.gift as gift, tod.package_id as packageId, " +
                " tot.real_pay realPay,tot.deposit_pay deposit,tgo.goods_sell_type " +
                " FROM\n" +
                "t_order tot \n" +
                "LEFT JOIN t_order_detail tod ON tod.order_id = tot.id\n" +
                "LEFT JOIN t_goods_online tgo ON tgo.online_id = tod.online_id\n" +
                "LEFT JOIN t_goods tg ON tgo.spu_id = tg.id " +
                " where tod.order_id= :order_id ");
        List orderDetailList = orderDetailDao.findManyBySql(sqlForDetail.toString(), OrderGoodsDto.class, orderId);
        //对订单详情理的list数据进行是否是package套装组合判断，是的话就剔除然后封装OrderPackageDto到list中
        OrderGoodsDto detailDto = null;
        for (int i = 0; i < orderDetailList.size(); i++) {
            detailDto = (OrderGoodsDto) orderDetailList.get(i);
            if (detailDto.getPackageId() == null) {
                detailDto.setGiftReturn(JsonUtil.fromJson(detailDto.getGift(), new TypeReference<List<CommonGiftDto>>() {
                }));
                if (detailDto.getGiftReturn() != null && detailDto.getGiftReturn().size() > 0) {
                    for (CommonGiftDto gift : detailDto.getGiftReturn()) {
                        gift.setNum(gift.getNum() * detailDto.getNum());
                    }
                }
                continue;
            }
            //获取package数据
            StringBuffer sqlForPackage = new StringBuffer("SELECT tod.order_detail_id as detailId, " +
                    " tod.package_id, tod.num, tp.name as packageName, " +
                    "  tod.original_price as salePrice, tod.discount_price, tod.gift " +
                    " from t_order_detail tod " +
                    " left join t_combo_package tp on tp.id = tod.package_id " +
                    " where tod.order_detail_id= :detailId");
            OrderPackageWFYDto packageDto = orderDetailDao.findOneBySql(sqlForPackage.toString(), OrderPackageWFYDto.class, detailDto.getDetailId());
            //获取package下的所有商品数据
            StringBuffer sqlForPackageDetail = new StringBuffer("SELECT tg.small_pic as imgUrl, tgo.online_id as onlineId, tg.full_name as goodsName,tgo.sale_price singleSalePrice  " +
                    " from t_combo_package_rel tpr " +
                    " left join t_goods_online tgo on tpr.online_id=tgo.online_id " +
                    " left join t_goods tg on tg.id=tgo.spu_id " +
                    " where tpr.package_id= :package_id ");
            List<OrderPackageDetailDto> OrderPackageDetailDtos = comboPackageDao.findManyBySql(sqlForPackageDetail.toString(), OrderPackageDetailDto.class, detailDto.getPackageId());
            packageDto.setDetails(OrderPackageDetailDtos);
            packageDto.setGiftReturn(JsonUtil.fromJson(packageDto.getGift(), new TypeReference<List<CommonGiftDto>>() {
            }));
            if (packageDto.getGiftReturn() != null && packageDto.getGiftReturn().size() > 0) {
                for (CommonGiftDto gift : packageDto.getGiftReturn()) {
                    gift.setNum(gift.getNum() * packageDto.getNum());
                }
            }
            //判断是package就去除掉当前的OrderDetailDto对象，添加OrderPackageDto数据到订单详情list中
            orderDetailList.set(i, packageDto);
        }
        return orderDetailList ;

    }

}
