package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.JsonUtil;
import com.dripop.goodspackage.dto.OpComboGoodsDto;
import com.dripop.goodspackage.dto.OpComboPackSettingDto;
import com.dripop.goodspackage.dto.OpComboPackageDto;
import com.dripop.goodspackage.dto.OpComboPackageGoodsReq;
import com.dripop.goodspackage.service.ComboPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by liyou on 2018/1/10.
 */
@Controller
@RequestMapping("combopackage")
public class ComboPackageController extends BaseController {

    @Autowired
    private ComboPackageService comboPackageService;

    /**
     * OP端组合套装配置
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("setting")
    @ResponseBody
    public ResultInfo setting(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        OpComboPackSettingDto comboPackSettingDto = JsonUtil.fromJson(jsonObject, OpComboPackSettingDto.class);
        comboPackageService.setting(comboPackSettingDto);
        return returnSuccess();
    }

    /**
     * OP端配置组合套装商品列表
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("page")
    @ResponseBody
    public ResultInfo page(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        OpComboPackageGoodsReq condition = JsonUtil.fromJson(jsonObject, OpComboPackageGoodsReq.class);
        Integer pageNo = jsonObject.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<OpComboGoodsDto> page = comboPackageService.opComboPackageGoodsPage(pageable, condition);
        return returnSuccess(page);
    }

    /**
     * OP端组合套装列表
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("list")
    @ResponseBody
    public ResultInfo list(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        Long onlineId = jsonObject.getLong("onlineId");
        List<OpComboPackageDto> packageList = comboPackageService.opComboPackageList(onlineId);
        return returnSuccess(packageList);
    }

    /**
     * OP端组合套装信息
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("info")
    @ResponseBody
    public ResultInfo opComboPackageInfo(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        Long onlineId = jsonObject.getLong("onlineId");
        Map map = comboPackageService.opComboPackageInfo(onlineId);
        return returnSuccess(map);
    }

    /**
     * OP端删除组合套装
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        Long onlineId = jsonObject.getLong("onlineId");
        comboPackageService.deteleComboPackage(onlineId);
        return returnSuccess();
    }
}
