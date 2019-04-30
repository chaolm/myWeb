package com.dripop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.ExcelUtil;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.entity.*;
import com.dripop.goods.dto.*;
import com.dripop.goods.service.GoodsService;
import com.dripop.util.UserUtil;
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
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by liyou on 2018/1/10.
 */
@Controller
@RequestMapping("goods")
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 上架商品
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("saveGoodsOnlineWeb")
    @ResponseBody
    public ResultInfo saveGoodsOnlineWeb(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        BigDecimal salePrice = reqJson.getBigDecimal("salePrice");
        if(salePrice != null){
            reqJson.put("salePrice", salePrice.multiply(new BigDecimal("100")).intValue());
        }
        BigDecimal officialPrice = reqJson.getBigDecimal("officialPrice");
        if (officialPrice != null) {
            reqJson.put("officialPrice", officialPrice.multiply(new BigDecimal("100")).intValue());
        }
        //定金
        BigDecimal deposit = reqJson.getBigDecimal("deposit");
        if(deposit != null){
            reqJson.put("deposit",deposit.multiply(new BigDecimal("100")).intValue());
        }
        //预售价
        BigDecimal presellMoney = reqJson.getBigDecimal("presellMoney");
        if(presellMoney != null){
            reqJson.put("presellMoney",presellMoney.multiply(new BigDecimal("100")).intValue());
        }
        BigDecimal depositDiscountAmount = reqJson.getBigDecimal("depositDiscountAmount");
        if(depositDiscountAmount != null){
            reqJson.put("depositDiscountAmount",depositDiscountAmount.multiply(new BigDecimal("100")).intValue());
        }
        TGoodsOnline goodsOnline = JsonUtil.fromJson(reqJson, TGoodsOnline.class);
        if (StringUtil.isBlank(goodsOnline.getOpPayModel())) {
            throw new ServiceException("上架商品必须有一种销售配置!");
        }
        if (goodsOnline.getGoodsSellType() == null) {
            goodsOnline.setGoodsSellType(1);
        }
        goodsService.addOrEdit(goodsOnline);
        return returnSuccess();
    }

    /**
     * 商品下架
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("downGoodsOnlineWeb")
    @ResponseBody
    public ResultInfo downGoodsOnlineWeb(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long onlineId = reqJson.getLong("onlineId");
        goodsService.downGoodsOnlineWeb(onlineId);
        return returnSuccess();
    }

    /**
     * 获取商品列表
     *
     * @param reqJson
     * @return
     */
    @PostMapping("getGoodsListWeb")
    @ResponseBody
    public ResultInfo getGoodsListWeb(@RequestBody JSONObject reqJson) {
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        OpGoodsSearchReq opGoodsSearchReq = JsonUtil.fromJson(reqJson, OpGoodsSearchReq.class);
        Pagination<OpGoodsSearchDtoForModel> page = goodsService.pageQueryForModel(opGoodsSearchReq, pageable);
        return returnSuccess(page);
    }

    /**
     * 获取商品列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("goodsList")
    @ResponseBody
    public ResultInfo goodsList(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Pageable pageable = new Pageable();
        pageable.setPageSize(Pageable.NO_PAGE);
        OpGoodsSearchReq opGoodsSearchReq = JsonUtil.fromJson(reqJson, OpGoodsSearchReq.class);
        Pagination<OpGoodsSearchDto> page = goodsService.pageQuery(opGoodsSearchReq, pageable);
        return returnSuccess(page);
    }

    /**
     * 获取商品spu详情
     * @param reqJson
     * @return
     */
    @PostMapping("getSpuForWeb")
    @ResponseBody
    public ResultInfo getGoodsSpu(@RequestBody JSONObject reqJson) {
        Long modelId = reqJson.getLong("modelId");
        TGoodsModel model = goodsService.getGoodsSpu(modelId);
        return returnSuccess(model);
    }

    /**
     * 保存商品spu
     * @param reqJson
     * @return
     */
    @PostMapping("saveSpuForWeb")
    @ResponseBody
    public ResultInfo saveGoodsSpu(@RequestBody JSONObject reqJson) {
        TGoodsModel model = JsonUtil.fromJson(reqJson, TGoodsModel.class);
        Long modelId = reqJson.getLong("modelId");
        model.setId(modelId);
        goodsService.saveGoodsSpu(model, modelId);
        return returnSuccess();
    }

    /**
     * OP平台保存商品
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("saveSkuForWeb")
    @ResponseBody
    public ResultInfo saveSkuForWeb(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        TGoods goods = JsonUtil.fromJson(reqJson, TGoods.class);
        goods.setId(reqJson.getLong("goodId"));
        String images = reqJson.getString("images");
        JSONArray jsonArray = reqJson.getJSONArray("goodsParams");
        TGoodsParam goodsParam = null;
        List<TGoodsParam> goodsParamList = new ArrayList<TGoodsParam>();
        for (int i = 0; i < jsonArray.size(); i++) {
            goodsParam = new TGoodsParam();
            Long paramId = jsonArray.getJSONObject(i).getLong("paramId");
            String paramVal = jsonArray.getJSONObject(i).getString("goodsParamVal");
            goodsParam.setParamId(paramId);
            goodsParam.setParamVal(paramVal);
            goodsParam.setCreateTime(new Date());
            goodsParam.setCreator(UserUtil.currentAdminUser().getId());
            goodsParam.setUpdateTime(new Date());
            goodsParam.setUpdater(UserUtil.currentAdminUser().getId());
            goodsParamList.add(goodsParam);
        }
        goodsService.saveGoods(goods, images, goodsParamList);
        return returnSuccess();
    }

    /**
     * 商品导出
     *
     * @param request
     * @return
     */
    @PostMapping("exportGoodsList")
    public void exportGoodsList(HttpServletRequest request, HttpServletResponse response, String exportJson) throws IOException {
        Pageable pageable = new Pageable();
        pageable.setPageSize(Pageable.NO_PAGE);
        OpGoodsSearchReq opGoodsSearchReq = JsonUtil.fromJson(exportJson, OpGoodsSearchReq.class);
        Pagination<OpGoodsSearchDtoForModel> page = goodsService.pageQueryForModel(opGoodsSearchReq, pageable);
        String[] header = {"商品名称", "类目", "品牌", "商品ID", "上架ID", "上架状态", "售价"};
        List<Object[]> objList = new ArrayList<Object[]>();
        List<Object> objArr = null;
        for (OpGoodsSearchDtoForModel dto : page.getItems()) {
            for (OpGoodsSkuDto sku : dto.getSkuList()) {
                objArr = new LinkedList<Object>();
                objArr.add(sku.getFullName());
                objArr.add(dto.getTypeName());
                objArr.add(dto.getBrandName());
                objArr.add(sku.getGoodsId());
                objArr.add(sku.getOnlineId());
                objArr.add(sku.getStatusText());
                objArr.add(sku.getSalePriceText());
                objList.add(objArr.toArray());
            }

        }
        HSSFWorkbook book = ExcelUtil.buildExcel(header, objList);
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("UTF-8");
        String fileName = new String("商品库信息.xls".getBytes("gbk"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        book.write(ouputStream);
        ouputStream.flush();
        if (ouputStream != null) {
            ouputStream.close();
        }
    }

    /**
     * op平台根据goodId获取详情
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("skuDetail")
    @ResponseBody
    public ResultInfo getSkuDetailForWeb(HttpServletRequest request, @RequestBody JSONObject reqJson) {
       //保存商品访问记录
//        createVisit(request,reqJson);
        Long goodsId = reqJson.getLong("goodsId");
        OpGoodsDetailDto opGoodsDetailDto = goodsService.getOpGoodsDetail(goodsId);
        return returnSuccess(opGoodsDetailDto);
    }

//    /**
//     * 保存商品访问记录
//     * @param request
//     * @param jsonObject
//     */
//    private void createVisit(HttpServletRequest request, JSONObject jsonObject) {
//        //String channel = request.getParameter("channel");
//        TVisit tVisit = new TVisit();
//        tVisit.setcIp(SessionUtil.getIpAddr());
//        tVisit.setCreateTime(new Date());
//        tVisit.setFlatformType(ChannelType.PC.getValue());
//        tVisit.setSkuId(jsonObject.getLong("goodsId"));
//        tVisit.setUserId(UserUtil.currentCustomer().getId());
//        tVisit.setVisitType(VisitType.VISITORS.getValue());
//        if (tVisit.getUserId() != null) {
//            tVisit.setVisitType(VisitType.CUSTOMER.getValue());
//        }
//        goodsService.createVisit(tVisit);
//    }

    /**
     * 获取商品参数
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("paramDetail")
    @ResponseBody
    public ResultInfo getGoodsParam(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long goodsId = reqJson.getLong("goodsId");
        Long typeId = reqJson.getLong("typeId");
        List<OpParamChannelDto> paramChannelList = goodsService.getOpGoodsParam(goodsId, typeId);
        return returnSuccess(paramChannelList);
    }


    /**
     * 获取某条上架商品数据
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("onlineDetail")
    @ResponseBody
    public ResultInfo getGoodsOnlineDataWeb(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long onlineId = reqJson.getLong("goodsOnlineId");
        TGoodsOnline goodsOnline = goodsService.findGoodsOnline(onlineId);
        return returnSuccess(goodsOnline);
    }

    /**
     * OP平台删除商品spu
     * @param reqJson
     * @return
     */
    @PostMapping("deleteSpuForWeb")
    @ResponseBody
    public ResultInfo deleteSpuForWeb(@RequestBody JSONObject reqJson) {
        Long modelId = reqJson.getLong("modelId");
        goodsService.deleteGoodsSpu(modelId);
        return returnSuccess();
    }

    /**
     * OP平台删除商品sku
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("deleteSkuForWeb")
    @ResponseBody
    public ResultInfo deleteSkuForWeb(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long goodsId = reqJson.getLong("goodsId");
        goodsService.deleteGoods(goodsId);
        return returnSuccess();
    }

    /**
     * 新建商品索引
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("build/index")
    @ResponseBody
    public ResultInfo buildIndex(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        goodsService.buildIndex();
        return returnSuccess();
    }
}
