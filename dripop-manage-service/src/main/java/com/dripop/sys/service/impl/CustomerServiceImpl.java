package com.dripop.sys.service.impl;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.CustomerDao;
import com.dripop.dao.OrderDao;
import com.dripop.entity.TCustomer;
import com.dripop.sys.dto.CustomerOrderDetailDto;
import com.dripop.sys.dto.InterDtoWXCustomer;
import com.dripop.sys.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyou on 2018/1/11.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private OrderDao orderDao;

    public Pagination<InterDtoWXCustomer> customerPage(InterDtoWXCustomer customer, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sb.append("SELECT tc.id, tc.nick_name `name`, tc.phone_no, tc.create_time createTime, tc.sex, tc.wechat, " +
                "tc.wechat_nick_name, tc.qq, tc.qq_nick_name, tod.buyMoney, tod.buyNumber, tc.point_value, tc.headimg_url " +
                "FROM t_customer tc " +
                "LEFT JOIN (SELECT cust_id, SUM(real_pay) buyMoney, COUNT(id) buyNumber from t_order tod WHERE pay_status = 1 GROUP BY cust_id) tod " +
                "ON tod.cust_id =tc.id WHERE 1=1");
        if (StringUtil.isNotBlank(customer.getPhoneNo())) {
            sb.append(" and tc.phone_no like :phoneNo");
            params.add("%"+customer.getPhoneNo()+"%");
        }
        if(StringUtil.isNotBlank(customer.getWechat())){
            sb.append(" and tc.wechat like :wechat ");
            params.add("%"+customer.getWechat()+"%");
        }
        if(StringUtil.isNotBlank(customer.getQq())){
           sb.append(" and tc.qq like :qq ");
            params.add("%"+customer.getQq()+"%");
        }
        if(customer.getSex()!=null){
            sb.append(" and tc.sex = :sex");
            params.add(customer.getSex());
        }
        if(customer.getCreateTime()!=null){
          sb.append(" and tc.create_time > :createTime");
          params.add(customer.getCreateTime());
        }
         if(customer.getEndTime()!=null){
            sb.append(" and tc.create_time < :endTime");
            params.add(customer.getEndTime());
        }

        return customerDao.findPageBySql(sb.toString(), pageable, InterDtoWXCustomer.class, params.toArray());
    }


    public Pagination<CustomerOrderDetailDto> detailPage(Long custId, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> parms = new ArrayList<>();
        parms.add(custId);
        sb.append(" SELECT " +
                "GROUP_CONCAT(tg.small_pic) imgUrls, " +
                "GROUP_CONCAT(" +
                " CASE" +
                " WHEN tcp.id IS NULL THEN" +
                " tg.full_name" +
                " ELSE" +
                " tcp. NAME" +
                " END" +
                " ) goodsNames," +
                " tod.id AS orderId," +
                " tod.order_no AS orderNo," +
                " tod.create_time," +
                " tod.`status`," +
                " tod.shipping_model," +
                " tod.real_pay,"+
                " tod.pay_model" +
                "  FROM" +
                " t_order tod" +
                " LEFT JOIN t_order_detail detail ON tod.id = detail.order_id" +
                " LEFT JOIN t_combo_package tcp ON tcp.id = detail.package_id" +
                " LEFT JOIN t_goods tg ON tg.id = detail.spu_id where tod.cust_id=:custId" +
                " GROUP BY" +
                " tod.id  ORDER BY tod.create_time DESC");
        return orderDao.findPageBySql(sb.toString(), pageable, CustomerOrderDetailDto.class, parms.toArray());
    }
}
