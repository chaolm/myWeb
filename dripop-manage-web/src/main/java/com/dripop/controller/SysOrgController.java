package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.entity.TSysOrg;
import com.dripop.sys.dto.OpStoreSearchDto;
import com.dripop.sys.dto.OpStoreSearchReq;
import com.dripop.sys.service.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liyou on 2018/1/10.
 */
@Controller
@RequestMapping("sysorg")
public class SysOrgController extends BaseController {

    @Autowired
    private SysOrgService sysOrgService;

    /**
     * OP分页查询门店
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("pageStore")
    @ResponseBody
    public ResultInfo pageStore(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        OpStoreSearchReq reqDto = JsonUtil.fromJson(reqJson, OpStoreSearchReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<OpStoreSearchDto> page = sysOrgService.pageStore(reqDto, pageable);
        return returnSuccess(page);
    }

    /**
     * OP查询所有门店
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("allStore")
    @ResponseBody
    public ResultInfo allStore(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        OpStoreSearchReq reqDto = JsonUtil.fromJson(reqJson, OpStoreSearchReq.class);
        List<OpStoreSearchDto> allList = sysOrgService.listStore(reqDto);
        return returnSuccess(allList);
    }

    /**
     * OP门店营业状态设置
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("businessSetting")
    @ResponseBody
    public ResultInfo businessSetting(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long orgId = reqJson.getLong("orgId");
        Integer status = reqJson.getInteger("status");
        sysOrgService.businessSetting(orgId, status);
        return returnSuccess();
    }

    /**
     * OP删除门店
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("deleteStore")
    @ResponseBody
    public ResultInfo deleteStore(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long orgId = reqJson.getLong("orgId");
        sysOrgService.deleteStore(orgId);
        return returnSuccess();
    }

    /**
     * OP新增门店
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("addStore")
    @ResponseBody
    public ResultInfo addStore(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        TSysOrg sysOrg = JsonUtil.fromJson(reqJson, TSysOrg.class);
        String logoStr= reqJson.getString("logo");
        String password = reqJson.getString("password");
        sysOrgService.addStore(sysOrg, logoStr,password);
        return returnSuccess();
    }

    /**
     * OP修改门店
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("updateStore")
    @ResponseBody
    public ResultInfo updateStore(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        TSysOrg sysOrg = JsonUtil.fromJson(reqJson, TSysOrg.class);
        //门店风采图片logoStr
        String logoStr=reqJson.getString("logo");
        String password = reqJson.getString("password");
        sysOrgService.updateStore(sysOrg,logoStr,password);
        return returnSuccess();
    }

    /**
     * OP根据orgId查门店详情
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("storeDetail")
    @ResponseBody
    public ResultInfo storeDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long orgId = reqJson.getLong("orgId");
        TSysOrg sysOrg = sysOrgService.storeDetail(orgId);
        return returnSuccess(sysOrg);
    }
}
