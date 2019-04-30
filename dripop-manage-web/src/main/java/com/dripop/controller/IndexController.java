package com.dripop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.entity.TStaticData;
import com.dripop.goods.dto.GoodsClassDto;
import com.dripop.goods.service.GoodsService;
import com.dripop.indexArea.dto.IndexAreaDetailDto;
import com.dripop.indexArea.dto.IndexAreaDto;
import com.dripop.indexArea.dto.StaticDataDetail;
import com.dripop.indexArea.service.IndexAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商城配置 controller
 *
 * @author dq
 * @date 2018/3/12 13:44
 */

@Controller
@RequestMapping("area")
public class IndexController extends BaseController {
    @Autowired
    private IndexAreaService indexAreaService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 保存广告图
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("saveStaticDate")
    @ResponseBody
    public ResultInfo saveStaticDate(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        String extendParams = reqJson.getString("extendParams");
        TStaticData tStaticData = new TStaticData();
        tStaticData.setExtendParams(extendParams);
        indexAreaService.saveStaticDate(tStaticData);
        return returnSuccess();
    }

    /**
     * 保存PC商城登陆页面背景图片
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("saveBackPicture")
    @ResponseBody
    public ResultInfo saveBackPicture(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        String imgUrl = reqJson.getString("imgUrl");
        indexAreaService.saveBackPicture(imgUrl);
        return returnSuccess();
    }
    /**
     * PC商城获取登陆背景图片
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getLoginBackPic")
    @ResponseBody
    public ResultInfo getLoginBackPic(HttpServletRequest request,@RequestBody JSONObject reqJson) {
        String result = indexAreaService.getLoginBackPic();
        return super.returnSuccess(result);
    }
    /**
     * 删除广告图
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("deleteStaticDate")
    @ResponseBody
    public ResultInfo deleteStaticDate(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Integer id = reqJson.getInteger("id");
        indexAreaService.deleteStaticDate(id);
        return returnSuccess();
    }


    /**
     * 保存首页、专区
     *
     * @param request
     * @param indexAreaDetailDto
     * @return
     */
    @PostMapping("saveArea")
    @ResponseBody
    public ResultInfo saveArea(HttpServletRequest request, @RequestBody IndexAreaDetailDto indexAreaDetailDto) {
        indexAreaService.saveArea(indexAreaDetailDto);
        return returnSuccess();
    }

    /**
     * 删除专区
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("deleteArea")
    @ResponseBody
    public ResultInfo deleteArea(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        if (id == null) {
            throw new ServiceException("缺失必要参数");
        }
        indexAreaService.deleteArea(id);
        return returnSuccess();
    }

    /**
     * 编辑商品分类
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("editGoodsType")
    @ResponseBody
    public ResultInfo editGoodsType(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        JSONArray goodTypeList = reqJson.getJSONArray("goodTypeList");
        if (goodTypeList == null || goodTypeList.isEmpty()) {
            throw new ServiceException("缺失必要参数");
        }
        goodsService.editGoodsType(goodTypeList);
        return returnSuccess();
    }

    /**
     * 广告图详情
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("staticDateDetail")
    @ResponseBody
    public ResultInfo staticDateDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {

        StaticDataDetail staticDataDetail = indexAreaService.staticDateDetail();
        return returnSuccess(staticDataDetail);
    }

    /**
     * 专区列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("areaList")
    @ResponseBody
    public ResultInfo areaList(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Integer dataType = reqJson.getInteger("dataType");
        List<IndexAreaDto> list = indexAreaService.areaList(dataType);
        return returnSuccess(list);
    }

    /**
     * 专区详情
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("areaDetail")
    @ResponseBody
    public ResultInfo areaDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long areaId = reqJson.getLong("areaId");
        Integer dataType = reqJson.getInteger("dataType");
        IndexAreaDetailDto detailDto = indexAreaService.areaDetail(areaId,dataType);
        return returnSuccess(detailDto);
    }

    /**
     * 商品分类详情
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("goodsTypeDetail")
    @ResponseBody
    public ResultInfo goodsTypeDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        List<GoodsClassDto> list = goodsService.goodsTypeDetail();
        return returnSuccess(list);
    }

    /**
     * 专区排序
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("areaSort")
    @ResponseBody
    public ResultInfo areaSort(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        Integer sort = reqJson.getInteger("sort");
        if (id == null || sort == null) {
            throw new ServiceException("缺失必要参数");
        }
        indexAreaService.areaSort(id, sort);
        return returnSuccess();
    }
}
