package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.bean.PayModel;
import com.dripop.accountmanage.dto.*;
import com.dripop.accountmanage.service.AccountManageService;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.ExcelUtil;
import com.dripop.core.util.JsonUtil;
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

/**
 * Created by chaoleiming on 2018/3/29.
 */
@Controller
@RequestMapping("accountManage")
public class AccountManageController extends BaseController{

    @Autowired
    private AccountManageService accountManageService;

    /**
     * 商城对账单查询
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getShopCheckInfo")
    @ResponseBody
    public ResultInfo getShopCheckInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        ShopCheckReq reqDto = JsonUtil.fromJson(reqJson, ShopCheckReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable page = new Pageable(pageNo);
        Pagination<GetShopCheckInfoDto> byImeiFindInfos = accountManageService.getShopCheckInfo(reqDto,page);
        return returnSuccess(byImeiFindInfos);
    }

    /**
     * 商城对账单明细查询
     */
    @PostMapping("getShopCheckDetail")
    @ResponseBody
    public ResultInfo getShopCheckDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        ShopCheckReq reqDto = JsonUtil.fromJson(reqJson, ShopCheckReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable page = new Pageable(pageNo);
        Pagination<GetShopdetailInfo> byImeiFindInfos = accountManageService.getShopCheckDetail(reqDto,page);
        return returnSuccess(byImeiFindInfos);
    }
    /**
     * ERP对账单查询
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getERPCheckInfo")
    @ResponseBody
    public ResultInfo getERPCheckInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        ShopCheckReq reqDto = JsonUtil.fromJson(reqJson, ShopCheckReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable page = new Pageable(pageNo);
        Pagination<GetShopCheckInfoDto> byImeiFindInfos = accountManageService.getERPCheckInfo(reqDto,page);
        return returnSuccess(byImeiFindInfos);
    }

    /**
     * ERP对账单明细查询
     */

    @PostMapping("getERPCheckDetail")
    @ResponseBody
    public ResultInfo getERPCheckDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        ShopCheckReq reqDto = JsonUtil.fromJson(reqJson, ShopCheckReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable page = new Pageable(pageNo);
        Pagination<GetERPDetailInfo> byImeiFindInfos = accountManageService.getERPCheckDetail(reqDto,page);
        return returnSuccess(byImeiFindInfos);
    }
    /**
     * shop下载
     */

    @PostMapping("getDownloadShop")
    public void getDownloadShop(HttpServletRequest request, HttpServletResponse response, String exportJson) throws IOException {
        ShopCheckReq reqDto = JsonUtil.fromJson(exportJson, ShopCheckReq.class);
        Pageable page = new Pageable();
        page.setPageSize(Pageable.NO_PAGE);
        Pagination<GetShopdetailInfo> byImeiFindInfos = accountManageService.getShopCheckDetail(reqDto,page);
        String[] header = {"交易时间", "订单号", "流水号", "交易类型", "交易渠道","交易金额", "手续费", "分期利息","结算金额"};
        List<Object[]> objList = new ArrayList<Object[]>();
        List<Object> objArr = null;
        for (GetShopdetailInfo dto : byImeiFindInfos.getItems()) {
            objArr = new LinkedList<Object>();
            objArr.add(dto.getCreateTime());
            objArr.add(dto.getOrderNo());
            objArr.add(dto.getPayNo());
            objArr.add(dto.getEndStatus());
            objArr.add(dto.getPayModelText());
            objArr.add(dto.getRealPayY());
            objArr.add(" ");
            objArr.add(dto.getPoundageCostY());
            objArr.add(dto.getSettlementMoneyY());
            objList.add(objArr.toArray());
        }
        HSSFWorkbook book = ExcelUtil.buildExcel(header, objList);
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("UTF-8");
        String fileName = new String("商城对账单明细信息.xls".getBytes("gbk"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        book.write(ouputStream);
        ouputStream.flush();
        if (ouputStream != null) {
            ouputStream.close();
        }
    }

    /**
     * ERP下载
     */

    @PostMapping("getDownloadERP")
    public void getDownloadERP(HttpServletRequest request, HttpServletResponse response, String exportJson) throws IOException {
        ShopCheckReq reqDto = JsonUtil.fromJson(exportJson, ShopCheckReq.class);
        Pageable page = new Pageable();
        page.setPageSize(Pageable.NO_PAGE);
        Pagination<GetERPDetailInfo> byImeiFindInfos = accountManageService.getERPCheckDetail(reqDto,page);
        String[] header = {"交易时间", "订单号", "商品名称", "门店名称", "交易渠道","交易金额", "手续费", "分期利息","结算金额"};
        List<Object[]> objList = new ArrayList<Object[]>();
        List<Object> objArr = null;
        for (GetERPDetailInfo dto : byImeiFindInfos.getItems()) {
            objArr = new LinkedList<Object>();
            objArr.add(dto.getCreateTime());
            objArr.add(dto.getOrderNo());
            objArr.add(dto.getFullName());
            objArr.add(dto.getName());
            objArr.add(PayModel.getName(Integer.parseInt(dto.getPayModel().toString())));
            objArr.add(dto.getRealPayY());
            objArr.add(" ");
            objArr.add(dto.getPoundageCostY());
            objArr.add(dto.getSettlementMoneyY());
            objList.add(objArr.toArray());
        }
        HSSFWorkbook book = ExcelUtil.buildExcel(header, objList);
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("UTF-8");
        String fileName = new String("ERP对账单明细信息.xls".getBytes("gbk"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        book.write(ouputStream);
        ouputStream.flush();
        if (ouputStream != null) {
            ouputStream.close();
        }
    }
    @PostMapping("account")
    @ResponseBody
    public void account(){
        accountManageService.execute();
    }
    /**
     * 商城对账单明细查询
     */
    @PostMapping("getShopCheckDetailafter")
    @ResponseBody
    public ResultInfo getShopCheckDetailafter(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        ShopCheckReq reqDto = JsonUtil.fromJson(reqJson, ShopCheckReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable page = new Pageable(pageNo);
        Pagination<GetShopDetailAfter> byImeiFindInfos = accountManageService.getShopCheckDetailafter(reqDto,page);
        return returnSuccess(byImeiFindInfos);
    }
    /**
     * 商城对账单查询
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getShopCheckInfoAfter")
    @ResponseBody
    public ResultInfo getShopCheckInfoAfter(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        ShopCheckReq reqDto = JsonUtil.fromJson(reqJson, ShopCheckReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable page = new Pageable(pageNo);
        Pagination<GetshopInfoAfter> byImeiFindInfos = accountManageService.getShopCheckInfoAfter(reqDto,page);
        return returnSuccess(byImeiFindInfos);
    }


}
