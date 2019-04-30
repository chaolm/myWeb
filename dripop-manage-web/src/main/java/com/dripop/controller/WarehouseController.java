package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.ExcelUtil;
import com.dripop.core.util.JsonUtil;
import com.dripop.warehouse.dto.*;
import com.dripop.warehouse.dto.AllWarehouse.*;
import com.dripop.warehouse.service.*;
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
import java.util.*;


@Controller
@RequestMapping("warehouse")
public class WarehouseController extends BaseController {

    @Autowired
    private WarehouseAllService warehouseAllService;

    @Autowired
    private WarehouseRkService warehouseRkService;

    @Autowired
    private WarehouseYkService warehouseYkService;

    @Autowired
    private WarehouseCkService warehouseCkService;

    @Autowired
    private WarehouseTkService warehouseTkService;

    /**
     * 总仓库所有商品列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getAllGoodsInfo")
    @ResponseBody
    public ResultInfo getAllGoodsInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        AllWarehouseReq requDto = JsonUtil.fromJson(reqJson, AllWarehouseReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<WarehouseAllSearchDto> page = warehouseAllService.getAllGoodsInfo(requDto, pageable);
        return returnSuccess(page);
    }


    /**
     * 所有商品导出
     * @param request
     * @param
     */
    @PostMapping("exportAllGoodsList")
    public void exportAllGoodsList(HttpServletRequest request, HttpServletResponse response, String exportJson) throws IOException {
        Pageable pageable = new Pageable();
        pageable.setPageSize(Pageable.NO_PAGE);
        AllWarehouseReq requDto = JsonUtil.fromJson(exportJson, AllWarehouseReq.class);
        Pagination<WarehouseAllSearchDto> page = warehouseAllService.getAllGoodsInfo(requDto, pageable);
        String[] header = { "类目", "品牌","名称", "库存总数", "在途", "在库", "售后"};
        List<Object[]> objList = new ArrayList<Object[]>();
        List<Object> objArr = null;
        for(WarehouseAllSearchDto dto :page.getItems() ){
            objArr = new LinkedList<Object>();
            objArr.add(dto.getTypeName());
           objArr.add(dto.getBrandName());
           objArr.add(dto.getGoodsName());
           objArr.add(dto.getAllNum());
           objArr.add(dto.getWayNum());
           objArr.add(dto.getInWarehouseNum());
           objArr.add(dto.getSaleAfterNum());
           objList.add(objArr.toArray());
        }
        HSSFWorkbook book = ExcelUtil.buildExcel(header,objList);
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("UTF-8");
        String fileName = new String("仓库管理信息.xls".getBytes("gbk"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        book.write(ouputStream);
        ouputStream.flush();
        if (ouputStream != null) {
            ouputStream.close();
        }
    }

    /**
     * 总仓库单个商品列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getSingleWarehouseGoodsInfo")
    @ResponseBody
    public ResultInfo getSingleWarehouseGoodsInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        GetSingleWarehouseInfo requto = JsonUtil.fromJson(reqJson, GetSingleWarehouseInfo.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<GetSingleWarehouseGoodsInfo> page = warehouseAllService.getSingleWarehouseGoodsInfo(requto, pageable);
        return returnSuccess(page);
    }


    /**
     * 创建入库单
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("createInWarehouse")
    @ResponseBody
    public ResultInfo createInWarehouse(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        CreateInWarehouse createInWarehouse = JsonUtil.fromJson(reqJson, CreateInWarehouse.class);
        warehouseRkService.saveInWarehouse(createInWarehouse);
        return returnSuccess();
    }
    /**
     * 创建入库单同时完成移库
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("createInWarehouseAndMove")
    @ResponseBody
    public ResultInfo createInWarehouseAndMove(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        CreateInWarehouse createInWarehouse = JsonUtil.fromJson(reqJson, CreateInWarehouse.class);
        warehouseRkService.createInWarehouseAndMove(createInWarehouse);
        return returnSuccess();
    }
    /**
     * 根据入库单id获取商品入库列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getInWarehouseInfo")
    @ResponseBody
    public ResultInfo getInWarehouseInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        GetInWarehouseInfo inWarehouseInfo = warehouseRkService.getInWarehouseInfo(id);
        return returnSuccess(inWarehouseInfo);
    }

    /**
     * 获取所有入库单列表
     *
     * @return
     */
    @PostMapping("getAllInWarehouseInfo")
    @ResponseBody
    public ResultInfo getAllInWarehouseInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Date startDate = reqJson.getDate("startDate");
        Date endDate = reqJson.getDate("endDate");
        Integer pageNo = reqJson.getInteger("pageNo");
        Long orgId = reqJson.getLong("orgId");
        Pageable pageable = new Pageable(pageNo);
        Pagination<GetAllInWarehouseInfo> page = warehouseRkService.getAllInWarehouseInfo(startDate, endDate, pageable,orgId);
        return returnSuccess(page);
    }

    /**
     * 创建移库单
     *
     * @param request
     * @param createMoveWarehouse
     * @return
     */
    @PostMapping("createMoveWarehouse")
    @ResponseBody
    public ResultInfo createMoveWarehouse(HttpServletRequest request, @RequestBody CreateMoveWarehouse createMoveWarehouse) {
        warehouseYkService.saveMoveWarehouse(createMoveWarehouse);
        return returnSuccess();
    }

    /**
     * 根据移库单id获取商品列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getMoveWarehouseInfo")
    @ResponseBody
    public ResultInfo getMoveWarehouseInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Integer pageNo = reqJson.getInteger("pageNo");
        Integer id = reqJson.getInteger("id");
        List<MoveWarehouseInfo> list = warehouseYkService.getMoveWarehouseInfo(pageNo, id);
        return returnSuccess(list);
    }

    /**
     * 获取所有移库商品列表
     *
     * @return
     */
    @RequestMapping("getAllMoveWarehouseInfo")
    @ResponseBody
    public ResultInfo getAllMoveWarehouseInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Date startDate = reqJson.getDate("startDate");
        Date endDate = reqJson.getDate("endDate");
        Integer pageNo = reqJson.getInteger("pageNo");
        Long ycOrgId = reqJson.getLong("ycOrgId");
        Long yrOrgId = reqJson.getLong("yrOrgId");
        Pageable pageable = new Pageable(pageNo);
        Pagination<GetAllMoveWarehouseInfo> page = warehouseYkService.getAllMoveWarehouseInfo(startDate, endDate, pageable,ycOrgId,yrOrgId);
        return returnSuccess(page);
    }

    /**
     * 取消和确认移库操作
     * @param request
     * @param reqJson
     * @return
     */
    @RequestMapping("confirmAndCancelMove")
    @ResponseBody
    public ResultInfo confirmAndCancelMove(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        Integer status = reqJson.getInteger("status");
        warehouseYkService.confirmAndCancelMove(id,status);
        return returnSuccess();
    }


    /**
     * 创建出库单
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("createOutWarehouse")
    @ResponseBody
    public ResultInfo createOutWarehouse(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        CreateMoveWarehouse createOutWarehouse = JsonUtil.fromJson(reqJson, CreateMoveWarehouse.class);
        warehouseCkService.saveOutWarehouse(createOutWarehouse);
        return returnSuccess();
    }

    /**
     * 根据入库单id获取商品出库列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getOutWarehouseInfo")
    @ResponseBody
    public ResultInfo getOutWarehouseInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Integer pageNo = reqJson.getInteger("pageNo");
        Integer id = reqJson.getInteger("id");
        List<MoveWarehouseInfo> page = warehouseCkService.getOutWarehouseInfo(pageNo, id);
        return returnSuccess(page);
    }

    /**
     * 获取所有出出库列表
     */
    @PostMapping("getAllOutWarehouseInfo")
    @ResponseBody
    public ResultInfo getAllOutWarehouseInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Date startDate = reqJson.getDate("startDate");
        Date endDate = reqJson.getDate("endDate");
        Integer pageNo = reqJson.getInteger("pageNo");
        Long ycOrgId = reqJson.getLong("ycOrgId");
        Pageable pageable = new Pageable(pageNo);
        Pagination<GetAllInWarehouseInfo> page = warehouseCkService.getAllOutWarehouseInfo(startDate, endDate, pageable,ycOrgId);
        return returnSuccess(page);
    }

    /**
     * 创建调库单
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("createAjustWarehouse")
    @ResponseBody
    public ResultInfo createAjustWarehouse(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        AjustWarehouseDto ajustWarehouseDto = JsonUtil.fromJson(reqJson, AjustWarehouseDto.class);
        warehouseTkService.saveAjustWarehouse(ajustWarehouseDto);
        return returnSuccess();
    }

    /**
     * 根据调库单id获取商品列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getAjustWarehouseInfo")
    @ResponseBody
    public ResultInfo getAjustWarehouseInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        List<GetImeiChangeInfo> lists = warehouseTkService.getAjustWarehouseInfo(id);
        return returnSuccess(lists);
    }

    /**
     * 获取调库单列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getAllAjustWarehouseInfo")
    @ResponseBody
    public ResultInfo getAllAjustWarehouseInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Date startDate = reqJson.getDate("startDate");
        Date endDate = reqJson.getDate("endDate");
        Integer pageNo = reqJson.getInteger("pageNo");
        Long orgId = reqJson.getLong("orgId");
        Pageable pageable = new Pageable(pageNo);
        Pagination<GetAjustWarehouseReq> page = warehouseTkService.getAllAjustWarehouseInfo(pageable, startDate, endDate,orgId);
        // MoveWarehousePageDto
        return returnSuccess(page);
    }


    /**
     * 获取门店
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getShop")
    @ResponseBody
    public ResultInfo getShop(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        List<GetShop> lists = warehouseAllService.returgetShop();
        return returnSuccess(lists);
    }

    /**
     * 获取供应商
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getSupplier")
    @ResponseBody
    public ResultInfo getSupplier(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        List<GetSupplier> lists = warehouseAllService.getSupplier();
        return returnSuccess(lists);
    }


    /**
     * 串号查找商品信息
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("findByImei")
    @ResponseBody
    public ResultInfo byImeiFindInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        String imeis = reqJson.getString("imeis");
        Integer type = reqJson.getInteger("type");
        Integer updateImeiType = reqJson.getInteger("updateImeiType");
        List<ByImeiFindInfo> byImeiFindInfos = warehouseAllService.byImeiFindInfo(imeis, type, updateImeiType);
        return returnSuccess(byImeiFindInfos);
    }

    /**
     * 获取串号跟踪记录
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getImeiFollowInfo")
    @ResponseBody
    public ResultInfo getImeiFollowInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        String imeis = reqJson.getString("imeis");
        List<GetImeiFollowInfo> byImeiFindInfos = warehouseAllService.getImeiFollowInfo(imeis);
        return returnSuccess(byImeiFindInfos);
    }

    /**
     * 库存分析
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("warehouseAnalysis")
    @ResponseBody
    public ResultInfo warehouseAnalysis(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        //String imeis = reqJson.getString("imeis");
        AllWarehouseReq reqDto = JsonUtil.fromJson(reqJson, AllWarehouseReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable page = new Pageable(pageNo);
        Pagination<WarehouseAllSearchAnalysisDto> pages = warehouseAllService.warehouseAnalysis(page,reqDto);
        return returnSuccess(pages);
    }

    /**
     * 库存分析导出
     * @param request
     * @param
     */
    @PostMapping("exportAllGoodsAnalysisList")
    @ResponseBody
    public void exportAllGoodsAnalysisList(HttpServletRequest request,HttpServletResponse response, String exportJson) throws IOException{
        Pageable pageable = new Pageable();
        pageable.setPageSize(Pageable.NO_PAGE);
        AllWarehouseReq reqDto = JsonUtil.fromJson(exportJson, AllWarehouseReq.class);
        Pagination<WarehouseAllSearchAnalysisDto> pages = warehouseAllService.warehouseAnalysis(pageable,reqDto);
        String[] header = { "类目", "品牌","名称", "库存总数", "订单待发", "售后占用", "正常库存"};
        List<Object[]> objList = new ArrayList<Object[]>();
        List<Object> objArr = null;
        for(WarehouseAllSearchAnalysisDto dto :pages.getItems() ){
            objArr = new LinkedList<Object>();
            objArr.add(dto.getTypeName());
            objArr.add(dto.getBrandName());
            objArr.add(dto.getGoodsName());
            objArr.add(dto.getAllNum());
            objArr.add(dto.getOrderUsed());
            objArr.add(dto.getSaleAfterNum());
            objArr.add(dto.getCanUsedNum());
            objList.add(objArr.toArray());
        }
        HSSFWorkbook book = ExcelUtil.buildExcel(header,objList);
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("UTF-8");
        String fileName = new String("库存分析.xls".getBytes("gbk"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        book.write(ouputStream);
        ouputStream.flush();
        if (ouputStream != null) {
            ouputStream.close();
        }
    }


    /**
     * 根据商品id获取商品库存分析
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("warehouseAnalysisByGoodId")
    @ResponseBody
    public ResultInfo warehouseAnalysisByGoodId(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long goodId = reqJson.getLong("goodId");
        List<GetWarehouseAnalysisDto> lists = warehouseAllService.warehouseAnalysisByGoodId(goodId);
        return returnSuccess(lists);
    }

    /**
     * 设置报警值
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("setWarnValue")
    @ResponseBody
    public ResultInfo setWarnValue(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        GetWarnValueReq getWarnValueReq = JsonUtil.fromJson(reqJson,GetWarnValueReq.class);
        warehouseAllService.setWarnValue(getWarnValueReq);
        return returnSuccess();
    }
}



