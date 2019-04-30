package com.dripop.controller;


import com.alibaba.fastjson.JSONObject;
import com.bean.*;
import com.dripop.accountmanage.dto.GetShopdetailInfo;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.ExcelUtil;
import com.dripop.core.util.JsonUtil;
import com.dripop.order.dto.*;
import com.dripop.order.service.OrderDetailService;
import com.dripop.sys.dto.CustomerOrderDetailDto;
import com.dripop.util.PriceUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("order")
public class OrderAboutController extends BaseController {
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("allServerOrderPage")
    @ResponseBody
    public ResultInfo allServerOrderPage(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        ServerPageDto serverPageDto = JsonUtil.fromJson(reqJson, ServerPageDto.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<ServerPageDto> page = orderDetailService.allServerOrderPage(serverPageDto, pageable);
        return returnSuccess(page);
    }

    /**
     * 订单售后服务导出
     * @param request
     * @param
     */
    @PostMapping("exportServerOrderList")
    @ResponseBody
    public void exportServerOrderList(HttpServletRequest request,HttpServletResponse response, String exportJson)throws IOException {
        ServerPageDto serverPageDto = JsonUtil.fromJson(exportJson, ServerPageDto.class);
        Pageable pageable = new Pageable();
        pageable.setPageSize(Pageable.NO_PAGE);
        Pagination<ServerPageDto> page = orderDetailService.allServerOrderPage(serverPageDto, pageable);
        String[] header = {"申请时间", "商品名称", "服务单号", "退款金额", "退款方式", "用户账号","订单类型","售后类型", "当前状态"};
        List<Object[]> objList = new ArrayList<Object[]>();
        List<Object> objArr = null;
        for (ServerPageDto dto : page.getItems()) {
            objArr = new LinkedList<Object>();
            objArr.add(dto.getCancelTime());
            objArr.add(dto.getGoodsNames());
            objArr.add(dto.getServerNo());
            objArr.add(PriceUtil.getSimplePriceText(dto.getCancelPrice()));
            objArr.add(dto.getPayModelStr());
            objArr.add(dto.getCustPhoneNo());
            objArr.add(OrderType.getName(dto.getOrderType()));
            objArr.add(ServiceType.getName(dto.getServiceType()));
            objArr.add(OrderCancelStatus.getName(dto.getOpStatus()));
            objList.add(objArr.toArray());
        }
        HSSFWorkbook book = ExcelUtil.buildExcel(header, objList);
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("UTF-8");
        String fileName = new String("订单售后服务信息.xls".getBytes("gbk"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        book.write(ouputStream);
        ouputStream.flush();
        if (ouputStream != null) {
            ouputStream.close();
        }
    }


    /*订单查询跟踪列表*/
    @PostMapping("orderSearchePage")
    @ResponseBody
    public ResultInfo orderSearchePage(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Integer pageNo = reqJson.getInteger("pageNo");
        OrderSearchDto orderSearchDto = JsonUtil.fromJson(reqJson, OrderSearchDto.class);
        Pageable pageable = new Pageable(pageNo);
        return returnSuccess(orderDetailService.allOrderPage(orderSearchDto, pageable));
    }
    /*导出订单查询跟踪列表*/
    @PostMapping("getDownloadorderSearch")
    public void getDownloadorderSearch(HttpServletRequest request, HttpServletResponse response, String exportJson)throws IOException {
        OrderSearchDto orderSearchDto = JsonUtil.fromJson(exportJson, OrderSearchDto.class);
        Pageable pageable = new Pageable();
        pageable.setPageSize(Pageable.NO_PAGE);
        Pagination<CustomerOrderDetailDto> page = orderDetailService.allOrderPage(orderSearchDto, pageable);
        String[] header = {"下单时间", "商品名称", "订单编号", "订单金额", "订单类型", "支付方式","收货方式", "用户账号", "当前状态","收货人地址","收货人联系方式","收货人","是否需要发票","纳税人识别号","发票内容","发票类型","发票抬头"};
        List<Object[]> objList = new ArrayList<Object[]>();
        List<Object> objArr = null;
        for (CustomerOrderDetailDto dto : page.getItems()) {
            objArr = new LinkedList<Object>();
            objArr.add(dto.getCreateTime());
            objArr.add(dto.getGoodsNames());
            objArr.add(dto.getOrderNo());
            if(dto.getRealPay() == null){
                objArr.add("待发布");
            }else {
                objArr.add(PriceUtil.getSimplePriceText(dto.getRealPay()));
            }
            objArr.add(OrderType.getName(dto.getOrderType()));
            objArr.add(PayModel.getName(dto.getPayModel()));
            objArr.add(ShoppingModel.getName(dto.getShippingModel()));
            objArr.add(dto.getCustPhoneNo());
            objArr.add(dto.getStatusStr());
            objArr.add(dto.getDeliverAddr());
            objArr.add(dto.getDeliverPhoneNo());
            objArr.add(dto.getDeliverName());
            if(dto.getNeedInvoice() == null){
                objArr.add("无");
            }else if(dto.getNeedInvoice() == 1){
                objArr.add("需要");
            }else if(dto.getNeedInvoice() == 2){
                objArr.add("不需要");
            }
            objArr.add(dto.getInvoiceNumber() == null ? "无":dto.getInvoiceNumber());
            objArr.add(dto.getInvoiceContent() == null ? "无":dto.getInvoiceContent());
            objArr.add(dto.getInvoiceType() == null ? "无":"普通类型");
            objArr.add(dto.getInvoiceName() == null ? "无":dto.getInvoiceName());
            objList.add(objArr.toArray());
        }
        HSSFWorkbook book = ExcelUtil.buildExcel(header, objList);
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("UTF-8");
        String fileName = new String("订单查询与跟踪信息.xls".getBytes("gbk"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        book.write(ouputStream);
        ouputStream.flush();
        if (ouputStream != null) {
            ouputStream.close();
        }
    }

    /**
     * 售后订单详情
     */
     @PostMapping("serverSingleDtail")
    @ResponseBody
    public ResultInfo serverSingleDtail(@RequestBody JSONObject reqJson) {
         ServerSingleDto orderCancelExchaengeDetailDto = orderDetailService.cancelDetail(reqJson.getLong("cancelId"));

         return returnSuccess(orderCancelExchaengeDetailDto);
    }




    /**
     * 获取串号
     */
    @PostMapping("orderGoodsIMeis")
    @ResponseBody
    public ResultInfo orderGoodsIMeis(@RequestBody JSONObject reqJson) {
        Long orderId=reqJson.getLong("orderId");
        DeliveryOperDto imeis = orderDetailService.goodsImeis(orderId);
        return returnSuccess(imeis);
    }
}
