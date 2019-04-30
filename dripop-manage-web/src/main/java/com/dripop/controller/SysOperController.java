package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.JsonUtil;
import com.dripop.entity.TSysOper;
import com.dripop.entity.TSysRole;
import com.dripop.sys.dto.*;
import com.dripop.sys.service.SysOperService;
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
@RequestMapping("sysoper")
public class SysOperController extends BaseController {

    @Autowired
    private SysOperService sysOperService;

    /**
     * 店员分页查询
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("pageOper")
    @ResponseBody
    public ResultInfo pageOper(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        OpOperSearchReq reqDto = JsonUtil.fromJson(reqJson, OpOperSearchReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<OpOperSearchDto> page = sysOperService.pageOper(reqDto, pageable);
        return returnSuccess(page);
    }

    /**
     * 设置店员状态启用、停用、审核通过、拒绝
     * @param request
     * @return
     */
    @PostMapping("setting")
    @ResponseBody
    public ResultInfo operStatusSetting(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long operId = reqJson.getLong("operId");
        Integer status = reqJson.getInteger("status");
        sysOperService.operStatusSetting(operId, status);
        return returnSuccess();
    }

    /**
     * OP删除店员
     * @param request
     * @return
     */
    @PostMapping("deleteOper")
    @ResponseBody
    public ResultInfo deleteOper(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long operId = reqJson.getLong("operId");
        sysOperService.deleteOper(operId);
        return returnSuccess();
    }

    /**
     * OP新增店员
     * @param request
     * @return
     */
    @PostMapping("addOper")
    @ResponseBody
    public ResultInfo addOper(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        TSysOper sysOper = JsonUtil.fromJson(reqJson, TSysOper.class);
        Long roleId = reqJson.getLong("roleId");
        Long orgId = reqJson.getLong("orgId");
        //短信通知店员登录密码
        sysOperService.addOper(sysOper, roleId, orgId);
        return returnSuccess();
    }

    /**
     * OP修改店员
     * @param request
     * @return
     */
    @PostMapping("updateOper")
    @ResponseBody
    public ResultInfo updateOper(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        TSysOper sysOper = JsonUtil.fromJson(reqJson, TSysOper.class);
        Long roleId = reqJson.getLong("roleId");
        Long orgId = reqJson.getLong("orgId");
        sysOperService.updateOper(sysOper, roleId, orgId);
        return returnSuccess();
    }

    /**
     * 管理员查询
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("listAdminOper")
    @ResponseBody
    public ResultInfo listAdminOper(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        List<OpOperSearchDto> list = sysOperService.listAdminOper();
        return returnSuccess(list);
    }

    /**
     * 新增管理员用户
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("addAdminOper")
    @ResponseBody
    public ResultInfo addAdminOper(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        TSysOper sysOper = JsonUtil.fromJson(reqJson, TSysOper.class);
        Long roleId = reqJson.getLong("roleId");
        sysOperService.addAdminOper(sysOper, roleId);
        return returnSuccess();
    }

    /**
     * 修改管理员用户
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("updateAdminOper")
    @ResponseBody
    public ResultInfo updateAdminOper(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        TSysOper sysOper = JsonUtil.fromJson(reqJson, TSysOper.class);
        Long roleId = reqJson.getLong("roleId");
        sysOperService.updateAdminOper(sysOper, roleId);
        return returnSuccess();
    }

    /**
     * 删除管理员用户
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("deleteAdminOper")
    @ResponseBody
    public ResultInfo deleteAdminOper(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long operId = reqJson.getLong("operId");
        sysOperService.deleteAdminOper(operId);
        return returnSuccess();
    }

    /**
     * 角色查询
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("listRole")
    @ResponseBody
    public ResultInfo listRole(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        List<TSysRole> list = sysOperService.listRole();
        return returnSuccess(list);
    }

    /**
     *新增角色
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("createRole")
    @ResponseBody
    public ResultInfo createRole(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        String roleName = reqJson.getString("roleName");
        String remark = reqJson.getString("remark");
        sysOperService.createRole(roleName,remark);
        return returnSuccess();
    }

    /**
     * 删除角色
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("deleteRole")
    @ResponseBody
    public ResultInfo deleteRole(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long roleId = reqJson.getLong("roleId");
        sysOperService.deleteRole(roleId);
        return returnSuccess();
    }
    /**
     * 获取角色权限
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getPermission")
    @ResponseBody
    public ResultInfo getPermission(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long roleId = reqJson.getLong("roleId");
        List list = sysOperService.getPermission(roleId);
        return returnSuccess(list);
    }

    /**
     * 权限设置
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("roleManage")
    @ResponseBody
    public ResultInfo roleManage(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long roleId = reqJson.getLong("roleId");
        MenuIdsReq menuIds = JsonUtil.fromJson(reqJson, MenuIdsReq.class);
        sysOperService.roleManage(roleId,menuIds);
        return returnSuccess();
    }

    /**
     * 获取店员详情
     * @param request
     * @return
     */
    @PostMapping("operDetail")
    @ResponseBody
    public ResultInfo operDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long operId = reqJson.getLong("operId");
        Long roleId = reqJson.getLong("roleId");
        OpOperDetailDto opOperDetailDto = sysOperService.operDetail(operId,roleId);
        return returnSuccess(opOperDetailDto);
    }
}
