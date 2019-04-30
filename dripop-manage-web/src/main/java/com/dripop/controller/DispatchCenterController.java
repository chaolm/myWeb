package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.JsonUtil;
import com.dripop.dispatchcenter.dto.DispatchShopInfo;
import com.dripop.dispatchcenter.dto.GetDispatchCenterReq;
import com.dripop.dispatchcenter.dto.GetDispatchInfo;
import com.dripop.dispatchcenter.dto.OrderId;
import com.dripop.dispatchcenter.service.DispatchCenterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("dispatchCenter")
public class DispatchCenterController extends BaseController{

    @Autowired
    private DispatchCenterInfoService dispatchCenterInfoService;

    /**
     * 查询派单中心信息
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getDispatchCenterInfo")
    @ResponseBody
    public ResultInfo getAllGoodsInfo(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        GetDispatchCenterReq requDto = JsonUtil.fromJson(reqJson, GetDispatchCenterReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<GetDispatchInfo> page = dispatchCenterInfoService.getDispatchCenterInfo(requDto,pageable);
        return returnSuccess(page);
    }

    /**
     * 查询库存
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("queryStock")
    @ResponseBody
    public ResultInfo queryStock(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long orgId = reqJson.getLong("orgId");
        OrderId  orderId = JsonUtil.fromJson(reqJson, OrderId.class);
        List<DispatchShopInfo> list = dispatchCenterInfoService.queryStock(orderId,orgId);
        return returnSuccess(list);
    }

    /**
     * 确认派单或改派
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("submitDispatcher")
    @ResponseBody
    public ResultInfo submitDispatcher(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        OrderId  orderId = JsonUtil.fromJson(reqJson, OrderId.class);
        Long orgId = reqJson.getLong("orgId");
        dispatchCenterInfoService.submitDispatcher(orderId,orgId);
        return returnSuccess();
    }
}
