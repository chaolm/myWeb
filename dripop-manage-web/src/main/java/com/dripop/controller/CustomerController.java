package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.JsonUtil;
import com.dripop.order.service.OrderDetailService;
import com.dripop.sys.dto.CustomerOrderDetailDto;
import com.dripop.sys.dto.InterDtoWXCustomer;
import com.dripop.sys.service.CustomerService;
import com.dripop.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liyou on 2018/1/11.
 */
@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 根据条件检索客户清单
     * @param request
     * @return
     */
    @PostMapping("page")
    @ResponseBody
    public ResultInfo page(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Integer pageNo = reqJson.getInteger("pageNo");
        InterDtoWXCustomer customer = JsonUtil.fromJson(reqJson,InterDtoWXCustomer.class);
        Pageable pageable = new Pageable(pageNo);
        Pagination<InterDtoWXCustomer> page = customerService.customerPage(customer, pageable);
        return returnSuccess(page);
    }

    /**
     * 会员详情列表
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("detail")
    @ResponseBody
    public ResultInfo detail(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Integer pageNo = reqJson.getInteger("pageNo");
        Long custId=reqJson.getLong("custId");
        Pageable pageable = new Pageable(pageNo);
        Pagination<CustomerOrderDetailDto> page = customerService.detailPage(custId, pageable);
        return returnSuccess(page);
    }

    /**
     * 单个订单详情
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("singleOrderDetail")
    @ResponseBody
    public ResultInfo singleOrderDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long orderId = reqJson.getLong("orderId");
        return returnSuccess( orderDetailService.singleOrderDetail(orderId));
    }
}
