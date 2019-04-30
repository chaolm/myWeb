package com.dripop.accountmanage.service.impl;

import com.bean.PayModel;
import com.bean.PayStatus;
import com.bean.ServiceType;
import com.dripop.accountmanage.dto.*;
import com.dripop.accountmanage.service.AccountManageService;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.util.DateUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.AccountDao;
import com.dripop.dao.OrderDao;
import com.dripop.entity.TAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AccountManageServiceImpl implements AccountManageService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private AccountDao accountDao;

    @Override
    public Pagination<GetShopCheckInfoDto> getERPCheckInfo(ShopCheckReq reqDto, Pageable page) {
        List<Object> list = new ArrayList<>();
        String dateFormat = null;
        if (reqDto.getQueryStatus() != null && reqDto.getQueryStatus() == 1) {
            dateFormat = "%Y-%m-%d";
        } else if (reqDto.getQueryStatus() != null && reqDto.getQueryStatus() == 2) {
            dateFormat = "%Y-%m";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM (\n" +
                //"-- 普通支付 \n" +
                "SELECT * from (SELECT STR_TO_DATE(td.date,'" + dateFormat + "') time from t_date td where td.date <NOW() group by time ORDER BY time DESC) td \n" +
                "LEFT JOIN\n" +
                "(select STR_TO_DATE(tot.create_time,'" + dateFormat + "') timee, sum(tot.real_pay) realPay, \n" +
                "sum(case when tos.is_seller_poundage = 0 then tos.poundage_cost else 0 end) poundageCost \n" +
                "from t_order tot\n" +
                "LEFT JOIN \n" +
                "t_order_staging tos ON tos.order_id = tot.id and tos.stag_type = 2 \n" +
                "where   tot.pay_status = 1 and tot.status in (2,3) GROUP BY timee) result \n" +
                "ON result.timee = td.time \n" +
                "left join\n" +
                "(SELECT \n" +
                "count(case when too.service_type = 1 then 1 else null end) deliveryNum, \n" +
                "count(case when too.service_type = 2 then 1 else null end) cancelNum, \n" +
                "STR_TO_DATE(too.create_time,'" + dateFormat + "') tootime \n" +
                "from t_order_oper too GROUP BY tootime) t \n" +
                "on t.tootime = td.time \n" +
                "union all\n" +
                //" -- 定金支付\n" +
                "SELECT * from (SELECT STR_TO_DATE(td.date,'" + dateFormat + "') time from t_date td where td.date <NOW() group by time ORDER BY time DESC) td \n" +
                "LEFT JOIN\n" +
                "(select STR_TO_DATE(tot.create_time,'" + dateFormat + "') timee, sum(tot.deposit_pay) realPay, \n" +
                "sum(case when tos.is_seller_poundage = 0 then tos.poundage_cost else 0 end) poundageCost \n" +
                "from t_order tot\n" +
                "LEFT JOIN \n" +
                "t_order_staging tos ON tos.order_id = tot.id  and tos.stag_type = 1\n" +
                "where tot.order_type = 5 and tot.deposit_pay_status = 1 and tot.pay_status = 0 and tot.`status` = 1 GROUP BY timee) result \n" +
                "ON result.timee = td.time \n" +
                "left join\n" +
                "(SELECT \n" +
                "count(case when too.service_type = 1 then 1 else null end) deliveryNum, \n" +
                "count(case when too.service_type = 2 then 1 else null end) cancelNum, \n" +
                "STR_TO_DATE(too.create_time,'" + dateFormat + "') tootime \n" +
                "from t_order_oper too GROUP BY tootime) t \n" +
                "on t.tootime = td.time\n" +
                "UNION ALL\n" +
                //"-- 退尾款\n" +
                "SELECT * from (SELECT STR_TO_DATE(td.date,'" + dateFormat + "') time from t_date td where td.date <NOW() group by time ORDER BY time DESC) td \n" +
                "LEFT JOIN\n" +
                "(select STR_TO_DATE(tot.create_time,'" + dateFormat + "') timee, -sum(case when tot.order_type != 5 then tocs.total_refund else (tot.real_pay-tot.deposit_pay)/tot.total_num*cancelNum end) realPay, \n" +
                "-sum(case when tos.is_seller_poundage = 0 then tocs.total_refund/tot.real_pay*tos.poundage_cost else 0 end) poundageCost \n" +
                "from t_order_cancel_submit tocs \n" +
                "left join (select tcod.cancel_order_id, count(1) cancelNum from t_cancel_order_detail tcod group by tcod.cancel_order_id) tcod on tcod.cancel_order_id = tocs.cancel_order_id  \n" +
                "left join t_order tot on tot.id = tocs.order_id \n" +
                "left join t_order_staging tos on tos.order_id = tot.id and tos.stag_type = 2 \n" +
                "where tot.pay_status = 1 and tocs.op_status = 5 and tocs.service_type in (1,4) group by timee) result ON result.timee = td.time \n" +
                "left join\n" +
                "(SELECT \n" +
                "count(case when too.service_type = 1 then 1 else null end) deliveryNum, \n" +
                "count(case when too.service_type = 2 then 1 else null end) cancelNum, \n" +
                "STR_TO_DATE(too.create_time,'" + dateFormat + "') tootime \n" +
                "from t_order_oper too GROUP BY tootime\n" +
                ") t on t.tootime = td.time\n" +
                "UNION ALL\n" +
                //"-- 退定金\n" +
                "SELECT * from (SELECT STR_TO_DATE(td.date,'" + dateFormat + "') time from t_date td where td.date <NOW() group by time ORDER BY time DESC) td \n" +
                "LEFT JOIN\n" +
                "(select STR_TO_DATE(tot.create_time,'" + dateFormat + "') timee, -sum(tot.deposit_pay/tot.total_num*cancelNum) realPay, \n" +
                "-sum(case when tos.is_seller_poundage = 0 then tocs.total_refund/tot.real_pay*tos.poundage_cost else 0 end) poundageCost \n" +
                "from t_order_cancel_submit tocs \n" +
                "left join (select tcod.cancel_order_id, count(1) cancelNum from t_cancel_order_detail tcod group by tcod.cancel_order_id) tcod on tcod.cancel_order_id = tocs.cancel_order_id \n" +
                "left join t_order tot on tot.id = tocs.order_id \n" +
                "left join t_order_staging tos on tos.order_id = tot.id and tos.stag_type = 1   \n" +
                "where tot.order_type = 5 and tot.pay_status = 0 and tot.deposit_pay_status = 1 and tocs.op_status = 5 and tocs.service_type in (1,4) group by timee) result ON result.timee = td.time \n" +
                "left join\n" +
                "(SELECT \n" +
                "count(case when too.service_type = 1 then 1 else null end) deliveryNum, \n" +
                "count(case when too.service_type = 2 then 1 else null end) cancelNum, \n" +
                "STR_TO_DATE(too.create_time,'" + dateFormat + "') tootime \n" +
                "from t_order_oper too GROUP BY tootime\n" +
                ") t on t.tootime = td.time )\n" +
                "tall where 1 = 1 ");
        if (reqDto.getStartDate() != null) {
            sb.append(" and tall.time >= :startDate ");
            list.add(reqDto.getStartDate());
        }
        if (reqDto.getEndDate() != null) {
            sb.append(" and tall.time <= :endDate ");
            list.add(reqDto.getEndDate());
        }
        return getCheckInfo(sb, page, list, reqDto);
    }

    @Override
    public Pagination<GetShopCheckInfoDto> getShopCheckInfo(ShopCheckReq reqDto, Pageable page) {

        List<Object> list = new ArrayList<>();
        String dateFormat = null;
        if (reqDto.getQueryStatus() != null && reqDto.getQueryStatus() == 1) {
            dateFormat = "%Y-%m-%d";
        } else if (reqDto.getQueryStatus() != null && reqDto.getQueryStatus() == 2) {
            dateFormat = "%Y-%m";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("select * FROM\n" +
                //"-- 普通支付 \n" +
                "(select td.time, sum(t.realPay) realPay, sum(t.poundage_cost)poundage_cost, t.payModel from (SELECT STR_TO_DATE(td.date,'" + dateFormat + "') time \n" +
                "from t_date td where td.date < NOW() group by time ORDER BY time DESC) td \n" +
                "left join \n" +
                "(select STR_TO_DATE(tot.pay_time,'" + dateFormat + "') timee, sum(case when tot.order_type = 5 then tot.real_pay - tot.deposit_pay else tot.real_pay end) realPay,\n" +
                "sum(case when tos.is_seller_poundage = 0 then tos.poundage_cost else 0 end) poundage_cost,\n" +
                "tot.pay_model payModel from t_order tot  \n" +
                "LEFT JOIN t_order_staging tos ON tos.order_id = tot.id and tos.stag_type = 2 " +
                "where tot.pay_status = 1 group by timee,payModel\n" +
                "UNION ALL\n" +
                //" -- 定金支付\n" +
                "select STR_TO_DATE(tot.deposit_pay_time,'" + dateFormat + "') timee, sum(tot.deposit_pay) realPay,\n" +
                "sum(case when tos.is_seller_poundage = 0 then tos.poundage_cost else 0 end) poundage_cost,\n" +
                "tot.deposit_pay_model payModel from t_order tot  \n" +
                "LEFT JOIN t_order_staging tos ON tos.order_id = tot.id and tos.stag_type = 1 " +
                "where  tot.order_type = 5 and tot.deposit_pay_status = 1 group by timee,payModel \n" +
                "UNION all\n" +
                // "-- 退尾款 \n" +
                "select STR_TO_DATE(tocs.op_audit_time,'" + dateFormat + "') timee, -sum(case when tot.order_type != 5 then tocs.total_refund else (tot.real_pay-tot.deposit_pay)/tot.total_num*cancelNum end) realPay,\n" +
                "-sum(case when tos.is_seller_poundage = 0 then tocs.total_refund/tot.real_pay*tos.poundage_cost else 0 end) poundage_cost,\n" +
                "tot.pay_model payModel from t_order_cancel_submit tocs \n" +
                "left join (select tcod.cancel_order_id, count(1) cancelNum from t_cancel_order_detail tcod group by tcod.cancel_order_id) tcod on tcod.cancel_order_id = tocs.cancel_order_id \n" +
                "left join t_order tot on tot.id = tocs.order_id \n" +
                "left join t_order_staging tos on tos.order_id = tot.id and tos.stag_type = 2 \n" +
                "where tot.pay_status = 1 and tocs.op_status = 5 and tocs.service_type in (1,4) group by timee, payModel \n" +
                "UNION all\n" +
                // "-- 退定金 \n" +
                "select STR_TO_DATE(tocs.op_audit_time,'" + dateFormat + "') timee, -sum(tot.deposit_pay/tot.total_num*cancelNum) realPay,\n" +
                "-sum(case when tos.is_seller_poundage = 0 then tocs.total_refund/tot.real_pay*tos.poundage_cost else 0 end) poundage_cost,\n" +
                "tot.deposit_pay_model payModel from t_order_cancel_submit tocs \n" +
                "left join (select tcod.cancel_order_id, count(1) cancelNum from t_cancel_order_detail tcod group by tcod.cancel_order_id) tcod on tcod.cancel_order_id = tocs.cancel_order_id \n" +
                "left join t_order tot on tot.id = tocs.order_id \n" +
                "left join t_order_staging tos on tos.order_id = tot.id and tos.stag_type = 1   \n" +
                "where tot.order_type = 5 and tot.pay_status = 0 and tot.deposit_pay_status = 1 and tocs.op_status = 5 and tocs.service_type in (1,4) group by timee, payModel \n" +
                ") t on t.timee = td.time group by t.timee, t.payModel order by t.timee desc \n" +
                ")tall where 1 = 1 ");

        if (reqDto.getPaymodel() != null) {
            sb.append(" and tall.payModel = :payModel ");
            list.add(reqDto.getPaymodel());
        }
        if (reqDto.getStartDate() != null) {
            sb.append(" and tall.time >= :startDate ");
            list.add(reqDto.getStartDate());
        }
        if (reqDto.getEndDate() != null) {
            sb.append(" and tall.time <= :endDate ");
            list.add(reqDto.getEndDate());
        }
        return getCheckInfo(sb, page, list, reqDto);
    }

    /**
     * @param sb
     * @param page
     * @return
     */
    private Pagination<GetShopCheckInfoDto> getCheckInfo(StringBuffer sb, Pageable page, List<Object> list, ShopCheckReq reqDto) {
        Pagination<GetShopCheckInfoDto> result = orderDao.findPageBySql(sb.toString(), page, GetShopCheckInfoDto.class, list.toArray());
        List<GetShopCheckInfoDto> list1 = result.getItems();
        for (GetShopCheckInfoDto getShopCheckInfoDto : list1) {
            if (getShopCheckInfoDto.getPayModel() == PayModel.CASH.getValue()) {
                getShopCheckInfoDto.setPayModelName(PayModel.CASH.getName());
            }
            if (getShopCheckInfoDto.getPayModel() == PayModel.WECHAT.getValue()) {
                getShopCheckInfoDto.setPayModelName(PayModel.WECHAT.getName());
            }
            if (getShopCheckInfoDto.getPayModel() == PayModel.ALIPAY.getValue()) {
                getShopCheckInfoDto.setPayModelName(PayModel.ALIPAY.getName());
            }
            if (getShopCheckInfoDto.getPayModel() == PayModel.STAGING_PAY.getValue()) {
                getShopCheckInfoDto.setPayModelName(PayModel.STAGING_PAY.getName());
            }
            if (getShopCheckInfoDto.getPayModel() == null) {
                getShopCheckInfoDto.setPayModelName("无");
            }
            //判断日账单
            if (reqDto.getQueryStatus() == 1) {
                if (DateUtil.isToday(getShopCheckInfoDto.getTime())) {
                    getShopCheckInfoDto.setStatusName("今日待生成");
                } else {
                    getShopCheckInfoDto.setStatusName("下载");
                }
            }
            //判断月账单
            if (reqDto.getQueryStatus() == 2) {
                //加一个月
                getShopCheckInfoDto.setTimeMonth(DateUtil.dateAdd(DateUtil.DATE_INTERVAL_MONTH, getShopCheckInfoDto.getTime(), 1));
                Long time = getShopCheckInfoDto.getTimeMonth().getTime();
                if (isThisMonth(time)) {
                    getShopCheckInfoDto.setStatusName("当月待生成");
                } else {
                    getShopCheckInfoDto.setStatusName("下载");
                }
            }

        }
        return result;
    }

    @Override
    public Pagination<GetERPDetailInfo> getERPCheckDetail(ShopCheckReq req, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List list = new ArrayList();
        sb.append("select * FROM (\n" +
                //"-- 普通支付\n" +
                "SELECT tot.create_time createTime, tot.id,tot.order_no orderNo,tg.full_name fullName,tso.name,tot.pay_model payModel,tot.real_pay realPay,\n" +
                "(case when tos.is_seller_poundage = 0 then tos.poundage_cost else 0 end) poundageCost, \n" +
                "tos.staging_num , tos.is_seller_poundage from\n" +
                "t_order tot\n" +
                "LEFT JOIN t_sys_org tso  on tso.org_id = tot.org_id \n" +
                "LEFT JOIN t_order_detail tod on tot.id = tod.order_id\n" +
                "LEFT JOIN t_goods tg on tod.spu_id = tg.id\n" +
                "LEFT JOIN t_order_staging tos on tot.id = tos.order_id  and tos.stag_type = 2 where tot.pay_status = 1\n" +
                "and tot.create_time >= :startDate \n" +
                "and tot.create_time <= :endDate \n" +
                "UNION ALL\n" +
                //" -- 定金支付\n" +
                "SELECT tot.create_time createTime, tot.id,tot.order_no orderNo,tg.full_name fullName,tso.name,tot.deposit_pay_model payModel,tot.deposit_pay realPay,\n" +
                "(case when tos.is_seller_poundage = 0 then tos.poundage_cost else 0 end) poundageCost, \n" +
                "tos.staging_num , tos.is_seller_poundage from\n" +
                "t_order tot\n" +
                "LEFT JOIN t_sys_org tso  on tso.org_id = tot.org_id \n" +
                "LEFT JOIN t_order_detail tod on tot.id = tod.order_id\n" +
                "LEFT JOIN t_goods tg on tod.spu_id = tg.id\n" +
                "LEFT JOIN t_order_staging tos on tot.id = tos.order_id  and tos.stag_type = 1 \n" +
                "where tot.pay_status = 0 and tot.deposit_pay_status = 1 and tot.order_type = 5\n" +
                "and tot.create_time >= :startDate \n" +
                "and tot.create_time <= :endDate \n" +
                "UNION ALL\n" +
                //"-- 退尾款\n" +
                "SELECT tocs.op_audit_time createTime,tot.id,tocs.cancel_order_no orderNo,tg.full_name fullName,tso.name,tot.pay_model payModel,-(case when tot.order_type != 5 then tocs.total_refund else (tot.real_pay-tot.deposit_pay)/tot.total_num*cancelNum end) realPay,\n" +
                "-(case when tos.is_seller_poundage = 0 then tocs.total_refund/tot.real_pay*tos.poundage_cost else 0 end) poundageCost, \n" +
                "tos.staging_num, tos.is_seller_poundage\n" +
                "from\n" +
                "t_order_cancel_submit tocs\n" +
                "left join (select tcod.cancel_order_id, count(1) cancelNum from t_cancel_order_detail tcod group by tcod.cancel_order_id) tcod on tcod.cancel_order_id = tocs.cancel_order_id \n" +
                "LEFT JOIN t_order tot on tot.id = tocs.order_id\n" +
                "LEFT JOIN t_sys_org tso  on tso.org_id = tot.org_id \n" +
                "LEFT JOIN t_order_detail tod on tot.id = tod.order_id\n" +
                "LEFT JOIN t_goods tg on tod.spu_id = tg.id\n" +
                "LEFT JOIN t_order_staging tos on tot.id = tos.order_id and tos.stag_type = 2\n" +
                "where tocs.op_status= 5 and tocs.service_type in (1,4)\n" +
                "UNION ALL \n" +
                //"-- 退定金\n" +
                "SELECT tocs.op_audit_time createTime,tot.id,tocs.cancel_order_no orderNo,tg.full_name fullName,tso.name,tot.deposit_pay_model payModel,-(tot.deposit_pay/tot.total_num*cancelNum) realPay,\n" +
                "-(case when tos.is_seller_poundage = 0 then tocs.total_refund/tot.real_pay*tos.poundage_cost else 0 end) poundageCost, \n" +
                "tos.staging_num, tos.is_seller_poundage\n" +
                "from\n" +
                "t_order_cancel_submit tocs\n" +
                "left join (select tcod.cancel_order_id, count(1) cancelNum from t_cancel_order_detail tcod group by tcod.cancel_order_id) tcod on tcod.cancel_order_id = tocs.cancel_order_id \n" +
                "LEFT JOIN t_order tot on tot.id = tocs.order_id\n" +
                "LEFT JOIN t_sys_org tso  on tso.org_id = tot.org_id \n" +
                "LEFT JOIN t_order_detail tod on tot.id = tod.order_id\n" +
                "LEFT JOIN t_goods tg on tod.spu_id = tg.id\n" +
                "LEFT JOIN t_order_staging tos on tot.id = tos.order_id and tos.stag_type = 1\n" +
                "where tot.order_type = 5 and tocs.op_status= 5 and tocs.service_type in (1,4) \n" +
                ") tall where 1 = 1 ");
        sb.append(" and tall.createTime >= :startDate");
        list.add(req.getStartDate());
        sb.append(" and tall.createTime <= :endDate");
        list.add(req.getEndDate());
        if (StringUtil.isNotBlank(req.getShopName())) {
            sb.append(" and tall.fullName like :shopName");
            list.add("%" + req.getShopName() + "%");
        }
        if (StringUtil.isNotBlank(req.getOrderNo())) {
            sb.append(" and tall.orderNo = :orderNo");
            list.add(req.getOrderNo());
        }
        if (req.getPaymodel() != null && req.getPaymodel() != 5) {
            //
            sb.append(" and tall.payModel = :payModel ");
            list.add(req.getPaymodel());
        }
        if (req.getPaymodel() != null && req.getPaymodel() == 5) {
            sb.append(" and tall.staging_num = :stagingNum and tall.is_seller_poundage = :isSellerPoundage ");
            list.add(req.getDivideNum());
            list.add(req.getIsSellerPoundage());
        }
        if (req.getShopId() != null) {
            sb.append(" and tso.org_id = :orgId");
            list.add(req.getShopId());
        }

        sb.append(" ORDER BY tall.createTime desc");
        Pagination<GetERPDetailInfo> page = orderDao.findPageBySql(sb.toString(), pageable, GetERPDetailInfo.class, list.toArray());
        return page;
    }

    @Override
    public Pagination<GetShopdetailInfo> getShopCheckDetail(ShopCheckReq req, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> list = new ArrayList<>();
        sb.append("select * FROM (\n" +
                //"-- 退尾款\n" +
                "SELECT\n" +
                "tocs.op_audit_time create_time,tot.id,tocs.cancel_order_no orderNo, tot.pay_no payNo,tocs.service_type,10 payStatus,tot.pay_model payModel,-(case when tot.order_type != 5 then tocs.total_refund else (tot.real_pay-tot.deposit_pay)/tot.total_num*cancelNum end) realPay,\n" +
                "-(case when tos.is_seller_poundage = 0 then tocs.total_refund/tot.real_pay*tos.poundage_cost else 0 end) poundage_cost,tos.staging_num,tos.is_seller_poundage\n" +
                "from t_order_cancel_submit tocs\n" +
                "left join (select tcod.cancel_order_id, count(1) cancelNum from t_cancel_order_detail tcod group by tcod.cancel_order_id) tcod on tcod.cancel_order_id = tocs.cancel_order_id \n" +
                "LEFT JOIN t_order tot on tot.id = tocs.order_id \n" +
                "LEFT JOIN t_order_staging tos on tot.id = tos.order_id and tos.stag_type = 2\n" +
                "where tocs.op_status= 5 and tocs.service_type in (1,4) \n" +
                "UNION all\n" +
                //"-- 退定金\n" +
                "SELECT\n" +
                "tocs.op_audit_time create_time,tot.id,tocs.cancel_order_no orderNo, tot.deposit_no payNo,tocs.service_type,10 payStatus,tot.deposit_pay_model payModel,-(tot.deposit_pay/tot.total_num*cancelNum) realPay,\n" +
                "-(case when tos.is_seller_poundage = 0 then tocs.total_refund/tot.real_pay*tos.poundage_cost else 0 end) poundage_cost,tos.staging_num,tos.is_seller_poundage\n" +
                "from t_order_cancel_submit tocs\n" +
                "left join (select tcod.cancel_order_id, count(1) cancelNum from t_cancel_order_detail tcod group by tcod.cancel_order_id) tcod on tcod.cancel_order_id = tocs.cancel_order_id \n" +
                "LEFT JOIN t_order tot on tot.id = tocs.order_id \n" +
                "LEFT JOIN t_order_staging tos on tot.id = tos.order_id and tos.stag_type = 1\n" +
                "where tot.order_type = 5 and tocs.op_status= 5 and tocs.service_type in (1,4) \n" +
                "UNION all\n" +
                //"-- 普通支付\n" +
                "select tot.pay_time create_time,tot.id,tot.order_no, tot.pay_no payNo,11 serviceType,tot.pay_status payStatus,tot.pay_model payModel, " +
                "(case when tot.order_type = 5 then tot.real_pay - tot.deposit_pay else tot.real_pay end) realPay,\n" +
                "(case when tos.is_seller_poundage = 0 then tos.poundage_cost else 0 end) poundage_cost ,tos.staging_num,tos.is_seller_poundage\n" +
                "from t_order tot \n" +
                "LEFT JOIN t_order_staging tos on tot.id = tos.order_id and tos.stag_type = 2\n" +
                "where tot.pay_status = 1 \n" +
                //" -- 定金支付\n" +
                "UNION ALL \n" +
                "select tot.deposit_pay_time create_time,tot.id,tot.order_no, tot.deposit_no payNo,11 serviceType,tot.deposit_pay_status payStatus,tot.deposit_pay_model payModel,tot.deposit_pay realPay,\n" +
                "(case when tos.is_seller_poundage = 0 then tos.poundage_cost else 0 end) poundage_cost ,tos.staging_num,tos.is_seller_poundage\n" +
                "from t_order tot \n" +
                "LEFT JOIN t_order_staging tos on tot.id = tos.order_id and tos.stag_type = 1\n" +
                "where tot.order_type = 5 and tot.deposit_pay_status = 1\n" +
                ") y where 1 = 1 and y.payModel = :allPayModel ");
        list.add(req.getAllPayModel());
        if (StringUtil.isNotBlank(req.getOrderNo())) {
            sb.append(" and y.order_no = :orderNo ");
            list.add(req.getOrderNo());
        }
        if (StringUtil.isNotBlank(req.getPayNo())) {
            sb.append(" and y.payNo = :payNo ");
            list.add(req.getPayNo());
        }
        if (req.getPaymodel() != null && req.getPaymodel() != 5) {
            //
            sb.append(" and y.payModel = :payModel ");
            list.add(req.getPaymodel());
        }
        if (req.getPaymodel() != null && req.getPaymodel() == 5) {
            sb.append(" and y.staging_num = :stagingNum and y.is_seller_poundage = :isSellerPoundage ");
            list.add(req.getDivideNum());
            list.add(req.getIsSellerPoundage());
        }
        if (req.getStatus() != null && req.getStatus() == 1) {
            //订单完成
            sb.append(" and y.payStatus = :payStatus ");
            list.add(req.getStatus());
        }
        if (req.getCancelStatus() != null) {
            //订单取消
            sb.append("and y.service_type = :serviceType ");
            list.add(req.getCancelStatus());
        }
        if (req.getStartDate() != null) {
            sb.append(" and y.create_time >= :startDate");
            list.add(req.getStartDate());
        }
        if (req.getEndDate() != null) {
            sb.append(" and y.create_time <= :endDate");
            list.add(req.getEndDate());
        }
        sb.append(" ORDER BY y.create_time desc ");
        Pagination<GetShopdetailInfo> page = orderDao.findPageBySql(sb.toString(), pageable, GetShopdetailInfo.class, list.toArray());
        return page;
    }

    public static boolean isThisMonth(long time) {
        return isThisTime(time, "yyyy-MM");
    }

    private static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    @Transactional
    public void execute() {
        StringBuffer sbRZ = new StringBuffer();
        sbRZ.append("select * FROM(\n" +
                //"-- 支付尾款\n" +
                "select tot.create_time,tot.id,tot.order_no, tot.pay_no payNo,11 serviceType,tot.pay_status payStatus,tot.pay_model payModel,tot.real_pay realPay,\n" +
                "(case when tos.is_seller_poundage = 0 then tos.poundage_cost else 0 end) poundage_cost ,tos.staging_num,tos.is_seller_poundage,tso.org_id orgId,tso.`name`,\n" +
                "tot.send_time sendTime\n" +
                "from t_order tot\n" +
                "LEFT JOIN t_sys_org tso  on tso.org_id = tot.org_id \n" +
                "LEFT JOIN t_order_staging tos on tot.id = tos.order_id and tos.stag_type = 2\n" +
                "where tot.pay_status = 1\n" +
                "UNION ALL\n" +
                //"-- 支付定金\n" +
                "select tot.create_time,tot.id,tot.order_no, tot.deposit_no payNo,11 serviceType,tot.deposit_pay_status payStatus,tot.deposit_pay_model payModel,tot.deposit_pay realPay,\n" +
                "(case when tos.is_seller_poundage = 0 then tos.poundage_cost else 0 end) poundage_cost ,tos.staging_num,tos.is_seller_poundage,tso.org_id orgId,tso.`name`,\n" +
                "tot.send_time sendTime\n" +
                "from t_order tot \n" +
                "LEFT JOIN t_sys_org tso  on tso.org_id = tot.org_id \n" +
                "LEFT JOIN t_order_staging tos on tot.id = tos.order_id and tos.stag_type = 1\n" +
                "where tot.order_type = 5 and tot.pay_status = 0 and tot.deposit_pay_status = 1\n" +
                ")  allData where allData.create_time >= CURDATE()- 1  and allData.create_time <= CURDATE() ");
        queryAccount(sbRZ,1);
        StringBuffer sbCZ = new StringBuffer();
        sbCZ.append("SELECT * FROM(\n" +
                "SELECT\n" +
                "tocs.op_audit_time create_time,tot.id,tocs.cancel_order_no orderNo, tot.pay_no payNo,tocs.service_type,10 payStatus,tot.pay_model payModel,-(case when tot.order_type != 5 then tocs.total_refund else (tot.real_pay-tot.deposit_pay)/tot.total_num*cancelNum end) realPay,\n" +
                "-(case when tos.is_seller_poundage = 0 then tocs.total_refund/tot.real_pay*tos.poundage_cost else 0 end) poundage_cost,tos.staging_num,tos.is_seller_poundage,tso.org_id orgId,tso.`name`,\n" +
                "tot.send_time sendTime\n" +
                "from t_order_cancel_submit tocs\n" +
                "left join (select tcod.cancel_order_id, count(1) cancelNum from t_cancel_order_detail tcod group by tcod.cancel_order_id) tcod on tcod.cancel_order_id = tocs.cancel_order_id \n" +
                "LEFT JOIN t_order tot on tot.id = tocs.order_id\n" +
                "LEFT JOIN t_sys_org tso  on tso.org_id = tot.org_id  \n" +
                "LEFT JOIN t_order_staging tos on tot.id = tos.order_id and tos.stag_type = 2\n" +
                "where tocs.op_status= 5 and tocs.service_type in (1,4) \n" +
                "UNION all\n" +
                "SELECT\n" +
                "tocs.op_audit_time create_time,tot.id,tocs.cancel_order_no orderNo, tot.deposit_no payNo,tocs.service_type,10 payStatus,tot.deposit_pay_model payModel,-(tot.deposit_pay/tot.total_num*cancelNum) realPay,\n" +
                "-(case when tos.is_seller_poundage = 0 then tocs.total_refund/tot.real_pay*tos.poundage_cost else 0 end) poundage_cost,tos.staging_num,tos.is_seller_poundage,tso.org_id orgId,tso.`name`,\n" +
                "tot.send_time sendTime\n" +
                "from t_order_cancel_submit tocs\n" +
                "left join (select tcod.cancel_order_id, count(1) cancelNum from t_cancel_order_detail tcod group by tcod.cancel_order_id) tcod on tcod.cancel_order_id = tocs.cancel_order_id \n" +
                "LEFT JOIN t_order tot on tot.id = tocs.order_id\n" +
                "LEFT JOIN t_sys_org tso  on tso.org_id = tot.org_id  \n" +
                "LEFT JOIN t_order_staging tos on tot.id = tos.order_id and tos.stag_type = 1\n" +
                "where tot.order_type = 5 and tocs.op_status= 5 and tocs.service_type in (1,4)\n" +
                ") allData where allData.create_time >= CURDATE()- 1  and allData.create_time <= CURDATE() ");
        queryAccount(sbCZ,2);
    }

    private void queryAccount(StringBuffer sb,Integer type) {
        List<GetShopdetailInfo> result = orderDao.findManyBySql(sb.toString(), GetShopdetailInfo.class);
        TAccount tAccount = null;
        for (GetShopdetailInfo getShopdetailInfo : result) {
            if (getShopdetailInfo.getPayStatus() != null && getShopdetailInfo.getPayStatus() == 1) {
                getShopdetailInfo.setEndStatus("订单");
            } else {
                getShopdetailInfo.setEndStatus(ServiceType.getName(getShopdetailInfo.getServiceType()));
            }
            tAccount = new TAccount();
            tAccount.setTradeTime(getShopdetailInfo.getCreateTime());
            tAccount.setOrderId(getShopdetailInfo.getId());
            tAccount.setOrderNo(getShopdetailInfo.getOrderNo());
            tAccount.setPayNo(getShopdetailInfo.getPayNo());
            tAccount.setTradeType(getShopdetailInfo.getEndStatus());
            tAccount.setTradeChannel(getShopdetailInfo.getPayModel());
            tAccount.setTradeChannelText(PayModel.getName(getShopdetailInfo.getPayModel(), getShopdetailInfo.getStagingNum(), getShopdetailInfo.getIsSellerPoundage()));
            tAccount.setTradeMoney(Long.valueOf(getShopdetailInfo.getRealPay()));
            tAccount.setStageMoney(Long.valueOf(getShopdetailInfo.getPoundageCost()));
            tAccount.setSettlementMoney(Long.valueOf(getShopdetailInfo.getRealPay() - (getShopdetailInfo.getPoundageCost() == null ? 0 : getShopdetailInfo.getPoundageCost())));
            tAccount.setSendTime(getShopdetailInfo.getSendTime());
            tAccount.setOrgId(getShopdetailInfo.getOrgId());
            tAccount.setOrgName(getShopdetailInfo.getName());
            tAccount.setPayStatus(getShopdetailInfo.getPayStatus());
            tAccount.setStagingNum(getShopdetailInfo.getStagingNum());
            tAccount.setIsSellerPoundage(getShopdetailInfo.getIsSellerPoundage());
            tAccount.setServiceType(getShopdetailInfo.getServiceType());
            tAccount.setMoneyFlow(type);
            accountDao.insert(tAccount);
        }
    }

    @Override
    public Pagination<GetShopDetailAfter> getShopCheckDetailafter(ShopCheckReq req, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> param = new ArrayList<>();
        sb.append("select \n" +
                "ta.trade_time createTime,ta.order_no orderNo,ta.pay_no payNo,\n" +
                "ta.trade_type endStatus,ta.trade_channel_text payModelText,ta.trade_channel payModel,\n" +
                "ta.trade_money realPay,ta.stage_money poundageCost,ta.settlement_money settlementMoney FROM t_account ta " +
                "where 1 = 1 and ta.trade_channel = :allPayModel");
        param.add(req.getAllPayModel());
        if (StringUtil.isNotBlank(req.getOrderNo())) {
            sb.append(" and ta.order_no = :orderNo ");
            param.add(req.getOrderNo());
        }
        if (StringUtil.isNotBlank(req.getPayNo())) {
            sb.append(" and ta.pay_no = :payNo ");
            param.add(req.getPayNo());
        }
        if (req.getPaymodel() != null && req.getPaymodel() != 5) {
            //
            sb.append(" and ta.trade_channel = :payModel ");
            param.add(req.getPaymodel());
        }
        if (req.getPaymodel() != null && req.getPaymodel() == 5) {
            sb.append(" and ta.staging_num = :stagingNum and ta.is_seller_poundage = :isSellerPoundage ");
            param.add(req.getDivideNum());
            param.add(req.getIsSellerPoundage());
        }
        if (req.getStatus() != null && req.getStatus() == 1) {
            //订单完成
            sb.append(" and ta.pay_status = :payStatus ");
            param.add(req.getStatus());
        }
        if (req.getCancelStatus() != null) {
            //订单取消
            sb.append("and ta.service_type = :serviceType ");
            param.add(req.getCancelStatus());
        }
        if (req.getStartDate() != null) {
            sb.append(" and ta.trade_time >= :startDate");
            param.add(req.getStartDate());
        }
        if (req.getEndDate() != null) {
            sb.append(" and ta.trade_time <= :endDate");
            param.add(req.getEndDate());
        }
        sb.append(" ORDER BY ta.trade_time desc ");
        Pagination<GetShopDetailAfter> pagination = accountDao.findPageBySql(sb.toString(),pageable,GetShopDetailAfter.class,param.toArray());
        return pagination;
    }

    @Override
    public Pagination<GetshopInfoAfter> getShopCheckInfoAfter(ShopCheckReq reqDto, Pageable page) {
        List<Object> list = new ArrayList<>();
        String dateFormat = null;
        if (reqDto.getQueryStatus() != null && reqDto.getQueryStatus() == 1) {
            dateFormat = "%Y-%m-%d";
        } else if (reqDto.getQueryStatus() != null && reqDto.getQueryStatus() == 2) {
            dateFormat = "%Y-%m";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("select td.time, sum(realPay) realPay, sum(poundage_cost) poundageCost, sum(settlementMoney) settlementMoney ,t.payModel FROM (\n" +
                "SELECT STR_TO_DATE(td.date,'" + dateFormat + "') time \n" +
                "from t_date td where td.date < NOW() group by time ORDER BY time DESC) td \n" +
                "left join \n" +
                "(select STR_TO_DATE(ta.trade_time,'" + dateFormat + "') timee, sum(ta.trade_money) realPay,sum(ta.settlement_money) settlementMoney,\n" +
                "sum(ta.stage_money) poundage_cost,\n" +
                "ta.trade_channel payModel from t_account ta \n" +
                " group by timee,payModel)\n" +
                "t on t.timee = td.time where 1 = 1 ");

        if (reqDto.getPaymodel() != null) {
            sb.append(" and t.payModel = :payModel ");
            list.add(reqDto.getPaymodel());
        }
        if (reqDto.getStartDate() != null) {
            sb.append(" and t.time >= :startDate ");
            list.add(reqDto.getStartDate());
        }
        if (reqDto.getEndDate() != null) {
            sb.append(" and t.time <= :endDate ");
            list.add(reqDto.getEndDate());
        }
        sb.append(" group by t.timee, t.payModel order by t.timee desc ");
        return getCheckInfoAfter(sb, page, list, reqDto);
        }
    /**
     * @param sb
     * @param page
     * @return
     */
    private Pagination<GetshopInfoAfter> getCheckInfoAfter(StringBuffer sb, Pageable page, List<Object> list, ShopCheckReq reqDto) {
        Pagination<GetshopInfoAfter> result = accountDao.findPageBySql(sb.toString(),page,GetshopInfoAfter.class,list.toArray());
        List<GetshopInfoAfter> getshopInfoAfters = result.getItems();
        for (GetshopInfoAfter getShopCheckInfoDto : getshopInfoAfters) {
            if (getShopCheckInfoDto.getPayModel() == PayModel.CASH.getValue()) {
                getShopCheckInfoDto.setPayModelName(PayModel.CASH.getName());
            }
            if (getShopCheckInfoDto.getPayModel() == PayModel.WECHAT.getValue()) {
                getShopCheckInfoDto.setPayModelName(PayModel.WECHAT.getName());
            }
            if (getShopCheckInfoDto.getPayModel() == PayModel.ALIPAY.getValue()) {
                getShopCheckInfoDto.setPayModelName(PayModel.ALIPAY.getName());
            }
            if (getShopCheckInfoDto.getPayModel() == PayModel.STAGING_PAY.getValue()) {
                getShopCheckInfoDto.setPayModelName(PayModel.STAGING_PAY.getName());
            }
            if (getShopCheckInfoDto.getPayModel() == null) {
                getShopCheckInfoDto.setPayModelName("无");
            }
            //判断日账单
            if (reqDto.getQueryStatus() == 1) {
                if (DateUtil.isToday(getShopCheckInfoDto.getTime())) {
                    getShopCheckInfoDto.setStatusName("今日待生成");
                } else {
                    getShopCheckInfoDto.setStatusName("下载");
                }
            }
            //判断月账单
            if (reqDto.getQueryStatus() == 2) {
                //加一个月
                getShopCheckInfoDto.setTimeMonth(DateUtil.dateAdd(DateUtil.DATE_INTERVAL_MONTH, getShopCheckInfoDto.getTime(), 1));
                Long time = getShopCheckInfoDto.getTimeMonth().getTime();
                if (isThisMonth(time)) {
                    getShopCheckInfoDto.setStatusName("当月待生成");
                } else {
                    getShopCheckInfoDto.setStatusName("下载");
                }
            }

        }
        return result;
    }
}
