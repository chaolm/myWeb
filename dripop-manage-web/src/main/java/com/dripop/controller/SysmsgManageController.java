package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.entity.TSysmsg;
import com.dripop.sys.dto.SysmsgManageDto;
import com.dripop.sys.service.SysmsgManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台消息管理 controller
 *
 * @author dq
 * @date 2018/3/19 10:24
 */
@Controller
@RequestMapping("sysmsgPlatform")
public class SysmsgManageController extends BaseController {

    @Autowired
    private SysmsgManageService sysmsgManageService;

    /**
     * 新增
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("saveSysmsg")
    @ResponseBody
    public ResultInfo saveSysmsg(HttpServletRequest request, @RequestBody TSysmsg sysmsg) {
        if (StringUtil.isBlank(sysmsg.getTitle())) {
            throw new ServiceException("通知标题不能为空");
        }
        if (StringUtil.isBlank(sysmsg.getContent())) {
            throw new ServiceException("通知副标题不能为空");
        }
        if (sysmsg.getStartTime() == null) {
            throw new ServiceException("开始时间不能为空");
        }
        if (sysmsg.getExpireTime() == null) {
            throw new ServiceException("结束时间不能为空");
        }
        sysmsgManageService.save(sysmsg);
        return returnSuccess();
    }

    /**
     * 后台消息通知列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("sysmsgList")
    @ResponseBody
    public ResultInfo sysmsgList(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        SysmsgManageDto sysmsgManageDto = JsonUtil.fromJson(reqJson, SysmsgManageDto.class);
        Pagination<SysmsgManageDto> list = sysmsgManageService.sysmsgList(sysmsgManageDto, pageable);
        return returnSuccess(list);
    }


    /**
     * 删除后台消息通知
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        if (id == null) {
            throw new ServiceException("确实必要参数");
        }
        sysmsgManageService.delete(id);
        return returnSuccess();
    }

    /**
     *  撤销后台消息通知
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("repeal")
    @ResponseBody
    public ResultInfo repeal(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        if (id == null) {
            throw new ServiceException("确实必要参数");
        }
        sysmsgManageService.repeal(id);
        return returnSuccess();
    }

}
