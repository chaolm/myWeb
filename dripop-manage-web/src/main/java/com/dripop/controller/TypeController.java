package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.JsonUtil;
import com.dripop.entity.TGoodsType;
import com.dripop.goods.dto.OpTypeDto;
import com.dripop.goods.service.GoodsTypeService;
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
@RequestMapping("type")
public class TypeController extends BaseController {

    @Autowired
    private GoodsTypeService goodsTypeService;

    /**
     * 新增类目
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("saveType")
    @ResponseBody
    public ResultInfo saveType(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        TGoodsType goodsType = JsonUtil.fromJson(jsonObject, TGoodsType.class);
        String brandIds = jsonObject.getString("brandIds");
        goodsTypeService.saveType(goodsType, brandIds);
        return returnSuccess();
    }

    /**
     * 修改类目
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("updateType")
    @ResponseBody
    public ResultInfo updateType(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        TGoodsType goodsType = JsonUtil.fromJson(jsonObject, TGoodsType.class);
        String brandIds = jsonObject.getString("brandIds");
        goodsTypeService.updateType(goodsType, brandIds);
        return returnSuccess();
    }

    /**
     * 删除类目
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("deleteType")
    @ResponseBody
    public ResultInfo deleteType(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        Long id = jsonObject.getLong("id");
        goodsTypeService.deleteType(id);
        return returnSuccess();
    }

    /**
     * 分页类目列表
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("pageType")
    @ResponseBody
    public ResultInfo pageType(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        String name = jsonObject.getString("name");
        Integer pageNo = jsonObject.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<OpTypeDto> page = goodsTypeService.pageType(name, pageable);
        return returnSuccess(page);
    }

    /**
     * 类目列表
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("listType")
    @ResponseBody
    public ResultInfo listType(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        Long parentId = jsonObject.getLong("parentId");
        List<TGoodsType> list = goodsTypeService.listType(parentId);
        return returnSuccess(list);
    }

    /**
     * 类目列表
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("listTypeSort")
    @ResponseBody
    public ResultInfo listTypeSort(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Integer isFull = reqJson.getInteger("isFull");
        List<TGoodsType> list = goodsTypeService.listTypeSort(isFull);
        return returnSuccess(list);
    }
}
