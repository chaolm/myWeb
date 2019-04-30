package com.dripop.statistics.service.impl;

import com.dripop.core.util.DateUtil;
import com.dripop.core.util.NumberUtil;
import com.dripop.dao.CustomerDao;
import com.dripop.dao.OrderDao;
import com.dripop.dao.VisitDao;
import com.dripop.statistics.dto.*;
import com.dripop.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 统计业务层 service实现类
 *
 * @author dq
 * @date 2018/3/15 11:09
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private VisitDao visitDao;


    @Override
    public List<CustomerStaDto> customerSta() {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT tcda.province,SUM(1) sum " +
                "FROM (select tod.express_address from t_order tod group by tod.express_address) tor " +
                "LEFT JOIN t_customer_deliver_addr tcda ON tor.express_address = tcda.id " +
                "GROUP BY " +
                "tcda.province " +
                "HAVING " +
                "tcda.province IS NOT NULL " +
                "AND tcda.province != '' " +
                "ORDER BY " +
                "sum DESC " +
                "LIMIT 10;");
        List<CustomerStaDto> list = customerDao.findManyBySql(sb.toString(), CustomerStaDto.class);

        StringBuffer sbb = new StringBuffer();
        sbb.append("select SUM(1) sum from " +
                "(SELECT id, cust_id  FROM t_customer_deliver_addr group by cust_id ORDER BY cust_id ) tt ");
        CustomerStaDto dto = customerDao.findOneBySql(sbb.toString(), CustomerStaDto.class);
        Integer sum = dto.getSum();
        BigDecimal totalbd = BigDecimal.valueOf(sum);//总数
        BigDecimal portionbd = null;//占比数
        for (CustomerStaDto customerStaDto : list) {
            portionbd = BigDecimal.valueOf(
                    customerStaDto.getSum()).divide(totalbd, 4, RoundingMode.HALF_UP).
                    multiply(new BigDecimal(100));
            customerStaDto.setPortion(portionbd.doubleValue());
        }
        return list;
    }

    @Override
    public OrderAnalyslsDto orderAnalysls(AnalyslsParamsDto params) {
        //得到时间段
        getTimeQuantum(params);
        //当天订单数据
        StringBuffer orderNow = new StringBuffer();
        orderNow.append("SELECT  date time , " +
                "SUM(CASE `pay_status` WHEN 0 THEN 1 ELSE 0 END) AS unpaid , \n" +
                "SUM(CASE `pay_status` WHEN 1 THEN 1 ELSE 0 END) AS success \n" +
                "FROM ( SELECT date FROM t_date) vd   " +
                "LEFT JOIN  t_order  tor ON DATE_FORMAT(tor.create_time,'%Y-%m-%d') = vd.date  " +
                "WHERE vd.date = :afterDate ");

        OrderAnalyslsDto orderAnalyslsNow = orderDao.findOneBySql(orderNow.toString(), OrderAnalyslsDto.class, DateUtil.getNowStringDate());


        //订单统计
        StringBuffer orderSb = new StringBuffer();
        orderSb.append("SELECT date time, " +
                " SUM(CASE `pay_status` WHEN 0 THEN 1 ELSE 0 END) AS unpaid , \n" +
                "SUM(CASE `pay_status` WHEN 1 THEN 1 ELSE 0 END) AS success \n" +
                "FROM ( SELECT date FROM t_date) vd  LEFT JOIN " +
                " t_order  tor ON DATE_FORMAT(tor.create_time,'%Y-%m-%d') = vd.date " +
                "WHERE vd.date <= :afterDate and vd.date >= :beforeDate " +
                "GROUP BY date ORDER BY date DESC  ");

        List<OrderAnalyslsDto> orderAnalyslsList = orderDao.findManyBySql(orderSb.toString(),
                OrderAnalyslsDto.class, params.getAfterDate(), params.getBeforeDate());

        //售后统计
        StringBuffer serviceSb = new StringBuffer();
        serviceSb.append("SELECT date time, " +
                "SUM(CASE `service_type` WHEN 1 THEN 1 ELSE 0 END) AS returnNum , " +
                "SUM(CASE `service_type` WHEN 2 THEN 1 ELSE 0 END) AS exchange , " +
                "SUM(CASE `service_type` WHEN 4 THEN 1 ELSE 0 END) AS refund  " +
                "FROM ( SELECT date FROM t_date) vd " +
                "LEFT JOIN  t_order_cancel_submit toc on DATE_FORMAT(toc.cancel_time,'%Y-%m-%d') = vd.date and op_status = 5 " +
                "WHERE  vd.date <= :afterDate and vd.date >= :beforeDate " +
                "GROUP BY date ORDER BY date DESC  ");

        List<OrderAnalyslsDto> serviceAnalyslsList = orderDao.findManyBySql(
                serviceSb.toString(), OrderAnalyslsDto.class, params.getAfterDate(), params.getBeforeDate());
        OrderAnalyslsDto orderAnalysls = null;
        OrderAnalyslsDto serviceAnalysls = null;
        for (int i = 0; i < orderAnalyslsList.size(); i++) {
            orderAnalysls = orderAnalyslsList.get(i);
            serviceAnalysls = serviceAnalyslsList.get(i);
            orderAnalysls.setReturnNum(serviceAnalysls.getReturnNum());
            orderAnalysls.setExchange(serviceAnalysls.getExchange());
            orderAnalysls.setRefund(serviceAnalysls.getRefund());
            if (i == 0) {
                orderAnalyslsNow.setReturnNum(serviceAnalysls.getReturnNum());
                orderAnalyslsNow.setExchange(serviceAnalysls.getExchange());
                orderAnalyslsNow.setRefund(serviceAnalysls.getRefund());
            }
        }
        orderAnalyslsNow.setList(orderAnalyslsList);
        return orderAnalyslsNow;
    }

    /**
     * 得到时间段
     *
     * @param params
     */
    private void getTimeQuantum(AnalyslsParamsDto params) {
        if (params.getType().equals(1)) {
            //模式一 最近几天
            if (params.getTime() == null) {
                params.setTime(1);
            }
            params.setAfterDate(DateUtil.getNowStringDate());
            Date date = DateUtil.dateAdd(DateUtil.DATE_INTERVAL_DAY, DateUtil.getNowDate(), -(params.getTime() - 1));
            params.setBeforeDate(DateUtil.dateFormat(date, DateUtil.DATE_FORMAT_YMD));
        } else {
            //模式二 时间段
            params.setAfterDate(DateUtil.dateFormat(
                    params.getEndTime(), DateUtil.DATE_FORMAT_YMD));
            params.setBeforeDate(DateUtil.dateFormat(
                    params.getStartTime(), DateUtil.DATE_FORMAT_YMD));
        }
    }

    @Override
    public GoodsAnalyslsDto goodsAnalysls(AnalyslsParamsDto params) {
        //浏览用户统计
        //得到时间段
        getTimeQuantum(params);
        List<Object> param = new ArrayList<Object>();
        StringBuffer goodsSb = new StringBuffer();
        goodsSb.append("SELECT date time, " +
                "COUNT(id) total, " +
                "COUNT(DISTINCT user_id) customerNum, " +
                "COUNT(DISTINCT user_id,c_ip) peopleNum " +
                " FROM ( SELECT date FROM t_date) vd  " +
                "LEFT JOIN t_visit tv on DATE_FORMAT(create_time,'%Y-%m-%d') = vd.date ");
        if (params.getGoodsId() != null) {
            goodsSb.append(" and sku_id = :goodsId ");
            param.add(params.getGoodsId());
        }
        goodsSb.append("WHERE vd.date <= :afterDate and vd.date >= :beforeDate " +
                "GROUP BY time ORDER BY time desc ;");
        param.add(params.getAfterDate());
        param.add(params.getBeforeDate());
        List<GoodsAnalyslsDto> goodsAnalyslsList = visitDao.findManyBySql(
                goodsSb.toString(), GoodsAnalyslsDto.class, param.toArray());
        GoodsAnalyslsDto goodsAnalyslsDto = new GoodsAnalyslsDto();
        goodsAnalyslsDto.setTotal(goodsAnalyslsList.get(0).getTotal());
        goodsAnalyslsDto.setCustomerNum(goodsAnalyslsList.get(0).getCustomerNum());
        goodsAnalyslsDto.setPeopleNum(goodsAnalyslsList.get(0).getPeopleNum());
        goodsAnalyslsDto.setTime(goodsAnalyslsList.get(0).getTime());
        goodsAnalyslsDto.setList(goodsAnalyslsList);
        return goodsAnalyslsDto;
    }

    @Override
    public ChannelAnalyslsDto channelAnalysls(AnalyslsParamsDto params) {
        //得到时间段
        getTimeQuantum(params);
        List<Object> param = new ArrayList<Object>();
        StringBuffer channelSb = new StringBuffer();
        channelSb.append("SELECT date time, " +
                "SUM(CASE `flatform_type` WHEN 3 THEN 1 ELSE 0 END) AS h5, " +
                "SUM(CASE `flatform_type` WHEN 4 THEN 1 ELSE 0 END) AS ios, " +
                "SUM(CASE `flatform_type` WHEN 5 THEN 1 ELSE 0 END) AS android, " +
                "SUM(CASE `flatform_type` WHEN 6 THEN 1 ELSE 0 END) AS pc " +
                " FROM ( SELECT date FROM t_date) vd  " +
                "LEFT JOIN t_visit tv on DATE_FORMAT(create_time,'%Y-%m-%d') = vd.date ");
        if (params.getGoodsId() != null) {
            channelSb.append(" and sku_id = :goodsId ");
            param.add(params.getGoodsId());
        }
        channelSb.append("WHERE vd.date <= :afterDate and vd.date >= :beforeDat  ");
        param.add(params.getAfterDate());
        param.add(params.getBeforeDate());
        ChannelAnalyslsDto channelAnalyslsDto = visitDao.findOneBySql(
                channelSb.toString(), ChannelAnalyslsDto.class, param.toArray());
        return channelAnalyslsDto;
    }

    @Override
    public PayAnalyslsDto payAnalysls(AnalyslsParamsDto params) {
        //得到时间段
        getTimeQuantum(params);
        //支付统计
        StringBuffer paySb = new StringBuffer();
        paySb.append("SELECT date time,\n" +
                "SUM(CASE WHEN order_type = 1 OR (order_type = 5 AND deposit_pay is null ) OR (order_type = 5 AND deposit_pay is not null and pay_status = 0 ) or \n" +
                "(order_type = 5 AND deposit_pay is not null and pay_status = 1 AND DATE_FORMAT(deposit_pay_time, '%Y-%m-%d') <> DATE_FORMAT(pay_time, '%Y-%m-%d') )\n" +
                "THEN 1 WHEN order_type = 5 AND deposit_pay is not null and pay_status = 1 AND DATE_FORMAT(deposit_pay_time, '%Y-%m-%d') =  DATE_FORMAT(pay_time, '%Y-%m-%d') THEN 2  \n" +
                " ELSE 0 END ) successPay, \n" +
                "COUNT(DISTINCT cust_id) AS peoplePayNum,\n" +
                "SUM(CASE WHEN order_type = 1 OR (order_type = 5 AND deposit_pay is null ) THEN real_pay \n" +
                "WHEN order_type = 5 AND  pay_status = 1 AND deposit_pay is not null AND DATE_FORMAT(deposit_pay_time, '%Y-%m-%d') =  DATE_FORMAT(pay_time, '%Y-%m-%d')  THEN real_pay \n" +
                "WHEN order_type = 5 AND  pay_status = 1 AND deposit_pay is not null AND DATE_FORMAT(deposit_pay_time, '%Y-%m-%d') <>  DATE_FORMAT(pay_time, '%Y-%m-%d')  THEN real_pay - deposit_pay\n" +
                "WHEN order_type = 5 AND  pay_status = 0  THEN  deposit_pay\n" +
                "ELSE 0 END    ) AS realPay \n" +
                "FROM t_date vd \n" +
                "LEFT JOIN t_order tor ON (DATE_FORMAT(pay_time, '%Y-%m-%d') = vd.date or DATE_FORMAT(deposit_pay_time, '%Y-%m-%d') = vd.date ) AND (pay_status = 1 OR deposit_pay_status = 1)" +
                "WHERE vd.date <= :afterDate and vd.date >= :beforeDate \n" +
                "GROUP BY time ORDER BY time DESC");
        List<PayAnalyslsDto> payList = orderDao.findManyBySql(paySb.toString(), PayAnalyslsDto.class,
                params.getAfterDate(), params.getBeforeDate());

        //退款统计
        StringBuffer refundSb = new StringBuffer();
        refundSb.append("SELECT date time, COUNT(cancel_order_id) successRefund, COUNT(DISTINCT cust_id) AS peopleRefundNum, " +
                "SUM(total_refund) AS totalRefund FROM t_date vd " +
                "LEFT JOIN t_order_cancel_submit toc " +
                "ON DATE_FORMAT(op_audit_time, '%Y-%m-%d') = vd.date and toc.service_type in (1,4) and toc.op_status = 5 " +
                "WHERE  vd.date <= :afterDate and vd.date >= :beforeDate  " +
                "GROUP BY time ORDER BY time DESC ");
        List<PayAnalyslsDto> refundList = orderDao.findManyBySql(refundSb.toString(), PayAnalyslsDto.class,
                params.getAfterDate(), params.getBeforeDate());
        PayAnalyslsDto pay = null;
        PayAnalyslsDto refund = null;
        PayAnalyslsDto payAnalyslsDto = new PayAnalyslsDto();
        for (Integer i = 0; i < payList.size(); i++) {
            pay = payList.get(i);
            refund = refundList.get(i);
            pay.setTotalRefund(refund.getTotalRefund());
            pay.setSuccessRefund(refund.getSuccessRefund());
            pay.setPeopleRefundNum(refund.getPeopleRefundNum());
            pay.setSettlement(NumberUtil.sub(pay.getPay().doubleValue(), pay.getRefund().doubleValue()));
            if (i == 0) {
                payAnalyslsDto.setRealPay(pay.getRealPay());
                payAnalyslsDto.setSuccessPay(pay.getSuccessPay());
                payAnalyslsDto.setPeoplePayNum(pay.getPeoplePayNum());
                payAnalyslsDto.setTotalRefund(refund.getTotalRefund());
                payAnalyslsDto.setSuccessRefund(refund.getSuccessRefund());
                payAnalyslsDto.setPeopleRefundNum(refund.getPeopleRefundNum());
                payAnalyslsDto.setTime(pay.getTime());
                payAnalyslsDto.setSettlement(pay.getSettlement());
            }
        }
        payAnalyslsDto.setList(payList);
        return payAnalyslsDto;
    }

    /**
     * 基本封装方法 填充
     *
     * @return
     */

    private OrderAnalyslsDto creatOrderAnalyslsDto() {
        OrderAnalyslsDto orderAnalyslsDto = new OrderAnalyslsDto();
        orderAnalyslsDto.setRefund(0);
        orderAnalyslsDto.setSuccess(0);
        orderAnalyslsDto.setReceiving(0);
        orderAnalyslsDto.setExchange(0);
        orderAnalyslsDto.setCancel(0);
        orderAnalyslsDto.setReturnNum(0);
        orderAnalyslsDto.setUnpaid(0);
        orderAnalyslsDto.setTime(" ");
        return orderAnalyslsDto;
    }


}
