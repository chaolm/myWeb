package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.JsonUtil;
import com.dripop.entity.TBrand;
import com.dripop.goods.service.BrandService;
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
@RequestMapping("brand")
public class BrandController extends BaseController {

    @Autowired
    private BrandService brandService;

    /**
     * 品牌配置-品牌查询
     * @param request,reqJson
     * @return
     */
    @PostMapping("page")
    @ResponseBody
    public ResultInfo brandPage(HttpServletRequest request, @RequestBody JSONObject reqJson){
        String name = reqJson.getString("name");
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<TBrand> page = brandService.brandPage(name, pageable);
        return returnSuccess(page);
    }

    /**
     * 类目品牌查询
     * @param request
     * @param reqJson
     * @return
     * @throws Exception
     */
    @PostMapping("list")
    @ResponseBody
    public ResultInfo list(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long typeId = reqJson.getLong("typeId");
        typeId = null;//忽略类目过滤条件
        List<TBrand> brandList = brandService.brandList(typeId);
        return returnSuccess(brandList);
    }

    /**
     * 新增品牌
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("saveBrand")
    @ResponseBody
    public ResultInfo saveBrand(HttpServletRequest request, @RequestBody JSONObject reqJson){
        TBrand brand = JsonUtil.fromJson(reqJson, TBrand.class);
        brandService.saveBrand(brand);
        return returnSuccess();
    }

    /**
     * 修改品牌
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("updateBrand")
    @ResponseBody
    public ResultInfo updateBrand(HttpServletRequest request, @RequestBody JSONObject reqJson){
        TBrand brand = JsonUtil.fromJson(reqJson, TBrand.class);
        brandService.updateBrand(brand);
        return returnSuccess();
    }

    /**
     * 删除品牌
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("deleteBrand")
    @ResponseBody
    public ResultInfo deleteBrand(HttpServletRequest request, @RequestBody JSONObject reqJson){
        Long id = reqJson.getLong("id");
        brandService.deleteBrand(id);
        return returnSuccess();
    }

}
